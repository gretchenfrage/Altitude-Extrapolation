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
		double lowestY = Double.MAX_VALUE;
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
		double highestY = -Double.MAX_VALUE;
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
	
	public double lowestX(double xMin, double xMax, double step) {
		for (double x = xMin; x <= xMax; x += step) {
			if (inDomain(x)) return x;
		}
		return Double.NaN;
	}
	
	public double highestX(double xMin, double xMax, double step) {
		for (double x = xMax; x >= xMin; x -= step) {
			if (inDomain(x)) return x;
		}
		return Double.NaN;
	}
	
}
