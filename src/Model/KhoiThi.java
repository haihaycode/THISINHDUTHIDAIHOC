/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.List;

/**
 *
 * @author HAIP
 */
public class KhoiThi {

    private String tenKhoi;
    private List<String> monThi;

    public KhoiThi(String tenKhoi, List<String> monThi) {
        this.tenKhoi = tenKhoi;
        this.monThi = monThi;
    }

    public KhoiThi() {
    }

    public String getTenKhoi() {
        return tenKhoi;
    }

    public void setTenKhoi(String tenKhoi) {
        this.tenKhoi = tenKhoi;
    }

    public List<String> getMonThi() {
        return monThi;
    }

    public void setMonThi(List<String> monThi) {
        this.monThi = monThi;
    }

}
