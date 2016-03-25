package perceptron;

import java.io.FileReader;
import java.io.IOException;

import com.opencsv.CSVReader;

import perceptron.Neuron;

public class TestNeuronFromFile {
	public static String FILENAME = "./data/testdata.txt";
	
	public static void main(String[] args) throws IOException {
		double[][] x; // = {{1.0, 0.0, 0.0}, {1.0, 0.0, 1.0}, {1.0, 1.0, 0.0}, {1.0, 1.0, 1.0}};
		double[] y; // = {1.0, 1.0, 1.0, 0.0};
		
		// Initialize 
        int numPoints = readNRows(FILENAME);
        int numDims = readNColumns(FILENAME) - 1;
		System.out.println(String.format("numPoints = %d", numPoints));
		System.out.println(String.format("numDims = %d", numDims));
        x = new double[numPoints][numDims]; // rows x cols #TODO does this take
        y = new double[numPoints];
        
        
        // Load file
        CSVReader reader = new CSVReader(new FileReader(FILENAME));
        String [] nextLine;
        for(int k=0; k<numPoints; k++) {
    		System.out.print("for point ");
    		System.out.println(k);
        	nextLine = reader.readNext();
        	for(int i=0; i<numDims; i++) {
        		System.out.println(String.format("    x[%d][%d]=%s", k, i, nextLine[i]));
        		x[k][i] = Float.parseFloat(nextLine[i]);
        	}
            y[k] = Float.parseFloat(nextLine[numDims]);
    		System.out.println(String.format("    y[%d]=%s", k, nextLine[numDims]));
        }
        reader.close();
        
        System.out.println("Loaded data.");

		Neuron neuron = new Neuron(x, y);
		
		double accuracy = neuron.train(100, .02);
		System.out.print("accuracy on training set: ");
		System.out.println(accuracy);
		System.out.println();
		
		double[] test = {0.3, 0.3, 0.3}; 
		double classifiedAs = neuron.apply(test);
		System.out.print("classifiedAs: ");
		System.out.println(classifiedAs);
	}
	
	protected static int readNRows(String filename) throws IOException{
		CSVReader reader = new CSVReader(new FileReader(filename));
		int n = 0;
		while(reader.readNext() != null){
			n++;
		}
		reader.close();
		return n;
	}
    
	protected static int readNColumns(String filename) throws IOException{
		CSVReader reader = new CSVReader(new FileReader(filename));
		String [] line = reader.readNext();
		reader.close();
		int n = line.length;
		return n;
	}
	
}
