package com.fatihkaracoban.job_follow_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fatihkaracoban.job_follow_system.entities.Is;

public interface IIsRepository extends JpaRepository<Is, Integer> {

    List<Is> findByAktifTrue(); // Sadece silinmemiş işleri getirir

    List<Is> findByDurumAndAktifTrue(String durum);
}
