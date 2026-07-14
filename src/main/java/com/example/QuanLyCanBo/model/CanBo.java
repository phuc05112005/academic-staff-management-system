package com.example.QuanLyCanBo.model;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.*;

@Entity
@Table(name = "CanBo")
public class CanBo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maCB")
    private Integer maCB;

    @Column(name = "hoTen", nullable = false, length = 100)
    private String hoTen;

    @Column(name = "gioiTinh", length = 10)
    private String gioiTinh;

    @Temporal(TemporalType.DATE)
    @Column(name = "ngaySinh")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngaySinh;

    @Column(name = "diaChi", length = 255)
    private String diaChi;

    @Column(name = "soDienThoai", length = 15)
    private String soDienThoai;

    @Column(name = "email", length = 100)
    private String email;

    @Temporal(TemporalType.DATE)
    @Column(name = "ngayVaoTruong")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngayVaoTruong;

    @Column(name = "hinhAnh", length = 255)
    private String hinhAnh;

    @Column(name = "trangThai")
    private Boolean trangThai;

    // ===== Liên kết =====
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "maHocVi")
    private HocVi hocVi;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "maChucVu")
    private ChucVu chucVu;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "maBoMon")
    private BoMon boMon;

    @OneToOne(mappedBy = "canBo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private TaiKhoan taiKhoan;

    public CanBo() {
    }

    public void setMaCB(Integer maCB) {
        this.maCB = maCB;
    }

    public Integer getMaCB() {
        return maCB;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getNgayVaoTruong() {
        return ngayVaoTruong;
    }

    public void setNgayVaoTruong(Date ngayVaoTruong) {
        this.ngayVaoTruong = ngayVaoTruong;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public HocVi getHocVi() {
        return hocVi;
    }

    public void setHocVi(HocVi hocVi) {
        this.hocVi = hocVi;
    }

    public ChucVu getChucVu() {
        return chucVu;
    }

    public void setChucVu(ChucVu chucVu) {
        this.chucVu = chucVu;
    }

    public BoMon getBoMon() {
        return boMon;
    }

    public void setBoMon(BoMon boMon) {
        this.boMon = boMon;
    }

    public TaiKhoan getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public Boolean getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Boolean trangThai) {
        this.trangThai = trangThai;
    }

    @Transient
    public Khoa getKhoa() {
        return boMon != null ? boMon.getKhoa() : null;
    }
}
