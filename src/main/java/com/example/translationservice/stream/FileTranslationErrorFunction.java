package com.example.translationservice.stream;

import com.example.translationservice.entity.Status;
import com.example.translationservice.payload.MessageFileDto;
import com.example.translationservice.service.TranslationService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class FileTranslationErrorFunction {
    private final TranslationService translationService;

    public FileTranslationErrorFunction(TranslationService translationService) {
        this.translationService = translationService;
    }

    @Bean
    public Function<MessageFileDto, MessageFileDto> onRefundComplete() {
        return (message) -> {
            translationService.updateStatus(message.getTranslationId(), Status.FAILED);

            return message;
        };
    }
}
