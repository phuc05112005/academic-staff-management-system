package com.example.QuanLyCanBo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "TaiKhoan")
public class TaiKhoan {

    @Id
    @Column(name = "tenDangNhap")
    private String tenDangNhap;

    @Column(name = "matKhau")
    private String matKhau;

    @Column(name = "vaiTro")
    private String vaiTro;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "maCB", referencedColumnName = "maCB")
    private CanBo canBo;

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

    public CanBo getCanBo() {
        return canBo;
    }

    public void setCanBo(CanBo canBo) {
        this.canBo = canBo;
    }
}
