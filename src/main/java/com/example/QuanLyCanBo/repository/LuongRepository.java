package com.example.QuanLyCanBo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.QuanLyCanBo.model.Luong;

@Repository
public interface LuongRepository extends JpaRepository<Luong, Integer> {
        Luong findByCanBo_MaCB(Integer maCB);

        List<Luong> findAllByCanBo_MaCB(Integer maCB);

        @Query(value = "SELECT l FROM Luong l JOIN l.canBo cb " +
                        "WHERE (:hoTen IS NULL OR :hoTen = '' OR LOWER(cb.hoTen) LIKE LOWER(CONCAT('%', :hoTen, '%'))) "
                        +
                        "AND (:thang IS NULL OR l.thang = :thang) " +
                        "AND (:nam IS NULL OR l.nam = :nam)", countQuery = "SELECT COUNT(l) FROM Luong l JOIN l.canBo cb "
                                        +
                                        "WHERE (:hoTen IS NULL OR :hoTen = '' OR LOWER(cb.hoTen) LIKE LOWER(CONCAT('%', :hoTen, '%'))) "
                                        +
                                        "AND (:thang IS NULL OR l.thang = :thang) " +
                                        "AND (:nam IS NULL OR l.nam = :nam)")
        Page<Luong> searchLuong(@Param("hoTen") String hoTen,
                        @Param("thang") Integer thang,
                        @Param("nam") Integer nam,
                        Pageable pageable);

        Luong findByCanBo_MaCBAndThangAndNam(Integer maCB, int thang, int nam);
}