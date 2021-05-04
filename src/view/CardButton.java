package view;

import javax.swing.JButton;

import model.cards.Card;

public class CardButton extends JButton {
	
	private Card card;
	
	public CardButton(Card c) {
		this.setCard(c);
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card c) {
		this.card = c;
	}
	

}
