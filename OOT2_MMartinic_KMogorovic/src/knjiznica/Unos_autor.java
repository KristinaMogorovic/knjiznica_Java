package knjiznica;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;

public class Unos_autor {

	private JFrame frame;
	private JTextField ime;
	private JTextField prezime;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Unos_autor window = new Unos_autor();
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
	public Unos_autor() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 431, 250);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Ime autora:");
		lblNewLabel.setBounds(27, 64, 86, 13);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Prezime autora:");
		lblNewLabel_1.setBounds(27, 105, 109, 13);
		frame.getContentPane().add(lblNewLabel_1);
		
		ime = new JTextField();
		ime.setBounds(123, 61, 126, 19);
		frame.getContentPane().add(ime);
		ime.setColumns(10);
		
		prezime = new JTextField();
		prezime.setBounds(123, 102, 126, 19);
		frame.getContentPane().add(prezime);
		prezime.setColumns(10);
		
		JButton btnNewButton = new JButton("Spremi");
		btnNewButton.setBounds(272, 151, 85, 21);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_2 = new JLabel("Unos autora");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_2.setBounds(171, 21, 143, 13);
		frame.getContentPane().add(lblNewLabel_2);
	}
}
