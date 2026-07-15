package com.example.QuanLyCanBo.controller;

import com.example.QuanLyCanBo.model.TaiKhoan;
import com.example.QuanLyCanBo.repository.CanBoRepository;
import com.example.QuanLyCanBo.service.TaiKhoanService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/taikhoan")
public class TaiKhoanController {

    @Autowired
    private TaiKhoanService taiKhoanService;

    @Autowired
    private CanBoRepository canBoRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping
    public String listTaiKhoan(Model model,
            Principal principal,
            @RequestParam(defaultValue = "0") int page) {

        TaiKhoan tkLogin = taiKhoanService.findByTenDangNhap(principal.getName());

        if (tkLogin == null) {
            return "redirect:/login?error=session";
        }

        String vaiTro = tkLogin.getVaiTro() == null ? "" : tkLogin.getVaiTro().toUpperCase();

        if ("ADMIN".equals(vaiTro)) {
            Page<TaiKhoan> p = taiKhoanService.search("", PageRequest.of(page, 10));
            long totalTaiKhoan = taiKhoanService.countTaiKhoan();
            model.addAttribute("page", p);
            model.addAttribute("list", p.getContent());
            model.addAttribute("totalTaiKhoan", totalTaiKhoan);
        } else if ("GIANGVIEN".equals(vaiTro)) {

            List<TaiKhoan> list = List.of(tkLogin);

            Page<TaiKhoan> p = new PageImpl<>(list, PageRequest.of(0, 10), 1);

            model.addAttribute("list", list);
            model.addAttribute("page", p);
        } else {
            return "redirect:/login?error=forbidden";
        }

        return "taikhoan/list";
    }

    @GetMapping("/search")
    public String search(@RequestParam(defaultValue = "") String username,
            Principal principal,
            Model model,
            @RequestParam(defaultValue = "0") int page) {

        TaiKhoan tkLogin = taiKhoanService.findByTenDangNhap(principal.getName());
        if (tkLogin == null) return "redirect:/login?error=session";

        String vaiTro = tkLogin.getVaiTro() == null ? "" : tkLogin.getVaiTro().trim().toUpperCase();
        if (!"ADMIN".equals(vaiTro)) {
            if ("GIANGVIEN".equals(vaiTro)) {
                model.addAttribute("list", List.of(tkLogin));
                model.addAttribute("page", null);
                return "taikhoan/list";
            }
            return "redirect:/taikhoan?error=forbidden";
        }

        Page<TaiKhoan> p = taiKhoanService.search(username, PageRequest.of(page, 10));
        model.addAttribute("page", p);
        model.addAttribute("list", p.getContent());
        model.addAttribute("username", username);

        return "taikhoan/list";
    }

    @GetMapping("/add")
    public String addForm(Model model, Principal principal) {
        TaiKhoan tkLogin = taiKhoanService.findByTenDangNhap(principal.getName());
        if (tkLogin == null) return "redirect:/login?error=session";

        if (!"ADMIN".equalsIgnoreCase(tkLogin.getVaiTro())) {
            return "redirect:/taikhoan?error=forbidden";
        }

        model.addAttribute("taiKhoan", new TaiKhoan());
        model.addAttribute("listRoles", List.of("ADMIN", "GIANGVIEN"));
        model.addAttribute("canBoList", canBoRepo.findAll());

        return "taikhoan/add";
    }

    @PostMapping("/save")
    public String save(@RequestParam("canBoId") int canBoId,
            @ModelAttribute("taiKhoan") TaiKhoan tk) {

        var cb = canBoRepo.findById(canBoId).orElse(null);
        tk.setCanBo(cb);

        if (tk.getMatKhau() != null && !tk.getMatKhau().isEmpty()) {
            tk.setMatKhau(passwordEncoder.encode(tk.getMatKhau()));
        } else {
            tk.setMatKhau(""); 
        }

        taiKhoanService.save(tk);
        return "redirect:/taikhoan?success=added";
    }

    @GetMapping("/edit/{tenDangNhap}")
    public String edit(@PathVariable String tenDangNhap,
            Model model,
            Principal principal) {

        TaiKhoan tkLogin = taiKhoanService.findByTenDangNhap(principal.getName());
        if (tkLogin == null) return "redirect:/login?error=session";
        
        TaiKhoan tk = taiKhoanService.findByTenDangNhap(tenDangNhap);

        if (tk == null) {
            return "redirect:/taikhoan?error=notfound";
        }

        boolean isAdmin = "ADMIN".equalsIgnoreCase(tkLogin.getVaiTro());
        boolean isOwner = tkLogin.getTenDangNhap().equals(tenDangNhap);

        if (!isAdmin && !isOwner) {
            return "redirect:/taikhoan?error=forbidden";
        }

        model.addAttribute("taiKhoan", tk);
        model.addAttribute("canBoList", canBoRepo.findAll());
        model.addAttribute("listRoles", List.of("ADMIN", "GIANGVIEN"));
        model.addAttribute("isAdmin", isAdmin);

        return "taikhoan/edit";
    }

    @PostMapping("/update")
    public String update(
            @RequestParam(value = "canBoId", required = false) Integer canBoId,
            @ModelAttribute TaiKhoan tkForm,
            Principal principal) {

        TaiKhoan tkLogin = taiKhoanService.findByTenDangNhap(principal.getName());
        if (tkLogin == null) return "redirect:/login?error=session";
        
        TaiKhoan tk = taiKhoanService.findByTenDangNhap(tkForm.getTenDangNhap());

        if (tk == null) {
            return "redirect:/taikhoan?error=notfound";
        }

        boolean isAdmin = "ADMIN".equalsIgnoreCase(tkLogin.getVaiTro());
        boolean isOwner = tkLogin.getTenDangNhap().equals(tk.getTenDangNhap());

        if (!isAdmin && !isOwner) {
            return "redirect:/taikhoan?error=forbidden";
        }

        if (isAdmin) {
            tk.setVaiTro(tkForm.getVaiTro());

            if (canBoId != null) {
                var cb = canBoRepo.findById(canBoId).orElse(null);
                tk.setCanBo(cb);
            }
        }

        if (tkForm.getMatKhau() != null && !tkForm.getMatKhau().isEmpty()) {
            tk.setMatKhau(passwordEncoder.encode(tkForm.getMatKhau()));
        }

        taiKhoanService.save(tk);
        return "redirect:/taikhoan?success=updated";
    }

    @GetMapping("/delete/{tenDangNhap}")
    public String delete(@PathVariable String tenDangNhap,
            Principal principal) {

        TaiKhoan tkLogin = taiKhoanService.findByTenDangNhap(principal.getName());
        if (tkLogin == null) return "redirect:/login?error=session";

        if (!"ADMIN".equals(tkLogin.getVaiTro())) {
            return "redirect:/taikhoan?error=forbidden";
        }

        taiKhoanService.deleteByTenDangNhap(tenDangNhap);

        return "redirect:/taikhoan?deleted";
    }
}
