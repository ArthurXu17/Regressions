#include <iostream>
#include <cmath>

using namespace std;

double dotProduct(double x[], double y[], int xlength, int ylength) {//method for dot product of 2 arrays used for multiplying matrices
		double result=0;
		
		if(xlength == ylength) {
			
			for (int i = 0; i < xlength; i++) {//sum up the products of corresponding index
				result += x[i]*y[i];
			}
			return result;
		}
		else {//for the sake of this application, we will assume that the inputs should be of same length, this is simply an error code for bug checking
			return 999999;
		}
		
	}

int main(){
    
    int n, p;//n is number of values, p is polynomial degree plus1
    
    //get values in
    cout<<"How many x/y values?";
    cin>>n;
    cout<<"What is the degree of the polynomial?";
    cin>>p;

    double x[n];
    double y[n];

    //x array input
    for(int i = 0; i < n; i++){
        cout<<"Enter the "<<i+1<<"th x value";
        cin>>x[i];
    }
    //y array input
    for(int i = 0; i < n; i++){
        cout<<"Enter the "<<i+1<<"th y value";
        cin>>y[i];
    }
//******************************Displaying Input Values*****************************

    cout<<"x values\n\n";
    for(int i = 0; i < n; i++){
        cout<<x[i]<<"\n";
    }
    cout<<"\ny values\n\n";
    for(int i = 0; i < n; i++){
        cout<<y[i]<<"\n";
    }
    cout<<"\n";
//***********************************Vandermonde Matrix*****************************

    //constructing vandermonde matrix
    double V[n][p+1];//degree of p has p+1 terms

    for(int j = 0; j < n;j++) {//raises each x value to the appropriate powers
		for(int i = p; i >=0; i--) {
			V[j][p-i] = std::pow(x[j], (double)i);//going through each row and raising x_j to the power starting from p and going to 0
		}
	}

    //outputing vandermonde matrix

    cout<<"Vandermonde Matrix\n\n";

    for(int i = 0; i < n; i++){//going through each index and printing out values, putting spaces and indents where necessary
        for(int j = 0; j <= p; j++){
            cout<<V[i][j]<<" ";
        }
        cout<<"\n\n";
    }
//********************************Vandermonde Transpose*****************************

    //constructing tranpose matrix
    double Vt[p+1][n];//opposite dimensions of vandermonde matrix

    for(int i = 0; i < p+1; i++) {
			for (int j = 0; j < n;j++) {
				Vt[i][j] = V[j][i];//the coordinates are simply flipped
			}
		}

    //outputing vandermonde transpose matrix

    cout<<"Vandermonde Transpose Matrix\n\n";

    for(int i = 0; i < p+1; i++){//going through each index and printing out values, putting spaces and indents where necessary
        for(int j = 0; j < n; j++){
            cout<<Vt[i][j]<<" ";
        }
        cout<<"\n\n";
    }

//****************************Calculating and outputting VtV************************

    //constructing product matrix

    double VtV[p+1][p+1];//If we do the math, VtV will be a square matrix whose size is based on the polynomial

    for(int i = 0; i < p+1;i++) {
		for(int j = 0; j < p+1;j++) {
				
			double rowarrVt[n];//these will be used to track the rows within Vt--> which have length of n
			double colarrV[n];//these will be used to track the columns within V --> which have length of n
				
			for(int k = 0; k < n;k++) {//going through a given row in Vt and putting those values in rowarr
				rowarrVt[k] = Vt[i][k];
			}
				
			for(int l = 0; l < n;l++) {//going through a given column in V and putting those in colarr
				colarrV[l] = V[l][j];
			}

            //the value of the product matrix at i,j is dot product of row i in array1 and column j in array2
				
			VtV[i][j] = dotProduct(rowarrVt,colarrV,n,n);//find the dot product of those 2 arrays to find the value of each element in the product array
		}
			
	}

    //outputing product matrix

    cout<<"VtV\n\n";

    for(int i = 0; i < p+1; i++){//going through each index and printing out values, putting spaces and indents where necessary
        for(int j = 0; j < p+1; j++){
            cout<<VtV[i][j]<<" ";
        }
        cout<<"\n\n";
    }
//************************************VtV Inverse***********************************

    //note: when computing the inverse, we will assume that VtV's determinant isn't 0
    double VtVinverse [p+1][p+1];//the resulting matrix -- inverting keeps the same dimensions
    double aug[p+1][2*p+2];//the augmented matrix (columns are doubled)

			
			
	//Creating the augmented matrix
	for(int i = 0; i < p+1;i++) {
		for(int j = 0; j < p+1;j++) {
			aug[i][j] = VtV[i][j];//same stuff
				
			if (i != j) {//adding identity matrix
				aug[i][j+p+1] = 0;
			}
			else {
				aug[i][j+p+1] = 1;
			}
				
		}
	}

    /*for this application, we know that the matrix will almost never have a determinent of 0,
     so we can ignore that scenario*/
			
			
	//going through each row and actually reducing the matrix
			
	for(int i = 0; i < p+1; i++) {//Note that the counter i is the row that we're using as the pivot

		//switching in case the intended pivot is 0
		int switchcounter = i+1;// if switching is intended, we start at the row 1 below the pivot row
		while(aug[i][i] == 0) {//if the intended pivot is 0
			double temp [p+1][2*p+2];//temporary array with same values and dimensions as augmented array
			for(int k = 0; k < 2*p+2; k++) {
				for(int l = 0; l < p+1;l++) {
					temp[k][l] = aug[k][l];
				}
			}
					
			if(temp[switchcounter] [i]!= 0) {//if we are successful in finding another non-zero pivot, switch the 2 rows by using the temporary array
				for(int s = 0; s < 2*p+2;s++) {
					aug[i][s] = temp[switchcounter][s];
					aug[switchcounter][s] = temp[i][s];
				}				
			}
			else {//if not, keep on moving down the array
				switchcounter++;
			}
		}
			
		//first goes through the pivot row and divides the entire row by the "pivot", this ensures that the pivot becomes 1
		double pivot = aug[i][i];
		for(int j = 0; j < 2*p+2; j++) {
			aug[i][j] = aug[i][j]/pivot;
		}
					
		//subtracting the pivot row away from each row that is not the pivot row
		for(int k = 0; k < p+1; k++) {
					
			if(k == i) {
				//do nothing if the row is the pivot row
			}
			else {
				double constant = aug[k][i];
				for(int l = 0; l < 2*p+2; l++) {
					aug[k][l]-=constant*aug[i][l];//subtracting each row by the pivot row multiplied by the appropriate constant - we can do this because we made sure that the pivot row has 1
				}
			}
		}
				
	}
			
    //getting the inverse matrix based on the augmented
	for (int i = 0; i < p+1; i++) {
		for (int j = 0; j < p+1; j++) {
			VtVinverse[i][j] = aug[i][j+p+1];//we simply add the length of the original matrix(VtV) to the column
		}
	}

    //outputing VtVInverse

    cout<<"VtVinverse\n\n";

    for(int i = 0; i < p+1; i++){//going through each index and printing out values, putting spaces and indents where necessary
        for(int j = 0; j < p+1; j++){
            cout<<VtVinverse[i][j]<<" ";
        }
        cout<<"\n\n";
    }
			
	
//*************************Finding the product of VtVinverse and Vt*****************

    //constructing product matrix

    double VtVinverseVt[p+1][n];//Vtvinverse is (p+1 x p+1), Vt is (p+1 x n); therefore VtvinverseVt is (p+1 x n)

    for(int i = 0; i < p+1;i++) {
		for(int j = 0; j < n;j++) {
				
			double rowarrVtVinverse[p+1];//these will be used to track the rows within VtVinverse --> which have length of p+1
			double colarrVt[p+1];//these will be used to track the columns within Vt --> which have length of p+1
				
			for(int k = 0; k < p+1;k++) {//going through a given row in VtVinverse and putting those values in rowarr
				rowarrVtVinverse[k] = VtVinverse[i][k];
			}
				
			for(int l = 0; l < p+1;l++) {//going through a given column in Vt and putting those in colarr
				colarrVt[l] = Vt[l][j];
			}

            //the value of the product matrix at i,j is dot product of row i in array1 and column j in array2
				
			VtVinverseVt[i][j] = dotProduct(rowarrVtVinverse,colarrVt,p+1,p+1);//find the dot product of those 2 arrays to find the value of the product array
		}
			
	}

    //outputing product matrix

    cout<<"(VtV-1)Vt\n\n";

    for(int i = 0; i < p+1; i++){//going through each index and printing out values, putting spaces and indents where necessary
        for(int j = 0; j < n; j++){
            cout<<VtVinverseVt[i][j]<<" ";
        }
        cout<<"\n\n";
    }

//*****************************Finding the product of Vtv-1Vt and y*****************

    double coefficients[p+1];

    for(int i = 0; i < p+1; i++){

        double rowarrVtVinverseVt[n];//these will be used to track the rows within (VtV-1)(Vt) --> which have length of n

        for(int k = 0; k < n; k++){//going through a given row in VtV-1Vt and putting those values in rowarr
            rowarrVtVinverseVt[k] = VtVinverseVt[i][k]; 
        }

        //since the y values is a matrix with only 1 column, we find the dot product of every row and the yvalue arrays

        coefficients[i] = dotProduct(rowarrVtVinverseVt,y,n,n);
        
    }

   //outputing product matrix

    cout<<"coefficients = (VtV-1)(Vt)(y)\n\n";

    for(int i = 0; i < p+1; i++){//going through each index and printing out values, putting spaces and indents where necessary
        cout<<coefficients[i];
        cout<<"\n\n";
    } 

//*************************************Displaying the equation**********************

    cout<<"Equation: y=";
    for(int i = 0; i <= p; i++){
        if (i!=p){
            cout<<"("<<coefficients[i]<<")"<<"x^"<<p-i<<"+";
        }
        else{
            cout<<"("<<coefficients[i]<<")";
        }
        
    }

    cin.get();
}