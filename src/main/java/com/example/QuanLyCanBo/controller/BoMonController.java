package com.example.QuanLyCanBo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.QuanLyCanBo.model.BoMon;
import com.example.QuanLyCanBo.service.BoMonService;
import com.example.QuanLyCanBo.service.KhoaService;

@Controller
public class BoMonController {

    @Autowired
    private BoMonService boMonService;

    @Autowired
    private KhoaService khoaService;

    @GetMapping("/bomon")
    public String listBoMon(Model model) {
        List<BoMon> list = boMonService.getAllBoMon();
        long totalBoMon = boMonService.countBoMon();

        model.addAttribute("listBoMon", list);
        model.addAttribute("totalBoMon", totalBoMon);

        return "bomon/list";
    }

    @GetMapping("/bomon/add")
    public String addBoMonForm(Model model) {
        model.addAttribute("boMon", new BoMon());
        model.addAttribute("listKhoa", khoaService.findAll());
        return "bomon/add";
    }

    @PostMapping("/bomon/add")
    public String saveBoMon(@ModelAttribute("boMon") BoMon boMon) {
        boMonService.save(boMon);
        return "redirect:/bomon";
    }

    @GetMapping("/bomon/edit/{id}")
    public String editBoMon(@PathVariable("id") Integer id, Model model) {
        BoMon boMon = boMonService.getBoMonById(id);
        model.addAttribute("boMon", boMon);
        model.addAttribute("listKhoa", khoaService.findAll());
        return "bomon/edit";
    }

    @PostMapping("/bomon/edit")
    public String updateBoMon(@ModelAttribute("boMon") BoMon boMon) {
        boMonService.save(boMon);
        return "redirect:/bomon";
    }

    @GetMapping("/bomon/delete/{id}")
    public String deleteBoMon(@PathVariable("id") Integer id) {
        boMonService.delete(id);
        return "redirect:/bomon";
    }
}
