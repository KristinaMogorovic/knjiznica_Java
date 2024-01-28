package knjiznica;

import java.sql.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.SwingConstants;

public class clanarine {

	private JFrame frame;
	private JTextField trazilica;
	private JTable tablica;
	private JScrollPane scrollPane;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JTextField TFvrsta;
	private JTextField TFcijena;
	private JButton btnNewButton_2;
	
	private String vrstaUpdate;  //dodano za izvršenje ažuriranja
	
	private JButton btnNewButton_3;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_3;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					clanarine window = new clanarine();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public clanarine() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
	
		frame = new JFrame();
		frame.setBounds(100, 100, 813, 457);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(48, 168, 488, 138);
		frame.getContentPane().add(scrollPane);

		tablica = new JTable();
		scrollPane.setViewportView(tablica);
		tablica.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"vrsta", "cijena"
			}
		){
			boolean[] columnEditables = new boolean[] {
					false, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
					
			});
		
////////////////////////////////////////////////////*PRIKAZ PODATAKA*/////////////////////////////////////////////////////////////
		
			try
			{
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","6929");
				
				String upit="SELECT * FROM RWAclanarina";
				Statement stmt=con.createStatement();
				ResultSet rs=stmt.executeQuery(upit);
					
				DefaultTableModel model=(DefaultTableModel)tablica.getModel();
				
				while(rs.next()) {
		
					String vrsta=rs.getString(1);
					float cijena=rs.getFloat(2);
						
					model.addRow(new Object[] {vrsta, cijena});
						
				} //while
					
			}//try
				
			catch(Exception e1)
			{
				JOptionPane.showMessageDialog(null, "Greška pri dohvatu podataka");
			}//catch
			
/////////////////////////////////*TRAŽILICA*///////////////////////////////////////////////
		
			trazilica = new JTextField();
			
			trazilica.setBounds(72, 99, 223, 25);
			frame.getContentPane().add(trazilica);
			trazilica.setColumns(10);
			
			JButton btnNewButton_1 = new JButton("Traži");
			btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String pretragas=trazilica.getText();
	
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","6929");
					
					//uzimanje podataka
					
					String upit="SELECT * FROM RWAclanarina WHERE vrsta LIKE ? OR cijena LIKE ?";
					PreparedStatement ps=con.prepareStatement(upit);
					
					ps.setString(1, "%"+pretragas+"%"); //% omogućuje da se ne gleda samo pocetak i kraj nego i sve između
					ps.setString(2, "%"+pretragas+"%");
					
					ResultSet rs=ps.executeQuery();
					
					DefaultTableModel model=(DefaultTableModel)tablica.getModel();
					
					model.setRowCount(0); //postavi broj redaka na 0
					
					while(rs.next()) {
		
						String vrsta=rs.getString(1);
						float cijena=rs.getFloat(2);
						
						model.addRow(new Object[] {vrsta, cijena});
						
					} //while
				
				} //try
				catch(Exception e2) {
					JOptionPane.showMessageDialog(null, "Traženje podataka nije omogućeno");
				} //catch 
				
			
			} //public void zagrada
		}); //action listener zagrada
			
			btnNewButton_1.setBounds(360, 101, 85, 21);
			frame.getContentPane().add(btnNewButton_1);
			
////////////////////////////*PREBACIVANJE ODABRANIH PODATAKA U TEXTFILED-OVE*/////////////////////////////////////////////
			
		TFvrsta = new JTextField();
		TFvrsta.setBounds(616, 172, 160, 19);
		frame.getContentPane().add(TFvrsta);
		TFvrsta.setColumns(10);
		
		TFcijena = new JTextField();
		TFcijena.setBounds(616, 236, 96, 19);
		frame.getContentPane().add(TFcijena);
		TFcijena.setColumns(10);
		
		lblNewLabel = new JLabel("Vrsta:");
		lblNewLabel.setBounds(561, 175, 45, 13);
		frame.getContentPane().add(lblNewLabel);
		
		lblNewLabel_3 = new JLabel("Cijena:");
		lblNewLabel_3.setBounds(561, 239, 45, 13);
		frame.getContentPane().add(lblNewLabel_3);
		
		tablica.addMouseListener(new MouseAdapter() 
		{
			public void mouseClicked(MouseEvent e) 
			{
				int odabraniRedak=tablica.getSelectedRow(); //da znamo koji je redak odabran
				
				//popunjavamo TextFieldove  --0 je prvi stupac tablice
				
				TFvrsta.setText(tablica.getValueAt(odabraniRedak, 0).toString());
				TFcijena.setText(tablica.getValueAt(odabraniRedak, 1).toString());
				
				//vezano uz update donjih kodova
				
				vrstaUpdate=tablica.getValueAt(odabraniRedak, 0).toString();
			
			} //public void zagrada
			
		  } //mouse adapter zagrada
				
		); //tablica.addMouseListener zagrada
		
			/////////////////////////////////////////////
		
		JButton btnNewButton_2 = new JButton("Ažuriraj");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				// 1. preuzimanje podataka od korisnika iz textField
				
				String vrstas=TFvrsta.getText();
				String cijenas=TFcijena.getText();
				
				// 2. kreiranje update upita za bazu
				
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","6929");
					
					String upit = "UPDATE RWAclanarina SET  vrsta=?, cijena=? WHERE vrsta=?";
					PreparedStatement ps=con.prepareStatement(upit);
					
					ps.setString(1, vrstas); //1 jer gledamo ? u upitu
					ps.setString(2, cijenas); //na misto drugeg ? dodaj vrijednost koju je korisnik unija u textField-u za cijenu

					//dodajemo kako bi iz upita zna za koji PK 
					ps.setString(3, vrstaUpdate);
					
					//izvršavanje upita
					int updateRedak=ps.executeUpdate();
					
					//obavjest korisnicima
					if(updateRedak > 0) 
					{
						JOptionPane.showMessageDialog(null, "Uspješno ažuriranje!");
						
						//ako je uspješno ažurirano u bazi da tablica prikaže ažurirane podatke
						
						try {
						
							String upit1="SELECT * FROM RWAclanarina";
							Statement stmt=con.createStatement();
							ResultSet rs=stmt.executeQuery(upit1);
							
							DefaultTableModel model=(DefaultTableModel)tablica.getModel();
							
							model.setRowCount(0);//svaki put kad stisnemo batun broj redaka se postavlja na 0
							
							while(rs.next()) {
	
								String vrsta=rs.getString(1);
								Float cijena=rs.getFloat(2);
								
								model.addRow(new Object[] {vrsta, cijena});
								
							} //while
						} //try
						catch(Exception e1) {
							JOptionPane.showMessageDialog(null, "Greška pri dohvaćanju podataka iz baze - nakon ažuriranja");
						}//catch
						
					} //if
					else 
					{
						JOptionPane.showMessageDialog(null, "Neuspješno ažuriranje!");
					}//else
					
				}//try
				catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Nije moguće izvršiti ažuriranje");
				}//catch
		

		}//public void action performed zagrada
		});//add action listener

		
		btnNewButton_2.setBounds(640, 311, 96, 34);
		frame.getContentPane().add(btnNewButton_2);
		
		
		lblNewLabel_2 = new JLabel("ČLANARINE");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_2.setBounds(254, 29, 160, 25);
		frame.getContentPane().add(lblNewLabel_2);
		
		
			
			} //private void inicialize zagrada
	
	public void showWindow() {
		frame.setVisible(true);
	}//public void showWindow


}
