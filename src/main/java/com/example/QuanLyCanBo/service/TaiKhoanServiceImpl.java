// src/main/java/com/example/QuanLyCanBo/service/impl/TaiKhoanServiceImpl.java
package com.example.QuanLyCanBo.service;

import com.example.QuanLyCanBo.model.TaiKhoan;
import com.example.QuanLyCanBo.repository.TaiKhoanRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaiKhoanServiceImpl implements TaiKhoanService {

    @Autowired
    private TaiKhoanRepository repo;

    @Override
    public long countTaiKhoan() {
        return repo.count();
    }

    @Override
    public Page<TaiKhoan> search(String keyword, Pageable pageable) {
        return repo.search(keyword, pageable);
    }

    @Override
    public TaiKhoan save(TaiKhoan tk) {
        return repo.save(tk);
    }

    @Override
    public TaiKhoan findByTenDangNhap(String tenDangNhap) {
        return repo.findByTenDangNhap(tenDangNhap);
    }

    @Override
    @Transactional
    public void deleteByTenDangNhap(String tenDangNhap) {
        TaiKhoan tk = repo.findByTenDangNhap(tenDangNhap);
        if (tk != null) {
            repo.delete(tk);
        }
    }

    @Override
    public List<TaiKhoan> findAll() {
        return repo.findAll();
    }

}