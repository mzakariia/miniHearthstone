package view;

import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.text.IconView;

public class ChoosingHeroPanel extends JPanel{
	private ArrayList<JButton> heros;
	public ChoosingHeroPanel() {
		this.setLayout(new FlowLayout());
		JButton b = new JButton();
		JButton b1 = new JButton();
		JButton b2 = new JButton();
		JButton b3 = new JButton();
		JButton b4 = new JButton();
		b.setText("Hunter");
		b.setIcon(new ImageIcon("images/Hunter.jpeg"));
		b1.setText("Mage");
		b1.setIcon(new ImageIcon("images/Mage.jpeg"));
		b2.setText("Paladin");
		b2.setIcon(new ImageIcon("images/Paladin.jpeg"));
		b3.setText("Warlock");
		b3.setIcon(new ImageIcon("images/Warlock.jpeg"));
		b4.setText("Priest");
		b4.setIcon(new ImageIcon("images/Priest.jpeg"));
		this.add(b);
		this.add(b1);
		this.add(b2);
		this.add(b3);
		this.add(b4);
		heros= new ArrayList<JButton>();
		heros.add(b);
		heros.add(b1);
		heros.add(b2);
		heros.add(b3);
		heros.add(b4);
	}
	
	public void setHeros(ArrayList<JButton> l) {
		heros=l;
	}
	public ArrayList<JButton> getHeros(){
		return this.heros;
	}

}
