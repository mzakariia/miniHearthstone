package controller;


import java.util.*;
import java.awt.event.*;
import java.io.IOException;
import java.awt.*;
import javax.swing.*;
import engine.Game;
import engine.GameListener;
import exceptions.CannotAttackException;
import exceptions.FullFieldException;
import exceptions.FullHandException;
import exceptions.HeroPowerAlreadyUsedException;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughManaException;
import exceptions.NotSummonedException;
import exceptions.NotYourTurnException;
import exceptions.TauntBypassException;
import model.cards.Card;
import model.cards.minions.Minion;
import model.cards.spells.*;
import model.heroes.*;
import view.CardButton;
import view.ChoosingHeroPanel;
import view.GameView;
import view.HeroButton;

public class Controller implements ActionListener, GameListener {
	private Hero h1;
	private Hero h2;
	private GameView view;
	private Game game;
	private Card attacker;
	private Minion attacked;
	private Hero target;
	public Controller() {
		view = new GameView();
		ArrayList<JButton> p1 = ((ChoosingHeroPanel)view.getP1()).getHeros();
		ArrayList<JButton> p2 = ((ChoosingHeroPanel)view.getP2()).getHeros();
		JButton b = (JButton) ((view.getP3()).getComponents())[0];
		for(int i =0;i<p1.size();i++) {
			p2.get(i).addActionListener(this);
			p2.get(i).setActionCommand(p2.get(i).getText());
			p1.get(i).addActionListener(this);
			p1.get(i).setActionCommand(p1.get(i).getText());	
		}
		b.addActionListener(this);
		b.setActionCommand(b.getText());
	}	
	public void onGameOver() {

		int n=0;
		if (h1.getCurrentHP()==0)
			n=1;
		else
			n=2;
		view.onGameOver(n);
	}

	public void link() {
		attacker=null;
		target=null;
		attacked=null;
		view.getContentPane().removeAll();
		view.onStartingGame();
		view.getCurrentHeroHand().clear();
		view.getCurrentHeroField().clear();
		view.getOppHeroField().clear();
		Hero curHero = game.getCurrentHero();
		Hero oppHero = game.getOpponent();
		for(Card c : curHero.getHand() ) {
			CardButton b = new CardButton(c);
			String s = c.getName() +" "+c.getRarity()+" "+ "ManaCost: " +c.getManaCost();
			if(c instanceof Minion) {
				s+=" Attack: "+((Minion)c).getAttack()+" currentHP: "+((Minion)c).getCurrentHP()+"/"+((Minion)c).getMaxHP()+" ";
				if(((Minion)c).isTaunt())
					s+="Taunt ";
				if(((Minion)c).isDivine())
					s+="Divine ";
				if(!((Minion)c).isSleeping())
					s+="Charge";
			}
			b.setMaximumSize(b.getMaximumSize());;
			b.setText(s);
			if( c instanceof LeechingSpell || c instanceof MinionTargetSpell || c instanceof HeroTargetSpell)
				b.setActionCommand("Attack");
			else 
				b.setActionCommand("Play");
			b.addActionListener(this);
			view.getCurrentHand().add(b);
			view.getCurrentHeroHand().add(b);
		}
		for(Card c : oppHero.getHand() ) {
			JButton b = new JButton();
			b.setEnabled(false);
			b.setIcon(new ImageIcon("images/Deck.jpeg"));
			b.setDisabledIcon(new ImageIcon("images/Deck.jpeg"));
			view.getOpponentHand().add(b);
			b.setOpaque(false);
			b.setContentAreaFilled(false);
			b.setBorderPainted(false);
		}
		HeroButton b1 = new HeroButton(game.getCurrentHero());
		String w="";
		if(game.getCurrentHero()==h1)
			w="Player 1: ";
		else 
			w="Player 2: ";
		b1.setText(w+curHero.getName());
		b1.setOpaque(false);
		b1.setContentAreaFilled(false);
		b1.setBorderPainted(false);
		view.setCurHero(b1);
		b1.setActionCommand("Attack");
		b1.addActionListener(this);
		JButton b2 = new JButton();
		b2.setText("HP:"+curHero.getCurrentHP()+"/30");
		b2.setEnabled(false);
		b2.setOpaque(false);
		b2.setContentAreaFilled(false);
		b2.setBorderPainted(false);
		JButton b3 = new JButton();
		b3.setText("Mana Crystals:"+curHero.getCurrentManaCrystals()+"/"+curHero.getTotalManaCrystals());
		b3.setEnabled(false);
		b3.setOpaque(false);
		b3.setContentAreaFilled(false);
		b3.setBorderPainted(false);
		JButton b4 = new JButton();
		b4.setIcon(new ImageIcon("images/Deck.jpeg"));
		b4.setDisabledIcon(new ImageIcon("images/Deck.jpeg"));
		b4.setText(curHero.getDeck().size()+"");
		b4.setEnabled(false);
		b4.setOpaque(false);
		b4.setContentAreaFilled(false);
		b4.setBorderPainted(false);
		JButton b5 = new JButton();
		b5.addActionListener(this);
		b5.setActionCommand("Use Hero Power");
		b5.setIcon(new ImageIcon("images/HeroPower.png"));
		b5.setText("Hero Power");
		if(curHero.isHeroPowerUsed()) b5.setEnabled(false);
		b5.setOpaque(false);
		b5.setContentAreaFilled(false);
		b5.setBorderPainted(false);
		JButton b6 = new JButton();
		b6.addActionListener(this);
		b6.setActionCommand("End Turn");
		b6.setIcon(new ImageIcon("images/EndTurn.png"));
		b6.setOpaque(false);
		b6.setContentAreaFilled(false);
		b6.setBorderPainted(false);
		HeroButton b7 = new HeroButton(oppHero);
		if(game.getCurrentHero()==h2)
			w="Player 1: ";
		else 
			w="Player 2: ";
		b7.setText(w+oppHero.getName());
		b7.setActionCommand("Attack");
		b7.addActionListener(this);
		b7.setOpaque(false);
		b7.setContentAreaFilled(false);
		b7.setBorderPainted(false);
		view.setOppHero(b7);
		JButton b8 = new JButton();
		b8.setText("HP:"+oppHero.getCurrentHP()+"/30");
		b8.setEnabled(false);
		b8.setOpaque(false);
		b8.setContentAreaFilled(false);
		b8.setBorderPainted(false);
		JButton b9 = new JButton();
		b9.setText("Mana Crystals:"+oppHero.getCurrentManaCrystals()+"/"+oppHero.getTotalManaCrystals());
		b9.setEnabled(false);
		b9.setOpaque(false);
		b9.setContentAreaFilled(false);
		b9.setBorderPainted(false);
		JButton b10 = new JButton();
		b10.setIcon(new ImageIcon("images/Deck.jpeg"));
		b10.setDisabledIcon(new ImageIcon("images/Deck.jpeg"));
		b10.setText(oppHero.getDeck().size()+"");
		b10.setEnabled(false);
		b10.setOpaque(false);
		b10.setContentAreaFilled(false);
		b10.setBorderPainted(false);
		view.getCurrentHero().add(b1);
		view.getCurrentHero().add(b2);
		view.getCurrentHero().add(b3);
		view.getCurrentHero().add(b4);
		view.getCurrentHero().add(b5);
		view.getCurrentHero().add(b6);
		view.getOpponentHero().add(b7);
		view.getOpponentHero().add(b8);
		view.getOpponentHero().add(b9);
		view.getOpponentHero().add(b10);
		for(Card c : curHero.getField() ) {
			CardButton b = new CardButton(c);
			String s = c.getName() +" "+c.getRarity()+" "+ "ManaCost: " +c.getManaCost();
			if(c instanceof Minion) {
				s+=" Attack: "+((Minion)c).getAttack()+" currentHP: "+((Minion)c).getCurrentHP()+"/"+((Minion)c).getMaxHP()+" ";
				if(((Minion)c).isTaunt())
					s+="Taunt ";
				if(((Minion)c).isDivine())
					s+="Divine ";
				if(!((Minion)c).isSleeping())
					s+="Charge ";
				if(!((Minion)c).isAttacked() && !((Minion)c).isSleeping())
					s+="|| Can Attack";
				else if(((Minion)c).isAttacked() && !((Minion)c).isSleeping())
					s+="|| Already Attacked";
				else 
					s+="|| Can't Attack";
			}
			b.setMaximumSize(b.getMaximumSize());
			b.setText(s);
			b.setActionCommand("Attack");
			b.addActionListener(this);
			view.getCurrentField().add(b);
			view.getCurrentHeroField().add(b);
		}
		for(Card c : oppHero.getField() ) {
			CardButton b = new CardButton(c);
			String s = c.getName() +" "+c.getRarity()+" "+ "ManaCost: " +c.getManaCost();
			if(c instanceof Minion) {
				s+=" Attack: "+((Minion)c).getAttack()+" currentHP: "+((Minion)c).getCurrentHP()+"/"+((Minion)c).getMaxHP()+" ";
				if(((Minion)c).isTaunt())
					s+="Taunt ";
				if(((Minion)c).isDivine())
					s+="Divine ";
				if(!((Minion)c).isSleeping())
					s+="Charge";
			}
			b.setMaximumSize(b.getMaximumSize());
			b.setText(s);
			b.setActionCommand("Attack");
			b.addActionListener(this);
			view.getOpponentField().add(b);
			view.getOppHeroField().add(b);
		}
		view.revalidate();
		view.repaint();

	}

	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Hunter")) {
			if(e.getSource().equals(((ChoosingHeroPanel)view.getP1()).getHeros().get(0))) {
				try {
					h1 = new Hunter();
				} catch (IOException e1) {
					view.handleException(e1.getMessage());
				} catch (CloneNotSupportedException e1) {
					view.handleException(e1.getMessage());
				}
			}	
			else {
				try {
					h2 = new Hunter();
				} catch (IOException e1) {
					view.handleException(e1.getMessage());
				} catch (CloneNotSupportedException e1) {
					view.handleException(e1.getMessage());
				}
			}		}	
		if(e.getActionCommand().equals("Mage")) {
			if(e.getSource().equals(((ChoosingHeroPanel)view.getP1()).getHeros().get(1))) {
				try {
					h1 = new Mage();
				} catch (IOException e1) {
					view.handleException(e1.getMessage());
				} catch (CloneNotSupportedException e1) {
					view.handleException(e1.getMessage());
				}
			}	
			else {
				try {
					h2 = new Mage();
				} catch (IOException e1) {
					view.handleException(e1.getMessage());
				} catch (CloneNotSupportedException e1) {
					view.handleException(e1.getMessage());
				}
			}
		}	
		if(e.getActionCommand().equals("Paladin")) {
			if(e.getSource().equals(((ChoosingHeroPanel)view.getP1()).getHeros().get(2))) {
				try {
					h1 = new Paladin();
				} catch (IOException e1) {
					view.handleException(e1.getMessage());
				} catch (CloneNotSupportedException e1) {
					view.handleException(e1.getMessage());
				}
			}	
			else {
				try {
					h2 = new Paladin();
				} catch (IOException e1) {
					view.handleException(e1.getMessage());
				} catch (CloneNotSupportedException e1) {
					view.handleException(e1.getMessage());
				}
			}
		}
		if(e.getActionCommand().equals("Warlock")) {
			if(e.getSource().equals(((ChoosingHeroPanel)view.getP1()).getHeros().get(3))) {
				try {
					h1 = new Warlock();
				} catch (IOException e1) {
					view.handleException(e1.getMessage());
				} catch (CloneNotSupportedException e1) {
					view.handleException(e1.getMessage());
				}
			}	
			else {
				try {
					h2 = new Warlock();
				} catch (IOException e1) {
					view.handleException(e1.getMessage());
				} catch (CloneNotSupportedException e1) {
					view.handleException(e1.getMessage());
				}
			}
		}
		if(e.getActionCommand().equals("Priest")) {
			if(e.getSource().equals(((ChoosingHeroPanel)view.getP1()).getHeros().get(4))) {
				try {
					h1 = new Priest();
				} catch (IOException e1) {
					view.handleException(e1.getMessage());
				} catch (CloneNotSupportedException e1) {
					view.handleException(e1.getMessage());
				}
			}	
			else {
				try {
					h2 = new Priest();
				} catch (IOException e1) {
					view.handleException(e1.getMessage());
				} catch (CloneNotSupportedException e1) {
					view.handleException(e1.getMessage());
				}
			}
		}
		if(e.getActionCommand().equals("Start game")) {
			if(h1!=null && h2!=null) {
				view.getContentPane().removeAll();
				view.onStartingGame();
				try {
					game = new Game(h1,h2);
					game.setListener(this);
					this.link();
				} catch (FullHandException | CloneNotSupportedException e1) {
					view.handleException(e1.getMessage());
				}

			}	
		}
		if(e.getActionCommand().equals("Attack")) {
			if((view.getCurrentHeroField().contains(e.getSource()) || view.getCurrentHeroHand().contains(e.getSource()))) {
				for(CardButton b:view.getCurrentHeroField()) {
					if(b==e.getSource()) {
						if(attacker==(Minion)b.getCard()) {
							attacker=null;
							break;
						}	
						else {
							attacker=(Minion)b.getCard();
							break;
						}
					}
				}
				for(CardButton b:view.getCurrentHeroHand()) {
					if(b==e.getSource()) {
						if(attacker==(Spell)b.getCard()) {
							attacker=null;
							break;
						}	
						else {
							attacker=(Spell)b.getCard();
							break;
						}
					}	
				}
			}
			else if(view.getOppHeroField().contains(e.getSource()) || view.getCurrentHeroField().contains(e.getSource())) {
				for(CardButton b:view.getOppHeroField()) {
					if(b==e.getSource() && attacker!=null ) {
						attacked=(Minion)b.getCard();
						break;
					}
				}
				for(CardButton b:view.getCurrentHeroField()) {
					if(b==e.getSource() && attacker!=null ) {
						attacked=(Minion)b.getCard();
						break;
					}
				}
				if( attacker instanceof Minion) {
					try {
						game.getCurrentHero().attackWithMinion((Minion)attacker, attacked);
					} catch (CannotAttackException | NotYourTurnException | TauntBypassException
							| InvalidTargetException | NotSummonedException e1) {
						view.handleException(e1.getMessage());
					}
					finally {
						attacker=null;
						attacked=null;
						link();
					}
				}
				else {
					if(attacker instanceof LeechingSpell) {
						try {
							game.getCurrentHero().castSpell((LeechingSpell)attacker, attacked);
						} catch (NotYourTurnException | NotEnoughManaException e1) {
							view.handleException(e1.getMessage());
						}
						finally {
							link();
							attacker=null;
							attacked=null;
						}
					}
					else if(attacker instanceof MinionTargetSpell) {
						try {
							game.getCurrentHero().castSpell((MinionTargetSpell)attacker, attacked);
						} catch (NotYourTurnException | NotEnoughManaException | InvalidTargetException e1) {
							view.handleException(e1.getMessage());
						}
						finally {
							link();
							attacker=null;
							attacked=null;
						}
					}
				}
			}
			else if (e.getSource() instanceof HeroButton) {
				if(e.getSource()==view.getCurHero())
					target=game.getCurrentHero();
				else
					target=game.getOpponent();
				if(attacker instanceof Minion) {
					try {
						game.getCurrentHero().attackWithMinion((Minion)attacker, target);
					} catch (CannotAttackException | NotYourTurnException | TauntBypassException | NotSummonedException
							| InvalidTargetException e1) {
						view.handleException(e1.getMessage());
					}
					finally {
						attacker=null;
						target=null;
						link();
					}

				}
				else if(attacker instanceof HeroTargetSpell) {
					try {
						game.getCurrentHero().castSpell((HeroTargetSpell)attacker,target);
					} catch (NotYourTurnException | NotEnoughManaException e1) {
						view.handleException(e1.getMessage());
					}
					finally {
						link();
						attacker=null;
						target=null;
					}
				}
			}
		}

		if(e.getActionCommand().equals("Play")) {
			for(CardButton b:view.getCurrentHeroHand())
				if(b==e.getSource()) {
					if(b.getCard() instanceof Minion) {
						try {
							game.getCurrentHero().playMinion(((Minion)b.getCard()));
						} catch (NotYourTurnException | NotEnoughManaException | FullFieldException e1) {
							view.handleException(e1.getMessage());
						}
						finally {
							link();
						}
					}
					else {
						if(b.getCard() instanceof AOESpell) {
							try {
								game.getCurrentHero().castSpell((AOESpell)b.getCard(), game.getOpponent().getField());
							} catch (NotYourTurnException | NotEnoughManaException e1) {
								view.handleException(e1.getMessage());
							}
							finally {
								link();
							}
						}
						if(b.getCard() instanceof FieldSpell) {
							try {
								game.getCurrentHero().castSpell((FieldSpell)b.getCard());
							} catch (NotYourTurnException | NotEnoughManaException e1) {
								view.handleException(e1.getMessage());
							}
							finally {
								link();
							}
						}

					}
				}
		}
		if(e.getActionCommand().equals("End Turn")) {
			try {
				game.endTurn();
			} catch (FullHandException | CloneNotSupportedException e1) {
				view.handleException(e1.getMessage());
				if(e1 instanceof FullHandException)
					this.displayCard(((FullHandException)e1).getBurned());
			}
			finally {
				this.link();
			}
		}
		if(e.getActionCommand().equals("Use Hero Power")){
			if(game.getCurrentHero() instanceof Mage) {
				if(attacked!=null) {
					try {
						((Mage)game.getCurrentHero()).useHeroPower(attacked);
					} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
							| FullHandException | FullFieldException | CloneNotSupportedException e1) {
						view.handleException(e1.getMessage());
						if(e1 instanceof FullHandException)
							this.displayCard(((FullHandException)e1).getBurned());
					}
					finally {
						link();
					}
				}
				else if(target!=null) {
					try {
						((Mage)game.getCurrentHero()).useHeroPower(target);
					} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
							| FullHandException | FullFieldException | CloneNotSupportedException e1) {
						view.handleException(e1.getMessage());
						if(e1 instanceof FullHandException)
							this.displayCard(((FullHandException)e1).getBurned());
					}
					finally {
						link();
					}

				}
			}
			else if(game.getCurrentHero() instanceof Priest) {
				if(attacked!=null) {
					try {
						((Priest)game.getCurrentHero()).useHeroPower(attacked);
					} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
							| FullHandException | FullFieldException | CloneNotSupportedException e1) {
						view.handleException(e1.getMessage());
						if(e1 instanceof FullHandException)
							this.displayCard(((FullHandException)e1).getBurned());
					}
					finally {
						link();
					}
				}
				else if(target!=null) {
					try {
						((Priest)game.getCurrentHero()).useHeroPower(target);
					} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
							| FullHandException | FullFieldException | CloneNotSupportedException e1) {
						view.handleException(e1.getMessage());
						if(e1 instanceof FullHandException)
							this.displayCard(((FullHandException)e1).getBurned());
					}
					finally {
						link();
					}

				}
			}
			else {
				try {
					game.getCurrentHero().useHeroPower();
				} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
						| FullHandException | FullFieldException | CloneNotSupportedException e1) {
					view.handleException(e1.getMessage());
					if(e1 instanceof FullHandException)
						this.displayCard(((FullHandException)e1).getBurned());
				}
				finally {
					this.link();
				}
			}
		}	
	}
	
	public void displayCard(Card c) {
		JFrame j =new JFrame();
		j.setDefaultCloseOperation(2);
		j.setVisible(true);
		String s = c.getName() +" "+c.getRarity()+" "+ "ManaCost: " +c.getManaCost();
		if(c instanceof Minion) {
			s+=" Attack: "+((Minion)c).getAttack()+" currentHP: "+((Minion)c).getCurrentHP()+"/"+((Minion)c).getMaxHP()+" ";
			if(((Minion)c).isTaunt())
				s+="Taunt ";
			if(((Minion)c).isDivine())
				s+="Divine ";
			if(!((Minion)c).isSleeping())
				s+="Charge";
		}	
		JLabel b = new JLabel();
		b.setText(s);
		j.setLayout(new BorderLayout());
		j.add(b,BorderLayout.CENTER);
		j.setBounds(500,500,600,200);
	}

	public static void main(String[]args) {
		new Controller();
	}



}
