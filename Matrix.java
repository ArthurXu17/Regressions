import java.util.*;

public class Matrix {
	
	private int rows, columns;
	double [][] arr;
	
	public Matrix(double[] input) {//making the 1-d arrays a matrix -- needed so that we can use dot product with the x and y vectors
		rows = input.length;
		columns = 1;
		arr = new double[input.length][1];
		for(int i = 0; i < rows; i++) {
			arr[i][0] = input[i];
		}
	}
	
	public Matrix(double[][] input) {//converting 2-d arrays into matrix, simply just taking the input and matching the object array with it
		rows = input.length;
		columns = input[0].length;
		arr = new double[input.length][input[0].length];
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				arr[i][j] = input[i][j];
			}
		}
	}
	//getters
	public int getRows() {
		return rows;
	}
	
	public int getColumns() {
		return columns;
	}
	
	public double getValue(int r, int c) {
		return arr[r][c];
	}
	public double[][] getArr() {
		return arr;
	}
	
	public static double dotProduct(double[] x, double[] y) {
		double result=0;
		
		if(x.length == y.length) {
			
			for (int i = 0; i < x.length; i++) {
				result += x[i]*y[i];
			}
			return result;
		}
		else if (x.length<y.length){//in this case all the x vector's coordinates of dimension higher than itself become 0
			for (int i = 0; i < x.length; i++) {//the loop goes to the length of x
				result += x[i]*y[i];
			}
			return result;
		}
		else {//y is less than x
			for (int i = 0; i < y.length; i++) {//loop goes to length of y
				result += x[i]*y[i];
			}
			return result;
		}
		
	}
	public void print() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public double det() {
		if (rows == 2 && columns == 2) {
			return (arr[0][0]*arr[1][1]-arr[1][0]*arr[0][1]);//by definition
		}
		else if(rows > 2 && columns > 2 && rows == columns) {//for larger matrices we use the recursive definition
			double result=0;
			
			for(int i = 0; i < rows; i++) {
				double[][] temporary = new double[rows-1][columns-1];//the temporary array is the array that we visualize after we remove a vertical column
				
				//determining the 2-d array after we removed a column
				for (int j = 1; j < rows; j++) {
					for (int k = 0; k < columns; k++) {
						if (k < i) {
							temporary[j - 1][k] = arr[j][k];
						} else if (k > i) {
							temporary[j - 1][k - 1] = arr[j][k];
						}
					}
				}
				
				Matrix tempM = new Matrix(temporary);
				
				result+=arr[0][i]*Math.pow(-1, (double)i)*tempM.det();
			}
			
			return result;
			
		}
		else {//error code for when it's not square
			return 99999;
		}
	}
	
	public Matrix multiply (Matrix arr2) {
		double [][] result = new double[rows][arr2.getColumns()];//we know the outcome from matrix math
		for(int i = 0; i < rows;i++) {
			for(int j = 0; j < arr2.getColumns();j++) {
				
				double[] rowarr1 = new double[columns];//tracking the rows of the first matrix
				double[] colarr2 = new double[arr2.getRows()];//tracking the columns of the second matrix
				
				for(int k = 0; k < columns;k++) {//going through a given row in the first matrix
					rowarr1[k] = arr[i][k];
				}
				
				for(int l = 0; l < arr2.getRows();l++) {//going through a given column in the second matrix
					colarr2[l] = arr2.getValue(l, j);
				}
				
				result[i][j] = dotProduct(rowarr1,colarr2);//dot producting the 2
			}
		}
		
		Matrix resultMatrix = new Matrix(result);//converting to Matrix
		
		return resultMatrix;
		
	}
	
	public Matrix vandermondeTranspose() {
		double[][] result = new double[columns][rows];
		
		for(int i = 0; i < result.length; i++) {//simply swapping rows and columns
			for (int j = 0; j < result[i].length;j++) {
				result[i][j] = arr[j][i];
			}
		}
		
		Matrix resultMatrix = new Matrix(result);
		return resultMatrix;
	}
	
	public Matrix invert() {
		double[][] aug = new double[rows][2*columns];
		Matrix test = new Matrix(aug);
		double[][] inverse = new double[rows][columns];
		
		
		if(rows != columns) {
			inverse = new double[1][1];
			inverse[0][0] = 48749323;
		}
		else if (this.det() == 0) {
			inverse = new double[1][1];
			inverse[0][0] = 696969;
		}
		else {
			
			
			//Creating the augmented matrix
			for(int i = 0; i < rows;i++) {
				for(int j = 0; j < columns;j++) {
					aug[i][j] = arr[i][j];//same stuff
					
					if (i != j) {//adding identity matrix
						aug[i][j+rows] = 0;
					}
					else {
						aug[i][j+rows] = 1;
					}
					
				}
			}
			
			
			//going through each row and clearing out
			
			for(int i = 0; i < rows; i++) {//Note that the counter i is the row that we're using as the pivot

				//switching in case the intended pivot is 0
				int switchcounter = i+1;
				while(aug[i][i] == 0) {
					double[][] temp = new double[aug.length][aug[0].length];
					for(int k = 0; k < aug.length; k++) {
						for(int l = 0; l < aug[k].length;l++) {
							temp[k][l] = aug[k][l];
						}
					}
					
					if(temp[switchcounter] [i]!= 0) {
						for(int s = 0; s < 2*columns;s++) {
							aug[i][s] = temp[switchcounter][s];
							aug[switchcounter][s] = temp[i][s];
						}
						
					}
					else {
						switchcounter++;
					}
				}
				
				//first goes through the rows and divides the entire row by the "pivot"
				double pivot = aug[i][i];
				for(int j = 0; j < 2*columns; j++) {
					aug[i][j] = aug[i][j]/pivot;
				}

				//subtracting the pivot row away from each row that is not the pivot row
				for(int k = 0; k < rows; k++) {
					
					if(k == i) {
						//do nothing if the row is the pivot row
					}
					else {
						double constant = aug[k][i];
						for(int l = 0; l < 2*columns; l++) {
							aug[k][l]-=constant*aug[i][l];//subtracting each row by the pivot row multiplied by an appropriate constant - we can do this because we made sure that the pivot row has 1
						}
					}
				}
			}
			//getting the inverse matrix based on the augmented
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < columns; j++) {
					inverse[i][j] = aug[i][j+columns];
				}
			}
			
		}
		
		Matrix resultmatrix = new Matrix(inverse);
		
		return resultmatrix;
		
	}
}
