
public class Command
{
	String line;

	public Command(String line)
	{
		this.line = line.toUpperCase();
	}

	public String getLine()
	{
		return this.line;
	}

	public boolean hasLineNumber()
	{
		try
		{
			if(getLine().indexOf(" ") != -1)
				Integer.parseInt(getLine().substring(0, getLine().indexOf(" ")));
			else
				Integer.parseInt(getLine());
			return true;
		}
		catch(NumberFormatException e)
		{
			return false;
		}
	}

	public boolean hasKeyword()
	{
		try
		{
			Integer.parseInt(getLine().trim());
			return false;
		}
		catch (NumberFormatException e)
		{
			return true;
		}
	}

	public int getLineNumber()
	{
		if (!hasLineNumber())
			return -1;
		else
			return Integer.parseInt(getLine().substring(0, (getLine() + " ").indexOf(" ")));
	}

	public String getKeyword()
	{
		if (!hasLineNumber())
			return getLine().substring(0, getLine().indexOf(" "));
		else if (!hasKeyword())
			return null;
		else
			return getLine().substring(getLine().indexOf(" ") + 1, (getLine() + " ").indexOf(" ", getLine().indexOf(" ") + 1));
	}

	public String getParameters()
	{
		switch(getKeyword().toUpperCase())
		{
			case "LET" :
			{
				String[] elements = getLine().split(" ", 3);
				return elements[2].trim();
			}
			case "PRINT" :
			{
				if (hasLineNumber())
				{
					String[] elements = getLine().split(" ", 3);
					return elements[2].trim();
				}
				else
				{
					String[] elements = getLine().split(" ", 2);
					return elements[1].trim();
				}
			}
			case "GOTO" :
			{
				String[] elements = getLine().split(" ", 3);
				return elements[2].trim();
			}
			
			default : return "";
		}
	}
	
	public String toString()
	{
		return getLine();
	}
}
