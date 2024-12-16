package view;

import java.awt.event.*;
import java.awt.Frame;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.table.DefaultTableModel;

public class FormLihatPetugas extends JDialog {
  private final String judulKolomTabelPetugas[] = {"Kode","Nama Petugas"};
  
  private String kodePetugasDipilih="";
  
  public FormLihatPetugas(Frame parent, boolean modal){
    super(parent, modal);
    initComponents();
  }
  
  private void initComponents(){
    setTitle("Data Petugas");
	setSize(400,300);
	setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
	setAlwaysOnTop(true);
	
	Dimension dimensi = Toolkit.getDefaultToolkit().getScreenSize(); 
    setLocation((dimensi.width-getWidth())/2,(dimensi.height-getHeight())/2);
	
	panel = new JPanel();
	panel.setLayout(null);
	
	petugasTableModel = new DefaultTableModel(null,judulKolomTabelPetugas);
	petugasTable = new JTable(){
	                      public boolean isCellEditable(int rowIndex, int colIndex) {
						    return false; //Disable Editing 
						  }
	                    };
	petugasScrollPane = new JScrollPane();
	
	pilihButton = new JButton("Pilih");
	tutupButton = new JButton("Tutup");
	
	petugasTable.setModel(petugasTableModel);
	petugasTable.getColumnModel().getColumn(0).setMinWidth(80);
    petugasTable.getColumnModel().getColumn(0).setPreferredWidth(80);
    petugasTable.getColumnModel().getColumn(0).setMaxWidth(80);
	
	petugasScrollPane.getViewport().add(petugasTable);
	panel.add(petugasScrollPane);
	panel.add(pilihButton);
	panel.add(tutupButton);
	
	this.add(panel);
	
	petugasScrollPane.setBounds(0,0,380,200);
	pilihButton.setBounds(100,230,100,25);
	tutupButton.setBounds(240,230,100,25);
	
	addWindowListener(new WindowAdapter() {
            public void windowActivated(WindowEvent evt) {
                formWindowActivated(evt);
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
  
  public String getKodePetugasDipilih(){
    return kodePetugasDipilih;
  }
  
  public void tampilkanData(Object[][] petugasList){
    petugasTableModel.setRowCount(0);
    if ((petugasList != null) && (petugasList.length > 0)){
	  for (int i=0; i<petugasList.length; i++){
	    petugasTableModel.addRow(petugasList[i]);
	  }
	}
  }
  
  private void formWindowActivated(WindowEvent evt){
    kodePetugasDipilih = "";
  }
  
  private void pilihButtonActionPerformed(ActionEvent evt){
    if (petugasTable.getSelectedRowCount() > 0){
	  kodePetugasDipilih = petugasTable.getValueAt(petugasTable.getSelectedRow(), 0).toString();
	  dispose();
	} else {
	  JOptionPane.showMessageDialog(this, "Belum ada yang dipilih");
	}
  }
  
  private void tutupButtonActionPerformed(ActionEvent evt){
    dispose();
  }
  
  private JPanel panel;
  private DefaultTableModel petugasTableModel;
  private JTable petugasTable;
  private JScrollPane petugasScrollPane;
  private JButton pilihButton;
  private JButton tutupButton;
}  