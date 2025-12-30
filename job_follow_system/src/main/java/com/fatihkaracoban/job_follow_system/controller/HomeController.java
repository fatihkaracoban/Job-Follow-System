package com.fatihkaracoban.job_follow_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fatihkaracoban.job_follow_system.entities.Is;
import com.fatihkaracoban.job_follow_system.service.IIsService;

@Controller
@RequestMapping("/") 
public class HomeController {

    private final IIsService isService; 

    public HomeController(IIsService isService) {
        this.isService = isService;
    }

    @GetMapping
    public String listAllJobs(Model model) {
        model.addAttribute("isler", isService.getAll());
        return "index"; 
    }
    
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("is", new Is());
        return "add-job"; 
    }

    @PostMapping("/add")
    public String addJob(@ModelAttribute("is") Is is, RedirectAttributes redirectAttributes) {
        isService.isKaydet(is);
        redirectAttributes.addFlashAttribute("successMessage", "✅ İş başarıyla eklendi: " + is.getIsAdi()); 
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteJob(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        Is deletedIs = isService.isGetirById(id);
        if (deletedIs != null) {
            isService.isSil(id);
            redirectAttributes.addFlashAttribute("successMessage", "🗑️ İş başarıyla silindi: " + deletedIs.getIsAdi());
        }
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        Is is = isService.isGetirById(id);
        if (is == null) {
            return "redirect:/";
        }
        model.addAttribute("is", is);
        return "edit-job";
    }

    @PostMapping("/update/{id}")
    public String updateJob(@PathVariable("id") Integer id, @ModelAttribute("is") Is is, RedirectAttributes redirectAttributes) {
        is.setId(id);
        isService.isKaydet(is);
        redirectAttributes.addFlashAttribute("successMessage", "🔄 İş başarıyla güncellendi: " + is.getIsAdi());
        return "redirect:/";
    }
}