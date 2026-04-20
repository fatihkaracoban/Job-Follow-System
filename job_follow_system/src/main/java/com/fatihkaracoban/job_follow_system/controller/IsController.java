package com.fatihkaracoban.job_follow_system.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fatihkaracoban.job_follow_system.entities.Is;
import com.fatihkaracoban.job_follow_system.entities.IsFotograf;
import com.fatihkaracoban.job_follow_system.entities.Malzeme;
import com.fatihkaracoban.job_follow_system.repository.IsFotografRepository;
import com.fatihkaracoban.job_follow_system.service.IIsService;
import com.fatihkaracoban.job_follow_system.service.IMalzemeService;

@Controller
@RequestMapping("/isler")
public class IsController {

    private final IIsService isService;
    private final IMalzemeService malzemeService;
    private final IsFotografRepository fotografRepository;

    public IsController(IIsService isService, IMalzemeService malzemeService, IsFotografRepository fotografRepository) {
        this.isService = isService;
        this.malzemeService = malzemeService;
        this.fotografRepository = fotografRepository;
    }

    @GetMapping("/{id}")
    public String showIsDetail(@PathVariable("id") Integer id, Model model) {
        Is is = isService.isGetirById(id);
        if (is == null) {
            return "redirect:/";
        }
        model.addAttribute("is", is);
        model.addAttribute("malzemeler", is.getMalzemeler());
        model.addAttribute("malzeme", new Malzeme());
        return "is-detail";
    }

    @PostMapping("/{isId}/malzeme/add")
    public String addMalzeme(@PathVariable("isId") Integer isId,
            @ModelAttribute("malzeme") Malzeme malzeme,
            RedirectAttributes redirectAttributes) {
        Is is = isService.isGetirById(isId);
        if (is != null) {
            malzeme.setIs(is);
            malzemeService.saveMalzeme(malzeme);
            redirectAttributes.addFlashAttribute("successMessage", "➕ Malzeme başarıyla eklendi.");
        }
        return "redirect:/isler/" + isId;
    }

    @GetMapping("/{isId}/malzeme/delete/{malzemeId}")
    public String deleteMalzeme(@PathVariable("isId") Integer isId,
            @PathVariable("malzemeId") Integer malzemeId,
            RedirectAttributes redirectAttributes) {
        malzemeService.deleteMalzeme(malzemeId);
        redirectAttributes.addFlashAttribute("successMessage", "🗑️ Malzeme başarıyla silindi.");
        return "redirect:/isler/" + isId;
    }

    @GetMapping("/{isId}/malzeme/edit/{malzemeId}")
    public String malzemeDuzenleForm(@PathVariable Integer isId, @PathVariable Integer malzemeId, Model model) {
        Malzeme malzeme = malzemeService.malzemeGetirById(malzemeId);
        model.addAttribute("malzeme", malzeme);
        model.addAttribute("isId", isId);
        return "edit-malzeme";
    }

    @PostMapping("/{isId}/malzeme/update/{malzemeId}")
    public String malzemeGuncelle(@PathVariable Integer isId, @PathVariable Integer malzemeId,
            @ModelAttribute("malzeme") Malzeme malzeme, RedirectAttributes ra) {
        malzeme.setId(malzemeId);
        Is ilgiliIs = isService.isGetirById(isId);
        malzeme.setIs(ilgiliIs);
        malzemeService.saveMalzeme(malzeme); // saveMalzeme hem ekleme hem güncelleme yapar
        ra.addFlashAttribute("successMessage", "✅ Malzeme başarıyla güncellendi!");
        return "redirect:/isler/" + isId;
    }

    @PostMapping("/{isId}/fotograf/ekle")
    public String fotografYukle(@PathVariable("isId") Integer isId,
            @RequestParam("dosyalar") MultipartFile[] dosyalar,
            RedirectAttributes redirectAttributes) {
        Is ilgiliIs = isService.isGetirById(isId);

        if (ilgiliIs != null && dosyalar.length > 0) {
            // Dosyaların kaydedileceği klasör
            String uploadDir = System.getProperty("user.dir") + "/uploads/";

            try {
                // Klasör yoksa oluştur
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                for (MultipartFile dosya : dosyalar) {
                    if (!dosya.isEmpty()) {
                        // Aynı isimli dosyalar çakışmasın diye rastgele bir isim (UUID) üretiyoruz
                        String dosyaUzantisi = dosya.getOriginalFilename().substring(dosya.getOriginalFilename().lastIndexOf("."));
                        String yeniDosyaAdi = UUID.randomUUID().toString() + dosyaUzantisi;

                        // Dosyayı klasöre kaydet
                        Path filePath = uploadPath.resolve(yeniDosyaAdi);
                        Files.copy(dosya.getInputStream(), filePath);

                        // Veritabanına kaydet
                        IsFotograf foto = new IsFotograf();
                        foto.setDosyaAdi(yeniDosyaAdi);
                        foto.setIs(ilgiliIs);
                        fotografRepository.save(foto);
                    }
                }
                redirectAttributes.addFlashAttribute("successMessage", "📷 Fotoğraflar başarıyla yüklendi.");
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("errorMessage", "❌ Fotoğraf yüklenirken bir hata oluştu.");
                e.printStackTrace();
            }
        }
        return "redirect:/isler/" + isId;
    }
}
