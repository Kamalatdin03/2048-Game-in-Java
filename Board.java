import java.util.Random; 
import java.util.ArrayList;

public class Board
{
	private int _score;
	private int _boardSize;
	private Random _random;
	private Cell[][] _board;

	public Board(int boardSize)
	{
		_random = new Random();
		_boardSize = boardSize;
	}

	public void initalized()
	{
		_board = new Cell[_boardSize][_boardSize];

		for (int i = 0; i < _boardSize; i++)
		{
			for (int j = 0; j < _boardSize; j++)
			{
				_board[i][j] = new Cell(j, i, 0);
			}
		}
	}

	public void createNewCell()
	{
		Cell[] emptyCells = getEmptyCells();

		if (emptyCells.length == 0)
			throw new IllegalStateException("Empty cell not finded");

		Cell cell = emptyCells[_random.nextInt(_boardSize)];

		int value = _random.nextDouble() > .1 ? 1 : 2;

		_board[cell.getY()][cell.getX()].setValue(value);
	}

	public void move(MoveDirection direction)
	{
		boolean isSideDirection = direction == MoveDirection.Left || direction == MoveDirection.Right;

		int yStart = isSideDirection ? 0 : (direction == MoveDirection.Top ? (1) : (_boardSize - 2));

		int xStart = !isSideDirection ? 0 : (direction == MoveDirection.Left ? 0 : (_boardSize - 1));

		int yDimesion = !isSideDirection ? direction.getValue() : -1;
		int xDimesion = isSideDirection ? -direction.getValue() : 1;

		for (int i = yStart; i >= 0 && i < _boardSize; i -= yDimesion)
		{
			for (int j = xStart; j >= 0 && j < _boardSize; j += xDimesion)
			{
				Cell cell = isSideDirection ? _board[j][i] : _board[i][j], otherCell;

				if (cell.isEmpty()) continue;

				otherCell = findMergeCell(cell, direction);
				if (otherCell != null)
				{
					cell.merge(otherCell);
					_score += otherCell.getPoint();
				}

				otherCell = findEmptyCell(cell, direction);
				if (otherCell != null) cell.swap(otherCell);
			}
		}

		createNewCell();
	}

	public Cell[] getEmptyCells()
	{
		ArrayList<Cell> emptyCells = new ArrayList<Cell>();

		for (int i = 0; i < _boardSize; i++)
		{
			for (int j = 0; j < _boardSize; j++)
			{
				if (_board[i][j].isEmpty())
					emptyCells.add(_board[i][j]);
			}
		}

		Cell[] result = new Cell[emptyCells.size()];
		emptyCells.toArray(result);

		return result;
	}

	public Cell[][] getBoard()
	{
		return _board;
	}

	public int getScore()
	{
		return _score;
	}

	public void reset()
	{
		_score = 0;

		for (int i = 0; i < _boardSize; i++)
		{
			for (int j = 0; j < _boardSize; j++)
			{
				_board[i][j].reset();
			}
		}
	}

	private Cell findEmptyCell(Cell cell, MoveDirection direction)
	{
		int startP = (direction == MoveDirection.Top || direction == MoveDirection.Bottom) ? cell.getY() : cell.getX();

		boolean isSideDirection = direction == MoveDirection.Left || direction == MoveDirection.Right;

		Cell emptyCell = null;

		for (int i = startP + direction.getValue(); i >= 0 && i < _boardSize; i += direction.getValue())
		{
			if (isSideDirection)
			{
				if (_board[cell.getY()][i].isEmpty())
				{
					emptyCell = _board[cell.getY()][i];
				}
				else break;
			}
			else
			{
				if (_board[i][cell.getX()].isEmpty())
				{
					emptyCell = _board[i][cell.getX()];
				}
				else break;
			}
		}

		return emptyCell;
	}

	private Cell findMergeCell(Cell cell, MoveDirection direction)
	{
		int startP = (direction == MoveDirection.Top || direction == MoveDirection.Bottom) ? cell.getY() : cell.getX();

		boolean isSideDirection = direction == MoveDirection.Left || direction == MoveDirection.Right;

		Cell mergeCell = null;

		for (int i = startP + direction.getValue(); i >= 0 && i < _boardSize; i += direction.getValue())
		{
			if (isSideDirection)
			{
				if (_board[cell.getY()][i].isEmpty()) continue;
				else
				{
					if (cell.getPoint() == _board[cell.getY()][i].getPoint())
						mergeCell = _board[cell.getY()][i];

					break;
				}
			}
			else
			{
				if (_board[i][cell.getX()].isEmpty()) continue;
				else 
				{
					if (cell.getPoint() == _board[i][cell.getX()].getPoint())
						mergeCell = _board[i][cell.getX()];

					break;
				}
			}
		}

		return mergeCell;
	}
}