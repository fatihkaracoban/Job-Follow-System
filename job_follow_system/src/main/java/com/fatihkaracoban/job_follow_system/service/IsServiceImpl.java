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

    // Spring 4.3'ten sonra tek constructor varsa @Autowired yazmaya gerek yoktur.
    public IsServiceImpl(IIsRepository isRepository) {
        this.isRepository = isRepository;
    }

    @Override
    public List<Is> getAll() {
        return isRepository.findAll();
    }

    @Override
    public List<Is> getIslerByDurum(String durum) {
        return isRepository.findByDurum(durum);
    }

    @Override
    public Is isGetirById(Integer id) {
        return isRepository.findById(id).orElse(null);
    }

    @Override
    public Is isKaydet(Is is) {
        // --- KRİTİK BÖLÜM: Malzeme Silinmesini Engelleme ---
        if (is.getId() != null) {
            isRepository.findById(is.getId()).ifPresent(existingIs -> {
                if (existingIs.getMalzemeler() != null) {
                    is.setMalzemeler(existingIs.getMalzemeler());
                }
            });
        }

        if (is.getDurum() == null || is.getDurum().isEmpty()) {
            is.setDurum("Yapılacak");
        }

        if (is.getMalzemeler() != null) {
            is.getMalzemeler().forEach(malzeme -> malzeme.setIs(is));
        }

        return isRepository.save(is);
    }

    @Override
    public void isBitir(Integer id) {
        // İhtiyaç varsa buraya mantık eklenebilir.
    }

    @Override
    public void isSil(Integer id) {
        isRepository.deleteById(id);
    }

    @Override
    public List<Is> getHatirlatilacakIsler() {
        return isRepository.findAll().stream()
                .filter(is -> "Yapılacak".equals(is.getDurum()))
                .filter(is -> {
                    // HATA DÜZELTİLDİ: Artık parse işlemine gerek yok.
                    LocalDate baslamaTarihi = is.getBaslamaTarihi();
                    if (baslamaTarihi == null) {
                        return false;
                    }

                    LocalDate bugun = LocalDate.now();
                    LocalDate yediGunSonra = bugun.plusDays(7);

                    // Bugün veya bugünden sonraki 7 gün içindeki işleri filtreler
                    return !baslamaTarihi.isBefore(bugun) && baslamaTarihi.isBefore(yediGunSonra);
                })
                .toList();
    }
}
