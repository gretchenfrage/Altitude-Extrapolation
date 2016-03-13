package com.phoenixkahlo.utils;

public abstract class Function {

	/*
	 * If the domain is not all real numbers, override this
	 */
	public boolean inDomain(double x) {
		return true;
	}
	
	public abstract double invoke(double x);
	
	public double xOfLowestY(double xMin, double xMax, double step) {
		double xOfLowestY = Double.NaN;
		double lowestY = 0;
		for (double x = xMin; x <= xMax; x += step) {
			if (inDomain(x) && invoke(x) < lowestY) {
				xOfLowestY = x;
				lowestY = invoke(x);
			}
		}
		return xOfLowestY;
	}
	
	public double xOfHighestY(double xMin, double xMax, double step) {
		double xOfHighestY = Double.NaN;
		double highestY = 0;
		for (double x = xMin; x <= xMax; x += step) {
			if (inDomain(x) && invoke(x) > highestY) {
				xOfHighestY = x;
				highestY = invoke(x);
			}
		}
		return xOfHighestY;
	}
	
	public double lowestY(double xMin, double xMax, double step) {
		return invoke(xOfLowestY(xMin, xMax, step));
	}
	
	public double highestY(double xMin, double xMax, double step) {
		return invoke(xOfHighestY(xMin, xMax, step));
	}
	
}
