package display;

import Conection.KetNoi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class StoreHouseJPanel extends javax.swing.JPanel {

    /**
     * Creates new form StoreHouseJPanel
     */
    DefaultTableModel tbn = new DefaultTableModel();

    public StoreHouseJPanel() {

        initComponents();
        loadComboboxLoai();
        loadComboboxSize();
        loadData();

        txtSoLuong.setEnabled(false);

    }

    public void loadComboboxSize() {
        try {
            KetNoi a = new KetNoi();
            Connection conn = a.layKetNoi();
            PreparedStatement ps = conn.prepareStatement("Select SIZE from SIZE");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                jcbSize.addItem(rs.getString("SIZE"));
            }
        } catch (Exception e) {
            System.err.println("Bi loi khi load ComboBox Size ");
        }
    }

    public void loadComboboxLoai() {
        try {
            KetNoi a = new KetNoi();
            Connection conn = a.layKetNoi();
            PreparedStatement ps = conn.prepareStatement("Select MALOAI from LoaiGD");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                jcbLoai.addItem(rs.getString("MALOAI"));
            }
        } catch (Exception e) {
            System.err.println("Bi loi khi load ComboBox Loai ");
        }
    }

    public void loadData() {
        try {
            DefaultTableModel tbn = new DefaultTableModel();
            KetNoi a = new KetNoi();
            Connection conn = a.layKetNoi();
            int number;
            Vector row, column;
            column = new Vector();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT DISTINCT GIAYDEP.MASP, GIAYDEP.TENSP, GIAYDEP.DONGIA, CHITIETGD.SOLUONGTON, CHITIETGD.SIZE, LOAIGD.MALOAI\n"
                    + "FROM GIAYDEP\n"
                    + "	INNER JOIN CHITIETGD ON GIAYDEP.MASP = CHITIETGD.MASP\n"
                    + "	INNER JOIN SIZE ON GIAYDEP.MASP = CHITIETGD.MASP\n"
                    + "	INNER JOIN LOAIGD ON GIAYDEP.MALOAI = LOAIGD.MALOAI");
            ResultSetMetaData metadata = rs.getMetaData();
            number = metadata.getColumnCount(); //tra ve so cot
            for (int i = 1; i <= number; i++) {
                column.add(metadata.getColumnName(i)); // Lay ra tieu de cua cac cot
            }
            tbn.setColumnIdentifiers(column);

            while (rs.next()) {
                row = new Vector();
                for (int i = 1; i <= number; i++) {
                    row.addElement(rs.getString(i));
                }
                tbn.addRow(row);
                jTbSP.setModel(tbn);
            }

            // Ham tra gia tri tu bang len textField
            jTbSP.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (jTbSP.getSelectedRow() >= 0) {
                        txtMaSanPham.setText(jTbSP.getValueAt(jTbSP.getSelectedRow(), 0) + "");
                        txtTenSanPham.setText(jTbSP.getValueAt(jTbSP.getSelectedRow(), 1) + "");
                        txtDonGia.setText(jTbSP.getValueAt(jTbSP.getSelectedRow(), 2) + "");
                        txtSoLuong.setText(jTbSP.getValueAt(jTbSP.getSelectedRow(), 3) + "");
                        jcbSize.setSelectedItem(jTbSP.getModel().getValueAt(jTbSP.getSelectedRow(), 4) + "");
                        jcbLoai.setSelectedItem(jTbSP.getModel().getValueAt(jTbSP.getSelectedRow(), 5) + "");

                    }
                }

            });
            // ket thuc ham tra gia tri tu bang len textField 

        } catch (Exception e) {
            System.err.println("Loi khi Load Data");
        }
    }

    public void ThemChiTietGD() {
        KetNoi a = new KetNoi();
        Connection conn = a.layKetNoi();
        txtSoLuong.setText("0");
        String sqlChiTietGD = "insert into CHITIETGD values(?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sqlChiTietGD);
            ps.setString(1, txtMaSanPham.getText());
            ps.setString(2, (String) jcbSize.getSelectedItem());
            ps.setString(3, txtSoLuong.getText());
            ps.execute();

        } catch (Exception e) {
            System.err.println("Loi Them Chi Tiet Giay Dep");
        }
    }

    public void ThemGiayDep() {
        KetNoi a = new KetNoi();
        Connection conn = a.layKetNoi();

        String sqlGiayDep = "insert into GIAYDEP values(?,?,?,?)";
        try {
            PreparedStatement ps1 = conn.prepareStatement(sqlGiayDep);
            ps1.setString(1, txtMaSanPham.getText());
            ps1.setString(2, txtTenSanPham.getText());
            ps1.setString(3, txtDonGia.getText());
            ps1.setString(4, (String) jcbLoai.getSelectedItem());
            ps1.execute();

        } catch (Exception e) {
            System.err.println("Loi thêm giày dép");
        }
    }

    public void XoaGiayDep() {
        KetNoi a = new KetNoi();
        Connection conn = a.layKetNoi();

        String sql = "delete GiayDep Where masp = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, jTbSP.getValueAt(jTbSP.getSelectedRow(), 0).toString());
            ps.execute();
        } catch (Exception e) {
            System.err.println("Loi khi xoa CSDL GiayDep");
        }

    }

    public void XoaChiTietGD() {
        KetNoi a = new KetNoi();
        Connection conn = a.layKetNoi();

        String sql = "delete ChiTietGD Where masp = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, jTbSP.getValueAt(jTbSP.getSelectedRow(), 0).toString());
            ps.execute();
        } catch (Exception e) {
            System.err.println("Loi khi xoa CSDL ChiTietGiayDep");
        }
    }

    public void SuaGiayDep() {
        KetNoi a = new KetNoi();
        Connection conn = a.layKetNoi();

        String maSanPham, tenSanPham, donGiaSP, soLuongSP, sizeSP, loaiSP;
        maSanPham = txtMaSanPham.getText();
        tenSanPham = txtTenSanPham.getText();
        donGiaSP = txtDonGia.getText();
        soLuongSP = txtSoLuong.getText();
        sizeSP = jcbSize.getSelectedItem().toString();
        loaiSP = jcbLoai.getSelectedItem().toString();

        String sql = "update GiayDep set TENSP = '" + tenSanPham + "', DONGIA = '" + donGiaSP + "', MALOAI = '" + loaiSP + "'\n"
                + "where MASP = '" + maSanPham + "'";
        try {
            PreparedStatement ps1 = conn.prepareStatement(sql);
            ps1.execute();
        } catch (Exception e) {
            System.err.println("Bị lỗi khi sửa CSDL Giày dép");
        }
    }

    public void SuaChiTietGiayDep() {
        KetNoi a = new KetNoi();
        Connection conn = a.layKetNoi();

        String maSanPham, tenSanPham, donGiaSP, soLuongSP, sizeSP, loaiSP;
        maSanPham = txtMaSanPham.getText();
        tenSanPham = txtTenSanPham.getText();
        donGiaSP = txtDonGia.getText();
        soLuongSP = txtSoLuong.getText();
        sizeSP = jcbSize.getSelectedItem().toString();
        loaiSP = jcbLoai.getSelectedItem().toString();

        String sql = "update CHITIETGD set size = '" + sizeSP + "', soluongton = '" + soLuongSP + "' where masp = '" + maSanPham + "'";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.execute();
        } catch (Exception e) {
            System.err.println("Bị lỗi khi sửa CSDL Chi Tiết giày dép");
        }

    }
    /*
     public String LayMaSanPham(String maSPTemp,String sizeTemp){
     String maSP = "";
        
     String sql = "select CHITIETGD.MASP from CHITIETGD\n"
     + "where CHITIETGD.MASP = '" + maSP + "' and CHITIETGD.SIZE = '" + sizeTemp + "'";
     ConectionLogin a = new ConectionLogin();
     Connection conn = a.layKetNoi();
     try {
     PreparedStatement ps = conn.prepareStatement(sql);
     ResultSet rs = ps.executeQuery();
     while (rs.next()) {
     maSP = rs.getString("MASP");
     //System.out.println("ma sp trong ham la : " + maSP);
     }

     } catch (Exception e) {
     System.err.println("Loi tai lay ma sp");
     }
     return maSP;
     }
    
     public String LaySizeTheoMaSanPham(String maSPTemp,String sizeTemp){
     String size = "";
        
     String sql = "select CHITIETGD.MASP from CHITIETGD\n"
     + "where CHITIETGD.MASP = '" + maSPTemp + "' and CHITIETGD.SIZE = '" + sizeTemp + "'";
     ConectionLogin a = new ConectionLogin();
     Connection conn = a.layKetNoi();
     try {
     PreparedStatement ps = conn.prepareStatement(sql);
     ResultSet rs = ps.executeQuery();
     while (rs.next()) {
     size = rs.getString("SIZE");

     }

     } catch (Exception e) {
     System.err.println("Loi tai lay size");
     }
     return size;
     }    
     */

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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtMaSanPham = new javax.swing.JTextField();
        txtTenSanPham = new javax.swing.JTextField();
        txtDonGia = new javax.swing.JTextField();
        txtSoLuong = new javax.swing.JTextField();
        jcbSize = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jcbLoai = new javax.swing.JComboBox();
        jBtnXoaSanPham = new javax.swing.JButton();
        jBtnSuaSanPham = new javax.swing.JButton();
        jBtnThemSanPham = new javax.swing.JButton();
        jBtnLamLai = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        txtTimSanPham = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTbSP = new javax.swing.JTable();
        jBtnTimSanPham = new javax.swing.JButton();
        jBtnTaoPhieuNhap = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("QUẢN LÝ KHO");

        jLabel2.setText("Mã Sản Phẩm:");

        jLabel3.setText("Tên Sản Phẩm:");

        jLabel4.setText("Đơn Giá:");

        jLabel5.setText("Số Lượng:");

        jLabel6.setText("Size:");

        txtSoLuong.setText("0");

        jcbSize.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-Chọn Size-" }));

        jLabel7.setText("Loại:");

        jcbLoai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-Chọn Loại-" }));

        jBtnXoaSanPham.setText("Xóa Sản Phẩm");
        jBtnXoaSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnXoaSanPhamActionPerformed(evt);
            }
        });

        jBtnSuaSanPham.setText("Sửa Sản Phẩm");
        jBtnSuaSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSuaSanPhamActionPerformed(evt);
            }
        });

        jBtnThemSanPham.setText("Thêm sản phẩm");
        jBtnThemSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnThemSanPhamActionPerformed(evt);
            }
        });

        jBtnLamLai.setText("Làm lại");
        jBtnLamLai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnLamLaiActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setText("Thêm mới sản phẩm");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jBtnThemSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jBtnLamLai, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jBtnXoaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jBtnSuaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(31, 31, 31))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtTenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtMaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jcbSize, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jcbLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addContainerGap()))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbSize, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnThemSanPham)
                    .addComponent(jBtnSuaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnXoaSanPham)
                    .addComponent(jBtnLamLai))
                .addContainerGap())
        );

        txtTimSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimSanPhamActionPerformed(evt);
            }
        });

        jTbSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã Sản Phẩm", "Tên Sản Phẩm", "Đơn Giá", "Số Lượng", "Size", "Loại"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTbSP);

        jBtnTimSanPham.setText("Tìm kiếm");
        jBtnTimSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnTimSanPhamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtTimSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnTimSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 99, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTimSanPham)
                    .addComponent(jBtnTimSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
                .addContainerGap())
        );

        jBtnTaoPhieuNhap.setText("Tạo phiếu nhập mới");
        jBtnTaoPhieuNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnTaoPhieuNhapActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                        .addGap(489, 489, 489)
                        .addComponent(jBtnTaoPhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(17, 17, 17))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jBtnTaoPhieuNhap))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
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
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtTimSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimSanPhamActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_txtTimSanPhamActionPerformed

    private void jBtnTimSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnTimSanPhamActionPerformed
        // TODO add your handling code here:
        String timID = txtTimSanPham.getText();
        try {
            if (timID.trim().length() == 0) {
                loadData();
            } else {
                try {
                    String sql = "SELECT DISTINCT GIAYDEP.MASP, GIAYDEP.TENSP, GIAYDEP.DONGIA, CHITIETGD.SOLUONGTON, CHITIETGD.SIZE, LOAIGD.LOAI\n"
                            + "FROM GIAYDEP\n"
                            + "	INNER JOIN CHITIETGD ON GIAYDEP.MASP = CHITIETGD.MASP\n"
                            + "	INNER JOIN SIZE ON GIAYDEP.MASP = CHITIETGD.MASP\n"
                            + "	INNER JOIN LOAIGD ON GIAYDEP.MALOAI = LOAIGD.MALOAI\n"
                            + "WHERE GIAYDEP.MASP = '" + timID + "'";

                    KetNoi a = new KetNoi();
                    Connection conn = a.layKetNoi();
                    int number;
                    Vector row, column;
                    column = new Vector();
                    Statement st = conn.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    ResultSetMetaData metadata = rs.getMetaData();
                    number = metadata.getColumnCount(); //tra ve so cot
                    for (int i = 1; i <= number; i++) {
                        column.add(metadata.getColumnName(i)); // Lay ra tieu de cua cac cot
                    }
                    tbn.setColumnIdentifiers(column);

                    while (rs.next()) {
                        row = new Vector();
                        for (int i = 1; i <= number; i++) {
                            row.addElement(rs.getString(i));
                        }
                        tbn.addRow(row);
                        jTbSP.setModel(tbn);
                    }
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
            System.err.println("Loi nut san pham theo id");
        }

    }//GEN-LAST:event_jBtnTimSanPhamActionPerformed

    private void jBtnLamLaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnLamLaiActionPerformed
        // TODO add your handlingcode  here:
        txtMaSanPham.setText("");
        txtTenSanPham.setText("");
        txtDonGia.setText("");
        txtSoLuong.setText("0");
        jcbSize.setSelectedIndex(0);
        jcbLoai.setSelectedIndex(0);
        loadData();
    }//GEN-LAST:event_jBtnLamLaiActionPerformed

    private void jBtnThemSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnThemSanPhamActionPerformed
        // TODO add your handling code here:
        KetNoi a = new KetNoi();
        Connection conn = a.layKetNoi();

        String maSanPham, tenSanPham, donGiaSP, soLuongSP, sizeSP, loaiSP;
        maSanPham = txtMaSanPham.getText();
        tenSanPham = txtTenSanPham.getText();
        donGiaSP = txtDonGia.getText();
        soLuongSP = txtSoLuong.getText();
        sizeSP = jcbSize.getSelectedItem().toString();
        loaiSP = jcbLoai.getSelectedItem().toString();

        ///////////////////////     Kiểm Tra maSP cùng với Size đã tồn tại hay chưa??
        /*
         StoreHouseJPanel getMaSP = new StoreHouseJPanel();
         String layMASP = getMaSP.LayMaSanPham(maSanPham,sizeSP);
         StoreHouseJPanel getSizeSP = new StoreHouseJPanel();
         String laySizeSP = getSizeSP.LaySizeTheoMaSanPham(maSanPham,sizeSP);
        
         System.out.println("Ma San Pham da lay : " + layMASP);
         System.out.println("Size theo mã San Pham da lay : " + laySizeSP);
         */
        //////////////////////
        String sqlChiTietGD = "insert into CHITIETGD"
                + "values(?,?,?";
        String sqlGiayDep = "insert into GIAYDEP"
                + "values(?,?,?,?)";

        if (maSanPham.equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã sản phẩm");
        } else if (tenSanPham.equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên sản phẩm");
        } else if (donGiaSP.equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đơn giá sản phẩm");
        } else if (soLuongSP.equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng sản phẩm");
        } else if (sizeSP.equals("-Chọn Size-")) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn size sản phẩm");
        } else if (loaiSP.equals("-Chọn Loại-")) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn loại sản phẩm");
        } else {
            try {
                ThemGiayDep();
                ThemChiTietGD();
                loadData();
                JOptionPane.showMessageDialog(this, "Thêm thành công");
            } catch (Exception ex) {
                System.err.println("Loi Them San pham");
            }

        }

    }//GEN-LAST:event_jBtnThemSanPhamActionPerformed

    private void jBtnXoaSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnXoaSanPhamActionPerformed
        // TODO add your handling code here:
        try {
            XoaGiayDep();
            XoaChiTietGD();
            loadData();
            JOptionPane.showMessageDialog(this, "Xóa thành công");
        } catch (Exception e) {
            System.err.println("Loi tai nut Xoa san Pham");
        }
    }//GEN-LAST:event_jBtnXoaSanPhamActionPerformed

    private void jBtnSuaSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSuaSanPhamActionPerformed
        // TODO add your handling code here:
        try {
            SuaGiayDep();
            SuaChiTietGiayDep();
            loadData();
            JOptionPane.showMessageDialog(this, "Sửa thành công");
        } catch (Exception e) {
            System.err.println("Bi loi khi Sửa CSDL Chi tiet GD");
        }
    }//GEN-LAST:event_jBtnSuaSanPhamActionPerformed

    private void jBtnTaoPhieuNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnTaoPhieuNhapActionPerformed
        // TODO add your handling code here:
        ImportProductJFrame viewImportProductJFrame = new ImportProductJFrame();
        viewImportProductJFrame.setVisible(true);
    }//GEN-LAST:event_jBtnTaoPhieuNhapActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnLamLai;
    private javax.swing.JButton jBtnSuaSanPham;
    private javax.swing.JButton jBtnTaoPhieuNhap;
    private javax.swing.JButton jBtnThemSanPham;
    private javax.swing.JButton jBtnTimSanPham;
    private javax.swing.JButton jBtnXoaSanPham;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTbSP;
    private javax.swing.JComboBox jcbLoai;
    private javax.swing.JComboBox jcbSize;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtMaSanPham;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenSanPham;
    private javax.swing.JTextField txtTimSanPham;
    // End of variables declaration//GEN-END:variables
}
