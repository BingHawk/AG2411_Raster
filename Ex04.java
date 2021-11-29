// AG2412|GIS Architectures and Algorithms 
// Ex03:Implementing Map Algebraic Operations
//-------------------------------------------------
// Created & Updated by: Leonard Hökby & Mahsa Movaghar
// Wednesday, Nov 26, 2021
// ------------------------------------------------

import ag2411.network.*;


public class Ex04 {

	//run with:
	//java Ex04.java data/test/smallnetwork.txt
	public static void main(String[] args) {
		// args[0] : name of an input file
		// args[1] : name of an out put file 
		Network testNetwork = new Network("test",args[0]);
		testNetwork.printNodes();
		System.out.println();
		testNetwork.printArcs();

		String[] splitPath = args[0].split("/");
		testNetwork.save("data/output/arc"+splitPath[2]);
	}

}
