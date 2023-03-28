package com.example.translationservice.controller;

import com.example.translationservice.entity.Status;
import com.example.translationservice.feignclient.FileStorageServiceClient;
import com.example.translationservice.feignclient.PaymentServiceClient;
import com.example.translationservice.payload.MessageFileDto;
import com.example.translationservice.service.TranslationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class TranslationController {
    @Value("${app.kafka.prepared.destination}")
    String kafkaTopicPrepared;
    private final PaymentServiceClient paymentServiceClient;
    private final FileStorageServiceClient fileStorageServiceClient;
    private final TranslationService translationService;
    private final StreamBridge streamBridge;

    public TranslationController(PaymentServiceClient paymentServiceClient,
                                 FileStorageServiceClient fileStorageServiceClient,
                                 TranslationService translationService,
                                 StreamBridge streamBridge) {
        this.paymentServiceClient = paymentServiceClient;
        this.fileStorageServiceClient = fileStorageServiceClient;
        this.translationService = translationService;
        this.streamBridge = streamBridge;
    }

    @PostMapping("translation")
    public ResponseEntity<Long> translate(
            JwtAuthenticationToken token,
            @RequestParam long fileId
    ) {
        String userId = token.getToken().getSubject();

        int cost = fileStorageServiceClient.getFileCost(fileId);

        long paymentId = paymentServiceClient.makePayment(fileId, cost);
        long translationId = translationService.create(userId, fileId);

        fileStorageServiceClient.updateStatus(fileId, Status.IN_PROCESS);

        MessageFileDto messageFileDto = fileStorageServiceClient.getMessageFileDto(fileId);
        messageFileDto.setTranslationId(translationId);
        messageFileDto.setPaymentId(paymentId);

        streamBridge.send(kafkaTopicPrepared, messageFileDto);

        return ResponseEntity.ok(fileId);
    }
}
