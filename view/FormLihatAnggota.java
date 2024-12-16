package view;

import java.awt.event.*;
import java.awt.Frame;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.table.DefaultTableModel;

public class FormLihatAnggota extends JDialog {
  private final String judulKolomTabelAnggota[] = {"Kode","Nama Anggota"};
  
  public static String kodeDipilih="";
  
  public FormLihatAnggota(Frame parent, boolean modal){
    super(parent, modal);
    initComponents();
  }
  
  private void initComponents(){
    setTitle("Data Anggota");
	setSize(400,300);
	setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
	setAlwaysOnTop(true);
	
	Dimension dimensi = Toolkit.getDefaultToolkit().getScreenSize(); 
    setLocation((dimensi.width-getWidth())/2,(dimensi.height-getHeight())/2);
	
	panel = new JPanel();
	panel.setLayout(null);
	
	anggotaTableModel = new DefaultTableModel(null,judulKolomTabelAnggota);
	anggotaTable = new JTable(){
	                      public boolean isCellEditable(int rowIndex, int colIndex) {
						    return false; //Disable Editing 
						  }
	                    };
	anggotaScrollPane = new JScrollPane();
	
	pilihButton = new JButton("Pilih");
	tutupButton = new JButton("Tutup");
	
	anggotaTable.setModel(anggotaTableModel);
	anggotaTable.getColumnModel().getColumn(0).setMinWidth(80);
    anggotaTable.getColumnModel().getColumn(0).setPreferredWidth(80);
    anggotaTable.getColumnModel().getColumn(0).setMaxWidth(80);
	
	anggotaScrollPane.getViewport().add(anggotaTable);
	panel.add(anggotaScrollPane);
	panel.add(pilihButton);
	panel.add(tutupButton);
	
	this.add(panel);
	
	anggotaScrollPane.setBounds(0,0,380,200);
	pilihButton.setBounds(100,230,100,25);
	tutupButton.setBounds(240,230,100,25);
	
	addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
	
    pilihButton.addActionListener(new ActionListener(){
	  public void actionPerformed(ActionEvent evt){
	    pilihButtonActionPerformed(evt);
	  }
	});	
	
	tutupButton.addActionListener(new ActionListener(){
	  public void actionPerformed(ActionEvent evt){
	    tutupButtonActionPerformed(evt);
	  }
	});
  }
  
  public void tampilkanData(Object[][] list){
    anggotaTableModel.setRowCount(0);
    if ((list != null) && (list.length > 0)) {
	  for (int i=0; i<list.length; i++){
	    anggotaTableModel.addRow(list[i]);
	  }
	}
  }
  
  public String getKodeDipilih(){
    return kodeDipilih;
  }
  
  private void formWindowOpened(WindowEvent evt){
    kodeDipilih= "";
  }
  
  private void pilihButtonActionPerformed(ActionEvent evt){
    if (anggotaTable.getSelectedRowCount() > 0){
	  kodeDipilih = anggotaTable.getValueAt(anggotaTable.getSelectedRow(), 0).toString();
	  dispose();
	} else {
	  JOptionPane.showMessageDialog(this, "Belum ada yang dipilih");
	}
  }
  
  private void tutupButtonActionPerformed(ActionEvent evt){
    dispose();
  }
  
  private JPanel panel;
  private DefaultTableModel anggotaTableModel;
  private JTable anggotaTable;
  private JScrollPane anggotaScrollPane;
  private JButton pilihButton;
  private JButton tutupButton;
}  