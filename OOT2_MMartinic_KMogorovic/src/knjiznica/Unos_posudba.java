package knjiznica;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Unos_posudba {

	private JFrame frame;
	private JTextField datum_posudbe;
	private JTextField datum_povrata;
	private JTextField sifraKnjiznicar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Unos_posudba window = new Unos_posudba();
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
	public Unos_posudba() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 802, 705);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(27, 132, 274, 196);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ČLAN");
		lblNewLabel.setBounds(112, 23, 45, 13);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Ime:");
		lblNewLabel_1.setBounds(10, 71, 75, 13);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Prezime:");
		lblNewLabel_2.setBounds(10, 104, 75, 13);
		panel.add(lblNewLabel_2);
		
		 
																//////PREZIME COMBOBOX///////
		JComboBox prezime_clan = new JComboBox();
		prezime_clan.setBounds(95, 100, 96, 21);
		panel.add(prezime_clan);
		
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
			JOptionPane.showMessageDialog(null,"Greška pri dohvatu prezimena člana");
		}//catch
				
																////////IME COMBOBOX///////
		JComboBox ime_clan = new JComboBox();
		ime_clan.setBounds(95, 67, 96, 21);
		panel.add(ime_clan);
		
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
			JOptionPane.showMessageDialog(null, "Greška pri dohvatu podataka o imenu člana.");
		}//catch
		
//////////////////////////////////////////////////////////////////////////////////////////		
	
/////////////////////////////////////////////////////////////////////////////
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(363, 131, 274, 197);
		frame.getContentPane().add(panel_1);
		
		JLabel lblKnjiga = new JLabel("KNJIGA");
		lblKnjiga.setBounds(112, 23, 45, 13);
		panel_1.add(lblKnjiga);
		
		JLabel lblNewLabel_1_1 = new JLabel("ISBN: ");
		lblNewLabel_1_1.setBounds(10, 108, 45, 13);
		panel_1.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_8 = new JLabel("Naziv:");
		lblNewLabel_8.setBounds(10, 66, 45, 13);
		panel_1.add(lblNewLabel_8);
													////////////////NAZIV COMBOBOX////////////
		JComboBox naziv = new JComboBox();
		naziv.setBounds(65, 62, 172, 21);
		panel_1.add(naziv);
		
	
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
			JOptionPane.showMessageDialog(null, "Greška pri dohvatu podataka o nazivu knjige.");
		}//catch
		
	///////////////////////////////////////////////////////////////UNOS ISBN NA TEMELJU NAZIVA KNJIGE////////////////////////////
		
		JComboBox isbn = new JComboBox();
		isbn.setBounds(65, 104, 172, 21);
		panel_1.add(isbn);
		
	/*	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","6929") ;
			
			String upit = "SELECT isbn FROM RWAknjiga;";
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery(upit);
			
			while (rs.next()) {
				String podatak =rs.getString(1);
				isbn.addItem(podatak);
			}
		}//try
		catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "Greška pri povlačenju podataka o isbn knjige.");
		}//catch
		*/
		
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
		            JOptionPane.showMessageDialog(null, "Greška pri dohvatu podatka o ISBN knjige");
		        }
		    }
		});
	
	//////////////////////////////	
		
		
		JLabel lblNewLabel_5 = new JLabel("Datum posudbe: ");
		lblNewLabel_5.setBounds(35, 415, 128, 13);
		frame.getContentPane().add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Predviđeni datum povrata:");
		lblNewLabel_6.setBounds(35, 457, 175, 13);
		frame.getContentPane().add(lblNewLabel_6);
		
		datum_posudbe = new JTextField();
		datum_posudbe.setBounds(131, 415, 128, 19);
		frame.getContentPane().add(datum_posudbe);
		datum_posudbe.setColumns(10);
		
		datum_povrata = new JTextField();
		datum_povrata.setBounds(215, 454, 96, 19);
		frame.getContentPane().add(datum_povrata);
		datum_povrata.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Šifra knjižničara");
		lblNewLabel_3.setBounds(34, 377, 96, 13);
		frame.getContentPane().add(lblNewLabel_3);
		
		sifraKnjiznicar = new JTextField();
		sifraKnjiznicar.setBounds(131, 374, 114, 19);
		frame.getContentPane().add(sifraKnjiznicar);
		sifraKnjiznicar.setColumns(10);
		
		JButton btnNewButton = new JButton("Spremi");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String imes, prezimes, isbns, nazivs, sifras, datum_posudbes, datum_povratas;
				
				imes=(String)ime_clan.getSelectedItem();
				prezimes=(String)prezime_clan.getSelectedItem();
				isbns=(String)isbn.getSelectedItem();
				nazivs=(String)naziv.getSelectedItem();
				sifras=sifraKnjiznicar.getText();
				datum_posudbes=datum_posudbe.getText();
				datum_povratas=datum_povrata.getText();
				
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
					
					String upitKnjiga ="SELECT id_knjiga FROM RWAknjiga WHERE naziv='"+nazivs+"' AND isbn='"+isbns+"' ";
					Statement stmKnjiga=con.createStatement();
					ResultSet rsKnjiga=stmKnjiga.executeQuery(upitKnjiga);
					
					int idKnjiga=0;
					
					if(rsKnjiga.next()) {
						idKnjiga=rsKnjiga.getInt(1);
					} //if 
					

					////upis u agregaciju
					
					String upit_posudba="INSERT INTO RWAposudba (id_clan, id_knjiga, sifra_knjiznicar, datum_posudbe, datum_povrata) VALUES (?,?,?,?,?)";
					PreparedStatement ps=con.prepareStatement(upit_posudba);
					
					ps.setInt(1, idClan); // inserta se rezultat iz varijeble idClan
					ps.setInt(2, idKnjiga);
					ps.setString(3, sifras);
					ps.setString(4, datum_posudbes);
					ps.setString(5, datum_povratas);
					
					int redakaUbaceno=ps.executeUpdate();
					
					if(redakaUbaceno==1) {
						JOptionPane.showMessageDialog(null, "Posudba je spremljena");
					}//if
					else {
						JOptionPane.showMessageDialog(null, "Spremanje neuspješno!");
					}//else

				} //try
				catch(Exception e1) {
					JOptionPane.showMessageDialog(null, "Greška pri spajanju na bazu podataka");
				} //catch
				
			
			} //public void
		}); //action listener
		
		
		btnNewButton.setBounds(474, 435, 85, 21);
		frame.getContentPane().add(btnNewButton);
		
		
		JLabel lblNewLabel_4 = new JLabel("UNOS POSUDBE");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_4.setBounds(303, 41, 145, 13);
		frame.getContentPane().add(lblNewLabel_4);
		
	}
	
	public void showWindow() {
		frame.setVisible(true);
	}//public void showWindow
}
