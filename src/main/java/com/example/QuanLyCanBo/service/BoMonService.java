package com.example.QuanLyCanBo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.QuanLyCanBo.model.BoMon;
import com.example.QuanLyCanBo.repository.BoMonRepository;

@Service
public class BoMonService {

    @Autowired
    private BoMonRepository boMonRepository;

    public List<BoMon> getAllBoMon() {
        return boMonRepository.findAll();
    }

    public BoMon getBoMonById(Integer boMonId) {
        return boMonRepository.findById(boMonId).orElse(null);
    }

    public BoMon save(BoMon boMon) {
        return boMonRepository.save(boMon);
    }

    public void delete(Integer id) {
        boMonRepository.deleteById(id);
    }

    public long countBoMon() {
        return boMonRepository.count();
    }

}
