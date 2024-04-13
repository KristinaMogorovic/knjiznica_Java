package knjiznica;

import knjiznica.Glavni_izbornik;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

public class Log_In {

	private JFrame frame;
	private JTextField sifra_knjiznicar;
	private JPasswordField lozinka;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Log_In window = new Log_In();
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
	public Log_In() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 513, 302);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("šifra knjižničara:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNewLabel.setBounds(133, 102, 114, 13);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("lozinka: ");
		lblNewLabel_1.setBounds(133, 158, 76, 13);
		frame.getContentPane().add(lblNewLabel_1);
		
		sifra_knjiznicar = new JTextField();
		sifra_knjiznicar.setBounds(257, 99, 96, 19);
		frame.getContentPane().add(sifra_knjiznicar);
		sifra_knjiznicar.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Log in");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(194, 33, 104, 34);
		frame.getContentPane().add(lblNewLabel_2);
		
		lozinka = new JPasswordField();
		lozinka.setBounds(257, 155, 96, 19);
		frame.getContentPane().add(lozinka);
		

		
		JButton btnNewButton = new JButton("LOG IN");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String sifra_knjiznicars, lozinkas;
				
				sifra_knjiznicars = sifra_knjiznicar.getText();
				lozinkas = new String (lozinka.getPassword());	
				
				try {
					
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","****") ;
					
					
					String upit="SELECT * FROM RWA_knjiznicar WHERE sifra_knjiznicar=? AND lozinka=?";//pi�emo SQL kod koj �e se izvr�it na bazi
					PreparedStatement ps=con.prepareStatement(upit)  ;//klasa koja zna to slat na server; con �alje upit serveru
					ps.setString(1, sifra_knjiznicars);
					ps.setString(2, lozinkas);
					ResultSet rs=ps.executeQuery() ; // izvr�avanje upita
					
					if(rs.next()) {
						Glavni_izbornik gi = new Glavni_izbornik(); 
						gi.showWindow();
					}//if
					else {
						
						JOptionPane.showMessageDialog(null, "korisnik ne postoji ili je kriva lozinka");
					}//else
					
					
				}//try
				catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Greška pri spajanju ");
				}//catch
				
			}
		});
		btnNewButton.setBounds(346, 215, 85, 21);
		frame.getContentPane().add(btnNewButton);
	}
	
	public void showWindow() {
		frame.setVisible(true);
	}//public void showWindow
	
}
