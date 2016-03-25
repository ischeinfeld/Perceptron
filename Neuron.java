package perceptron;

import Jama.Matrix;
import util.MatrixUtil;

public class Neuron {
	
	private int numThetas; // number of features/thetas
	private int numPoints; // number of input sets
	private Matrix X; // data (including 1s column)
	private Matrix Y; // labels (column vector)
	private Matrix T; // thetas (column vector)
	
	
	public Neuron(double[][] x, double[] y) { // array of x and y
		System.out.println("CONSTRUCTING...");
		
		// construct x (input) matrix
		X = new Matrix(x);
		X = MatrixUtil.addInitialOnes(X);
		
		// construct y column vector
		Y = new Matrix(y, 1);
		Y = Y.transpose();
		
		numPoints = X.getRowDimension();
		numThetas = X.getColumnDimension(); // takes into account initial 1s, not yet feature mapping
		
		// initialize t (weight) column vector
		T = new Matrix(numThetas, 1); // initializes thetas as 0s
		
		System.out.println(String.format("X is %d cols by %d rows", X.getColumnDimension(), X.getRowDimension()));
		X.print(5, 2);
		System.out.println(String.format("Y is %d cols by %d rows", Y.getColumnDimension(), Y.getRowDimension()));
		Y.print(5, 2);
		System.out.println(String.format("T initial is %d cols by %d rows", T.getColumnDimension(), T.getRowDimension()));
		T.print(5, 2);
		System.out.println("CONSTRUCTED\n");
	}
	
	
	public double train(int maxIter, double learningRate) {
		System.out.println("TRAINING...");
		int numErrors = Integer.MAX_VALUE;
		for(int i=0; (i < maxIter) && (numErrors != 0); i++) {
			numErrors = 0;
			System.out.println(String.format("Iteration #%d ---------------------------------", i+1));
			for(int pointNum=0; pointNum<numPoints; pointNum++) {
				Matrix x = X.getMatrix(pointNum, pointNum, 0, numThetas-1); // row vector of one input set
				double y = Y.getMatrix(pointNum, pointNum, 0, 0).get(0, 0); // double value of 1x1 matrix of expected y
				System.out.print("For xs");
				x.print(5, 1);
				
				double result = result(x, T);
				double error = y - result;
				
				if(error != 0) numErrors++;
				
				T = T.plus(x.transpose().times(error*learningRate));
				System.out.print(" new thetas");
				T.print(5,  10);
			}
		}
		
		double accuracy = (numPoints - numErrors) / numPoints;
		
		System.out.println("TRAINED\n");
		return accuracy;
	}
	
	
	public double apply(double[] inputs) {
		System.out.println("APPLYING...");
		
		Matrix x = new Matrix(inputs, 1);
		x = MatrixUtil.addInitialOnes(x);
		double result = result(x, T);
		
		System.out.println("APPLIED\n");
		return result;
	}
	
	
	private double result(Matrix x, Matrix t) {
		Matrix result = x.times(t); // 1x1 matrix of result
		double rawResult = result.get(0, 0); // double value of result
		return unitStep(rawResult); // return unit step
	}
	
	
	private double unitStep(double x) {
		if (x < 0) {
			return 0.0;
		} else {
			return 1.0;
		}
	}
}