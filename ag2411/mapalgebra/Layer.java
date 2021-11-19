package ag2411.mapalgebra;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.awt.image.*;

import java.util.HashMap;
import java.util.Random;

public class Layer {

	// Attributes
	public String name; // name of this layer
	public int nRows; // number of rows
	public int nCols; // number of columns
	public double[] origin = new double[2]; // x,y-coordinates of lower-left corner
	public double resolution; // cell size
	public double[] [] values; // data. Alternatively, public double[][] values;
	public double nullValue; // value designated as "No data"

	//Constructors
	public Layer(String layerName, String fileName) {	// Constructor - same name as class (Layer in this case).
		//System.exit(0);
		// You may want to do some work before reading a file.

		try { // Exception may be thrown while reading (and writing) a file.

			// ----- ----- READER - first 6 lines ----- -----
						File rFile = new File(fileName);						// This object represents an input file, elevation.txt, located at ./data/.
						FileReader fReader = new FileReader(rFile);				// This object represents a stream of characters read from the file.
						BufferedReader bReader = new BufferedReader(fReader); 	// This object represents lines of Strings created from the stream of characters.
						String eachLine;
						eachLine = bReader.readLine().substring(5).trim();	// Start reading from index 14 (place 15)
						nCols = Integer.parseInt(eachLine);
						eachLine = bReader.readLine().substring(5).trim();
						nRows = Integer.parseInt(eachLine);
						eachLine = bReader.readLine().substring(9).trim();
						origin[0] = Double.parseDouble(eachLine);
						eachLine = bReader.readLine().substring(9).trim();
						origin[1] = Double.parseDouble(eachLine);
						eachLine = bReader.readLine().substring(8).trim();
						resolution = Integer.parseInt(eachLine);
						eachLine = bReader.readLine().substring(12).trim();
						nullValue = Double.parseDouble(eachLine);
						// ----- ----- READER - first 6 lines ----- -----


						// Creating a 2-dim matix (array of arrays)
						values = new double [nRows][nCols];

						/*
						 * // ----- ----- READER - values ----- ----- eachLine =
						 * bReader.readLine().replace(",", "."); // Reusing the string named temp, and
						 * replacing it with the first row of values. for (int l =0; l < nRows -1;l++) {
						 * String temptemp = bReader.readLine().replace(",", "."); eachLine = eachLine +
						 * temptemp; } String[] stringarray = eachLine.split(" "); // ----- ----- READER
						 * - values ----- -----
						 * 
						 * // ----- TEST: Content of stringarray ----- REMOVE LATER int r=(nRows - 1) *
						 * (nCols - 1); // r anv�nds f�r att g�ra printen under allm�n for (int m =0; m
						 * < r; m++) { System.out.println("Stringarray [" + m + "]: "+ stringarray[m]);
						 * } // ----- TEST: Content of stringarray -----
						 */

						for (int i=0; i < nRows; i++) {
							eachLine = bReader.readLine().replace(",", ".");
							String[] stringarray = eachLine.split(" ");
							for (int j = 0; j < nCols; j++) {
								//int k = i*nCols + j;
								values [i] [j] = Double.parseDouble(stringarray[j]); 
							}
						}
						bReader.close();

		} 
		catch (Exception e) {		// Exception
			System.out.println("Error");
			
			e.printStackTrace();
			
			System.exit(0);
		}
	}

	public Layer(String name, int nRows, int nCols, double[] origin, double resolution, double nullValue) {
        // construct a new layer by assigning a value to each of its attributes
        this.name= name;// on the left hand side are the attributes of
        this.nRows= nRows;// the new layer;
        this.nCols= nCols;
		this.origin= origin;
		this.resolution= resolution;
		this.nullValue=nullValue;
		this.values = new double [nRows][nCols];
		// on the right hand side are the parameters.// to be continued...}
	}
	// Methods
	// Print
	public void print(){

		//Print this layer to console
		System.out.println("ncols "+nCols);
		System.out.println("nrows "+nRows);
		System.out.println("xllcorner "+origin[0]);
		System.out.println("yllcorner "+origin[1]);
		System.out.println("cellsize "+resolution);
		System.out.println("NODATA_value " + nullValue);

		try{
			for (int i = 0; i < nRows; i++) { 
				for (int j = 0; j < nCols; j++) {
					System.out.print(values[i][j]+" "); // Re-worked for 2-dim array. 
				}
				System.out.println(); 
			}
		}
		catch(java.lang.NullPointerException e){
			System.out.println("No data to print");

		}


	}

	public void save(String outputFileName) {
		// save this layer as an ASCII file that can be imported to ArcGIS

		try {
			// This object represents an output file, out.txt, located at ./data/.
			File file = new File(outputFileName);
			// This object represents ASCII data (to be) stored in the file
			FileWriter fWriter = new FileWriter(file);
			// Write to the file
			fWriter.write("ncols         "+ nCols + "\n"); // "\n" represents a new line
			fWriter.write("nrows         "+ nRows + "\n"); 
			fWriter.write("xllcorner     "+ origin[0] + "\n");
			fWriter.write("yllcorner     "+ origin[1] + "\n");
			fWriter.write("cellsize      "+ resolution + "\n"); 
			fWriter.write("NODATA_value  "+ nullValue + "\n");

			for (int n = 0; n < nRows; n++) {
				for (int p = 0; p < nCols; p++) {
					fWriter.write(values [n] [p] + " ");
				}
				fWriter.write("\n");

			}
			fWriter.close();
		}

		catch (Exception e) {		// Exception
			System.out.println("Error");
			e.printStackTrace();
		}

	}
	
	private double getMax() {
		double max = Double.NEGATIVE_INFINITY;
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				if (values[i][j] > max) {
					max = values[i][j];
				}
			}
		}
		return max;
	}
	
	private double getMin() {
		double min = Double.POSITIVE_INFINITY;
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				if (values[i][j] < min) {
					min = values[i][j];
				}
			}
		}
		return min;
	}
	
	
	//BufferedImage
	public BufferedImage toImage() {
		// This object represents a 24-bit RBG image with a width of 20 pixels
		// (corresponding to the number of columns) and a height of 30 pixels
		// (corresponding to the number of rows).
		BufferedImage image = new BufferedImage(nCols, nRows, BufferedImage.TYPE_INT_RGB);

		WritableRaster raster = image.getRaster();
		
		double min = getMin();
		double max = getMax();
	
		for (int i = 0; i < nRows; i++) { 
			for (int j = 0; j < nCols; j++) {
				double [] color = new double[3];
				double range = max - min;
				double pixel_greyscale = (values[i][j] - min ) * 255 / range;
				color[0] = pixel_greyscale; // Red
				color[1] = pixel_greyscale; // Green
				color[2] = pixel_greyscale; // Blue
				raster.setPixel(j, i, color); // (19,0) is the pixel at the top-right corner.
			}
		}		
		return image;
	}
	

	//BufferedImage
	public BufferedImage toImage(double vois[]) {
		// This object represents a 24-bit RBG image with a width of 20 pixels
		// (corresponding to the number of columns) and a height of 30 pixels
		// (corresponding to the number of rows).
		BufferedImage image = new BufferedImage(nCols, nRows, BufferedImage.TYPE_INT_RGB);

		WritableRaster raster = image.getRaster();
		
		double min = getMin();
		double max = getMax();
		
		HashMap<Double, int[]> voi2color = new HashMap<Double, int[]>();
		for (int i=0; i < vois.length; i++) {
			int [] color = new int[3];
			int upper = 255;
			Random random = new Random();
			color[0] = random.nextInt(upper); // Red
			color[1] = random.nextInt(upper); // Green
			color[2] = random.nextInt(upper); // Blue
			voi2color.put(vois[i], color);
		}
		
		
		for (int i = 0; i < nRows; i++) { 
			for (int j = 0; j < nCols; j++) {
				for (int k = 0; k < vois.length; k++) {
					if (values[i][j] != vois[k]) {
						double [] color = new double[3];
						double range = max - min;
						double pixel_greyscale = (values[i][j] - min ) * 255 / range;
						color[0] = pixel_greyscale; // Red
						color[1] = pixel_greyscale; // Green
						color[2] = pixel_greyscale; // Blue
						
						raster.setPixel(j, i, color); // (19,0) is the pixel at the top-right corner.
					}
					else {
						raster.setPixel(j, i, voi2color.get(vois[k]));
						break;
					}
				}
			}
		}		
		return image;
	}

	/*public Layer localSum(Layer inLayer, String outLayerName){
		Layer outLayer = new Layer(outLayerName, nRows, nCols, origin,
		resolution, nullValue);

		// Check that resolution are the same.
		if(nRows != inLayer.nRows || nCols != inLayer.nCols || resolution != inLayer.resolution){
            System.out.println("Columns, Rows or resolution does not match");
            System.exit(0);
        }

		for (int i = 0; i < nRows; i++) { 
			for (int j = 0; j < nCols; j++) {
				outLayer.values[i][j] = values[i][j] + inLayer.values[i][j];
				
				// set cell in outlayer to nullvalue if any of the inlayers include nullvalue.
				if (values[i][j] == nullValue || inlayer.values[i][j] == nullValue)
					outLayer.values[i][j] = nullValue;
			}
		}
		return outLayer;
	}*/

	/*public Layer focalVariety(int r, boolean IsSquare, String outLayerName) {
		int nbh = new getNeighborhood(int i, int j, int r, boolean isSquare);

	}*/
	
	// Ska va private
	public int[][] getNeighborhood(int i, int j, int r, boolean isSquare) {
	
		int n = 4*r*(r+1)+1;

		int [][] nbh = new int[n+1][2]; //[ [i1][j1],[i2][j2] ]

		// Ta fram kvadrat. ---> Lägg till undantagsfall: EDGES <---
		for (int k = i-r; k <= i+r; k++) { 
			for (int l = j-r; l <= j+r; l++) {
				for (int m =0; m < 4*r*(r+1)+1; m++)
				
				// Om vi vill ha cirkel tar vi bort hörnen.
				if (isSquare == false) {
					if ((k-i)*(k-i) + (l-j)*(l-j) <= r){
						nbh[m][0] = k;
						nbh[m][1] = l;
					}
					else break;
				}
				else {
					nbh[m][0] = k;
					nbh[m][1] = l;
				}
			}
		}
		return nbh;
	}
	
}

