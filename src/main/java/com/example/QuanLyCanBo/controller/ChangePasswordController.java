package com.example.QuanLyCanBo.controller;

import com.example.QuanLyCanBo.model.TaiKhoan;
import com.example.QuanLyCanBo.repository.TaiKhoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChangePasswordController {

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/canbo/change-password")
    public String showChangePasswordForm() {
        return "canbo/change-password";
    }

    @PostMapping("/canbo/change-password")
    public String changePassword(Authentication authentication,
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model) {

        String username = authentication.getName();
        TaiKhoan tk = taiKhoanRepository.findByTenDangNhap(username);

        if (!passwordEncoder.matches(currentPassword, tk.getMatKhau())) {
            model.addAttribute("error", "Mật khẩu hiện tại không đúng!");
            return "canbo/change-password";
        }

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Mật khẩu mới và xác nhận không khớp!");
            return "canbo/change-password";
        }

        tk.setMatKhau(passwordEncoder.encode(newPassword));
        taiKhoanRepository.save(tk);

        model.addAttribute("success", "Đổi mật khẩu thành công!");
        return "canbo/change-password";
    }
}
