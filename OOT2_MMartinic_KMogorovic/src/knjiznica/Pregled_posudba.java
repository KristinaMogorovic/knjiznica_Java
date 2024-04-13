package knjiznica;

import java.awt.EventQueue;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
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

public class Pregled_posudba {

	private JFrame frame;
	private JTable tablica;
	private JTextField trazilica;
	
	private int id_posudba; //za izvršavanje ažuriranja
	private JTextField TXT_stv_dat_povrata;
	private int odabraniRedak;
	private int id_odabrane_posudbe;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Pregled_posudba window = new Pregled_posudba();
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
	public Pregled_posudba() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1122, 624);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		/////////////////////////////////////////////////////////////////////////////////*PRIKAZ PODATAKA*//////////////////////////
					
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 163, 1068, 220);
		frame.getContentPane().add(scrollPane);
		
		tablica = new JTable();
		scrollPane.setViewportView(tablica);
		tablica.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"id_posudba","naziv_knjige","ime_autora","prezime_autora", "prezime_clana","id_clana", "datum_posudbe", "stvarni_dat_povrata", "zakasnina", "sifra_knjiznicar"  
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
			Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","****");
			String upit="SELECT p.id_posudbe, k.naziv AS naziv_knjige, a.ime AS ime_autora, a.prezime AS prezime_autora, c.prezime AS prezime_clana, p.id_clan, p.datum_posudbe, p.stvarni_dat_povrata, p.zakasnina, posudba.sifra_knjiznicar\r\n"
					+ "FROM RWAposudba p\r\n"
					+ "INNER JOIN RWAknjiga k ON p.id_knjiga = k.id_knjiga\r\n"
					+ "INNER JOIN RWAautor_knjiga ak ON k.id_knjiga = ak.id_knjiga\r\n"
					+ "INNER JOIN RWAautor a ON ak.id_autor = a.id_autor\r\n"
					+ "INNER JOIN RWAclan c ON p.id_clan = c.id_clan\r\n"
					+ "LEFT JOIN RWA_knjiznicar posudba ON p.sifra_knjiznicar = posudba.sifra_knjiznicar;\r\n"
					+ "";
			
			
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery(upit);
			
			DefaultTableModel model=(DefaultTableModel)tablica.getModel();
			
			while(rs.next()) {

				int id_posudbe=rs.getInt(1); 
				String naziv_knjige=rs.getString(2);
				String ime_autora=rs.getString(3);
				String prezime_autora=rs.getString(4);
				String prezime_clana=rs.getString(5);
				int id_clana=rs.getInt(6);
				String datum_posudbe=rs.getString(7);
				String stvarni_dat_povrata=rs.getString(8);
				float zakasnina=rs.getFloat(9);
				int sifra_knjiznicar=rs.getInt(10);
				
				model.addRow(new Object[] {id_posudbe, naziv_knjige, ime_autora, prezime_autora, prezime_clana, id_clana, datum_posudbe, stvarni_dat_povrata, zakasnina, sifra_knjiznicar});
				
			} //while
			
		}//try
		
		catch(Exception e1)
		{
			JOptionPane.showMessageDialog(null, "Greška pri spajanju na bazu");
		}
		
		//////////////////////////////////////////////////////////////////////////*TRAŽILICA*//////////////////////////////////////
		trazilica = new JTextField();
		
		JButton btnNewButton_3 = new JButton("Traži");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//uzimamo podatke
				String pretragas=trazilica.getText();
				
				try {
					
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","****");
					
					String upit="SELECT *\r\n"
							+ "FROM (\r\n"
							+ "    SELECT p.id_posudbe, k.naziv AS naziv_knjige, a.ime AS ime_autora, a.prezime AS prezime_autora, c.prezime AS prezime_clana, p.id_clan, p.datum_posudbe, p.stvarni_dat_povrata, p.zakasnina, posudba.sifra_knjiznicar\r\n"
							+ "    FROM RWAposudba p\r\n"
							+ "    INNER JOIN RWAknjiga k ON p.id_knjiga = k.id_knjiga\r\n"
							+ "    INNER JOIN RWAautor_knjiga ak ON k.id_knjiga = ak.id_knjiga\r\n"
							+ "    INNER JOIN RWAautor a ON ak.id_autor = a.id_autor\r\n"
							+ "    INNER JOIN RWAclan c ON p.id_clan = c.id_clan\r\n"
							+ "    LEFT JOIN RWA_knjiznicar posudba ON p.sifra_knjiznicar = posudba.sifra_knjiznicar\r\n"
							+ ") AS tmp\r\n"
							+ "WHERE \r\n"
							+ "    tmp.naziv_knjige LIKE ? OR\r\n"
							+ "    tmp.ime_autora LIKE ? OR\r\n"
							+ "    tmp.prezime_autora LIKE ? OR\r\n"
							+ "    tmp.prezime_clana LIKE ? OR\r\n"
							+ "    tmp.id_clan LIKE ? OR\r\n"
							+ "    tmp.stvarni_dat_povrata LIKE ? OR \r\n"
							+ "	 tmp.datum_posudbe LIKE ? OR \r\n"
							+ "	 tmp.sifra_knjiznicar LIKE ? \r\n"
							+ "	 ;\r\n"
							+ " ";
					
					PreparedStatement ps=con.prepareStatement(upit);
					ps.setString(1, "%"+pretragas+"%");
					ps.setString(2, "%"+pretragas+"%");
					ps.setString(3, "%"+pretragas+"%");
					ps.setString(4, "%"+pretragas+"%");
					ps.setString(5, "%"+pretragas+"%");
					ps.setString(6, "%"+pretragas+"%");
					ps.setString(7, "%"+pretragas+"%");
					ps.setString(8, "%"+pretragas+"%");
					
					ResultSet rs=ps.executeQuery();
					
					DefaultTableModel model=(DefaultTableModel)tablica.getModel();
					
					model.setRowCount(0);
					
					while(rs.next()) {

						int id_posudbe=rs.getInt(1); 
						String naziv_knjige=rs.getString(2);
						String ime_autora=rs.getString(3);
						String prezime_autora=rs.getString(4);
						String prezime_clana=rs.getString(5);
						int id_clana=rs.getInt(6);
						String datum_posudbe=rs.getString(7);
						String stvarni_dat_povrata=rs.getString(8);
						float zakasnina=rs.getFloat(9);
						int sifra_knjiznicar=rs.getInt(10);
						
						model.addRow(new Object[] {id_posudbe, naziv_knjige, ime_autora, prezime_autora, prezime_clana, id_clana, datum_posudbe, stvarni_dat_povrata, zakasnina, sifra_knjiznicar});
						
					} //while
					
				}//try
				catch(Exception e1) 
				{
					JOptionPane.showMessageDialog(null, "Greška pri spajanju na bazu");
					//System.out.println(e1);
				} //catch
				
			} //public void
			
		}); //action listener
		
		btnNewButton_3.setBounds(361, 106, 85, 31);
		frame.getContentPane().add(btnNewButton_3);
		
		
		trazilica.setBounds(84, 106, 220, 31);
		frame.getContentPane().add(trazilica);
		trazilica.setColumns(10);
		
		////////////////////////////////////////////////////////////////////////////////////*UNESI NOVU POSUDBU*///////////////////
					
		
		JButton btnNewButton = new JButton("Unesi novu posudbu");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Unos_posudba up = new Unos_posudba();
				up.showWindow();
			
			} //public void
			
		}); //action listener
		
		btnNewButton.setBounds(783, 109, 176, 24);
		frame.getContentPane().add(btnNewButton);
		
		////////////////////////////////////////////////////////////////////////////////////*BRISANJE*/////////////////////////
					
		
		JButton btnNewButton_1 = new JButton("Obriši");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				//da znamo koji je red odabran iz tablice u dizajnu
				DefaultTableModel model=(DefaultTableModel)tablica.getModel();
				int odabraniRedak=tablica.getSelectedRow();
				
				if(odabraniRedak >= 0) {
					
					try {
						
						int id_posudba=Integer.parseInt(tablica.getValueAt(odabraniRedak, 0).toString());
						
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","****") ;
						
						String upit="DELETE FROM RWAposudba WHERE id_posudbe=?";
						
						PreparedStatement ps=con.prepareStatement(upit);
						ps.setInt(1, id_posudba);
						
						int rezultat=ps.executeUpdate();
						
						//provjeramo dal se podatak izbrisa u bazi
						if(rezultat==1) {
							DefaultTableModel model1=(DefaultTableModel)tablica.getModel();
							model1.removeRow(odabraniRedak);
							
							JOptionPane.showMessageDialog(null, "Posudba je uspješno izbrisana!");
							
						} //if drugi
						else
						{
							JOptionPane.showMessageDialog(null, "Posudbu nije moguće izbrisati!");
						} //else drugi
						
					} //try
					catch(Exception e1) 
					{
						JOptionPane.showMessageDialog(null, "greska pri spajanju na bazu");
					} //catch
					
				} //if prvi
				else
				{
					JOptionPane.showMessageDialog(null, "Nije odabran redak tablice!");
				}//else prvi
				
			} //PUBLIC VOID
		}); //action listener
		
		btnNewButton_1.setBounds(34, 417, 95, 45);
		frame.getContentPane().add(btnNewButton_1);
		
		/////////////////////////////////////////////////////////////////////////////////////////////////*AŽURIRANJE*/////////////////
						
		
		JButton btnNewButton_2 = new JButton("Ažuriraj");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//ispis u tablicu
				DefaultTableModel model=(DefaultTableModel)tablica.getModel();
				model.setRowCount(0);
				
				try
				{
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","****");
					String upit="SELECT p.id_posudbe, k.naziv AS naziv_knjige, a.ime AS ime_autora, a.prezime AS prezime_autora, c.prezime AS prezime_clana, p.id_clan, p.datum_posudbe, p.stvarni_dat_povrata, p.zakasnina, posudba.sifra_knjiznicar\r\n"
							+ "FROM RWAposudba p\r\n"
							+ "INNER JOIN RWAknjiga k ON p.id_knjiga = k.id_knjiga\r\n"
							+ "INNER JOIN RWAautor_knjiga ak ON k.id_knjiga = ak.id_knjiga\r\n"
							+ "INNER JOIN RWAautor a ON ak.id_autor = a.id_autor\r\n"
							+ "INNER JOIN RWAclan c ON p.id_clan = c.id_clan\r\n"
							+ "LEFT JOIN RWA_knjiznicar posudba ON p.sifra_knjiznicar = posudba.sifra_knjiznicar;\r\n"
							+ "";
					
					
					Statement stmt=con.createStatement();
					ResultSet rs=stmt.executeQuery(upit);
					
					
					while(rs.next()) {

						int id_posudbe=rs.getInt(1); 
						String naziv_knjige=rs.getString(2);
						String ime_autora=rs.getString(3);
						String prezime_autora=rs.getString(4);
						String prezime_clana=rs.getString(5);
						int id_clana=rs.getInt(6);
						String datum_posudbe=rs.getString(7);
						String stvarni_dat_povrata=rs.getString(8);
						float zakasnina=rs.getFloat(9);
						int sifra_knjiznicar=rs.getInt(10);
						
						model.addRow(new Object[] {id_posudbe, naziv_knjige, ime_autora, prezime_autora, prezime_clana, id_clana, datum_posudbe, stvarni_dat_povrata, zakasnina, sifra_knjiznicar});
						
					} //while
					
				}//try
				
				catch(Exception e1)
				{
					JOptionPane.showMessageDialog(null, "Greska pri spajanju na bazu");
				}
		
			} //public void
		}); //action listener
		
		btnNewButton_2.setBounds(34, 484, 95, 45);
		frame.getContentPane().add(btnNewButton_2);
		
		
		
		JLabel lblNewLabel_1 = new JLabel("PREGLED POSUDBI");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(414, 37, 225, 31);
		frame.getContentPane().add(lblNewLabel_1);
		
		TXT_stv_dat_povrata = new JTextField();
		TXT_stv_dat_povrata.setBounds(571, 446, 96, 19);
		frame.getContentPane().add(TXT_stv_dat_povrata);
		TXT_stv_dat_povrata.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Stvarni datum povrata:");
		lblNewLabel.setBounds(414, 449, 147, 13);
		frame.getContentPane().add(lblNewLabel);
		
		
		
		tablica.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				odabraniRedak=tablica.getSelectedRow(); //da znamo koji redak je odaberen
				
				//popunjavamo textfieldove sa podacima iz tablice iz dizajna
				TXT_stv_dat_povrata.setText(tablica.getValueAt(odabraniRedak, 7).toString()); 
				id_odabrane_posudbe=(int)tablica.getValueAt(odabraniRedak, 0);
				System.out.println(id_odabrane_posudbe);
				
				//pozivanje podatka o id-ju za ažuriranje
				//parsiramo u int jer je u sql bazi taj tip podatka
				//stv_dat_povratas=Integer.parseInt(tablica.getValueAt(odabraniRedak, 0).toString());
			
			}//public void
		
		}); //mouseAdapter zagrada
	
		 
		
		
		
		////////////////////////////////////////////////////// mijenjanje datuma povrata////////////////////////////////////////////////
		JButton btnNewButton_4 = new JButton("Spremi promjene");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String stv_dat=TXT_stv_dat_povrata.getText();
				//int id_odabrane_posudbe=(int)tablica.getValueAt(odabraniRedak, 1);
				//System.out.println(id_odabrane_posudbe);
				
				
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","****");
					
					String insert="UPDATE RWAposudba SET stvarni_dat_povrata='"+stv_dat+"' WHERE id_posudbe='"+id_odabrane_posudbe+"';" ;
					PreparedStatement psInsertDatum=con.prepareStatement(insert); 
					
					int redakaUbaceno=psInsertDatum.executeUpdate();
					if (redakaUbaceno==1) {
						JOptionPane.showMessageDialog(null, "Promjena spremljena");
					}
					else {
						JOptionPane.showMessageDialog(null, "Doslo je do greške. Pokušajte ponovo.");
					}
				}//try
				catch(Exception e1) {
					JOptionPane.showMessageDialog(null, "Došlo je do greške na bazi.");
					//System.out.println(e1);
				}//catch
				
			}//actionPerformed
		});//actionListener
		btnNewButton_4.setBounds(549, 484, 138, 45);
		frame.getContentPane().add(btnNewButton_4);

		
	} //private void initialize
	
	public void showWindow() {
		frame.setVisible(true);
		
	}//public void showWindow
}
