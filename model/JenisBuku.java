package model;

import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.ResultSet; 
import java.sql.Statement; 
import javax.swing.JOptionPane;

public class JenisBuku{  
  private String kodeJenisBuku, namaJenisBuku;
  private Koneksi koneksi = new Koneksi();
  
  public JenisBuku(){
    kodeJenisBuku = "";
    namaJenisBuku = "";
  }
  
  public void setKodeJenisBuku(String kodeJenisBuku){
    this.kodeJenisBuku = kodeJenisBuku;
  }
  
  public void setNamaJenisBuku(String namaJenisBuku){
    this.namaJenisBuku = namaJenisBuku;
  }
    
  public String getKodeJenisBuku(){
    return kodeJenisBuku;
  }
  
  public String getNamaJenisBuku(){
    return namaJenisBuku;
  }
  
  public boolean simpan(){
    boolean adaKesalahan = false;	
	Connection connection; 
    if ((connection = koneksi.getConnection()) != null){
	    int jumlahSimpan=0; 
		boolean simpan = false; 
		
		try { 
		  String SQLStatemen = "select * from tbjenisbuku where kodejenisbuku='"+kodeJenisBuku+"'"; 
		  Statement sta = connection.createStatement(); 
		  ResultSet rset = sta.executeQuery(SQLStatemen);                    
		  
		  rset.next(); 
		  if (rset.getRow()>0){ 
		    sta.close(); 
			rset.close(); 
			Object[] arrOpsi = {"Ya","Tidak"}; 
			int pilih=JOptionPane.showOptionDialog(null,"Kode jenisbuku sudah ada\nApakah data diupdate?","Konfirmasi",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null,arrOpsi,arrOpsi[0]); 
			if (pilih==0){ 
			  simpan = true; 
			  SQLStatemen = "update tbjenisbuku set namajenisbuku='"+namaJenisBuku+
							"' where kodejenisbuku='"+kodeJenisBuku+"'"; 
			  sta = connection.createStatement();
			  jumlahSimpan = sta.executeUpdate(SQLStatemen); 
			}
		  } else {
		    sta.close();
			rset.close(); 
			
			simpan = true; 
			SQLStatemen = "insert into tbjenisbuku values ('"+ kodeJenisBuku +"','"+ 
			              namaJenisBuku+"')"; 
            sta = connection.createStatement(); 
			jumlahSimpan = sta.executeUpdate(SQLStatemen); 
		  }
		  
		  if (simpan) {
		    if (jumlahSimpan > 0){
			  JOptionPane.showMessageDialog(null,"Data jenis buku sudah tersimpan","Informasi",JOptionPane.INFORMATION_MESSAGE);
			} else {
			  adaKesalahan = true; 
			  JOptionPane.showMessageDialog(null,"Gagal menyimpan data jenis buku","Kesalahan",JOptionPane.ERROR_MESSAGE); 
			} 
		  } else {
		    adaKesalahan = true; 
		  }
		} catch (Exception ex){
		  adaKesalahan = true; 
		  JOptionPane.showMessageDialog(null,"Tidak dapat membuka tabel tbjenisbuku\n"+ex,"Kesalahan",JOptionPane.ERROR_MESSAGE);
		}
	} else {
      adaKesalahan = true;
      JOptionPane.showMessageDialog(null,"Tidak dapat melakukan koneksi ke server\n"+koneksi.getPesanKesalahan(),"Kesalahan",JOptionPane.ERROR_MESSAGE);
    }
	
	return !adaKesalahan;
  }
  
  public boolean baca(String kodeJenisBuku){
    boolean adaKesalahan = false;	
	Connection connection; 
    if ((connection = koneksi.getConnection()) != null){
	    try { 
		  String SQLStatemen = "select * from tbjenisbuku where kodejenisbuku='"+kodeJenisBuku+"'"; 
		  Statement sta = connection.createStatement(); 
		  ResultSet rset = sta.executeQuery(SQLStatemen);                    
		  
		  rset.next(); 
		  if (rset.getRow()>0){ 
		    kodeJenisBuku = rset.getString("kodejenisbuku"); 
		    namaJenisBuku = rset.getString("namajenisbuku");
			sta.close(); 
			rset.close(); 
		  } else {
		    sta.close();
			rset.close(); 
			adaKesalahan = true; 
			JOptionPane.showMessageDialog(null,"Kode jenis buku \""+kodeJenisBuku+"\" tidak ditemukan","Informasi",JOptionPane.INFORMATION_MESSAGE);
		  }
		} catch (Exception ex){
		  adaKesalahan = true; 
		  JOptionPane.showMessageDialog(null,"Tidak dapat membuka tabel tbjenisbuku\n"+ex,"Kesalahan",JOptionPane.ERROR_MESSAGE);
		}
	} else {
      adaKesalahan = true;
      JOptionPane.showMessageDialog(null,"Tidak dapat melakukan koneksi ke server\n"+koneksi.getPesanKesalahan(),"Kesalahan",JOptionPane.ERROR_MESSAGE);
    }
	
	return !adaKesalahan;
  }
  
  public Object[][] bacaData(){
    boolean adaKesalahan = false;	
	Object[][] jenisBukuList = null;
	
	Connection connection; 
    if ((connection = koneksi.getConnection()) != null){
	    try { 
		  String SQLStatemen = "select kodejenisbuku,namajenisbuku from tbjenisbuku"; 
		  Statement sta = connection.createStatement(); 
		  ResultSet rset = sta.executeQuery(SQLStatemen);                    
		  
		  rset.next(); 
		  rset.last();		  
		  if (rset.getRow()>0){
		    jenisBukuList = new Object[rset.getRow()][2];
		    rset.first();
			int i=0;
			do { 
		      jenisBukuList[i] = new Object[]{rset.getString("kodejenisbuku"), rset.getString("namajenisbuku")};
			  i++;
		    } while (rset.next());
		  }
		  
		  sta.close(); 
		  rset.close(); 
		} catch (Exception ex){
		  adaKesalahan = true; 
		  JOptionPane.showMessageDialog(null,"Tidak dapat membuka tabel tbjenisbuku\n"+ex,"Kesalahan",JOptionPane.ERROR_MESSAGE);
		}
	} else {
      adaKesalahan = true;
      JOptionPane.showMessageDialog(null,"Tidak dapat melakukan koneksi ke server\n"+koneksi.getPesanKesalahan(),"Kesalahan",JOptionPane.ERROR_MESSAGE);
    }
	
	return jenisBukuList;
  }
  
  public boolean hapus(String kodeJenisBuku){
    boolean adaKesalahan = false;	
	Connection connection; 
    if ((connection = koneksi.getConnection()) != null){
	    int jumlahHapus=0; 
		
		try { 
		  String SQLStatemen = "delete from tbjenisbuku where kodejenisbuku='"+kodeJenisBuku+"'"; 
		  Statement sta = connection.createStatement(); 
		  jumlahHapus = sta.executeUpdate(SQLStatemen);                    
		  
		  if (jumlahHapus>0){ 
		    sta.close(); 
			JOptionPane.showMessageDialog(null,"Data jenis buku sudah dihapus","Informasi",JOptionPane.INFORMATION_MESSAGE);
		  } else {
		    sta.close();
			JOptionPane.showMessageDialog(null,"Kode jenis buku tidak ditemukan","Informasi",JOptionPane.INFORMATION_MESSAGE);
			adaKesalahan = true;
		  }
		} catch (Exception ex){
		  adaKesalahan = true; 
		  JOptionPane.showMessageDialog(null,"Tidak dapat membuka tabel tbjenisbuku\n"+ex,"Kesalahan",JOptionPane.ERROR_MESSAGE);
		}
	} else {
      adaKesalahan = true;
      JOptionPane.showMessageDialog(null,"Tidak dapat melakukan koneksi ke server\n"+koneksi.getPesanKesalahan(),"Kesalahan",JOptionPane.ERROR_MESSAGE);
    }
	
	return !adaKesalahan;
  }
  
}