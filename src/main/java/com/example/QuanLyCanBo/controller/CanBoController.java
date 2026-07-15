package com.example.QuanLyCanBo.controller;

import com.example.QuanLyCanBo.model.CanBo;
import com.example.QuanLyCanBo.model.TaiKhoan;
import com.example.QuanLyCanBo.service.BoMonService;
import com.example.QuanLyCanBo.service.CanBoService;
import com.example.QuanLyCanBo.service.ChucVuService;
import com.example.QuanLyCanBo.service.HocViService;
import com.example.QuanLyCanBo.service.TaiKhoanService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Date;

@Controller
@RequestMapping("/canbo")
public class CanBoController {

    @Autowired
    private CanBoService canBoService;

    @Autowired
    private HocViService hocViService;

    @Autowired
    private ChucVuService chucVuService;

    @Autowired
    private BoMonService boMonService;

    @Autowired
    private TaiKhoanService taiKhoanService;

    @GetMapping
    public String viewCanBoList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model,
            Principal principal) {

        TaiKhoan tkLogin = taiKhoanService.findByTenDangNhap(principal.getName());
        if (tkLogin == null)
            return "redirect:/login?error=session";

        String role = tkLogin.getVaiTro().toUpperCase();

        if ("ADMIN".equals(role)) {
            PageRequest pageable = PageRequest.of(page, size);
            Page<CanBo> pageCanBo = canBoService.getAllCanBo(pageable);
            long totalCanBo = canBoService.countCanBo();

            model.addAttribute("page", pageCanBo);
            model.addAttribute("listCanBo", pageCanBo.getContent());
            model.addAttribute("totalCanBo", totalCanBo);
            return "canbo/list";
        }

        else if ("GIANGVIEN".equals(role)) {
            CanBo cb = tkLogin.getCanBo();

            if (cb == null) {
                model.addAttribute("message", "Bạn chưa được gán vào thông tin cán bộ!");
                return "canbo/list";
            }

            model.addAttribute("listCanBo", java.util.List.of(cb));
            model.addAttribute("page", null);

            return "canbo/list";
        }

        return "redirect:/login?error=forbidden";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        CanBo canBo = new CanBo();
        canBo.setNgayVaoTruong(new Date());
        model.addAttribute("canbo", canBo);

        model.addAttribute("listHocVi", hocViService.getAllHocVi());
        model.addAttribute("listChucVu", chucVuService.getAllChucVu());
        model.addAttribute("listBoMon", boMonService.getAllBoMon());

        return "canbo/add";
    }

    @PostMapping("/save")
    public String saveCanBo(@ModelAttribute("canbo") CanBo canBo,
            @RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {
        try {
            String filePath = saveFile(file);
            if (filePath != null)
                canBo.setHinhAnh(filePath);

            if (canBo.getHocVi() != null && canBo.getHocVi().getMaHocVi() > 0) {
                canBo.setHocVi(hocViService.getHocViById(canBo.getHocVi().getMaHocVi()));
            } else {
                canBo.setHocVi(null);
            }
            if (canBo.getChucVu() != null && canBo.getChucVu().getMaChucVu() > 0) {
                canBo.setChucVu(chucVuService.getChucVuById(canBo.getChucVu().getMaChucVu()));
            } else {
                canBo.setChucVu(null);
            }
            if (canBo.getBoMon() != null && canBo.getBoMon().getMaBoMon() > 0) {
                canBo.setBoMon(boMonService.getBoMonById(canBo.getBoMon().getMaBoMon()));
            } else {
                canBo.setBoMon(null);
            }

            canBoService.saveCanBo(canBo);
            redirectAttributes.addFlashAttribute("success", "Thêm cán bộ thành công!");
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Lỗi khi lưu ảnh!");
        }
        return "redirect:/canbo";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {

        CanBo canBo = canBoService.getCanBoByIdWithRelations(id);
        model.addAttribute("canbo", canBo);

        model.addAttribute("listHocVi", hocViService.getAllHocVi());
        model.addAttribute("listChucVu", chucVuService.getAllChucVu());
        model.addAttribute("listBoMon", boMonService.getAllBoMon());

        return "canbo/edit";
    }

    @PostMapping("/update")
    public String updateCanBo(@ModelAttribute("canbo") CanBo canBo,
            @RequestParam("file") MultipartFile file) {
        try {

            CanBo existing = canBoService.getCanBoByIdWithRelations(canBo.getMaCB());

            String filePath = saveFile(file);
            if (filePath != null)
                existing.setHinhAnh(filePath);

            existing.setHoTen(canBo.getHoTen());
            existing.setGioiTinh(canBo.getGioiTinh());
            existing.setNgaySinh(canBo.getNgaySinh());
            existing.setDiaChi(canBo.getDiaChi());
            existing.setSoDienThoai(canBo.getSoDienThoai());
            existing.setEmail(canBo.getEmail());
            existing.setNgayVaoTruong(canBo.getNgayVaoTruong());
            existing.setTrangThai(canBo.getTrangThai());

            if (canBo.getHocVi() != null && canBo.getHocVi().getMaHocVi() > 0) {
                existing.setHocVi(hocViService.getHocViById(canBo.getHocVi().getMaHocVi()));
            } else {
                existing.setHocVi(null);
            }
            if (canBo.getChucVu() != null && canBo.getChucVu().getMaChucVu() > 0) {
                existing.setChucVu(chucVuService.getChucVuById(canBo.getChucVu().getMaChucVu()));
            } else {
                existing.setChucVu(null);
            }
            if (canBo.getBoMon() != null && canBo.getBoMon().getMaBoMon() > 0) {
                existing.setBoMon(boMonService.getBoMonById(canBo.getBoMon().getMaBoMon()));
            } else {
                existing.setBoMon(null);
            }

            canBoService.saveCanBo(existing);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/canbo";
    }

    @GetMapping("/delete/{id}")
    public String deleteCanBo(@PathVariable("id") Integer id) {
        canBoService.deleteCanBo(id);
        return "redirect:/canbo";
    }

    private String saveFile(MultipartFile file) throws IOException {
        if (file.isEmpty())
            return null;

        String uploadDir = "uploads/images/";
        File folder = new File(uploadDir);
        if (!folder.exists())
            folder.mkdirs();

        String originalName = file.getOriginalFilename();
        String extension = "";
        if (originalName != null && originalName.contains(".")) {
            extension = originalName.substring(originalName.lastIndexOf("."));
        }
        String fileName = java.util.UUID.randomUUID().toString() + extension;
        Path path = Paths.get(uploadDir + fileName);
        Files.write(path, file.getBytes());

        return fileName;
    }

    @GetMapping("/search")
    public String searchCanBo(
            @RequestParam(required = false) String absolute,
            @RequestParam(required = false) String relative,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        if ((absolute == null || absolute.trim().isEmpty()) &&
                (relative == null || relative.trim().isEmpty())) {
            return "redirect:/canbo";
        }

        PageRequest pageable = PageRequest.of(page, size);
        Page<CanBo> pageCanBo;

        if (absolute != null && !absolute.trim().isEmpty()) {
            pageCanBo = canBoService.searchAbsolute(absolute.trim(), pageable);
        } else {
            pageCanBo = canBoService.searchRelative(relative.trim(), pageable);
        }

        model.addAttribute("listCanBo", pageCanBo.getContent());
        model.addAttribute("page", pageCanBo);
        model.addAttribute("absolute", absolute);
        model.addAttribute("relative", relative);

        if (pageCanBo.isEmpty()) {
            model.addAttribute("message", "Không tìm thấy kết quả nào phù hợp!");
        }

        return "canbo/list";
    }

}
