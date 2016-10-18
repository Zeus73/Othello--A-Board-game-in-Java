package Othello;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class GameManager1 extends JFrame implements ActionListener {
	Board b;
	Player p1;
	Player p2;
	int wait=0;
	static Scanner s = new Scanner(System.in);
	private static final long serialVersionUID = 1L;
	JPanel[] row = new JPanel[9];
	JButton[] button = new JButton[64];
	String buttonString = "";
	int[] dimW = {300,45,100,90};
	int[] dimH = {35, 40};
	Dimension displayDimension = new Dimension(dimW[0], dimH[0]);
	Dimension regularDimension = new Dimension(dimW[1], dimH[1]);

	double[] temporary = {0, 0};
	JTextArea display = new JTextArea(1,20);
	Font font = new Font("Times new Roman", Font.BOLD, 15);

	public GameManager1()  {

		super("Othello");

		p1 = Player.takePlayerInput(1,'1');
		do {
			p2 = Player.takePlayerInput(2,'2');
		} while (p1.symbol == p2.symbol);

		b  = new Board(p1.symbol, p2.symbol);

		setDesign();
		setSize(550, 550);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		GridLayout grid = new GridLayout(9,8);
		setLayout(grid);

		FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);
		FlowLayout f2 = new FlowLayout(FlowLayout.CENTER,1,1);
		for(int i = 0; i < 9; i++) {
			row[i] = new JPanel();
		}

		row[0].setLayout(f1);

		for(int i = 1; i < 9; i++)
			row[i].setLayout(f2);

		for(int i = 0; i < 64; i++) {
			button[i] = new JButton();
			button[i].setText(buttonString);
			button[i].setFont(font);
			button[i].addActionListener(this);
		}


		display.setFont(font);
		display.setEditable(false);
		display.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		display.setPreferredSize(displayDimension);
		for(int i = 0; i < 64; i++)
			button[i].setPreferredSize(regularDimension);

		row[0].add(display);
		add(row[0]);

		for(int i = 0; i < 8; i++)
			row[1].add(button[i]);
		add(row[1]);

		for(int i = 8; i < 16; i++)
			row[2].add(button[i]);
		add(row[2]);

		for(int i = 16; i < 24; i++)
			row[3].add(button[i]);
		add(row[3]);

		for(int i = 24; i < 32; i++)
			row[4].add(button[i]);
		add(row[4]);

		for(int i = 32; i < 40; i++)
			row[5].add(button[i]);
		add(row[5]);

		for(int i = 40; i < 48; i++)
			row[6].add(button[i]);
		add(row[6]);

		for(int i = 48; i < 56; i++)
			row[7].add(button[i]);
		add(row[7]);

		for(int i = 56; i < 64; i++)
			row[8].add(button[i]);
		add(row[8]);

		setVisible(true);
		button[27].setBackground(Color.BLACK);
		button[36].setBackground(Color.BLACK);
		
		button[35].setBackground(Color.YELLOW);
		button[28].setBackground(Color.YELLOW);

		defBorder=button[0].getBorder();


		play();
	}



	Player currentPlayer;

	public final void setDesign() {
		try {
			UIManager.setLookAndFeel(
					"com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch(Exception e) {   
		}
	}
	boolean player1Turn = false;
	boolean permission = true;
	ArrayList<Integer> validMovesList=null;
	Border defBorder=null;
	String showNow="";


	private void play(){
		if(permission){

			permission=false;
			

			player1Turn = !player1Turn;
			if (player1Turn)
				currentPlayer = p1;
			else
				currentPlayer = p2;

			validMovesList=b.allValidMoves(currentPlayer.symbol);
			System.out.println(currentPlayer.name + " has "+ validMovesList.size()+" moves avail");

			if(validMovesList.size()>0){
				showNow+=currentPlayer.name + "'s turn";
				display.setText(showNow);
				for(int bb:validMovesList){
					if(currentPlayer==p1)
						button[bb].setBorder(new LineBorder(Color.BLACK,2));
					else
						button[bb].setBorder(new LineBorder(Color.YELLOW,2));
				}
				showNow="";
				if(wait==1)
					wait=0;
			}

			else{
				
				if(wait==0||wait==1){
					showNow=currentPlayer.name+" has no move available\n";
					wait++;
					permission=true;
				}
				
				if(wait==2||b.allBoxesFilled()){

					String res="Game ends now!\n";
					Pair<Integer, Integer> squareCount=b.getBothPlayerCount();
					if (squareCount.data1>squareCount.data2) {
						res+=(p1.name + " won");
					} else if (squareCount.data1<squareCount.data2) {
						res+=(p2.name + " won");
					} else if (squareCount.data1==squareCount.data2) {
						res+=("Match Draw");
					}

					display.setText(res);

//					for(int i=0;i<64;++i)
//						button[i].setEnabled(false);
				}
				
				
			}

		}		// end of permission


	}			//end of play




	public static void main(String[] args) {
		GameManager1 gm = new GameManager1();
	}


	@Override
	public void actionPerformed(ActionEvent ae) {

		int iter=0;
		for(iter=0;iter<64;++iter){

			if(ae.getSource()==button[iter])
				break;
			if(iter==63)
				iter=-1;

		}
		int x=iter/8;
		int y=iter%8;


		boolean done = b.makeAMove(x,y, currentPlayer.symbol,this);

		if(done){
			if(currentPlayer==p1)
				button[x*8+y].setBackground(Color.BLACK);
			else
				button[x*8+y].setBackground(Color.YELLOW);

			for(int bb:validMovesList){
				button[bb].setBorder(defBorder);
			}


			permission=true;
		}
		else{
			display.setText("Invalid move! Try Again\n "+currentPlayer.name+"'s turn");
		}

		play();

	}

	public void changebuttoncolors(int xaxis,int yaxis,char symb){

		if(symb=='1')
			button[xaxis*8+yaxis].setBackground(Color.BLACK);
		else if(symb=='2')
			button[xaxis*8+yaxis].setBackground(Color.YELLOW);

	}


}
