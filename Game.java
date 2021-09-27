import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game
{
	private Board _board;
	private int _boardSize;
	private boolean _isWon;

	private final int START_CELLS_COUNT = 2;
	private final String[] KEYS = {"w", "s", "a", "d"};

	public Game(int boardSize)
	{
		_boardSize = boardSize;
		_board = new Board(boardSize);
	}

	public void initalized()
	{
		_board.initalized();
		
		for (int i = 0; i < START_CELLS_COUNT; i++)
			_board.createNewCell();
		
		_board.OnReached = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				_isWon = true;
			}
		};
	}

	public void move(String key)
	{
		if (key.equalsIgnoreCase("w"))
		{
			_board.move(MoveDirection.Up);
		}
		else if (key.equalsIgnoreCase("s"))
		{
			_board.move(MoveDirection.Down);
		}
		else if (key.equalsIgnoreCase("a"))
		{
			_board.move(MoveDirection.Left);
		}
		else if (key.equalsIgnoreCase("d"))
		{
			_board.move(MoveDirection.Right);
		}
		else
		{
			throw new IllegalStateException("Key is not valid " + key);
		}
	}

	public void displayRules()
	{
		System.out.println("Controls:");
		System.out.println("   w for move up");
		System.out.println("   s for move down");
		System.out.println("   a for move left");
		System.out.println("   d for move right\n");
		System.out.println("Rules:");
		System.out.println("  You must reach 2048 title");
	}

	public void displayScore()
	{
		System.out.println("\nScore: " + _board.getScore());
	}

	public void renderBoard()
  	{
	  	StringBuilder text = new StringBuilder();
	  	int x = 0, y = 0;

		Cell[][] board = _board.getBoard();

		for (int i = 0; i < (3 + (_boardSize - 1) * 2); i++)
		{
			for (int j = 0; j < (5 + (_boardSize) * 3); j++)
			{
				if (i % 2 == 0)
				{
					if ((j + 1) % 4 == 0) text.append("----");
					else text.append("-");
				}
				else
				{
					if (j % 4 == 0) text.append("|");
					else
					{
						if (j % 2 == 0) text.append(board[y][x++]);
						else text.append(" ");
					}
				}
			}

			if (i % 2 != 0) y++;
			x = 0;

			text.append("\n");
		}

		System.out.println(text);
	}

	public void reset()
	{
		_board.reset();
		_isWon = false;
	}

	public boolean isEnd()
	{
		if (_isWon)
		{
			System.out.println("You Won");
			return true;
		}

		if (!_board.hasMove())
		{
			System.out.println("You Lose");
			return true;
		}

		return false;
	}

	public boolean keyIsValid(String text)
	{
		for (int i = 0; i < KEYS.length; i++)
		{
			if (KEYS[i].equalsIgnoreCase(text))
				return true;
		}

		return false;
	}
}