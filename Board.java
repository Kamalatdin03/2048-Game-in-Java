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

		Cell cell = emptyCells[_random.nextInt(_boardSize - 1)];

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

	public boolean hasMove()
	{
		for (int i = 0; i < _boardSize; i++)
		{
			for (int j = 0; j < _boardSize; j++)
			{
				if (hasEqualNeighbor(_board[i][j]))
					return true;
			}
		}

		return false;
	}

	private boolean hasEqualNeighbor(Cell cell)
	{
		if (cell.getX() + 1 < _boardSize)
			return cell.getPoint() == _board[cell.getY()][cell.getX() + 1].getPoint();

		if (cell.getY() + 1 < _boardSize)
			return cell.getPoint() == _board[cell.getY() + 1][cell.getX()].getPoint();

		return false;
	}

	private Cell findEmptyCell(Cell cell, MoveDirection direction)
	{
		boolean isSideDirection = direction == MoveDirection.Left || direction == MoveDirection.Right;

		int startP = !isSideDirection ? cell.getY() : cell.getX();

		Cell emptyCell = null;

		for (int i = startP + direction.getValue(); i >= 0 && i < _boardSize; i += direction.getValue())
		{
			Cell otherCell = isSideDirection ? _board[cell.getY()][i] : _board[i][cell.getX()];

			if (otherCell.isEmpty())
			{
				emptyCell = otherCell;
			}
			else break;
		}

		return emptyCell;
	}

	private Cell findMergeCell(Cell cell, MoveDirection direction)
	{
		boolean isSideDirection = direction == MoveDirection.Left || direction == MoveDirection.Right;

		int startP = !isSideDirection ? cell.getY() : cell.getX();

		Cell mergeCell = null;

		for (int i = startP + direction.getValue(); i >= 0 && i < _boardSize; i += direction.getValue())
		{
			Cell otherCell = isSideDirection ? _board[cell.getY()][i] : _board[i][cell.getX()];

			if (otherCell.isEmpty()) continue;
			else
			{
				if (cell.getPoint() == otherCell.getPoint())
					mergeCell =otherCell;

				break;
			}
		}

		return mergeCell;
	}
}