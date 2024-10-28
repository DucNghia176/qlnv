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
 * @author MY PC
 */
public class frmTinhLuong extends javax.swing.JInternalFrame {

    public void getPayrollData() {
        try {
            DefaultTableModel dt = (DefaultTableModel) tbTinhLuong.getModel();
            dt.setRowCount(0);

            DatabaseHelper cn = new DatabaseHelper();
            System.out.println("Connected to SQL server successfully");
            Object[] argv = new Object[0];

            try (ResultSet resultSet = cn.selectQuery("SELECT p.payId, p.empId, e.name, p.amount, p.payDate, d.deptName "
                    + "FROM payroll p "
                    + "JOIN employees e ON p.empId = e.empId "
                    + "JOIN departments d ON e.deptId = d.deptId", argv)) {
                while (resultSet.next()) {
                    Vector v = new Vector();
                    v.add(resultSet.getString("payId"));   // Mã lương
                    v.add(resultSet.getString("empId"));   // Mã nhân viên
                    v.add(resultSet.getString("name"));    // Tên nhân viên
                    v.add(resultSet.getString("amount"));   // Số tiền lương
                    v.add(resultSet.getString("payDate"));  // Ngày trả lương
                    v.add(resultSet.getString("deptName")); // Tên phòng ban
                    dt.addRow(v);
                }
                cn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy dữ liệu lương: " + e.getMessage());
        }
    }

    //  Lấy dữ liệu nhân viên từ cơ sở dữ liệu cho ComboBox mã nhân viên
//    public void loadEmployeeIdsToComboBox() {
//        try {
//            DatabaseHelper cn = new DatabaseHelper();
//            ResultSet resultSet = cn.selectQuery("SELECT empId, name FROM employees", new Object[0]);
//
//            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
//            while (resultSet.next()) {
//                String empId = resultSet.getString("empId");
//                String empName = resultSet.getString("name");
//                model.addElement(empId + " - " + empName);
//            }
//
//            // Thiết lập model cho ComboBox mã nhân viên
//            cbIDNhanVien.setModel(model);
//
//            // Đóng kết nối
//            cn.close();
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Lỗi tải dữ liệu nhân viên: " + e.getMessage());
//        }
//    }
    /**
     * Creates new form frmTinhLuong
     */
    public frmTinhLuong() {
        initComponents();
        getPayrollData();
    }

    // Hàm insert
    public int insertTinhLuong() {
        // Lấy mã lương từ JTextField
        String payId = txtID.getText().trim(); // Giả sử có trường nhập liệu txtPayId
        String empId = txtIDNhanVien.getText().trim();
        String payDate = txtNgayTraLuong.getText().trim();
        String amount = txtTienLuong.getText().trim();

        // Kiểm tra xem các trường nhập liệu có bị bỏ trống không
        if (payId.isEmpty() || empId.isEmpty() || payDate.isEmpty() || amount.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin!");
            return 0;
        }

        // Kiểm tra xem amount và payId có phải là số hợp lệ không
        try {
            Integer.parseInt(payId); // Kiểm tra giá trị payId
            Double.parseDouble(amount); // Kiểm tra giá trị amount
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Dữ liệu không hợp lệ! Vui lòng kiểm tra lại.");
            return 0;
        }

        // Câu lệnh SQL để thêm mới dữ liệu vào bảng payroll
        Object[] argv = {payId, empId, payDate, amount};
        try {
            DatabaseHelper cn = new DatabaseHelper();
            int rs = cn.executeQuery("INSERT INTO payroll (payId, empId, payDate, amount) VALUES (?, ?, ?, ?)", argv);

            if (rs > 0) {
                JOptionPane.showMessageDialog(null, "Thêm mới bảng tính lương thành công!");
                clearPayrollText();  // Giả định có hàm này để xóa các trường nhập liệu
            }
            return rs;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Thêm mới thất bại: " + e.getMessage());
            return 0;
        }
    }

    // Phương thức clearPayrollText để xóa các trường nhập liệu sau khi thêm thành công
    private void clearPayrollText() {
        // cbIDNhanVien.setSelectedIndex(0); // Đặt lại ComboBox về vị trí đầu tiên
        txtIDNhanVien.setText("");
        txtNgayTraLuong.setText("");         // Xóa ngày trả lương
        txtTienLuong.setText("");          // Xóa số tiền lương
    }

    // Hàm cập nhật tính lương
    public int updateTinhLuong() {
        // Lấy dữ liệu từ các trường nhập liệu
        String payId = txtID.getText().trim();  // Mã lương
        String empId = txtIDNhanVien.getText().trim(); // Mã nhân viên từ JTextField
        String payDate = txtNgayTraLuong.getText().trim(); // Ngày trả lương
        String amount = txtTienLuong.getText().trim(); // Số tiền lương

        // Kiểm tra xem các trường có rỗng hay không
        if (payId.isEmpty() || empId.isEmpty() || payDate.isEmpty() || amount.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin!");
            return 0;
        }

        Object[] argv = {empId, payDate, amount, payId}; // Đối số cho câu truy vấn
        try {
            DatabaseHelper cn = new DatabaseHelper();
            // Câu truy vấn SQL để cập nhật
            int rs = cn.executeQuery("UPDATE payroll SET empId = ?, payDate = ?, amount = ? WHERE payId = ?", argv);

            if (rs > 0) {
                JOptionPane.showMessageDialog(null, "Cập nhật bảng tính lương thành công!");
                clearPayrollText(); // Gọi hàm để xóa các trường nhập liệu
            }
            return rs;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Cập nhật thất bại: " + e.getMessage());
            return 0;
        }
    }

    // Hàm xóa tính lương
    public int deleteTinhLuong() {
        String payId = txtID.getText().trim(); // Lấy mã lương từ trường nhập liệu

        // Kiểm tra xem mã lương có rỗng hay không
        if (payId.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn mã lương để xóa!");
            return 0;
        }

        // Xác nhận xóa bản ghi
        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa bản ghi này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return 0; // Nếu người dùng không xác nhận, thoát hàm
        }

        Object[] argv = {payId}; // Đối số cho câu truy vấn
        try {
            DatabaseHelper cn = new DatabaseHelper();
            // Câu truy vấn SQL để xóa
            int rs = cn.executeQuery("DELETE FROM payroll WHERE payId = ?", argv);

            if (rs > 0) {
                JOptionPane.showMessageDialog(null, "Xóa bản ghi tính lương thành công!");
                clearPayrollText(); // Gọi hàm để xóa các trường nhập liệu
                getPayrollData(); // Cập nhật lại bảng dữ liệu
            }
            return rs;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Xóa thất bại: " + e.getMessage());
            return 0;
        }
    }

    // Hàm Tìm kiếm 
    public void searchPayrollData() {
        String empId = txtIDNhanVien.getText().trim(); // Lấy mã nhân viên từ trường văn bản

        try {
            DefaultTableModel dt = (DefaultTableModel) tbTinhLuong.getModel();
            dt.setRowCount(0); // Xóa dữ liệu cũ trong bảng

            DatabaseHelper cn = new DatabaseHelper();

            // Câu truy vấn SQL
            String query = "SELECT p.payId, p.empId, e.name, p.amount, p.payDate, d.deptName "
                    + "FROM payroll p "
                    + "JOIN employees e ON p.empId = e.empId "
                    + "JOIN departments d ON e.deptId = d.deptId "
                    + "WHERE e.empId = ? OR e.name LIKE ?";

            Object[] argv = {empId, "%" + empId + "%"}; // Sử dụng % để tìm kiếm tên nhân viên

            try (ResultSet resultSet = cn.selectQuery(query, argv)) {
                while (resultSet.next()) {
                    Vector<Object> v = new Vector<>();
                    v.add(resultSet.getString("payId"));   // Mã lương
                    v.add(resultSet.getString("empId"));   // Mã nhân viên
                    v.add(resultSet.getString("name"));     // Tên nhân viên
                    v.add(resultSet.getString("amount"));   // Số tiền lương
                    v.add(resultSet.getString("payDate"));  // Ngày trả lương
                    v.add(resultSet.getString("deptName")); // Tên phòng ban
                    dt.addRow(v);
                }
            }
            cn.close(); // Đóng kết nối
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm dữ liệu lương: " + e.getMessage());
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

        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbTinhLuong = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        txtTienLuong = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtNgayTraLuong = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtNameNhanVien = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtPhongBan = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtIDNhanVien = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Bảng Tính Lương");

        tbTinhLuong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã Lương", "Mã Nhân Viên", "Tên Nhân Viên", "Số Tiền Lương", "Ngày Trả Lương", "Phòng Ban"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tbTinhLuong.setDragEnabled(true);
        tbTinhLuong.setShowGrid(true);
        tbTinhLuong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbTinhLuongMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbTinhLuong);

        jScrollPane2.setViewportView(jScrollPane1);

        jLabel4.setText("Ngày trả lương");

        jLabel5.setText("Tên nhân viên");

        jLabel6.setText("Phòng ban");

        jLabel1.setText("Mã lương");

        jLabel2.setText("Mã nhân viện");

        jLabel3.setText("Số tiền lương");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtID, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                    .addComponent(txtIDNhanVien))
                .addGap(63, 63, 63)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTienLuong)
                    .addComponent(txtNgayTraLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(61, 61, 61)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtNameNhanVien)
                    .addComponent(txtPhongBan, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTienLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtNameNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(txtNgayTraLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtPhongBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIDNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        btnAdd.setText("Thêm mới");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setText("Cập nhật");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnRefresh.setText("Làm mới");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        btnSearch.setText("Tìm kiếm");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(btnAdd)
                .addGap(61, 61, 61)
                .addComponent(btnUpdate)
                .addGap(59, 59, 59)
                .addComponent(btnDelete)
                .addGap(72, 72, 72)
                .addComponent(btnRefresh)
                .addGap(64, 64, 64)
                .addComponent(btnSearch)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAdd)
                        .addComponent(btnRefresh)
                        .addComponent(btnSearch)
                        .addComponent(btnDelete)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbTinhLuongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTinhLuongMouseClicked
        // mouse click load text field to data
        int i = tbTinhLuong.getSelectedRow();
        if (i >= 0 && tbTinhLuong.getValueAt(i, 0) != null) {
            // Lấy dữ liệu từ các cột trong hàng được chọn
            String payId = tbTinhLuong.getValueAt(i, 0).toString();         // Mã lương
            String empId = tbTinhLuong.getValueAt(i, 1).toString();         // Mã nhân viên
            String empName = tbTinhLuong.getValueAt(i, 2).toString();       // Tên nhân viên
            String amount = tbTinhLuong.getValueAt(i, 3).toString();        // Số tiền lương
            String payDate = tbTinhLuong.getValueAt(i, 4).toString();       // Ngày trả lương
            String deptName = tbTinhLuong.getValueAt(i, 5).toString();      // Phòng ban

            // Đặt mã lương vào trường txtID
            txtID.setText(payId);

            // Đặt ngày trả lương và số tiền lương
            txtNgayTraLuong.setText(payDate);
            txtTienLuong.setText(amount);

            // Cập nhật thông tin tên nhân viên và phòng ban
            txtNameNhanVien.setText(empName);
            txtPhongBan.setText(deptName);

            // Đặt mã nhân viên vào trường txtIDNhanVien
            txtIDNhanVien.setText(empId);
        }
    }//GEN-LAST:event_tbTinhLuongMouseClicked

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        // Gọi chức năng thêm
        insertTinhLuong();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        clearPayrollText(); // Xóa các trường nhập liệu
        getPayrollData(); // Làm mới bảng dữ liệu
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        updateTinhLuong();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        // Gọi chức năng xóa
        deleteTinhLuong(); // Giả định bạn đã tạo phương thức deleteTinhLuong()
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        searchPayrollData();
    }//GEN-LAST:event_btnSearchActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbTinhLuong;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtIDNhanVien;
    private javax.swing.JTextField txtNameNhanVien;
    private javax.swing.JTextField txtNgayTraLuong;
    private javax.swing.JTextField txtPhongBan;
    private javax.swing.JTextField txtTienLuong;
    // End of variables declaration//GEN-END:variables
}
