/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Helper.JDBC;
import Model.Thisinh;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HAIP
 */
public class TuyenSinhDao {
    public List<Thisinh> select() {
        String Sql = "SELECT * from ThiSinh inner join KhoiThi on ThiSinh.KhoiThi= khoithi.khoithi";
        return select(Sql);
    }
    public Thisinh selectById(int SoBaoDanh) {
        String Sql = "SELECT * from ThiSinh inner join KhoiThi on ThiSinh.KhoiThi= khoithi.khoithi where SoBaoDanh like ?";
        List<Thisinh> list = select(Sql, SoBaoDanh);
        return list.size() > 0 ? list.get(0) : null;
    }
    public void insert(Thisinh model) {
        String Sql = "INSERT INTO ThiSinh (SoBaoDanh, HoTen, DiaChi, MucUuTien, KhoiThi) VALUES (?,?,?,?,?)";
        JDBC.executeUpdate(Sql, model.getSoBaoDanh(), model.getHoTen(), model.getDiaChi(), model.getMucUuTien(), model.getKhoiThi());
    }
        public void detete(int SoBaoDanh) {
        String Sql = "Delete from ThiSinh where SoBaoDanh =?";
        JDBC.executeUpdate(Sql, SoBaoDanh);
        
    }
    

    private List<Thisinh> select(String sql, Object... args) {
        List<Thisinh> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JDBC.executeQuery(sql, args);
                while (rs.next()) {
                    Thisinh model = readFromResultSet(rs);
                    list.add(model);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }

    private Thisinh readFromResultSet(ResultSet rs) throws SQLException {
        Thisinh model = new Thisinh();
        model.setSoBaoDanh(rs.getInt("SoBaoDanh"));
        model.setHoTen(rs.getString("HoTen"));
        model.setDiaChi(rs.getString("DiaChi"));
        model.setMucUuTien(rs.getString("MucUuTien"));
        model.setKhoiThi(rs.getString("KhoiThi"));
        List<String> monThi = new ArrayList<>();
        monThi.add(rs.getString("MonThi1"));
        monThi.add(rs.getString("MonThi2"));
        monThi.add(rs.getString("MonThi3"));
        model.setMonThi(monThi);
        return model;
    }

    //testSql
    public static void main(String[] args) {
        TuyenSinhDao dao = new TuyenSinhDao();
        dao.select();
    }
}
