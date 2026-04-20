package com.fatihkaracoban.job_follow_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.fatihkaracoban.job_follow_system.entities.Is;
import com.fatihkaracoban.job_follow_system.service.IIsService;

import jakarta.validation.Valid;

@Controller
public class HomeController {

    private final IIsService isService;

    public HomeController(IIsService isService) {
        this.isService = isService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("isler", isService.getAll());
        return "index";
    }

    @GetMapping("/add")
    public String addPage(Model model) {
        model.addAttribute("is", new Is());
        return "add-job";
    }

    @PostMapping("/add")
    public String addJob(@Valid @ModelAttribute("is") Is is, BindingResult result) {
        if (result.hasErrors()) {
            return "add-job";
        }
        isService.isKaydet(is);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Integer id, Model model) {
        model.addAttribute("is", isService.isGetirById(id));
        return "edit-job";
    }

    @PostMapping("/update/{id}")
    public String updateJob(@PathVariable Integer id, @Valid @ModelAttribute("is") Is is, BindingResult result) {
        if (result.hasErrors()) {
            return "edit-job";
        }
        is.setId(id);
        isService.isKaydet(is);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteJob(@PathVariable Integer id) {
        isService.isSil(id);
        return "redirect:/";
    }
}
