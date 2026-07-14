package com.example.QuanLyCanBo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.QuanLyCanBo.model.HocVi;
import com.example.QuanLyCanBo.repository.HocViRepository;

@Service
public class HocViService {

    @Autowired
    private HocViRepository hocViRepository;

    public List<HocVi> getAllHocVi() {
        return hocViRepository.findAll();
    }

    public HocVi getHocViById(Integer hocViId) {
        return hocViRepository.findById(hocViId).orElse(null);
    }

    public HocVi save(HocVi hocVi) {
        return hocViRepository.save(hocVi);
    }

    public void delete(Integer id) {
        hocViRepository.deleteById(id);
    }
}
