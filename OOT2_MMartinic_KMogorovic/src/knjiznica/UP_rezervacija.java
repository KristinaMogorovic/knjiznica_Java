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

public class UP_rezervacija {

	private JFrame frame;
	private JTable tablica;
	private JTextField trazilica;
	private JTextField textField;
	private JTextField imeClan;
	private JTextField prezimeClan;
	private JTextField idClan;
	private JTextField textField_1;
	private JTextField TFid_clan;
	private JTextField TFid_knjiga;
	private JTextField TFdatum_rezervacije;
	
	private int id_rezervacijaUpdate; //za izvršenje ažuriranja

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
				"id_rezervacija", "id_clan", "id_knjiga", "datum_rezervacije"
			}
				){
			boolean[] columnEditables = new boolean[] {
					false, false, false, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
			try
			{
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","6929");
				
				String upit="SELECT * FROM RWArezervacija";
				Statement stmt=con.createStatement();
				ResultSet rs=stmt.executeQuery(upit);
				
				DefaultTableModel model=(DefaultTableModel)tablica.getModel();
				model.setRowCount(0);
				
				while(rs.next()) {
	
					int id_rezervacija=rs.getInt(1); 
					int id_clan=rs.getInt(2);
					int id_knjiga=rs.getInt(3);
					Date datum_rezervacije=rs.getDate(4);
					
					model.addRow(new Object[] {id_rezervacija, id_clan, id_knjiga, datum_rezervacije});
					
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
					
					String upit="SELECT * FROM RWArezervacija WHERE id_clan LIKE ? OR id_knjiga LIKE ? OR datum_rezervacije LIKE ?";
					
					PreparedStatement ps=con.prepareStatement(upit);
					ps.setString(1, "%"+pretragas+"%");
					ps.setString(2, "%"+pretragas+"%");
					ps.setString(3, "%"+pretragas+"%");
					
					ResultSet rs=ps.executeQuery();
					
					DefaultTableModel model=(DefaultTableModel)tablica.getModel();
					model.setRowCount(0);
					
					while(rs.next()) {

						int id_rezervacija=rs.getInt(1); 
						int id_clan=rs.getInt(2);
						int id_knjiga=rs.getInt(3);
						Date datum_rezervacije=rs.getDate(4);
						
						model.addRow(new Object[] {id_rezervacija, id_clan, id_knjiga, datum_rezervacije});
						
					} //while
				
				} //try
				catch(Exception e1)
				{
					JOptionPane.showMessageDialog(null, e1);
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
						JOptionPane.showMessageDialog(null, e1);
					} //catch
					
				} //if prvi
				else
				{
					JOptionPane.showMessageDialog(null, "Nije odabran redak tablice!");
				} //else
			
			} //public void
		}); //action listener
		
		btnNewButton.setBounds(482, 328, 85, 31);
		pregled.add(btnNewButton);
		
		/////////////////////////////////////////////////////////////*AŽURIRANJE*////////////////////////////////////////////
		
		////////////*UNOS PODATAKA U TEXTFIELD-OVE*//////////////////////////
		
		TFid_clan = new JTextField();
		TFid_clan.setBounds(523, 155, 141, 19);
		pregled.add(TFid_clan);
		TFid_clan.setColumns(10);
		
		TFid_knjiga = new JTextField();
		TFid_knjiga.setBounds(523, 211, 141, 19);
		pregled.add(TFid_knjiga);
		TFid_knjiga.setColumns(10);
		
		TFdatum_rezervacije = new JTextField();
		TFdatum_rezervacije.setBounds(523, 269, 141, 19);
		pregled.add(TFdatum_rezervacije);
		TFdatum_rezervacije.setColumns(10);
		
		tablica.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent e)
				{
					int odabraniRedak=tablica.getSelectedRow(); //da znamo koji redak je odaberen
					
					//popunjavamo textfieldove sa podacima iz tablice iz dizajna
					TFid_clan.setText(tablica.getValueAt(odabraniRedak, 1).toString()); //1 zato jer je 0 id_rezervacija koju ne pisemo
					TFid_knjiga.setText(tablica.getValueAt(odabraniRedak, 2).toString()); //odabrani redak je redak u tablici, a 1 je broj stupca iz kojeg vadimo podatak - u gui 
					TFdatum_rezervacije.setText(tablica.getValueAt(odabraniRedak, 3).toString()); //moramo parsirat u string jer je objektnog oblika
				
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
				String id_clans=TFid_clan.getText();
				String id_knjigas=TFid_knjiga.getText();
				String datum_rezervacijes=TFdatum_rezervacije.getText();
				
				// kreiranje upita u bazu
				
				try 
				{
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","6929");
					
					String upit="UPDATE RWArezervacija SET id_clan=?, id_knjiga=?, datum_rezervacije=? WHERE id_rezervacija=?";
					
					PreparedStatement ps=con.prepareStatement(upit);	
					ps.setString(1, id_clans); //1 upitnik -- stavljamo vrijednost od korisnika u textfield za id clana
					ps.setString(2, id_knjigas);
					ps.setString(3, datum_rezervacijes);
					
					//dodajemo za id da zna ča treba ažurirat
					ps.setInt(4, id_rezervacijaUpdate); //4 upitnik
					
					int updateRedak=ps.executeUpdate();
					
					//poruke korisniku
					if(updateRedak > 0) 
					{
						JOptionPane.showMessageDialog(null, "Uspješno ažuriranje!");
						
						String upit1="SELECT * FROM RWArezervacija";
						Statement stmt=con.createStatement();
						ResultSet rs=stmt.executeQuery(upit1);
						
						DefaultTableModel model=(DefaultTableModel)tablica.getModel();
						model.setRowCount(0);
						
						while(rs.next()) {

							int id_rezervacija=rs.getInt(1); 
							int id_clan=rs.getInt(2);
							int id_knjiga=rs.getInt(3);
							Date datum_rezervacije=rs.getDate(4);
							
							model.addRow(new Object[] {id_rezervacija, id_clan, id_knjiga, datum_rezervacije});
							
						} //while
						
					} //if
					else
					{
						JOptionPane.showMessageDialog(null, "Neuspješno ažuriranje!");
					}//else
					
				} //try
				catch(Exception e1) 
				{
					JOptionPane.showMessageDialog(null, e1);
				} //catch
				
				
				
			} //public void
			
		}); //action listener
		
		btnNewButton_1.setBounds(630, 328, 85, 31);
		pregled.add(btnNewButton_1);
		

		JPanel unos = new JPanel();
		tabbedPane_1.addTab("unos", null, unos, null);
		unos.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("ime člana:");
		lblNewLabel_2.setBounds(39, 105, 96, 13);
		unos.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Prezime člana:");
		lblNewLabel_3.setBounds(39, 138, 107, 13);
		unos.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("id člana:");
		lblNewLabel_4.setBounds(39, 176, 72, 13);
		unos.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("datum rezervacije:");
		lblNewLabel_5.setBounds(39, 277, 119, 13);
		unos.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("ISBN:");
		lblNewLabel_6.setBounds(393, 105, 45, 13);
		unos.add(lblNewLabel_6);
		
		textField = new JTextField();
		textField.setBounds(448, 102, 196, 19);
		unos.add(textField);
		textField.setColumns(10);
		
		imeClan = new JTextField();
		imeClan.setBounds(145, 105, 96, 19);
		unos.add(imeClan);
		imeClan.setColumns(10);
		
		prezimeClan = new JTextField();
		prezimeClan.setColumns(10);
		prezimeClan.setBounds(145, 138, 96, 19);
		unos.add(prezimeClan);
		
		idClan = new JTextField();
		idClan.setColumns(10);
		idClan.setBounds(145, 176, 96, 19);
		unos.add(idClan);
		
		textField_1 = new JTextField();
		textField_1.setBounds(160, 274, 96, 19);
		unos.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnNewButton_2 = new JButton("spremi");
		btnNewButton_2.setBounds(472, 241, 85, 21);
		unos.add(btnNewButton_2);
	}
	
	public void showWindow() {
		frame.setVisible(true);
	}//public void showWindow
}
