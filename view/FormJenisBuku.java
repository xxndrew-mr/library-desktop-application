package view;

import javax.swing.*;
import java.awt.event.*;
import model.JenisBuku;

public class FormJenisBuku extends JInternalFrame {
  private final JenisBuku jenisBuku = new JenisBuku();
  private FormLihatJenisBuku formLihatJenisBuku;
  
  public FormJenisBuku(){
    initComponents();
  }
  
  private void initComponents(){
    setTitle("Master Data Jenis Buku");
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);	
	setSize(370, 200);
	
	panel = new JPanel();
	kodeJenisBukuLabel = new JLabel("Kode");
	jenisBukuLabel = new JLabel("Jenis Buku");
	
	kodeJenisBukuTextField = new JTextField();
	jenisBukuTextField = new JTextField();
	
	lihatButton = new JButton("Lihat");
	simpanButton = new JButton("Simpan");
	hapusButton = new JButton("Hapus");
	tutupButton = new JButton("tutup");
	
	panel.setLayout(null);
	getContentPane().add(panel);
	
	panel.add(kodeJenisBukuLabel);
	panel.add(jenisBukuLabel);
	panel.add(kodeJenisBukuTextField);
	panel.add(jenisBukuTextField);
	
	panel.add(lihatButton);
	panel.add(simpanButton);
	panel.add(hapusButton);
	panel.add(tutupButton);
	
	kodeJenisBukuLabel.setBounds(30, 30, 80, 25);
	jenisBukuLabel.setBounds(30, 60, 80, 25);
	
	kodeJenisBukuTextField.setBounds(115, 30, 100, 25);
	jenisBukuTextField.setBounds(115, 60, 220, 25);
	
	lihatButton.setBounds(220, 30, 70, 30);
	simpanButton.setBounds(30, 110, 100, 30);
	hapusButton.setBounds(135, 110, 90, 30);
	tutupButton.setBounds(230, 110, 80, 30);
	
	kodeJenisBukuTextField.addKeyListener(new KeyAdapter(){
	  public void KeyPressed(KeyEvent evt){
	    kodeJenisBukuTextFieldKeyPressed(evt);
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
  
  private boolean cariJenisBuku(String kodeJenisBuku){
    if (jenisBuku.baca(kodeJenisBuku)){
	  jenisBukuTextField.setText(jenisBuku.getNamaJenisBuku());	  
	  return true;
	}
	return false;
  }
  
  private void kodeJenisBukuTextFieldKeyPressed(KeyEvent evt){
    if (evt.getKeyCode() == KeyEvent.VK_ENTER){
	  if (kodeJenisBukuTextField.getText().equals("")){
	    JOptionPane.showMessageDialog(this, "Kode jenis buku belum diisi");
	  } else {
	    if (!cariJenisBuku(kodeJenisBukuTextField.getText())){
		  JOptionPane.showMessageDialog(this, "Data jenis buku tidak ada");
		}
	  }
	}
  }
  
  private void lihatButtonActionPerformed(ActionEvent evt){
    formLihatJenisBuku = new FormLihatJenisBuku(null, true);
	formLihatJenisBuku.tampilkanData(jenisBuku.bacaData());
	formLihatJenisBuku.setVisible(true);
	
	if (!formLihatJenisBuku.getKodeDipilih().equals("")){
	  kodeJenisBukuTextField.setText(formLihatJenisBuku.getKodeDipilih());
	  cariJenisBuku(formLihatJenisBuku.getKodeDipilih());
	}
  }
  
  private void simpanButtonActionPerformed(ActionEvent evt){
    if (kodeJenisBukuTextField.equals("")){
	  JOptionPane.showMessageDialog(this, "Kode jenis buku belum diisi");
	} else {
	  jenisBuku.setKodeJenisBuku(kodeJenisBukuTextField.getText());
	  jenisBuku.setNamaJenisBuku(jenisBukuTextField.getText());
	  if (jenisBuku.simpan()){
	    kodeJenisBukuTextField.setText("");
		jenisBukuTextField.setText("");
	  } else {
	    JOptionPane.showMessageDialog(this, "Tidak dapat menyimpan");
	  }
	}
  }
  
  private void hapusButtonActionPerformed(ActionEvent evt){
    if (kodeJenisBukuTextField.equals("")){
	  JOptionPane.showMessageDialog(this, "Kode jenis buku belum diisi");
	} else {
	  if (jenisBuku.hapus(kodeJenisBukuTextField.getText())){
	    kodeJenisBukuTextField.setText("");
		jenisBukuTextField.setText("");
	  } else {
	    JOptionPane.showMessageDialog(this, "Tidak dapat menghapus");
	  }
	}
  }
  
  private void tutupButtonActionPerformed(ActionEvent evt){
    dispose();
  }
  
  private JPanel panel;
  private JLabel kodeJenisBukuLabel;
  private JLabel jenisBukuLabel;
  
  private JTextField kodeJenisBukuTextField;
  private JTextField jenisBukuTextField;
  
  private JButton lihatButton;
  private JButton simpanButton;
  private JButton hapusButton;
  private JButton tutupButton;
}