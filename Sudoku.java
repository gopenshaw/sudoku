import java.util.*;

public class Sudoku
{
	int[][] board = new int[9][9];

	public static void main(String[] args)
	{
		Sudoku solver = new Sudoku();
		solver.begin();
	}

	void begin()
	{
		inputBoard();
		solve(0, 0);
		System.out.println("no solution found.");
	}

	void printBoard()
	{
		System.out.println("- - - - - - - - - - - - - - - -");
		for(int r = 0; r < 9; r++)
		{
			for(int c = 0; c < 9; c++)
			{
				if(c % 3 == 0) System.out.print("|");
				System.out.print(" " + board[r][c] + " ");
				
			}
			System.out.println("|");
			if(r == 2 || r == 5) System.out.print("| - - - - - - - - - - - - - - |\n");		
		}
		System.out.println("- - - - - - - - - - - - - - - - ");
	}

	void inputBoard()
	{
		Scanner input = new Scanner(System.in);
		System.out.println("Enter all values for board:");
		for(int r = 0; r < 9; r++)
		{
			for(int c = 0; c < 9; c++)
			{
				board[r][c] = input.nextInt();
			}
		}
	}

	boolean inRow(int n, int r)
	{
		for(int c = 0; c < 9; c++)
		{
			if(board[r][c] == n) return true;
		}
		return false;
	}

	boolean inCol(int n, int c)
	{
		for(int r = 0; r < 9; r++)
		{
			if(board[r][c] == n) return true;
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
			if(board[rZone * 3 + i / 3][cZone * 3 + i % 3] == n) return true;
		}
		return false;
	}

    void solve(int r, int c)
    {
	//System.out.println(r + " " + c);
		if(board[r][c] == 0)
		{
			for(int i = 1; i < 10; i++)
			{
				//System.out.println("Checking " + i);
				//System.out.print("inRow is " + inRow(i, r));
				//System.out.println(" inCol is " + inCol(i, c));
				//System.out.println(" inSqr is " + inSqr(i, r, c));
				if (!inRow(i, r) && !inCol(i, c) && !inSqr(i, r, c))
				{
					board[r][c] = i;
					if(r == 8 && c == 8)
					{
						printBoard();
						System.exit(0);
					}
					else if(c == 8) solve(r + 1, 0);
					else solve(r, c + 1);
				}
			}
			board[r][c] = 0;
		}
		else if(r == 8 && c == 8)
		{
			printBoard();
			System.exit(0);
		}
		else if(c == 8) 
		{
			solve(r + 1, 0);
		}
		else 
		{
			solve(r, c + 1);
		}
    }
}