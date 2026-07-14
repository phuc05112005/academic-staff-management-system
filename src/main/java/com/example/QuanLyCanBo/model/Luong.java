package com.example.QuanLyCanBo.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;

@Entity
@Table(name = "Luong")
public class Luong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maLuong")
    private Integer maLuong;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "maCB", nullable = false)
    private CanBo canBo;

    @Column(name = "thang", nullable = false)
    private Integer thang;

    @Column(name = "nam", nullable = false)
    private Integer nam;

    @Column(name = "luongCoBan", nullable = false)
    private Double luongCoBan;

    @Column(name = "phuCap", nullable = false)
    private Double phuCap;

    @Column(name = "tongLuong", insertable = false, updatable = false)
    private Double tongLuong;

    @Temporal(TemporalType.DATE)
    @Column(name = "ngayTinhLuong")
    private LocalDate ngayTinhLuong;

    public Luong() {
    }

    public Luong(CanBo canBo, int thang, int nam, Double luongCoBan, Double phuCap) {
        this.canBo = canBo;
        this.thang = thang;
        this.nam = nam;
        this.luongCoBan = luongCoBan;
        this.phuCap = phuCap;
    }

    public void setMaLuong(Integer maLuong) {
        this.maLuong = maLuong;
    }

    public Integer getMaLuong() {
        return maLuong;
    }

    public CanBo getCanBo() {
        return canBo;
    }

    public void setCanBo(CanBo canBo) {
        this.canBo = canBo;
    }

    public void setNam(Integer nam) {
        this.nam = nam;
    }

    public Integer getNam() {
        return nam;
    }

    public void setThang(Integer thang) {
        this.thang = thang;
    }

    public Integer getThang() {
        return thang;
    }

    public Double getLuongCoBan() {
        return luongCoBan;
    }

    public void setLuongCoBan(Double luongCoBan) {
        this.luongCoBan = luongCoBan;
    }

    public Double getPhuCap() {
        return phuCap;
    }

    public void setPhuCap(Double phuCap) {
        this.phuCap = phuCap;
    }

    public Double getTongLuong() {
        return tongLuong;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate getNgayTinhLuong() {
        return ngayTinhLuong;
    }

    public void setNgayTinhLuong(LocalDate ngayTinhLuong) {
        this.ngayTinhLuong = ngayTinhLuong;
    }
}
