package com.example.QuanLyCanBo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.QuanLyCanBo.model.ChucVu;
import com.example.QuanLyCanBo.repository.ChucVuRepository;

@Service
public class ChucVuService {

    @Autowired
    private ChucVuRepository chucVuRepository;

    public List<ChucVu> getAllChucVu() {
        return chucVuRepository.findAll();
    }

    public ChucVu getChucVuById(Integer chucVuId) {
        return chucVuRepository.findById(chucVuId).orElse(null);
    }

    public ChucVu save(ChucVu chucVu) {
        return chucVuRepository.save(chucVu);
    }

    public void delete(Integer id) {
        chucVuRepository.deleteById(id);
    }
}
