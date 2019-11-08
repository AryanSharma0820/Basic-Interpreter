public class Client
{
    public static void main(String[] args)
    {
        while (true)
        {
        	Command command = new Command(Console.readLine());
            if (command.getLine().equalsIgnoreCase("LIST"))
                Program.listCode();
            else if (command.getLine().equalsIgnoreCase("RUN"))
                Program.run();
            else if (command.getLine().equalsIgnoreCase("CATALOG"))
                Program.catalog();
            else if (command.getLine().equalsIgnoreCase("HOME"))
                Program.home();
            else if (!command.hasLineNumber() && command.getKeyword().equals("PRINT"))
            	Program.print(command);
            else
            	Program.addCommand(command);
        }
    }
}