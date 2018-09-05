package com.stackroute.datamunger.query;

//This class contains methods to evaluate expressions
public class Filter {
	
	/* 
	 * The evaluateExpression() method of this class is responsible for evaluating 
	 * the expressions mentioned in the query. It has to be noted that the process 
	 * of evaluating expressions will be different for different data types. there 
	 * are 6 operators that can exist within a query i.e. >=,<=,<,>,!=,= This method 
	 * should be able to evaluate all of them. 
	 * Note: while evaluating string expressions, please handle uppercase and lowercase 
	 * 
	 */
	
	public boolean evaluateExpression(String operator, String firstInput, String secondInput, String dataType) {
		
		
		switch (operator) {
	    case "=":
	        if (equalTo(firstInput, secondInput, dataType))
	            return true;
	        else
	            return false;
	    case "!=":
	        if (!equalTo(firstInput, secondInput, dataType))
	            return true;
	        else
	            return false;
	    case ">=":
	        if (greaterThanOrEqualTo(firstInput, secondInput, dataType))
	            return true;
	        else
	            return false;
	    case "<=":
	        if (lessThanOrEqualTo(firstInput, secondInput, dataType))
	            return true;
	        else
	            return false;
	    case ">":
	        if (greaterThan(firstInput, secondInput, dataType))
	            return true;
	        else
	            return false;
	    case "<":
	        if (lessThan(firstInput, secondInput, dataType))
	            return true;
	        else
	            return false;
	    }
	    return false;
	}
	
	
	
	//Method containing implementation of equalTo operator
	
	
	
	
	//Method containing implementation of notEqualTo operator
	
	  
	
	
	//Method containing implementation of greaterThan operator
	
	  
	
	
	
	
	
	//Method containing implementation of greaterThanOrEqualTo operator
	
	   
	
	
	
	
	//Method containing implementation of lessThan operator
	  
	  
	private boolean lessThan(String firstInput, String secondInput, String dataType) {
		// TODO Auto-generated method stub
		switch(dataType) {
    	case "java.lang.Integer":
    		
    	case "java.lang.Double":
    		
    		try {
                if (Double.parseDouble(firstInput) < Double.parseDouble(secondInput))
                    return true;
                else
                    return false;
            } catch (NumberFormatException nfe) {
                return false;
            }
        default:
            if ((firstInput.compareToIgnoreCase(secondInput)) < 0)
                return true;
            else
                return false;
    	}
	}

	private boolean greaterThan(String firstInput, String secondInput, String dataType) {
		// TODO Auto-generated method stub
		switch(dataType) {
    	case "java.lang.Integer":
    		
    	case "java.lang.Double":
    		try {
                if (Double.parseDouble(firstInput) > Double.parseDouble(secondInput))
                    return true;
                else
                    return false;
            } catch (NumberFormatException nfe) {
                return false;
            }
        default:
            if ((firstInput.compareToIgnoreCase(secondInput)) > 0)
                return true;
            else
                return false;
    	}
	}

	private boolean lessThanOrEqualTo(String firstInput, String secondInput, String dataType) {
		// TODO Auto-generated method stub

    	switch(dataType) {
    	case "java.lang.Integer":
    		
    	case "java.lang.Double":
    		
    		try {
                if (Double.parseDouble(firstInput) <= Double.parseDouble(secondInput))
                    return true;
                else
                    return false;
            } catch (NumberFormatException nfe) {
                return false;
            }
        default:
            if ((firstInput.compareToIgnoreCase(secondInput)) <= 0)
                return true;
            else
                return false;
    	}
	}

	private boolean greaterThanOrEqualTo(String firstInput, String secondInput, String dataType) {
		// TODO Auto-generated method stub
		switch(dataType) {
    	case "java.lang.Integer":
    		
    	case "java.lang.Double":
    		
    		try {
                if (Double.parseDouble(firstInput) >= Double.parseDouble(secondInput))
                    return true;
                else
                    return false;
            } catch (NumberFormatException nfe) {
                return false;
            }
        default:
            if ((firstInput.compareToIgnoreCase(secondInput)) >= 0)
                return true;
            else
                return false;
    	

    	}
	}

	private boolean equalTo(String firstInput, String secondInput, String dataType) {

    	switch(dataType) {
    	case "java.lang.Integer":
    		
    	case "java.lang.Double":
    		
    	default:
    		if(firstInput.equalsIgnoreCase(secondInput))
    			return true;
    		else
    			return false;
    	}
	}

	    	
	
	
	
	//Method containing implementation of lessThanOrEqualTo operator
	
}
