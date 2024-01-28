package knjiznica;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Font;

public class Pregled_knjiga {

	private JFrame frame;
	private JTable tablica;
	private JTextField trazilica;
	
	private int id_knjigaUpdate; //za izvršavanje ažuriranja
	private JTextField brPrimjeraka;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Pregled_knjiga window = new Pregled_knjiga();
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
	public Pregled_knjiga() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1039, 629);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	
		//////////////////////////////////////////////////*PRIKAZ PODATAKA*////////////////////////////////////
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(40, 173, 975, 226);
		frame.getContentPane().add(scrollPane);
		
		tablica = new JTable();
		scrollPane.setViewportView(tablica);
		tablica.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"id_knjiga", "naziv", "ISBN", "nakladnik", "Odjeljak", "ime autora","prezime autora",  "ukupan_broj", "rezervirani", "posudeni"
					
			}
		){
			boolean[] columnEditables = new boolean[] {
					false, false, false, false, false, false, false, false, false, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
			
			try
			{
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","6929");
				
				
				String upit="SELECT k.id_knjiga, k.naziv AS naziv_knjige, k.isbn, k.nakladnik, kl.odjeljak, a.ime AS ime_autora, a.prezime AS prezime_autora,k.ukupan_broj,k.rezervirani, k.posudeni\r\n"
						+ "FROM RWAknjiga k\r\n"
						+ "INNER JOIN RWAklasifikacijska_oznaka kl ON kl.id_KlasOznake=k.id_KlasOznake\r\n"
						+ "INNER JOIN RWAautor_knjiga ak ON ak.id_knjiga=k.id_knjiga\r\n"
						+ "INNER JOIN RWAautor a ON ak.id_autor=a.id_autor;";
				
				Statement stmt=con.createStatement();
				ResultSet rs=stmt.executeQuery(upit);
				
				DefaultTableModel model=(DefaultTableModel)tablica.getModel();
				
				while(rs.next()) {

					//
					int id_knjiga=rs.getInt(1);  //1. indeks kreirane tablice rezultata upita -->1. stupac kreirane tablice je 1. indeks
					String naziv_knjige=rs.getString(2);
					String isbn=rs.getString(3);
					String nakladnik=rs.getString(4);
					String odjeljak=rs.getString(5);
					String ime_autora=rs.getString(6);
					String prezime_autora=rs.getString(7);
					int ukupan_broj=rs.getInt(8);
					int rezervirani=rs.getInt(9);
					int posudeni=rs.getInt(10);
					
					
					model.addRow(new Object[] {id_knjiga, naziv_knjige, isbn, nakladnik, odjeljak, ime_autora, prezime_autora, ukupan_broj, rezervirani, posudeni});
					
					
				} //while
				
			}//try
			
			catch(Exception e1)
			{
				JOptionPane.showMessageDialog(null, "Greška u dohvatu podataka!");
			}
		///////////////////////////////////////////////////////////*TRAŽILICA*/////////////////////////////////////////////////
		trazilica = new JTextField();
		
		JButton btnNewButton_2 = new JButton("Traži");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String pretragas=trazilica.getText();
				
					try {
										
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","6929");
						
								String upit1="SELECT *\r\n"
								+ "FROM (\r\n"
								+ "SELECT k.id_knjiga, k.naziv AS naziv_knjige,  k.isbn, k.nakladnik, kl.odjeljak, a.ime AS ime_autora, a.prezime AS prezime_autora, k.ukupan_broj, k.rezervirani, k.posudeni\r\n"
								+ "FROM RWAknjiga k\r\n"
								+ "INNER JOIN RWAklasifikacijska_oznaka kl ON kl.id_KlasOznake=k.id_KlasOznake \r\n"
								+ "INNER JOIN RWAautor_knjiga ak ON ak.id_knjiga=k.id_knjiga \r\n"
								+ "INNER JOIN RWAautor a ON ak.id_autor=a.id_autor\r\n"
								+ ") AS tmp \r\n"
								+ "WHERE \r\n"
								+ "tmp.naziv_knjige LIKE ? OR\r\n"
								+ "tmp.isbn LIKE ? OR\r\n"
								+ "tmp.nakladnik LIKE ? OR\r\n"
								+ "tmp.odjeljak LIKE ? OR\r\n"
								+ "tmp.ime_autora LIKE ? OR\r\n"
								+ "tmp.prezime_autora LIKE ? OR\r\n"
								+ "tmp.ukupan_broj LIKE ? OR\r\n"
								+ "tmp.rezervirani LIKE ? OR\r\n"
								+ "tmp.posudeni LIKE ? \r\n"
								+"	;\r\n"
								+"";
								
								
						
						PreparedStatement ps=con.prepareStatement(upit1);
						ps.setString(1, "%"+pretragas+"%"); 
						ps.setString(2, "%"+pretragas+"%");
						ps.setString(3, "%"+pretragas+"%");
						ps.setString(4, "%"+pretragas+"%");
						ps.setString(5, "%"+pretragas+"%");
						ps.setString(6, "%"+pretragas+"%");
						ps.setString(7, "%"+pretragas+"%");
						ps.setString(8, "%"+pretragas+"%");
						ps.setString(9, "%"+pretragas+"%");
					//	ps.setString(10, "%"+pretragas+"%");
						
						
						ResultSet rs=ps.executeQuery();
						
						DefaultTableModel model=(DefaultTableModel)tablica.getModel();
						
						model.setRowCount(0);
						
						
						//od gore while za pretragu:
						while(rs.next()) { // po bazi

							//
							int id_knjiga=rs.getInt(1);  //1. indeks kreirane tablice rezultata upita -->1. stupac kreirane tablice je 1. indeks
							String naziv_knjige=rs.getString(2);
							String isbn=rs.getString(3);
							String nakladnik=rs.getString(4);
							String odjeljak=rs.getString(5);
							String ime_autora=rs.getString(6);
							String prezime_autora=rs.getString(7);
							int ukupan_broj=rs.getInt(8);
							int rezervirani=rs.getInt(9);
							int posudeni=rs.getInt(10);
							
							
							model.addRow(new Object[] {id_knjiga, naziv_knjige, isbn, nakladnik, odjeljak, ime_autora, prezime_autora, ukupan_broj, rezervirani, posudeni});
							
							
						} //while
						
					} //try
					catch(Exception e1) 
					{
						JOptionPane.showMessageDialog(null, e1);
					} //catch
			
			
			} //public void
		}); //action listener zagrada
		
		
		btnNewButton_2.setBounds(370, 97, 85, 28);
		frame.getContentPane().add(btnNewButton_2);
		
		trazilica.setBounds(96, 97, 222, 28);
		frame.getContentPane().add(trazilica);
		trazilica.setColumns(10);
		
		/////////////////////////////////////////////////////////////*UNESI NOVU KNJIGU*////////////////////////////////
		
		
		JButton btnNewButton = new JButton("Unesi novu knjigu");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Unos_knjiga uk = new Unos_knjiga();
				uk.showWindow();
			
			}//public void
		});//action listener
		
		btnNewButton.setBounds(707, 91, 165, 38);
		frame.getContentPane().add(btnNewButton);
		
		/////////////////////////////////////////////////////////////*BRISANJE PODATAKA*///////////////////////////////////
		
		
		JButton btnNewButton_1 = new JButton("Obriši");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				DefaultTableModel model=(DefaultTableModel)tablica.getModel();
				
				int odabraniRedak=tablica.getSelectedRow();
				
				if(odabraniRedak >=0) { //ako je odabran neki redak
					
					try {
						
						int id_knjiga=Integer.parseInt(tablica.getValueAt(odabraniRedak, 0).toString()); //parsiranje jer je id tip int u heidi
						
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","6929");
						
						//najprije treba izbrisat podatak child tablice da bi se moga podatak u parent izbrisat
						
						String upit="DELETE FROM RWAautor_knjiga WHERE id_knjiga=?";
						String upit1="DELETE FROM RWAknjiga WHERE id_knjiga=?";
						
						PreparedStatement ps=con.prepareStatement(upit);
						PreparedStatement ps1=con.prepareStatement(upit1);
						
						ps.setInt(1, id_knjiga); //1. upitnik
						
						ps1.setInt(1, id_knjiga);
						
						int rezultat=ps.executeUpdate();
						int rezultat1=ps1.executeUpdate();
						
						
						if(rezultat==1 && rezultat1==1 ) //ako se je upit izvrsia na bazi
						//if(rezultat1==1)
						{
							DefaultTableModel model1=(DefaultTableModel)tablica.getModel();
							
							model1.removeRow(odabraniRedak);
							
							JOptionPane.showMessageDialog(null, "Knjiga je uspješno izbrisana!");
						}//if drugi
						else
						{
							JOptionPane.showMessageDialog(null, "Knjigu nije moguće izbrisati!");
						} //else drugi
						
					} //try
					catch(Exception e1)
					{
						JOptionPane.showMessageDialog(null, "Gre");
					} //catch
					
				} //if prvi
				else
				{
					JOptionPane.showMessageDialog(null, "Nije odabran redak tablice!");
				} //else prvi
			
			}//public void
		});//action listener
		
		btnNewButton_1.setBounds(276, 504, 95, 45);
		frame.getContentPane().add(btnNewButton_1);
		
		////////////////////////////////////////////////////////////*AŽURIRAJ PODATKE*/////////////////////////////////////
		
													///////UNOS U TEXTFIELD/////////////
		brPrimjeraka = new JTextField();
		brPrimjeraka.setBounds(139, 442, 165, 19);
		frame.getContentPane().add(brPrimjeraka);
		brPrimjeraka.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Broj primjeraka");
		lblNewLabel.setBounds(40, 445, 103, 13);
		frame.getContentPane().add(lblNewLabel);
		
		
		tablica.addMouseListener(new MouseAdapter()
				
		{
			public void mouseClicked(MouseEvent e) 
			{
				int odabraniRedak=tablica.getSelectedRow(); //da znamo koji je redak odabran
				
				//popunjavamo TextFieldove - počinjemo od 1 jer je 0 id, a njega ne pisemo u textfield
				
				brPrimjeraka.setText(tablica.getValueAt(odabraniRedak, 7).toString()); //7 jer je u tablici 7. stupac -->broji se od 0
				
				id_knjigaUpdate=Integer.parseInt(tablica.getValueAt(odabraniRedak, 0).toString());
			
			} //public void zagrada
			
		  } //mouse adapter zagrada
				
		); //tablica.addMouseListener zagrada
		
		
		///////////////////
		
		JButton btnNewButton_1_1 = new JButton("Ažuriraj");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// 1. preuzimanje podataka od korisnika iz textField
				String brprimjerkas=brPrimjeraka.getText();
				
				// 2. kreiranje update upita za bazu
				
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","6929") ;
					
					String upit="UPDATE RWAknjiga SET ukupan_broj=? WHERE id_knjiga=?;";
					PreparedStatement ps=con.prepareStatement(upit);
					
					ps.setString(1,brprimjerkas); //1. upitnik
					
					ps.setInt(2, id_knjigaUpdate); //2. upitnik
					
					int updateRedak=ps.executeUpdate();
					
					if(updateRedak >0) { //ako je ažurirano u tablici
						JOptionPane.showMessageDialog(null, "Uspješno ažuriranje!");
						
						try {
							
							String upit2="SELECT k.id_knjiga, k.naziv AS naziv_knjige, k.isbn, k.nakladnik, kl.odjeljak, a.ime AS ime_autora, a.prezime AS prezime_autora,k.ukupan_broj,k.rezervirani, k.posudeni\r\n"
									+ "FROM RWAknjiga k\r\n"
									+ "INNER JOIN RWAklasifikacijska_oznaka kl ON kl.id_KlasOznake=k.id_KlasOznake\r\n"
									+ "INNER JOIN RWAautor_knjiga ak ON ak.id_knjiga=k.id_knjiga\r\n"
									+ "INNER JOIN RWAautor a ON ak.id_autor=a.id_autor;";
							
							Statement stmt=con.createStatement();
							ResultSet rs=stmt.executeQuery(upit2);
							
							DefaultTableModel model=(DefaultTableModel)tablica.getModel();
							
							model.setRowCount(0);
							
							while(rs.next()) {

								//
								int id_knjiga=rs.getInt(1);  //1. indeks kreirane tablice rezultata upita -->1. stupac kreirane tablice je 1. indeks
								String naziv_knjige=rs.getString(2);
								String isbn=rs.getString(3);
								String nakladnik=rs.getString(4);
								String odjeljak=rs.getString(5);
								String ime_autora=rs.getString(6);
								String prezime_autora=rs.getString(7);
								int ukupan_broj=rs.getInt(8);
								int rezervirani=rs.getInt(9);
								int posudeni=rs.getInt(10);
								
								
								model.addRow(new Object[] {id_knjiga, naziv_knjige, isbn, nakladnik, odjeljak, ime_autora, prezime_autora, ukupan_broj, rezervirani, posudeni});
								
								
							} //while
							
						}//try unutarnji
						catch(Exception e2) {
							JOptionPane.showMessageDialog(null, "Greška u dohvatu podataka!");
						}//catch unutarnji
						
					}//if
					
					else {
						JOptionPane.showMessageDialog(null, "Ažuriranje neuspješno");
						
					}//else
					
					}//try 
					

				catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Greška pri ažuriranju");
				}//catch
			
			}//public void
		});//action listener
		
		btnNewButton_1_1.setBounds(96, 504, 95, 45);
		frame.getContentPane().add(btnNewButton_1_1);
		
		
		
		JLabel lblNewLabel_1 = new JLabel("PREGLED KNJIGA");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(387, 31, 222, 28);
		frame.getContentPane().add(lblNewLabel_1);
		
		

	}//public void inicialize
	
	public void showWindow() {
		frame.setVisible(true);
	}//public void showWindow

}
