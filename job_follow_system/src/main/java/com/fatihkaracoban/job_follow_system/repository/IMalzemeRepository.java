package com.fatihkaracoban.job_follow_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fatihkaracoban.job_follow_system.entities.Malzeme;

public interface IMalzemeRepository extends JpaRepository<Malzeme, Integer> {

    List<Malzeme> findByIsId(Integer isId);
}
