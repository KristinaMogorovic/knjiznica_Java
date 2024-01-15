package knjiznica;

import java.sql.*;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;

public class Unos_clan {

	private JFrame frame;
	private JTextField ime;
	private JTextField prezime;
	private JTextField kontakt;
	private JTextField adresa;
	private JPasswordField lozinka;
	private JTextField datum_upis;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Unos_clan window = new Unos_clan();
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
	public Unos_clan() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 641, 505);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Ime: ");
		lblNewLabel.setBounds(23, 95, 45, 13);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Unos člana");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(332, 22, 116, 13);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Prezime:");
		lblNewLabel_2.setBounds(23, 131, 101, 13);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("Kontakt:");
		lblNewLabel_2_1.setBounds(23, 164, 101, 13);
		frame.getContentPane().add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_2_2 = new JLabel("Adresa:");
		lblNewLabel_2_2.setBounds(23, 201, 101, 13);
		frame.getContentPane().add(lblNewLabel_2_2);
		
		JLabel lblNewLabel_2_3 = new JLabel("Vrsta članarine:");
		lblNewLabel_2_3.setBounds(23, 241, 101, 13);
		frame.getContentPane().add(lblNewLabel_2_3);
		
		JLabel lblNewLabel_2_6 = new JLabel("Lozinka:");
		lblNewLabel_2_6.setBounds(23, 328, 101, 13);
		frame.getContentPane().add(lblNewLabel_2_6);
		
		ime = new JTextField();
		ime.setBounds(87, 92, 155, 19);
		frame.getContentPane().add(ime);
		ime.setColumns(10);
		
		prezime = new JTextField();
		prezime.setColumns(10);
		prezime.setBounds(87, 128, 155, 19);
		frame.getContentPane().add(prezime);
		
		JLabel lblDatumUpisa = new JLabel("Datum upisa:");
		lblDatumUpisa.setBounds(404, 241, 90, 13);
		frame.getContentPane().add(lblDatumUpisa);
		
		datum_upis = new JTextField();
		datum_upis.setBounds(398, 265, 96, 19);
		frame.getContentPane().add(datum_upis);
		datum_upis.setColumns(10);
		
		kontakt = new JTextField();
		kontakt.setColumns(10);
		kontakt.setBounds(87, 161, 155, 19);
		frame.getContentPane().add(kontakt);
		
		adresa = new JTextField();
		adresa.setColumns(10);
		adresa.setBounds(87, 198, 155, 19);
		frame.getContentPane().add(adresa);
		
		lozinka = new JPasswordField();
		lozinka.setBounds(78, 325, 93, 19);
		frame.getContentPane().add(lozinka);
		
		JComboBox clanarina = new JComboBox();
		clanarina.setBounds(23, 264, 314, 21);
		frame.getContentPane().add(clanarina);
		
		try {
						
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC", "kmogorovi", "6929");
			
			String upit="SELECT * FROM RWAclanarina";
			Statement stmt=con.createStatement();
			
			ResultSet rs=stmt.executeQuery(upit);
			
			while (rs.next()) 
			{
				String podatak=rs.getString(1);
				clanarina.addItem(podatak);
			}
			
		}
		catch(Exception e2)
		{
			JOptionPane.showMessageDialog(null, e2);
		}
		
		
		//spremanje podataka
		JButton btnNewButton = new JButton("Spremi");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String imes, prezimes, kontakts, adresas, lozinkas, clanarinas, datums;
				imes=ime.getText();
				prezimes=prezime.getText();
				kontakts=kontakt.getText();
				adresas=adresa.getText();
				lozinkas=lozinka.getText();//hesh
				clanarinas=(String)clanarina.getSelectedItem();
				datums=datum_upis.getText();
				
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","6929") ;
					
					String unos="INSERT INTO RWAclan (ime, prezime, kontakt, adresa, lozinka, clanarina,datum_upisa_string) VALUES (?,?,?,?,?,?,?);";
					PreparedStatement psInsert=con.prepareStatement(unos);
					psInsert.setString(1, imes);
					psInsert.setString(2, prezimes);
					psInsert.setString(3, kontakts);
					psInsert.setString(4, adresas);
					psInsert.setString(5, lozinkas);
					psInsert.setString(6, clanarinas);
					psInsert.setString(7, datums);
					
					int redakaUbaceno = psInsert.executeUpdate();
					if (redakaUbaceno==1) {
						JOptionPane.showMessageDialog(null, "Unos uspješan");
					}//if
					else {
						JOptionPane.showMessageDialog(null, "Greška pri unosu.");
					}//else
					
					
					
					
				}
				catch(Exception e1) {
					JOptionPane.showMessageDialog(null, e1);
				}
				
			}
		});
		btnNewButton.setBounds(40, 387, 131, 47);
		frame.getContentPane().add(btnNewButton);
		


	}
	
	public void showWindow() {
		frame.setVisible(true);
	}//public void showWindow
}
