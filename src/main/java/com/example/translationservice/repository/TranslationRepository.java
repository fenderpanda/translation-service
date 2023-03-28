package com.example.translationservice.repository;

import com.example.translationservice.entity.Status;
import com.example.translationservice.entity.Translation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TranslationRepository extends JpaRepository<Translation, Long> {
    @Query("select t.status from Translation t where t.id = :id")
    String getStatus(@Param(value = "id") long translationId);
    @Modifying
    @Transactional
    @Query("update Translation t set t.status = :status where t.id = :id")
    void setStatus(@Param(value = "id") long translationId,
                   @Param(value = "status") Status status);
}
