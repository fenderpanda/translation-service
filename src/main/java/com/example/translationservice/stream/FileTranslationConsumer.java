package com.example.translationservice.stream;

import com.example.translationservice.entity.Status;
import com.example.translationservice.payload.MessageFileDto;
import com.example.translationservice.service.TranslationService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class FileTranslationConsumer {

    private final TranslationService translationService;

    public FileTranslationConsumer(TranslationService translationService) {
        this.translationService = translationService;
    }

    @Bean
    public Consumer<MessageFileDto> onTranslationComplete() {
        return (message) -> {
            translationService.updateStatus(message.getTranslationId(), Status.DONE);
        };
    }
}
