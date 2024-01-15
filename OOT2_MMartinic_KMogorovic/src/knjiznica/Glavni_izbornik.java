package knjiznica;

import knjiznica.clanarine;
import knjiznica.Pregled_autor;
import knjiznica.UP_rezervacija;
import knjiznica.Unos_zaposlenik;
import knjiznica.Unos_posudba;
import knjiznica.Unos_knjiga;
import knjiznica.Unos_clan;
import knjiznica.Pregled_web;
import knjiznica.Pregled_posudba;
import knjiznica.Pregled_knjiga;
import knjiznica.Pregled_clan;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Glavni_izbornik {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Glavni_izbornik window = new Glavni_izbornik();
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
	public Glavni_izbornik() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 466, 730);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton B_unos_clan = new JButton("unos članova");
		B_unos_clan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Unos_clan uc = new Unos_clan(); 
					uc.showWindow();
				}
				catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1);
				}//catch
			}
		});
		B_unos_clan.setBackground(new Color(135, 206, 250));
		B_unos_clan.setBounds(25, 31, 156, 81);
		frame.getContentPane().add(B_unos_clan);
		
		JButton B_unos_knjiga = new JButton("unos knjiga");
		B_unos_knjiga.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Unos_knjiga uk = new Unos_knjiga();
				uk.showWindow();
			}
		});
		B_unos_knjiga.setBackground(new Color(135, 206, 250));
		B_unos_knjiga.setBounds(25, 168, 156, 81);
		frame.getContentPane().add(B_unos_knjiga);
		
		JButton B_unos_posudba = new JButton("unos posudba");
		B_unos_posudba.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Unos_posudba up = new Unos_posudba();
				up.showWindow();
			}
		});
		B_unos_posudba.setBackground(new Color(135, 206, 250));
		B_unos_posudba.setBounds(25, 304, 156, 81);
		frame.getContentPane().add(B_unos_posudba);
		
		JButton B_pregled_clan = new JButton("pregled članova");
		B_pregled_clan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Pregled_clan pc = new Pregled_clan();
				pc.showWindow();
			}
		});
		B_pregled_clan.setBackground(new Color(152, 251, 152));
		B_pregled_clan.setBounds(236, 31, 156, 81);
		frame.getContentPane().add(B_pregled_clan);
		
		JButton B_pregled_knjiga = new JButton("pregled knjiga");
		B_pregled_knjiga.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Pregled_knjiga pk = new Pregled_knjiga();
				pk.showWindow();
			}
		});
		B_pregled_knjiga.setBackground(new Color(152, 251, 152));
		B_pregled_knjiga.setBounds(236, 122, 156, 81);
		frame.getContentPane().add(B_pregled_knjiga);
		
		JButton B_pregled_autor = new JButton("pregled autora");
		B_pregled_autor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Pregled_autor pa = new Pregled_autor();
				pa.showWindow();
			}
		});
		B_pregled_autor.setBackground(new Color(152, 251, 152));
		B_pregled_autor.setBounds(236, 213, 156, 81);
		frame.getContentPane().add(B_pregled_autor);
		
		JButton B_pregled_posudba = new JButton("pregled posudba");
		B_pregled_posudba.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Pregled_posudba pp = new Pregled_posudba();
				pp.showWindow();
			}
		});
		B_pregled_posudba.setBackground(new Color(152, 251, 152));
		B_pregled_posudba.setBounds(236, 304, 156, 81);
		frame.getContentPane().add(B_pregled_posudba);
		
		JButton B_unos_pregled_rezervacija = new JButton("unos i pregled \r\nrezervacija");
		B_unos_pregled_rezervacija.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UP_rezervacija upr = new UP_rezervacija();
				upr.showWindow();
			}
		});
		B_unos_pregled_rezervacija.setBackground(new Color(152, 251, 152));
		B_unos_pregled_rezervacija.setBounds(236, 395, 156, 81);
		frame.getContentPane().add(B_unos_pregled_rezervacija);
		
		JButton B_upiti_web = new JButton("pregled upita sa weba");
		B_upiti_web.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Pregled_web pw = new Pregled_web();
				pw.showWindow();
			}
		});
		B_upiti_web.setBackground(new Color(255, 250, 205));
		B_upiti_web.setBounds(117, 584, 186, 81);
		frame.getContentPane().add(B_upiti_web);
		
		JButton B_clanarina = new JButton("članarine");
		B_clanarina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clanarine c = new clanarine();
				c.showWindow();
			}
		});
		B_clanarina.setBackground(new Color(255, 250, 205));
		B_clanarina.setBounds(221, 493, 171, 81);
		frame.getContentPane().add(B_clanarina);
		
		JButton B_novi_zaposlenik = new JButton("unos novog zaposlenika");
		B_novi_zaposlenik.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Unos_zaposlenik uz = new Unos_zaposlenik();
				uz.showWindow();
			}
		});
		B_novi_zaposlenik.setBackground(new Color(250, 250, 210));
		B_novi_zaposlenik.setBounds(25, 493, 186, 81);
		frame.getContentPane().add(B_novi_zaposlenik);
	}
	
	public void showWindow() {
		frame.setVisible(true);
	}//public void showWindow
}
