package colouring;
import graph.*;
import java.util.*;

/**
 * This class implements the interface <tt>ColouringInterface</tt> and 
 * the method in this class colours the graph according to the BSC-
 * algorithm.
 */
public class DegreeOfSaturationColouringWithBackTracking implements ColouringInterface {
    /**
     * This method colours the given graph <tt>theGraph</tt> and returns 
     * it.
     * @param theGraph the graph to be coloured
     * @return the coloured graph
     */
    public GraphMatrix colouring(GraphMatrix theGraph) {
	int[] A = sortAccordingToTheHighestDegree(theGraph, theGraph.getVertices());
	int[] Fopt = new int[A.length];
	int[] colours = new int[A.length + 1];
	colours[0] = 0;
	int start = 0;
	int optColourNumber = A.length + 1;
	int x = A[0];
	int i = start;
	ArrayList<Integer> U = new ArrayList<Integer>();
	U.add(1);
	
	while(start >= 0) {
	    boolean back = false;
	    
	    for(i = start; i < A.length; i++) {
		if(i > start) {
		    x = getVertexWithHighestSaturation(theGraph, A);
		    U = theGraph.getFreeColours(x, optColourNumber);
		}
	
		if(U.size() > 0) {
		    theGraph.setColour(x, U.get(0));
		    colours[i+1] = Math.max(U.get(0), colours[i]);
		    
		} else {
		    start = i - 1;
		    back = true;
		    break;
		}
	    }
	    
	    if(back) {
		if(start >= 0) {
		    x = A[start];
		    theGraph.uncolour(x);
		}
	    }
	    else {
		for(int c=0; c < A.length; c++) {
		    Fopt[c] = theGraph.getColour(A[c]); 
		}
		
		optColourNumber = colours[A.length];
		
		for(int c=0; c < A.length; c++) {
		    if(Fopt[c] == optColourNumber) {
			i = c;
			c = A.length;
		    }
		}
		
		start = i - 1;
		
		if(start < 0) {
		    break;
		}
		
		for(int c=start; c < A.length; c++) {
		    theGraph.uncolour(A[c]); 
		}
		
		x = A[start];
		U = theGraph.getFreeColours(x, optColourNumber);

	    }
	}
	
	for(int c=0; c < A.length; c++) {
	    theGraph.setColour(A[c], Fopt[c]); 
	}

	return theGraph;
    }

    /**
     * This method sorts the vertices in the graph according to their 
     degrees.
     * @param theGraph the graph to be coloured
     * @param vertices the vertices to be sorted
     * @return the vertices sorted in according to their degree and in
     a descending order.
     */     
    private int[] sortAccordingToTheHighestDegree(GraphMatrix theGraph, int[] vertices) {
        for(int i = 0; i < vertices.length; i++) {
	    for(int j = i; j < vertices.length; j++) {
               if(theGraph.getDegree(vertices[i]) < 
		  theGraph.getDegree(vertices[j])) {
		   int temp = vertices[i];
		   vertices[i] = vertices[j];
		   vertices[j] = temp;
	       }    
	    }
        }
	
	return vertices;
    }
    
    /**
     * This method returns the vertex with the highest saturation.
     * @param theGraph the graph to be coloured
     * @param vertices the vertices in the graph
     * @return the vertex with the highest saturation
     */     
    private int getVertexWithHighestSaturation(GraphMatrix theGraph, int[] vertices) {
	int v = -1;
	int vSaturation = -1;
	
	for(int i = 0; i < vertices.length; i++){
	  
	    if(theGraph.getColour(vertices[i]) == 0) {
		if(v == -1) {
		    v = i;
		    vSaturation = theGraph.getSaturation(vertices[i]);
		} else 
		    if(vSaturation < theGraph.getSaturation(vertices[v]))
		    {
			v = i;
			vSaturation = theGraph.getSaturation(vertices[v]);
		    }
	    }
	}
	
	return vertices[v];
    }
}
