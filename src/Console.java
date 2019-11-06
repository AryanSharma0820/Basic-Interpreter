import java.util.Scanner;

public class Console
{
	private static Scanner scanner = new Scanner(System.in);
	public static String readLine()
	{
		System.out.print("] ");
		return scanner.nextLine();
	}
}
