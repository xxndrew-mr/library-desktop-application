package view;

import model.*;
import javax.swing.*;
import java.awt.event.*;

public class FormLogin extends JDialog{
  private FormPetugas formPetugas = new FormPetugas();
  private final Petugas petugas = new Petugas();
  private final Enkripsi enkripsi = new Enkripsi();
  
  public static String userLogin = "";
  
  public FormLogin(java.awt.Frame parent, boolean modal) {
    super(parent, modal);
    inisialisasiKomponen();	
  }
  
  private void inisialisasiKomponen(){
    setSize(470,250);
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	
	panel = new JPanel();
	
	userIdLabel = new JLabel("User ID");
	passwordLabel = new JLabel("Password");
	
	userIdTextField = new JTextField();
	passwordField = new JPasswordField();
	
	loginButton = new JButton("Login");
	tutupButton = new JButton("Tutup");
	
	panel.setLayout(null);
	getContentPane().add(panel);
	
	panel.add(userIdLabel);
	panel.add(passwordLabel);
	
	panel.add(userIdTextField);
	panel.add(passwordField);
	
	panel.add(loginButton);
	panel.add(tutupButton);
	
	userIdLabel.setBounds(30,30,110,25);
	passwordLabel.setBounds(30,60,110,25);
	
	userIdTextField.setBounds(140,30,100,25);
	passwordField.setBounds(140,60,220,25);
	
	loginButton.setBounds(90,130,100,25);
	tutupButton.setBounds(310,130,70,25);
	
	
	loginButton.addActionListener(new ActionListener(){
	  public void actionPerformed(ActionEvent evt){
	    loginButtonActionPerformed(evt);
	  }
	});
	
	tutupButton.addActionListener(new ActionListener(){
	  public void actionPerformed(ActionEvent evt){
	    tutupButtonActionPerformed(evt);
	  }
	});
  }
  
  public String getUserLogin(){
        return userLogin;
    }
  
  public boolean validasi(javax.swing.JTextField userIdTextField, javax.swing.JPasswordField passwordField){
    boolean valid = false;
	String hashedInputPassword = "";
        
        if (!userIdTextField.getText().equals("")){
            if (petugas.baca(userIdTextField.getText())){
                try {
                    hashedInputPassword = enkripsi.hashMD5(new String(passwordField.getPassword()));
                } catch (Exception ex){}
                
                if (petugas.getPassword().equalsIgnoreCase(hashedInputPassword)){
                    valid = true;
                } else {
                    JOptionPane.showMessageDialog(null, "User Id (NIM) atau password salah", "Kesalahan", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "User Id (NIM) tidak boleh kosong", "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
        
        return valid;
  }
  
  private void loginButtonActionPerformed(ActionEvent evt){
    if (userIdTextField.getText().equals("")){
	  JOptionPane.showMessageDialog(this, "User ID tidak boleh kosong");
	} else {
	  if (validasi(userIdTextField, passwordField)){
            userLogin = userIdTextField.getText();
            userIdTextField.setText("");
            passwordField.setText("");
            dispose();
        }
	}
  }
  
  private void tutupButtonActionPerformed(ActionEvent evt){
    dispose();
  }
  
  private JPanel panel;
  
  private JLabel userIdLabel;
  private JLabel passwordLabel;
  
  private JTextField userIdTextField;
  private JPasswordField passwordField;
  
  private JButton loginButton;
  private JButton tutupButton;
}