package knjiznica;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;

public class UP_rezervacija {

	private JFrame frame;
	private JTable table;
	private JTextField trazi;
	private JTextField textField;
	private JTextField imeClan;
	private JTextField prezimeClan;
	private JTextField idClan;
	private JTextField textField_1;

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
		frame.setBounds(100, 100, 844, 749);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Pregled i unos rezervacija");
		lblNewLabel.setBounds(283, 34, 289, 29);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		frame.getContentPane().add(lblNewLabel);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(20, 89, 784, 588);
		frame.getContentPane().add(tabbedPane_1);
		
		JPanel pregled = new JPanel();
		tabbedPane_1.addTab("pregled", null, pregled, null);
		pregled.setLayout(null);
		
		table = new JTable();
		table.setBounds(10, 56, 451, 462);
		pregled.add(table);
		
		trazi = new JTextField();
		trazi.setColumns(10);
		trazi.setBounds(581, 64, 166, 19);
		pregled.add(trazi);
		
		JLabel lblNewLabel_1 = new JLabel("Traži:");
		lblNewLabel_1.setBounds(497, 70, 45, 13);
		pregled.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Obriši");
		btnNewButton.setBounds(505, 106, 85, 21);
		pregled.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Ažuriraj");
		btnNewButton_1.setBounds(506, 147, 85, 21);
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
