public enum MoveDirection
{
	Up(-1), Down(1), Left(-1), Right(1);

	private int _value;

	private MoveDirection(int value)
	{
		_value = value;
	}

	public int getValue()
	{
		return _value;
	}
}