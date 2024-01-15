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

public class Pregled_clan {

	private JFrame frame;
	private JTable tablica;
	private JTextField trazilica;
	
	private int id_clanUpdate; //za izvršavanje ažuriranja

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
		frame.setBounds(100, 100, 1379, 533);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(41, 174, 993, 198);
		frame.getContentPane().add(scrollPane);
		
		tablica = new JTable();
		scrollPane.setViewportView(tablica);
		tablica.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"id_clan", "ime", "prezime", "kontakt", "adresa", "lozinka", "clanarina", "datum_upisa", "datum_isteka"
			}
		){
			boolean[] columnEditables = new boolean[] {
					false, false, false, false, false, false, false, false, false
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

					int id_clan=rs.getInt(1); 
					String ime=rs.getString(2);
					String prezime=rs.getString(3);
					String kontakt=rs.getString(4);
					String adresa=rs.getString(5);
					String lozinka=rs.getString(6);
					String clanarina=rs.getString(7);
					Date datum_upisa=rs.getDate(8);
					Date datum_isteka=rs.getDate(9);
					
					model.addRow(new Object[] {id_clan, ime, prezime,kontakt, adresa, lozinka, clanarina, datum_upisa, datum_isteka});
					
				} //while
				
			}//try
			
			catch(Exception e1)
			{
				JOptionPane.showMessageDialog(null, e1);
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
					
					String upit="SELECT * FROM RWAclan WHERE ime LIKE ? OR prezime LIKE ? OR kontakt LIKE ? OR adresa LIKE ? OR lozinka LIKE ? OR clanarina LIKE ? OR datum_upisa LIKE ? OR datum_isteka LIKE ?";
					
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
					
					while(rs.next()) 
					{
						int id_clan=rs.getInt(1);
						String ime=rs.getString(2);
						String prezime=rs.getString(3);
						String kontakt=rs.getString(4);
						String adresa=rs.getString(5);
						String lozinka=rs.getString(6);
						String clanarina=rs.getString(7);
						Date datum_upisa=rs.getDate(8);
						Date datum_isteka=rs.getDate(9);
						
						model.addRow(new Object[] {id_clan, ime, prezime,kontakt, adresa, lozinka, clanarina, datum_upisa, datum_isteka});
						
					} //while
					
				} //try
				catch(Exception e1) 
				{
					JOptionPane.showMessageDialog(null,e1);
				} //catch
				
				
			} //public void
		}); //action listener
		
		
		btnNewButton_3.setBounds(399, 98, 85, 30);
		frame.getContentPane().add(btnNewButton_3);
		
		trazilica.setBounds(106, 98, 239, 30);
		frame.getContentPane().add(trazilica);
		trazilica.setColumns(10);
		
		
		//////////////////////////////*UNOS NOVOG ČLANA*////////////////////////////////////////////////
		
		
		JButton btnNewButton = new JButton("Unesi novog člana");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Unos_clan uc = new Unos_clan(); 
					uc.showWindow();
				}
				catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1);
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
							
							JOptionPane.showMessageDialog(null, "Član je uspjšeno izbrisan!");
						} //if
						else 
						{
							JOptionPane.showMessageDialog(null, "Člana nije moguće izbrisati!");
						}//else
						
					}//try
					catch(Exception e1) 
					{
						JOptionPane.showMessageDialog(null, e1);
					} //catch
				
				} //if glavni
			
				else 
				{
					JOptionPane.showMessageDialog(null, "Nije odabran redak tablice!");
				} //else glavni
			}//public void
			
		});//action listener
		
		btnNewButton_1.setBounds(485, 405, 95, 42);
		frame.getContentPane().add(btnNewButton_1);
		
		///////////////////////////////////////////////////////*AŽURIRANJE PODATAKA*//////////////////////////////////////////////
		
		
	
		JButton btnNewButton_2 = new JButton("Ažuriraj");
		btnNewButton_2.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
	
			//ispis u tablicu
			DefaultTableModel model=(DefaultTableModel)tablica.getModel();
			
			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","6929") ;
				
				model.setRowCount(0);//svaki put kad stisnemo batun broj redaka se postavlja na 0
				
				
				String upit="UPDATE RWAclan SET id_clan=?, ime=?, prezime=?, kontakt=?, adresa=?, lozinka=?, clanarina=?, datum_upisa=?, datum_isteka=? WHERE id_clan=?";
				Statement stmt=con.createStatement();//pripremanje "tunela" za slanje upita
				ResultSet rs=stmt.executeQuery(upit);
				while (rs.next()) {
					
					//preuzimanje podatka iz baze
					int id_clan=rs.getInt(1);
					String ime=rs.getString(2);
					String prezime=rs.getString(3);
					String kontakt=rs.getString(4);
					String adresa=rs.getString(5);
					String lozinka=rs.getString(6);
					String clanarina=rs.getString(7);
					Date datum_upisa=rs.getDate(8);
					Date datum_isteka=rs.getDate(9); 
			
					//stavljanje podatka u tablicu
					model.addRow(new Object[] {id_clan, ime, prezime, kontakt, adresa, lozinka, clanarina, datum_upisa, datum_isteka});
				}
			}//try
			catch (Exception e1) 
			{
				JOptionPane.showMessageDialog(null, e1);
			}//catch
			
			}//public void action event
			
			});//action listener
			
		
		btnNewButton_2.setBounds(645, 405, 95, 42);
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
