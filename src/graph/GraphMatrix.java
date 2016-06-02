package graph;
import java.util.*;
import java.io.*;

/**
 * This class represents an undirected graph <tt>G = (V,E)</tt> and the 
 * edges in the graph are stored in an adjacent matrix.
 */
public class GraphMatrix
{
    private int[][] theGraph = null;

    /**
     * Returns a graph <tt>G = (V, E)</tt> consisting of n vertices and an
     * empty set of edges, <tt>E</tt>.
     *
     * @param  size the number of vertices in the graph
     */
    public GraphMatrix(int size)
    {   
	theGraph = new int[size + 1][size + 1];
    }

    /**
     * Inserts the edge <tt>e = {vertex0, vertex1}</tt> to the graph 
     * <tt>G = (V, E)</tt>.
     *
     * @param  vertex0 - the first vertex in the edge e
     * @param  vertex1 - the second vertex in the edge e
     * @return a graph in which e has been inserted.
     */
    public GraphMatrix addEdge(int vertex0, int vertex1) 
    {
	if (vertex0 != vertex1) {
	    theGraph[vertex0][vertex1] = 1;
	    theGraph[vertex1][vertex0] = 1;
	}
	
	return this;
    }

    /**
     * Removes the edge <tt>e = {vertex0, vertex1}</tt> from the graph 
     * <tt>G = (V, E)</tt>.
     *
     * @param  vertex0 - the first vertex in the edge e
     * @param  vertex1 - the second vertex in the edge e
     * @return a graph in which e has been removed
     */
    public GraphMatrix removeEdge(int vertex0, int vertex1) 
    {
	if (vertex0 != vertex1) {
	    theGraph[vertex0][vertex1] = 0;
	    theGraph[vertex1][vertex0] = 0;
	}	

	return this;
    }
    
    /**
     * Returns an array of the vertices in the graph.
     *
     * @return  an array of the vertices in the graph
     */
    public int[] getVertices() {
	int[] vertices = new int[size() - 1];
	
	for(int i = 1; i < size(); i++) {
	    vertices[i - 1] = i;
	}
	
	return vertices;
    }
    
     /**
     * Returns the adjacent matrix which contains the edges in the graph.
     *
     * @return  an array of the vertices in the graph
     */
    public int[][] getMatrix() {	
	return theGraph;
    }
  
    /**
     * Returns true if the edge <tt>e = {vertex0, vertex1} </tt> eixsts in
     * the graph <tt>G = (V, E)</tt>.
     *
     * @param  vertex0 - the first vertex in the edge e
     * @param  vertex1 - the second vertex in the edge e
     * @return true if the edge e exists in the graph and false otherwise.
     */
    public boolean edgeExists(int vertex0, int vertex1) 
    {
	if(vertex0 == vertex1) {
	    return false;
	}
	else {
	    return theGraph[vertex0][vertex1] != 0;
	}
    }
  
    /**
     * Returns the degree of the given vertex, that is how many edges the
     * vertex has.
     *
     * @param  vertex - the given vertex
     * @return returns the degree of vertex.
     */
    public int getDegree(int vertex) {
	return getNeighbours(vertex).size();
    }
    
    /**
     * Returns the neighbours of the given vertex, that an arraylist of 
     * the vertices that are adjancent to the given vertex.
     *
     * @param  vertex - the given vertex
     * @return returns an arraylist constaining the neighbours of the 
     * given vertex.
     */
    public ArrayList<Integer> getNeighbours(int vertex)
    {
	ArrayList<Integer> neighbours = new ArrayList<Integer>();
	
	for(int i = 1; i < theGraph.length; i++) {
	    if(theGraph[vertex][i] != 0) {
		neighbours.add(i);
	    }
	}
	
	return neighbours;
    }

    /**
     * Returns the colour of the given vertex.
     *
     * @param  vertex - the given vertex
     * @return returns the colour of the given vertex.
     */
    public int getColour(int vertex)
    {
	return theGraph[vertex][0];
    }
    
    /**
     * Assigns the given colour to the given vertex.
     *
     * @param  vertex - the given vertex
     * @param  colour - the given colour
     */
    public void setColour(int vertex, int colour)
    {
	if(getColour(vertex) == 0) {
	    theGraph[vertex][0] = colour;
	}
    }

    public void uncolour(int vertex)
    {
	theGraph[vertex][0] = 0;
    }
    
    /**
     * Returns the lowest colour that can be used for colour the vertex. 
     * A colour is free to use for colouring if none of the neighbours of 
     * the given vertex has been coloured by the colour.
     *
     * @param  vertex - the given vertex
     * @return the lowest colour that can be used for colour the vertex
     */
    public int getFreeColour(int vertex)
    {
	int colour = 1;
	HashSet<Integer> colours = new HashSet<Integer>();
	
	for(int i=1; i < theGraph.length; i++) {
	    if(theGraph[vertex][i] != 0) {
		colours.add(getColour(i));
	    }
	}
	
	while(colours.contains(colour)) {
	    colour++;
	}
		
	return colour;
    }
    
    /**
     * Returns the set with colours that can be used for colouring the given vertex. 
     * A colour is free to use for colouring if none of the neighbours of 
     * the given vertex has been coloured by the colour.
     *
     * @param  vertex - the given vertex
     * @param  optColourNumber - the optimal number of colours
     * @return the colours which are less than optColourNumber and can be used 
     * for colour the vertex
     */
    public ArrayList<Integer> getFreeColours(int vertex, int optColourNumber)
    {
	ArrayList<Integer> freeColours = new ArrayList<Integer>();
	int colour = 1;
	HashSet<Integer> colours = new HashSet<Integer>();
	
	for(int i=1; i < theGraph.length; i++) {
	    if(theGraph[vertex][i] != 0) {
		colours.add(getColour(i));
	    }
	}
	
	while(colour < optColourNumber) {
	    if(!colours.contains(colour) && colour < optColourNumber) {
		freeColours.add(colour);
	    }
	    colour++;
	}
	
	Collections.sort(freeColours);
	
	return freeColours;
    }
    
    /**
     * Returns the saturation of the given vertex, that is number of 
     * colours in found in the neighbourhood
     *
     * @param  vertex - the given vertex
     * @return Returns the saturation of the given vertex.
     */
    public int getSaturation(int vertex)
    {
	HashSet<Integer> colours = new HashSet<Integer>();
	
	for(int i=1; i < theGraph.length; i++) {
	    if(theGraph[vertex][i] != 0) {
		colours.add(getColour(i));
	    }
	}
	
	return colours.size();
    }

    /**
     * Returns the highest colour that has been used to colour the graph.
     *
     * @return Returns the highest colour that has been used to colour 
     * the graph.
     */
    public int getMaxColour()
    {
	int colour = getColour(1);
	
	for(int i=2; i < theGraph.length; i++) {
	    if(colour < getColour(i)) {
		colour = getColour(i);
	    }
	}
	
	return colour;
    }

    /**
     * Returns the size of the graph including an extra coloum/row for 
     * storing the colours given to each vertex.
     *
     * @return Returns the size of the graph including an extra coloum/row
     * for storing the colours given to each vertex.
     */
    public int size()
    {
	return theGraph.length;
    }
    
   
    /**
     * Converts the graph to a string and returns it.
     *
     * @return  Converts the graph to a string and returns it.
     */   
    public String toString()
    {
	StringBuffer string = new StringBuffer();
	
	string.append("-------------------------------------\n");
	
	for(int i=1; i < theGraph.length;i++) {
	    string.append(i + ":\n----\ncolour: " + theGraph[i][0] + 
			  "\nedges: "); 
	    
	    for(int j=1;j < theGraph[i].length;j++) {
		if(theGraph[i][j] != 0) {
		    string.append(j + " "); 
		}
	    }
	    string.append("\n-------------------------------------\n");
	}
	
	return string.toString();                                  
    }
}


