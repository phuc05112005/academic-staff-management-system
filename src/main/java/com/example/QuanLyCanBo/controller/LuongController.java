package com.example.QuanLyCanBo.controller;

import com.example.QuanLyCanBo.model.CanBo;
import com.example.QuanLyCanBo.model.Luong;
import com.example.QuanLyCanBo.model.TaiKhoan;
import com.example.QuanLyCanBo.service.CanBoService;
import com.example.QuanLyCanBo.service.LuongService;
import com.example.QuanLyCanBo.service.TaiKhoanService;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/luong")
public class LuongController {

    @Autowired
    private LuongService luongService;

    @Autowired
    private CanBoService canBoService;

    @Autowired
    private TaiKhoanService taiKhoanService;

    @GetMapping({ "", "/" })
    public String viewLuongList(
            Model model,
            Principal principal,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "hoTen", required = false) String hoTen,
            @RequestParam(value = "thang", required = false) Integer thang,
            @RequestParam(value = "nam", required = false) Integer nam) {

        TaiKhoan tk = taiKhoanService.findByTenDangNhap(principal.getName());

        if (tk == null) {
            return "redirect:/login?error=session";
        }

        String role = tk.getVaiTro().toUpperCase();

        if ("ADMIN".equals(role)) {
            Pageable pageable = PageRequest.of(page, size);
            Page<Luong> luongPage;

            if ((hoTen != null && !hoTen.trim().isEmpty()) || thang != null || nam != null) {
                luongPage = luongService.searchLuong(hoTen, thang, nam, pageable);
                model.addAttribute("hoTen", hoTen);
                model.addAttribute("thang", thang);
                model.addAttribute("nam", nam);
            } else {
                luongPage = luongService.getAllLuong(pageable);
            }

            model.addAttribute("page", luongPage);
            model.addAttribute("listLuong", luongPage.getContent());
            return "luong/list";
        }

        if ("GIANGVIEN".equals(role)) {
            if (tk.getCanBo() == null) {
                model.addAttribute("listLuong", List.of());
                model.addAttribute("page", null);
                model.addAttribute("hasLuong", false);
                return "luong/list";
            }

            Integer maCB = tk.getCanBo().getMaCB();

            List<Luong> list = luongService.getListByCanBo(maCB);
            if (list == null) {
                list = List.of();
            }

            model.addAttribute("listLuong", list);
            model.addAttribute("page", null);
            model.addAttribute("hasLuong", !list.isEmpty());

            return "luong/list";
        }

        return "redirect:/login?error=forbidden";
    }

    @GetMapping("/search")
    public String searchLuong(
            @RequestParam(value = "hoTen", required = false) String hoTen,
            @RequestParam(value = "thang", required = false) Integer thang,
            @RequestParam(value = "nam", required = false) Integer nam,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Model model,
            Principal principal) {

        return viewLuongList(model, principal, page, size, hoTen, thang, nam);
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("luong", new Luong());
        model.addAttribute("listCanBo", canBoService.getAllCanBo());
        return "luong/add";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        Luong luong = luongService.getLuongById(id);
        if (luong == null) {
            model.addAttribute("error", "Không tìm thấy bảng lương!");
            return "redirect:/luong";
        }
        model.addAttribute("luong", luong);
        model.addAttribute("listCanBo", canBoService.getAllCanBo());
        return "luong/edit";
    }

    @PostMapping("/save")
    public String saveLuong(@ModelAttribute("luong") Luong luong, Model model) {

        try {
            Integer maCanBo = luong.getCanBo() != null ? luong.getCanBo().getMaCB() : null;

            if (maCanBo == null || maCanBo <= 0) {
                model.addAttribute("error", "Vui lòng chọn cán bộ!");
                model.addAttribute("listCanBo", canBoService.getAllCanBo());
                return luong.getMaLuong() == null ? "luong/add" : "luong/edit";
            }

            CanBo canBo = canBoService.getCanBoById(maCanBo);
            luong.setCanBo(canBo);

            Luong existing = luongService.findByCanBoAndThangNam(maCanBo, luong.getThang(), luong.getNam());
            if (existing != null && (luong.getMaLuong() == null || !existing.getMaLuong().equals(luong.getMaLuong()))) {
                model.addAttribute("error", "Đã tồn tại bảng lương cho cán bộ này trong tháng/năm này!");
                model.addAttribute("listCanBo", canBoService.getAllCanBo());
                model.addAttribute("luong", luong);
                return luong.getMaLuong() == null ? "luong/add" : "luong/edit";
            }

            luongService.saveLuong(luong);
            return "redirect:/luong?success";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Lỗi hệ thống: " + e.getMessage());
            return luong.getMaLuong() == null ? "luong/add" : "luong/edit";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteLuong(@PathVariable("id") int id) {
        try {
            luongService.deleteLuong(id);
        } catch (Exception ignored) {
        }
        return "redirect:/luong?success";
    }
}