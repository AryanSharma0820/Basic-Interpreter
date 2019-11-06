
public class Command
{
	String line;

	public Command(String line)
	{
		this.line = line;
	}

	public String getLine()
	{
		return this.line;
	}

	public boolean hasLineNumber()
	{
		try
		{
			Integer.parseInt(this.line.substring(0, this.line.indexOf(" ")));
			return true;
		}
		catch(NumberFormatException e)
		{
			return false;
		}
	}

	public int getLineNumber()
	{
		if (!hasLineNumber())
			return -1;
		else
			return Integer.parseInt(this.line.substring(0, this.line.indexOf(" ")));
	}

	public String getKeyword()
	{
		if (!hasLineNumber())
			return this.line.substring(0, this.line.indexOf(" "));
		else
			return this.line.substring(this.line.indexOf(" ") + 1, this.line.indexOf(" ", this.line.indexOf(" ") + 1));
	}

	public String getParameters()
	{
		switch(getKeyword().toUpperCase())
		{
			case "LET" :
			{
				String[] elements = this.line.split(" ", 3);
				return elements[2];
			}
			case "PRINT" :
			{
				if (hasLineNumber())
				{
					String[] elements = this.line.split(" ", 3);
					return elements[2];
				}
				else
				{
					String[] elements = this.line.split(" ", 2);
					return elements[1];
				}
			}
			case "GOTO" :
			{
				String[] elements = this.line.split(" ", 3);
				return elements[2];
			}
			default : return "";
		}
	}
}
