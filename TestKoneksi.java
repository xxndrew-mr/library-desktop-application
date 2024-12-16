import model.Koneksi;

public class TestKoneksi{
	public static void main(String[] args){
	Koneksi koneksi = new Koneksi();
	
	if (koneksi.getConnection() !=null){
		System.out.println("Berhasil melakukan koneksi ke database");
	} else{
		System.out.println("Gagal melakukan koneksi ke database");
		System.out.println("Kesalahan: "+koneksi.getPesanKesalahan());
		}
	}
}