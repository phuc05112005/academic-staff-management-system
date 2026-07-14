// src/main/java/com/example/QuanLyCanBo/repository/TaiKhoanRepository.java
package com.example.QuanLyCanBo.repository;

import com.example.QuanLyCanBo.model.TaiKhoan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaiKhoanRepository extends JpaRepository<TaiKhoan, String> {

    TaiKhoan findByTenDangNhap(String tenDangNhap);

    @Query("""
             SELECT tk FROM TaiKhoan tk
             LEFT JOIN tk.canBo cb
             WHERE
                 (:keyword IS NULL OR :keyword = ''
                  OR LOWER(tk.tenDangNhap) LIKE LOWER(CONCAT('%', :keyword, '%'))
                  OR LOWER(cb.hoTen) LIKE LOWER(CONCAT('%', :keyword, '%')))
            """)
    Page<TaiKhoan> search(@Param("keyword") String keyword, Pageable pageable);

}