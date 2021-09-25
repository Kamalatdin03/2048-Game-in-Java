import java.util.Scanner;

class Main 
{
	public static void main(String[] args) 
	{	
		var board = new Board(4);

		board.initalized();

		board.createNewCell(3, 0);
		board.createNewCell(3, 1);
		board.createNewCell(3, 2);
		board.createNewCell(3, 3);

		renderBoard(board.getBoard());

		board.move(MoveDirection.Top);
		renderBoard(board.getBoard());	

		board.move(MoveDirection.Left);
		renderBoard(board.getBoard());

		board.move(MoveDirection.Right);
		renderBoard(board.getBoard());		
	}

	public static void renderBoard(Cell[][] board)
	{
		String text = "";
		int boardSize = board.length;

		for (int i = 0; i < boardSize; i++)
		{
			for (int j = 0; j < boardSize; j++)
			{
				text += board[i][j].toString() + " ";
			}

			text += "\n";
		}

		System.out.println(text + "\n");
	}
}