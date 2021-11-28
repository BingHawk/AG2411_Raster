//package kth.ag2411.network;
package ag2411.network;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.io.File;  // Import the File class
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;


public class Network {
    String name; 
    HashMap<String,Node> nodeMap;

    // Constructor
    public Network(String name, String inputFileName) {
        // Initialize theattributes
        this.name= name;
        this.nodeMap= new HashMap<String, Node>();
        
        // You MAY need these local variables to store values or objects 
        // temporarily while constructing a new Network object
        String line,arcID,tailName,headName;
        Node tail,head;
        double weight;
        Arc forwardArc, backwardArc;
        try{
            // Get access to the contents of an ASCIIfile
            File file = new File(inputFileName);
            FileReader fReader = new FileReader(file);
            BufferedReader bReader = new BufferedReader(fReader);
            
            // Read the first line, and do nothing to skip it
            line = bReader.readLine();
            
            // Read the second line, which represents the first 
            // (undirected) arc stored in the file
            line = bReader.readLine();
            // Store each element of the network in forward star.
            while(line != null) {
                // Split each line into an array of 4 Strings
                //using ,as separator.
                String[] tokens = line.split(",");
                arcID = tokens[0];
                tailName = tokens[1];
                headName = tokens[2];
                weight = Double.parseDouble(tokens[3]);

                //creating or retrieving head and tail nodes. 
                if (nodeMap.containsKey(tailName)) {
                    tail = nodeMap.get(tailName);
                    if (nodeMap.containsKey(headName)) {
                        head = nodeMap.get(headName);
                    } else {
                        head = new Node(headName);
                        nodeMap.put(headName,head);
                    }
                } else if(nodeMap.containsKey(headName)){
                    head = nodeMap.get(headName);
                    tail = new Node(tailName);
                    nodeMap.put(tailName,tail);

                } else {
                    head = new Node(headName);
                    nodeMap.put(headName,head);

                    tail = new Node(tailName);
                    nodeMap.put(tailName,tail);
                }

                forwardArc = new Arc(arcID+"_forward",head,tail,weight);
                backwardArc = new Arc(arcID+"_backward",tail,head,weight);

                head.outArcs.add(backwardArc);
                tail.outArcs.add(forwardArc);


                // Do the following.
                // Check if nodeMap contains a Node whose name is
                // tailName or headName.
                // If not, create it, assign it to tail or head, and add
                // it to nodeMap.
                // Otherwise, retrieve it from nodeMap.
                // Then, create two Arcs:
                // one from tail to head, to be added to outArc of tail
                // one from head to tail, to be added to outArc of head.
                // Read the next line
                line = bReader.readLine();
            }
            bReader.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
// Other methods
    public void save(String outputFileName) {
        try {
            File writeFile = new File(outputFileName);
            if (writeFile.createNewFile()) {
              System.out.println("File created: " + writeFile.getName());
            } else {
              System.out.println("File already exists. Owerwriting existing file");
            }
            FileWriter writer = new FileWriter(outputFileName);
            writer.write("TLID\tNAME\tWEIGHT\n");


            writer.close();
        } catch(IOException e) {
                e.printStackTrace();
            }
        // Do something
    }

    private void saveRec(Node node, HashMap visited, FileWriter writer){
        
    }

    public void printNodes(){
        System.out.println("\tNODE NAME\tVALUE");
        Node node;
        for(String nodeName: nodeMap.keySet()) {
            // loop thru nodeMap 
            node = nodeMap.get(nodeName);
            System.out.print("\t"+ node.name);
            // \t represents tab space
            System.out.print("\t\t"+ node.value);
            System.out.println();
        }
    }
    public void printArcs(){
        System.out.println("\tARC NAME\tTAIL NAME\tHEAD NAME\tWEIGHT");
        Node node;
        node = nodeMap.get(nodeMap.keySet().toArray()[0]);
        HashMap<String, Arc> visited = new HashMap<String, Arc>();
        printRec(node,visited);
    }

    private void printRec(Node node, HashMap<String, Arc> visited){
        for(Arc arc: node.outArcs){
            if (!visited.containsKey(arc.name)){
                System.out.println("\t"+arc.name+"\t"+arc.tail.name+"\t\t"+arc.head.name+"\t\t"+arc.weight);
                visited.put(arc.name,arc);
                printRec(arc.head,visited);
            }

        }
    }
}
