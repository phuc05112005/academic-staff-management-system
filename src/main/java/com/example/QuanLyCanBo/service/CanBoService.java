package com.example.QuanLyCanBo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.QuanLyCanBo.model.CanBo;

public interface CanBoService {
    List<CanBo> getAllCanBo();

    long countCanBo();

    CanBo getCanBoById(Integer id);

    CanBo saveCanBo(CanBo canBo);

    void deleteCanBo(Integer id);

    CanBo getCanBoByIdWithRelations(Integer id);

    Page<CanBo> getAllCanBo(Pageable pageable);

    Page<CanBo> searchAbsolute(String keyword, Pageable pageable);

    Page<CanBo> searchRelative(String keyword, Pageable pageable);

}
