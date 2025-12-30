package com.fatihkaracoban.job_follow_system.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fatihkaracoban.job_follow_system.entities.Malzeme;
import com.fatihkaracoban.job_follow_system.repository.IMalzemeRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MalzemeServiceImpl implements IMalzemeService {

    private final IMalzemeRepository malzemeRepository;

    public MalzemeServiceImpl(IMalzemeRepository malzemeRepository) {
        this.malzemeRepository = malzemeRepository;
    }

    @Override
    public List<Malzeme> getMalzemelerByIsId(Integer isId) {
        // Tüm malzemeleri çekip Java Stream ile filtreleyerek çözüldü
        return malzemeRepository.findAll().stream()
                .filter(malzeme -> malzeme.getIs().getId().equals(isId))
                .collect(Collectors.toList());
    }

    @Override
    public Malzeme saveMalzeme(Malzeme malzeme) {
        return malzemeRepository.save(malzeme);
    }

    @Override
    public void deleteMalzeme(Integer malzemeId) {
        malzemeRepository.deleteById(malzemeId);
    }

    @Override
    public Malzeme malzemeGetirById(Integer id) {
        return malzemeRepository.findById(id).orElse(null);
    }

}
