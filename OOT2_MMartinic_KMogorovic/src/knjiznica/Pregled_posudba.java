package knjiznica;

import java.awt.EventQueue;
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
		frame.setBounds(100, 100, 1033, 595);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		/////////////////////////////////////////////////////////////////////////////////*PRIKAZ PODATAKA*//////////////////////////
					
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(45, 196, 947, 218);
		frame.getContentPane().add(scrollPane);
		
		tablica = new JTable();
		scrollPane.setViewportView(tablica);
		tablica.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"id_posudba", "id_clan", "id_knjiga", "sifra_knjiznicar", "datum_posudbe", "datum_povrata", "stvarni_dat_povrata", "zakasnina"
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
			String upit="SELECT * FROM RWAposudba";
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery(upit);
			
			DefaultTableModel model=(DefaultTableModel)tablica.getModel();
			
			while(rs.next()) {

				int id_posudbe=rs.getInt(1); 
				int id_clan=rs.getInt(2);
				int id_knjiga=rs.getInt(3);
				int sifra_knjiznicar=rs.getInt(4);
				Date datum_posudbe=rs.getDate(5);
				Date datum_povrata=rs.getDate(6);
				Date stvarni_dat_povrata=rs.getDate(7);
				float zakasnina=rs.getFloat(8);
				
				model.addRow(new Object[] {id_posudbe, id_clan, id_knjiga, sifra_knjiznicar, datum_posudbe, datum_povrata, stvarni_dat_povrata, zakasnina});
				
			} //while
			
		}//try
		
		catch(Exception e1)
		{
			JOptionPane.showMessageDialog(null, e1);
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
					Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","6929");
					
					String upit="SELECT * FROM RWAposudba WHERE id_clan LIKE ? OR id_knjiga LIKE ? OR sifra_knjiznicar LIKE ? OR datum_posudbe LIKE ? OR datum_povrata LIKE ? OR stvarni_dat_povrata LIKE ? OR zakasnina LIKE ? ";
					
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
					
					while(rs.next()) {

						int id_posudbe=rs.getInt(1); 
						int id_clan=rs.getInt(2);
						int id_knjiga=rs.getInt(3);
						int sifra_knjiznicar=rs.getInt(4);
						Date datum_posudbe=rs.getDate(5);
						Date datum_povrata=rs.getDate(6);
						Date stvarni_dat_povrata=rs.getDate(7);
						float zakasnina=rs.getFloat(8);
						
						model.addRow(new Object[] {id_posudbe, id_clan, id_knjiga, sifra_knjiznicar, datum_posudbe, datum_povrata, stvarni_dat_povrata, zakasnina});
						
					} //while
					
				}//try
				catch(Exception e1) 
				{
					JOptionPane.showMessageDialog(null, e1);
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
						Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","6929") ;
						
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
						JOptionPane.showMessageDialog(null, e1);
					} //catch
					
				} //if prvi
				else
				{
					JOptionPane.showMessageDialog(null, "Nije odabran redak tablice!");
				}//else prvi
				
			} //PUBLIC VOID
		}); //action listener
		
		btnNewButton_1.setBounds(526, 449, 95, 45);
		frame.getContentPane().add(btnNewButton_1);
		
		/////////////////////////////////////////////////////////////////////////////////////////////////*AŽURIRANJE*/////////////////
						
		
		JButton btnNewButton_2 = new JButton("Ažuriraj");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//ispis u tablicu
				DefaultTableModel model=(DefaultTableModel)tablica.getModel();
				
				
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","6929") ;
					
					model.setRowCount(0);//svaki put kad stisnemo batun broj redaka se postavlja na 0
					String upit = "SELECT * FROM RWAposudba";
					
				//	String upit="UPDATE RWAposudba SET id_posudbe=?, id_clan=?, id_knjiga=?, sifra_knjiznicar=?, datum_posudbe=?, datum_povratka=?, stvarni_dat_povrata=?, zakasnina=? WHERE id_posudbe=?";
					Statement stmt=con.createStatement();//pripremanje "tunela" za slanje upita
					ResultSet rs=stmt.executeQuery(upit);
					while (rs.next()) {
						
						//preuzimanje podatka iz baze
						int id_posudbe=rs.getInt(1);
						int id_clan=rs.getInt(2);
						int id_knjiga=rs.getInt(3);
						int sifra_knjiznicar=rs.getInt(4);
						Date datum_posudbe=rs.getDate(5);
						Date datum_povrata=rs.getDate(6);
						Date stvarni_dat_povrata=rs.getDate(7);
						float zakasnina=rs.getFloat(8);
						
						//stavljanje podatka u tablicu
						model.addRow(new Object[] {id_posudbe, id_clan ,id_knjiga, sifra_knjiznicar, datum_posudbe, datum_povrata, stvarni_dat_povrata, zakasnina});
					}
					
					
				}//try
				catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "tablica");
				}//catch
		
			} //public void
		}); //action listener
		
		btnNewButton_2.setBounds(699, 449, 95, 45);
		frame.getContentPane().add(btnNewButton_2);
		
		
		
		JLabel lblNewLabel_1 = new JLabel("PREGLED POSUDBI");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(414, 37, 225, 31);
		frame.getContentPane().add(lblNewLabel_1);

		
	} //private void initialize
	
	public void showWindow() {
		frame.setVisible(true);
		
	}//public void showWindow


}
