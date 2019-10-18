# myMath
Authors: Tal and Aric

General description: Our project consists of two main classes: Monom and Polynomial.

 polynomial contains objects from the Monom class using an array list.
 
the goal is to present different polynomials and perform basic mathematic functions, such as addition, subtraction, derivative, multiply and more complex operations such as finding the root of the function, compute the area above the X axis using the Riemann integral method.

Monom is defined as ax^b where "a" can be any real number and "b" can only be an Integer number non negative.

The Polynom will be sorted in the following manner. The Monoms with the higher power will be on the left side and those with the lower ones will be on the right side with Monoms whose coefficient is 0 will be deleted if they were created as a result of the department's activities or not added.


*In order to create a new polynomial, there are several ways: 
1. through a string describing the polynomial of the form ax^b+a1x^b1 for example: "4x^3+2x^2-3x ^1+7x^0"

2. creation of an empty polynomial and then its construction through the addition of monoms,
ie creating objects from the Monom class and adding them to the polynomial using the functions of the class.

3. Through deep copying we create a new polynomial object so that its values will be exactly the same values of another polynomial, and yet it will be another Polynom object, meaning that the addresses in the memory will be different and any action we take on one of the polynomials after copying will affect only it.

*In order to show visual any polynom we used Library "Knowm/xChart" for Example:
you need create Polynom and call the function "initGraph" with the exact range to show the graph and the Extreme points of the Polynom.
