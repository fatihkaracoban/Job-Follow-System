package com.fatihkaracoban.job_follow_system.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fatihkaracoban.job_follow_system.entities.Is;
import com.fatihkaracoban.job_follow_system.repository.IIsRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class IsServiceImpl implements IIsService {

    private final IIsRepository isRepository;

    public IsServiceImpl(IIsRepository isRepository) {
        this.isRepository = isRepository;
    }

    @Override
    public List<Is> getAll() {
        return isRepository.findByAktifTrue();
    }

    @Override
    public Is isGetirById(Integer id) {
        return isRepository.findById(id).orElse(null);
    }

    @Override
    public Is isKaydet(Is is) {
        if (is.getId() != null) {
            isRepository.findById(is.getId()).ifPresent(existing -> {
                if (is.getMalzemeler() == null) {
                    is.setMalzemeler(existing.getMalzemeler());
                }
            });
        }
        return isRepository.save(is);
    }

    @Override
    public void isSil(Integer id) {
        Is is = isGetirById(id);
        if (is != null) {
            is.setAktif(false); // Yumuşak silme
            isRepository.save(is);
        }
    }

    @Override
    public List<Is> getHatirlatilacakIsler() {
        LocalDate bugun = LocalDate.now();
        LocalDate yediGunSonra = bugun.plusDays(7);
        return getAll().stream()
                .filter(is -> "Yapılacak".equals(is.getDurum()))
                .filter(is -> is.getBaslamaTarihi() != null
                && !is.getBaslamaTarihi().isBefore(bugun)
                && is.getBaslamaTarihi().isBefore(yediGunSonra))
                .toList();
    }
}
