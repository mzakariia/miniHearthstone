package view;

import javax.swing.JButton;

import model.heroes.Hero;

public class HeroButton extends JButton {

	private Hero hero;
	public HeroButton(Hero h) {
		hero=h;
	}
	public Hero getHero() {
		return hero;
	}
	
}
