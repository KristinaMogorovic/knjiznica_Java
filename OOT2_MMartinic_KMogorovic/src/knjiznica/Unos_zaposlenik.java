package knjiznica;

import java.sql.*;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.sql.Statement;
import javax.swing.SwingConstants;
import java.awt.Font;

//unos zaposlenika bla bla bla

public class Unos_zaposlenik {

	private JFrame frame;
	private JTextField sifra;
	private JTextField ime;
	private JTextField prezime;
	private JTextField OIB;
	private JTextField kontakt;
	private JTextField adresa;
	private JPasswordField lozinka;
	private JPasswordField ponLozinka;
	private JTable tablica;
	private JTextField imeUpdate;
	private JTextField prezimeUpdate;
	
	private int sifraKnjiznicaraUpdate;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Unos_zaposlenik window = new Unos_zaposlenik();
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
	public Unos_zaposlenik() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 915, 571);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("šifra");
		lblNewLabel.setBounds(37, 48, 46, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("ime");
		lblNewLabel_1.setBounds(37, 79, 46, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("prezime");
		lblNewLabel_2.setBounds(267, 79, 46, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("OIB");
		lblNewLabel_3.setBounds(37, 107, 46, 14);
		frame.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("kontakt");
		lblNewLabel_4.setBounds(37, 135, 46, 14);
		frame.getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("adresa");
		lblNewLabel_5.setBounds(37, 163, 46, 14);
		frame.getContentPane().add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("lozinka");
		lblNewLabel_6.setBounds(37, 214, 46, 14);
		frame.getContentPane().add(lblNewLabel_6);
		
		sifra = new JTextField();
		sifra.setBounds(93, 45, 86, 20);
		frame.getContentPane().add(sifra);
		sifra.setColumns(10);
		
		ime = new JTextField();
		ime.setBounds(93, 76, 127, 20);
		frame.getContentPane().add(ime);
		ime.setColumns(10);
		
		prezime = new JTextField();
		prezime.setBounds(323, 76, 127, 20);
		frame.getContentPane().add(prezime);
		prezime.setColumns(10);
		
		OIB = new JTextField();
		OIB.setBounds(93, 104, 127, 20);
		frame.getContentPane().add(OIB);
		OIB.setColumns(10);
		
		kontakt = new JTextField();
		kontakt.setBounds(93, 132, 127, 20);
		frame.getContentPane().add(kontakt);
		kontakt.setColumns(10);
		
		adresa = new JTextField();
		adresa.setBounds(93, 160, 173, 20);
		frame.getContentPane().add(adresa);
		adresa.setColumns(10);
		
		lozinka = new JPasswordField();
		lozinka.setBounds(134, 211, 86, 20);
		frame.getContentPane().add(lozinka);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(64, 303, 463, 192);
		frame.getContentPane().add(scrollPane);
		
		tablica = new JTable();
		tablica.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\u0161ifra", "ime", "prezime"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(tablica);
		
		//ispis u tablicu
		DefaultTableModel model =(DefaultTableModel) tablica.getModel(); /////
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","6929") ;
			
			String upit = "SELECT sifra_knjiznicar, ime, prezime FROM RWA_knjiznicar";
			Statement stmt=con.createStatement();//pripremanje "tunela" za slanje upita
			ResultSet rs=stmt.executeQuery(upit);
			while (rs.next()) {
				//preuzimanje podatka iz baze
				int sifra=rs.getInt(1);
				String ime=rs.getString(2);
				String prezime=rs.getString(3);

				//stavljanje podatka u tablicu
				model.addRow(new Object[] {sifra, ime, prezime});
			}
		}//try
		catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "Greška u prikazu podataka tablice");
		}//catch
		
		//UPIS ZAPOSLENIKA U BAZU
		JButton btnNewButton = new JButton("spremi");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String sifras, imes, prezimes, oibs, kontakts, adresas,ponLozinkas, lozinkas; 
				
				sifras=sifra.getText();
				imes=ime.getText();
				prezimes=prezime.getText();
				oibs=OIB.getText();
				kontakts=kontakt.getText();
				adresas=adresa.getText();
				lozinkas = new String (lozinka.getPassword());
				ponLozinkas = new String (ponLozinka.getPassword());

				try {
					if (lozinkas.equals(ponLozinkas)) { //ako se lozinke podudaraju
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","6929") ;
						
						//provjera dal sifra zaposlenika vec postoji
						String provjera = "SELECT * FROM RWA_knjiznicar WHERE sifra_knjiznicar=?";
						PreparedStatement ps=con.prepareStatement(provjera);
						ps.setString(1, sifras);
						ResultSet rs=ps.executeQuery();		
						
						if (rs.next()) 
							JOptionPane.showMessageDialog(null, "Ova šifra već postoji. Izaberite drugu.");
						
						else {
							String upit="INSERT INTO RWA_knjiznicar (sifra_knjiznicar, ime, prezime, OIB, kontakt, adresa, lozinka) VALUES(NULL, ?, ?, ?, ?, ?, ?)";
							PreparedStatement psInsert=con.prepareStatement(upit); 
							//ps.setString(1,sifras);
							psInsert.setString(1, imes); //na koj upitnik se referira - prvi, tamo se upisuje ime iz textField-a  //ps je objekt koj zna poslat upit
							psInsert.setString(2, prezimes); //na koj upitnik se referira - drugi, tamo se upisuje prezime iz textField-a
							psInsert.setString(3, oibs); //na koj upitnik se referira - treći, tamo se upisuje god 
							psInsert.setString(4, kontakts); //na koj upitnik se referira - cetvrti, tamo se upisuje spol 
							psInsert.setString(5, adresas);
							psInsert.setString(6, lozinkas);
							
							int redakaUbaceno = psInsert.executeUpdate();
							if (redakaUbaceno==1) {
								JOptionPane.showMessageDialog(null, "Unos uspješan");
							}//if
							else {
								JOptionPane.showMessageDialog(null, "Greška pri unosu.");
							}//else
						}//else
							
					}//if
					else JOptionPane.showMessageDialog(null, "Lozinke se ne podudaraju");
					

				}//try
				catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Greška pri spajanju na server.");
				}
				
				//ispis u tablicu
				DefaultTableModel model =(DefaultTableModel) tablica.getModel(); /////
				model.setRowCount(0);
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","6929") ;
					
					String upit = "SELECT sifra_knjiznicar, ime, prezime FROM RWA_knjiznicar";
					Statement stmt=con.createStatement();//pripremanje "tunela" za slanje upita
					ResultSet rs=stmt.executeQuery(upit);
					while (rs.next()) {
						//preuzimanje podatka iz baze
						int sifra=rs.getInt(1);
						String ime=rs.getString(2);
						String prezime=rs.getString(3);

						//stavljanje podatka u tablicu
						model.addRow(new Object[] {sifra, ime, prezime});
					}
				}//try
				catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Greška u prikazu podataka tablice");
				}//catch
				
				

			}
		});
		btnNewButton.setBounds(389, 159, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_7 = new JLabel("ponovljena lozinka");
		lblNewLabel_7.setBounds(21, 247, 127, 14);
		frame.getContentPane().add(lblNewLabel_7);
		
		ponLozinka = new JPasswordField();
		ponLozinka.setBounds(134, 244, 86, 20);
		frame.getContentPane().add(ponLozinka);
		
		
	////////////////////////////////////////////////////////////////BRISANJE//////////////////////////////////////////
		
		JButton btnNewButton_1 = new JButton("obriši");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {//brisanje odabranog reda
				//sql naredba = DELETE FROM RWA_knjiznicar WHERE sifra_knjiznicar=?;
				
				//znat koji je redak odabran u tablici u dizajnu
				
				DefaultTableModel model=(DefaultTableModel)tablica.getModel();
				int odabraniRedak=tablica.getSelectedRow();
				
				if(odabraniRedak >= 0) { //je li odabran NE KOJI
					try {
						
						int sifra=Integer.parseInt(tablica.getValueAt(odabraniRedak, 0).toString());
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","6929");
						
						String upit="DELETE FROM RWA_knjiznicar WHERE sifra_knjiznicar=?";
						PreparedStatement ps=con.prepareStatement(upit);
						
						ps.setInt(1,sifra); //1 prvo misto
						
						int rezultat=ps.executeUpdate();
						
						if(rezultat==1) {
							
							DefaultTableModel model1=(DefaultTableModel)tablica.getModel();
							model1.removeRow(odabraniRedak);
							
							JOptionPane.showMessageDialog(null, "Knjižničar je uspješno izbrisan!");
							
						} //if drugi
						else 
						{
							JOptionPane.showMessageDialog(null, "Knjižničara nije moguće izbrisati!");
						} //else drugi
						
					}//try
					catch(Exception e1) 
					{
						JOptionPane.showMessageDialog(null, "Knjižničar je zapisan na posudbi. Ne možete ga izbrisati.");
					} //catch
					
				} //if prvi
				else 
				{
					JOptionPane.showMessageDialog(null, "Nije odabran redak tablice!");
				} //else drugi
				
			}
		});
		btnNewButton_1.setBounds(731, 472, 145, 23);
		frame.getContentPane().add(btnNewButton_1);
///////////////////////////////////////////////////////////////////////AŽURIRAJ////////////////////////////////////////////////////////		
		
		////////////////////////////////////////////////UNOS PODATAKA U TEXTFIELD////////////////////////
		
		imeUpdate = new JTextField();
		imeUpdate.setColumns(10);
		imeUpdate.setBounds(605, 350, 127, 20);
		frame.getContentPane().add(imeUpdate);
		
		JLabel lblNewLabel_1_1 = new JLabel("ime");
		lblNewLabel_1_1.setBounds(549, 353, 46, 14);
		frame.getContentPane().add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_2_1 = new JLabel("prezime");
		lblNewLabel_2_1.setBounds(549, 400, 46, 14);
		frame.getContentPane().add(lblNewLabel_2_1);
		
		prezimeUpdate = new JTextField();
		prezimeUpdate.setColumns(10);
		prezimeUpdate.setBounds(605, 397, 127, 20);
		frame.getContentPane().add(prezimeUpdate);
		
		
		tablica.addMouseListener(new MouseAdapter() 
		{
			public void mouseClicked(MouseEvent e) 
			{
				int odabraniRedak=tablica.getSelectedRow(); //da znamo koji je redak odabran
				
				//popunjavamo TextFieldove - počinjemo od 1 jer je 0 id, a njega ne pisemo u textfield
				
				imeUpdate.setText(tablica.getValueAt(odabraniRedak, 1).toString());
				prezimeUpdate.setText(tablica.getValueAt(odabraniRedak, 2).toString());
				
				//vezano uz update donjih kodova
				
				sifraKnjiznicaraUpdate=Integer.parseInt(tablica.getValueAt(odabraniRedak, 0).toString());
			} //public void zagrada
			
		  } //mouse adapter zagrada
				
		); //tablica.addMouseListener zagrada
		
		
		
		
		JButton btnNewButton_2 = new JButton("spremi promjene");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
// 1. preuzimanje podataka od korisnika iz textField
				
				String imes=imeUpdate.getText();
				String prezimes=prezimeUpdate.getText();
				
				// 2. kreiranje update upita za bazu
				
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","6929");
					
					String upit = "UPDATE RWA_knjiznicar SET  ime=?, prezime=? WHERE sifra_knjiznicar=?";
					PreparedStatement ps=con.prepareStatement(upit);
					
					ps.setString(1, imes); //1 jer gledamo ? u upitu
					ps.setString(2, prezimes); //na misto drugeg ? dodaj vrijednost koju je korisnik unija u textField-u za prezime

					//dodajemo kako bi iz upita zna za koji id --> id iz odabraneg retka
					ps.setInt(3, sifraKnjiznicaraUpdate);
					
					//izvršavanje upita
					int updateRedak=ps.executeUpdate();
					
					//obavjest korisnicima
					if(updateRedak > 0) 
					{
						JOptionPane.showMessageDialog(null, "Uspješno ažuriranje!");
						
						//ako je uspješno ažurirano u bazi da tablica prikaže ažurirane podatke
						
						String upit1="SELECT * FROM RWA_knjiznicar";
						Statement stmt=con.createStatement();
						ResultSet rs=stmt.executeQuery(upit1);
						
						DefaultTableModel model=(DefaultTableModel)tablica.getModel();
						
						model.setRowCount(0);//svaki put kad stisnemo batun broj redaka se postavlja na 0
						
						while(rs.next()) {

							int sifra_knjiznicar=rs.getInt(1); 
							String ime=rs.getString(2);
							String prezime=rs.getString(3);
							
							model.addRow(new Object[] {sifra_knjiznicar, ime, prezime});
							
						} //while
						
					} //if
					else 
					{
						JOptionPane.showMessageDialog(null, "Neuspješno ažuriranje!");
					}//else
					
				}//try
				catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Nije moguće izvršiti ažuriranje");
				}//catch
				
			}
		});
		btnNewButton_2.setBounds(550, 472, 145, 23);
		frame.getContentPane().add(btnNewButton_2);
		
		
		JLabel lblNewLabel_8 = new JLabel("UNOS ZAPOSLENIKA");
		lblNewLabel_8.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_8.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_8.setBounds(345, 10, 212, 23);
		frame.getContentPane().add(lblNewLabel_8);
		
		
	}
	
	public void showWindow() {
		frame.setVisible(true);
	}//public void showWindow
}
