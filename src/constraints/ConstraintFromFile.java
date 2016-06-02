package constraints;
import java.util.*;
import java.io.*;
import graph.*;

/**
 * This class implements the interface <tt>ConstraintInterface</tt> 
 * and is used for reading a file which contain a desciption of a
 * a constraint written in pseudo code. The class can also 
 * examine if the constraint will exist in the graph after the new 
 *  edge has been added to the 
 * graph.
 */
public class ConstraintFromFile implements ConstraintInterface{
    ArrayList<Statement> statements = new ArrayList<Statement>();

    /**
     * This Constructor reads in the pseudocode needed to examine a
     * constraint from an inputfile and stores it.
     * @param filename the file to be read
     */
    public ConstraintFromFile(String filename) {
	try {
	    BufferedReader reader =  new BufferedReader(new FileReader(filename));
	    String line = reader.readLine();
	    
	    while(line != null) {
		if(line.trim().startsWith("for_all")) {
		    Statement theStatement = new Statement(Statement.forallStatement);
		    String[] split = line.trim().split(" ");
		    theStatement.setCurrentVertex(split[2]);
		    ArrayList<String> excludedVertices = new ArrayList<String>();
		    String[] splitVertices = split[6].split(",");
		   
		    excludedVertices.add(splitVertices[0].substring(1, splitVertices[0].length()));
		
		    for(int i = 1; i < splitVertices.length-1; i++) {
			excludedVertices.add(splitVertices[i]);
		    }
		
		    excludedVertices.add
			(splitVertices[splitVertices.length-1].substring(0, splitVertices[0].length()-1));
		
		    theStatement.setExcludedVertices(excludedVertices);
		    statements.add(theStatement);
		}
		else if(line.trim().startsWith("if")) {
		    Statement theStatement = new Statement(Statement.ifStatement);
		    line = line.trim().substring(4, line.trim().length()-6);
		    String[] splitExpressions = line.split("and");
		    ArrayList<ArrayList<String>> edges = new ArrayList<ArrayList<String>>();
		
		    for(int i = 0; i < splitExpressions.length; i++) {
			String[] currentEdge = splitExpressions[i].trim().split(" ")[0].split(",");
			ArrayList<String> edge = new ArrayList<String>();
			
			edge.add(currentEdge[0].substring(1,currentEdge[0].length()));
			edge.add(currentEdge[1].substring(0,currentEdge[1].length()-1));
			edges.add(edge);
		    }
			
		    theStatement.setEdges(edges);
		    statements.add(theStatement);
		}
		else if(line.trim().startsWith("return")) {
		    Statement theStatement = new Statement(Statement.returnStatement);
       		
		    theStatement.setValue(line.trim().split(" ")[1]);
		    statements.add(theStatement);
	   
		}
		else if(line.trim().startsWith("end for")) {
		    Statement theStatement = new Statement(Statement.endfor);
		   
		    statements.add(theStatement);
		}
		else if(line.trim().startsWith("end if")) {
		    Statement theStatement = new Statement(Statement.endif);
		    
		    statements.add(theStatement);
		}
	
		line = reader.readLine();
	    }
	
	    reader.close();
	} catch(Exception e) {
	    System.err.println("Error while reading the file " + filename + ".");
	}
    }

    /**
     * This method examines if a constraint written in a given file will
     * exists in the graph after a new edge has been added to it.
     * @param theGraph The graph to be examined.
     * @param newEdge the edge to be added to the graph
     * @return true if the substructure exists in the graph and false 
     *         otherwise.
     */
    public boolean exists(GraphMatrix theGraph, ArrayList<Integer> newEdge) {
	ArrayList<Integer> vertices = new ArrayList<Integer>();
	
	vertices.add(newEdge.get(0));
	vertices.add(newEdge.get(1));
	
	return executeStatements(theGraph, 0, vertices);
    }
  
     /**
     * This method executes the statement read from the file and examines if the constraint
     * exists in the graph.
     * @param theGraph The graph to be examined.
     * @param step location of the statement to be executed
     * @return true if the substructure exists in the graph and false 
     *         otherwise.
     */
    public boolean executeStatements(GraphMatrix theGraph, int step, ArrayList<Integer> vertices) {
	Statement currentStatement = statements.get(step);
	boolean result = false;
	
	if(currentStatement.getType().equals(Statement.forallStatement)) {
	    for(int v = 1; v < theGraph.size(); v++) {
		if(!vertices.contains(v)) {
		    vertices.add(v);
		    result = result || executeStatements(theGraph, step + 1, vertices);
		    if(result) {
			return result;
		    }
		    
		    vertices.remove(vertices.size() - 1);
		}
	    }
	} 
	else if(currentStatement.getType().equals(Statement.ifStatement)) {
	    boolean allExists = true;
	    
	    for(ArrayList<String> edge: currentStatement.getEdges()) {
		ArrayList<Integer> testEdge = new ArrayList<Integer>();
		
		for(String vertex : edge) {
		    if(vertex.equals("e0")) {
			testEdge.add(vertices.get(0));
		    } else if(vertex.equals("e1")) {
			testEdge.add(vertices.get(1));
		    } 
		    else {
			testEdge.add(vertices.get(2 + Integer.parseInt(vertex.substring(1, vertex.length()))));
		    }
		}
			
		if(!theGraph.edgeExists(testEdge.get(0), testEdge.get(1))) {
		    allExists = false;
		}
	    }
	    
	    if(allExists) {
		boolean nextResult = false;
		
		if(step + 1 < statements.size()) {
		    nextResult = executeStatements(theGraph, step + 1, vertices);
		}
		
		if(allExists && nextResult) {
		    return true;
		}
	    }
	}
    	else if(currentStatement.getType().equals(Statement.returnStatement)) {
	    return currentStatement.getValue() == 1;
	}
	else if((currentStatement.getType().equals(Statement.endfor) ||
		 currentStatement.getType().equals(Statement.endif)) &&
		step + 1 < statements.size()) {
	    return result || executeStatements(theGraph, step + 1, vertices);
	}
	
	return result;	
    }
    
    /**
     * This method returns a string which contains the pseudocode read from the file.
     * @return a string with the pseudocode
     */
    public String toString() {
	String outString = "";
	String tab = "";
	
	for(Statement statement: statements) {
	    if (statement.getType().equals(Statement.endfor) || statement.getType().equals(Statement.endif)) {
		tab = tab.substring(0, tab.length()-1);
	    }
	    
	    outString += tab + statement + "\n";
	    
	    if(statement.getType().equals(Statement.forallStatement) || 
	       statement.getType().equals(Statement.ifStatement)) {
		tab += "\t";
	    }
	}
	
	return outString;
    }
}
