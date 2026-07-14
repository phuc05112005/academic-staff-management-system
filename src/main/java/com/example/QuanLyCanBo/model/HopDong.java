package com.example.QuanLyCanBo.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "HopDong")
public class HopDong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maHD;

    @ManyToOne
    @JoinColumn(name = "maCB")
    private CanBo canBo;

    @Column(length = 100)
    private String loaiHopDong;

    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;

    private BigDecimal mucLuongCoBan;
    private BigDecimal phuCap;

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public CanBo getCanBo() {
        return canBo;
    }

    public void setCanBo(CanBo canBo) {
        this.canBo = canBo;
    }

    public String getLoaiHopDong() {
        return loaiHopDong;
    }

    public void setLoaiHopDong(String loaiHopDong) {
        this.loaiHopDong = loaiHopDong;
    }

    public LocalDate getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(LocalDate ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public LocalDate getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(LocalDate ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public BigDecimal getMucLuongCoBan() {
        return mucLuongCoBan;
    }

    public void setMucLuongCoBan(BigDecimal mucLuongCoBan) {
        this.mucLuongCoBan = mucLuongCoBan;
    }

    public BigDecimal getPhuCap() {
        return phuCap;
    }

    public void setPhuCap(BigDecimal phuCap) {
        this.phuCap = phuCap;
    }
}
