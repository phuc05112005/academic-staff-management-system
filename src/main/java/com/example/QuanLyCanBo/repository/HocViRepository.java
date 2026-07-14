package com.example.QuanLyCanBo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.QuanLyCanBo.model.HocVi;

@Repository
public interface HocViRepository extends JpaRepository<HocVi, Integer> {
}
