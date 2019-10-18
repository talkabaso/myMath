package myMath;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class junitTest {

	private Polynom poly;
	private Polynom p1;
	private Polynom polyEx1;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {

		poly = new Polynom("5x^4-7x^3+2x^1-30x^0");

		p1 = new Polynom("-2x^2+5.5x^1+30x^0");
		
		polyEx1 =new Polynom ("0.2x^4-1.5x^3+3x^2-1x^1-5x^0");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	//	@Test
	//	void test() {
	//		fail("Not yet implemented");
	//	}
	
	@Test
	public void equalTest() {//test first in order to use that function in other tests

		// Arrange
		final Polynom expected = new Polynom("5x^4-7x^3+2x^1-30x^0");

		// Act
		final Polynom_able actual = poly.copy();

		// Assert
		Assert.assertNotSame(expected, actual);//check addreses in the memory (should be different)
	}
	
	@Test
	public void toStringTest() {
		
		Polynom test= new Polynom(poly.toString());
		
		if (!poly.equals(test)) {
			
			Assert.fail();
		}
	}

	@Test
	public void addTest() {

		// Arrange
		final Polynom expected = new Polynom("5x^4-7x^3-2x^2+7.5x^1");

		// Act
		poly.add(p1);
		Polynom actual=poly;

		// Assert
		if (!expected.equals(actual)) {

			Assert.fail();
		}
	}

	@Test
	public void substractTest() {

		// Arrange
		final Polynom expected = new Polynom("5x^4-7x^3+2x^2-3.5x^1-60x^0");

		// Act
		poly.substract(p1);
		Polynom actual=poly;

		// Assert
		if (!expected.equals(actual)) {

			Assert.fail();
		}
	}

	@Test
	public void multiplyTest() {

		// Arrange
		final Polynom expected = new Polynom("-10x^6+41.5x^5+111.5x^4-214x^3+71x^2-105x^1-900x^0");

		// Act
		poly.multiply(p1);
		Polynom actual=poly;

		// Assert
		if (!expected.equals(actual)) {

			Assert.fail();
		}
	}

	@Test
	public void fTest() {//assertEquals doesnt work with double

		// Arrange
		double expected=16882;

		// Act
		double actual=poly.f(8);

		// Assert
		
		Assert.assertEquals(expected, actual, 0);

		double expected2=7.5;
		actual=p1.f(5);

		Assert.assertEquals(expected2, actual, 0);
	}

	@Test
	public void isZeroTest() {
		
		//Test with poly-should be zero beacuse we will add the negative polynom

		Polynom polyNegative = new Polynom("-5x^4+7x^3-2x^1+30x^0");
		poly.add(polyNegative);
		Polynom actual=poly;
		// Assert
		Assert.assertTrue(actual.isZero()); //if true pass the test

		//One more test with p1 this time
		//The answer should not be zero Polynom 
		Polynom AlmostP1Negative = new Polynom("-2x^2+5.5x^1-30x^0");
		p1.add(AlmostP1Negative);
		actual=p1;
		//Assert
		Assert.assertFalse(actual.isZero());//if false pass the test
	}

	@Test
	public void derivativeTest() {

		// Arrange
		final Polynom expected = new Polynom("20x^3-21x^2+2x^0");

		// Act
		final Polynom_able actual = poly.derivative();

		// Assert
		if (!expected.equals(actual)) {

			Assert.fail();
		}
	}
	
	@Test
	public void copyTest() {

		// Arrange
		final Polynom expected = new Polynom("5x^4-7x^3+2x^1-30x^0");

		// Act
		final Polynom_able actual = poly.copy();

		// Assert
		if (!expected.equals(actual)) {//should be the same values of monoms

			Assert.fail();
		}
		Assert.assertNotSame(poly, actual);//should be different addresses in the memory
	}

	@Test
	public void rootTest() {

		
		// Arrange
		double expected=-2.734820556;

		// Act
		double actual=p1.root(-10, 5, 0.0000001);

		// Assert
		Assert.assertEquals(expected, actual, 0.0000001);
	}

	@Test
	public void areaTest() {

		// Arrange
		double expected=185.1131672612053;

		// Act
		double actual=p1.area(-10, 10, 0.001);

		// Assert
		Assert.assertEquals(expected, actual,0.01);
	}
	
	@Test
	public void areaUnderXaxisTest() {
		// Arrange
		double expected= -25.18374177435345;
		
		// Act
		double actual= polyEx1.areaUnderXaxis(-2, 6, 0.001);
		
		// Assert
		Assert.assertEquals(expected, actual,0.01);
	}
}