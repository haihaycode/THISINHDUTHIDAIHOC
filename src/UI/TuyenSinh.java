/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package UI;

import DAO.KhoithiDao;
import DAO.TuyenSinhDao;
import Model.KhoiThi;
import Model.Thisinh;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HAIP
 */
public final class TuyenSinh extends javax.swing.JFrame {

    /**
     * Creates new form TuyenSinh
     */
    private int xx, xy;
    private final TuyenSinhDao tsd = new TuyenSinhDao();
    private final KhoithiDao ktd = new KhoithiDao();

    public TuyenSinh() {
        initComponents();//chạy giao diện GUI  chương trình
        setLocationRelativeTo(null);//căn giữa Jframe
        keoTha();
        fillTable();
        fillcombobox();
    }

    public void keoTha() {
        ThanhTieuDe.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                xx = e.getX();
                xy = e.getY();
            }

        });
        ThanhTieuDe.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                int x = e.getXOnScreen();
                int y = e.getYOnScreen();
                setLocation(x - xx, y - xy);
            }
        });
    }

    public void setForm(Thisinh thisinh) {
        txtSoBaoDanh.setText(Integer.toString(thisinh.getSoBaoDanh()));
        txtHoTen.setText(thisinh.getHoTen());
        txtDiachi.setText(thisinh.getDiaChi());
        txtMucdoUwTien.setText(thisinh.getMucUuTien());
        cbbKhoiThi.setSelectedItem(thisinh.getKhoiThi());
    }

    public Thisinh getForm() {
        Thisinh model = new Thisinh();
        if (txtSoBaoDanh.getText().equals("") || txtHoTen.getText().equals("") || txtMucdoUwTien.getText().equals("") | txtDiachi.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui Lòng Nhập Đầy Đủ");
            model = null;
        } else {

            try {
                int soBaoDanh = Integer.parseInt(txtSoBaoDanh.getText());
                if (checkExists(soBaoDanh)) {
                    JOptionPane.showMessageDialog(this, "Số Báo Danh đã tồn tại !");
                    model = null;
                } else {
                    model.setSoBaoDanh(soBaoDanh);
                    model.setHoTen(txtHoTen.getText());
                    model.setDiaChi(txtDiachi.getText());
                    model.setMucUuTien(txtMucdoUwTien.getText());
                    model.setKhoiThi((String) cbbKhoiThi.getSelectedItem());
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Số Báo Danh phải là số !");
                model = null;
            }

        }
        return model;

    }

    public void clearForm() {
        txtSoBaoDanh.setText("");
        txtDiachi.setText("");
        txtHoTen.setText("");
        txtMucdoUwTien.setText("");
        txtTimKiem.setText("");
        cbbKhoiThi.setSelectedIndex(0);
    }

    public void fillTable() {

        DefaultTableModel model = (DefaultTableModel) tblTableThiSinh.getModel();
        model.setRowCount(0);
        List<Thisinh> list = tsd.select();
        for (Thisinh thisinh : list) {
            List<String> listMonThi = thisinh.getMonThi();
            String MonThi = "";
            for (String monthi : listMonThi) {
                MonThi = MonThi + " " + monthi;
            }
            model.addRow(new Object[]{
                thisinh.getSoBaoDanh(),
                thisinh.getHoTen(),
                thisinh.getDiaChi(),
                thisinh.getMucUuTien(),
                thisinh.getKhoiThi(),
                MonThi
            });
        }

    }

    public void fillcombobox() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbKhoiThi.getModel();
        model.setSelectedItem(0);
        model.removeAllElements();
        try {
            List<KhoiThi> kh = ktd.select();
            for (KhoiThi monthi : kh) {
                model.addElement(monthi.getTenKhoi());
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void fillTableToForm() {
        int selectedRow = tblTableThiSinh.getSelectedRow();
        if (selectedRow != -1) {
            int value = (int) tblTableThiSinh.getValueAt(selectedRow, 0);
            Thisinh ts = tsd.selectById(value);
            setForm(ts);
        } else {
            //bỏ qua
        }
    }

    public void ThemThiSinh() {
        Thisinh thisinhThem = getForm();
        if (thisinhThem == null) {
//            JOptionPane.showMessageDialog(this, "Quá trình thêm bị lỗi");
        } else {
            tsd.insert(thisinhThem);
            JOptionPane.showMessageDialog(this, "Quá trình thêm hoàn tất");
            clearForm();
            fillTable();
        }
    }

    public boolean checkExists(int soBaoDanh) {
        List<Thisinh> list = tsd.select();
        for (Thisinh thisinh : list) {
            if (thisinh.getSoBaoDanh() == soBaoDanh) {
                return true; // Trả về true nếu soBaoDanh đã tồn tại trong danh sách
            }
        }
        return false; // Trả về false nếu soBaoDanh không tồn tại trong danh sách
    }

    public void timKiemTheoSoBaoDanh() {

        try {
            if (checkExists(Integer.parseInt(txtTimKiem.getText().trim()))) {
                Thisinh thisinh = tsd.selectById(Integer.parseInt(txtTimKiem.getText().trim()));
                setForm(thisinh);

            } else {

                JOptionPane.showMessageDialog(this, "Số Báo danh không tồn tại");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số Báo danh phải là số");
        }

    }

    public void xoaThiSinh() {
        try {
            if (checkExists(Integer.parseInt(txtSoBaoDanh.getText().trim()))) {
                int mess = JOptionPane.showConfirmDialog(null, "Bạn có thực sự muốn xóa đi thí sinh này không ?", "Xóa Thí Sinh", JOptionPane.YES_NO_OPTION);
                if (JOptionPane.YES_OPTION == mess) {
                    tsd.detete(Integer.parseInt(txtSoBaoDanh.getText().trim()));
                    clearForm();
                    fillTable();
                    JOptionPane.showMessageDialog(this, "Quá trình Xóa hoàn tất");
                }else{
                    //bỏ qua
                }

            } else {

                JOptionPane.showMessageDialog(this, "Số Báo danh không tồn tại");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số Báo danh phải là số");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        ThanhTieuDe = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnThoatChuongTrinh = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtSoBaoDanh = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        txtHoTen = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        txtDiachi = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        txtMucdoUwTien = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        cbbKhoiThi = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        btnThemThiSinh = new javax.swing.JButton();
        btnXoaTrang = new javax.swing.JButton();
        btnXoaThiSinh = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTableThiSinh = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        ThanhTieuDe.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("QUẢN LÍ THÔNG TIN SINH VIÊN DỰ THI ĐẠI HỌC");

        btnThoatChuongTrinh.setBackground(new java.awt.Color(255, 0, 0));
        btnThoatChuongTrinh.setForeground(new java.awt.Color(255, 255, 255));
        btnThoatChuongTrinh.setMnemonic('T');
        btnThoatChuongTrinh.setText("Thoát Chương Trình");
        btnThoatChuongTrinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatChuongTrinhActionPerformed(evt);
            }
        });

        jLabel2.setText("❐");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Adobe Caslon Pro Bold", 0, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("_");
        jLabel3.setToolTipText("");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout ThanhTieuDeLayout = new javax.swing.GroupLayout(ThanhTieuDe);
        ThanhTieuDe.setLayout(ThanhTieuDeLayout);
        ThanhTieuDeLayout.setHorizontalGroup(
            ThanhTieuDeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ThanhTieuDeLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 581, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnThoatChuongTrinh, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        ThanhTieuDeLayout.setVerticalGroup(
            ThanhTieuDeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ThanhTieuDeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                .addComponent(btnThoatChuongTrinh)
                .addComponent(jLabel2))
            .addGroup(ThanhTieuDeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(4, 4, 4))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        txtSoBaoDanh.setBorder(null);

        jLabel4.setText("Họ Và Tên");

        txtHoTen.setBorder(null);

        jLabel5.setText("Địa Chỉ");

        txtDiachi.setBorder(null);

        jLabel6.setText("Mức Ưu Tiên");

        txtMucdoUwTien.setBorder(null);

        jLabel7.setText("Khối Thi");

        cbbKhoiThi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "khối Thi" }));

        jLabel8.setText("Số Báo Danh");

        jLabel9.setText("Tìm kiếm theo số báo danh ");

        btnTimKiem.setBackground(new java.awt.Color(0, 51, 102));
        btnTimKiem.setForeground(new java.awt.Color(255, 255, 255));
        btnTimKiem.setMnemonic('T');
        btnTimKiem.setText("Tìm Kiếm");
        btnTimKiem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTimKiemMouseClicked(evt);
            }
        });

        btnThemThiSinh.setBackground(new java.awt.Color(0, 51, 102));
        btnThemThiSinh.setForeground(new java.awt.Color(255, 255, 255));
        btnThemThiSinh.setMnemonic('T');
        btnThemThiSinh.setText("Thêm Thí Sinh");
        btnThemThiSinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThemThiSinhMouseClicked(evt);
            }
        });

        btnXoaTrang.setBackground(new java.awt.Color(0, 51, 102));
        btnXoaTrang.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaTrang.setMnemonic('X');
        btnXoaTrang.setText("Xóa Trắng");
        btnXoaTrang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaTrangActionPerformed(evt);
            }
        });

        btnXoaThiSinh.setBackground(new java.awt.Color(0, 51, 102));
        btnXoaThiSinh.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaThiSinh.setMnemonic('T');
        btnXoaThiSinh.setText("Xóa Thí Sinh");
        btnXoaThiSinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXoaThiSinhMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtSoBaoDanh)
            .addComponent(jSeparator1)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
            .addComponent(txtHoTen)
            .addComponent(jSeparator2)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
            .addComponent(txtDiachi)
            .addComponent(jSeparator3)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
            .addComponent(txtMucdoUwTien)
            .addComponent(jSeparator4)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
            .addComponent(jSeparator5)
            .addComponent(cbbKhoiThi, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtTimKiem)
                        .addGap(18, 18, 18)
                        .addComponent(btnTimKiem))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnThemThiSinh)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoaThiSinh)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnXoaTrang)))
                .addContainerGap())
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiem))
                .addGap(33, 33, 33)
                .addComponent(txtSoBaoDanh, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDiachi, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMucdoUwTien, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbbKhoiThi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemThiSinh)
                    .addComponent(btnXoaTrang)
                    .addComponent(btnXoaThiSinh))
                .addContainerGap(106, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(72, 72, 72)
                    .addComponent(jLabel8)
                    .addContainerGap(417, Short.MAX_VALUE)))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        tblTableThiSinh.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Số Báo Danh", "Họ Tên", "Địa chỉ", "Mức độ ưu tiên", "Khối Thi", "Các Môn Thi"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTableThiSinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTableThiSinhMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblTableThiSinh);

        jPanel4.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ThanhTieuDe, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(ThanhTieuDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThoatChuongTrinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatChuongTrinhActionPerformed
        int mess = JOptionPane.showConfirmDialog(null, "Bạn có muốn thoát chương trình không", "Thoát Chương trình", JOptionPane.YES_NO_OPTION);
        if (JOptionPane.YES_OPTION == mess) {
            System.exit(0);
        }
    }//GEN-LAST:event_btnThoatChuongTrinhActionPerformed

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        setExtendedState(JFrame.ICONIFIED);
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        if (getExtendedState() != JFrame.MAXIMIZED_BOTH) {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        } else {
            setSize(1300, 700);
            setExtendedState(JFrame.NORMAL);
        }
    }//GEN-LAST:event_jLabel2MouseClicked

    private void tblTableThiSinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTableThiSinhMouseClicked
        fillTableToForm();
    }//GEN-LAST:event_tblTableThiSinhMouseClicked

    private void btnXoaTrangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaTrangActionPerformed
        clearForm();
    }//GEN-LAST:event_btnXoaTrangActionPerformed

    private void btnThemThiSinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemThiSinhMouseClicked
        ThemThiSinh();
    }//GEN-LAST:event_btnThemThiSinhMouseClicked

    private void btnTimKiemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTimKiemMouseClicked
        timKiemTheoSoBaoDanh();
    }//GEN-LAST:event_btnTimKiemMouseClicked

    private void btnXoaThiSinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaThiSinhMouseClicked
        xoaThiSinh();
    }//GEN-LAST:event_btnXoaThiSinhMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TuyenSinh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TuyenSinh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TuyenSinh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TuyenSinh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TuyenSinh().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ThanhTieuDe;
    private javax.swing.JButton btnThemThiSinh;
    private javax.swing.JButton btnThoatChuongTrinh;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoaThiSinh;
    private javax.swing.JButton btnXoaTrang;
    private javax.swing.JComboBox<String> cbbKhoiThi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTable tblTableThiSinh;
    private javax.swing.JTextField txtDiachi;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtMucdoUwTien;
    private javax.swing.JTextField txtSoBaoDanh;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
