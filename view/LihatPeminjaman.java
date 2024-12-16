import java.awt.event.*;
import java.awt.Frame;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.table.DefaultTableModel;

public class LihatPeminjaman extends JDialog {
  private final String judulKolomTabelLihatPeminjaman[] = {"No. Peminjaman","Nama Anggota"};
  
  Peminjaman peminjaman = new Peminjaman();
  
  public static String noPeminjamanDicari="";
  
  public LihatPeminjaman(Frame parent, boolean modal){
    super(parent, modal);
    initComponents();
  }
  
  private void initComponents(){
    setTitle("Data Peminjaman");
	setSize(400,300);
	setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
	setAlwaysOnTop(true);
	
	Dimension dimensi = Toolkit.getDefaultToolkit().getScreenSize(); 
    setLocation((dimensi.width-getWidth())/2,(dimensi.height-getHeight())/2);
	
	panel = new JPanel();
	panel.setLayout(null);
	
	daftarPeminjamanDefaultTableModel = new DefaultTableModel(null,judulKolomTabelLihatPeminjaman);
	daftarPeminjamanTable = new JTable(){
	                      public boolean isCellEditable(int rowIndex, int colIndex) {
						    return false; //Disable Editing 
						  }
	                    };
	peminjamanScrollPane = new JScrollPane();
	
	pilihButton = new JButton("Pilih");
	tutupButton = new JButton("Tutup");
	
	daftarPeminjamanTable.setModel(daftarPeminjamanDefaultTableModel);
	daftarPeminjamanTable.getColumnModel().getColumn(0).setMinWidth(80);
    daftarPeminjamanTable.getColumnModel().getColumn(0).setPreferredWidth(80);
    daftarPeminjamanTable.getColumnModel().getColumn(0).setMaxWidth(80);
	
	peminjamanScrollPane.getViewport().add(daftarPeminjamanTable);
	panel.add(peminjamanScrollPane);
	panel.add(pilihButton);
	panel.add(tutupButton);
	
	this.add(panel);
	
	peminjamanScrollPane.setBounds(0,0,380,200);
	pilihButton.setBounds(100,230,100,25);
	tutupButton.setBounds(240,230,100,25);
	
	addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
	
    pilihButton.addActionListener(new ActionListener(){
	  public void actionPerformed(ActionEvent evt){
	    pilihButtonActionPerformed(evt);
	  }
	});	
	
	tutupButton.addActionListener(new ActionListener(){
	  public void actionPerformed(ActionEvent evt){
	    tutupButtonActionPerformed(evt);
	  }
	});
  }
  
  private void formWindowOpened(WindowEvent evt){
    Object[][] daftarPeminjaman = peminjaman.bacaDaftar();
	
	if (daftarPeminjaman.length>0){
	  daftarPeminjamanDefaultTableModel.setRowCount(0);
	  for (int i=0; i<daftarPeminjaman.length; i++){
	    daftarPeminjamanDefaultTableModel.insertRow(daftarPeminjamanDefaultTableModel.getRowCount(),daftarPeminjaman[i]); 
	  }
	}
  }
  
  private void pilihButtonActionPerformed(ActionEvent evt){
    noPeminjamanDicari="";
	if (daftarPeminjamanTable.getSelectedRowCount()>0){
	  noPeminjamanDicari = (String)daftarPeminjamanTable.getValueAt(daftarPeminjamanTable.getSelectedRow(), 0);
      dispose(); 
    } else {
	  JOptionPane.showMessageDialog(null,"Belum ada yang dipilih"); 
    }
  }
  
  private void tutupButtonActionPerformed(ActionEvent evt){
    noPeminjamanDicari="";
    dispose();
  }
  
  private JPanel panel;
  private DefaultTableModel daftarPeminjamanDefaultTableModel;
  private JTable daftarPeminjamanTable;
  private JScrollPane peminjamanScrollPane;
  private JButton pilihButton;
  private JButton tutupButton;
}  