//Alisikander Ahmed
//2048
//ICS4U1-02
//April/22/2014
//Strelkovska,H. 

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Ex1 extends JFrame implements ActionListener, KeyListener{
	private static final long serialVersionUID = 1L;
	private int row=4, col=4; 
	private JLabel btn[][] = new JLabel[row][col];       // number of buttons - can be changed
	private Container c;
	private JLabel spacer1 = new JLabel("                          ");
	private JLabel spacer2 = new JLabel("                          ");
	private JPanel btnPanel = new JPanel();
	private JTextField scoreBox = new JTextField(100);
	private JTextField highScoreBox = new JTextField(100);
	private String topThree;

	private JPanel southPanel = new JPanel();
	private JButton btn_no = new JButton("New Game");      //can change bbbb   label on action buttons
	private JButton btn_exit = new JButton("Exit");          //label on action buttons
	private JButton btn_highScore = new JButton("HighScore");          //label on action buttons

	private int[][] board = new int[row][col];
	static int count = 0;
	private boolean spawningVar = false;
	private int xRan = 0; 
	private int yRan = 0;
	private int score = 0;
	private boolean loseLeft, loseRight, loseUp, loseDown;
	private boolean playing = false;
	private int spawnChance = 0;
	private String name = "";

	public Ex1() throws IOException   {
		super( "2048 - Join the numbers and get to the 2048 tile!" );
		btnPanel.setLayout(new GridLayout(btn.length,btn[0].length,7,7));
		southPanel.setLayout(new GridLayout(1,3));
		btnPanel.setOpaque(true);

		setBackground(Color.darkGray);
		c = getContentPane();
		c.setLayout( new BorderLayout() );

		btn_no.addActionListener( this );
		southPanel.add(btn_no);
		btn_highScore.addActionListener( this );

		btn_exit.addActionListener( this );
		//btn_no.setFocusable(false);
		//btn_exit.setFocusable(false);

		btnPanel.setFocusable(true);

		southPanel.add(btn_exit);
		southPanel.add(btn_highScore);

		southPanel.add(scoreBox);
		southPanel.add(highScoreBox);

		scoreBox.setEditable(false);
		highScoreBox.setEditable(false);
		highScoreBox.setFocusable(false);

		btn_highScore.setFocusable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("temp.png"));
		BufferedReader br = new BufferedReader(new FileReader("HighScore.txt"));
		highScoreBox.setText("Best: " + br.readLine());
		highScoreBox.setHorizontalAlignment(JTextField.CENTER);
		br.close();
		btnPanel.addKeyListener(this);
		playing = true;

		c.add( southPanel, BorderLayout.SOUTH );
		c.add( btnPanel, BorderLayout.CENTER  );
		c.add( spacer1, BorderLayout.EAST  ); 
		c.add( spacer2, BorderLayout.WEST  ); 
		setSize( 600, 450 );                          //size of the window, can be changed
		setVisible(true);
		startGame();
		displayBoard();
		setLocationRelativeTo(null);

		final ImageIcon icon = new ImageIcon(new URL("http://alicia.mobile9.com/download/as/android/co/me/com.estoty.game2048/icon.100x100-75.jpg"));
		final ImageIcon icon1 = new ImageIcon(new URL("http://img2.wikia.nocookie.net/__cb20090524010838/bestbrute/images/thumb/9/9f/Question_mark_embedded_blue_3d.png/100px-0,129,0,128-Question_mark_embedded_blue_3d.png"));

		name = (String) JOptionPane.showInputDialog(null, "What is your name?", "Welcome", JOptionPane.INFORMATION_MESSAGE,icon1,null,null);
		JOptionPane.showMessageDialog(null, "\tHello " + name + '!' + "\n \t\t\t\t\t\t\t\t\t HOW TO PLAY: Use your arrow keys to move the tiles. \n\tWhen two tiles with the same number touch, they merge into one!","About", JOptionPane.INFORMATION_MESSAGE, icon);
	}

	public void displayBoard(){
		for (int i = 0; i < btn.length; i++ ) {
			for (int j = 0; j < btn[0].length; j++ ) {
				if(board[i][j] == 0){
					btn[i][j].setText(" ");
					btn[i][j].setBackground(new Color(204, 192 , 178));
				}

				else{
					btn[i][j].setText(""+ board[i][j]);
					//btn[i][j].setBackground(new Color( 230 , 173, (Integer.parseInt(btn[i][j].getText())*5)%255));
					if(board[i][j] == 2)
						btn[i][j].setBackground(new Color( 220 , 210, 210));
					else if(board[i][j] == 4)
						btn[i][j].setBackground(new Color( 237 , 200, 150));
					if(board[i][j] == 8)
						btn[i][j].setBackground(new Color( 244 , 178, 117));
					else if(board[i][j] == 16)
						btn[i][j].setBackground(new Color( 247 , 149, 93));
					else if(board[i][j] == 32)
						btn[i][j].setBackground(new Color( 248 , 124, 90));
					else if(board[i][j] == 64)
						btn[i][j].setBackground(new Color( 249 , 94, 50));
					else if(board[i][j] == 128)
						btn[i][j].setBackground(new Color( 238 , 208, 107));
					else if(board[i][j] == 256)
						btn[i][j].setBackground(new Color( 238 , 205, 88));
					else if(board[i][j] == 512)
						btn[i][j].setBackground(new Color( 238 , 201, 67));
					else if(board[i][j] == 1024)
						btn[i][j].setBackground(new Color( 226 , 185, 19));
					else if(board[i][j] == 2048)
						btn[i][j].setBackground(new Color( 235 , 194, 4));

				}
			}
		}
		scoreBox.setText("Score: "+score);
		scoreBox.setHorizontalAlignment(JTextField.CENTER);
	}



	public boolean constantRight(){
		boolean moved = false;

		for (int cols=0; cols<3; cols++){
			for (int rows=0; rows<4; rows++){
				if(board[rows][cols+1] == 0){
					if(board[rows][cols] != 0){
						moved = true;
						board[rows][cols+1] = board[rows][cols];
						board[rows][cols] = 0;
					}
				}
			}
		}
		return moved;
	}


	public boolean constantLeft(){
		boolean moved = false;

		for (int cols=0; cols<3; cols++){
			for (int rows=0; rows<4; rows++){
				if(board[rows][cols] == 0){
					if(board[rows][cols+1] != 0){
						moved = true;
						board[rows][cols] = board[rows][cols+1];
						board[rows][cols+1] = 0;
					}
				}
			}
		}
		return moved;
	}

	public boolean constantUp(){
		boolean moved = false;
		for (int cols=3; cols>=0; cols--){
			for (int rows=3; rows>0; rows--){
				if(board[rows-1][cols] == 0){
					if(board[rows][cols] != 0){
						moved = true;
						board[rows-1][cols] = board[rows][cols];
						board[rows][cols] = 0;
					}
				}
			}
		}
		return moved;
	}

	public boolean constantDown(){
		boolean moved = false;
		for (int cols=3; cols>=0; cols--){
			for (int rows=3; rows>0; rows--){
				if(board[rows][cols] == 0){
					if(board[rows-1][cols] != 0){
						moved = true;
						board[rows][cols] = board[rows-1][cols];
						board[rows-1][cols] = 0;
					}
				}
			}
		}
		return moved;
	}

	public boolean pressedDown(){
		boolean moved = false;
		for (int cols=0; cols<4; cols++){
			for (int rows=3; rows>0; rows--){
				if(board[rows][cols] == board[rows-1][cols] && board[rows][cols] != 0){
					moved = true;
					board[rows][cols] += board[rows-1][cols];
					score += board[rows][cols]*2;
					board[rows-1][cols] = 0;
				}	
			}
		}
		return moved;
	}

	public boolean pressedUp(){
		boolean moved = false;

		for (int cols=3; cols>=0; cols--){
			for (int rows=0; rows<3; rows++){
				if(board[rows+1][cols] == board[rows][cols] && board[rows][cols] != 0){
					moved = true;
					board[rows][cols] += board[rows+1][cols];
					score += board[rows+1][cols]*2;
					board[rows+1][cols] = 0;
				}	
			}
		}

		return moved;
	}

	public boolean pressedLeft(){
		boolean moved = false;
		for (int cols=0; cols<3; cols++){
			for (int rows=0; rows<4; rows++){
				if(board[rows][cols] == board[rows][cols+1]  && board[rows][cols] != 0 ){
					moved = true;
					board[rows][cols] += board[rows][cols+1];
					score += board[rows][cols+1]*2;
					board[rows][cols+1] = 0;
				}	
			}
		}
		return moved;
	}

	public boolean pressedRight(){
		boolean moved = false;

		for (int cols=3; cols>0; cols--){
			for (int rows=0; rows<4; rows++){
				if(board[rows][cols] == board[rows][cols-1] && board[rows][cols]!=0){
					moved = true;
					board[rows][cols] += board[rows][cols-1];
					score += board[rows][cols-1] *2;
					board[rows][cols-1] = 0;
				}	
			}
		}
		return moved;
	}

	public void spawnNew(){
		spawningVar = true; 
		spawnChance = (int) (Math.random()*5);
		while(spawningVar){
			xRan = (int) (Math.random()*4);
			yRan = (int) (Math.random()*4);

			if (board[xRan][yRan] == 0){
				if(spawnChance==0 || spawnChance == 3){
					board[xRan][yRan] = 4;
					spawningVar =false;
				}

				else{
					board[xRan][yRan] = 2;
					spawningVar = false;
				}
			}
		}
	}


	public void writeFile() {  
		try {
			BufferedWriter bwTemp = new BufferedWriter(new FileWriter("AllScores.txt", true));
			BufferedReader brTemp = new BufferedReader(new FileReader("AllScores.txt"));


			BufferedWriter bw = new BufferedWriter(new FileWriter("HighScore.txt"));

			ArrayList <Integer> topScores = new ArrayList <Integer> ();

			String line;
			bwTemp.write(""+score);
			bwTemp.newLine();

			while ((line = brTemp.readLine()) != null) {
				topScores.add(Integer.parseInt(line));
			}

			Collections.sort(topScores);
			//myDisplay(topScores);
			int highest = topScores.size();

			if(score>topScores.get(highest-1)){
				bw.write(""+score);
			}
			else
				bw.write(""+topScores.get(highest-1));

			topThree = "1. "+ topScores.get(highest-1) + "\n2. "+topScores.get(highest-2) +"\n3. "+topScores.get(highest-3) ;
			bwTemp.close();
			brTemp.close();
			bw.close();              

		} catch (IOException ioe){  
			ioe.printStackTrace();  
		}  
	}  

	public static void myDisplay(ArrayList<Integer> topScores){
		System.out.println("There are " + topScores.size() + " highscores!");
		System.out.println("Top Scores Are: ");
		for(int i =0; i<topScores.size(); i++){
			System.out.print(topScores.get(i) + "    ");
		}
		System.out.println("\n\n");
	}	

	public void checkWinLose(){
		for (int cols=0; cols<4; cols++){
			for (int rows=0; rows<4; rows++)
				if(board[rows][cols] == 2048)
					youWin();
		}

		loseCheck();
	}

	public void youWin(){
		writeFile();
		
		playing = false;
		for (int cols=0; cols<4; cols++)
			for (int rows=0; rows<4; rows++)
				board[rows][cols] = 0;

		for (int i = 0; i < btn.length; i++ ) {
			for (int j = 0; j < btn[0].length; j++ ) {
				btn[i][j].setText(" ");
				btn[i][j].setBackground(new Color( 235 , 194, 4));
			}
		}


		btn[1][0].setText("Y");
		btn[1][1].setText("O");
		btn[1][2].setText("U");

		btn[2][1].setText("W");
		btn[2][2].setText("I");
		btn[2][3].setText("N");

	}

	public void loseCheck(){
		if(loseUp == true && loseRight == true && loseLeft == true && loseDown == true)
			youLose();
	}

	public void youLose(){
		playing = false;
		writeFile();
		for (int cols=0; cols<4; cols++)
			for (int rows=0; rows<4; rows++)
				board[rows][cols] = 0;


		for (int i = 0; i < btn.length; i++ ) {
			for (int j = 0; j < btn[0].length; j++ ) {
				btn[i][j].setText(" ");
				btn[i][j].setBackground(new Color( 248 , 124, 90));
			}
		}


		btn[1][0].setText("G");
		btn[1][1].setText("A");
		btn[1][2].setText("M");
		btn[1][3].setText("E");

		btn[2][0].setText("O");
		btn[2][1].setText("V");
		btn[2][2].setText("E");
		btn[2][3].setText("R");

	}

	public void newGameSpawn(){
		xRan = (int) (Math.random()*4);
		yRan = (int) (Math.random()*4);

		board[xRan][yRan] = 2;

		xRan = (int) (Math.random()*4);
		yRan = (int) (Math.random()*4);

		board[xRan][yRan] = 2;
	}

	public void newGame(){
		for (int cols=0; cols<4; cols++)
			for (int rows=0; rows<4; rows++)
				board[rows][cols] = 0;
		score=0;
		playing = true;
		loseUp = false;
		loseRight = false;
		loseLeft = false;
		loseDown = false;
		newGameSpawn();
	}

	public void startGame(){
		Font myFont = new Font("Impact", Font.PLAIN, 40) ;  

		for (int i = 0; i < btn.length; i++ ) {
			for (int j = 0; j < btn[0].length; j++ ) {
				btn[i][j] = new JLabel("",JLabel.CENTER);
				btn[i][j].setOpaque(true);
				btn[i][j].setFont(myFont);
				btn[i][j].setForeground(Color.WHITE);
				btnPanel.add(btn[i][j]);
			}
		}

		board[3][3] = 2;
		board[1][0] = 4;

		board[0][0] = 4;
		board[2][0] = 2;
		board[3][0] = 2;

	}


	public void actionPerformed( ActionEvent e ){ 
		if (e.getSource()==btn_no){
			newGame();
			displayBoard();
			btn_no.setFocusable(false);
			btn_exit.setFocusable(false);
			scoreBox.setFocusable(false);
			highScoreBox.setEditable(false);
		}

		else if (e.getSource()==btn_exit)
			System.exit(0);   
		else if (e.getSource()==btn_highScore){
			writeFile();
			JOptionPane.showMessageDialog(null, "Top Three Scores: \n\n"+topThree,"HighScores", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public static void main( String args[] ) throws IOException {
		Ex1 app = new Ex1();
		app.setLocationRelativeTo(null);  
	}

	public void keyPressed(KeyEvent e){
		int code=e.getKeyCode();
		if (code == KeyEvent.VK_UP){
			if(playing){
				boolean moved = false;

				for(int i=0; i<3; i++){
					if(constantUp())
						moved = true;
				}

				if(pressedUp())
					moved = true;

				if(constantUp())
					moved = true;

				if(moved){
					spawnNew();
					loseUp = false;
				}

				else 
					loseUp = true;

				displayBoard();
				checkWinLose();
			}
		}

		if (code == KeyEvent.VK_DOWN){

			if(playing){

				boolean moved = false;

				for(int i=0; i<3; i++){
					if(constantDown())
						moved = true;
				}

				if(pressedDown())
					moved = true;

				if(constantDown())
					moved = true;

				if(moved){
					spawnNew();
					loseDown = false;
				}

				else 
					loseDown = true;

				displayBoard();
				checkWinLose();
			}	

		}

		if (code == KeyEvent.VK_RIGHT){
			if(playing){

				boolean moved = false;

				for(int i=0; i<3; i++){
					if(constantRight())
						moved = true;
				}

				if (pressedRight())
					moved = true;

				if(constantRight())
					moved = true;

				if(moved){
					spawnNew();
					loseRight = false;
				}

				else 
					loseRight = true;

				displayBoard();
				checkWinLose();

			}

		}

		if (code == KeyEvent.VK_LEFT){
			if(playing){

				boolean moved = false;

				for(int i=0; i<3; i++){
					if (constantLeft())
						moved = true;
				}

				if(pressedLeft())
					moved = true;

				if (constantLeft())
					moved = true;

				if(moved){
					spawnNew();
					loseLeft = false;
				}
				else
					loseLeft = true;

				displayBoard();
				checkWinLose();
			}
		}
	}

	public void keyReleased(KeyEvent e) {

	}

	public void keyTyped(KeyEvent e) {	
	} 

}

