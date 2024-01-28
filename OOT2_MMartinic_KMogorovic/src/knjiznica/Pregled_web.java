package knjiznica;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;

public class Pregled_web {

	private JFrame frame;
	private JTable tablica;
	private JTextField trazilica;
	private JLabel lblNewLabel_1;
	private JButton btnNewButton;
	private JButton btnNewButton_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Pregled_web window = new Pregled_web();
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
	public Pregled_web() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 913, 476);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////
		/*Prikaz podataka*/
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(48, 191, 820, 223);
		frame.getContentPane().add(scrollPane);
		
		tablica = new JTable();
		tablica.setEnabled(false);
		scrollPane.setViewportView(tablica);
		tablica.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"email", "upit", "datum"	
			}
		) {
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
			String upit1="SELECT * FROM RWAkontakt";
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery(upit1);
			
			DefaultTableModel model=(DefaultTableModel)tablica.getModel();
			
			while(rs.next()) {

				String email=rs.getString(2);
				String upit=rs.getString(3);
				Date datum=rs.getDate(4);
				
				model.addRow(new Object[] { email, upit, datum});
				
			} //while
			
		}//try
		
		catch(Exception e1)
		{
			JOptionPane.showMessageDialog(null, "Greška pri dohvatu podataka");
		}
		
		////////////////////////////////////////////////////////////////*TRAŽILICA*////////////////////////////////////////////
		trazilica = new JTextField();
		
		btnNewButton = new JButton("Traži");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String pretragas=trazilica.getText();
				
				try {
					
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","6929");
					
					String upit1="SELECT * FROM RWAkontakt WHERE email LIKE ? OR upit LIKE ? OR datum LIKE ?";
					
					PreparedStatement ps=con.prepareStatement(upit1);
					ps.setString(1, "%"+pretragas+"%"); //1. ?
					ps.setString(2, "%"+pretragas+"%");
					ps.setString(3,"%"+pretragas+"%");
					
					ResultSet rs=ps.executeQuery();
					
					DefaultTableModel model=(DefaultTableModel)tablica.getModel();
					model.setRowCount(0);
					
					while(rs.next()) {

						String email=rs.getString(2);
						String upit=rs.getString(3);
						Date datum=rs.getDate(4);
						
						model.addRow(new Object[] { email, upit, datum});
						
					} //while
					
				} //try
				catch(Exception e1)
				{
					JOptionPane.showMessageDialog(null, "Traženje podataka nije omogućeno");
				}//catch
				
				
			} //public void
			
		}); //action listener
		
		
		btnNewButton.setBounds(368, 113, 85, 28);
		frame.getContentPane().add(btnNewButton);
		
		trazilica.setBounds(66, 113, 237, 28);
		frame.getContentPane().add(trazilica);
		trazilica.setColumns(10);

		
		lblNewLabel_1 = new JLabel("PREGLED UPITA S WEBA");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(348, 40, 237, 28);
		frame.getContentPane().add(lblNewLabel_1);
		
		btnNewButton_1 = new JButton("ponovno učitaj");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","6929");
					String upit1="SELECT * FROM RWAkontakt";
					Statement stmt=con.createStatement();
					ResultSet rs=stmt.executeQuery(upit1);
					
					DefaultTableModel model=(DefaultTableModel)tablica.getModel();
					model.setRowCount(0);
					while(rs.next()) {

						String email=rs.getString(2);
						String upit=rs.getString(3);
						Date datum=rs.getDate(4);
						
						model.addRow(new Object[] { email, upit, datum});
						
					} //while
					
				}//try
				
				catch(Exception e1)
				{
					JOptionPane.showMessageDialog(null, "Greška pri dohvatu podataka");
				}
				
			}
		});
		btnNewButton_1.setBounds(727, 153, 131, 28);
		frame.getContentPane().add(btnNewButton_1);
		
		
	} //public void inicialize
	
	public void showWindow() {
		frame.setVisible(true);
	}//public void showWindow


}
