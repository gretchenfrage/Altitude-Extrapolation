package com.phoenixkahlo.rc;

import java.awt.Color;

import com.phoenixkahlo.swingutils.BasicFrame;
import com.phoenixkahlo.swingutils.FunctionPanel;
import com.phoenixkahlo.utils.AllDomainDoubleFunction;

class IndentityFunction extends AllDomainDoubleFunction {
	@Override
	public double invoke(double x) {
		return x;
	}
}

class SineFunction extends AllDomainDoubleFunction {
	@Override
	public double invoke(double x) {
		return Math.sin(x / 50) * 50 + 250;
	}
}

class Parabola extends AllDomainDoubleFunction {
	@Override
	public double invoke(double x) {
		return Math.pow(x - 250, 2) / 10;
	}
}

public class SwingTester {
	
	public static void main(String[] args) {
		BasicFrame frame = new BasicFrame();
		FunctionPanel panel = new FunctionPanel(1000, 500, 0, 500, 0, 500, false);
		frame.add(panel);
		frame.display();
		panel.addFunction(new IndentityFunction(), Color.RED);
		panel.addFunction(new SineFunction(), Color.BLUE);
		panel.addFunction(new Parabola(), Color.GREEN);
	}

	public static void functionPanelCoordinateConversionTest() {
		/*
		FunctionPanel panel = new FunctionPanel(100, 200, 100, 200, 100, 200, false);
		panel.setSize(panel.getPreferredSize());
		System.out.println("Coordinate conversion test 1");
		System.out.println(panel.xPixelsToYUnits(50) + " == 150 ?");
		System.out.println(panel.yPixelsToXUnits(100) + " == 150 ?");
		System.out.println(panel.xUnitsToYPixels(150) + " == 100 ?");
		System.out.println(panel.yUnitsToXPixels(150) + " == 50 ?");
		System.out.println("Coordinate conversion test 2");
		System.out.println(panel.xPixelsToYUnits(75) + " == 175 ?");
		System.out.println(panel.yPixelsToXUnits(150) + " == 175 ?");
		System.out.println(panel.xUnitsToYPixels(175) + " == 150 ?");
		System.out.println(panel.yUnitsToXPixels(175) + " == 75 ?");
		 */		
	}
	
}
