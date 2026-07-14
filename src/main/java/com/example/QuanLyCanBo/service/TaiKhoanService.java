// src/main/java/com/example/QuanLyCanBo/service/TaiKhoanService.java
package com.example.QuanLyCanBo.service;

import com.example.QuanLyCanBo.model.TaiKhoan;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaiKhoanService {
    Page<TaiKhoan> search(String keyword, Pageable pageable);

    long countTaiKhoan();

    TaiKhoan save(TaiKhoan tk);

    TaiKhoan findByTenDangNhap(String tenDangNhap);

    void deleteByTenDangNhap(String tenDangNhap);

    List<TaiKhoan> findAll();

}