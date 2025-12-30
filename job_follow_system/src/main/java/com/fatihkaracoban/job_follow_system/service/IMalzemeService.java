package com.fatihkaracoban.job_follow_system.service;

import java.util.List;

import com.fatihkaracoban.job_follow_system.entities.Malzeme;

public interface IMalzemeService {
    
    List<Malzeme> getMalzemelerByIsId(Integer isId);
    Malzeme saveMalzeme(Malzeme malzeme);
    void deleteMalzeme(Integer malzemeId);
    Malzeme malzemeGetirById(Integer id);
}