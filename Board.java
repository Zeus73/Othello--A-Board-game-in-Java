package Othello;

import java.util.ArrayList;

public class Board {
	private char[][] board;
	char player1Char;
	char player2Char;
	public static final int PLAYER1WON = 1;
	public static final int PLAYER2WON = 2;
	public static final int NOT_FINISHED = 0;
	public static final int DRAW = 3;

	public Board(char player1Char, char player2Char) {
		board = new char[8][8];
		for (int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				board[i][j] = ' ';
			}
		}
		this.player1Char = player1Char;
		this.player2Char = player2Char;

		board[3][3]=board[4][4]='1';
		board[4][3]=board[3][4]='2';

	}



	public boolean makeAMove(int x,int y,char symbol,GameManager1 gm){
		

		char oppo;
		if(symbol=='1')oppo='2';
		else oppo='1';
		if(board[x][y]!=' ')
			return false;

		boolean moveOccured=false;

		int a[]={1,1,1,0,0,-1,-1,-1};
		int b[]={1,0,-1,1,-1,1,0,-1};
		for(int k=0;k<8;++k){


			int i,j;
			i=x+a[k];
			j=y+b[k];

			if(i>=0&&i<8&&j>=0&&j<8&&board[i][j]==oppo){
				int p,q;
				for(p=i,q=j;p>=0&&p<8&&q>=0&&q<8;p+=a[k],q+=b[k]){
					if(board[p][q]==' '){
						p=-1;
						q=-1;
						break;
					}
					else if(board[p][q]==symbol){
						moveOccured=true;
						break;
					}
				}
				if(p>=0&&p<8&&q>=0&&q<8){
					for(int startX=i,startY=j;startX!=p||startY!=q;startX+=a[k],startY+=b[k]){
						board[startX][startY]=symbol;
						gm.changebuttoncolors(startX,startY,symbol);
					}
				}

			}
			
		}
		
		if(moveOccured)
			board[x][y]=symbol;
	
		
		return moveOccured;

	}
	

public boolean checkAMove(int x,int y,char symbol){

		char oppo;
		if(symbol=='1')oppo='2';
		else oppo='1';
		if(board[x][y]!=' ')
			return false;

		

		int a[]={1,1,1,0,0,-1,-1,-1};
		int b[]={1,0,-1,1,-1,1,0,-1};
		for(int k=0;k<8;++k){


			int i,j;
			i=x+a[k];
			j=y+b[k];

			if(i>=0&&i<8&&j>=0&&j<8&&board[i][j]==oppo){
				int p,q;
				for(p=i,q=j;p>=0&&p<8&&q>=0&&q<8;p+=a[k],q+=b[k]){
					if(board[p][q]==' '){
						break;
					}
					else if(board[p][q]==symbol){
						return true;
					}
				}
				
			}
			
		}
		
		
		return false;

	}
	

	
	public ArrayList<Integer> allValidMoves(char symb){
		ArrayList<Integer> ans=new ArrayList<>();
		for(int i=0;i<8;++i){
			for(int j=0;j<8;++j){
				if(checkAMove(i, j, symb))
					ans.add(i*8+j);
			}
		}
		return ans;
	}



	public void printBoard() {
		for(int xiter=0;xiter<8;++xiter){
			for(int yiter=0;yiter<8;++yiter){
				System.out.print(board[xiter][yiter]+"|");
			}
			System.out.println();
		}
		
	}



	public Pair<Integer, Integer> getBothPlayerCount() {
		Pair<Integer, Integer> counts=new Pair<>(0,0);
		for(int xiter=0;xiter<8;++xiter){
			for(int yiter=0;yiter<8;++yiter){
				if(board[xiter][yiter]=='1')
					counts.data1++;
				else if(board[xiter][yiter]=='2')
					counts.data2++;
			}
			
		}
		return counts;
	}



	public boolean allBoxesFilled() {
		for(int xiter=0;xiter<8;++xiter){
			for(int yiter=0;yiter<8;++yiter){
				if(board[xiter][yiter]==' ')
					return false;
			}
		}
		return true;
	}
	
	
	
}
