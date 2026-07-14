package com.example.QuanLyCanBo.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.QuanLyCanBo.model.CanBo;
import com.example.QuanLyCanBo.repository.CanBoRepository;

@Service
public class CanBoServiceImpl implements CanBoService {

    @Autowired
    private CanBoRepository canBoRepository;

    @Override
    public long countCanBo() {
        return canBoRepository.count();
    }

    @Override
    public List<CanBo> getAllCanBo() {
        return canBoRepository.findAll();
    }

    @Override
    public Page<CanBo> getAllCanBo(Pageable pageable) {
        return canBoRepository.findAll(pageable);
    }

    @Override
    public CanBo getCanBoById(Integer id) {
        return canBoRepository.findById(id).orElse(null);
    }

    @Override
    public CanBo getCanBoByIdWithRelations(Integer id) {
        return canBoRepository.findByIdWithRelations(id);
    }

    @Override
    public CanBo saveCanBo(CanBo canBo) {
        return canBoRepository.save(canBo);
    }

    @Override
    public void deleteCanBo(Integer id) {
        canBoRepository.deleteById(id);
    }

    private Boolean mapTrangThaiString(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        String normalized = value.trim()
                .toLowerCase()
                .replace("đ", "d")
                .replaceAll("\\s+", " ");

        if (normalized.contains("dang cong tac")
                || normalized.contains("dang lam")
                || normalized.contains("congtac")
                || normalized.equals("true")
                || normalized.equals("1")
                || normalized.equals("hoat dong")) {
            return true;
        }

        if (normalized.contains("nghi viec")
                || normalized.contains("nghi")
                || normalized.contains("da nghi")
                || normalized.equals("false")
                || normalized.equals("0")) {
            return false;
        }

        return null;
    }

    @Override
    public Page<CanBo> searchAbsolute(String absolute, Pageable pageable) {
        if (absolute == null || absolute.trim().isEmpty()) {
            return canBoRepository.findAll(pageable);
        }

        String searchValue = absolute.trim();
        Boolean trangThaiValue = mapTrangThaiString(searchValue);

        Integer maCBInt = null;
        if (searchValue.toUpperCase().startsWith("CB")) {
            try {
                maCBInt = Integer.parseInt(searchValue.substring(2));
            } catch (Exception e) {
            }
        }

        return canBoRepository.searchAbsoluteAllColumns(
                maCBInt,
                searchValue,
                trangThaiValue,
                pageable);
    }

    @Override
    public Page<CanBo> searchRelative(String relative, Pageable pageable) {
        if (relative == null || relative.trim().isEmpty()) {
            return canBoRepository.findAll(pageable);
        }

        String searchValue = relative.trim();
        Boolean trangThaiValue = mapTrangThaiString(searchValue);

        return canBoRepository.searchRelativeAllColumns(
                searchValue,
                trangThaiValue,
                pageable);
    }

}
