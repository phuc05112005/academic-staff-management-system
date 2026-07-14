package com.example.QuanLyCanBo.controller;

import com.example.QuanLyCanBo.model.Khoa;
import com.example.QuanLyCanBo.service.KhoaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/khoa")
public class KhoaController {

    @Autowired
    private KhoaService khoaService;

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("list", khoaService.findAll());
        return "khoa/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("khoa", new Khoa());
        return "khoa/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("khoa") Khoa khoa) {
        khoaService.save(khoa);
        return "redirect:/khoa";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        model.addAttribute("khoa", khoaService.findById(id));
        return "khoa/edit";
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute("khoa") Khoa khoa) {
        khoaService.save(khoa);
        return "redirect:/khoa";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        khoaService.delete(id);
        return "redirect:/khoa";
    }
}
