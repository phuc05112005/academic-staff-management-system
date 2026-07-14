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
@Table(name = "HocVi")
public class HocVi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maHocVi;

    @Column(length = 50)
    private String tenHocVi;

    @OneToMany(mappedBy = "hocVi")
    private List<CanBo> danhSachCanBo;

    public int getMaHocVi() {
        return maHocVi;
    }

    public void setMaHocVi(int maHocVi) {
        this.maHocVi = maHocVi;
    }

    public String getTenHocVi() {
        return tenHocVi;
    }

    public void setTenHocVi(String tenHocVi) {
        this.tenHocVi = tenHocVi;
    }
}