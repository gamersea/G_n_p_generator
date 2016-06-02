package colouring;
import graph.*;
/** 
 * This interface handles the method used for colouring.
 *
 */

public interface ColouringInterface {
    /**
     * This method colours the given graph.
     * 
     *@param theGraph - the graph to be coloured
     *@return the coloured graph
     */
    public GraphMatrix colouring(GraphMatrix theGraph);
}
