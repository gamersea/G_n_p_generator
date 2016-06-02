package probability;
import java.util.*;

/**
 * This class handles a probability function, the function can contain 
 * integers, the variable n (used as the number of vertices in the
 * graph), addition(<tt>+</tt>), substraction(<tt>-</tt>), 
 * multiplication(<tt>*</tt>), division(<tt>/</tt>) and 
 * parentheses(<tt>()</tt>).
 */

public class ProbabilityFunction{
    ArrayList<String> function = new ArrayList<String>();
    double value = 0.0;
    
    public ProbabilityFunction(String function) {
	String[] data = function.split(" ");
	
	for(int i = 0; i < data.length; i++) {
	    this.function.add(data[i]);
	}
    }
    
     /**
     * This method returns the value of the function.
     *
     * @return the value of the function
     */
    public double getValue() {
	return value;
    }

    /**
     * This calculates the value of the function.
     * @param variables the variables and their values.
     * @return the value of the function
     */    
    public double calculate(HashMap<String, String>  variables) {
	ArrayList<String> result = new ArrayList<String>();
	
	for(int i = 0; i < function.size(); i++) {
	    result.add(function.get(i));
	}
	
	for(int i = 0; i < result.size(); i++) {
	    String data = result.get(i);
	    
	    if(variables.get(data) != null) {
		result.set(i, variables.get(data));
	    }
	}

	
	for(int i = 0; i < result.size(); i++) {
	    String data = result.get(i);
	    
	    if(data.equals("sqrt") && result.get(i+1).equals("(")) {
		ArrayList<String> subExpression = 
		    new ArrayList<String>();
		
	        for(int j = i+2; j < result.size(); j++) {
		    if(!result.get(j).equals(")")) {
			subExpression.add(result.get(j));
		    }
		    else {
			j = result.size();
		    }
		}
		
		int subExpressionSize = subExpression.size();
		
		for(int j = 0; j < subExpressionSize + 3; j++) {
		    result.remove(i);
		}

		if(result.size() <= i) {
		    result.add(Math.sqrt
			       (Double.parseDouble
				(calculateSubExpression
				 (subExpression))) + "");
		} else {
		    result.set(i, 
			       Math.sqrt
			       (Double.parseDouble
				(calculateSubExpression
				 (subExpression))) + "");
		}
	
		i = i - subExpressionSize;
	    }
	}
	
	value = Double.parseDouble(calculateSubExpression(result));
		
	return value;
    }

     /**
     * This method calculates the value of an expression.
     * @param subExpression the expression to be calculated
     * @return the value of the expression
     */    
    private String calculateSubExpression(ArrayList<String>subExpression) {
    	ArrayList<String> result = subExpression; 

	for(int i = 0; i < result.size(); i++) {
	    String data = result.get(i);
	    
	    if(data.equals("*")) {
		double factor = 
		    Double.parseDouble(result.get(i-1)) * Double.parseDouble(result.get(i+1));
		
		result.set(i, factor + "");
		result.remove(i-1);
		result.remove(i);
		i = i - 2;
	    } else if(data.equals("/")) {
		double qout = Double.parseDouble(result.get(i-1)) / Double.parseDouble(result.get(i+1));
		
		result.set(i, qout + "");
		result.remove(i-1);
		result.remove(i);
		i= i - 2;
	    }
	}
	
	for(int i = 0; i < result.size(); i++) {
	    String data = result.get(i);
	    	    
	    if(data.equals("+")) {
		double sum = Double.parseDouble(result.get(i-1)) + Double.parseDouble(result.get(i+1));
		
		result.set(i, sum + "");
		result.remove(i-1);
		result.remove(i);
		i = i - 2;
		
	    } else if(data.equals("-")) {
		double diff = Double.parseDouble(result.get(i-1)) - Double.parseDouble(result.get(i+1));
		
		result.set(i, diff + "");
		result.remove(i-1);
		result.remove(i);
		i = i - 2;
	    }
	}
	
	return result.get(0);
    }

     /**
     * This methods returns a string consisting of the probability 
     * function.
     * @return a string consisting o the probability function.
     */    
    public String toString() {
	String outString = "p(n) =";
	
	for(int i = 0; i < function.size(); i++) {
	    outString += " " + function.get(i);
	}
	
	return outString;
    }
}
