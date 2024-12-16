public class Pemesanan {
    private String namaKasir;
    private String namaBarang;
    private int jumlahBarang;
    private double hargaBarang;

    public Pemesanan(String namaKasir, String namaBarang, int jumlahBarang, double hargaBarang) {
        this.namaKasir = namaKasir;
        this.namaBarang = namaBarang;
        this.jumlahBarang = jumlahBarang;
        this.hargaBarang = hargaBarang;
    }

    public String getNamaKasir() {
        return namaKasir;
    }

    public void setNamaKasir(String namaKasir) {
        this.namaKasir = namaKasir;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public int getJumlahBarang() {
        return jumlahBarang;
    }

    public void setJumlahBarang(int jumlahBarang) {
        this.jumlahBarang = jumlahBarang;
    }

    public double getHargaBarang() {
        return hargaBarang;
    }

    public void setHargaBarang(double hargaBarang) {
        this.hargaBarang = hargaBarang;
    }

    public double hitungTotalHarga() {
        return jumlahBarang * hargaBarang;
    }

    @Override
    public String toString() {
        return "Pemesanan oleh kasir: " + namaKasir + 
               "\nBarang: " + namaBarang + 
               "\nJumlah: " + jumlahBarang + 
               "\nHarga Satuan: " + hargaBarang + 
               "\nTotal Harga: " + hitungTotalHarga();
    }
}

Ini bagian gw ki