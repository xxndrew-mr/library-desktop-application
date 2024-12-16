private void setEnableMenu(boolean enabled){
    masterDataMenu.setEnabled(enabled);
	transaksiMenu.setEnabled(enabled);
	laporanMenu.setEnabled(enabled);
  }
  
  private void aksesMenuItemActionPerformed(ActionEvent evt){
    userLogin = "";
    if (aksesMenuItem.getText().equals("Login")){
	  formLogin = new FormLogin(null, true);
	  formLogin.setVisible(true);	
	
	  if (!formLogin.getUserLogin().equals("")){
	    userLogin = formLogin.getUserLogin();
	    setEnableMenu(true);
	    aksesMenuItem.setText("Logout");
	  } else {
	    setEnableMenu(false);	  
	  }
	} else {
	  setEnableMenu(false);
	  aksesMenuItem.setText("Login");
	}
  }