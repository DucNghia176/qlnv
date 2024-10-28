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

        jLabel1.setText("Mã phản hồi");

        jLabel2.setText("Mã nhân viên");

        txtID.setText("jTextField1");

        txtIDNhanVien.setText("jTextField2");

        jLabel3.setText("Nội dung");

        txtPhanHoi.setColumns(20);
        txtPhanHoi.setRows(5);
        jScrollPane1.setViewportView(txtPhanHoi);

        btnAdd.setText("Thêm mới");

        btnUpdate.setText("Câp nhật");

        btnDelete.setText("Xóa");

        btnRefresh.setText("Làm mới");

        btnSearch.setText("Tìm kiếm");

        jLabel4.setText("Đánh giá");

        cbDanhGia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

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
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtID, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                    .addComponent(txtIDNhanVien))
                .addGap(80, 80, 80)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbDanhGia, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(btnAdd)
                .addGap(74, 74, 74)
                .addComponent(btnUpdate)
                .addGap(81, 81, 81)
                .addComponent(btnDelete)
                .addGap(87, 87, 87)
                .addComponent(btnRefresh)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSearch)
                .addGap(47, 47, 47))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2)
                .addContainerGap())
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
