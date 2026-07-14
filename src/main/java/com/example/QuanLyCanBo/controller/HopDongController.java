package com.example.QuanLyCanBo.controller;

import com.example.QuanLyCanBo.model.HopDong;
import com.example.QuanLyCanBo.model.TaiKhoan;
import com.example.QuanLyCanBo.service.CanBoService;
import com.example.QuanLyCanBo.service.HopDongService;
import com.example.QuanLyCanBo.service.TaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/hopdong")
public class HopDongController {

    @Autowired
    private HopDongService hopDongService;

    @Autowired
    private CanBoService canBoService;

    @Autowired
    private TaiKhoanService taiKhoanService;

    @GetMapping
    public String list(Model model, Principal principal) {

        TaiKhoan tk = taiKhoanService.findByTenDangNhap(principal.getName());
        String role = tk.getVaiTro().toUpperCase();

        if (role.equals("ADMIN")) {
            model.addAttribute("listHopDong", hopDongService.getAll());
        } else { // GIANGVIEN
            Integer maCB = tk.getCanBo().getMaCB();
            model.addAttribute("listHopDong", hopDongService.getByCanBo(maCB));
        }

        return "hopdong/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("hopDong", new HopDong());
        model.addAttribute("listCanBo", canBoService.getAllCanBo());
        return "hopdong/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("hopDong") HopDong hopDong, Model model) {
        try {
            Integer maCB = hopDong.getCanBo() != null ? hopDong.getCanBo().getMaCB() : null;
            if (maCB == null) {
                model.addAttribute("error", "Vui lòng chọn cán bộ!");
                model.addAttribute("listCanBo", canBoService.getAllCanBo());
                return "hopdong/add";
            }

            var canBo = canBoService.getCanBoById(maCB);
            if (canBo == null) {
                model.addAttribute("error", "Cán bộ không tồn tại!");
                model.addAttribute("listCanBo", canBoService.getAllCanBo());
                return "hopdong/add";
            }
            hopDong.setCanBo(canBo);

            hopDongService.save(hopDong);
            return "redirect:/hopdong?success";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi hệ thống: " + e.getMessage());
            model.addAttribute("listCanBo", canBoService.getAllCanBo());
            return "hopdong/add";
        }
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Integer id,
            Principal principal,
            Model model) {

        TaiKhoan tk = taiKhoanService.findByTenDangNhap(principal.getName());
        String role = tk.getVaiTro().toUpperCase();

        HopDong hd = hopDongService.getById(id);

        if (role.equals("GIANGVIEN") &&
                !hd.getCanBo().getMaCB().equals(tk.getCanBo().getMaCB())) {
            return "redirect:/access-denied";
        }

        model.addAttribute("hopDong", hd);
        model.addAttribute("listCanBo", canBoService.getAllCanBo());
        return "hopdong/edit";
    }

    @PostMapping("/edit")
    public String editSave(@ModelAttribute HopDong hopDong,
            Principal principal) {

        TaiKhoan tk = taiKhoanService.findByTenDangNhap(principal.getName());
        String role = tk.getVaiTro().toUpperCase();

        if (role.equals("GIANGVIEN") &&
                !hopDong.getCanBo().getMaCB().equals(tk.getCanBo().getMaCB())) {
            return "redirect:/access-denied";
        }

        hopDongService.save(hopDong);
        return "redirect:/hopdong";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id,
            Principal principal) {

        TaiKhoan tk = taiKhoanService.findByTenDangNhap(principal.getName());
        String role = tk.getVaiTro().toUpperCase();

        HopDong hd = hopDongService.getById(id);

        if (role.equals("GIANGVIEN") &&
                !hd.getCanBo().getMaCB().equals(tk.getCanBo().getMaCB())) {
            return "redirect:/access-denied";
        }

        hopDongService.delete(id);
        return "redirect:/hopdong";
    }
}
