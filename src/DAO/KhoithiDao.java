/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Helper.JDBC;
import Model.KhoiThi;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HAIP
 */
public class KhoithiDao {
    public List<KhoiThi> select() {
        String Sql = "SELECT * from KhoiThi";
        return select(Sql);
    }
     private List<KhoiThi> select(String sql, Object... args) {
        List<KhoiThi> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs =JDBC.executeQuery(sql, args);
                while (rs.next()) {
                    KhoiThi model = readFromResultSet(rs);
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
     
     private KhoiThi readFromResultSet(ResultSet rs) throws SQLException {
        KhoiThi model = new KhoiThi();
        model.setTenKhoi(rs.getString("khoithi"));
        List<String> monThi = new ArrayList<>();
        monThi.add(rs.getString("MonThi1"));
        monThi.add(rs.getString("MonThi2"));
        monThi.add(rs.getString("MonThi3"));
        model.setMonThi(monThi);
        return model;
    }

}
