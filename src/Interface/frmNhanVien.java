/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Interface;

import Database.DatabaseHelper;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Asus
 */
public class frmNhanVien extends javax.swing.JInternalFrame {

    /**
     * Creates new form frmNhanvien1
     */
    frmNhanVien(frmMain aThis) {
        initComponents();
        getNhanVien();
        loadDepartmentsToComboBox();
    }

    public void getNhanVien() {

        try {
            // Lấy mô hình (model) của jTable1 và xóa tất cả các hàng hiện tại
            DefaultTableModel dt = (DefaultTableModel) tbNhanvien.getModel();
            dt.setRowCount(0);

            // Tạo kết nối với cơ sở dữ liệu
            DatabaseHelper cn = new DatabaseHelper();
            System.out.println("Connected SQL server success");
            Object[] argv = new Object[0];

            // Thực hiện truy vấn và lấy dữ liệu từ bảng employees
            try (ResultSet resultSet = cn.selectQuery("SELECT * FROM employees", argv)) {
                System.out.println("Kết nối OK" + resultSet);
                while (resultSet.next()) {
                    // Tạo một hàng dữ liệu để thêm vào bảng
                    Vector v = new Vector();
                    v.add(resultSet.getString("empId"));  // Mã nhân viên
                    v.add(resultSet.getString("name"));   // Tên nhân viên
                    v.add(resultSet.getString("dob"));    // Ngày sinh
                    v.add(resultSet.getString("gender")); // Giới tính
                    v.add(resultSet.getString("email"));  // Email
                    v.add(resultSet.getString("phone"));  // Số điện thoại
                    v.add(resultSet.getString("pos"));    // Chức vụ
                    v.add(resultSet.getString("sal"));  //lương
                    v.add(resultSet.getString("deptId"));  //mã phòng
                    // Thêm hàng vào mô hình của jTable1
                    dt.addRow(v);
                }

                // Đóng kết nối sau khi hoàn thành
                cn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi trong quá trình lấy dữ liệu: " + e.getMessage());
        }
    }

    public void loadDepartmentsToComboBox() { //lấy ra từ phòng
        try {
            DatabaseHelper cn = new DatabaseHelper();
            ResultSet resultSet = cn.selectQuery("SELECT deptId, deptName FROM departments", new Object[0]);

            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            while (resultSet.next()) {
                String deptId = resultSet.getString("deptId");
                String deptName = resultSet.getString("deptName");
                model.addElement(deptId + " - " + deptName);
            }

            boxPhong.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi tải dữ liệu phòng: " + e.getMessage());
        }
    }

    public int insertNhanVien() {
        // ID is Auto inc
        String id = txtId.getText().trim();
        String name = txtName.getText().trim();
        String dob = txtDate.getText().trim();
        String gender = boxGT.getSelectedItem().toString();
        String email = txtEmail.getText().trim();
        String phone = txtPhone.getText().trim();
        String pos = txtChucvu.getText().trim();
        String sal = txtLuong.getText().trim();
        String deptId = boxPhong.getSelectedItem().toString().split(" - ")[0];

        // Kiểm tra nếu các trường không được để trống
        if (name.isEmpty() || dob.isEmpty() || gender.isEmpty() || email.isEmpty() || phone.isEmpty() || pos.isEmpty() || sal.isEmpty() || deptId.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin!");
            return 0;
        }

        Object[] argv = new Object[9];
        // Số lượng tham số đúng
        argv[0] = Integer.parseInt(id);
        argv[1] = name;
        argv[2] = dob;
        argv[3] = gender;
        argv[4] = email;
        argv[5] = phone;
        argv[6] = pos;
        argv[7] = sal;
        argv[8] = deptId.split(" - ")[0];

        try {
            // Tạo kết nối tới cơ sở dữ liệu
            DatabaseHelper cn = new DatabaseHelper();
            // Thực hiện câu truy vấn chèn dữ liệu vào bảng employees
            int rs = cn.executeQuery("INSERT INTO employees (empId, name, dob, gender, email, phone, pos, sal, deptId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", argv);

            // Kiểm tra xem dữ liệu đã được chèn thành công chưa
            if (rs > 0) {
                JOptionPane.showMessageDialog(null, "Thêm mới thành công!");
                clearText(); // Xóa các trường nhập liệu sau khi thêm thành công
            }
            return rs; // Trả về số lượng hàng đã chèn

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Thêm mới thất bại: " + e.getMessage());
            return 0; // Trả về 0 nếu có lỗi
        }
    }

    private String[] getDataFromUI() {

        String id = txtId.getText().trim();  // Lấy và xóa khoảng trắng đầu/cuối
        String name = txtName.getText().trim();
        String dob = txtDate.getText().trim();
        String gender = boxGT.getSelectedItem().toString();
        String email = txtEmail.getText().trim();
        String phone = txtPhone.getText().trim();
        String pos = txtChucvu.getText().trim();
        String sal = txtLuong.getText().trim();
        String deptId = boxPhong.getSelectedItem().toString().split(" - ")[0];

        // Trả về mảng với các giá trị lấy từ giao diện
        return new String[]{id, name, dob, gender, email, phone, pos, sal, deptId};
    }

    public void updateNhanVien() {
        try {
            String[] data = getDataFromUI();

            if (data.length < 9) {
                JOptionPane.showMessageDialog(null, "Dữ liệu không đủ để cập nhật!");
                return;
            }

            if (data[0].isEmpty()) {
                JOptionPane.showMessageDialog(null, "ID không được để trống!");
                return;
            }
            int id = Integer.parseInt(data[0]);
            String name = data[1];
            String dob = data[2];
            String gender = data[3];
            String email = data[4];
            String phone = data[5];
            String pos = data[6];
            String sal = data[7];
            String deptId = data[8];

            DatabaseHelper cn = new DatabaseHelper();
            Object[] params = {name, dob, gender, email, phone, pos, sal, deptId, id};

            int rs = cn.executeQuery("UPDATE employees SET name = ?, dob = ?, gender = ?, email = ?, phone = ?, pos = ?, sal = ?, deptId =? WHERE empId = ?", params);
            if (rs > 0) {
                JOptionPane.showMessageDialog(null, "Cập nhật thành công phòng có ID: " + id);
                clearText();
            } else {
                JOptionPane.showMessageDialog(null, "Không tìm thấy phòng với ID: " + id);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID không hợp lệ!");
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi trong quá trình cập nhật: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public int deleteNhanVien() {
        // ID is Auto inc
        String id = txtId.getText();

        if (id.isEmpty()) { // Kiểm tra xem ID có rỗng không
            JOptionPane.showMessageDialog(null, "Vui lòng nhập mã nhân viên cần xóa!");
            return 0;
        }

        try {
            int parsedId = Integer.parseInt(id); // Chuyển đổi ID thành số nguyên
            Object[] argv = new Object[]{parsedId};
            DatabaseHelper cn = new DatabaseHelper();

            int rs = cn.executeQuery("DELETE FROM employees WHERE empId =?", argv);
            if (rs > 0) {
                JOptionPane.showMessageDialog(null, "Xóa thành công dữ liệu id:" + id);
                clearText();
            }
            return rs;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID không hợp lệ: " + id);
            System.out.println(e);
            return 0;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Xóa thất bại dữ liệu id:" + id + ", error detail:" + e);
            System.out.println(e);
            return 0;
        }
    }

// Phương thức clearText để xóa các trường nhập liệu
    private void clearText() {
        txtId.setText("");        // Xóa mã nhân viên
        txtName.setText("");       // Xóa tên nhân viên
        txtDate.setText("");        // Xóa ngày sinh
        boxGT.setSelectedIndex(0);  // Đặt lại ComboBox về giá trị mặc định (Nam)
        txtEmail.setText("");       // Xóa email
        txtPhone.setText("");       // Xóa số điện thoại
        txtChucvu.setText("");      // Xóa chức vụ
        txtLuong.setText("");
        boxPhong.setSelectedIndex(0);
    }

    public frmNhanVien() {
        initComponents();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtDate = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        txtPhone = new javax.swing.JTextField();
        boxGT = new javax.swing.JComboBox<>();
        btThem = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtChucvu = new javax.swing.JTextField();
        btUpdate = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        txtLuong = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        boxPhong = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jscro = new javax.swing.JScrollPane();
        tbNhanvien = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Danh sách nhân viên\n");

        jLabel3.setText("Mã nhân viên:");

        jLabel4.setText("Tên nhân viên:");

        txtId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdActionPerformed(evt);
            }
        });

        jLabel5.setText("Ngày sinh:");

        jLabel6.setText("Giới tính");

        jLabel7.setText("Email");

        jLabel8.setText("Số điện thoại");

        txtPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPhoneActionPerformed(evt);
            }
        });

        boxGT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"Nam","Nu"}));
        boxGT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxGTActionPerformed(evt);
            }
        });

        btThem.setText("Thêm");
        btThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btThemActionPerformed(evt);
            }
        });

        jLabel1.setText("Chức vụ: ");

        btUpdate.setText("Cập nhật");
        btUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btUpdateActionPerformed(evt);
            }
        });

        jButton1.setText("Làm mới");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Xóa");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel2.setText("Lương: ");

        jButton3.setText("Tìm Kiếm");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel9.setText("Phòng ban: ");

        boxPhong.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {""}));
        boxPhong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxPhongActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(101, 101, 101)
                                .addComponent(jLabel6))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDate)
                                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(75, 75, 75)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtEmail)
                            .addComponent(txtPhone)
                            .addComponent(boxGT, 0, 126, Short.MAX_VALUE))
                        .addGap(58, 58, 58)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtChucvu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtLuong, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(boxPhong, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(0, 101, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addGap(97, 97, 97)
                        .addComponent(btThem)
                        .addGap(64, 64, 64)
                        .addComponent(btUpdate)
                        .addGap(57, 57, 57)
                        .addComponent(jButton1)
                        .addGap(80, 80, 80)
                        .addComponent(jButton2)))
                .addGap(51, 51, 51))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6)
                    .addComponent(boxGT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(txtChucvu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel7)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(boxPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btThem)
                    .addComponent(btUpdate)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        tbNhanvien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã nhân viên", "Tên nhân viên", "Ngày sinh", "Giới tính", "Email", "Số điện thoại", "Chức vụ", "Lương", "Phòng Ban"
            }
        ));
        tbNhanvien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbNhanvienMouseClicked(evt);
            }
        });
        jscro.setViewportView(tbNhanvien);

        jScrollPane1.setViewportView(jscro);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbNhanvienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbNhanvienMouseClicked
        // TODO add your handling code here:
        int i = tbNhanvien.getSelectedRow();
        if (i >= 0 && tbNhanvien.getValueAt(i, 0) != null) {
            String id = tbNhanvien.getValueAt(i, 0).toString();
            String name = tbNhanvien.getValueAt(i, 1).toString();
            String dob = tbNhanvien.getValueAt(i, 2).toString();
            String gender = tbNhanvien.getValueAt(i, 3).toString();
            String email = tbNhanvien.getValueAt(i, 4).toString();
            String phone = tbNhanvien.getValueAt(i, 5).toString();
            String pos = tbNhanvien.getValueAt(i, 6).toString();
            String sal = tbNhanvien.getValueAt(i, 7).toString();
            String deptId = tbNhanvien.getValueAt(i, 8).toString();

            // Cập nhật các trường trong UI
            txtId.setText(id);
            txtName.setText(name);
            txtDate.setText(dob);
            boxGT.setSelectedItem(gender); // Giả sử cbGender là JComboBox cho gender
            txtEmail.setText(email);
            txtPhone.setText(phone);
            txtChucvu.setText(pos);
            txtLuong.setText(sal);
            for (int j = 0; j < boxPhong.getItemCount(); j++) {
                String item = boxPhong.getItemAt(j).toString();
                if (item.startsWith(deptId + " - ")) { // Kiểm tra xem ID có trùng với mục không
                    boxPhong.setSelectedIndex(j); // Chọn mục tương ứng
                    break; // Thoát khỏi vòng lặp sau khi tìm thấy
                }
            }
        }
    }//GEN-LAST:event_tbNhanvienMouseClicked

    private void boxPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxPhongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_boxPhongActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        int confirm = JOptionPane.showConfirmDialog(null,
            "Bạn có chắc chắn muốn xóa phòng này?", "Xác nhận",
            JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            deleteNhanVien();
        }
        getNhanVien();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        getNhanVien();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btUpdateActionPerformed
        // TODO add your handling code here:
        updateNhanVien();
        getNhanVien();
    }//GEN-LAST:event_btUpdateActionPerformed

    private void btThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btThemActionPerformed
        // TODO add your handling code here:
        insertNhanVien();
        getNhanVien();
    }//GEN-LAST:event_btThemActionPerformed

    private void boxGTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxGTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_boxGTActionPerformed

    private void txtPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPhoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPhoneActionPerformed

    private void txtIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> boxGT;
    private javax.swing.JComboBox<String> boxPhong;
    private javax.swing.JButton btThem;
    private javax.swing.JButton btUpdate;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jscro;
    private javax.swing.JTable tbNhanvien;
    private javax.swing.JTextField txtChucvu;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtLuong;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPhone;
    // End of variables declaration//GEN-END:variables
}
