package myMath;

/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real number and a is an integer (summed a none negative), 
 * see: https://en.wikipedia.org/wiki/Monomial 
 * The class implements function and support simple operations as: construction, value at x, derivative, add and multiply. 
 * @author Tal and Aric
 *
 */
public class Monom implements function{

	private double _coefficient; //a
	private int _power; //b

	/**
	 * Init Monom with coefficient and power
	 * @param a is the coefficient
	 * @param b is the power
	 */
	public Monom(double a, int b){

		this.set_coefficient(a);
		this.set_power(b);
	}

	/**
	 * Init copy - Create a new Monom with the same values of the other Monom
	 * @param ot is other Monom
	 */
	public Monom(Monom ot) {
		this(ot.get_coefficient(), ot.get_power());
	}

	/**
	 * Init(String) - Create New Monom from this string in this shape: ax^b
	 * @param str is String of Monom
	 * @return Monom 
	 */
	public Monom (String str) {
		if (str.length()==0) {
			
			throw new RuntimeException("Error: please enter string:ax^b");
		}
		int i=0;
		String temp="";
		while(i<str.length() && str.charAt(i)!='x') { //searching for coefficient

			temp=temp+str.charAt(i);
			i++;
		}
		if (temp=="") { //coefficient does not exist for x
			_coefficient=1;
		}
		else {
			_coefficient=Double.parseDouble(temp);
		}
		//searching for the power
		i++;
		if ( i<str.length() && str.charAt(i)!='^') { //after x must be the sign "^"
			
			throw new RuntimeException("Error: The string not match the format of polynom ");
		}
		i++;
		if ( i<str.length() && str.charAt(i)=='-') { //if the power is negative
			
			throw new RuntimeException("Error: cant create monom with negative power ");
		}
		temp="";		
		if (i<str.length()) {
			
			while(i<str.length() ) {

				temp =temp+str.charAt(i);
				i++;			
			}
			_power=Integer.parseInt(temp);
		}
		else { //if the string end with "^"

			throw new RuntimeException("Error: The string not match the format of polynom ");
		}
	}

	// ***************** add your code below **********************
	//add construction, derivative, add(if power equal so add the coefficient)and multiply(newpower=power1+power2,multiply the co..)

	/**
	 * Compute the value of the Monom at x
	 * @param x is the point we calculate the value of the Monom
	 * @return the value of f(x)
	 */
	@Override
	public double f(double x) {

		return(( Math.pow(x, _power))*_coefficient);
	}

	/**
	 * Create a new Monom with the value of the derivative Monom
	 * @return new Monom with the value of the derivative 
	 */
	public Monom derivative () {
		
		double co = 0;
		int pow = _power;
		if (_power!=0) {
			
			co=this._coefficient*this._power;
			pow--;
		}
		return (new Monom (co,pow));
	}

	/**
	 * add other coeff to this Monom coeff if they have the same power
	 * @param other is other Monom
	 * @return true if we added other successfully otherwise return false 
	 */
	public boolean add(Monom other) { //we use bool for adding to the polynom

		if (_power==other._power) {

			_coefficient+=other._coefficient;
			return true;
		}
		return false; //else case: other power different from current power

	}

	/**
	 * Subtract other from this Monom if they have the same power
	 * @param other is other Monom
	 * @return true if we subtracted other successfully otherwise return false
	 */
	public boolean substract (Monom other) {

		if (_power==other._power) {

			_coefficient=_coefficient-other._coefficient;
			return true;
		}
		return false; //else case: other power different from current power
	}

	/**
	 * Change the sign of the monom coefficient
	 * @param other is other Monom
	 */
	protected void changeSign () { //assist to subtract

		set_coefficient(get_coefficient()*(-1)); //in order to subtract
	}

	/**
	 * @return the sign of this Monom
	 */
	protected String sign() { //return the sign of the monom

		if (this._coefficient<0) {
			
			return "-";
		}
		else {
			return "+";
		}
	}

	/**
	 * Test if this is negative Monom
	 * @return true if this negative Monom otherwise return false
	 */

	protected boolean isNegative() { //return true if the monom is negative

		if (this._coefficient<0) {

			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Multiply this Monom by other without changing the value of the current Monom
	 * @param other is other Monom
	 * @return new Monom after multiplying
	 */
	//assist to monomMultPoly at polynom class, dont change the value create new monom
	public Monom multiply(Monom other) {

		Monom temp=new Monom (other);
		temp.set_coefficient(this._coefficient*temp.get_coefficient());
		temp.set_power(this._power+temp.get_power());
		return temp;
	}

	/** 
	 * @return the value of the coefficient
	 */
	public double get_coefficient() {

		return this._coefficient;
	}

	/**	 
	 * @return the value of the power
	 */
	public int get_power() {

		return this._power;
	}

	/**
	 * Print the Monom on the screen
	 * @return the Monoms's shape is: ax^b
	 */
	@Override
	public String toString() {

		String s="";
		if (get_coefficient()!=0) {

			if (get_power()==0) { //if just a number power=0
				s=s+get_coefficient();		
			}
			else {
				s=s+get_coefficient()+"x^"+get_power();	
			}		
		}
		return s;
	}

	//End****************** Private Methods and Data *****************

	private void set_coefficient(double a){
		this._coefficient = a;
	}
	private void set_power(int p) {
		this._power = p;
	}
}
