package com.example.translationservice.service;

import com.example.translationservice.entity.Status;

public interface TranslationService {
    long create(String userId, long fileId);
    void updateStatus(long translationId, Status status);
    String getStatus(long translationId);
}
