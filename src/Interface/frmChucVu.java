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
public class frmChucVu extends javax.swing.JInternalFrame {

    /**
     * Creates new form frmChucVu
     */
    public frmChucVu() {
        initComponents();
        getChucVu();
    }

    public void getChucVu() {
        try {
            DefaultTableModel dt = (DefaultTableModel) tbChucVu.getModel();
            dt.setRowCount(0);

            DatabaseHelper cn = new DatabaseHelper();
            Object[] argv = new Object[0];

            try (ResultSet resultSet = cn.selectQuery("SELECT * FROM positions", argv)) {
                while (resultSet.next()) {
                    Vector v = new Vector();
                    v.add(resultSet.getString("posId"));
                    v.add(resultSet.getString("posName"));
                    // Thêm các trường khác nếu có
                    dt.addRow(v);
                }
                cn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi trong quá trình lấy dữ liệu: " + e.getMessage());
        }
    }

    public int insertChucVu() {
        String posId = txtID.getText().trim();
        String posName = txtName.getText().trim();

        if (posId.isEmpty() || posName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin!");
            return 0;
        }

        Object[] argv = {posId, posName};
        try {
            DatabaseHelper cn = new DatabaseHelper();
            int rs = cn.executeQuery("INSERT INTO positions (posId, posName) VALUES (?, ?)", argv);

            if (rs > 0) {
                JOptionPane.showMessageDialog(null, "Thêm mới chức vụ thành công!");
                clearText();
            }
            return rs;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Thêm mới thất bại: " + e.getMessage());
            return 0;
        }
    }

    public void updateChucVu() {
        String posId = txtID.getText().trim();
        String posName = txtName.getText().trim();

        if (posId.isEmpty() || posName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin!");
            return;
        }

        Object[] params = {posName, posId};

        try {
            DatabaseHelper cn = new DatabaseHelper();
            int rs = cn.executeQuery("UPDATE positions SET posName = ? WHERE posId = ?", params);
            if (rs > 0) {
                JOptionPane.showMessageDialog(null, "Cập nhật thành công chức vụ có ID: " + posId);
                clearText();
            } else {
                JOptionPane.showMessageDialog(null, "Không tìm thấy chức vụ với ID: " + posId);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi trong quá trình cập nhật: " + e.getMessage());
        }
    }

    public int deleteChucVu() {
        String posId = txtID.getText().trim();

        if (posId.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập mã chức vụ cần xóa!");
            return 0;
        }

        try {
            int parsedPosId = Integer.parseInt(posId);
            Object[] argv = {parsedPosId};
            DatabaseHelper cn = new DatabaseHelper();

            int rs = cn.executeQuery("DELETE FROM positions WHERE posId = ?", argv);
            if (rs > 0) {
                JOptionPane.showMessageDialog(null, "Xóa thành công chức vụ có ID: " + posId);
                clearText();
            }
            return rs;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID không hợp lệ: " + posId);
            return 0;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Xóa thất bại: " + e.getMessage());
            return 0;
        }
    }

    // Phương thức clearText để xóa các trường nhập liệu trong frmChucVu
    private void clearText() {
        txtID.setText(""); // Xóa mã chức vụ
        txtName.setText("");
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
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbChucVu = new javax.swing.JTable();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnLoadData = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        txtName = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Bảng chức vụ");

        jLabel1.setText("Mã chức vụ");

        jLabel2.setText("Tên chức vụ");

        txtID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDActionPerformed(evt);
            }
        });

        tbChucVu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Mã Chức Vụ", "Tên Chức Vụ"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tbChucVu.setDragEnabled(true);
        tbChucVu.setShowGrid(true);
        tbChucVu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbChucVuMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbChucVu);

        jScrollPane2.setViewportView(jScrollPane1);

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

        btnLoadData.setText("Làm mới");
        btnLoadData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadDataActionPerformed(evt);
            }
        });

        btnSearch.setText("Tìm kiếm");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnExit.setText("Thoát");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(145, 145, 145)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnLoadData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(78, 78, 78)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnDelete)
                            .addComponent(btnExit))
                        .addGap(47, 47, 47))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 824, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(37, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdd)
                    .addComponent(btnUpdate)
                    .addComponent(btnDelete))
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(btnLoadData)
                    .addComponent(btnSearch)
                    .addComponent(btnExit)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDActionPerformed

    private void tbChucVuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbChucVuMouseClicked
        // mouse click load text field to data
        int i = tbChucVu.getSelectedRow();
        if (i >= 0 && tbChucVu.getValueAt(i, 0) != null) {
            String id = tbChucVu.getValueAt(i, 0).toString();
            String name = tbChucVu.getValueAt(i, 1).toString();
            txtID.setText(id);
            txtName.setText(name); // Đặt mục được chọn trong JComboBox
        }
    }//GEN-LAST:event_tbChucVuMouseClicked

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        insertChucVu();
        getChucVu();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        updateChucVu();
        getChucVu();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        int confirm = JOptionPane.showConfirmDialog(null,
            "Bạn có chắc chắn muốn xóa phòng này?", "Xác nhận",
            JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            deleteChucVu();
        }
        getChucVu();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnLoadDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadDataActionPerformed
        // TODO add your handling code here:
        getChucVu();
    }//GEN-LAST:event_btnLoadDataActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnExitActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnLoadData;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbChucVu;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}
