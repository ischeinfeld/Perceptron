package perceptron;

import perceptron.Neuron;

public class TestNeuron {
	public static String FILENAME = "./data/testdata.txt";
	
	public static void main(String[] args) {
		double[][] x = {{1.0, 0.0, 0.0}, {1.0, 0.0, 1.0}, {1.0, 1.0, 0.0}, {1.0, 1.0, 1.0}};
		double[] y = {1.0, 1.0, 1.0, 0.0};

		Neuron neuron = new Neuron(x, y);
		
		double accuracy = neuron.train(100, .02);
		System.out.print("accuracy on training set: ");
		System.out.println(accuracy);
		System.out.println();
		
		double[] test = {1.0, 1.0, 1.0}; 
		double classifiedAs = neuron.apply(test);
		System.out.print("classifiedAs: ");
		System.out.println(classifiedAs);
	}
}
