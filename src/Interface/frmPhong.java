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
public class frmPhong extends javax.swing.JInternalFrame {

    public frmPhong(frmMain aThis) {
        initComponents();
        getPhong();
    }

    /**
     * Creates new form frmPhong
     */
     public void getPhong() {

        try {

            DefaultTableModel dt = (DefaultTableModel) tbPhong.getModel();
            dt.setRowCount(0);
            DatabaseHelper cn = new DatabaseHelper();
            // Create a JTable model to store the retrieved data
            System.out.println("Connected SQL Servers success");
            
            Object[] argv = new Object[0];
            try (ResultSet resultSet = cn.selectQuery("SELECT *  FROM departments", argv)) {
                // Create a JTable model to store the retrieved data
                System.out.println("Ket noi ok" + resultSet);
                while (resultSet.next()) {
                    Vector v = new Vector();
                    v.add(resultSet.getString("deptId")); // id
                    v.add(resultSet.getString("deptName"));// name                   
                    dt.addRow(v);

                }
                cn.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi trong quá trình lấy dữ liệu: " + e.getMessage());
        }

    }   
    
    public int insertPhong() {
        // ID is Auto inc
        String id = txtID.getText().trim();
        String name = txtName.getText().trim();       
             
        if (id.isEmpty() || !id.matches("\\d+")) {
        JOptionPane.showMessageDialog(null, "Vui lòng nhập mã phòng hợp lệ!");
        return 0;
        }
        Object[] argv = new Object[2];
        argv[0] = Integer.parseInt(id);
        argv[1] = name; 
        
        try {

            DatabaseHelper cn = new DatabaseHelper();
            int rs = cn.executeQuery("INSERT INTO departments (deptId,deptName) VALUES (?,?)", argv);
            if (rs > 0) {
                JOptionPane.showMessageDialog(null, "Thêm mới thành công dữ liệu id:"+id);
                clearText();
            }
            return rs;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Thêm mới thất bại dữ liệu id:"+id+ e);                
            System.out.println(e);
            return 0;
        }

    }
    private String[] getDataFromUI() {
        
    String id = txtID.getText().trim();  // Lấy và xóa khoảng trắng đầu/cuối
    String name = txtName.getText().trim();

    // Trả về mảng với các giá trị lấy từ giao diện
    return new String[]{id, name};
    }

    public void updatePhong() {
        try {
            String[] data = getDataFromUI();

            if (data.length < 2) {
                JOptionPane.showMessageDialog(null, "Dữ liệu không đủ để cập nhật!");
                return;
            }

            int id = Integer.parseInt(data[0]);
            String name = data[1];

            DatabaseHelper cn = new DatabaseHelper();
            Object[] params = {name, id};

            int rs = cn.executeQuery("UPDATE departments SET deptName = ? WHERE deptId = ?", params);
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

    public int deletePhong() {
        // ID is Auto inc
        String id = txtID.getText();     
        
        if (id.isEmpty()) { // Kiểm tra xem ID có rỗng không
        JOptionPane.showMessageDialog(null, "Vui lòng nhập mã phòng cần xóa!");
        return 0;
        }

        try {
            int parsedId = Integer.parseInt(id); // Chuyển đổi ID thành số nguyên
            Object[] argv = new Object[]{parsedId};
            DatabaseHelper cn = new DatabaseHelper();
            
            int rs = cn.executeQuery("DELETE FROM departments WHERE deptId =?", argv);
            if (rs > 0) {
                JOptionPane.showMessageDialog(null, "Xóa thành công dữ liệu id:"+id);
                clearText();
            }            
            return rs;
        } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "ID không hợp lệ: " + id);
        System.out.println(e);
        return 0;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Xóa thất bại dữ liệu id:"+id +", error detail:"+ e );                 
            System.out.println(e);
            return 0;
        }

    }
    public void clearText() {
        txtID.setText("");
        txtName.setText("");      
    }
    
    public frmPhong() {
        initComponents();
        getPhong();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnExit = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbPhong = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnLoadData = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        btnExit.setText("Thoát");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        tbPhong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Mã Phòng", "Tên Phòng"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tbPhong.setDragEnabled(true);
        tbPhong.setShowGrid(true);
        tbPhong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPhongMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbPhong);

        jScrollPane2.setViewportView(jScrollPane1);

        jLabel2.setText("Mã Phòng");

        jLabel4.setText("Tên Phòng");

        txtID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDActionPerformed(evt);
            }
        });

        btnAdd.setText("Thêm mới");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setText("Cập nhật");
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

        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtName)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 288, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLoadData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(45, 45, 45)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(86, 86, 86))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAdd)
                            .addComponent(btnUpdate))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnLoadData)
                                    .addComponent(btnDelete)))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(98, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(420, 420, 420)
                .addComponent(btnExit)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane2)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExit)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        insertPhong();
        getPhong();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        // TODO add your handling code here:
       this.dispose();
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        int confirm = JOptionPane.showConfirmDialog(null, 
        "Bạn có chắc chắn muốn xóa phòng này?", "Xác nhận", 
        JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            deletePhong();
        }
        getPhong();
    }//GEN-LAST:event_btnDeleteActionPerformed
 
    private void tbPhongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPhongMouseClicked
        //mouse click load text field to data
        int i = tbPhong.getSelectedRow();
        if (i >= 0 && tbPhong.getValueAt(i, 0) != null) {
            String id = tbPhong.getValueAt(i, 0).toString();
            String name = tbPhong.getValueAt(i, 1).toString();
            txtID.setText(id);
            txtName.setText(name);
        }
    }//GEN-LAST:event_tbPhongMouseClicked

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
         updatePhong();
        getPhong();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnLoadDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadDataActionPerformed
        // TODO add your handling code here:
        getPhong();
    }//GEN-LAST:event_btnLoadDataActionPerformed

    private void txtIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnLoadData;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbPhong;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}
