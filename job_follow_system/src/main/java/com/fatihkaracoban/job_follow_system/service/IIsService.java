package com.fatihkaracoban.job_follow_system.service;

import com.fatihkaracoban.job_follow_system.entities.Is;
import java.util.List;

public interface IIsService {

    List<Is> getAll();

    Is isGetirById(Integer id);

    Is isKaydet(Is is);

    void isSil(Integer id);

    List<Is> getHatirlatilacakIsler();
}
