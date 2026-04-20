package com.fatihkaracoban.job_follow_system.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fatihkaracoban.job_follow_system.entities.Kullanici;

public interface IKullaniciRepository extends JpaRepository<Kullanici, Integer> {

    Optional<Kullanici> findByKullaniciAdi(String kullaniciAdi);
}
