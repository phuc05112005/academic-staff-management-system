package com.example.QuanLyCanBo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.QuanLyCanBo.model.Khoa;

@Repository
public interface KhoaRepository extends JpaRepository<Khoa, Integer> {
}
