import java.util.*;
public class Regression {
//*********************************************User input methods***********************************************
	public static double[] getx(int n) {
		Scanner sc = new Scanner(System.in);
		double[] valuesarr = new double[n];
		for(int i = 1; i <= n; i++) {
			System.out.println("Enter in the "+i+"th x value");
			double value = sc.nextDouble();
			valuesarr[i-1] = value;
		}
		return valuesarr;
	}
	public static double[] gety(int n) {
		Scanner sc = new Scanner(System.in);
		double[] valuesarr = new double[n];
		for(int i = 1; i <= n; i++) {
			System.out.println("Enter in the "+i+"th y value");
			double value = sc.nextDouble();
			valuesarr[i-1] = value;
		}
		return valuesarr;
	}
//*********************************************************methods used for regression*********************************
	public static String[] regressionForm() {
		
		Scanner sc = new Scanner(System.in);
		int arrsize = 0;
		String[] regressionForm = new String[arrsize];
		
		int choicepoly;
		System.out.println("Do you want to regress a polynomial. If yes, enter in the degree of the polynomial. If not enter 0");
		choicepoly = sc.nextInt();//picking poly or not poly
		
		if(choicepoly == 0) {
			ArrayList<String> formArrayList = new ArrayList<String>();//using array list for easier adding function, converted to array later
			String termchoice="";//term choice must be declared outside the loop to allow "stop" to end the do while loop
			do {
				System.out.println("Enter in the next term");
				System.out.println("The options are: pow, exp, log, sin, cos, tan, cons. Enter 'stop' if you want no more terms.");
				termchoice = sc.next();
				if(termchoice.equals("pow")) {//form x^n
					double powerchoice;
					System.out.println("What do you want the power to be (it doesn't have to be an integer)");
					powerchoice = sc.nextDouble();
					String term = "pow,"+powerchoice;//choice for n
					formArrayList.add(term);
				}else if(termchoice.equals("exp")) {//form n^x
					double basechoice;
					System.out.println("What do you want the base to be (it doesn't have to be an integer). If you would like the base to be Euler's number, enter 0");
					basechoice = sc.nextDouble();
					String term = "exp,"+basechoice;//choice for n
					formArrayList.add(term);
				}else if(termchoice.equals("log")) {//form log_n(x)
					double logbasechoice;
					System.out.println("What do you want the logarithm base to be (it doesn't have to be an integer). If you would like the natural logarithm, enter 0");
					logbasechoice = sc.nextDouble();
					String term = "log,"+logbasechoice;//choice of n
					formArrayList.add(term);
				}else if(termchoice.equals("sin")) {
					String term = "sin,";
					formArrayList.add(term);
				}else if(termchoice.equals("cos")) {
					String term = "cos,";
					formArrayList.add(term);
				}else if(termchoice.equals("tan")) {
					String term = "tan,";
					formArrayList.add(term);
				}else if(termchoice.equals("cons")) {
					String term = "cons,";
					formArrayList.add(term);
				}else if(termchoice.equals("stop")) {
					
				}else{
					System.out.println("The text input was invalid");
				}
				
			}while(!termchoice.equals("stop"));//end the loop when stop is selcted
			
			regressionForm = new String[formArrayList.size()];
			for(int i = 0; i < formArrayList.size(); i++) {
				regressionForm[i] = formArrayList.get(i);
			}
		}
		else if (choicepoly >=1){
			arrsize = choicepoly+1;//size becomes 1+the polynomial degree. eg x^3 has x^0+x^1+x^2+x^3
			regressionForm = new String[arrsize];
			for(int i = 0; i <= choicepoly ;i++) {
				regressionForm[i] = "pow,"+(choicepoly-i);//in general polynomial of degree n, each term will go from x^0 to x^n
			}
		}
		return regressionForm;
	}

	public static double[][] getVandermonde(String[] regressionForm, double[] x) {
		double[][] vandermonde = new double[x.length][regressionForm.length];
		
		for (int i = 0; i < regressionForm.length;i++) {//going across the top of the vandermonde matrix
			
			String[] temp = regressionForm[i].split(",");//split at the comma so i can differentiate between the type of term and any specifics (i.e bases and what not)
			if(temp[0].equals("pow")) {
				for(int j = 0; j < x.length;j++) {
					vandermonde[j][i] = Math.pow(x[j], Double.parseDouble(temp[1]));
				}
			}else if(temp[0].equals("exp")) {
				for(int j = 0; j < x.length;j++) {
					if(Double.parseDouble(temp[1]) == 0.0) {//if it's 0, we want the base to be e
						vandermonde[j][i] = Math.pow(Math.E, x[j]);
					}
					else {
						vandermonde[j][i] = Math.pow(Double.parseDouble(temp[1]), x[j]);
					}
					
				}
			}else if(temp[0].equals("log")) {
				for(int j = 0; j < x.length;j++) {
					if(Double.parseDouble(temp[1]) == 0) {//if it's 0, we want the base to be e
						vandermonde[j][i] = Math.log(x[j]);//we can then directly use the ln function in java
					}
					else {
						vandermonde[j][i] = Math.log(x[j])/Math.log(Double.parseDouble(temp[1]));//log_{n}(x) = ln(x)/ln(n)
					}
					
				}
			}else if(temp[0].equals("sin")) {
				for(int j = 0; j < x.length;j++) {
					vandermonde[j][i] = Math.sin(x[j]);
				}
			}else if(temp[0].equals("cos")) {
				for(int j = 0; j < x.length;j++) {
					vandermonde[j][i] = Math.cos(x[j]);
				}
			}else if(temp[0].equals("tan")) {
				for(int j = 0; j < x.length;j++) {
					vandermonde[j][i] = Math.tan(x[j]);
				}
			}else if(temp[0].equals("cons")) {
				for(int j = 0; j < x.length;j++) {
					vandermonde[j][i] = 1;
				}
			}
		
		}
		return vandermonde;
	}
//***************************************methods for r^2******************************************************
	
	public static double mean(double[] arr) {
		double sum = 0;
		int n = arr.length;
		double nd = (double)n;//typecasting for division
		for(int i = 0; i < n; i++) {
			sum+=arr[i];
		}
		return sum/nd;
	}
	
	//sum of total squares
	public static double sst(double[] arr) {
		double sum = 0;
		double mean = mean(arr);
		for(int i = 0; i < arr.length; i++) {
			sum += Math.pow(arr[i] - mean, 2);
		}
		return sum;
	}
	
	//sum squared regression
	public static double ssr(double[] xvals, double[] yvals, double[] regCoefficients,String[] regEquation) {
		double sum = 0;
		for(int i = 0; i < xvals.length; i++) {//finding the residual squared for each data point x,y
			double realValue = yvals[i];
			double regValue = 0;
			//finding the regressed value for each x_i
			for(int j = 0 ; j < regCoefficients.length; j++) {//summing up the cf(x)'s
				String[] temp = regEquation[j].split(",");
				if(temp[0].equals("pow")) {
					regValue += regCoefficients[j]*Math.pow(xvals[i], Double.parseDouble(temp[1]));
				}else if(temp[0].equals("exp")) {
					if(Double.parseDouble(temp[1]) == 0.0) {//if it's 0, we want the base to be e
						regValue += regCoefficients[j]*Math.pow(Math.E, xvals[i]);
					}
					else {//otherwise base is the temp[1]
						regValue += regCoefficients[j]*Math.pow(Double.parseDouble(temp[1]), xvals[i]);
					}
				}else if(temp[0].equals("log")) {
					if(Double.parseDouble(temp[1]) == 0.0) {//if it's 0, we want the base to be e
						regValue+=regCoefficients[j]*Math.log(xvals[i]);
					}
					else {
						regValue+=regCoefficients[j]*Math.log(xvals[i])/Math.log(Double.parseDouble(temp[1]));
					}
				}else if(temp[0].equals("sin")) {
					regValue += regCoefficients[j]*Math.sin(xvals[i]);
				}else if(temp[0].equals("cos")) {
					regValue += regCoefficients[j]*Math.cos(xvals[i]);
				}else if(temp[0].equals("tan")) {
					regValue += regCoefficients[j]*Math.tan(xvals[i]);
				}else if(temp[0].equals("cons")) {
					regValue += regCoefficients[j];
				}
			}
			//determining the residual
			double residual = realValue - regValue;
			sum+=Math.pow(residual, 2);//adding the residual squared
		}
		return sum;
	}

//**************************************************main******************************************************
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		//user input and interpreting the information
		System.out.println("How many x,y pairs are there?");
		int n = sc.nextInt();
		
		double[] xvals = getx(n);
		double[] yvals = gety(n);
		
		String[] regForm = regressionForm();
		for(int i = 0; i < regForm.length;i++) {
			System.out.println(regForm[i]);
		}
		double[][] vandermonde = getVandermonde(regForm, xvals);
		//performing regression
		Matrix xm = new Matrix(xvals);
		Matrix ym = new Matrix(yvals);
		Matrix V = new Matrix(vandermonde);
		Matrix Vt = V.vandermondeTranspose();
		Matrix VtV = Vt.multiply(V);
		Matrix VtVinverse = VtV.invert();
		Matrix VtVinverseVt = VtVinverse.multiply(Vt);
		Matrix ans1 = VtVinverse.multiply(Vt).multiply(ym);
		xm.print();
		ym.print();
		System.out.println("V");
		V.print();
		System.out.println("Vt");
		Vt.print();
		System.out.println("(Vt)(V)");
		VtV.print();
		System.out.println("(VtV)-1");
		VtVinverse.print();
		System.out.println("([VtV]^-1)Vt");
		VtVinverseVt.print();
		System.out.println("{([VtV]^-1)(Vt)}y");
		ans1.print();
		
		//printing out the regressed equation
		System.out.println("Thus, the regressed equation is:");
		for(int i = 0; i < regForm.length;i++) {
			System.out.print("("+ans1.getValue(i, 0)+")");//first print out the coefficient determined using the regression
			String[] temp = regForm[i].split(",");
			//afterwards, follow it up with the proper function
			if(temp[0].equals("pow")) {
				System.out.print("x^("+temp[1]+")");
			}else if(temp[0].equals("exp")) {
				if(Double.parseDouble(temp[1]) == 0.0) {//if it's 0, we want the base to be e
					System.out.print("e^(x)");
				}
				else {
					System.out.print(temp[1]+"^(x)");
				}
			}else if(temp[0].equals("log")) {
				if(Double.parseDouble(temp[1]) == 0.0) {//if it's 0, we want the base to be e
					System.out.print("ln(x)");
				}
				else {
					System.out.print("log_(" + temp[1] + ")(x)");
				}
			}else if(temp[0].equals("sin")) {
				System.out.print("sin(x)");
			}else if(temp[0].equals("cos")) {
				System.out.print("cos(x)");
			}else if(temp[0].equals("tan")) {
				System.out.print("tan(x)");
			}else if(temp[0].equals("cons")) {
				
			}
			
			if(i != regForm.length-1) {//add a plus if it isn't the last term
				System.out.print(" + ");
			}
		}
		
		//determining r^2
		double[][] regcoeff2d = ans1.getArr();
		double[] regcoeff1d = new double[regcoeff2d.length];
		for(int i = 0; i < regcoeff2d.length;i++) {
			regcoeff1d[i] = regcoeff2d[i][0];
		}
		double r2 = 1-ssr(xvals,yvals,regcoeff1d,regForm)/sst(yvals);
		System.out.println("\nThe coefficient of determination (R^2) is: " + r2);
	}

}
