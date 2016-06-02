package colouring;
import graph.*;
import java.util.*;

/**
 * This class implements the interface <tt>ColouringInterface</tt> and 
 * the method in this class colours the graph according to the DSATUR-
 * algorithm.
 */
public class DegreeOfSaturationColouring implements ColouringInterface {
    /**
     * This method colours the given graph <tt>theGraph</tt> and returns 
     * it.
     * @param theGraph the graph to be coloured
     * @return the coloured graph
     */
    public GraphMatrix colouring(GraphMatrix theGraph) {
	int[] A = sortAccordingToTheHighestDegree(theGraph, theGraph.getVertices());
	
	theGraph.setColour(A[0], 1);
	for(int i = 1; i < A.length; i++) {
	    int x = getVertexWithHighestSaturation(theGraph, A);
	    theGraph.setColour(x, theGraph.getFreeColour(x));		
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
