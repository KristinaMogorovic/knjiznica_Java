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

public class Pregled_autor {

	private JFrame frame;
	private JTextField trazilica;
	private JTable tablica;
	
	private int id_autorUpdate; //umetnutno za izvršavanje ažuriranja
	private JTextField ime;
	private JTextField prezime;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Pregled_autor window = new Pregled_autor();
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
	public Pregled_autor() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 922, 548);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		////////////////////////////////////////////////////////////////////////////////////////////
		/*PRIKAZ PODATAKA U TABLICU*/
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(36, 196, 582, 168);
		frame.getContentPane().add(scrollPane);
		
		tablica = new JTable();
		scrollPane.setViewportView(tablica);
		tablica.setModel(new DefaultTableModel(
		new Object[][] {
		},
		new String[] {
			"id_autor", "ime ", "prezime"
		}
		){
		boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		//////////////////////////////////////////////////////PRIKAZ PODATAKA//////////////////////////////
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","6929");
			String upit="SELECT * FROM RWAautor";
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery(upit);
			
			DefaultTableModel model=(DefaultTableModel)tablica.getModel();
			
			while(rs.next()) {

				int id_autor=rs.getInt(1); 
				String ime=rs.getString(2);
				String prezime=rs.getString(3);
				
				model.addRow(new Object[] {id_autor, ime, prezime});
				
			} //while
			
		}//try
		
		catch(Exception e1)
		{
			JOptionPane.showMessageDialog(null, "Greška pri dohvatu podataka");
		}//catch
		
		
		/////////////////////////////////////////////*PROZOR UNOS NOVOG AUTORA*/////////////////////////////////////////////////////////////
		
		
		JButton btnNewButton = new JButton("Unesi novog autora");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//prozor unos autora
				Unos_autor ua = new Unos_autor();
				ua.showWindow();
			
			}//public void
		}); //action listener
		
		btnNewButton.setBounds(640, 110, 152, 46);
		frame.getContentPane().add(btnNewButton);
		
		////////////////////////////////////////////////*TRAŽILICA*/////////////////////////////////////////////////
		
		trazilica = new JTextField();
				
		JButton btnNewButton_3 = new JButton("Traži");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//uzimamo podatke
				String pretragas=trazilica.getText();
				
				try 
				{
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","6929");
					
					String upit="SELECT * FROM RWAautor WHERE ime LIKE ? OR prezime LIKE ?";
					
					PreparedStatement ps=con.prepareStatement(upit);
					
					ps.setString(1, "%"+pretragas+"%"); //omogućuje pretragu kroz sve ne samo s početka ili kraja
					ps.setString(2, "%"+pretragas+"%");
					
					ResultSet rs=ps.executeQuery();
					
					DefaultTableModel model=(DefaultTableModel)tablica.getModel();
					
					model.setRowCount(0);
					
					while(rs.next()) { //dok ima podataka

						int id_autor=rs.getInt(1); 
						String ime=rs.getString(2);
						String prezime=rs.getString(3);
						
						model.addRow(new Object[] {id_autor, ime, prezime});
						
					} //while
					
				} //try
				catch(Exception e1) 
				{
					JOptionPane.showMessageDialog(null, "Traženje podataka nije omogućeno");
				} //catch
				
			} //public void
		}); //action listener
		
		btnNewButton_3.setBounds(367, 117, 99, 27);
		frame.getContentPane().add(btnNewButton_3);
							
				
		
		trazilica.setBounds(78, 120, 266, 27);
		frame.getContentPane().add(trazilica);
		trazilica.setColumns(10);
		
		
		/////////////////////////////////////////////////////////////////////////*BRISANJE PODATAKA*/////////////////////////////////
		
		
		JButton btnNewButton_1 = new JButton("Obriši");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//znat koji je redak odabran u tablici u dizajnu
				
				DefaultTableModel model=(DefaultTableModel)tablica.getModel();
				int odabraniRedak=tablica.getSelectedRow();
				
				if(odabraniRedak >= 0) { //je li odabran redak NE KOJI
					try {
						
						int id_autor=Integer.parseInt(tablica.getValueAt(odabraniRedak, 0).toString());
						
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","6929");
						
						String upit="DELETE FROM RWAautor WHERE id_autor=?";
						PreparedStatement ps=con.prepareStatement(upit);
						
						ps.setInt(1,id_autor); //1 prvo misto
						
						int rezultat=ps.executeUpdate();
						
						if(rezultat==1) {
							
							DefaultTableModel model1=(DefaultTableModel)tablica.getModel();
							model1.removeRow(odabraniRedak);
							
							JOptionPane.showMessageDialog(null, "Autor je uspješno izbrisan!");
							
						} //if drugi
						else 
						{
							JOptionPane.showMessageDialog(null, "Autora nije moguće izbrisati!");
						} //else drugi
						
					}//try
					catch(Exception e1) 
					{
						JOptionPane.showMessageDialog(null, "Nije moguće izvršiti brisanje");
					} //catch
					
				} //if prvi
				else 
				{
					JOptionPane.showMessageDialog(null, "Nije odabran redak tablice!");
				} //else drugi
			
			
			}//public void
		}); //action listener
		
		btnNewButton_1.setBounds(533, 391, 85, 37);
		frame.getContentPane().add(btnNewButton_1);
		
		//////////////////////////////////////////////////////////////*AŽURIRANJE PODATAKA*////////////////////////////////
		
		
				////////////////UNOS PODATAKA U TEXTFIELD-OVE////////////////
		
		ime = new JTextField();
		ime.setBounds(707, 231, 152, 19);
		frame.getContentPane().add(ime);
		ime.setColumns(10);
		
		prezime = new JTextField();
		prezime.setBounds(707, 294, 152, 19);
		frame.getContentPane().add(prezime);
		prezime.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Ime:");
		lblNewLabel.setBounds(652, 234, 45, 13);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("Prezime:");
		lblNewLabel_2.setBounds(638, 297, 74, 13);
		frame.getContentPane().add(lblNewLabel_2);
		
		tablica.addMouseListener(new MouseAdapter() 
		{
			public void mouseClicked(MouseEvent e) 
			{
				int odabraniRedak=tablica.getSelectedRow(); //da znamo koji je redak odabran
				
				//popunjavamo TextFieldove - počinjemo od 1 jer je 0 id, a njega ne pisemo u textfield
				
				ime.setText(tablica.getValueAt(odabraniRedak, 1).toString());
				prezime.setText(tablica.getValueAt(odabraniRedak, 2).toString());
				
				//vezano uz update donjih kodova
				
				id_autorUpdate=Integer.parseInt(tablica.getValueAt(odabraniRedak, 0).toString());
			} //public void zagrada
			
		  } //mouse adapter zagrada
				
		); //tablica.addMouseListener zagrada
		
			/////////////////////////////////////////////
		
		JButton btnNewButton_2 = new JButton("Ažuriraj");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				// 1. preuzimanje podataka od korisnika iz textField
				
				String imes=ime.getText();
				String prezimes=prezime.getText();
				
				// 2. kreiranje update upita za bazu
				
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","6929");
					
					String upit = "UPDATE RWAautor SET  ime=?, prezime=? WHERE id_autor=?";
					PreparedStatement ps=con.prepareStatement(upit);
					
					ps.setString(1, imes); //1 jer gledamo ? u upitu
					ps.setString(2, prezimes); //na misto drugeg ? dodaj vrijednost koju je korisnik unija u textField-u za prezime

					//dodajemo kako bi iz upita zna za koji id --> id iz odabraneg retka
					ps.setInt(3, id_autorUpdate);
					
					//izvršavanje upita
					int updateRedak=ps.executeUpdate();
					
					//obavjest korisnicima
					if(updateRedak > 0) 
					{
						JOptionPane.showMessageDialog(null, "Uspješno ažuriranje!");
						
						//ako je uspješno ažurirano u bazi da tablica prikaže ažurirane podatke
						
						String upit1="SELECT * FROM RWAautor";
						Statement stmt=con.createStatement();
						ResultSet rs=stmt.executeQuery(upit1);
						
						DefaultTableModel model=(DefaultTableModel)tablica.getModel();
						
						model.setRowCount(0);//svaki put kad stisnemo batun broj redaka se postavlja na 0
						
						while(rs.next()) { //baza

							int id_autor=rs.getInt(1); 
							String ime=rs.getString(2);
							String prezime=rs.getString(3);
							
							model.addRow(new Object[] {id_autor, ime, prezime});
							
						} //while
						
					} //if
					else 
					{
						JOptionPane.showMessageDialog(null, "Neuspješno ažuriranje!");
					}//else
					
				}//try
				catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Nije moguće izvšiti ažuriranje!");
				}//catch
			
			}//public void action event
			
		});//action listener
		
		btnNewButton_2.setBounds(707, 391, 85, 37);
		frame.getContentPane().add(btnNewButton_2);
		
		JLabel lblNewLabel_1 = new JLabel("PREGLED AUTORA");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(354, 38, 210, 27);
		frame.getContentPane().add(lblNewLabel_1);

		
		
	}//private void initialize
	
	public void showWindow() {
		frame.setVisible(true);
	}//public void showWindow


}
