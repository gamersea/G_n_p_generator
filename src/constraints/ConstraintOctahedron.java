package constraints;
import graph.*;
import java.util.*;

/**
 * This class implements the interface <tt>ConstraintInterface</tt> 
 * and is used for examing if the constraint <tt>octahedron</tt> 
 * will exist in the graph after the new edge has been added to the 
 * graph.
 */
public class ConstraintOctahedron implements ConstraintInterface{
    /**
     * This method examines if an octahedron exists in the graph 
     * after a new edge has been added to it.
     * @param theGraph The graph to be examined.
     * @param newEdge the edge to be added to the graph
     * @return true if the substructure exists in the graph and false 
     * otherwise.
     */

    public boolean exists(GraphMatrix theGraph, ArrayList<Integer> newEdge) {
	int e0 = newEdge.get(0);
	int e1 = newEdge.get(1);
	
	for(int v0 = 1; v0 < theGraph.size(); v0++) {
	    for(int v1 = 1; v1 < theGraph.size(); v1++) {
		if(v0 != v1 && v0 != e0 && 
		   !newEdge.contains(v0) && !newEdge.contains(v1) &&
		   theGraph.edgeExists(e0, v0) && theGraph.edgeExists(e0, v1) && 
		   theGraph.edgeExists(v0, v1) && theGraph.edgeExists(e1, v0)) {
		    for(int v2 = 1; v2 < theGraph.size(); v2++) {
			if(v2 != v0 && v2 != v1 && 
			   !newEdge.contains(v2) && 
			   theGraph.edgeExists(e0, v2) && theGraph.edgeExists(v1, v2) && theGraph.edgeExists(v2, e1)) {
		           for(int v3 = 1; v3 < theGraph.size(); v3++) 
                           {
			       if(v3 != v0 && v3 != v1 && v3 != v2 && 
				  !newEdge.contains(v3)) {
				   if(theGraph.edgeExists(e1, v3) && theGraph.edgeExists(v0, v3) &&
				      theGraph.edgeExists(v1, v3) && theGraph.edgeExists(v2, v3)) {
				       return true;
				   }
			       }
			   }
			}
		    }
		}
	    }
	}
    
	return false;
    }
}
