package myMath;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;


import myMath.Monom;
/**
 * This class represents a Polynom with add, multiply functionality, it also should support the following:
 * 1. Riemann's Integral: https://en.wikipedia.org/wiki/Riemann_integral
 * 2. Finding a numerical value between two values (currently support root only f(x)=0).
 * 3. Derivative
 * 
 * @author Tal and Aric
 *
 */
public class Polynom implements Polynom_able{

	// ********** add your code below ***********
	private ArrayList<Monom> monoms;

	/**
	 * Init zero polynom - Create a new  empty Polynom  
	 * 
	 */
	public Polynom () {

		monoms=new ArrayList<Monom>();
	}

	/**
	 * Init copy - Create a new Polynom with the same  monoms of the other polynom 
	 * @param p1 the Polynom that we want to copy
	 * @return the copy Polynom of p1
	 */
	public Polynom(Polynom p1) { //copy constructor

		monoms=new ArrayList<Monom>(); //create collection
		Iterator<Monom> it2 = p1.iteretor();
		while(it2.hasNext()) {
			Monom pointer = it2.next();
			Monom temp=new Monom(pointer); //call the copy constructor of Monom class
			add(temp);
		}
	}

	/**
	 * Init(String) - Create Polynom from this string in this shape: ax^b+c
	 * @param str is String of Polynom (equation)
	 * @return Polynom 
	 */
	public Polynom(String str) {

		if (str.length()==0) {

			throw new RuntimeException("Error: please enter string:ax^b+...");
		}
		monoms=new ArrayList<Monom>();
		int i=0;
		int start=0;
		String temp="";
		boolean first=true;

		while(i<str.length()) {

			if (str.charAt(start)=='-') {

				if (first) { //if we are in the first element so we need to convert the co to negative and dont ignore the '-'

					i++;
				}

				temp="-"; //in order to convert to negative
			}
			while(i<str.length()&& str.charAt(i)!='+' && str.charAt(i)!='-') {

				temp=temp+str.charAt(i);
				i++;
			}
			Monom m =new Monom(temp);
			add(m);
			start=i; //to know the sign of the number
			i++; // continue to the next monom
			temp="";
			first=false; //know we pass the first element
		}
	}

	/**
	 * Remove all the Monoms with coefficient 0
	 */
	protected void removeZero ()
	{
		Iterator<Monom> it = monoms.iterator();
		Monom pointer = null;
		boolean isToDeletePrev = false;

		while(it.hasNext())
		{
			if(isToDeletePrev)
			{
				it.remove();
				isToDeletePrev = false;
			}

			pointer = it.next();

			if(pointer.get_coefficient()==0)
			{
				isToDeletePrev = true;
			}
		}
		if(isToDeletePrev)
		{
			it.remove();
			isToDeletePrev = false;
		}
	}

	/**
	 * Sort the Polynom according to the power value
	 */
	protected void sort()	{

		Comparator<Monom> cmpByPower = new Monom_Comperator();
		monoms.sort(cmpByPower);
	}

	/**
	 * Multiply this Polynom by Monom
	 * @param m1 is other Monom
	 */
	protected void multiply(Monom m1) { //assist multiply the Polynom with the Monom m1
		Iterator<Monom> it = monoms.iterator();
		while(it.hasNext()) //if we still in the collection
		{
			Monom pointer = it.next();
			pointer.multiply(m1);
		}
	}

	/**
	 * Multiply this Polynom by Polynom
	 * @param p1 is other Polynom
	 */
	@Override
	public void multiply(Polynom_able p1) {

		Polynom_able p2;
		p2=copy();
		monoms.removeAll(monoms);
		Iterator<Monom> it2 = p1.iteretor();
		while(it2.hasNext()) //if we still in the collection
		{

			Monom pointer = it2.next();
			Polynom_able tempPolynom=monomMultPoly(pointer,p2); //tempPolynom=Polynom*Monom
			add(tempPolynom);
		}
	}

	/**
	 * Multiply p1 Polynom by Monom
	 * @param m1 is other Monom
	 * @param p1 is other Polynom
	 * @return Polynom_able after Multiply
	 */
	//return Polynom_able and don't change the current value because of multiply2(Monom)
	protected Polynom_able monomMultPoly(Monom m1,Polynom_able p1) {

		Polynom_able p=new Polynom(); //add new polynom able
		Iterator<Monom> it2 = p1.iteretor();
		while(it2.hasNext()) //if we still in the collection
		{
			Monom pointer = it2.next();
			Monom temp=pointer.multiply(m1); //use multiply2 from Monom class (Monom*Monom)
			p.add(temp); //add the new Monom to polynom_able p

		}
		return p; //return Polynom = p1*m1
	}

	/**
	 * Add Monom to this Polynom
	 * @param m1 is other Monom
	 */
	@Override
	public void add(Monom m1) {

		if (m1.get_coefficient()==0) {

			return;
		}
		boolean isAdded=false;
		//need to check if i can add this m1 to another Monom
		Iterator<Monom> it = monoms.iterator();
		while(it.hasNext()) //if we still in the collection
		{
			Monom pointer = it.next();
			isAdded=pointer.add(m1); //try to add to exciting Monom into arraylist
			if (isAdded) {
				break;

			}
		}
		if (!isAdded) { //if we didnt add m1 to exciting monom
			monoms.add(m1);
			sort();
		}
		removeZero();
	}

	/**
	 * Add Polynom to this Polynom
	 * @param p1 is other Polynom
	 */
	@Override
	public void add(Polynom_able p1) {

		Iterator<Monom> it2=p1.iteretor();
		while(it2.hasNext())//if we still in the collection
		{
			Monom pointer = it2.next();
			Monom temp= new Monom(pointer.get_coefficient(),pointer.get_power());
			add(temp); //try to add pointer Monom to the arrayList of the original Polynom
		}
		sort();
	}

	/**
	 * Subtract m1 from this Polynom
	 * @param m1 is other Monom
	 */

	protected void substract(Monom m1) { //assist function
		if (m1.get_coefficient()==0) {

			return;
		}
		m1.changeSign();
		add(m1);

	}

	/**
	 * Subtract Polynom from this Polynom
	 * @param p1 is other Polynom
	 */
	@Override
	public void substract(Polynom_able p1) {

		Iterator<Monom> it2=p1.iteretor();
		while(it2.hasNext()) //if we still in the collection
		{
			Monom pointer = it2.next();
			pointer.changeSign();
			add(pointer); //try to add pointer monom to the arrayList of the original polynom	
		}
		removeZero();
		sort();
	}

	/**
	 * Compute the value of the Polynom at x
	 * @param x is the point we calculate the value of the Polynom
	 * @return the value of f(x)
	 */
	@Override
	public double f (double x){

		double sum=0;
		Iterator<Monom> it = monoms.iterator();
		while(it.hasNext()) //if we still in the collection
		{
			Monom pointer = it.next();
			sum+=pointer.f(x); //call the funciton from the Monom class
		}
		return sum;
	}

	/**
	 * Check if the Polynom is empty (size 0)
	 * @return true if Polynom is empty
	 */
	@Override
	public boolean isZero() {

		return monoms.size()==0;
	}

	/**
	 * Compute and create a new Polynom which is the derivative of this Polynom
	 * @return Polynom_able after Derivative
	 */
	@Override
	public Polynom_able derivative() {

		Polynom p2=new Polynom();
		Iterator<Monom> it = monoms.iterator();
		while(it.hasNext())
		{
			Monom pointer = it.next();
			p2.add(pointer.derivative());
		}
		return p2; //will Up casting to polynom_able automatically
	}

	/**
	 * Create a deep copy of this Polynom
	 * @return the copy Polynom_able
	 */
	@Override
	public Polynom_able copy() {

		return new Polynom(this); //will Up casting to polynom_able automatically	
	}

	/**
	 * Compare the Amount of Monoms in this Polynom between Polynom_able
	 * @param p1 is other Polynom
	 * @return true if this Polynom have the same amount of Monoms  
	 */
	protected boolean CompareSize(Polynom_able p1) {

		//check the size of p1
		Iterator<Monom> it2 = p1.iteretor();
		int size=0;
		while(it2.hasNext()) {	

			Monom pointer2=it2.next();
			if (pointer2.get_coefficient()!=0) {

				size++;
			}
		}
		int thisSize=monoms.size();
		return size==thisSize;
	}

	/**
	 * Test if this Polynom is logically equals to Polynom_able.
	 * @param p1 is other Polynom
	 * @return true iff this Polynom represents the same function ans p1
	 */
	@Override
	public boolean equals (Polynom_able p1) {

		Iterator<Monom> it = monoms.iterator();
		Iterator<Monom> it2 = p1.iteretor();
		if (!CompareSize(p1)) { //if they havn't the same size so not equal
			return false;
		}
		while(it.hasNext() && it2.hasNext())
		{	
			Monom pointer  = it.next();
			Monom pointer2 = it2.next();
			if ((pointer.get_coefficient()!=pointer2.get_coefficient()) || (pointer.get_power()!=pointer2.get_power())) {

				return false;   	
			}
		}
		return true;
	}

	/**
	 * Compute the root of this Polynom
	 * @param x0 starting point
	 * @param x1 end point
	 * @param eps step (positive) value
	 * @return x value that f(x) is less from eps
	 */
	@Override
	public double root(double x0, double x1, double eps) {

		double y0 = f(x0);
		double y1 = f(x1);

		if(y0*y1>0) {
			throw new RuntimeException("Error: you should choose x0 and x1 such that f(x0) and f(x1) should be on oposite sides ");
		}

		if (eps<0) {

			throw new RuntimeException("Error: you should insert a positive eps  ");

		}

		double start=x0, end=x1, middle=(x0+x1)/2;

		if (f(x0)>0 && f(x1)<0) {

			while(middle<end && middle>start) {

				if (Math.abs(f(middle))<eps) {

					return middle;
				}

				if (f(middle)>0) {

					start=middle;
					middle=(end+start)/2;

				}
				else {

					end=middle;
					middle=(end+start)/2;
				}
			}
		}
		else {

			while(middle<end && middle>start) {

				if (Math.abs(f(middle))<eps) {

					return middle;
				}

				if (f(middle)<0) {

					start=middle;
					middle=(end+start)/2;
				}
				else {

					end=middle;
					middle=(end+start)/2;
				}
			}
		}
		return middle;
	}

	/**
	 * Compute Riemann's Integral over this Polynom starting from x0, till x1 using eps size steps
	 * @param x0 starting point
	 * @param x1 end point
	 * @param eps step (positive) value,the width of each rectangle
	 * @return the approximated area above the x-axis below this Polynom and between the [x0,x1] range
	 */
	@Override
	public double area(double x0,double x1, double eps) {

		if (eps>x1-x0) {

			throw new RuntimeException("Error: the width of each rectangle cant be more then the width of the bound ");
		}
		if (eps<0) {

			throw new RuntimeException("Error: the width of each rectangle cant be negative ");

		}
		int steps=(int)((x1-x0)/eps); //the number of rectangles
		double area=0; //the sum of all the areas
		double temp=0; //the current area
		double start=x0+eps;
		for(double i=0;i<steps;i++) {

			temp=eps*f(start);
			if (temp>0) { //to sum only above the x axis

				area=area+temp;
			}
			start=start+eps;

		}
		return area;
	}

	/**
	 * Compute Riemann's Integral over this Polynom starting from x0, till x1 using eps size steps
	 * @param x0 starting point
	 * @param x1 end point
	 * @param eps step (positive) value,the width of each rectangle
	 * @return the approximated area under the x-axis above this Polynom and between the [x0,x1] range
	 */
	public double areaUnderXaxis(double x0,double x1, double eps) {

		if (eps>x1-x0) {

			throw new RuntimeException("Error: the width of each rectangle cant be more then the width of the bound ");
		}
		if (eps<0) {

			throw new RuntimeException("Error: the width of each rectangle cant be negative ");

		}
		int steps=(int)((x1-x0)/eps); //the number of rectangles
		double area=0; //the sum of all the areas
		double temp=0; //the current area
		double start=x0+eps;
		for(double i=0;i<steps;i++) {

			temp=eps*f(start);
			if (temp<0) { //to sum only above the x axis

				area=area+temp;
			}
			start=start+eps;
		}
		return area;

	}

	/**
	 * Create iteretor for iterate over elements 
	 * @return an Iterator (of Monoms) over this Polynom
	 */
	@Override
	public Iterator<Monom> iteretor(){

		Iterator<Monom> it = monoms.iterator();
		return it;
	}

	/**
	 * Print the Polynom on the screen
	 * @return the Polynom's shape is: ax^b+c
	 */
	@Override
	public String toString() {

		Iterator<Monom> it = monoms.iterator();
		String s="";
		boolean first=true;
		while(it.hasNext())
		{
			Monom pointer = it.next();

			if (first) { //if this the first monom in the polynom

				s=s+pointer.get_coefficient()+"x^"+pointer.get_power();									
			}

			if (!first) { //in the middle of the polynom

				if (!pointer.isNegative()) {//if positive 

					s=s+pointer.sign()+pointer.get_coefficient()+"x^"+pointer.get_power();//print the sign too
				}
				else { //if negative dont print the sign beacuse its appear in the coefficient

					s=s+pointer.get_coefficient()+"x^"+pointer.get_power();

				}	
			}
			first=false;
		}
		return s;
	}
}
