package com.example.QuanLyCanBo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.QuanLyCanBo.model.ChucVu;
import com.example.QuanLyCanBo.service.ChucVuService;

@Controller
@RequestMapping("/chucvu")
public class ChucVuController {

    @Autowired
    private ChucVuService chucVuService;

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("list", chucVuService.getAllChucVu());
        return "chucvu/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("chucvu", new ChucVu());
        return "chucvu/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("chucvu") ChucVu chucVu) {
        chucVuService.save(chucVu);
        return "redirect:/chucvu";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        model.addAttribute("chucvu", chucVuService.getChucVuById(id));
        return "chucvu/edit";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute("chucvu") ChucVu chucVu) {
        chucVuService.save(chucVu);
        return "redirect:/chucvu";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        chucVuService.delete(id);
        return "redirect:/chucvu";
    }
}
