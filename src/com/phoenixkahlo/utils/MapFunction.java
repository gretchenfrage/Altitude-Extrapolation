package com.phoenixkahlo.utils;

import java.util.Map;

public class MapFunction extends Function {

	private Map<? extends Number, ? extends Number> map;
	
	public MapFunction(Map<? extends Number, ? extends Number> map) {
		this.map = map;
	}
	
	@Override
	public double invoke(double x) {
		for (Map.Entry<? extends Number, ? extends Number> entry : map.entrySet()) {
			if (entry.getKey().intValue() == (int) x) return entry.getValue().doubleValue();
		}
		throw new RuntimeException();
	}

	@Override
	public boolean inDomain(double x) {
		for (Number key : map.keySet()) {
			if (key.intValue() == (int) x) return true;
		}
		return false;
	}
	
}
