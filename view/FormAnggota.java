package view;

import model.*;
import javax.swing.*;
import java.awt.event.*;

public class FormAnggota extends JInternalFrame{
  private Anggota anggota = new Anggota();
  private FormLihatAnggota formLihatAnggota = new FormLihatAnggota(null,true);
  
  public FormAnggota(){
    super("Master Data Anggota", true, true);
    inisialisasiKomponen();	
  }
  
  private void inisialisasiKomponen(){
    setSize(490,270);
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	
	panel = new JPanel();
	
	kodeAnggotaLabel = new JLabel("Kode Anggota");
	namaAnggotaLabel = new JLabel("Nama Anggota");
	alamatAnggotaLabel = new JLabel("Alamat Anggota");
	
	kodeAnggotaTextField = new JTextField();
	namaAnggotaTextField = new JTextField();
	alamatAnggotaTextField = new JTextField();
	
	lihatButton = new JButton("Lihat");
	simpanButton = new JButton("Simpan");
	hapusButton = new JButton("Hapus");
	tutupButton = new JButton("Tutup");
	
	panel.setLayout(null);
	getContentPane().add(panel);
	
	panel.add(kodeAnggotaLabel);
	panel.add(namaAnggotaLabel);
	panel.add(alamatAnggotaLabel);
	
	panel.add(kodeAnggotaTextField);
	panel.add(namaAnggotaTextField);
	panel.add(alamatAnggotaTextField);
	
	panel.add(lihatButton);
	panel.add(simpanButton);
	panel.add(hapusButton);
	panel.add(tutupButton);
	
	kodeAnggotaLabel.setBounds(30,30,90,25);
	namaAnggotaLabel.setBounds(30,60,90,25);
	alamatAnggotaLabel.setBounds(30,90,90,25);
	
	kodeAnggotaTextField.setBounds(130,30,100,25);
	namaAnggotaTextField.setBounds(130,60,220,25);
	alamatAnggotaTextField.setBounds(130,90,320,25);
	
	lihatButton.setBounds(230,30,70,25);
	simpanButton.setBounds(90,180,100,25);
	hapusButton.setBounds(210,180,70,25);
	tutupButton.setBounds(310,180,70,25);
	
	kodeAnggotaTextField.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent evt) {
        kodeAnggotaTextFieldKeyPressed(evt);
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
  
  private void cariAnggota(String kodeAnggota){
    if (anggota.baca(kodeAnggota)){
	  namaAnggotaTextField.setText(anggota.getNamaAnggota());
	  alamatAnggotaTextField.setText(anggota.getAlamatAnggota());
	}
  }
  
  private void kodeAnggotaTextFieldKeyPressed(KeyEvent evt){
    if (evt.getKeyCode() == KeyEvent.VK_ENTER){
	  if (!kodeAnggotaTextField.getText().equals("")){
	    cariAnggota(kodeAnggotaTextField.getText());
	  }
	}
  }
  
  private void lihatButtonActionPerformed(ActionEvent evt){
    formLihatAnggota.tampilkanData(anggota.bacaData());
	formLihatAnggota.setVisible(true);
	
	if (!formLihatAnggota.getKodeDipilih().equals("")){
	  kodeAnggotaTextField.setText(formLihatAnggota.getKodeDipilih());
	  cariAnggota(kodeAnggotaTextField.getText());
	}
  }
  
  private void simpanButtonActionPerformed(ActionEvent evt){
    if (kodeAnggotaTextField.getText().equals("")){
	  JOptionPane.showMessageDialog(this, "Kode anggota tidak boleh kosong");
	} else {
	  anggota.setKodeAnggota(kodeAnggotaTextField.getText());
	  anggota.setNamaAnggota(namaAnggotaTextField.getText());
	  anggota.setAlamatAnggota(alamatAnggotaTextField.getText());
	  
	  if (anggota.simpan()){
	    kodeAnggotaTextField.setText("");
		namaAnggotaTextField.setText("");
		alamatAnggotaTextField.setText("");
	  }
	}
  }
  
  private void hapusButtonActionPerformed(ActionEvent evt){
    if (kodeAnggotaTextField.getText().equals("")){
	  JOptionPane.showMessageDialog(this, "Kode jenis buku tidak boleh kosong");
	} else {
	  if (anggota.hapus(kodeAnggotaTextField.getText())){
	    kodeAnggotaTextField.setText("");
		namaAnggotaTextField.setText("");
		alamatAnggotaTextField.setText("");
	  }
	}
  }
  
  private void tutupButtonActionPerformed(ActionEvent evt){
    dispose();
  }
  
  private JPanel panel;
  
  private JLabel kodeAnggotaLabel;
  private JLabel namaAnggotaLabel;
  private JLabel alamatAnggotaLabel;
  
  private JTextField kodeAnggotaTextField;
  private JTextField namaAnggotaTextField;
  private JTextField alamatAnggotaTextField;
  
  private JButton lihatButton;
  private JButton simpanButton;
  private JButton hapusButton;
  private JButton tutupButton;
}