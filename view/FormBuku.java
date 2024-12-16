package view;

import javax.swing.*;
import java.awt.event.*;
import model.Buku;
import model.JenisBuku;

public class FormBuku extends JInternalFrame{
  private FormLihatBuku formLihatBuku = new FormLihatBuku(null,true);
  private Buku buku = new Buku();
  private JenisBuku jenisBuku = new JenisBuku();
  
  private Object[][] dataJenisBuku=null;
  
  public FormBuku(){
    super("Master Data Buku", true, true);
    inisialisasiKomponen();	
  }
  
  private void inisialisasiKomponen(){
    setSize(390,270);
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	
	panel = new JPanel();
	
	kodeBukuLabel = new JLabel("Kode Buku");
	judulBukuLabel = new JLabel("Judul Buku");
	jenisBukuLabel = new JLabel("Jenis Buku");
	pengarangLabel = new JLabel("Pengarang");
	
	kodeBukuTextField = new JTextField();
	judulBukuTextField = new JTextField();
	pengarangTextField = new JTextField();
	
	jenisBukuComboBox = new JComboBox<String>();
	
	dataJenisBuku = jenisBuku.bacaData();
	if ((dataJenisBuku != null) && (dataJenisBuku.length > 0)){
	  for (int i=0; i<dataJenisBuku.length; i++){
	    jenisBukuComboBox.addItem(dataJenisBuku[i][0].toString()+": "+dataJenisBuku[i][1].toString());
	  }
	}
	
	lihatButton = new JButton("Lihat");
	simpanButton = new JButton("Simpan");
	hapusButton = new JButton("Hapus");
	tutupButton = new JButton("Tutup");
	
	panel.setLayout(null);
	getContentPane().add(panel);
	
	panel.add(kodeBukuLabel);
	panel.add(judulBukuLabel);
	panel.add(jenisBukuLabel);
	panel.add(pengarangLabel);
	
	panel.add(kodeBukuTextField);
	panel.add(judulBukuTextField);
	panel.add(jenisBukuComboBox);
	panel.add(pengarangTextField);
	
	panel.add(lihatButton);
	panel.add(simpanButton);
	panel.add(hapusButton);
	panel.add(tutupButton);
	
	kodeBukuLabel.setBounds(30,30,90,25);
	judulBukuLabel.setBounds(30,60,90,25);
	jenisBukuLabel.setBounds(30,90,90,25);
	pengarangLabel.setBounds(30,120,90,25);
	
	kodeBukuTextField.setBounds(130,30,100,25);
	judulBukuTextField.setBounds(130,60,220,25);
	jenisBukuComboBox.setBounds(130,90,220,25);
	pengarangTextField.setBounds(130,120,220,25);
	
	lihatButton.setBounds(240,30,70,25);
	simpanButton.setBounds(30,180,100,25);
	hapusButton.setBounds(140,180,70,25);
	tutupButton.setBounds(240,180,70,25);
	
	kodeBukuTextField.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent evt) {
        kodeBukuTextFieldKeyPressed(evt);
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
  
  private boolean cariBuku(String kodeBuku){
    if (buku.baca(kodeBuku)){
	  judulBukuTextField.setText(buku.getJudulBuku());
	  pengarangTextField.setText(buku.getPengarang());
	  if (jenisBuku.baca(buku.getKodeJenisBuku())){
	    jenisBukuComboBox.setSelectedItem(buku.getKodeJenisBuku()+": "+jenisBuku.getNamaJenisBuku());
	  }
	  return true;
	} else {
	  return false;
	}
  }
  
  private void kodeBukuTextFieldKeyPressed(KeyEvent evt){
    if (evt.getKeyCode() == KeyEvent.VK_ENTER){
	  if (!kodeBukuTextField.getText().equals("")){
	    if (!cariBuku(kodeBukuTextField.getText())){
	      JOptionPane.showMessageDialog(this, "Kode buku tidak ada");
		}
	  } else {
	    JOptionPane.showMessageDialog(this, "Kode buku harus diisi");
	  }
	}
  }
  
  private void lihatButtonActionPerformed(ActionEvent evt){
    formLihatBuku = new FormLihatBuku(null, true);
	formLihatBuku.tampilkanData(buku.bacaData());
	formLihatBuku.setVisible(true);
	
	if (!formLihatBuku.getKodeDipilih().equals("")){
	  kodeBukuTextField.setText(formLihatBuku.getKodeDipilih());
	  cariBuku(kodeBukuTextField.getText());
	}
  }
  
  private void simpanButtonActionPerformed(ActionEvent evt){
    if (kodeBukuTextField.getText().equals("")){
	  JOptionPane.showMessageDialog(this, "Kode buku harus diisi");
	} else {
	  buku.setKodeBuku(kodeBukuTextField.getText());
	  buku.setJudulBuku(judulBukuTextField.getText());
	  buku.setPengarang(pengarangTextField.getText());
	  buku.setKodeJenisBuku(dataJenisBuku[jenisBukuComboBox.getSelectedIndex()][0].toString());
	  
	  if (buku.simpan()){
	    kodeBukuTextField.setText("");
		judulBukuTextField.setText("");
		pengarangTextField.setText("");
		jenisBukuComboBox.setSelectedIndex(0);
	  }
	}
  }
  
  private void hapusButtonActionPerformed(ActionEvent evt){
    if (kodeBukuTextField.getText().equals("")){
	  JOptionPane.showMessageDialog(this, "Kode buku harus diisi");
	} else {
	  if (buku.hapus(kodeBukuTextField.getText())){
	    kodeBukuTextField.setText("");
		judulBukuTextField.setText("");
		pengarangTextField.setText("");
		jenisBukuComboBox.setSelectedIndex(0);
	  }
	}
  }
  
  private void tutupButtonActionPerformed(ActionEvent evt){
    dispose();
  }
  
  private JPanel panel;
  
  private JLabel kodeBukuLabel;
  private JLabel judulBukuLabel;
  private JLabel jenisBukuLabel;
  private JLabel pengarangLabel;
  
  private JTextField kodeBukuTextField;
  private JTextField judulBukuTextField;
  private JTextField pengarangTextField;
  
  private JComboBox<String> jenisBukuComboBox;
  
  private JButton lihatButton;
  private JButton simpanButton;
  private JButton hapusButton;
  private JButton tutupButton;
}