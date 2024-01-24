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
		frame.setBounds(100, 100, 963, 583);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	
		//////////////////////////////////////////////////*PRIKAZ PODATAKA*////////////////////////////////////
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(40, 173, 880, 226);
		frame.getContentPane().add(scrollPane);
		
		tablica = new JTable();
		scrollPane.setViewportView(tablica);
		tablica.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"id_knjiga", "naziv", "godina_izdanja", "ISBN", "nakladnik", "opis", "jezik", "id_KlasOznake", "slika", "ukupan_broj", "rezervirani", "posudeni"
			}
		){
			boolean[] columnEditables = new boolean[] {
					false, false, false, false, false, false, false, false, false, false, false, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
			
			try
			{
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","6929");
				String upit="SELECT * FROM RWAknjiga";
				Statement stmt=con.createStatement();
				ResultSet rs=stmt.executeQuery(upit);
				
				DefaultTableModel model=(DefaultTableModel)tablica.getModel();
				
				while(rs.next()) {

					int id_knjiga=rs.getInt(1); 
					String naziv=rs.getString(2);
					int godina_izdanja=rs.getInt(3);
					String ISBN=rs.getString(4);
					String nakladnik=rs.getString(5);
					String opis=rs.getString(6);
					String jezik=rs.getString(7);
					int id_klasOznake=rs.getInt(8);
					String slika=rs.getString(9);
					int ukupan_broj=rs.getInt(11);
					int rezervirani=rs.getInt(12);
					int posudeni=rs.getInt(13);
					
					model.addRow(new Object[] {id_knjiga, naziv, godina_izdanja, ISBN, nakladnik, opis, jezik, id_klasOznake, slika, ukupan_broj, rezervirani, posudeni});
					
				} //while
				
			}//try
			
			catch(Exception e1)
			{
				JOptionPane.showMessageDialog(null, e1);
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
						
						String upit="SELECT * FROM RWAknjiga WHERE naziv LIKE ? OR godina_izdanja LIKE ? OR isbn LIKE ? OR nakladnik LIKE ? OR opis LIKE ? OR jezik LIKE ? OR id_KlasOznake LIKE ? OR slika LIKE ? OR ukupan_broj LIKE ? OR rezervirani LIKE ? OR posudeni LIKE ?";
						
						PreparedStatement ps=con.prepareStatement(upit);
						ps.setString(1, "%"+pretragas+"%"); 
						ps.setString(2, "%"+pretragas+"%");
						ps.setString(3, "%"+pretragas+"%");
						ps.setString(4, "%"+pretragas+"%");
						ps.setString(5, "%"+pretragas+"%");
						ps.setString(6, "%"+pretragas+"%");
						ps.setString(7, "%"+pretragas+"%");
						ps.setString(8, "%"+pretragas+"%");
						ps.setString(9, "%"+pretragas+"%");
						ps.setString(10, "%"+pretragas+"%");
						ps.setString(11, "%"+pretragas+"%");
						
						ResultSet rs=ps.executeQuery();
						
						DefaultTableModel model=(DefaultTableModel)tablica.getModel();
						model.setRowCount(0);
						
						while(rs.next()) {

							int id_knjiga=rs.getInt(1); 
							String naziv=rs.getString(2);
							int godina_izdanja=rs.getInt(3);
							String ISBN=rs.getString(4);
							String nakladnik=rs.getString(5);
							String opis=rs.getString(6);
							String jezik=rs.getString(7);
							int id_klasOznake=rs.getInt(8);
							String slika=rs.getString(9);
							int ukupan_broj=rs.getInt(10);
							int rezervirani=rs.getInt(11);
							int posudeni=rs.getInt(12);
							
							model.addRow(new Object[] {id_knjiga, naziv, godina_izdanja, ISBN, nakladnik, opis, jezik, id_klasOznake, slika, ukupan_broj, rezervirani, posudeni});
							
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
				
				if(odabraniRedak >=0) {
					
					try {
						
						int id_knjiga=Integer.parseInt(tablica.getValueAt(odabraniRedak, 0).toString());
						
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","6929");
						
						String upit="DELETE FROM RWAknjiga WHERE id_knjiga=?";
						
						PreparedStatement ps=con.prepareStatement(upit);
						ps.setInt(1, id_knjiga);
						
						int rezultat=ps.executeUpdate();
						
						if(rezultat==1) 
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
						JOptionPane.showMessageDialog(null, e1);
					} //catch
					
				} //if prvi
				else
				{
					JOptionPane.showMessageDialog(null, "Nije odabran redak tablice!");
				} //else prvi
			
			}//public void
		});//action listener
		
		btnNewButton_1.setBounds(567, 425, 95, 45);
		frame.getContentPane().add(btnNewButton_1);
		
		////////////////////////////////////////////////////////////*AŽURIRAJ PODATKE*/////////////////////////////////////
		
		
		JButton btnNewButton_1_1 = new JButton("Ažuriraj");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//ispis u tablicu
				DefaultTableModel model=(DefaultTableModel)tablica.getModel();
				
				
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","6929") ;
					
					model.setRowCount(0);//svaki put kad stisnemo batun broj redaka se postavlja na 0
					String upit = "SELECT * FROM RWAknjiga";
					
				//	String upit="UPDATE RWAknjiga SET id_knjiga=?, naziv=?, godina_izdanja=?, isbn=?, nakladnik=?, opis=?, jezik=?, id_KlasOznake=?, slika=?, ukupni_broj=?, rezervirani=?, posudeni=? WHERE id_knjiga=?";
					Statement stmt=con.createStatement();//pripremanje "tunela" za slanje upita
					ResultSet rs=stmt.executeQuery(upit);
					while (rs.next()) {
						
						//preuzimanje podatka iz baze
						int id_knjiga=rs.getInt(1);
						String naziv=rs.getString(2);
						int godina_izdanja=rs.getInt(3);
						String isbn=rs.getString(4);
						String nakladnik=rs.getString(5);
						String opis=rs.getString(6);
						String jezik=rs.getString(7);
						int id_KlasOznake=rs.getInt(8);
						String slika=rs.getString(9);
						int ukupni_broj=rs.getInt(10);
						int rezervirani=rs.getInt(11);
						int posudeni=rs.getInt(12);
				
						//stavljanje podatka u tablicu
						model.addRow(new Object[] {id_knjiga, naziv, godina_izdanja, isbn, nakladnik, opis, jezik, id_KlasOznake, slika, ukupni_broj, rezervirani, posudeni});
					}
					
					
				}//try
				catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "tablica");
				}//catch
			
			}//public void
		});//action listener
		
		btnNewButton_1_1.setBounds(702, 425, 95, 45);
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
