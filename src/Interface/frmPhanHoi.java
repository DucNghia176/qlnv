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
public class frmPhanHoi extends javax.swing.JInternalFrame {

    public void getFeedbackData() {
        try {
            DefaultTableModel dt = (DefaultTableModel) tbPhanHoi.getModel();
            dt.setRowCount(0);

            DatabaseHelper cn = new DatabaseHelper();
            System.out.println("Kết nối thành công đến máy chủ SQL");
            Object[] argv = new Object[0];

            try (ResultSet resultSet = cn.selectQuery("SELECT f.fbId, f.empId, e.name, f.comments, f.rating "
                    + "FROM feedback f "
                    + "JOIN employees e ON f.empId = e.empId", argv)) {
                while (resultSet.next()) {
                    Vector v = new Vector();
                    v.add(resultSet.getString("fbId"));      // Mã phản hồi
                    v.add(resultSet.getString("empId"));     // Mã nhân viên
                    v.add(resultSet.getString("name"));      // Tên nhân viên
                    v.add(resultSet.getString("comments"));  // Nội dung phản hồi
                    v.add(resultSet.getString("rating"));    // Đánh giá
                    dt.addRow(v);
                }
                cn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy dữ liệu phản hồi: " + e.getMessage());
        }
    }

    public frmPhanHoi() {
        initComponents();
        getFeedbackData();
    }

    // Hàm insert
    // Hàm insertFeedback
    public int insertFeedback() {
        // Lấy dữ liệu từ JTextField và ComboBox
        String fbId = txtID.getText().trim(); // Giả sử có trường nhập liệu txtFeedbackID
        String empId = txtIDNhanVien.getText().trim();
        String comments = txtPhanHoi.getText().trim();
        String rating = cbDanhGia.getSelectedItem().toString().trim(); // ComboBox để chọn đánh giá từ 1-5

        // Kiểm tra xem các trường nhập liệu có bị bỏ trống không
        if (fbId.isEmpty() || empId.isEmpty() || comments.isEmpty() || rating.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin!");
            return 0;
        }

        // Kiểm tra xem fbId và rating có phải là số hợp lệ không
        try {
            int fbIdValue = Integer.parseInt(fbId); // Kiểm tra giá trị fbId
            int empIdValue = Integer.parseInt(empId); // Kiểm tra giá trị empId
            int ratingValue = Integer.parseInt(rating); // Kiểm tra giá trị rating
            if (ratingValue < 1 || ratingValue > 5) {
                JOptionPane.showMessageDialog(null, "Đánh giá phải nằm trong khoảng từ 1 đến 5!");
                return 0;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Dữ liệu không hợp lệ! Vui lòng kiểm tra lại.");
            return 0;
        }

        // Câu lệnh SQL để thêm mới dữ liệu vào bảng feedback
        Object[] argv = {fbId, empId, comments, rating};
        try {
            DatabaseHelper cn = new DatabaseHelper();
            int rs = cn.executeQuery("INSERT INTO feedback (fbId, empId, comments, rating) VALUES (?, ?, ?, ?)", argv);

            if (rs > 0) {
                JOptionPane.showMessageDialog(null, "Thêm mới phản hồi thành công!");
                clearFeedbackText(); // Giả định có hàm này để xóa các trường nhập liệu
            }
            return rs;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Thêm mới thất bại: " + e.getMessage());
            return 0;
        }
    }

    // Phương thức clearFeedbackText để xóa các trường nhập liệu sau khi thêm thành công
    private void clearFeedbackText() {
        txtID.setText("");       // Xóa mã phản hồi
        txtIDNhanVien.setText("");       // Xóa mã nhân viên
        txtPhanHoi.setText("");         // Xóa nội dung phản hồi
        cbDanhGia.setSelectedIndex(0); // Đặt lại ComboBox về vị trí đầu tiên
    }

    // Hàm updateFeedback
    public int updateFeedback() {
        // Lấy dữ liệu từ các trường nhập liệu
        String fbId = txtID.getText().trim();            // Mã phản hồi từ JTextField
        String empId = txtIDNhanVien.getText().trim();   // Mã nhân viên
        String comments = txtPhanHoi.getText().trim();   // Nội dung phản hồi
        String rating = cbDanhGia.getSelectedItem().toString().trim(); // Đánh giá từ ComboBox

        // Kiểm tra xem các trường có rỗng hay không
        if (fbId.isEmpty() || empId.isEmpty() || comments.isEmpty() || rating.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin!");
            return 0;
        }

        // Kiểm tra xem rating có phải là số hợp lệ từ 1 đến 5 không
        try {
            int ratingValue = Integer.parseInt(rating);
            if (ratingValue < 1 || ratingValue > 5) {
                JOptionPane.showMessageDialog(null, "Đánh giá phải nằm trong khoảng từ 1 đến 5!");
                return 0;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Đánh giá phải là số!");
            return 0;
        }

        // Mảng đối số cho câu truy vấn
        Object[] argv = {empId, comments, rating, fbId};
        try {
            DatabaseHelper cn = new DatabaseHelper();
            // Câu truy vấn SQL để cập nhật bảng feedback
            int rs = cn.executeQuery("UPDATE feedback SET empId = ?, comments = ?, rating = ? WHERE fbId = ?", argv);

            if (rs > 0) {
                JOptionPane.showMessageDialog(null, "Cập nhật phản hồi thành công!");
                clearFeedbackText(); // Gọi hàm để xóa các trường nhập liệu
            }
            return rs;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Cập nhật thất bại: " + e.getMessage());
            return 0;
        }
    }

    // Hàm deleteFeedback
    public int deleteFeedback() {
        String fbId = txtID.getText().trim(); // Lấy mã phản hồi từ trường nhập liệu

        // Kiểm tra xem mã phản hồi có rỗng hay không
        if (fbId.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn mã phản hồi để xóa!");
            return 0;
        }

        // Xác nhận xóa bản ghi
        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa phản hồi này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return 0; // Nếu người dùng không xác nhận, thoát hàm
        }

        Object[] argv = {fbId}; // Đối số cho câu truy vấn
        try {
            DatabaseHelper cn = new DatabaseHelper();
            // Câu truy vấn SQL để xóa phản hồi
            int rs = cn.executeQuery("DELETE FROM feedback WHERE fbId = ?", argv);

            if (rs > 0) {
                JOptionPane.showMessageDialog(null, "Xóa phản hồi thành công!");
                clearFeedbackText(); // Gọi hàm để xóa các trường nhập liệu
                getFeedbackData(); // Cập nhật lại bảng dữ liệu
            }
            return rs;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Xóa thất bại: " + e.getMessage());
            return 0;
        }
    }

    // Hàm searchFeedbackByEmpId
    public void searchFeedbackByEmpId() {
        String empId = txtIDNhanVien.getText().trim(); // Lấy mã nhân viên từ trường nhập liệu

        // Kiểm tra xem mã nhân viên có rỗng hay không
        if (empId.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập mã nhân viên để tìm kiếm!");
            return;
        }

        // Mảng đối số cho câu truy vấn
        Object[] argv = {empId};
        try {
            DatabaseHelper cn = new DatabaseHelper();
            // Thực hiện truy vấn SQL để tìm kiếm phản hồi theo mã nhân viên
            ResultSet rs = cn.selectQuery("SELECT * FROM feedback WHERE empId = ?", argv);

            // Xóa dữ liệu cũ trong bảng
            DefaultTableModel model = (DefaultTableModel) tbPhanHoi.getModel();
            model.setRowCount(0);

            // Duyệt qua kết quả và thêm vào bảng
            while (rs.next()) {
                String fbId = rs.getString("fbId");
                String empName = getEmployeeNameById(empId); // Hàm giả định lấy tên nhân viên
                String comments = rs.getString("comments");
                String rating = rs.getString("rating");

                // Thêm hàng mới vào bảng
                model.addRow(new Object[]{fbId, empId, empName, comments, rating});
            }

            // Kiểm tra nếu không tìm thấy kết quả
            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Không tìm thấy phản hồi nào cho mã nhân viên: " + empId);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Tìm kiếm thất bại: " + e.getMessage());
        }
    }

// Hàm giả định lấy tên nhân viên dựa vào mã nhân viên
    private String getEmployeeNameById(String empId) {
        try {
            DatabaseHelper cn = new DatabaseHelper();
            Object[] argv = {empId};
            ResultSet rs = cn.selectQuery("SELECT name FROM employees WHERE empId = ?", argv);

            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Không xác định";
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtIDNhanVien = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtPhanHoi = new javax.swing.JTextArea();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        cbDanhGia = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbPhanHoi = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);

        jLabel1.setText("Mã phản hồi");

        jLabel2.setText("Mã nhân viên");

        jLabel3.setText("Nội dung");

        txtPhanHoi.setColumns(20);
        txtPhanHoi.setRows(5);
        jScrollPane1.setViewportView(txtPhanHoi);

        btnAdd.setText("Thêm mới");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setText("Câp nhật");
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

        btnSearch.setText("Tìm kiếm");

        jLabel4.setText("Đánh giá");

        cbDanhGia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"1","2","3","4","5"}));

        tbPhanHoi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã Phản Hồi", "Mã Nhân Viên", "Tên Nhân Viên", "Nội Dung", "Đánh Giá"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tbPhanHoi.setDragEnabled(true);
        tbPhanHoi.setShowGrid(true);
        tbPhanHoi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPhanHoiMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbPhanHoi);

        jScrollPane2.setViewportView(jScrollPane3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtID, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                            .addComponent(txtIDNhanVien)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(btnAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                        .addComponent(btnUpdate)
                        .addGap(9, 9, 9)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbDanhGia, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(btnDelete)
                        .addGap(70, 70, 70)
                        .addComponent(btnRefresh)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSearch)))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtIDNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(cbDanhGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnUpdate)
                    .addComponent(btnDelete)
                    .addComponent(btnRefresh)
                    .addComponent(btnSearch))
                .addGap(28, 28, 28)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbPhanHoiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPhanHoiMouseClicked
        // mouse click load text field to data
        // Xử lý sự kiện khi người dùng nhấn chuột vào bảng phản hồi
        int selectedRow = tbPhanHoi.getSelectedRow();
        if (selectedRow >= 0 && tbPhanHoi.getValueAt(selectedRow, 0) != null) {
            // Lấy dữ liệu từ các cột trong hàng được chọn
            String fbId = tbPhanHoi.getValueAt(selectedRow, 0).toString();      // Mã phản hồi
            String empId = tbPhanHoi.getValueAt(selectedRow, 1).toString();     // Mã nhân viên
            String empName = tbPhanHoi.getValueAt(selectedRow, 2).toString();   // Tên nhân viên
            String comments = tbPhanHoi.getValueAt(selectedRow, 3).toString();  // Nội dung phản hồi
            String rating = tbPhanHoi.getValueAt(selectedRow, 4).toString();    // Đánh giá

            // Đặt mã phản hồi vào trường txtID
            txtID.setText(fbId);

            // Cập nhật thông tin nhân viên và phản hồi
            // txtIDNhanVien.setText(empName);
            txtPhanHoi.setText(comments);

            // Đặt mã nhân viên vào trường txtIDNhanVien
            txtIDNhanVien.setText(empId);

            // Đặt đánh giá vào combobox cbDanhGia (nếu có)
            cbDanhGia.setSelectedItem(rating);
        }
    }//GEN-LAST:event_tbPhanHoiMouseClicked

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        insertFeedback();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        updateFeedback();
        getFeedbackData();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        deleteFeedback();
    }//GEN-LAST:event_btnDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cbDanhGia;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tbPhanHoi;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtIDNhanVien;
    private javax.swing.JTextArea txtPhanHoi;
    // End of variables declaration//GEN-END:variables
}
