package constraints;
import graph.*;
import java.util.*;

/**
 * This class implements the interface <tt>ConstraintInterface</tt> 
 * and is used for examing if the constraint <tt>triangle</tt> will
 * exist in the graph after the new edge has been added to the graph.
 */

public class ConstraintTriangle implements ConstraintInterface{
    /**
     * This method examines if a triangle exists in the graph after 
     * a new edge has been added to it.
     * @param theGraph The graph to be examined.
     * @param newEdge the edge to be added to the graph
     * @return true if the substructure exists in the graph and false 
     *         otherwise.
     */
    public boolean exists(GraphMatrix theGraph, ArrayList<Integer> newEdge){
	int e0 = newEdge.get(0);
	int e1 = newEdge.get(1);

	for(int v = 1; v < theGraph.size(); v++) {
	    if(v != e0 && v != e1 && 
	       theGraph.edgeExists(e0, v) && theGraph.edgeExists(e1, v)) {
		return true;
	    }
	} 
	
       return false;
    }
}
