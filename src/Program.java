import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class Program
{
	private static LinkedList<Command> list = new LinkedList<Command>();
	private static HashMap<String, Double> numVars= new HashMap<String, Double>();
	private static HashMap<String, String> stringVars= new HashMap<String, String>();

	public static boolean addCommand(Command command)
	{
		if (command.getLineNumber() == -1)
			return false;
		else
		{
			if (list.isEmpty())
				list.add(command);
			else if (!command.hasKeyword())
			{
				ListIterator<Command> listIterator = list.listIterator();
				while (listIterator.hasNext())
				{
					if (command.getLineNumber() == listIterator.next().getLineNumber())
						listIterator.remove();
				}
			}
			else if (command.getLineNumber() < list.getFirst().getLineNumber())
				list.addFirst(command);
			else if (command.getLineNumber() > list.getLast().getLineNumber())
				list.addLast(command);
			else if (command.getLineNumber() == list.getFirst().getLineNumber())
				list.set(0, command);
			else
			{
	 			ListIterator<Command> listIterator = list.listIterator();
				boolean bAdded = false;
				Command previous = listIterator.next();

				while (listIterator.hasNext() && !bAdded)
				{
					Command next = listIterator.next();
					if (command.getLineNumber() > previous.getLineNumber() && command.getLineNumber() < next.getLineNumber())
					{
						bAdded = true;
						listIterator.previous();
						listIterator.add(command);
					}
					else if (command.getLineNumber() == next.getLineNumber())
					{
						bAdded = true;
						listIterator.previous();
						listIterator.set(command);
					}

					previous = next;
				}
			}
			return true;
		}
	}

	public static void listCode()
	{
		System.out.println("] ");
		for(Command c : list)
			System.out.println("] " + c);
		System.out.println("] ");
	}

	public static void catalog()
	{
		System.out.println("]");
		System.out.println("] HOME");
		System.out.println("] LIST");
		System.out.println("] RUN");
		System.out.println("] LET");
		System.out.println("] PRINT");
		System.out.println("] GOTO");
		System.out.println("] END");
		System.out.println("]");
	}

	public static void home()
	{
		for (int i = 0; i < 80000; i++)
			System.out.println();
	}

	public static void run()
    {
        System.out.println("]");

        boolean bEnded = false;
        ListIterator<Command> listIterator = list.listIterator();
        while (listIterator.hasNext())
        {
            if (bEnded)
                return;
            Command command = listIterator.next();
            switch (command.getKeyword().toUpperCase())
            {
                case "LET" :
                    let(command);
                    break;
                case "PRINT" :
                    print(command);
                    break;
                case "HOME" :
                    home();
                    break;
                case "GOTO" :
                    boolean bLineExists = false;
                    Iterator<Command> iterator = list.iterator();
                    while (iterator.hasNext() && !bLineExists)
                    {
                        if (iterator.next().getLineNumber() == command.getLineNumber())
                            bLineExists = true;
                    }

                    if(bLineExists)
                    {
                        if (command.getLineNumber() > Integer.valueOf(command.getParameters()))
                            while(listIterator.previous().getLineNumber() != Integer.valueOf(command.getParameters())) {}
                        else if (command.getLineNumber() < Integer.valueOf(command.getParameters()))
                        {
                            while(listIterator.next().getLineNumber() != Integer.valueOf(command.getParameters())) {}
                            listIterator.previous();
                        }
                    }

                    break;
                case "END" :
                    bEnded = true;
                    break;
            }
        }
        System.out.println("]");
    }

	public static void print(Command command)
    {
        if (command.getParameters().charAt(command.getParameters().length() - 1) == '$')
        {
            if (stringVars.containsKey(command.getParameters()))
                System.out.println("] " + stringVars.get(command.getParameters()));
            else
                System.out.println("] ");
        }
        else
        {
            if(numVars.containsKey(command.getParameters()))
            {
                if(numVars.get(command.getParameters()) == numVars.get(command.getParameters()).intValue())
                    System.out.println("] " + numVars.get(command.getParameters()).intValue());
                else
                    System.out.println("] " + numVars.get(command.getParameters()));
            }
            else
                System.out.println("] 0");
        }
    }

	private static void let(Command command)
    {
        String[] parameters = command.getParameters().split("=");
        for(int i = 0; i < parameters.length; i++)
            parameters[i] = parameters[i].trim();

        if (parameters[0].charAt(parameters[0].length() - 1) == '$')
        {
            parameters[1] = parameters[1].replaceAll("\"", "");
            stringVars.put(parameters[0], parameters[1]);
        }
        else
        {
            try
            {
                numVars.put(parameters[0], Double.valueOf(parameters[1]));
            }
            catch(NumberFormatException e)
            {
                System.out.println("] ParseError: Type mismatch error: Expected number in line " + command.getLineNumber());
            }
        }
    }
}
