package com.example.QuanLyCanBo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.QuanLyCanBo.model.BoMon;

@Repository
public interface BoMonRepository extends JpaRepository<BoMon, Integer> {
}
