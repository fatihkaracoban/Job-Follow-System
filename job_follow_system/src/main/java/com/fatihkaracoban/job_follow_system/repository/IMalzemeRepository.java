package com.fatihkaracoban.job_follow_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fatihkaracoban.job_follow_system.entities.Malzeme;

@Repository
public interface IMalzemeRepository extends JpaRepository<Malzeme, Integer> {
}