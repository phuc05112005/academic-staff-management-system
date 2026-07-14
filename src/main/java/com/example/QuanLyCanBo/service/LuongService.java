package com.example.QuanLyCanBo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.QuanLyCanBo.model.Luong;

public interface LuongService {
    List<Luong> getAllLuong();

    Luong getLuongById(Integer id);

    Luong saveLuong(Luong luong);

    void deleteLuong(Integer id);

    Luong getLuongByCanBo(Integer maCB);

    List<Luong> getListByCanBo(Integer maCB);

    Page<Luong> getAllLuong(Pageable pageable);

    Page<Luong> searchLuong(String hoTen, Integer thang, Integer nam, Pageable pageable);

    Luong findByCanBoAndThangNam(Integer maCB, int thang, int nam);
}
