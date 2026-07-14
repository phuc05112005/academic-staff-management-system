package com.example.QuanLyCanBo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.QuanLyCanBo.model.CanBo;

@Repository
public interface CanBoRepository extends JpaRepository<CanBo, Integer> {

        @Query("SELECT c FROM CanBo c WHERE " +
                        "(:maCBInt IS NOT NULL AND c.maCB = :maCBInt) " +
                        "OR LOWER(c.hoTen) = LOWER(:keyword) " +
                        "OR LOWER(c.soDienThoai) = LOWER(:keyword) " +
                        "OR LOWER(c.email) = LOWER(:keyword) " +
                        "OR LOWER(c.gioiTinh) = LOWER(:keyword) " +
                        "OR LOWER(c.diaChi) = LOWER(:keyword) " +
                        "OR LOWER(c.hocVi.tenHocVi) = LOWER(:keyword) " +
                        "OR LOWER(c.chucVu.tenChucVu) = LOWER(:keyword) " +
                        "OR LOWER(c.boMon.tenBoMon) = LOWER(:keyword) " +
                        "OR str(c.ngaySinh) = :keyword " +
                        "OR (:trangThaiValue IS NOT NULL AND c.trangThai = :trangThaiValue)")
        Page<CanBo> searchAbsoluteAllColumns(
                        @Param("maCBInt") Integer maCBInt,
                        @Param("keyword") String keyword,
                        @Param("trangThaiValue") Boolean trangThaiValue,
                        Pageable pageable);

        @Query("SELECT c FROM CanBo c WHERE " +
                        "(:trangThaiValue IS NULL OR c.trangThai = :trangThaiValue) " +
                        "AND (" +
                        "str(c.maCB) LIKE CONCAT('%', :keyword, '%') " +
                        "OR LOWER(c.hoTen) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                        "OR LOWER(c.gioiTinh) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                        "OR str(c.ngaySinh) LIKE CONCAT('%', :keyword, '%') " +
                        "OR LOWER(c.email) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                        "OR LOWER(c.soDienThoai) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                        "OR LOWER(c.diaChi) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                        "OR LOWER(c.hocVi.tenHocVi) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                        "OR LOWER(c.chucVu.tenChucVu) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                        "OR LOWER(c.boMon.tenBoMon) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                        ")")
        Page<CanBo> searchRelativeAllColumns(
                        @Param("keyword") String keyword,
                        @Param("trangThaiValue") Boolean trangThaiValue,
                        Pageable pageable);

        @Query("""
                        SELECT c FROM CanBo c
                        LEFT JOIN FETCH c.hocVi
                        LEFT JOIN FETCH c.chucVu
                        LEFT JOIN FETCH c.boMon
                        WHERE c.id = :id
                        """)
        CanBo findByIdWithRelations(@Param("id") Integer id);

}
