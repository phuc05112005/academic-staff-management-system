package com.example.QuanLyCanBo.service;

import com.example.QuanLyCanBo.model.HopDong;
import com.example.QuanLyCanBo.repository.HopDongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HopDongServiceImpl implements HopDongService {

    @Autowired
    private HopDongRepository hopDongRepository;

    @Override
    public List<HopDong> getAll() {
        return hopDongRepository.findAll();
    }

    @Override
    public HopDong getById(Integer id) {
        return hopDongRepository.findById(id).orElse(null);
    }

    @Override
    public List<HopDong> getByCanBo(Integer maCB) {
        return hopDongRepository.findByCanBo_MaCB(maCB);
    }

    @Override
    public HopDong save(HopDong hd) {
        return hopDongRepository.save(hd);
    }

    @Override
    public void delete(Integer id) {
        hopDongRepository.deleteById(id);
    }
}
