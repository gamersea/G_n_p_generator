package constraints;
import graph.*;
import java.util.*;

/**
 * This class implements the interface <tt>ConstraintInterface</tt> 
 * and is used for examing if the constraint <tt>tetrahedron</tt> 
 * will exist in the graph after the new edge has been added to the 
 * graph.
 */
public class ConstraintTetrahedron implements ConstraintInterface{
    /**
     * This method examines if a tetrahedron exists in the graph 
     * after a new edge has been added to it.
     * @param theGraph The graph to be examined.
     * @param newEdge the edge to be added to the graph
     * @return true if the substructure exists in the graph and false 
     *         otherwise.
     */
    public  boolean exists(GraphMatrix theGraph, ArrayList<Integer> newEdge) {
	int e0 = newEdge.get(0);
	int e1 = newEdge.get(1);
	
	for(int v0 = 1; v0 < theGraph.size(); v0++) {
	    if(theGraph.edgeExists(e0, v0) && theGraph.edgeExists(e1, v0)) {
		for(int v1 = 1; v1 < theGraph.size(); v1++) {
		    if(v1 != v0 && e0 != v0 && e1 != v1) {
			if(theGraph.edgeExists(e0, v1) && theGraph.edgeExists(e1, v1)) {
			    if(theGraph.edgeExists(v0, v1)) {
				return true;
			    }
			}
		    }
		}
	    }
	}
	
	return false;
    }
}
