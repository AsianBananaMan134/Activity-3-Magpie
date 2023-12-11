public class Magpie
{
	/**
	 * Gets a default greeting.
	 * @return String
	 */
	public String greeting()
	{
		return "Hey, what's up?";
	}

	/**
	 * Resturns a response to a user statement
	 * 
	 * @param statement
	 * @return String
	 */
	public String getResponse(String statement)
	{
		String response = "";
		if (findKeyword(statement, "no") >= 0)
		{
		    response = "Don't be so negative!";
		} 
		else if (
		    findKeyword(statement, "mother") >= 0 ||
		    findKeyword(statement, "brother") >= 0 ||
		    findKeyword(statement, "sister") >= 0 ||
		    findKeyword(statement, "father") >= 0
		)
	    {
	        response = "Tell me more about your family!";
	    } 
	    else if (
	        findKeyword(statement, "weather") >= 0 ||
		    findKeyword(statement, "sun") >= 0 ||
		    findKeyword(statement, "rain") >= 0
        )
        {
            response = "The weather here is really nice.";
        } 
        else if (findKeyword(statement, "I want to", 0) >= 0)
        {
            response = transformIWantToStatement(statement);  
        } 
        else if (findKeyword(statement, "I want", 0) >= 0)
        {
            response = transformWantStatement(statement);
        } else {
            if (hasXThenY(statement, "you", "me"))
            {
                response = transformYouMeStatement(statement);
            } 
            else if (hasXThenY(statement, "I", "you"))
            {
                response = transformIYouStatement(statement);
            } else {
                response = randomResponse();
            }
        }
		return response;
	}

	public int findKeyword(String statement, String goal, int startPos)
	{
	    String phrase = statement.trim().toLowerCase();
	    goal = goal.toLowerCase();
	    int position = phrase.indexOf(goal, startPos);

	    while (position >= 0)
	    {
	        String before = " ";
	        String after = " ";
	        if (position > 0)
	        {
	            before = phrase.substring(position - 1, position).toLowerCase();
	        }
	        if (position + goal.length() < phrase.length())
	        {
	            after = phrase.substring(position + goal.length(),
	                                     position + goal.length() + 1).toLowerCase();
	        }
	        // uncomment to view the values of the variables
	        // System.out.println( " psn: " + position + " before: '" + before + "' after: '" + after + "'");
	        if (((before.compareTo ("a") < 0 ) || (before.compareTo("z") > 0)) &&
	            ((after.compareTo ("a") < 0 ) || (after.compareTo("z") > 0)))     
            {
                return position;
            }    
            position = phrase.indexOf(goal, position + 1);
	    }
	    return -1;
	}


	private int findKeyword(String statement, String goal)
	{
	    return findKeyword(statement, goal, 0);
	}

	private String transformIWantToStatement(String statement)
	{
	    return transformSingle(statement, "I want to ", "I don't want to ", ".");
	}

	private String transformWantStatement(String statement)
	{
	    return transformSingle(statement, "I want ", "Would you really be happy if you had ", "?");
	}
	

	private String transformSingle(String statement, String keyword, String before, String after)
	{
	    statement = statement.trim();
	    String lastChar = statement.substring(statement.length() - 1);
	    if (lastChar.equals("."))
	    {
	        statement = statement.substring(0, statement.length() - 1);
	    }
	    int position = findKeyword(statement, keyword, 0);
	    String restOfStatement = statement.substring(position + keyword.length()).trim();
	    return before + restOfStatement + after;
	}


	private boolean hasXThenY(String statement, String first, String second)
	{
	    int position = findKeyword(statement, first, 0);
	    return position >= 0 && findKeyword(statement, second, position) >= 0;
 	}

 	private String transformIYouStatement(String statement)
 	{
 	    return transformInner(statement, "I", "you", "Why do you ", " me?");
 	}

 	private String transformYouMeStatement(String statement)
 	{
 	    return transformInner(statement, "you", "me", "I don't ", " you, where did you hear that?");
 	}

 	private String transformInner(String statement, String starter, String ender, String before, String after)
 	{
 	    statement = statement.trim();
		String lastChar = statement.substring(statement.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement.length() - 1);
		}

		int psnOfStart = findKeyword (statement, starter, 0);
		int psnOfEnd = findKeyword (statement, ender, psnOfStart + starter.length());

		String restOfStatement = statement.substring(psnOfStart + starter.length(), psnOfEnd).trim();
		return before + restOfStatement + after;
 	}

	/**
	 * Pick a default response to use if nothing else fits.
	 * @return String
	 */
	private String randomResponse()
	{
		int NUMBER_OF_RESPONSES = 6;
		double responseIndex = Math.random();
		int whichResponse = (int)(responseIndex * NUMBER_OF_RESPONSES);
		String response = "";

		if (whichResponse == 0)
		{
			response = "Very cool!";
		}
		else if (whichResponse == 1)
		{
			response = "Tell me more about that.";
		}
		else if (whichResponse == 2)
		{
			response = "That's really interesting!";
		}
		else if (whichResponse == 3)
		{
			response = "Can we talk about something else?";
		}
		else if (whichResponse == 4)
		{
			response = "Booooring.";
		}
		else if (whichResponse == 5)
		{
			response = "You really like to talk, don't you?";
		}
		return response;
	}
}
