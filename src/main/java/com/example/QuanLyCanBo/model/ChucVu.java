package com.example.QuanLyCanBo.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "ChucVu")
public class ChucVu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maChucVu;

    @Column(length = 50)
    private String tenChucVu;

    @OneToMany(mappedBy = "chucVu")
    private List<CanBo> danhSachCanBo;

    public void setMaChucVu(Integer maChucVu) {
        this.maChucVu = maChucVu;
    }

    public Integer getMaChucVu() {
        return maChucVu;
    }

    public String getTenChucVu() {
        return tenChucVu;
    }

    public void setTenChucVu(String tenChucVu) {
        this.tenChucVu = tenChucVu;
    }
}