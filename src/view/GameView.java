package view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameView extends JFrame{
	private JPanel p1;
	private JPanel p2;
	private JPanel p3;
	private JPanel currentHand;
	private JPanel currentHero;
	private JPanel currentField;
	private JPanel opponentHand;
	private JPanel opponentHero;
	private JPanel opponentField;
	private ArrayList<CardButton> currentHeroField;
	private ArrayList<CardButton> oppHeroField;
	private ArrayList<CardButton> currentHeroHand;
	private HeroButton oppHero;
	private HeroButton curHero;
	public GameView() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(0,0,screenSize.width,screenSize.height);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	    p1 = new ChoosingHeroPanel();
	    p2 = new ChoosingHeroPanel();
	    p3 = new JPanel();
	    p3.setLayout(new BorderLayout());
	    JButton b = new JButton();
	    b.setText("Start game");
	    p3.add(b,BorderLayout.CENTER);
	    p1.setPreferredSize(new Dimension(screenSize.width,screenSize.height/3));
	    p2.setPreferredSize(new Dimension(screenSize.width,screenSize.height/3));
		p3.setPreferredSize(new Dimension(screenSize.width,screenSize.height/3));
		this.add(p1,BorderLayout.NORTH);
		this.add(p3,BorderLayout.CENTER);
		this.add(p2, BorderLayout.SOUTH);
		this.revalidate();
		this.repaint();
	}
	
	public void onStartingGame() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		JLabel background = new JLabel(new ImageIcon("images/Background.jpg"));
		this.add(background);
		currentHeroField = new ArrayList<CardButton>();
		currentHeroHand = new ArrayList<CardButton>();
		oppHeroField = new ArrayList<CardButton>();
		currentHand = new JPanel();
		currentHand.setOpaque(false);
		currentHero = new JPanel();
		currentHero.setOpaque(false);
		currentField= new JPanel();
		currentField.setOpaque(false);
		opponentHand = new JPanel();
		opponentHand.setOpaque(false);
		opponentHero = new JPanel();
		opponentHero.setOpaque(false);
		opponentField = new JPanel();
		opponentField.setOpaque(false);
		currentHand.setPreferredSize(new Dimension(screenSize.width,screenSize.height/6));
		currentHero.setPreferredSize(new Dimension(screenSize.width,screenSize.height/6));
		currentField.setPreferredSize(new Dimension(screenSize.width,screenSize.height/6));
		opponentHand.setPreferredSize(new Dimension(screenSize.width,screenSize.height/6));
		opponentHero.setPreferredSize(new Dimension(screenSize.width,screenSize.height/6));
		opponentField.setPreferredSize(new Dimension(screenSize.width,screenSize.height/6));
		background.setLayout(new GridLayout(6,1));	
		background.add(opponentHand);
		background.add(opponentHero);
		background.add(opponentField);
		background.add(currentField);
		background.add(currentHero);
		background.add(currentHand);
		this.revalidate();
		this.repaint();
		
	}
	
	public void onGameOver(int n) {
		JFrame j = new JFrame();
		j.setVisible(true);
		j.setDefaultCloseOperation(EXIT_ON_CLOSE);
		j.setBounds(0,0,640,400);
		this.dispose();
		JLabel background =new JLabel();
		if(n==1)
			 background = new JLabel(new ImageIcon("images/P1.jpeg"));
		else 
			 background = new JLabel(new ImageIcon("images/P2.jpeg"));
		
		j.add(background);
		j.revalidate();
		j.repaint();
	}
	
	public void handleException(String s) {
		JFrame j =new JFrame();
		j.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		j.setVisible(true);
		JLabel b = new JLabel();
		j.setLayout(new BorderLayout());
		b.setText(s);
		j.add(b,BorderLayout.CENTER);
		j.setBounds(500,500,400,200);
	}
	
	public JPanel getOpponentField() {
		return opponentField;
	}

	public void setOpponentField(JPanel opponentField) {
		this.opponentField = opponentField;
	}

	public JPanel getOpponentHero() {
		return opponentHero;
	}

	public void setOpponentHero(JPanel opponentHero) {
		this.opponentHero = opponentHero;
	}

	public JPanel getOpponentHand() {
		return opponentHand;
	}

	public void setOpponentHand(JPanel opponentHand) {
		this.opponentHand = opponentHand;
	}

	public JPanel getCurrentField() {
		return currentField;
	}

	public void setCurrentField(JPanel currentField) {
		this.currentField = currentField;
	}

	public JPanel getCurrentHero() {
		return currentHero;
	}

	public void setCurrentHero(JPanel currentHero) {
		this.currentHero = currentHero;
	}

	public JPanel getCurrentHand() {
		return currentHand;
	}

	public void setCurrentHand(JPanel currentHand) {
		this.currentHand = currentHand;
	}
	public JPanel getP1() {
		return p1;
	}
	public void setP1(JPanel p1) {
		this.p1 = p1;
	}
	public JPanel getP2() {
		return p2;
	}
	public void setP2(JPanel p2) {
		this.p2 = p2;
	}
	public JPanel getP3() {
		return p3;
	}
	public void setP3(JPanel p3) {
		this.p3 = p3;
	}

	public ArrayList<CardButton> getCurrentHeroField() {
		return currentHeroField;
	}

	public ArrayList<CardButton> getOppHeroField() {
		return oppHeroField;
	}

	public ArrayList<CardButton> getCurrentHeroHand() {
		return currentHeroHand;
	}

	public HeroButton getOppHero() {
		return oppHero;
	}

	public void setOppHero(HeroButton oppHero) {
		this.oppHero = oppHero;
	}

	public HeroButton getCurHero() {
		return curHero;
	}

	public void setCurHero(HeroButton curHero) {
		this.curHero = curHero;
	}
	 

}
