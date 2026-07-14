package com.example.QuanLyCanBo.service;

import com.example.QuanLyCanBo.model.TaiKhoan;
import com.example.QuanLyCanBo.repository.TaiKhoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Override
    public UserDetails loadUserByUsername(String tenDangNhap) throws UsernameNotFoundException {
        TaiKhoan user = taiKhoanRepository.findByTenDangNhap(tenDangNhap);
        if (user == null) {
            throw new UsernameNotFoundException("Không tìm thấy tài khoản: " + tenDangNhap);
        }

        String roleName = "ROLE_" + user.getVaiTro().toUpperCase();

        return new org.springframework.security.core.userdetails.User(
                user.getTenDangNhap(),
                user.getMatKhau(),
                Collections.singletonList(new SimpleGrantedAuthority(roleName)));
    }
}
