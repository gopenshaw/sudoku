import javax.swing.*;
import javax.swing.border.*;
import java.awt.color.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.*;


public class SudokuBoard extends JFrame
{
	protected JTextField[][] values = new JTextField[9][9];
	protected int[][] intBoard = new int[9][9];
	protected boolean solved = false;
	
	public static void main(String[] args)
	{
		SudokuBoard board = new SudokuBoard();
		board.setSize(300, 400);
		board.setLayout(new GridLayout(4, 3));
		board.init();
		board.setTitle("Sudoku Solver");
		board.setDefaultCloseOperation(EXIT_ON_CLOSE);
		board.setLocationRelativeTo(null);
		board.setVisible(true);
	}
	
	public void init()
	{
		for(int i = 0; i < 9; i++) // create panels, each with 9 text boxes
		{
			JPanel p = new JPanel();
			p.setLayout(new GridLayout(3, 3));
			p.setSize(50, 50);
			p.setBorder(new LineBorder(Color.BLACK));
			for(int j = 0; j < 9; j++)
			{
				// this numbering ensures that the text fields line up with the 2-d array
				values[j/3 + i/3*3][j%3 + i%3*3] = new JTextField();
				p.add(values[j/3 + i/3*3][j%3 + i%3*3]);
			}
			add(p);
		}
		JButton solveButton = new JButton("Solve");
		solveButton.addActionListener(new CallSolve());
		add(solveButton);
		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener(new Clear());
		add(clearButton);
	}
	
	public void solve()
	{
		solved = false;
		//copy all text values to intBoard
		for(int i = 0; i < 9; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				if(values[i][j].getText().equals("")) intBoard[i][j] = 0;
				else intBoard[i][j] = new Integer(values[i][j].getText());
			}
		}
		recSolve(0,0); // this call starts the solving process
		updateBoard();
	}
	
    void recSolve(int r, int c)
    {
		if(solved) return;
		else if(intBoard[r][c] == 0)
		{
			for(int i = 1; i < 10; i++) // test each number from 1 to 9
			{
				if (!inRow(i, r) && !inCol(i, c) && !inSqr(i, r, c))
				{
					intBoard[r][c] = i;
					if(r == 8 && c == 8)
					{
						System.out.println("solved");
						solved = true;
						return;
					}
					else if(c == 8) recSolve(r + 1, 0);
					else recSolve(r, c + 1);
				}
			}
			if(!solved) intBoard[r][c] = 0;
		}
		else if(r == 8 && c == 8)
		{
			System.out.println("Solved.");
			solved = true;
			return;
		}
		else if(c == 8) 
		{
			recSolve(r + 1, 0);
		}
		else 
		{
			recSolve(r, c + 1);
		}
    }
	
	boolean inRow(int n, int r)
	{
		for(int c = 0; c < 9; c++)
		{
			if(intBoard[r][c] == n) return true;
		}
		return false;
	}

	boolean inCol(int n, int c)
	{
		for(int r = 0; r < 9; r++)
		{
			if(intBoard[r][c] == n) return true;
		}
		return false;
	}

	boolean inSqr(int n, int r, int c)
	{
		int rZone = r / 3;
		int cZone = c / 3;
		//System.out.println("rZone is " + rZone + " and cZone is " + cZone);
		for(int i = 0; i < 9; i++)
		{
			//System.out.println("Checking " + (rZone * 3 + i / 3) + " " + (cZone * 3 + i % 3));
			if(intBoard[rZone * 3 + i / 3][cZone * 3 + i % 3] == n) return true;
		}
		return false;
	}	
	
	void updateBoard()
	{
		for(int i = 0; i < 9; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				if(intBoard[i][j] == 0) values[i][j].setText("");
				else values[i][j].setText(new Integer(intBoard[i][j]).toString());
			}
		}
	}	
	
	class CallSolve implements ActionListener
	{
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			solve();
		}
		
	}
	
	class Clear implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			for(int i = 0; i < 9; i++)
			{
				for(int j = 0; j < 9; j++)
				{
					intBoard[i][j] = 0;
				}
			}
			updateBoard();
		}
	}
	
	void printBoard()
	{
		System.out.println("- - - - - - - - - - - - - - - -");
		for(int r = 0; r < 9; r++)
		{
			for(int c = 0; c < 9; c++)
			{
				if(c % 3 == 0) System.out.print("|");
				System.out.print(" " + intBoard[r][c] + " ");
				
			}
			System.out.println("|");
			if(r == 2 || r == 5) System.out.print("| - - - - - - - - - - - - - - |\n");		
		}
		System.out.println("- - - - - - - - - - - - - - - - ");
	}
}