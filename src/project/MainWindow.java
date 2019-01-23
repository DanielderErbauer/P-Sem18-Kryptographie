

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import project.encryption.AESCipher;
import project.encryption.Caesar;
import project.gui.WindowModule;

/**
 * Hauptklasse des ganzen Programms.
 * Sollten sich in den Kommentaren sowohl Rechtschreib- als auch Grammatik- und Satzzeichenfehler finden, möchte ich (Daniel)
 * Eventuelle Schäden meines Gehirns durch das Lesen von "Iphigenie auf Tauris" nicht ausschließen.
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
        labelInput.setBounds(10, 10, 80, 20);
        textFieldInput = new JTextField();
        textFieldInput.setBounds(60, 10, 325, 20);
        labelAesKey = new JLabel("En-/Decryption Key: ");
        labelAesKey.setBounds(395, 65, 190, 20);
        textFieldAesKey = new JTextField();
        textFieldAesKey.setBounds(395, 90, 190, 20);
        textFieldAesKey.addActionListener(handler);
        btnEncrypt = new JButton("Encrypt");
        btnEncrypt.setBounds(395, 10, 100, 20);
        btnEncrypt.addActionListener(handler);
        btnDecrypt = new JButton("Decrypt");
        btnDecrypt.setBounds(505, 10, 100, 20);
        btnDecrypt.addActionListener(handler);
        btnPlus = new JButton("+");
        btnPlus.setBounds(395, 40, 100, 20);
        btnPlus.addActionListener(handler);
        btnMinus = new JButton("-");
        btnMinus.setBounds(505, 40, 100, 20);
        btnMinus.addActionListener(handler);
        labelResult = new JLabel("Out:");
        labelResult.setBounds(10, 85, 40, 20);
        textFieldResult = new JTextField();
        textFieldResult.setBounds(60, 85, 325, 20);
        wmList.add(new WindowModule(0, 40));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("P-Sem Kryptographie 2018-2020");
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
        this.setSize(635, 110 + (wmList.size() * 40));
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
    @SuppressWarnings("Duplicates")
    class EventHandler implements ActionListener{

        //Instanz/Referenz zur Hauptklasse
        private final MainWindow instance;

        public EventHandler(MainWindow instance) {
            this.instance = instance;
        }

        @Override
        public void actionPerformed(ActionEvent event) {

            /**
             * Überprüft zuerst, ob alle Module angekreuzt wurden, wenn nicht, gibt eine Fehlermeldung in Form
             * Eines JOptionPane aus, sonst, verschlüsselt den Input nach der/den ausgewählte(n) Verschlüsselungsalgorithmen
             * Und gibt in dann über das unter JTextField aus.
             */
            if(event.getSource() == btnEncrypt) {
                for(int i = 0; i < wmList.size(); i++) {
                    if(!wmList.get(i).isSelected()) {
                        JOptionPane.showMessageDialog(instance, "Alle Module müssen angekreuzt sein!");
                        return;
                    }
                }
                String textToEnkrypt = textFieldInput.getText();
                for(int i = 0; i < wmList.size(); i++) {
                    switch (wmList.get(i).getSelected()) {
                        case "CAESAR":
                            textToEnkrypt = Caesar.encrypt(textToEnkrypt, 3);
                            // Verschlüsselt mit Caesar
                            break;
                        case "AES":
                            if(textFieldAesKey.getText().length() != 16){
                                JFrame popup = new JFrame();
                                JOptionPane.showMessageDialog(popup, "Key must have a length of 16 chars.\nCurrent length is " + textFieldAesKey.getText().length());
                            }
                            try {
                                textToEnkrypt = AESCipher.aesEncryptString(textToEnkrypt, textFieldAesKey.getText());
                                //Verschlüsselt mit AES
                                break;
                            }
                            catch(Exception e){
                                System.out.println(e.getMessage());
                        }

                        case "DES":


                            break;
                        default:
                            break;
                    }
                }
                textFieldResult.setText(textToEnkrypt);
                /**
                 * Fügt zum JFrame ein neues Verschlüsselungsmodul hinzu, solange der JFrame kleiner als der Bildschirm des Benutzers ist.
                 * Bug: Würde das JFrame größer als der Bildschirm werden, würden alle Inhalte der ContentPane nicht mehr gerendert werden.
                 */
            } else if(event.getSource() == btnPlus) {
                if(!(instance.getHeight() + 40 > Toolkit.getDefaultToolkit().getScreenSize().height - 40)){
                    instance.getWindowModuleList().add(new WindowModule(instance.getWindowModuleList().size(), 40));
                    instance.reload();
                }
                return;
                /**
                 * Entfernt ein Verschlüsselungsmodul, solange die Anzahl der vorhandenen Module > 1 ist.
                 */
            } else if(event.getSource() == btnMinus) {
                if(!(wmList.size() == 1)) {
                    instance.getWindowModuleList().removeLast();
                    instance.reload();
                }
                return;
            }  else if (event.getSource() == btnDecrypt){
                for(int i = 0; i < wmList.size(); i++) {
                    if(!wmList.get(i).isSelected()) {
                        JOptionPane.showMessageDialog(instance, "Alle Module müssen angekreuzt sein!");
                        return;
                    }
                }
                String textToDekrypt = textFieldInput.getText();
                for(int i = 0; i < wmList.size(); i++) {
                    switch (wmList.get(i).getSelected()) {
                        case "CAESAR":
                            textToDekrypt = Caesar.decrypt(textToDekrypt, 3);

                            break;
                        case "AES":
                            if(textFieldAesKey.getText().length() != 16){
                                JFrame popup = new JFrame();
                                JOptionPane.showMessageDialog(popup, "Key must have a length of 16 chars.\nCurrent length is " + textFieldAesKey.getText().length());
                            }
                            try {
                                textToDekrypt = AESCipher.aesDecryptString(textToDekrypt, textFieldAesKey.getText());
                                //Verschlüsselt mit AES
                                break;
                            }
                            catch(Exception e){
                                System.out.println(e.getMessage());
                            }

                        case "SHA-1024":

                            break;
                        default:
                            break;
                    }
                }
                textFieldResult.setText(textToDekrypt);
            }
            }
        }


    public static void main(String[] args) {
        new MainWindow();
    }

}
