package generator;
import graph.*;
import colouring.*;
import constraints.*;
import probability.*;
import java.util.*;
import java.io.*;

/**
 * This class contains the main-method and methods used for 
 * generating a random graph with a constraint.
 */

public class GenerateGraph {
    /**
     * This method checks if the given string only contains an integer.
     * @param string the string to be examined.
     * @return true if the string only contains an integer and false
     * otherwise.
     */
    private static boolean isANumber(String string)  
    {  
	try  {  
	    int d = Integer.parseInt(string);  
	}  
	catch(NumberFormatException e)  {
	    return false;  
	}  
	return true;  
    }

    /**
     * The main method for the program.
     */
    public static void main(String[] args) {
	try {
	    HashMap<String,String> variables = new HashMap<String,String>();
	    int size = 0;
	    ProbabilityFunction pf = null;
	    String function = "";
	    ConstraintInterface theConstraint = null;
	    GraphMatrix theGraph = null;
	    String method = "";
	    String outputfile = "";
	    String constraint = "";
	    
	    if (args.length == 0) {
		System.out.println("Usage: java -jar G_n_p_generator.jar -help for more help and options");
		System.exit(0);
	    }
	    
	    for(int a =0; a < args.length; a++) {
		if(args[a].equals("-help")) {
		    System.out.println("----------------------------------------------------------------");
		    System.out.println("   RANDOM SAMPLING OF GRAPHS WITH FORBIDDEN CONSTRAINTS");
		    System.out.println("                         VERSION 1.0");
		    System.out.println("----------------------------------------------------------------");
		    System.out.println("-help                               - show options\n");
		    System.out.println("-size <integer>                     - the size of the graph\n");
		    System.out.println("-constraint <substructure>          - the structure that is not");
		    System.out.println("                                      allowed in the graph, can");
		    System.out.println("                                      either be triangle,");
		    System.out.println("                                      four_cycle, tetrahedron,");
		    System.out.println("                                      octahedron or a given file\n");
		    System.out.println("-probability <probability function> - the probability used to");
		    System.out.println("                                      determine the existence");
		    System.out.println("                                      of each edge in the graph,");
		    System.out.println("                                      should be on the form");
		    System.out.println("                                      p ( n ) = expression\n");
		    System.out.println("-method <edges/vertices>            - the method used for"); 
		    System.out.println("                                      generating the graph,");
		    System.out.println("                                      can either be based on"); 
		    System.out.println("                                      edges or vertices\n");
		    System.out.println("-o <filename>                       - the output-file in which");
		    System.out.println("                                      the graph and its properties");
		    System.out.println("                                      is written to");
		    System.out.println("------------------------------------------------------------------");
		    System.exit(0);
		}
		else if(args[a].equals("-size")) {
		    if(isANumber(args[a+1])) {
			size = Integer.parseInt(args[a+1]);
			variables.put("n", size + "");
			a++;
		    } else {
			System.out.println("The size must be an integer.");
			System.exit(0);
		    }
		}
		else if(args[a].equals("-probability")) {
		    for(int f = a + 6; f < args.length; f++) {
			if(args[f].length() > 1 && args[f].startsWith("-")) {
			    a = f - 1;
			    f = args.length;
			} else {
			    function += args[f] + " "; 
			}
		    }
		    
		    pf = new ProbabilityFunction(function);	
		}
		else if(args[a].equals("-constraint")) {
		    constraint = args[a+1];
		    
		    if(args[a+1].equals("triangle")) {
			theConstraint = new ConstraintTriangle();
		    }
		    else if(args[a+1].equals("four_cycle")) {
			theConstraint = new ConstraintFourCycle();
		    }
		    else if(args[a+1].equals("tetrahedron")) {
			theConstraint = new ConstraintTetrahedron();
		    }
		    else if(args[a+1].equals("octahedron")) {
			theConstraint = new ConstraintOctahedron();
		    } else {
			theConstraint = new ConstraintFromFile(args[a+1]);
		    }
		    a++;
		} else if(args[a].equals("-method")) {
		    method = args[a+1];
		    a++;
		} else if(args[a].equals("-o")) {
		    outputfile = args[a+1];
		    a++;
		}
	    }
	    
	    boolean missingArgument = false;
	   
	    if(size == 0) {
		System.out.println("The size is missing or has the value 0.");
		missingArgument = true;
	    }
	    
	    if(pf == null) {
		System.out.println("The probability function is missing.");
		missingArgument = true;
	    }
	    
	    if(theConstraint == null) {
		System.out.println("The forbidden constraint is missing.");
		missingArgument = true;
	    }
	    if(method.equals("")) {
		System.out.println("The generating method is missing.");
		missingArgument = true;
	    } if(outputfile.equals("")) {
		System.out.println("The outputfile is missing.");
		missingArgument = true;
	    } 
	
	    if(missingArgument) {
		System.exit(0);
	    }

	    if(method.equals("edges")) {
		theGraph = generateGraphEdges(size, pf.calculate(variables), theConstraint);
	    } else if(method.equals("vertices")) {
		theGraph = generateGraphVertices(size, pf.calculate(variables), theConstraint);
	    } else {
		System.out.println("The generating method" + " " + method + " is unknown.");
		System.exit(0);
	    }
	
	    ColouringInterface colouring = new DegreeOfSaturationColouringWithBackTracking();
	
	    theGraph = colouring.colouring(theGraph);

	    BufferedWriter writer = 
		new BufferedWriter(new FileWriter(outputfile));
	
	    writer.write("----------------------------------------------------------------");
	    writer.write("\n   RANDOM SAMPLING OF GRAPHS WITH FORBIDDEN CONSTRAINTS");
	    writer.write("\n                         VERSION 1.0");
	    writer.write("\n----------------------------------------------------------------");
	    writer.write("\ngraph size          : " + size);
	    writer.write("\nforbidden structure : " + constraint);
	    writer.write("\nprobability function: p ( n ) = " + function);
	    writer.write("\ngenerating method   : " + method);
	    writer.write("\n----------------------------------------------------------");
	    writer.write("\nBSC                 : " + theGraph.getMaxColour());
	    writer.write("\n#edges              : " + numberOfEdges(theGraph));
	    writer.write("\n#triangles          : " + countTriangles(theGraph));
	    writer.write("\n----------------------------------------------------------");
	    writer.write("\ngenerated graph:\n" + theGraph);
	
	    writer.close();
	
	} catch(Exception e) {
	    System.out.println("Error during writing to file" +  e.toString());
	}
    }

/**
     * This method creates a random graph according to edges.
     * @param size the number of vertices in the graph
     * @param pf the probability function
     * @param theConstraint method for searching after the forbidden structure
     * @return a random graph
     */
    private static GraphMatrix generateGraphEdges(int size, double pf, ConstraintInterface theConstraint) {
       	GraphMatrix newGraph = new GraphMatrix(size);
	Random randomizer = new Random();
	int numberOfEdges = 0;
	ArrayList<ArrayList<Integer>> edges = new ArrayList<ArrayList<Integer>>();
	
	for(int i = 1; i < size; i++) {	    
	    for(int j = i + 1; j <= size; j++) {
		ArrayList<Integer> edge = new ArrayList<Integer>();

		edge.add(i);
		edge.add(j);
		edges.add(edge);
	    }
	}
	
	while(edges.size() > 0) {
	    int i = (int)(randomizer.nextDouble() * edges.size());
	    ArrayList<Integer> edge = edges.get(i);
	    
	    if(randomizer.nextDouble() <= pf) {
		newGraph.addEdge(edge.get(0), edge.get(1));
		if(theConstraint.exists(newGraph, edge)) {
		    newGraph.removeEdge(edge.get(0), edge.get(1));
		}
	    }
	    
	    edges.remove(i);
	}
	
	return newGraph;
    }
    
    /**
     * This method creates a graph according to vertices.
     * @param size the number of vertices in the graph
     * @param pf the probability function
     * @param theConstraint method for searching after the forbidden structure
     * @return a random graph
     */
    private static GraphMatrix generateGraphVertices(int size, double pf, ConstraintInterface theConstraint) {
       	GraphMatrix newGraph = new GraphMatrix(size);
	Random randomizer = new Random();
	ArrayList<Integer> vertices = new ArrayList<Integer>();
	
	for(int i = 1; i <= size; i++) {
	    vertices.add(i);
	}
	
	while(vertices.size() > 0) {
	    int i = (int)(randomizer.nextDouble() * vertices.size());
	    int v = vertices.get(i);
	
	    System.out.print(v + " ");
            
	    for(int j = v + 1; j <= size; j++) {
		if(randomizer.nextDouble() <=  pf) {
		   newGraph.addEdge(v,j);
			   
		   ArrayList<Integer> newEdge = new ArrayList<Integer>();
		   newEdge.add(v);
		   newEdge.add(j);
		   
		   if(theConstraint.exists(newGraph, newEdge)) {
		       newGraph.removeEdge(v,j);
		   }
		}            
            }
	    
	    vertices.remove(i);
	}
	
	return newGraph;
    }
    
    
    /**
     * This method returns the number of triangles in the graph
     * @param theGraph the graph to be examined
     * @return the number of triangles
     */
    static private int countTriangles(GraphMatrix theGraph) {
        HashSet<ArrayList<Integer>> T = new HashSet<ArrayList<Integer>>(); 
	
	for(int i = 1; i < theGraph.size(); i++) {  
	    ArrayList<Integer> visited = new ArrayList<Integer>();
	    visited.add(i);
	    T.addAll(findAllTriangles(theGraph, visited));
	}

	return T.size();
    }
    
    /**
     * This method returns found in the graph when the search starts at a given vertex
     * @param theGraph the graph to be examined
     * @param visited contains the vertices that have been visited sofar
     * @return a set of the triangles found in the graph
     */
    static private HashSet<ArrayList<Integer>> findAllTriangles(GraphMatrix theGraph, ArrayList<Integer> visited) {
	HashSet<ArrayList<Integer>> T = new HashSet<ArrayList<Integer>>(); 
	
	if(visited.size() <= 3) {
	    ArrayList<Integer> neighbours = theGraph.getNeighbours(visited.get(visited.size()-1));
	    
	    for (Integer neighbour : neighbours) {
		if(visited.get(0) == neighbour && visited.size() == 3) {
		    ArrayList<Integer> newRelation = new ArrayList<Integer>();
		    newRelation.addAll(visited);
		    Collections.sort(newRelation);
		    T.add(newRelation);
		}
		
		if (visited.size() < 3 && !visited.contains(neighbour)){
		    visited.add(neighbour);
		    T.addAll(findAllTriangles(theGraph, visited));
		    visited.remove(visited.size()-1);
		}
	    }
	}
	
	return T;
    }

    /**
     * This method returns the number of edges in the graph
     * @param theGraph the graph to be examined
     * @return the number of edges in the graph
     */
    static private int numberOfEdges(GraphMatrix theGraph) {
	int[][] matrix = theGraph.getMatrix();
	int numberOfEdges = 0;
	
	for(int i = 1; i < matrix.length; i++) {
	    for(int j = i + 1; j < matrix.length; j++) {
		if (matrix[i][j] == 1) {
		    numberOfEdges++;
		}
	    }
	}
	
	return numberOfEdges;
    }
}
