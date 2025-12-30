package com.fatihkaracoban.job_follow_system.service;

import java.util.List;

import com.fatihkaracoban.job_follow_system.entities.Is;

public interface IIsService {
    
    List<Is> getAll(); 
    List<Is> getIslerByDurum(String durum);
    List<Is> getHatirlatilacakIsler();
    Is isGetirById(Integer id);
    
    Is isKaydet(Is is);
    void isBitir(Integer id);
    void isSil(Integer id);
}