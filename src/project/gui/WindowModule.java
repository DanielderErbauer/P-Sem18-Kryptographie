package project.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

/**
 * 
 * WindowModul Klasse
 *
 */
public class WindowModule extends JPanel{
	
	private WindowModule next;
	
	private JCheckBox cb1;
	private JCheckBox cb2;
	private JCheckBox cb3;
	
	public WindowModule(int pos, int space) {
		
		this.setBackground(Color.BLACK);
		this.setBounds(10, 40 * pos + space, 375, 40);
		
		cb1 = new JCheckBox("CAESAR");
		cb2 = new JCheckBox("SHA-256");
		cb3 = new JCheckBox("SHA-1024");
		
		EventHandler handler = new EventHandler();
		
		cb1.addActionListener(handler);
		cb2.addActionListener(handler);
		cb3.addActionListener(handler);
		
		this.add(cb1);
		this.add(cb2);
		this.add(cb3);
		
		
	}
	
	public boolean isSelected() {
		return cb1.isSelected() || cb2.isSelected() || cb3.isSelected();
	}
	
	public String getSelected() {
		if(cb1.isSelected()) return cb1.getText();
		else if(cb2.isSelected()) return cb2.getText();
		else if(cb3.isSelected()) return cb3.getText();
		else return null;
	}
	
	public WindowModule getNext() {
		return next;
	}
	
	public void setNext(WindowModule next) {
		this.next = next;
	}
	
	class EventHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if(event.getSource() == cb1) {
				if(cb1.isSelected()) {
					cb2.setEnabled(false);
					cb3.setEnabled(false);
				} else {
					cb2.setEnabled(true);
					cb3.setEnabled(true);
				}
				return;
			} else if(event.getSource() == cb2) {
				if(cb2.isSelected()) {
					cb1.setEnabled(false);
					cb3.setEnabled(false);
				} else {
					cb1.setEnabled(true);
					cb3.setEnabled(true);
				}
				return;
			} else if(event.getSource() == cb3) {
				if(cb3.isSelected()) {
					cb1.setEnabled(false);
					cb2.setEnabled(false);
				} else {
					cb1.setEnabled(true);
					cb2.setEnabled(true);
				}
				return;
			} 
		}
		
	}
}
