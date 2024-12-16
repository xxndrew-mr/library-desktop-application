package view;

import model.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import java.beans.*;

public class FormPeminjaman extends JInternalFrame {
  private final String judulKolomTabelPeminjaman[] = {"Kode","Judul Buku","Pengarang","Jenis Buku"};
  private final int lebarKolomTabelPeminjaman[] = {80,200,160,160};
  private final boolean editKolomTabelPeminjaman[] = {true,false,false,false};
  
  private Peminjaman peminjaman = new Peminjaman();
  private FormLihatAnggota formLihatAnggota = new FormLihatAnggota(null,true);
  private Anggota anggota = new Anggota();
  
  public static String kodeDicari="";
  
  private Buku buku = new Buku();
  private JenisBuku jenisBuku = new JenisBuku();
  private Object[][] dataJenisBuku = jenisBuku.bacaData();
  
  public FormPeminjaman(){
    super("Form Peminjaman",true,true);	
    initComponents();	
  }
  
  private void initComponents() {
    setSize(680,420);	
	setDefaultCloseOperation(HIDE_ON_CLOSE);
	
	panel = new JPanel();
	panel.setLayout(null);
	
	noPeminjamanLabel = new JLabel("No. Peminjaman");
	
    noPeminjamanTextField = new JTextField();
	
	kodeAnggotaLabel = new JLabel("Kode Anggota");
	namaAnggotaLabel = new JLabel("Nama Anggota");
	alamatAnggotaLabel = new JLabel("Alamat Anggota");
	
	kodeAnggotaTextField = new JTextField();
	namaAnggotaTextField = new JTextField();
	alamatAnggotaTextField = new JTextField();
	
	namaAnggotaTextField.setEditable(false);
	alamatAnggotaTextField.setEditable(false);
	
	peminjamanDefaultTableModel = new DefaultTableModel(null,judulKolomTabelPeminjaman);
	peminjamanTable = new JTable(){
	                      public boolean isCellEditable(int rowIndex, int colIndex) {
						    return editKolomTabelPeminjaman[colIndex]; //Pengaturan status Editing 
						  }
	                    };
	peminjamanScrollPane = new JScrollPane();
	
	peminjamanTable.setModel(peminjamanDefaultTableModel);
	peminjamanTable.setCellSelectionEnabled(true);
	peminjamanDefaultTableModel.setRowCount(1);
	
	for (int i=0; i<lebarKolomTabelPeminjaman.length; i++){
	  peminjamanTable.getColumnModel().getColumn(i).setMinWidth(lebarKolomTabelPeminjaman[i]);
      peminjamanTable.getColumnModel().getColumn(i).setPreferredWidth(lebarKolomTabelPeminjaman[i]);
      peminjamanTable.getColumnModel().getColumn(i).setMaxWidth(lebarKolomTabelPeminjaman[i]);
	}
	
	peminjamanScrollPane.getViewport().add(peminjamanTable);
	
	noPeminjamanBaruButton = new JButton("Peminjaman Baru");
	daftarButton = new JButton("Daftar");
    simpanButton = new JButton("Simpan");    
    tutupButton = new JButton("Tutup");    
	
	noPeminjamanLabel.setBounds(30,30,100,25);
	
	noPeminjamanTextField.setBounds(140,30,100,25);
	
	kodeAnggotaLabel.setBounds(30,60,90,25);
	namaAnggotaLabel.setBounds(30,90,90,25);
	alamatAnggotaLabel.setBounds(30,120,90,25);
	
	kodeAnggotaTextField.setBounds(140,60,100,25);
	namaAnggotaTextField.setBounds(140,90,220,25);
	alamatAnggotaTextField.setBounds(140,120,320,25);
	
	peminjamanScrollPane.setBounds(30,160,600,200);
	
	daftarButton.setBounds(250,60,70,25);
	noPeminjamanBaruButton.setBounds(245,30,140,25);
	simpanButton.setBounds(530,30,100,35);
	tutupButton.setBounds(530,80,100,25);
	
	panel.add(noPeminjamanLabel);	
	panel.add(noPeminjamanTextField);
	
	panel.add(kodeAnggotaLabel);
	panel.add(namaAnggotaLabel);
	panel.add(alamatAnggotaLabel);
	
	panel.add(kodeAnggotaTextField);
	panel.add(namaAnggotaTextField);
	panel.add(alamatAnggotaTextField);
	
	panel.add(peminjamanScrollPane);
	
	panel.add(noPeminjamanBaruButton);
	panel.add(daftarButton);
	panel.add(simpanButton);
	panel.add(tutupButton);
	
	this.add(panel);
	
	noPeminjamanTextField.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent evt) {
        noPeminjamanTextFieldKeyPressed(evt);
      }
    });
	
	kodeAnggotaTextField.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent evt) {
        kodeAnggotaTextFieldKeyPressed(evt);
      }
    });
	
	peminjamanTable.addPropertyChangeListener(new PropertyChangeListener() {
	  public void propertyChange(PropertyChangeEvent evt) {
	    peminjamanTablePropertyChange(evt);
	  }
	});
	
	peminjamanTable.addKeyListener(new java.awt.event.KeyAdapter() {
	  public void keyPressed(java.awt.event.KeyEvent evt) {
	    peminjamanTableKeyPressed(evt);
	  }
	});
		
	noPeminjamanBaruButton.addActionListener(new ActionListener(){
	  public void actionPerformed(ActionEvent evt){
	    noPeminjamanBaruButtonActionPerformed(evt);
	  }
	});
	
	daftarButton.addActionListener(new ActionListener(){
	  public void actionPerformed(ActionEvent evt){
	    daftarButtonActionPerformed(evt);
	  }
	});
	
	simpanButton.addActionListener(new ActionListener(){
	  public void actionPerformed(ActionEvent evt){
	    simpanButtonActionPerformed(evt);
	  }
	});
	
	tutupButton.addActionListener(new ActionListener(){
	  public void actionPerformed(ActionEvent evt){
	    tutupButtonActionPerformed(evt);
	  }
	});		
  }
  
  private void peminjamanTablePropertyChange(PropertyChangeEvent evt){
    switch (peminjamanTable.getSelectedColumn()){
	  case 0: String kodeBuku = "";
	          try {
			    kodeBuku = peminjamanTable.getValueAt(peminjamanTable.getSelectedRow(), 0).toString();
			  } catch (Exception ex){}
			  
	          if ((!kodeBuku.equals("")) && (buku.baca(kodeBuku))){
		        peminjamanTable.setValueAt(buku.getJudulBuku(),peminjamanTable.getSelectedRow(),1);
				peminjamanTable.setValueAt(buku.getPengarang(),peminjamanTable.getSelectedRow(),2);
				
				int i=0;
				while ((i<dataJenisBuku.length) && (!dataJenisBuku[i][0].toString().equals(buku.getKodeJenisBuku()))){
				  i++;
				}
				
				if ((i<dataJenisBuku.length) && (dataJenisBuku[i][0].toString().equals(buku.getKodeJenisBuku()))){
				  peminjamanTable.setValueAt(dataJenisBuku[i][1], peminjamanTable.getSelectedRow(),3);
				} else {
				  peminjamanTable.setValueAt("",peminjamanTable.getSelectedRow(),3);
				}
			  } else {
			    peminjamanTable.setValueAt("",peminjamanTable.getSelectedRow(),1);
				peminjamanTable.setValueAt("",peminjamanTable.getSelectedRow(),2);
			  }
			  break;
	}
  }
  
  private void peminjamanTableKeyPressed(KeyEvent evt){
    if (evt.getKeyCode() == KeyEvent.VK_ENTER){
	  if (peminjamanTable.getSelectedRow() == (peminjamanTable.getRowCount()-1)){
	    if (peminjamanTable.getSelectedColumn()==0) {
	      peminjamanDefaultTableModel.setRowCount(peminjamanDefaultTableModel.getRowCount()+1);
		}
	  }
	}
  }
  
  private void noPeminjamanBaruButtonActionPerformed(ActionEvent evt){ 
    noPeminjamanTextField.setText(peminjaman.buatNoBaru());
  }
  
  private void cariDataAnggota(String kodeAnggota){
    if (anggota.baca(kodeAnggota)){
	  namaAnggotaTextField.setText(anggota.getNamaAnggota());
	  alamatAnggotaTextField.setText(anggota.getAlamatAnggota());
	} else {
	  namaAnggotaTextField.setText("");
	  alamatAnggotaTextField.setText("");
	}
  }
  
  private void daftarButtonActionPerformed(ActionEvent evt){
    if (!formLihatAnggota.isVisible()){
	  formLihatAnggota.tampilkanData(anggota.bacaData());
	  formLihatAnggota.setVisible(true);
	
	  if (!formLihatAnggota.getKodeDipilih().equals("")){
	    kodeAnggotaTextField.setText(formLihatAnggota.getKodeDipilih());
	    cariDataAnggota(kodeAnggotaTextField.getText());
	  }
    }
  }
  
  private void kodeAnggotaTextFieldKeyPressed(KeyEvent evt){
    if (evt.getKeyCode()==KeyEvent.VK_ENTER){
	  if (!kodeAnggotaTextField.getText().equals("")){
	    cariDataAnggota(kodeAnggotaTextField.getText());
	  }
	}
  }
  
  private void noPeminjamanTextFieldKeyPressed(KeyEvent evt){
    if (evt.getKeyCode()==KeyEvent.VK_ENTER){
	  if (!noPeminjamanTextField.getText().equals("")){
	    if (peminjaman.baca(noPeminjamanTextField.getText())){
		  kodeAnggotaTextField.setText(peminjaman.getKodeAnggota());
		  cariDataAnggota(kodeAnggotaTextField.getText());
		  
		  peminjamanDefaultTableModel.setRowCount(0);
		  Object[][] data = peminjaman.getPeminjamanBuku();
		  if ((data != null) && (data.length > 0)){
		    for (int i=0; i<data.length; i++){
			  buku.baca(data[i][0].toString());
			  
			  peminjamanDefaultTableModel.addRow(new Object[]{data[i][0].toString(),buku.getJudulBuku(),buku.getPengarang(),""});
			}
		  }
		}
	  }
	}
  }
  
  private void simpanButtonActionPerformed(ActionEvent evt){    
    if (!noPeminjamanTextField.getText().equals("") && !kodeAnggotaTextField.getText().equals("")){
	  peminjaman.setNoPeminjaman(noPeminjamanTextField.getText());
	  peminjaman.setKodeAnggota(kodeAnggotaTextField.getText());
	  peminjaman.setKodePetugas(FormLogin.userLogin);
	  
	  int jumlahBuku=0, i=0;
	  
	  while (i<peminjamanTable.getRowCount()){
	    if ((peminjamanTable.getValueAt(i,0) != null) && !peminjamanTable.getValueAt(i,0).toString().equals("")){
		  jumlahBuku++;
		}
		i++;
	  }
	  
	  i=0;
	  Object[][] data = new Object[jumlahBuku][2];
	  while (i<peminjamanTable.getRowCount()){
	    if ((peminjamanTable.getValueAt(i,0) != null) && !peminjamanTable.getValueAt(i,0).toString().equals("")){
		  jumlahBuku++;
		  data[i][0] = peminjamanTable.getValueAt(i,0).toString();
		}
		i++;
	  }
	  
	  peminjaman.setPeminjamanBuku(data);
	  
	  if (peminjaman.simpan()){
	    JOptionPane.showMessageDialog(null,"Sudah disimpan");
	  } else {
	    JOptionPane.showMessageDialog(null,"Gagal menyimpan:"+peminjaman.getPesanError());
	  }
	} else {
	  JOptionPane.showMessageDialog(null,"No. peminjaman dan kode anggota tidak boleh kosong");
	}
  }
    
  private void tutupButtonActionPerformed(ActionEvent evt){
    dispose();
  }
  
  private JPanel panel;
  
  private JLabel noPeminjamanLabel;
  
  private JTextField noPeminjamanTextField;
  
  private JLabel kodeAnggotaLabel;
  private JLabel namaAnggotaLabel;
  private JLabel alamatAnggotaLabel;
  
  private JTextField kodeAnggotaTextField;
  private JTextField namaAnggotaTextField;
  private JTextField alamatAnggotaTextField;

  
  private JTable peminjamanTable;
  private DefaultTableModel peminjamanDefaultTableModel;
  private JScrollPane peminjamanScrollPane;
	
  private JButton noPeminjamanBaruButton;
  private JButton daftarButton;
  private JButton simpanButton;
  private JButton tutupButton;  
}