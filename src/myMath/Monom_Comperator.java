package myMath;

import java.util.Comparator;

public class Monom_Comperator implements Comparator<Monom> {

	public int compare(Monom o1, Monom o2)
	{
		return ((o2.get_power())-(o1.get_power())); //the bigger power first
	}
}