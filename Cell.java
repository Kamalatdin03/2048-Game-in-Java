import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Cell
{
	private int _x;
	private int _y;
	private int _value;
	private boolean _isMerged;

	private final int MAX_POINT = 2048;

	public ActionListener OnReached;

	public Cell(int x, int y, int value)
	{
		_x = x;
		_y = y;
		_value = value;
	}

	public void swap(Cell other)
	{
		other.setValue(_value);
		_value = 0;
	}

	public void merge(Cell other)
	{
		other.setValue(_value + 1);
		reset();
		_isMerged = true;
	}

	public void setValue(int value)
	{
		_value = value;

		if (getPoint() == MAX_POINT) OnReached.actionPerformed(null);
	}

	public void reset()
	{
		_value = 0;
	}

	public void resetFlag()
	{
		_isMerged = false;
	}

	public int getPoint()
	{
		return (int)Math.pow(2, _value);
	}

	public int getX()
	{
		return _x;
	}

	public int getY()
	{
		return _y;
	}

	public boolean isMerged()
	{
		return _isMerged;
	}

	public boolean isEmpty()
	{
		return _value == 0;
	}

	@Override
	public String toString()
	{
		return isEmpty() ? "::::" : String.format("%4d", getPoint());
	}
}