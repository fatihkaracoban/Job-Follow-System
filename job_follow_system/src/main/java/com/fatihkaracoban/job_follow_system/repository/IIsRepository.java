package com.fatihkaracoban.job_follow_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fatihkaracoban.job_follow_system.entities.Is;

@Repository
public interface IIsRepository extends JpaRepository<Is, Integer> {

    List<Is> findByDurum(String durum);
}