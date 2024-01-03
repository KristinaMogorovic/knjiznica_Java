package knjiznica;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.TextArea;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class Unos_knjiga {

	private JFrame frame;
	private JTextField godina;
	private JTextField ime;
	private JTextField naziv;
	private JTextField nakladnik;
	private JTextField jezik;
	private JTextField isbn;
	private JTextField slika;
	private JTextField primjerci;
	private JTextField prezime;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Unos_knjiga window = new Unos_knjiga();
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
	public Unos_knjiga() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1079, 572);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNaziv = new JLabel("naziv");
		lblNaziv.setBounds(30, 49, 82, 14);
		frame.getContentPane().add(lblNaziv);
		
		JLabel lblGodinaIzdanja = new JLabel("godina izdanja");
		lblGodinaIzdanja.setBounds(30, 100, 82, 14);
		frame.getContentPane().add(lblGodinaIzdanja);
		
		JLabel lblNakladnik = new JLabel("nakladnik");
		lblNakladnik.setBounds(30, 130, 82, 14);
		frame.getContentPane().add(lblNakladnik);
		
		JLabel lblJezik = new JLabel("jezik");
		lblJezik.setBounds(30, 155, 82, 14);
		frame.getContentPane().add(lblJezik);
		
		JLabel lblOdjeljak = new JLabel("odjeljak");
		lblOdjeljak.setBounds(30, 180, 82, 14);
		frame.getContentPane().add(lblOdjeljak);
		
		JLabel lblIsbn = new JLabel("ISBN");
		lblIsbn.setBounds(30, 205, 82, 14);
		frame.getContentPane().add(lblIsbn);
		
		JLabel lblUrlSlike = new JLabel("URL slike");
		lblUrlSlike.setBounds(30, 230, 82, 14);
		frame.getContentPane().add(lblUrlSlike);
		
		JLabel lblOpisRadnje = new JLabel("opis radnje");
		lblOpisRadnje.setBounds(30, 255, 82, 14);
		frame.getContentPane().add(lblOpisRadnje);
		
		JLabel lblUkupanBrojPrimjeraka = new JLabel("ukupan broj primjeraka");
		lblUkupanBrojPrimjeraka.setBounds(30, 457, 160, 14);
		frame.getContentPane().add(lblUkupanBrojPrimjeraka);
		
		godina = new JTextField();
		godina.setBounds(122, 97, 193, 20);
		frame.getContentPane().add(godina);
		godina.setColumns(10);
		
		naziv = new JTextField();
		naziv.setColumns(10);
		naziv.setBounds(122, 46, 193, 20);
		frame.getContentPane().add(naziv);
		
		nakladnik = new JTextField();
		nakladnik.setColumns(10);
		nakladnik.setBounds(122, 127, 193, 20);
		frame.getContentPane().add(nakladnik);
		
		jezik = new JTextField();
		jezik.setColumns(10);
		jezik.setBounds(122, 152, 193, 20);
		frame.getContentPane().add(jezik);
		
		isbn = new JTextField();
		isbn.setColumns(10);
		isbn.setBounds(122, 202, 193, 20);
		frame.getContentPane().add(isbn);
		
		slika = new JTextField();
		slika.setColumns(10);
		slika.setBounds(122, 227, 193, 20);
		frame.getContentPane().add(slika);
		
		primjerci = new JTextField();
		primjerci.setColumns(10);
		primjerci.setBounds(166, 454, 86, 20);
		frame.getContentPane().add(primjerci);
		
		
		JPanel panel = new JPanel();
		panel.setBackground(UIManager.getColor("Button.highlight"));
		panel.setBounds(387, 33, 227, 211);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblAutor = new JLabel("autor");
		lblAutor.setBounds(100, 23, 82, 14);
		panel.add(lblAutor);
		
		JLabel lblNewLabel = new JLabel("ime:");
		lblNewLabel.setBounds(37, 64, 46, 14);
		panel.add(lblNewLabel);
		
		ime = new JTextField();
		ime.setBounds(112, 61, 95, 20);
		panel.add(ime);
		ime.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("prezime:");
		lblNewLabel_1.setBounds(37, 98, 76, 14);
		panel.add(lblNewLabel_1);
		
		prezime = new JTextField();
		prezime.setBounds(112, 95, 95, 20);
		panel.add(prezime);
		prezime.setColumns(10);
		
		
		JButton btnNewButton_1 = new JButton("dodaj novog autora");
		btnNewButton_1.setBounds(37, 165, 145, 23);
		panel.add(btnNewButton_1);
		
		
		JComboBox odjeljak = new JComboBox();
		odjeljak.setBounds(122, 176, 193, 22);
		frame.getContentPane().add(odjeljak);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","6929") ;
			String upit = "SELECT * FROM RWAklasifikacijska_oznaka";
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery(upit);
			while (rs.next()) {
				String podatak =rs.getString(2);//3. atribut iz tablice
				odjeljak.addItem(podatak);
			}
		}//try
		catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "Greška pri povlačenju podataka o odjeljcima.");
		}//catch
		
		TextArea radnja = new TextArea();
		radnja.setBounds(118, 260, 380, 160);
		frame.getContentPane().add(radnja);
		
		
		JButton btnNewButton = new JButton("unesi");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nazivs, imes, prezimes, godinas, nakladniks, jeziks, odjeljaks, isbns, slikas, radnjas, primjercis;
				
				nazivs = naziv.getText();	
				imes =ime.getText();
				prezimes=prezime.getText();
				godinas=godina.getText();
				nakladniks=nakladnik.getText();
				jeziks=jezik.getText();
				odjeljaks=(String)odjeljak.getSelectedItem();
				isbns=isbn.getText();
				slikas=slika.getText();
				radnjas=radnja.getText();
				primjercis=primjerci.getText();
				
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://student.veleri.hr/kmogorovi?serverTimezone=UTC","kmogorovi","6929") ;
					
					String upitOdjeljak="SELECT id_KlasOznake FROM RWAklasifikacijska_oznaka WHERE odjeljak='"+odjeljaks+"'";
					Statement stmtOdjeljak=con.createStatement();
					ResultSet rsOdjeljak=stmtOdjeljak.executeQuery(upitOdjeljak);
					int idOdjeljak=0;
					if(rsOdjeljak.next()) {
						idOdjeljak=rsOdjeljak.getInt(1);
					}
					String upit1="INSERT INTO RWAknjiga VALUES(NULL, ?, ?, ?, ?, ?, ?, ?,?,?, NULL ,NULL)";
					PreparedStatement psInsertKnjiga=con.prepareStatement(upit1); 
					psInsertKnjiga.setString(1, nazivs);
					psInsertKnjiga.setString(2, godinas);
					psInsertKnjiga.setString(3, isbns);
					psInsertKnjiga.setString(4, nakladniks);
					psInsertKnjiga.setString(5, radnjas);
					psInsertKnjiga.setString(6, jeziks);
					psInsertKnjiga.setInt(7, idOdjeljak);
					psInsertKnjiga.setString(8, slikas);
					psInsertKnjiga.setString(9, primjercis);
					
					int redakaUbaceno = psInsertKnjiga.executeUpdate();
					if (redakaUbaceno==1) {
						JOptionPane.showMessageDialog(null, "knjiga zapisana u bazu");
					}
					else {
						JOptionPane.showMessageDialog(null, "knjiga nije zapisana u bazu");
					}
					

					String upitAutor="SELECT id_autor FROM RWAautor WHERE ime='"+imes+"' AND prezime='"+prezimes+"' ";
					Statement stmtAutor=con.createStatement();
					ResultSet rsAutor=stmtAutor.executeQuery(upitAutor);
					int idAutor=0;
					if(rsAutor.next()) {
						idAutor=rsAutor.getInt(1);
					}
					System.out.println(idAutor);
					
					String upitKnjiga="SELECT id_knjiga FROM RWAknjiga WHERE isbn='"+isbns+"' AND naziv='"+nazivs+"'  ";
					Statement stmtKnjiga=con.createStatement();
					ResultSet rsKnjiga=stmtKnjiga.executeQuery(upitKnjiga);
					int idKnjiga=0;
					if(rsKnjiga.next()) {
						idKnjiga=rsKnjiga.getInt(1);
					}
					System.out.println(idKnjiga);
					
					String upit2="INSERT INTO RWAautor_knjiga VALUES (?,?)";
					PreparedStatement psInsertKnjAut=con.prepareStatement(upit2);
					psInsertKnjAut.setInt(1, idKnjiga);
					psInsertKnjAut.setInt(2, idAutor);

					
				}
				catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2);
				}
				
				
				
			}
		});
		btnNewButton.setBounds(471, 485, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		
	}
	
	public void showWindow() {
		frame.setVisible(true);
	}//public void showWindow
}
