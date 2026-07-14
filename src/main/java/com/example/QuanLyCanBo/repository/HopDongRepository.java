package com.example.QuanLyCanBo.repository;

import com.example.QuanLyCanBo.model.HopDong;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HopDongRepository extends JpaRepository<HopDong, Integer> {

    List<HopDong> findByCanBo_MaCB(Integer maCB);
}
