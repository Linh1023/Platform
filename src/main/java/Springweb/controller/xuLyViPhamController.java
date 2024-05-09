package Springweb.controller;

import Springweb.entity.ThanhVien;
import Springweb.entity.XuLy;
import Springweb.repository.ThanhVienRepository;
import Springweb.repository.XuLyViPhamRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author dothetung
 */
@Controller
public class xuLyViPhamController {

  //
  @Autowired
  private XuLyViPhamRepository xulyRepository;

  private ThanhVienRepository thanhvienRepository;

  @Autowired
  public void YourController(ThanhVienRepository thanhvienRepository) {
    this.thanhvienRepository = thanhvienRepository;
  }

  @GetMapping(value = "/admin/xuly/all")
  public String getAll(Model m) {
    Iterable<XuLy> list = xulyRepository.findAll();
    m.addAttribute("list", list);
    m.addAttribute("templateName", "admin/xuly/xuly_all");
    return "admin/sample";
  }

  @GetMapping("/admin/xuly/add")
  public String register(Model m) {
    XuLy xl = new XuLy();
    m.addAttribute("xuly", xl);
    m.addAttribute("templateName", "admin/xuly/xuly_register");
    return "admin/sample";
  }

   @GetMapping("/admin/xuly/add2/{id}")
  public String LIST_PICK(Model m,@PathVariable("id") int id) {
      
     Optional<ThanhVien> cus = thanhvienRepository.findById(id);
     ThanhVien tv = cus.get();
      XuLy xl = new XuLy();
      xl.setMaTV(tv.getMaTV());
    m.addAttribute("xuly", xl);
    m.addAttribute("templateName", "admin/xuly/xuly_register");
    return "admin/sample";
  }
  
  @GetMapping("/admin/xuly/listthanhvien")
  public String list_thanh_vien(Model m) {
    // ThanhVien tv = new ThanhVien();
    Iterable<ThanhVien> tv = thanhvienRepository.findAll();
    m.addAttribute("list", tv);
    return "admin/xuly/luaThanhVien";
  }
  

  @GetMapping(value = { "/admin/xuly/edit/{id}" })
  public String edit(@PathVariable("id") int id, Model m) {
    Optional<XuLy> cus = xulyRepository.findById(id);
    cus.ifPresent(xuly -> m.addAttribute("xuly", cus));
    m.addAttribute("templateName", "admin/xuly/xuly_edit");
    return "admin/sample";
  }

  @PostMapping("/admin/xuly/update")
  public String update(Model m, @ModelAttribute("xuly") XuLy xu_ly) {
    Optional<XuLy> xl = xulyRepository.findById(xu_ly.getMaXL());
    XuLy c;
    c = xl.get();

    c.setMaXL(xu_ly.getMaXL());
    c.setMaTV(xu_ly.getMaTV());
    c.setHinhThucXL(xu_ly.getHinhThucXL());
    c.setSoTien(xu_ly.getSoTien());
    c.setNgayXL(xu_ly.getNgayXL());
    c.setTrangThaiXL(xu_ly.getTrangThaiXL());

    xulyRepository.save(c);
    return "redirect:/admin/xuly/all";
  }

  @PostMapping("/admin/xuly/save")
  public String save(Model m, @ModelAttribute("xuly") XuLy xu_ly) {
    Optional<XuLy> xl = xulyRepository.findById(xu_ly.getMaXL());
    if (!xl.isPresent()) {
      xulyRepository.save(xu_ly);
    } else {
      String errorMessage = "Mã Xử Lý đã tồn tại trong hệ thống.";
    }

    return "redirect:/admin/xuly/all";
  }

  @GetMapping(value = { "/admin/xuly/delete/{id}" })
  public String delete(Model m, @PathVariable("id") int id) {
    Optional<XuLy> XuLyOptional = xulyRepository.findById(id);

    if (XuLyOptional.isPresent()) {
      XuLy xuly = XuLyOptional.get();
      xulyRepository.delete(xuly);
      return "redirect:/admin/xuly/all";
    } else {
      // Handle the case where the thietbi with the given id doesn't exist
      return "redirect:/admin/xuly/all";
    }
  }
 
  
  
  
}


