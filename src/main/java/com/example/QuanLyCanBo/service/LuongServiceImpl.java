package com.example.QuanLyCanBo.service;

import com.example.QuanLyCanBo.model.Luong;
import com.example.QuanLyCanBo.repository.LuongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LuongServiceImpl implements LuongService {

    @Autowired
    private LuongRepository luongRepository;

    @Override
    public List<Luong> getAllLuong() {
        return luongRepository.findAll();
    }

    @Override
    public Luong getLuongById(Integer id) {
        return luongRepository.findById(id).orElse(null);
    }

    @Override
    public Luong saveLuong(Luong luong) {
        return luongRepository.save(luong);
    }

    @Override
    public void deleteLuong(Integer id) {
        luongRepository.deleteById(id);
    }

    @Override
    public Luong getLuongByCanBo(Integer maCB) {
        return luongRepository.findByCanBo_MaCB(maCB);
    }

    @Override
    public Page<Luong> getAllLuong(Pageable pageable) {
        return luongRepository.findAll(pageable);
    }

    @Override
    public Page<Luong> searchLuong(String hoTen, Integer thang, Integer nam, Pageable pageable) {
        return luongRepository.searchLuong(hoTen, thang, nam, pageable);
    }

    @Override
    public Luong findByCanBoAndThangNam(Integer maCB, int thang, int nam) {
        return luongRepository.findByCanBo_MaCBAndThangAndNam(maCB, thang, nam);
    }

    @Override
    public List<Luong> getListByCanBo(Integer maCB) {
        return luongRepository.findAllByCanBo_MaCB(maCB);
    }

}
