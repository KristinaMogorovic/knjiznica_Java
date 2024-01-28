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
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;

public class Pregled_clan {

	private JFrame frame;
	private JTable tablica;
	private JTextField trazilica;
	
	private int id_clanUpdate; //za izvršavanje ažuriranja
	
	private JTextField ime;
	private JTextField prezime;
	private JTextField kontakt;
	private JTextField adresa;
	private JTextField datum_upisa;
	private JTextField datum_isteka;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Pregled_clan window = new Pregled_clan();
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
	public Pregled_clan() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1379, 695);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(41, 174, 1314, 198);
		frame.getContentPane().add(scrollPane);
		
		tablica = new JTable();
		scrollPane.setViewportView(tablica);
		tablica.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				 "id_clan","ime", "prezime", "kontakt", "adresa", "clanarina",  "datum_isteka","datum_upisa"
			}
		){
			boolean[] columnEditables = new boolean[] {
					false, false, false, false, false, false, false, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
			
			try
			{
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","6929");
				
				String upit="SELECT * FROM RWAclan";
				Statement stmt=con.createStatement();
				ResultSet rs=stmt.executeQuery(upit);
				
				DefaultTableModel model=(DefaultTableModel)tablica.getModel();
				
				while(rs.next()) {

					int id_clan=rs.getInt(1); //po tablici u bazi brojevi
					String ime=rs.getString(2);
					String prezime=rs.getString(3);
					String kontakt=rs.getString(4);
					String adresa=rs.getString(5);
					
					String clanarina=rs.getString(7);
					String datum_isteka=rs.getString(9);
					String datum_upisa_string=rs.getString(8);
					
					model.addRow(new Object[] {  id_clan, ime, prezime,kontakt, adresa, clanarina, datum_isteka, datum_upisa_string});
					
				} //while
				
			}//try
			
			catch(Exception e1)
			{
				JOptionPane.showMessageDialog(null, "Došlo je do greške pri prikazu podataka");
			}
		
	///////////////////////////////////////////////////////////*TRAŽILICA*///////////////////////////////////
		trazilica = new JTextField();
		
		JButton btnNewButton_3 = new JButton("Traži");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String pretragas=trazilica.getText();
				
				try
				{
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","6929");
					
					String upit="SELECT * FROM RWAclan WHERE ime LIKE ? OR prezime LIKE ? OR kontakt LIKE ? OR adresa LIKE ? OR clanarina LIKE ? OR  datum_isteka LIKE ?  OR datum_upisa LIKE ? ";
					
					PreparedStatement ps=con.prepareStatement(upit);
					ps.setString(1, "%"+pretragas+"%");
					ps.setString(2, "%"+pretragas+"%");
					ps.setString(3, "%"+pretragas+"%");
					ps.setString(4, "%"+pretragas+"%");
					ps.setString(5, "%"+pretragas+"%");
					ps.setString(6, "%"+pretragas+"%");
					ps.setString(7, "%"+pretragas+"%");
					
					ResultSet rs=ps.executeQuery();
					
					DefaultTableModel model=(DefaultTableModel)tablica.getModel();
					
					model.setRowCount(0);
					
					while(rs.next()) //prema brojevima u bazi
					{
						int id_clan=rs.getInt(1);
						String ime=rs.getString(2);
						String prezime=rs.getString(3);
						String kontakt=rs.getString(4);
						String adresa=rs.getString(5);
						
						String clanarina=rs.getString(7);
					
						String datum_isteka=rs.getString(9);
						String datum_upisa_string=rs.getString(8);
						
						model.addRow(new Object[] {id_clan, ime, prezime,kontakt, adresa, clanarina, datum_isteka, datum_upisa_string});
						
					} //while
					
				} //try
				catch(Exception e1) 
				{
					JOptionPane.showMessageDialog(null, "Greška pri pretaživanju!");
				} //catch
				
				
			} //public void
		}); //action listener
		
		
		btnNewButton_3.setBounds(399, 98, 85, 30);
		frame.getContentPane().add(btnNewButton_3);
		
		trazilica.setBounds(106, 98, 239, 30);
		frame.getContentPane().add(trazilica);
		trazilica.setColumns(10);
		
		
		//////////////////////////////*PROZOR UNOS NOVOG ČLANA*////////////////////////////////////////////////
		
		
		JButton btnNewButton = new JButton("Unesi novog člana");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Unos_clan uc = new Unos_clan(); 
					uc.showWindow();
				}
				catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Došlo je do greške pri otvaranju novog prozora");
				}//catch
			
			}//public void
		});//action listener
		
		btnNewButton.setBounds(744, 86, 147, 42);
		frame.getContentPane().add(btnNewButton);
		
		
		//////////////////////////////////////////*BRISANJE PODATAKA*/////////////////////////////////////////////////////
		
		
		JButton btnNewButton_1 = new JButton("Obriši");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				DefaultTableModel model=(DefaultTableModel)tablica.getModel();
				
				int odabraniRedak=tablica.getSelectedRow();
				
				if(odabraniRedak >=0) {
					try {
						
						int id_clan=Integer.parseInt(tablica.getValueAt(odabraniRedak, 0).toString());
						
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","6929");
							
						String upit="DELETE FROM RWAclan WHERE id_clan=?";
						
						PreparedStatement ps=con.prepareStatement(upit);
						ps.setInt(1, id_clan);
					
						int rezultat=ps.executeUpdate();
					
						if(rezultat==1) 
						{
							DefaultTableModel model1=(DefaultTableModel)tablica.getModel();
							model1.removeRow(odabraniRedak);
							
							JOptionPane.showMessageDialog(null, "Član je uspješno izbrisan!");
						} //if
						else 
						{
							JOptionPane.showMessageDialog(null, "Člana nije moguće izbrisati!");
						}//else
						
					}//try
					catch(Exception e1) 
					{
						JOptionPane.showMessageDialog(null, "Greška pri brisanju!");
					} //catch
				
				} //if glavni
			
				else 
				{
					JOptionPane.showMessageDialog(null, "Nije odabran redak tablice!");
				} //else glavni
			}//public void
			
		});//action listener
		
		btnNewButton_1.setBounds(218, 580, 95, 42);
		frame.getContentPane().add(btnNewButton_1);
		
///////////////////////////////////////////////////////*AŽURIRANJE PODATAKA*//////////////////////////////////////////////
		
////////////////UNOS PODATAKA U TEXTFIELD-OVE////////////////
		
		JLabel lblNewLabel = new JLabel("Ime:");
		lblNewLabel.setBounds(41, 426, 45, 13);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("Prezime:");
		lblNewLabel_2.setBounds(218, 426, 56, 13);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Kontakt:");
		lblNewLabel_3.setBounds(474, 426, 57, 13);
		frame.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Adresa:");
		lblNewLabel_4.setBounds(742, 426, 67, 13);
		frame.getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_7 = new JLabel("Clanarina:");
		lblNewLabel_7.setBounds(41, 488, 95, 13);
		frame.getContentPane().add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("Datum upisa:");
		lblNewLabel_8.setBounds(467, 491, 83, 13);
		frame.getContentPane().add(lblNewLabel_8);
		
		JLabel lblNewLabel_9 = new JLabel("Datum isteka:");
		lblNewLabel_9.setBounds(723, 491, 77, 13);
		frame.getContentPane().add(lblNewLabel_9);
		
		ime = new JTextField();
		ime.setBounds(82, 423, 116, 19);
		frame.getContentPane().add(ime);
		ime.setColumns(10);
		
		prezime = new JTextField();
		prezime.setText("");
		prezime.setBounds(284, 423, 175, 19);
		frame.getContentPane().add(prezime);
		prezime.setColumns(10);
		
		kontakt = new JTextField();
		kontakt.setBounds(541, 423, 167, 19);
		frame.getContentPane().add(kontakt);
		kontakt.setColumns(10);
		
		adresa = new JTextField();
		adresa.setBounds(795, 423, 207, 19);
		frame.getContentPane().add(adresa);
		adresa.setColumns(10);
		
		JComboBox clanarina = new JComboBox();
		clanarina.setBounds(106, 484, 268, 21);
		frame.getContentPane().add(clanarina);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC", "kmogorovi", "6929");
			
			String upit="SELECT * FROM RWAclanarina";
			Statement stmt=con.createStatement();
			
			ResultSet rs=stmt.executeQuery(upit);
			
			while (rs.next()) {
				String podatak=rs.getString(1);
				clanarina.addItem(podatak);
			}
		}
		catch(Exception e2){
			JOptionPane.showMessageDialog(null, "Greška pri dohvatu članarine");
		}
		
		datum_upisa = new JTextField();
		datum_upisa.setText("");
		datum_upisa.setBounds(560, 488, 131, 19);
		frame.getContentPane().add(datum_upisa);
		datum_upisa.setColumns(10);
		
		datum_isteka = new JTextField();
		datum_isteka.setBounds(810, 488, 147, 19);
		frame.getContentPane().add(datum_isteka);
		datum_isteka.setColumns(10);
		
		
		tablica.addMouseListener(new MouseAdapter() 
		{
			public void mouseClicked(MouseEvent e) 
			{
				int odabraniRedak=tablica.getSelectedRow(); //da znamo koji je redak odabran
				
				//popunjavamo TextFieldove - počinjemo od 1 jer je 0 id, a njega ne pisemo u textfield
				//po redoslijedu iz tablice u gui -brojevi stupaca u tablici gui
				
				ime.setText(tablica.getValueAt(odabraniRedak, 1).toString()); //1 jer je ime u prven stupcu tablice u gui
				prezime.setText(tablica.getValueAt(odabraniRedak, 2).toString());
				kontakt.setText(tablica.getValueAt(odabraniRedak, 3).toString());
				adresa.setText(tablica.getValueAt(odabraniRedak, 4).toString());
				
				
				//clanarina.setToolTipText(tablica.getValueAt(odabraniRedak, 5).toString());
				datum_upisa.setText(tablica.getValueAt(odabraniRedak, 7).toString());
				datum_isteka.setText(tablica.getValueAt(odabraniRedak, 6).toString());
			

				//parsiramo u int jer je idclan tip int u sql bazi
				id_clanUpdate=Integer.parseInt(tablica.getValueAt(odabraniRedak, 0).toString());
			} //public void zagrada
			
		  } //mouse adapter zagrada
				
		); //tablica.addMouseListener zagrada
		
		
	
		JButton btnNewButton_2 = new JButton("Ažuriraj");
		btnNewButton_2.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
	
			// 1. preuzimanje podataka od korisnika iz textField
			
			String imes=ime.getText();
			String prezimes=prezime.getText();
			String kontakts=kontakt.getText();
			String adresas=adresa.getText();

			String clanarinas=(String) clanarina.getSelectedItem();
			String datum_upisas=datum_upisa.getText();
			String datum_istekas=datum_isteka.getText();
			
			// 2. kreiranje update upita za bazu
			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","6929");
				
				String upit = "UPDATE RWAclan SET  ime=?, prezime=?, kontakt=?, adresa=?,  clanarina=?, datum_isteka=?, datum_upisa=?  WHERE id_clan=?";
				PreparedStatement ps=con.prepareStatement(upit);
				
				ps.setString(1, imes); //1 jer gledamo ? u upitu
				ps.setString(2, prezimes); //na misto drugeg ? dodaj vrijednost koju je korisnik unija u textField-u za prezime
				ps.setString(3, kontakts);
				ps.setString(4, adresas);
			
				ps.setString(5, clanarinas);
				
				ps.setString(6, datum_istekas);
				ps.setString(7, datum_upisas);
				

				//dodajemo kako bi iz upita zna za koji id --> id iz odabraneg retka
				ps.setInt(8, id_clanUpdate); //deseti ?
				
				//izvršavanje upita
				int updateRedak=ps.executeUpdate();
				
				//obavjest korisnicima
				if(updateRedak > 0) 
				{
					JOptionPane.showMessageDialog(null, "Uspješno ažuriranje!");
					
					//ako je uspješno ažurirano u bazi da tablica prikaže ažurirane podatke
					
					try {
					
						String upit1="SELECT * FROM RWAclan";
						Statement stmt=con.createStatement();
						ResultSet rs=stmt.executeQuery(upit1);
						
						DefaultTableModel model=(DefaultTableModel)tablica.getModel();
						
						model.setRowCount(0);//svaki put kad stisnemo batun broj redaka se postavlja na 0
						
						while(rs.next()) {

							int id_clan=rs.getInt(1); //po tablici u bazi brojevi
							String ime=rs.getString(2);
							String prezime=rs.getString(3);
							String kontakt=rs.getString(4);
							String adresa=rs.getString(5);
							
							String clanarina=rs.getString(7);
							String datum_isteka=rs.getString(9);
							String datum_upisa_string=rs.getString(8);
							
							model.addRow(new Object[] {  id_clan, ime, prezime,kontakt, adresa, clanarina, datum_isteka, datum_upisa_string});
							
						} //while 
						
				
					}//try
					catch(Exception e2) {
						JOptionPane.showMessageDialog(null, "Greška u dohvatu podataka ");
					} //catch
					
				} //if
				else 
				{
					JOptionPane.showMessageDialog(null, "Neuspješno ažuriranje!");
				}//else
				
			}//try
			catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Greška pri spajanju na bazu");
			}//catch
			
			}//public void action event
			
			});//action listener
			
		
		btnNewButton_2.setBounds(66, 580, 95, 42);
		frame.getContentPane().add(btnNewButton_2);
		
		
		
		JLabel lblNewLabel_1 = new JLabel("PREGLED ČLANOVA");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(417, 34, 239, 30);
		frame.getContentPane().add(lblNewLabel_1);
		
		
		

	
	}//public void inicialize
	
	public void showWindow() {
		frame.setVisible(true);
	}//public void showWindow
}
