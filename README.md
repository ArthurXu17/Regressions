# Regressions

The file named "MatrixReal" is code written in C++ designed to determine regressions for polynomials of a general degree n. This was a bonus assignment that I did for my AP Physics-C course.

I used vandermonde matrices to perform the regression. I ended up having the program output every matrix step for debugging purposes.

I extended on it in Java as I was able to use object oriented programming and methods to work with arrays of arbitrary sizes (I'm not yet familiar on how to use Vectors in C++ to have the same effect). In the Java program, I first created a Matrix class which contains the matrix operations that are needed for Vandermonde matrices (i.e matrix multiplication, inversion etc.). I decided to regress a general function, not just a polynomial. I also decided to find the coefficient of determination of my regressions.
