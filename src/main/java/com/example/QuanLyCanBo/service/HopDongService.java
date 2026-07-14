package com.example.QuanLyCanBo.service;

import com.example.QuanLyCanBo.model.HopDong;

import java.util.List;

public interface HopDongService {
    List<HopDong> getAll();

    HopDong getById(Integer id);

    List<HopDong> getByCanBo(Integer maCB);

    HopDong save(HopDong hd);

    void delete(Integer id);
}
