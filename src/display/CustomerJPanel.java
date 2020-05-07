/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package display;

import Conection.KetNoi;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import model.Customer;

/**
 *
 * @author Admin
 */
public class CustomerJPanel extends javax.swing.JPanel {

    List<Customer> customerList = new ArrayList<Customer>();

    public CustomerJPanel() {
        initComponents();
        jLabel_LOISDT.setVisible(false);
        layKhachHang();
    }

    private void layKhachHang() {
        Connection ketNoi = KetNoi.layKetNoi();
        DefaultTableModel dtm = (DefaultTableModel) jtbKhachHang.getModel();
        dtm.setNumRows(0);
        String sql = "select * from KHACHHANG";
        Vector vt;
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            customerList.clear(); //moi lan lay lai kh phai reset lai list moi (loi bi trung du lieu)
            while (rs.next()) {
                Customer cs = new Customer(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
                customerList.add(cs);
                if (rs.getString(1).equals("KVL")) {
                    continue; //khach vang lai ko can xuat ra table
                }
                vt = new Vector();
                vt.add(rs.getString(1));
                vt.add(rs.getString(2));
                vt.add(rs.getString(3));
                vt.add(rs.getString(4));
                dtm.addRow(vt);
            }
            jtbKhachHang.setModel(dtm);
            rs.close();
            ps.close();
            ketNoi.close();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void themKhachHang(Customer cs) {
        Connection ketNoi = KetNoi.layKetNoi();
        String sql = "insert into KHACHHANG values (?,?,?,?)";
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql);
            ps.setString(1, cs.getMaKH());
            ps.setString(2, cs.getHoTen());
            if (cs.getNgaySinh().equals("")) {
                ps.setString(3, null);
            } else {
                ps.setString(3, cs.getNgaySinh());
            }
            ps.setString(4, cs.getDiaChi());
            ps.executeUpdate();
            ps.close();
            ketNoi.close();
            customerList.add(cs);
        } catch (SQLException ex) {
            Logger.getLogger(CustomerJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void xoaKhachHang(String maKH) {
        Connection ketNoi = KetNoi.layKetNoi();
        String sql = "delete from KHACHHANG where MAKH=?";
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql);
            ps.setString(1, maKH);
            ps.executeUpdate();
            ps.close();
            ketNoi.close();
            for (Customer cs : customerList) {
                if (cs.getMaKH().equals(maKH)) {
                    customerList.remove(cs);
                    break;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void suaKhachHang(Customer cs) {
        Connection ketNoi = KetNoi.layKetNoi();
        String sql = "update KHACHHANG set HOTEN=?,NGAYSINH=?,DIACHI=? where MAKH=?";
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql);
            ps.setString(1, cs.getHoTen());
            ps.setString(2, cs.getNgaySinh());
            ps.setString(3, cs.getDiaChi());
            ps.setString(4, cs.getMaKH());
            ps.executeUpdate();
            ps.close();
            ketNoi.close();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String chuanHoaChuoiHoTen(String hoTen) {
        String ten = hoTen.trim().replaceAll("\\s+", " ");
        if (ten.equals("")) {
            return ten;
        }
        String[] arr = ten.split(" ");
        String tmp = "";
        for (String t : arr) {
            t = t.substring(0, 1).toUpperCase() + t.substring(1, t.length());
            tmp = tmp + t + " ";
        }
        ten = tmp.trim();
        return ten;
    }

    private int checkMaKH_SDT(String maKH) {
        for (Customer cs : customerList) {
            if (cs.getMaKH().equals(maKH)) {
                return 1;//trung sdt
            }
        }
        return 0;
    }

    private void lamMoi() {
        txtTenKhachHang.setText("");
        txtDiaChiKhachHang.setText("");
        txtSDTKhachHang.setText("");
        jDateKhachHang.setCalendar(null);
        jLabel_LOISDT.setVisible(false);
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
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtSDTKhachHang = new javax.swing.JTextField();
        txtTenKhachHang = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDiaChiKhachHang = new javax.swing.JTextArea();
        jDateKhachHang = new com.toedter.calendar.JDateChooser();
        jLabel_LOISDT = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtTimKiemKhachHang = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbKhachHang = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jBtnTimKiemKhachHang = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jBtnThemKhachHang = new javax.swing.JButton();
        jBtnSuaKhachHang = new javax.swing.JButton();
        jBtnXoaKhachHang = new javax.swing.JButton();
        jBtnLamMoiKhachHang = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("DANH MỤC: KHÁCH HÀNG");

        jLabel3.setText("Mã khách hàng/SDT");

        jLabel4.setText("Tên khách hàng");

        jLabel5.setText("Ngày sinh:");

        jLabel6.setText("Địa chỉ:");

        txtSDTKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSDTKhachHangActionPerformed(evt);
            }
        });
        txtSDTKhachHang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSDTKhachHangKeyReleased(evt);
            }
        });

        txtTenKhachHang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTenKhachHangKeyReleased(evt);
            }
        });

        txtDiaChiKhachHang.setColumns(20);
        txtDiaChiKhachHang.setRows(5);
        jScrollPane2.setViewportView(txtDiaChiKhachHang);

        jLabel_LOISDT.setForeground(new java.awt.Color(255, 0, 0));
        jLabel_LOISDT.setText("Sai định dạng sđt. Hãy thử lại");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtSDTKhachHang)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                    .addComponent(txtTenKhachHang, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jDateKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_LOISDT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSDTKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_LOISDT, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                    .addComponent(jDateKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE))
                .addGap(46, 46, 46))
        );

        txtTimKiemKhachHang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKhachHangKeyReleased(evt);
            }
        });

        jtbKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "SĐT", "Họ và tên", "Ngày Sinh", "Địa chỉ"
            }
        ));
        jtbKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtbKhachHangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtbKhachHang);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Thông tin khách hàng:");

        jBtnTimKiemKhachHang.setText("Tìm Kiếm");
        jBtnTimKiemKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnTimKiemKhachHangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtTimKiemKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jBtnTimKiemKhachHang)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiemKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnTimKiemKhachHang))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jBtnThemKhachHang.setText("Thêm khách hàng");
        jBtnThemKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnThemKhachHangActionPerformed(evt);
            }
        });

        jBtnSuaKhachHang.setText("Sửa thông tin");
        jBtnSuaKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSuaKhachHangActionPerformed(evt);
            }
        });

        jBtnXoaKhachHang.setText("Xóa thông tin");
        jBtnXoaKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnXoaKhachHangActionPerformed(evt);
            }
        });

        jBtnLamMoiKhachHang.setText("Làm mới");
        jBtnLamMoiKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnLamMoiKhachHangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jBtnXoaKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                    .addComponent(jBtnThemKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(22, 22, 22)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBtnSuaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnLamMoiKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(75, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnThemKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnSuaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnXoaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnLamMoiKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
                        .addGap(596, 596, 596))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(52, 52, 52))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnThemKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnThemKhachHangActionPerformed
        // TODO add your handling code here:
        String maKH = txtSDTKhachHang.getText();
        String tenKH = chuanHoaChuoiHoTen(txtTenKhachHang.getText());
        String diaChi = txtDiaChiKhachHang.getText();

        String date = "";
        if (jDateKhachHang.getDate() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.format(jDateKhachHang.getDate()); // Date->String
        }

        boolean cohieu = true;
        if (maKH.equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn cần nhập mã khách hàng là sđt của khách!");
            return;
        }
        if (maKH.matches("^(\\+84|0)\\d{9,10}$") == false) {
            jLabel_LOISDT.setVisible(true);
            txtSDTKhachHang.requestFocus();
            cohieu = false;
        }
        if (tenKH.equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn cần nhập tên khách hàng!");
            return;
        }
        if (cohieu == true) {
            int c = checkMaKH_SDT(maKH);
            if (c == 1) {
                JOptionPane.showMessageDialog(this, "Mã khách hàng với sđt trên đã bị trùng. Vui lòng kiểm tra lại!");
            } else {
                Customer cs = new Customer(maKH, tenKH, date, diaChi);
                themKhachHang(cs);
                JOptionPane.showMessageDialog(this, "Đã thêm thành công khách hàng mới!");
            }
        }
        this.layKhachHang();
    }//GEN-LAST:event_jBtnThemKhachHangActionPerformed

    private void jBtnSuaKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSuaKhachHangActionPerformed
        // TODO add your handling code here:
        String maKH = txtSDTKhachHang.getText();
        int c = checkMaKH_SDT(maKH);
        if (c == 0) {
            JOptionPane.showMessageDialog(this, "Mã khách hàng hiện chưa có. Vui lòng thêm mới!");
        } else {
            String tenKH = txtTenKhachHang.getText();
            String diaChi = txtDiaChiKhachHang.getText();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(jDateKhachHang.getDate());

            for (Customer cs : customerList) {
                if (cs.getMaKH().equals(maKH)) {
                    cs.setHoTen(tenKH);
                    cs.setNgaySinh(date);
                    cs.setDiaChi(diaChi);
                    suaKhachHang(cs);
                    JOptionPane.showMessageDialog(this, "Sửa thông tin khách hàng thành công!");
                    this.layKhachHang();
                    break;
                }
            }
        }
    }//GEN-LAST:event_jBtnSuaKhachHangActionPerformed

    private void jBtnXoaKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnXoaKhachHangActionPerformed
        // TODO add your handling code here:
        String maKH = txtSDTKhachHang.getText();
        if (maKH.equals("")) {
            JOptionPane.showMessageDialog(this, "Mã khách hàng cần xóa không được dể trống!");
        } else {
            int c = checkMaKH_SDT(maKH);
            if (c == 0) {
                JOptionPane.showMessageDialog(this, "Mã khách hàng này không tồn tại!");
            } else {
                Object[] options = {"Đồng ý", "Hủy"};
                int chon = JOptionPane.showOptionDialog(this, "Bạn có chắc muốn xóa hv này không?",
                        "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (chon == JOptionPane.YES_OPTION) {
                    xoaKhachHang(maKH);
                    JOptionPane.showMessageDialog(this, "Xóa khách hàng thành công!");
                    this.layKhachHang();
                } else {
                    lamMoi();
                }
            }
        }

    }//GEN-LAST:event_jBtnXoaKhachHangActionPerformed

    private void txtSDTKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSDTKhachHangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSDTKhachHangActionPerformed

    private void jBtnLamMoiKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnLamMoiKhachHangActionPerformed
        // TODO add your handling code here:
        lamMoi();
    }//GEN-LAST:event_jBtnLamMoiKhachHangActionPerformed

    private void jtbKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtbKhachHangMouseClicked
        // TODO add your handling code here:
        int i = jtbKhachHang.getSelectedRow();
        DefaultTableModel dtm = (DefaultTableModel) jtbKhachHang.getModel();
        txtSDTKhachHang.setText(dtm.getValueAt(i, 0).toString());
        txtTenKhachHang.setText(dtm.getValueAt(i, 1).toString());
        if (dtm.getValueAt(i, 2) != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdf.parse((String) dtm.getValueAt(i, 2)); //String->Date
                jDateKhachHang.setDate(date);
            } catch (ParseException ex) {
                Logger.getLogger(CustomerJPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            jDateKhachHang.setCalendar(null);
        }
        if (dtm.getValueAt(i, 3) != null) {
            txtDiaChiKhachHang.setText(dtm.getValueAt(i, 3).toString());
        } else {
            txtDiaChiKhachHang.setText("");
        }
    }//GEN-LAST:event_jtbKhachHangMouseClicked

    private void txtSDTKhachHangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSDTKhachHangKeyReleased
        // TODO add your handling code here:
        String t = txtSDTKhachHang.getText();
        if (t.matches("^(\\+84|0)\\d{9,10}$") == false) {
            jLabel_LOISDT.setVisible(true);
        } else {
            jLabel_LOISDT.setVisible(false);
        }
    }//GEN-LAST:event_txtSDTKhachHangKeyReleased

    private void txtTenKhachHangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTenKhachHangKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenKhachHangKeyReleased

    private void txtTimKiemKhachHangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKhachHangKeyReleased
        // TODO add your handling code here:
        String f = txtTimKiemKhachHang.getText();

        DefaultTableModel dtm = (DefaultTableModel) jtbKhachHang.getModel();
        dtm.setNumRows(0);
        for (Customer x : customerList) {
            if (x.getMaKH().equals("KVL")) {
                continue;
            }
            String ten = x.getHoTen().toLowerCase();
            if (x.getMaKH().contains(f) || ten.contains(f.toLowerCase())) {
                Vector vt = new Vector();
                vt.add(x.getMaKH());
                vt.add(x.getHoTen());
                vt.add(x.getNgaySinh());
                vt.add(x.getDiaChi());
                dtm.addRow(vt);
            }
        }
        jtbKhachHang.setModel(dtm);
    }//GEN-LAST:event_txtTimKiemKhachHangKeyReleased

    private void jBtnTimKiemKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnTimKiemKhachHangActionPerformed
        // TODO add your handling code here:
        String f = txtTimKiemKhachHang.getText();
        boolean c = false;
        for (Customer cs : customerList) {
            String ten = cs.getHoTen().toLowerCase();
            if (cs.getMaKH().contains(f) || ten.contains(f.toLowerCase())) {
                c = true;
            }
        }
        if (c == false) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng này!");
        }
    }//GEN-LAST:event_jBtnTimKiemKhachHangActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnLamMoiKhachHang;
    private javax.swing.JButton jBtnSuaKhachHang;
    private javax.swing.JButton jBtnThemKhachHang;
    private javax.swing.JButton jBtnTimKiemKhachHang;
    private javax.swing.JButton jBtnXoaKhachHang;
    private com.toedter.calendar.JDateChooser jDateKhachHang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel_LOISDT;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jtbKhachHang;
    private javax.swing.JTextArea txtDiaChiKhachHang;
    private javax.swing.JTextField txtSDTKhachHang;
    private javax.swing.JTextField txtTenKhachHang;
    private javax.swing.JTextField txtTimKiemKhachHang;
    // End of variables declaration//GEN-END:variables
}
