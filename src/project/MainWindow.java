package project;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import project.encryption.Encryptions;
import project.gui.WindowModule;

/**
 * Hauptklasse des ganzen Programms.
 * Sollten sich in den Kommentaren sowohl Rechtschreib- als auch Grammatik- und Satzzeichenfehler finden, m�chte ich (Daniel)
 * Eventuelle Sch�den meines Gehirns durch das Lesen von "Iphigenie auf Tauris" nicht ausschlie�en. 
 * 
 * @author DanielderErbauer
 * @author Niels
 * @author Ernst
 *
 */
public class MainWindow extends JFrame{
	
	private JLabel labelInput;
	private JTextField textFieldInput;
	private JLabel labelAesKey;
	private JTextField textFieldAesKey;
	private JButton btnEncrypt;
	private JButton btnDecrypt;
	private JButton btnPlus;
	private JButton btnMinus;
	private JLabel labelResult;
	private JTextField textFieldResult;
	
	private EventHandler handler;
	private LinkedList<WindowModule> wmList;
	
	/**
	 * Konstruktor der Hauptklasse des ganzen Programms.
	 */
	public MainWindow() {
		handler = new EventHandler(this);
		wmList = new LinkedList<>();
		initWindow();
	}
	
	public void initWindow() {
		labelInput = new JLabel("Input: ");
		labelInput.setBounds(10, 10, 40, 20);
		textFieldInput = new JTextField();
		textFieldInput.setBounds(60, 10, 325, 20);
		labelAesKey = new JLabel("AES Key: ");
		labelAesKey.setBounds(395, 65, 190, 20);
		textFieldAesKey = new JTextField();
		textFieldAesKey.setBounds(395, 90, 190, 20);
		btnEncrypt = new JButton("Encrypt!");
		btnEncrypt.setBounds(395, 10, 90, 20);
		btnEncrypt.addActionListener(handler);
		btnDecrypt = new JButton("Decrypt!");
		btnDecrypt.setBounds(495, 10, 90, 20);
		btnDecrypt.addActionListener(handler);
		btnPlus = new JButton("+");
		btnPlus.setBounds(395, 40, 90, 20);
		btnPlus.addActionListener(handler);
		btnMinus = new JButton("-");
		btnMinus.setBounds(495, 40, 90, 20);
		btnMinus.addActionListener(handler);
		labelResult = new JLabel("Out:");
		labelResult.setBounds(10, 85, 40, 20);
		textFieldResult = new JTextField();
		textFieldResult.setBounds(60, 85, 325, 20);
		wmList.add(new WindowModule(0, 40));
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Kryptographie Projekt 2018/2019");
		this.setLocation(0, 40);
		this.setResizable(false);
		reload();
		this.setVisible(true);
	}
	
	/**
	 * Methode zum Neu-Laden aller Elemente im JFrame.
	 * Wird auch im Konstruktor verwendet, um redundanten Code zu vermeiden.
	 */
	public void reload() {
		this.getContentPane().removeAll();
		this.setSize(600, 110 + (wmList.size() * 40));
		this.getContentPane().setLayout(null);
		this.getContentPane().add(labelInput);
		this.getContentPane().add(textFieldInput);
		this.getContentPane().add(btnEncrypt);
		this.getContentPane().add(btnDecrypt);
		this.getContentPane().add(btnPlus);
		this.getContentPane().add(btnMinus);
		this.getContentPane().add(textFieldAesKey);
		this.getContentPane().add(labelAesKey);
		for(int i = 0; i < wmList.size(); i++) {
			this.getContentPane().add(wmList.get(i));
		}
		textFieldResult.setBounds(60, 50 + (wmList.size() * 40), 325, 20);
		labelResult.setBounds(10, 50 + (wmList.size() * 40), 40, 20);
		this.getContentPane().add(labelResult);
		this.getContentPane().add(textFieldResult);
	}
	
	
	public LinkedList<WindowModule> getWindowModuleList() {
		return wmList;
	}

	/**
	 * Interner EventHandler zum handlen aller Events des JFrames(Denglish for se win :P)
	 *
	 */
	class EventHandler implements ActionListener {
		
		//Instanz/Referenz zur Hauptklasse
		private final MainWindow instance;
		
		public EventHandler(MainWindow instance) {
			this.instance = instance;
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			
			/**
			 * �berpr�ft zuerst, ob alle Module angekreuzt wurden, wenn nicht, gibt eine Fehlermeldung in Form
			 * Eines JOptionPane aus, sonst, verschl�sselt den Input nach der/den ausgew�hlte(n) Verschl�sselungsalgorithmen
			 * Und gibt in dann �ber das unter JTextField aus.
			 */
			if(event.getSource() == btnEncrypt) {
				for(int i = 0; i < wmList.size(); i++) {
					if(!wmList.get(i).isSelected()) {
						JOptionPane.showMessageDialog(instance, "Alle Module m�ssen angekreuzt sein!");
						return;
					}
				}
				String textToEnkrypt = textFieldInput.getText();
				for(int i = 0; i < wmList.size(); i++) {
					switch (wmList.get(i).getSelected()) {
					case "CAESAR":
						textToEnkrypt = Encryptions.caesar(textToEnkrypt, 3);
						break;
					case "SHA-256":
						
						break;
						
					case "SHA-1024":
						
						break;
					default:
						break;
					}
				}
				textFieldResult.setText(textToEnkrypt);
			/**
			 * F�gt zum JFrame ein neues Verschl�sselungsmodul hinzu, solange der JFrame kleiner als der Bildschirm des Benutzers ist.
			 * Bug: W�rde das JFrame gr��er als der Bildschirm werden, w�rden alle Inhalte der ContentPane nicht mehr gerendert werden.
			 */
			} else if(event.getSource() == btnPlus) {
				if(!(instance.getHeight() + 40 > Toolkit.getDefaultToolkit().getScreenSize().height - 40)){
					instance.getWindowModuleList().add(new WindowModule(instance.getWindowModuleList().size(), 40));
					instance.reload();
				}
				return;
			/**
			 * Entfernt ein Verschl�sselungsmodul, solange die Anzahl der vorhandenen Module > 1 ist.
			 */
			} else if(event.getSource() == btnMinus) {
				if(!(wmList.size() == 1)) {
					instance.getWindowModuleList().removeLast();
					instance.reload();
				}
				return;
			}
		}
		
	}
	
	public static void main(String[] args) {
		new MainWindow();
	}
	
}
