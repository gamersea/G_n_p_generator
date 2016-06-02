package constraints;
import java.util.*;

/**
 * This class stores information about the statements in a given
 * input file.
 */
public class Statement {
    public static String forallStatement = "for_all";
    public static String ifStatement = "if";
    public static String endfor = "endfor";
    public static String endif = "endif";
    public static String returnStatement = "return";
    private String type = "";
    private int value = -1;
    private String currentVertex = "";
    private ArrayList<String> excludedVertices = null;
    private ArrayList<ArrayList<String>> edges = null;
    
    /**
     * This Constructor creates a statement.
     * @param type the type of the statement
     */
    public Statement(String type) {
	this.type = type;
    }
    
    /**
     * This method returns the type of the statement.
     * @return the type of the statement
     */
    public String getType() {
	return type;
    }
   
    /**
     * This method returns the value of the statement (if it exists).
     * @return the value of the statement (if it exists).
     */
    public int getValue() {
	return value;
    }
   
    /**
     * This method returns a list of edges used by an if-statement to
     * test if some edges exists in the graph.
     * @return a list of edges
     */
    public ArrayList<ArrayList<String>> getEdges() {
	return edges;
    }

    /**
     * This method sets a value to a statement.
     * @param value the value to be set
     */
    public void setValue(String value) {
	if(value.equals("true")) {
	    this.value = 1;
	} else {
	    this.value = 0;
	} 
    }
    
    /**
     * This method sets which vertex a for all loop is working with
     * @param currentVertex the vertex a for all loop is working with
     */
    public void setCurrentVertex(String currentVertex) {
	this.currentVertex = currentVertex;
    }
    
    /**
     * This method sets which vertices should be avoided by the for all loop
     * @param excludedVertices the vertices to be avoided
     */
    public void setExcludedVertices(ArrayList<String> excludedVertices) {
	this.excludedVertices = excludedVertices;
    }
    
    /**
     * This method sets edges to be examined by an if-statement.
     * @param edges the edges to be examined
     */
    public void setEdges(ArrayList<ArrayList<String>> edges) {
	this.edges = edges;
    }
   
    /**
     * This method returns a string containing the statement.
     * @return a string containing the statement
     */
    public String toString() {
	String outString = type;
	
	if(value != -1) {
	    outString += " " + value;
	}
	
	if(excludedVertices != null) {
	    outString += " ( " + currentVertex + " in V \\ {";
	    for(int i = 0; i < excludedVertices.size(); i++) {
		if(i > 0) {
		    outString += ",";
		}
		
		outString += excludedVertices.get(i);
	    }
	    outString += "} ) do";
	}
	
	if(edges != null) {
	    outString += " ( {";
	    
	    for(int i = 0; i < edges.size(); i++) {
		if(i > 0) {
		    outString += " and {";
		}
		
		outString += 
		    edges.get(i).get(0) + "," + 
		    edges.get(i).get(1) + "} in E";
	    }
	    
	    outString += " ) then";
	}
	
	return outString;
    }
}

