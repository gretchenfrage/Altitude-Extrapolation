package com.phoenixkahlo.utils;

public interface DoubleFunction {

	double invoke(double x);
	
	boolean inDomain(double x);
	
}
