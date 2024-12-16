package model;

import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.ResultSet; 
import java.sql.Statement; 
import javax.swing.JOptionPane;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Peminjaman{  
  private String noPeminjaman;
  private String kodeAnggota;
  private String kodePetugas;
  private String waktuPeminjaman;
  private Object[][] peminjamanBuku;
  private String pesanError;
  private Koneksi koneksi = new Koneksi();
  
  public static String noPeminjamanDicari="";
  
  public Peminjaman(){
    noPeminjaman = "";
    peminjamanBuku=null;
	pesanError = "";
  }
  
  public void setNoPeminjaman(String noPeminjaman){
    this.noPeminjaman = noPeminjaman;
  }
  
  public void setKodeAnggota(String kodeAnggota){
    this.kodeAnggota = kodeAnggota;
  }
  
  public void setKodePetugas(String kodePetugas){
    this.kodePetugas = kodePetugas;
  }
  
  public void setWaktuPeminjaman(String waktuPeminjaman){
    this.waktuPeminjaman = waktuPeminjaman;
  }
  
  public void setPeminjamanBuku(Object[][] peminjamanBuku){
    this.peminjamanBuku = peminjamanBuku;
  }
  
  public String getNoPeminjaman(){
    return noPeminjaman;
  }
  
  public String getKodeAnggota(){
    return kodeAnggota;
  }
  
  public String getKodePetugas(){
    return kodePetugas;
  }
  
  public String getWaktuPeminjaman(){
    return waktuPeminjaman;
  }
  
  public Object[][] getPeminjamanBuku(){
    return peminjamanBuku;
  }
  
  public String getPesanError(){
    return pesanError;
  }
  
  public String buatNoBaru(){
    boolean adaKesalahan = false;	
	Connection connection = null;

    String noTerakhir = "PB-0000001";
	
	if ((connection = koneksi.getConnection()) != null){ 
	    try { 
		  String SQLStatemen = "select nopeminjaman from tbpeminjaman order by nopeminjaman desc limit 0,1"; 
		  Statement sta = connection.createStatement(); 
		  ResultSet rset = sta.executeQuery(SQLStatemen);                    
		  
		  rset.next(); 
		  if (rset.getRow()>0){ 
		    noTerakhir = rset.getString("nopeminjaman");
			sta.close(); 
			rset.close();
			
			int no = 0;
			try {
			  no = Integer.parseInt(noTerakhir.substring(3, noTerakhir.length()))+1;
			} catch (Exception ex){}
			
			noTerakhir = noTerakhir.substring(0,3)+("0000000").substring(0,7-Integer.toString(no).length())+Integer.toString(no);
		  } else {
		    sta.close();
			rset.close(); 
		  }
		} catch (Exception ex){}
	}
	
	return noTerakhir;
  }
  
  public boolean simpan(){
    boolean adaKesalahan = false;
	Connection connection; 
	
	if ((connection = koneksi.getConnection()) != null){
	  int jumlahSimpan=0;
	  boolean simpan = false;
	  SimpleDateFormat waktu = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  
	  try { 
	    String SQLStatemen = "select * from tbpeminjaman where nopeminjaman='"+noPeminjaman+"'"; 
		Statement sta = connection.createStatement(); 
		ResultSet rset = sta.executeQuery(SQLStatemen);                    
		
		rset.next(); 
		if (rset.getRow()>0){
		  sta.close();
		  rset.close();
		  Object[] arrOpsi = {"Ya","Tidak"};
		  int pilih=JOptionPane.showOptionDialog(null,"No. peminjaman "+noPeminjaman+" sudah ada\nApakah data diupdate?","Konfirmasi",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null,arrOpsi,arrOpsi[0]); 
		  if (pilih==0){ 
		    simpan = true; 
			SQLStatemen = "update tbpeminjaman set kodeanggota='"+kodeAnggota+
			              "', kodepetugas='"+kodePetugas+
						  "', waktupeminjaman='"+waktu.format(new Date())+
						  "' where nopeminjaman='"+noPeminjaman+"'"; 
			sta = connection.createStatement();
			jumlahSimpan = sta.executeUpdate(SQLStatemen); 
		  }
		} else {
		  sta.close();
		  rset.close();
		  
		  simpan = true;
		  SQLStatemen = "insert into tbpeminjaman(nopeminjaman, kodeanggota, kodepetugas, waktupeminjaman) values ('"+ noPeminjaman +"','"+ 
			              kodeAnggota+"','"+ kodePetugas+"','"+ waktu.format(new Date()) +"')"; 
          sta = connection.createStatement(); 
		  jumlahSimpan = sta.executeUpdate(SQLStatemen); 
		}
		  
		if (simpan) {
		  try{
		    SQLStatemen = "delete from tbdetailpeminjaman where nopeminjaman='"+noPeminjaman+"'"; 
			sta = connection.createStatement();
			sta.executeUpdate(SQLStatemen);
		  } catch (Exception ex){}
		  
		  for (int i=0; i < peminjamanBuku.length; i++){
		    try {
			  SQLStatemen = "insert into tbdetailpeminjaman(nopeminjaman,kodebuku,waktupengembalian,dikembalikan) values ('"+ noPeminjaman +"','"+ 
			  peminjamanBuku[i][0] + "','" + waktu.format(new Date()) + "','T')";
			  sta = connection.createStatement();
			  jumlahSimpan += sta.executeUpdate(SQLStatemen);
		    } catch (Exception ex){}
		  }
		}
	  } catch (Exception ex){
	    adaKesalahan = true; 
		JOptionPane.showMessageDialog(null,"Tidak dapat membuka tabel peminjaman\n"+ex,"Kesalahan",JOptionPane.ERROR_MESSAGE);
	  }
	} else {
	  adaKesalahan = true;
	  JOptionPane.showMessageDialog(null,"Tidak dapat melakukan koneksi ke server\n"+koneksi.getPesanKesalahan(),"Kesalahan",JOptionPane.ERROR_MESSAGE);
    }
        
    return !adaKesalahan;
  }
  
  public boolean baca(String noPeminjaman){
    boolean adaKesalahan = false;	
	peminjamanBuku = null;
	
	Connection connection; 
    if ((connection = koneksi.getConnection()) != null){
	    try { 
		  String SQLStatemen = "select * from tbpeminjaman where nopeminjaman='"+noPeminjaman+"'"; 
		  Statement sta = connection.createStatement(); 
		  ResultSet rset = sta.executeQuery(SQLStatemen);                    
		  
		  rset.next(); 
		  if (rset.getRow()>0){ 
		    this.noPeminjaman = rset.getString("nopeminjaman");
			this.kodeAnggota = rset.getString("kodeanggota"); 
			this.kodePetugas = rset.getString("kodepetugas"); 
		    this.waktuPeminjaman = rset.getString("waktupeminjaman"); 
			sta.close(); 
			rset.close(); 
			
			SQLStatemen = "select tbdetailpeminjaman.kodebuku, tbbuku.judulbuku from tbdetailpeminjaman inner join tbbuku on tbdetailpeminjaman.kodebuku=tbbuku.kodebuku where nopeminjaman='"+noPeminjaman+"'";  
			sta = connection.createStatement(); 
			rset = sta.executeQuery(SQLStatemen);                    
			
			rset.next(); 
			rset.last();
			if (rset.getRow() > 0){
			  peminjamanBuku = new Object[rset.getRow()][2];
			  rset.first();
			  int i=0;
			  do { 
			    peminjamanBuku[i] = new Object[]{rset.getString("tbdetailpeminjaman.kodebuku"), rset.getString("tbbuku.judulbuku")}; 		    
			    i++;
			  } while (rset.next());
			}
			sta.close(); 
			rset.close(); 
		  } else {
		    sta.close();
			rset.close(); 
			adaKesalahan = true; 
			JOptionPane.showMessageDialog(null,"No. peminjaman \""+noPeminjaman+"\" tidak ditemukan","Informasi",JOptionPane.INFORMATION_MESSAGE);
		  }
		} catch (Exception ex){
		  adaKesalahan = true; 
		  JOptionPane.showMessageDialog(null,"Tidak dapat membuka tabel tbpeminjaman\n"+ex,"Kesalahan",JOptionPane.ERROR_MESSAGE);
		}
	} else {
      adaKesalahan = true;
      JOptionPane.showMessageDialog(null,"Tidak dapat melakukan koneksi ke server\n"+koneksi.getPesanKesalahan(),"Kesalahan",JOptionPane.ERROR_MESSAGE);
    }
	
	return !adaKesalahan;
  }
  
  public Object[][] bacaDaftar(){
    boolean adaKesalahan = false;	
	Object[][] daftarPeminjaman = new Object[0][0] ;
	
	Connection connection; 
    if ((connection = koneksi.getConnection()) != null){
	    try { 
		  String SQLStatemen = "select tbpeminjaman.nopeminjaman, tbanggota.namaanggota from tbpeminjaman inner join tbanggota on tbpeminjaman.kodeanggota=tbanggota.kodeanggota"; 
		  Statement sta = connection.createStatement(); 
		  ResultSet rset = sta.executeQuery(SQLStatemen);                    
		  
		  rset.next(); 
		  rset.last();
		  if (rset.getRow() > 0){
		    daftarPeminjaman = new Object[rset.getRow()][2];
		    rset.first();
			int i=0;
			do { 
		      daftarPeminjaman[i] = new Object[]{rset.getString("tbpeminjaman.nopeminjaman"), rset.getString("tbanggota.namaanggota")}; 		    
			  i++;
			} while (rset.next());
		  }
		  sta.close(); 
		  rset.close(); 
		} catch (Exception ex){
		  adaKesalahan = true; 
		  JOptionPane.showMessageDialog(null,"Tidak dapat membuka tabel tbpeminjaman dan tbanggota\n"+ex,"Kesalahan",JOptionPane.ERROR_MESSAGE);
		}
	} else {
      adaKesalahan = true;
      JOptionPane.showMessageDialog(null,"Tidak dapat melakukan koneksi ke server\n"+koneksi.getPesanKesalahan(),"Kesalahan",JOptionPane.ERROR_MESSAGE);
    }
	
	return daftarPeminjaman;
  }
  
}