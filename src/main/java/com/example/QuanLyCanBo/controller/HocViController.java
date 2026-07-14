package com.example.QuanLyCanBo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.QuanLyCanBo.model.HocVi;
import com.example.QuanLyCanBo.service.HocViService;

@Controller
public class HocViController {

    @Autowired
    private HocViService hocViService;

    @GetMapping("/hocvi")
    public String listHocVi(Model model) {
        model.addAttribute("listHocVi", hocViService.getAllHocVi());
        return "hocvi/list";
    }

    @GetMapping("/hocvi/add")
    public String addHocViForm(Model model) {
        model.addAttribute("hocVi", new HocVi());
        return "hocvi/add";
    }

    @PostMapping("/hocvi/add")
    public String saveHocVi(@ModelAttribute("hocVi") HocVi hocVi) {
        hocViService.save(hocVi);
        return "redirect:/hocvi";
    }

    @GetMapping("/hocvi/edit/{id}")
    public String editHocVi(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("hocVi", hocViService.getHocViById(id));
        return "hocvi/edit";
    }

    @PostMapping("/hocvi/edit")
    public String updateHocVi(@ModelAttribute("hocVi") HocVi hocVi) {
        hocViService.save(hocVi);
        return "redirect:/hocvi";
    }

    @GetMapping("/hocvi/delete/{id}")
    public String deleteHocVi(@PathVariable("id") Integer id) {
        hocViService.delete(id);
        return "redirect:/hocvi";
    }
}
