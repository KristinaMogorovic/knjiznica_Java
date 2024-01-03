package knjiznica;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;

public class Unos_posudba {

	private JFrame frame;
	private JTextField imeClan;
	private JTextField prezimeClan;
	private JTextField idClan;
	private JTextField isbnKnjiga;
	private JTextField datum_posudbe;
	private JTextField datum_povrata;
	private JTextField nazivKnjiga;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Unos_posudba window = new Unos_posudba();
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
	public Unos_posudba() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 802, 705);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(27, 132, 274, 196);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ČLAN");
		lblNewLabel.setBounds(112, 23, 45, 13);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Ime:");
		lblNewLabel_1.setBounds(10, 71, 75, 13);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Prezime:");
		lblNewLabel_2.setBounds(10, 104, 75, 13);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("ID člana:");
		lblNewLabel_3.setBounds(10, 143, 75, 13);
		panel.add(lblNewLabel_3);
		
		imeClan = new JTextField();
		imeClan.setBounds(95, 68, 96, 19);
		panel.add(imeClan);
		imeClan.setColumns(10);
		
		prezimeClan = new JTextField();
		prezimeClan.setColumns(10);
		prezimeClan.setBounds(95, 98, 96, 19);
		panel.add(prezimeClan);
		
		idClan = new JTextField();
		idClan.setColumns(10);
		idClan.setBounds(95, 137, 96, 19);
		panel.add(idClan);
		
		/* tu san mislila da se doda provjera kad upisujes. 
		 * 
		 * Npr. kad upises isbn da se automatski u textfield upise naziv knjige, jer u knjiznici skeniraju kod.
		 * 
		 * Isto za člana - ako upises id automatski se upise ime i prezime
		 * Kad upises ime i prezime automatski se upise id.
		 * 
		 * ako to ne rivamo napravit cemo samo nesto maknut 
		 * 
		 */
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(363, 131, 274, 197);
		frame.getContentPane().add(panel_1);
		
		JLabel lblKnjiga = new JLabel("KNJIGA");
		lblKnjiga.setBounds(112, 23, 45, 13);
		panel_1.add(lblKnjiga);
		
		JLabel lblNewLabel_1_1 = new JLabel("ISBN: ");
		lblNewLabel_1_1.setBounds(10, 71, 45, 13);
		panel_1.add(lblNewLabel_1_1);
		
		isbnKnjiga = new JTextField();
		isbnKnjiga.setColumns(10);
		isbnKnjiga.setBounds(65, 68, 172, 19);
		panel_1.add(isbnKnjiga);
		
		nazivKnjiga = new JTextField();
		nazivKnjiga.setBounds(65, 112, 172, 19);
		panel_1.add(nazivKnjiga);
		nazivKnjiga.setColumns(10);
		
		JLabel lblNewLabel_8 = new JLabel("Naziv:");
		lblNewLabel_8.setBounds(10, 115, 45, 13);
		panel_1.add(lblNewLabel_8);
		
		JLabel lblNewLabel_4 = new JLabel("UNOS POSUDBE");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_4.setBounds(303, 41, 145, 13);
		frame.getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_7 = new JLabel("New label");
		lblNewLabel_7.setBounds(0, 0, 45, 13);
		frame.getContentPane().add(lblNewLabel_7);
		
		JLabel lblNewLabel_5 = new JLabel("Datum posudbe: ");
		lblNewLabel_5.setBounds(35, 415, 128, 13);
		frame.getContentPane().add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Predviđeni datum povrata:");
		lblNewLabel_6.setBounds(35, 457, 175, 13);
		frame.getContentPane().add(lblNewLabel_6);
		
		datum_posudbe = new JTextField();
		datum_posudbe.setBounds(131, 415, 128, 19);
		frame.getContentPane().add(datum_posudbe);
		datum_posudbe.setColumns(10);
		
		datum_povrata = new JTextField();
		datum_povrata.setBounds(215, 454, 96, 19);
		frame.getContentPane().add(datum_povrata);
		datum_povrata.setColumns(10);
		
		JButton btnNewButton = new JButton("Spremi");
		btnNewButton.setBounds(474, 435, 85, 21);
		frame.getContentPane().add(btnNewButton);
	}
	
	public void showWindow() {
		frame.setVisible(true);
	}//public void showWindow
}
