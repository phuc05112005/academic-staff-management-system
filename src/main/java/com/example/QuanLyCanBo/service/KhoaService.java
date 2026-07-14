package com.example.QuanLyCanBo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.QuanLyCanBo.model.Khoa;
import com.example.QuanLyCanBo.repository.KhoaRepository;

@Service
public class KhoaService {

    @Autowired
    private KhoaRepository repo;

    public List<Khoa> findAll() {
        return repo.findAll();
    }

    public Khoa findById(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public Khoa save(Khoa khoa) {
        return repo.save(khoa);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }
}
