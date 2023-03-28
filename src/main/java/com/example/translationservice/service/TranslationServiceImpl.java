package com.example.translationservice.service;

import com.example.translationservice.entity.Status;
import com.example.translationservice.entity.Translation;
import com.example.translationservice.repository.TranslationRepository;
import org.springframework.stereotype.Service;

@Service
public class TranslationServiceImpl implements TranslationService{

    private final TranslationRepository translationRepository;

    public TranslationServiceImpl(TranslationRepository translationRepository) {
        this.translationRepository = translationRepository;
    }

    @Override
    public long create(String userId, long fileId) {
        Translation translation = new Translation();
        translation.setUserId(userId);
        translation.setFileId(fileId);
        translation.setStatus(Status.IN_PROCESS);

        return translationRepository.save(translation).getId();
    }

    @Override
    public void updateStatus(long translationId, Status status) {
        translationRepository.setStatus(translationId, status);
    }

    @Override
    public String getStatus(long translationId) {
        return translationRepository.getStatus(translationId);
    }
}
