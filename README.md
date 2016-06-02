# G_n_p_generator
A graph generator that generates graphs without a given constraint, which can be read from a txt-file. Each edge is added at random based on the probability function written by the user.

CONTENT 

This package includes a module for generating random graph without a forbidden constraint.

This software requires Java 6 (JDK 1.6.0+). (You must have installed it separately.
Check that the command "java -version" works and gives 1.6.0+.)

INSTALLATION

Place the gz-file in a directory and unzip it
> gtar -xzvf G_n_p_generator.tar.gz
> cd G_n_p_generator
> ant jar

To check that the installation completed successfully, run the following 
command:

> java -jar G_n_p_generator 

You should now get the usage printed. If you see this, you can start 
using the software. 

QUICKSTART

You can run the random graph generator software like this:

> java -jar G_n_p_generator.jar -size 4 -constraint triangle -method edges -probability p ( n ) = 1 / 2 -o  testtriangle.graph

This will result in a graph containing 4 vertices and no triangles generated 
according to edges and with the probability 1/2.

> java -jar G_n_p_generator.jar -size 10 -constraint four_cycle -method vertices -probability p ( n ) = 1 / sqrt ( n )  -o  testfourcycle.graph

This will result in a graph containing 10 vertices and no four cycles generated 
according to vertices and with the probability 1/sqrt(n).


> java -jar G_n_p_generator.jar -size 10 -constraint triangle.txt -method vertices -probability p ( n ) = 1 / n  -o  testtriangles.graph

This will result in a graph containing 10 vertices and no triangles  generated 
according to vertices and with the probability 1/n. Note that in this case has 
the constraint in the file triangle.txt been used.


NOTE: If you are using mac you have to replace ( with "\(" and ) with "\)".
