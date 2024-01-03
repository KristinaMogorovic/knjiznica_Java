package knjiznica;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class Unos_clan {

	private JFrame frame;
	private JTextField ime;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JPasswordField passwordField;

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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		JLabel lblNewLabel_2_4 = new JLabel("Datum upisa:");
		lblNewLabel_2_4.setBounds(291, 241, 101, 13);
		frame.getContentPane().add(lblNewLabel_2_4);
		
		JLabel lblNewLabel_2_5 = new JLabel("Datum isteka:");
		lblNewLabel_2_5.setBounds(459, 241, 101, 13);
		frame.getContentPane().add(lblNewLabel_2_5);
		
		JLabel lblNewLabel_2_6 = new JLabel("Lozinka:");
		lblNewLabel_2_6.setBounds(23, 328, 101, 13);
		frame.getContentPane().add(lblNewLabel_2_6);
		
		ime = new JTextField();
		ime.setBounds(87, 92, 155, 19);
		frame.getContentPane().add(ime);
		ime.setColumns(10);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(87, 128, 155, 19);
		frame.getContentPane().add(textField);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(87, 161, 155, 19);
		frame.getContentPane().add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(87, 198, 155, 19);
		frame.getContentPane().add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(291, 264, 101, 19);
		frame.getContentPane().add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(459, 264, 101, 19);
		frame.getContentPane().add(textField_4);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(78, 325, 93, 19);
		frame.getContentPane().add(passwordField);
		
		JButton btnNewButton = new JButton("Spremi");
		btnNewButton.setBounds(40, 387, 131, 47);
		frame.getContentPane().add(btnNewButton);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(23, 264, 234, 21);
		frame.getContentPane().add(comboBox);
	}
	
	public void showWindow() {
		frame.setVisible(true);
	}//public void showWindow
}
