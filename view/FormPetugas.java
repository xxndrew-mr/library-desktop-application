package view;

import model.*;
import javax.swing.*;
import java.awt.event.*;

public class FormPetugas extends JInternalFrame{
  private Petugas petugas = new Petugas();
  private Enkripsi enkripsi = new Enkripsi();
  private FormLihatPetugas formLihatPetugas = new FormLihatPetugas(null,true);
  
  private boolean terenkripsi = false;
  
  public FormPetugas(){
    super("Master Data Petugas", true, true);
    inisialisasiKomponen();	
  }
  
  private void inisialisasiKomponen(){
    setSize(490,270);
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	
	panel = new JPanel();
	
	kodePetugasLabel = new JLabel("Kode Petugas");
	namaPetugasLabel = new JLabel("Nama Petugas");
	alamatPetugasLabel = new JLabel("Alamat Petugas");
	passwordPetugasLabel = new JLabel("Password");
	
	kodePetugasTextField = new JTextField();
	namaPetugasTextField = new JTextField();
	alamatPetugasTextField = new JTextField();
	passwordField = new JPasswordField();
	
	lihatButton = new JButton("Lihat");
	simpanButton = new JButton("Simpan");
	hapusButton = new JButton("Hapus");
	tutupButton = new JButton("Tutup");
	
	panel.setLayout(null);
	getContentPane().add(panel);
	
	panel.add(kodePetugasLabel);
	panel.add(namaPetugasLabel);
	panel.add(alamatPetugasLabel);
	panel.add(passwordPetugasLabel);
	
	panel.add(kodePetugasTextField);
	panel.add(namaPetugasTextField);
	panel.add(alamatPetugasTextField);
	panel.add(passwordField);
	
	panel.add(lihatButton);
	panel.add(simpanButton);
	panel.add(hapusButton);
	panel.add(tutupButton);
	
	kodePetugasLabel.setBounds(30,30,90,25);
	namaPetugasLabel.setBounds(30,60,90,25);
	alamatPetugasLabel.setBounds(30,90,90,25);
	passwordPetugasLabel.setBounds(30,120,90,25);
	
	kodePetugasTextField.setBounds(130,30,100,25);
	namaPetugasTextField.setBounds(130,60,220,25);
	alamatPetugasTextField.setBounds(130,90,320,25);
	passwordField.setBounds(130,120,240,25);
	
	lihatButton.setBounds(230,30,70,25);
	simpanButton.setBounds(90,180,100,25);
	hapusButton.setBounds(210,180,70,25);
	tutupButton.setBounds(310,180,70,25);
	
	kodePetugasTextField.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent evt) {
        kodePetugasTextFieldKeyPressed(evt);
      }
    });
	
	passwordField.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent evt) {
        passwordFieldKeyPressed(evt);
      }
    });
	
	lihatButton.addActionListener(new ActionListener(){
	  public void actionPerformed(ActionEvent evt){
	    lihatButtonActionPerformed(evt);
	  }
	});
	
	simpanButton.addActionListener(new ActionListener(){
	  public void actionPerformed(ActionEvent evt){
	    simpanButtonActionPerformed(evt);
	  }
	});
	
	hapusButton.addActionListener(new ActionListener(){
	  public void actionPerformed(ActionEvent evt){
	    hapusButtonActionPerformed(evt);
	  }
	});
	
	tutupButton.addActionListener(new ActionListener(){
	  public void actionPerformed(ActionEvent evt){
	    tutupButtonActionPerformed(evt);
	  }
	});
  }
  
  private void cariPetugas(String kodePetugas){
    if (petugas.baca(kodePetugas)){
	  namaPetugasTextField.setText(petugas.getNamaPetugas());
	  alamatPetugasTextField.setText(petugas.getAlamatPetugas());
	  passwordField.setText(petugas.getPassword());
	  terenkripsi = true;
	}
  }
  
  private void kodePetugasTextFieldKeyPressed(KeyEvent evt){
    if (evt.getKeyCode() == KeyEvent.VK_ENTER){
	  if (!kodePetugasTextField.getText().equals("")){
	    cariPetugas(kodePetugasTextField.getText());
	  }
	}
  }
  
  private void passwordFieldKeyPressed(KeyEvent evt){
    terenkripsi = false;
  }
  
  private void lihatButtonActionPerformed(ActionEvent evt){
    formLihatPetugas.tampilkanData(petugas.bacaData());
	formLihatPetugas.setVisible(true);
	
	if (!formLihatPetugas.getKodePetugasDipilih().equals("")){
	  kodePetugasTextField.setText(formLihatPetugas.getKodePetugasDipilih());
	  cariPetugas(kodePetugasTextField.getText());
	}
  }
  
  private void simpanButtonActionPerformed(ActionEvent evt){
    if (kodePetugasTextField.getText().equals("")){
	  JOptionPane.showMessageDialog(this, "Kode petugas tidak boleh kosong");
	} else {
	  petugas.setKodePetugas(kodePetugasTextField.getText());
	  petugas.setNamaPetugas(namaPetugasTextField.getText());
	  petugas.setAlamatPetugas(alamatPetugasTextField.getText());
	  
	  String passwordTerenkripsi="";
	  if (terenkripsi){
	    passwordTerenkripsi = new String(passwordField.getPassword());
	  } else {
	    try {
		  passwordTerenkripsi = enkripsi.hashMD5(new String(passwordField.getPassword()));
		} catch (Exception ex){}
	  }
	  petugas.setPassword(passwordTerenkripsi);
	  
	  if (petugas.simpan()){
	    kodePetugasTextField.setText("");
		namaPetugasTextField.setText("");
		alamatPetugasTextField.setText("");
		passwordField.setText("");
	  }
	}
  }
  
  private void hapusButtonActionPerformed(ActionEvent evt){
    if (kodePetugasTextField.getText().equals("")){
	  JOptionPane.showMessageDialog(this, "Kode jenis buku tidak boleh kosong");
	} else {
	  if (petugas.hapus(kodePetugasTextField.getText())){
	    kodePetugasTextField.setText("");
		namaPetugasTextField.setText("");
		alamatPetugasTextField.setText("");
		passwordField.setText("");
	  }
	}
  }
  
  private void tutupButtonActionPerformed(ActionEvent evt){
    dispose();
  }
  
  private JPanel panel;
  
  private JLabel kodePetugasLabel;
  private JLabel namaPetugasLabel;
  private JLabel alamatPetugasLabel;
  private JLabel passwordPetugasLabel;
  
  private JTextField kodePetugasTextField;
  private JTextField namaPetugasTextField;
  private JTextField alamatPetugasTextField;
  private JPasswordField passwordField;
  
  private JButton lihatButton;
  private JButton simpanButton;
  private JButton hapusButton;
  private JButton tutupButton;
}