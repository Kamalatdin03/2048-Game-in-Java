import java.util.Scanner;

class Main 
{
	public static void main(String[] args) 
	{	
		var game = new Game(4);
		var input = new Scanner(System.in);

		game.initalized();
		game.displayRules();

		do
		{
			game.displayScore();
			game.renderBoard();

			String key = "";

			do
			{
				key = input.next();
			}
			while (game.keyIsValid(key));

			game.move(key);
		}
		while (!game.isEnd());
	}
}