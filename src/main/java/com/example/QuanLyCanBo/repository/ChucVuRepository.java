package com.example.QuanLyCanBo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.QuanLyCanBo.model.ChucVu;

@Repository
public interface ChucVuRepository extends JpaRepository<ChucVu, Integer> {
}