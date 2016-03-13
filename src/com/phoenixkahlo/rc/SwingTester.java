package com.phoenixkahlo.rc;

import java.awt.Color;

import com.phoenixkahlo.swingutils.BasicFrame;
import com.phoenixkahlo.swingutils.FunctionGraph;
import com.phoenixkahlo.utils.Function;



public class SwingTester {
	
	public static class IndentityFunction extends Function {
		@Override
		public double invoke(double x) {
			return x;
		}
	}

	public static class SineFunction extends Function {
		@Override
		public double invoke(double x) {
			return Math.sin(x / 50) * 50 + 250;
		}
	}

	public static class Parabola extends Function {
		@Override
		public double invoke(double x) {
			return Math.pow(x, 2) / 400;
		}
	}
	
	public static void main(String[] args) {
		BasicFrame frame = new BasicFrame();
		FunctionGraph panel = new FunctionGraph(1000, 500, 0, 500, 0, 500, false);
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
