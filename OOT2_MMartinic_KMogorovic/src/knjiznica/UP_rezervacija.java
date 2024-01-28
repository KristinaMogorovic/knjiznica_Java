package knjiznica;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;

public class UP_rezervacija {

	private JFrame frame;
	private JTable tablica;
	private JTextField trazilica;
	private JTextField datum_rezervacije;
	private JTextField TFime_clan;
	private JTextField TFnaziv_knjiga;
	private JTextField TFdatum_rezervacije;
	
	private int id_rezervacijaUpdate; //za izvršenje ažuriranja
	private JTextField TFprezime_clan;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UP_rezervacija window = new UP_rezervacija();
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
	public UP_rezervacija() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 852, 749);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Pregled i unos rezervacija");
		lblNewLabel.setBounds(320, 34, 289, 29);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		frame.getContentPane().add(lblNewLabel);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(20, 89, 784, 588);
		frame.getContentPane().add(tabbedPane_1);
		
		/////////////////////////////////////////////////////////////////*PRIKAZ PODATAKA*//////////////////////////////
		
		JPanel pregled = new JPanel();
		tabbedPane_1.addTab("pregled", null, pregled, null);
		pregled.setLayout(null);
			
			
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 56, 418, 462);
		pregled.add(scrollPane);
		
		tablica = new JTable();
		scrollPane.setViewportView(tablica);
		tablica.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"id_rezervacija", "ime člana", "prezime člana", "naziv knjige", "datum_rezervacije"
			}
				){
			boolean[] columnEditables = new boolean[] {
					false, false, false, false, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
		
		JLabel label = new JLabel("New label");
		scrollPane.setColumnHeaderView(label);
			try
			{
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","6929");
				
				String upit="SELECT r.id_rezervacija, c.ime AS ime_clana, c.prezime AS prezime_clana,k.naziv AS naziv_knjige,  r. datum_rezervacije\r\n"
						+ "FROM RWArezervacija r\r\n"
						+ "inner JOIN RWAclan c ON r.id_clan=c.id_clan\r\n"
						+ "inner JOIN RWAknjiga k ON r.id_knjiga=k.id_knjiga;";
				
				Statement stmt=con.createStatement();
				ResultSet rs=stmt.executeQuery(upit);
				
				DefaultTableModel model=(DefaultTableModel)tablica.getModel();
				model.setRowCount(0);
				
				while(rs.next()) {
	
					int id_rezervacija=rs.getInt(1); 
					String ime_clan=rs.getString(2);
					String prezime_clan=rs.getString(3);
					String naziv_knjige=rs.getString(4);
					Date datum_rezervacije=rs.getDate(5);
					
					model.addRow(new Object[] {id_rezervacija, ime_clan,prezime_clan, naziv_knjige, datum_rezervacije});
					
				} //while
				
			}//try
			
			catch(Exception e1)
			{
				JOptionPane.showMessageDialog(null, e1);
			}//catch
			

		//////////////////////////////////////////////////////////////////////*TRAŽILICA*///////////////////////////////////
		
		trazilica = new JTextField();
		
		JButton btnNewButton_3 = new JButton("Traži");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				String pretragas=trazilica.getText();
			
				try {
					
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","6929");
					
					String upit="SELECT *\r\n"
							+ "FROM ( \r\n"
							+ "	SELECT r.id_rezervacija, c.ime AS ime_clana, c.prezime AS prezime_clana,k.naziv AS naziv_knjige,  r. datum_rezervacije\r\n"
							+ "	FROM RWArezervacija r\r\n"
							+ "	inner JOIN RWAclan c ON r.id_clan=c.id_clan\r\n"
							+ "	inner JOIN RWAknjiga k ON r.id_knjiga=k.id_knjiga"
							+ ") AS tmp\r\n"
							+ "WHERE \r\n"
							+ "	tmp.ime_clana LIKE ? OR \r\n"
							+ "	tmp.prezime_clana LIKE ? OR \r\n"
							+ "	tmp.naziv_knjige LIKE ? OR \r\n"
							+ "	tmp.datum_rezervacije LIKE ? \r\n"
							+ "; \r\n"
							+ "";
							
					PreparedStatement ps=con.prepareStatement(upit);
					ps.setString(1, "%"+pretragas+"%");
					ps.setString(2, "%"+pretragas+"%");
					ps.setString(3, "%"+pretragas+"%");
					ps.setString(4, "%"+pretragas+"%");
					
					ResultSet rs=ps.executeQuery();
					
					DefaultTableModel model=(DefaultTableModel)tablica.getModel();
					
					model.setRowCount(0);
					
					while(rs.next()) {
						
						int id_rezervacija=rs.getInt(1); 
						String ime_clan=rs.getString(2);
						String prezime_clan=rs.getString(3);
						String naziv_knjige=rs.getString(4);
						Date datum_rezervacije=rs.getDate(5);
						
						model.addRow(new Object[] {id_rezervacija, ime_clan,prezime_clan, naziv_knjige, datum_rezervacije});
						
					} //while
				
				} //try
				catch(Exception e1)
				{
					JOptionPane.showMessageDialog(null, "Greška pri pretraživanju!");
				}//catch
				
			} //public void
		}); //action listener
		
		btnNewButton_3.setBounds(684, 65, 85, 21);
		pregled.add(btnNewButton_3);
		
		
		trazilica.setColumns(10);
		trazilica.setBounds(482, 66, 166, 19);
		pregled.add(trazilica);
		
		/////////////////////////////////////////////////////////////*BRISANJE*////////////////////////////////////////////
		
		JButton btnNewButton = new JButton("Obriši");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				///koji je redak označen u dizajnu tablice
				DefaultTableModel model=(DefaultTableModel)tablica.getModel();
				int odabraniRedak=tablica.getSelectedRow();
				
				if(odabraniRedak >= 0) { //je li odabran redak NE KOJI
					
					try
					{
						int id_rezervacije=Integer.parseInt(tablica.getValueAt(odabraniRedak, 0).toString());
						
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","6929");
						
						String upit="DELETE  FROM RWArezervacija WHERE id_rezervacija=?";
						
						PreparedStatement ps=con.prepareStatement(upit);
						ps.setInt(1, id_rezervacije);
						
						int rezultat=ps.executeUpdate();
						
						//provjeravamo dal se izbrisalo u bazi
						if(rezultat==1) {
							
							DefaultTableModel model1=(DefaultTableModel)tablica.getModel();
							model1.removeRow(odabraniRedak);
							
							JOptionPane.showMessageDialog(null, "Rezervacija je uspješno izbrisana!");
							
						} //if drugi
						else
						{
							JOptionPane.showMessageDialog(null, "Rezervaciju nije moguće izbrisati!");
						}//else drugi
						
					}//try
					catch(Exception e1) 
					{
						JOptionPane.showMessageDialog(null, "Greška pri brisanju!");
					} //catch
					
				} //if prvi
				else
				{
					JOptionPane.showMessageDialog(null, "Nije odabran redak tablice!");
				} //else
			
			} //public void
		}); //action listener
		
		btnNewButton.setBounds(503, 378, 85, 31);
		pregled.add(btnNewButton);
		
		/////////////////////////////////////////////////////////////*AŽURIRANJE*////////////////////////////////////////////
		
		////////////*UNOS PODATAKA U TEXTFIELD-OVE*//////////////////////////
		
		TFime_clan = new JTextField();
		TFime_clan.setBounds(553, 178, 141, 19);
		pregled.add(TFime_clan);
		TFime_clan.setColumns(10);
		
		TFprezime_clan = new JTextField();
		TFprezime_clan.setBounds(553, 226, 141, 19);
		pregled.add(TFprezime_clan);
		TFprezime_clan.setColumns(10);
		
		TFnaziv_knjiga = new JTextField();
		TFnaziv_knjiga.setBounds(553, 266, 141, 19);
		pregled.add(TFnaziv_knjiga);
		TFnaziv_knjiga.setColumns(10);
		
		TFdatum_rezervacije = new JTextField();
		TFdatum_rezervacije.setBounds(553, 308, 141, 19);
		pregled.add(TFdatum_rezervacije);
		TFdatum_rezervacije.setColumns(10);
		
		tablica.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent e)
				{
					int odabraniRedak=tablica.getSelectedRow(); //da znamo koji redak je odaberen
					
					//popunjavamo textfieldove sa podacima iz tablice iz dizajna
					
					TFime_clan.setText(tablica.getValueAt(odabraniRedak, 1).toString()); //1 zato jer je 0 id_rezervacija koju ne pisemo
					TFprezime_clan.setText(tablica.getValueAt(odabraniRedak, 2).toString());
					TFnaziv_knjiga.setText(tablica.getValueAt(odabraniRedak, 3).toString()); //odabrani redak je redak u tablici, a 1 je broj stupca iz kojeg vadimo podatak - u gui 
					TFdatum_rezervacije.setText(tablica.getValueAt(odabraniRedak, 4).toString()); //moramo parsirat u string jer je objektnog oblika
				
					//pozivanje podatka o id-ju za ažuriranje
					//parsiramo u int jer je u sql bazi taj tip podatka
					id_rezervacijaUpdate=Integer.parseInt(tablica.getValueAt(odabraniRedak, 0).toString());
				
				}//public void
			
		} //mouseAdapter zagrada
		
		); //zagrada tablica.addMouseListener
		
	/////////////////////////////////////////////////////////////
		
		JButton btnNewButton_1 = new JButton("Ažuriraj");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// preuzimamo podatke koje je korisnik unija u textField
				String ime_clans=TFime_clan.getText();
				String prezime_clans=TFprezime_clan.getText();
				String naziv_knjiges=TFnaziv_knjiga.getText();
				String datum_rezervacijes=TFdatum_rezervacije.getText();
				
				// kreiranje upita u bazu
				
				try 
				{
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","6929");
					
					//selektiranje id clana
					String upitClan="SELECT id_clan FROM RWAclan WHERE ime='"+ime_clans+"' AND prezime='"+prezime_clans+"' ";
					Statement stmClan=con.createStatement();
					ResultSet rsClan=stmClan.executeQuery(upitClan);
					
					int idClan=0; //varijabla u koju ce se spremit rezultat
					
					if(rsClan.next()) {
						idClan=rsClan.getInt(1); //rezultat iz upita sprema u varijablu
					} //if
					
					
					//selektiranje id knjige
					
					String upitKnjiga ="SELECT id_knjiga FROM RWAknjiga WHERE naziv='"+naziv_knjiges+"'  ";
					Statement stmKnjiga=con.createStatement();
					ResultSet rsKnjiga=stmKnjiga.executeQuery(upitKnjiga);
					
					int idKnjiga=0;
					
					if(rsKnjiga.next()) {
						idKnjiga=rsKnjiga.getInt(1);
					} //if
					

					String upit="UPDATE RWArezervacija SET id_clan=?, id_knjiga=?, datum_rezervacije=? WHERE id_rezervacija=?";
					
					PreparedStatement ps=con.prepareStatement(upit);	
					ps.setInt(1, idClan); //1 upitnik -- stavljamo vrijednost od korisnika u textfield za id clana //idClan je varijabla koja ima spremljen id na temelju imena i prezimena
					ps.setInt(2, idKnjiga);
					ps.setString(3, datum_rezervacijes);
					
					//dodajemo za id da zna ča treba ažurirat
					ps.setInt(4, id_rezervacijaUpdate); //4 upitnik
					
					int updateRedak=ps.executeUpdate();
					
					//poruke korisniku
					if(updateRedak > 0) 
					{
						JOptionPane.showMessageDialog(null, "Uspješno ažuriranje!");
						
						//ponovno ažuriranje dohvata podataka u tablici nakon ažuriranja
						
						String upit3="SELECT r.id_rezervacija, c.ime AS ime_clana, c.prezime AS prezime_clana,k.naziv AS naziv_knjige,  r. datum_rezervacije\r\n"
								+ "FROM RWArezervacija r\r\n"
								+ "inner JOIN RWAclan c ON r.id_clan=c.id_clan\r\n"
								+ "inner JOIN RWAknjiga k ON r.id_knjiga=k.id_knjiga;";
						
						Statement stmt=con.createStatement();
						ResultSet rs=stmt.executeQuery(upit3);
						
						DefaultTableModel model=(DefaultTableModel)tablica.getModel();
						model.setRowCount(0);
						
						while(rs.next()) {
			
							int id_rezervacija=rs.getInt(1); 
							String ime_clan=rs.getString(2);
							String prezime_clan=rs.getString(3);
							String naziv_knjige=rs.getString(4);
							Date datum_rezervacije=rs.getDate(5);
							
							model.addRow(new Object[] {id_rezervacija, ime_clan,prezime_clan, naziv_knjige, datum_rezervacije});
							
						} //while
						
					} //if
					else
					{
						JOptionPane.showMessageDialog(null, "Neuspješno ažuriranje!");
					}//else
					
				} //try
				catch(Exception e1) 
				{
					JOptionPane.showMessageDialog(null, "Greška pri ažuriranju!");
				} //catch
				
				
			} //public void
			
		}); //action listener
		
		btnNewButton_1.setBounds(634, 378, 85, 31);
		pregled.add(btnNewButton_1);
		
		///////////////////////////////////////////////////////////////////UNOS REZERVACIJE//////////////////////////////
		
		JLabel lblNewLabel_4 = new JLabel("ime člana:");
		lblNewLabel_4.setBounds(466, 181, 63, 13);
		pregled.add(lblNewLabel_4);
		
		JLabel lblNewLabel_7 = new JLabel("naziv knjige:");
		lblNewLabel_7.setBounds(466, 269, 81, 13);
		pregled.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("Datum:");
		lblNewLabel_8.setBounds(482, 311, 45, 13);
		pregled.add(lblNewLabel_8);
		
		JLabel lblNewLabel_9 = new JLabel("prezime člana:");
		lblNewLabel_9.setBounds(466, 229, 85, 13);
		pregled.add(lblNewLabel_9);
		

		JPanel unos = new JPanel();
		tabbedPane_1.addTab("unos", null, unos, null);
		unos.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("ime člana:");
		lblNewLabel_2.setBounds(39, 105, 96, 13);
		unos.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Prezime člana:");
		lblNewLabel_3.setBounds(39, 138, 107, 13);
		unos.add(lblNewLabel_3);
		
		JLabel lblNewLabel_5 = new JLabel("datum rezervacije:");
		lblNewLabel_5.setBounds(39, 246, 119, 13);
		unos.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("ISBN:");
		lblNewLabel_6.setBounds(393, 141, 45, 13);
		unos.add(lblNewLabel_6);
		
		JLabel lblNewLabel_1 = new JLabel("Naziv:");
		lblNewLabel_1.setBounds(393, 108, 45, 13);
		unos.add(lblNewLabel_1);
		
		datum_rezervacije = new JTextField();
		datum_rezervacije.setBounds(160, 243, 96, 19);
		unos.add(datum_rezervacije);
		datum_rezervacije.setColumns(10);
		
		/////////////////////ime combo box
				
		JComboBox ime_clan = new JComboBox();
		ime_clan.setBounds(144, 101, 112, 21);
		unos.add(ime_clan);
		
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","6929") ;
		
		String upit = "SELECT ime FROM RWAclan;";
		Statement stmt=con.createStatement();
		ResultSet rs=stmt.executeQuery(upit);
		
		while (rs.next()) {
			String podatak =rs.getString(1);
			ime_clan.addItem(podatak);
		}
		}//try
		catch (Exception e1) {
		JOptionPane.showMessageDialog(null, "Greška pri dohvatu imena člana");
		}//catch
		
		/////////////////////prezime combobox
		
		JComboBox prezime_clan = new JComboBox();
		prezime_clan.setBounds(144, 134, 112, 21);
		unos.add(prezime_clan);
		
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","6929") ;
		
		String upit = "SELECT prezime FROM RWAclan;";
		Statement stmt=con.createStatement();
		ResultSet rs=stmt.executeQuery(upit);
		
		while (rs.next()) {
			String podatak =rs.getString(1);
			prezime_clan.addItem(podatak);
		}
		}//try
		catch (Exception e1) {
		JOptionPane.showMessageDialog(null, "Greška pri dohvaćanju prezimena člana");
		}//catch
		
		///////////////////////////////// NAZIV KNJIGE COMBOBOX
		
		JComboBox naziv = new JComboBox();
		naziv.setBounds(448, 101, 196, 21);
		unos.add(naziv);
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","6929") ;
			
			String upit = "SELECT naziv FROM RWAknjiga;";
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery(upit);
			
			while (rs.next()) {
				String podatak =rs.getString(1);
				naziv.addItem(podatak);
			}
		}//try
		catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "Greška pri povlačenju podataka o nazivu knjige.");
		}//catch
		
		//////////////////////////////////////ISBN COMBOBOX
		
		JComboBox isbn = new JComboBox();
		isbn.setBounds(448, 134, 196, 21);
		unos.add(isbn);
		
		naziv.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        // Get the selected item when an item is chosen
		        String odabranaKnjiga = (String) naziv.getSelectedItem();

		        try {
		            Class.forName("com.mysql.cj.jdbc.Driver");
		            Connection con = DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC", "kmogorovi", "6929");

		            String upit = "SELECT isbn FROM RWAknjiga WHERE naziv=?";
		           
		            PreparedStatement psInsertISBNknjiga = con.prepareStatement(upit);
		            psInsertISBNknjiga.setString(1, odabranaKnjiga);

		            ResultSet rs = psInsertISBNknjiga.executeQuery();

		            isbn.removeAllItems();

		            while (rs.next()) {
		                String podatak = rs.getString(1);
		                isbn.addItem(podatak);
		            }
		        } 
		        catch (Exception e1) {
		            JOptionPane.showMessageDialog(null, e1);
		        }
		    }
		});
		
		///////////////////////////////////////////////////////////////////SPREMANJE//////////////////////////////
		
		JButton btnNewButton_2 = new JButton("spremi");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
			String imes, prezimes, isbns, nazivs, datums;
			
			imes=(String)ime_clan.getSelectedItem();
			prezimes=(String) prezime_clan.getSelectedItem();
			isbns=(String)isbn.getSelectedItem();
			nazivs=(String)naziv.getSelectedItem();
			datums=datum_rezervacije.getText();
			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","6929") ;
				
				//selektiranje id clana
				String upitClan="SELECT id_clan FROM RWAclan WHERE ime='"+imes+"' AND prezime='"+prezimes+"' ";
				Statement stmClan=con.createStatement();
				ResultSet rsClan=stmClan.executeQuery(upitClan);
				
				int idClan=0; //varijabla u koju ce se spremit rezultat
				
				if(rsClan.next()) {
					idClan=rsClan.getInt(1); //rezultat iz upita sprema u varijablu
				} //if
				
				//selektiranje id knjige
				
				String upitKnjiga ="SELECT id_knjiga FROM RWAknjiga WHERE isbn='"+isbns+"' AND naziv='"+nazivs+"' ";
				Statement stmKnjiga=con.createStatement();
				ResultSet rsKnjiga=stmKnjiga.executeQuery(upitKnjiga);
				
				int idKnjiga=0;  //varijabla u koju ce se spremit rezultat
				
				if(rsKnjiga.next()) {
					idKnjiga=rsKnjiga.getInt(1); //rezultat iz upita sprema u varijablu
				} //if 

				////upis u rezervaciju
				
				String upit_posudba="INSERT INTO RWArezervacija (id_clan, id_knjiga, datum_rezervacije) VALUES (?,?,?)";
				PreparedStatement ps=con.prepareStatement(upit_posudba);
				
				ps.setInt(1, idClan); // inserta se rezultat iz varijeble idClan
				ps.setInt(2, idKnjiga);
				ps.setString(3, datums);
				
				int redakaUbaceno=ps.executeUpdate();
				
				if(redakaUbaceno==1) {
					JOptionPane.showMessageDialog(null, "Rezervacija je spremljena");
				}//if
				else {
					JOptionPane.showMessageDialog(null, "Spremanje neuspješno!");
				}//else

			} //try
			catch(Exception e1) {
				JOptionPane.showMessageDialog(null, e1);
			} //catch
			
			
			} //public void
		}); //action listener
		btnNewButton_2.setBounds(472, 241, 85, 21);
		unos.add(btnNewButton_2);
		
	
	}
	
	public void showWindow() {
		frame.setVisible(true);
	}//public void showWindow
}
