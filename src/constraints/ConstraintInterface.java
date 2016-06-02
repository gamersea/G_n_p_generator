package constraints;
import graph.*;
import java.util.*;

/**
 * This interface handles the method used for examing if a constraint 
 * exists in the graph.
 */
public interface ConstraintInterface{
    /**
     * This method examining if some structure exists in the graph 
     * after a new edge has been
     * added to it.
     * @param theGraph The graph to be examined.
     * @param newEdge the edge to be added to the graph
     * @return true if the substructure exists in the graph
     *         and false otherwise.
     */
    public boolean exists(GraphMatrix theGraph, ArrayList<Integer> newEdge);
}
