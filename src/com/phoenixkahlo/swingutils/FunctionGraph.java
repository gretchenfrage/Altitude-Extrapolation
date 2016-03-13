package com.phoenixkahlo.swingutils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import com.phoenixkahlo.utils.Function;

public class FunctionGraph extends JComponent {
	
	private static final long serialVersionUID = 9051380125134841689L;

	private class FunctionDrawer {
		
		Function function;
		Color color;
		
		FunctionDrawer(Function function, Color color) {
			this.function = function;
			this.color = color;
		}
		
		void paint(Graphics graphics) {
			graphics.setColor(color);
			if (horizontalX) {
				for (int pixelsX = 0; pixelsX < getWidth(); pixelsX++) {
					double unitsX1 = xPixelsToUnits(pixelsX);
					double unitsX2 = xPixelsToUnits(pixelsX + 1);
					if (function.inDomain(unitsX1) && function.inDomain(unitsX2)) {
						int pixelsY1 = yUnitsToPixels(function.invoke(unitsX1));
						int pixelsY2 = yUnitsToPixels(function.invoke(unitsX2));
						graphics.drawLine(pixelsX, pixelsY1, pixelsX + 1, pixelsY2);
					}
				}
			} else {
				for (int pixelsY = 0; pixelsY < getHeight(); pixelsY++) {
					double unitsX1 = yPixelsToXUnits(pixelsY);
					double unitsX2 = yPixelsToXUnits(pixelsY + 1);
					if (function.inDomain(unitsX1) && function.inDomain(unitsX2)) {
						int pixelsX1 = yUnitsToXPixels(function.invoke(unitsX1));
						int pixelsX2 = yUnitsToXPixels(function.invoke(unitsX2));
						graphics.drawLine(pixelsX1, pixelsY, pixelsX2, pixelsY + 1);
					}
				}
			}
		}
		
	}
	
	private int flipPixelsVertical(int pixelsY) {
		return getHeight() - pixelsY;
	}
	
	/*
	 * x to x and y to y conversions are only for if horizontalX
	 */
	//TODO: recreate to not use flippixelsvertically
	private double xPixelsToUnits(int pixelsX) {
		if (horizontalX) {
			return pixelsX * (xMax - xMin) / getWidth() + xMin;
		} else {
			throw new RuntimeException();
		}
	}
	
	@SuppressWarnings("unused")
	private double yPixelsToUnits(int pixelsY) {
		if (horizontalX) {
			return flipPixelsVertical(pixelsY) * (yMax - yMin) / getHeight() + yMin;
		} else {
			throw new RuntimeException();
		}
	}
	
	@SuppressWarnings("unused")
	private int xUnitsToPixels(double unitsX) {
		if (horizontalX) {
			return (int) ((unitsX - xMin) * getWidth() / (xMax - xMin));
		} else {
			throw new RuntimeException();
		}
	}
	
	private int yUnitsToPixels(double unitsY) {
		if (horizontalX) {
			return flipPixelsVertical((int) ((unitsY - yMin) * getHeight() / (yMax - yMin)));
		} else {
			throw new RuntimeException();
		}
	}
	
	/*
	 * x to y and y to x conversions are only for if not horizontalX
	 */
	
	private double yPixelsToXUnits(int pixelsY) {
		if (!horizontalX) {
			return (double) pixelsY * (xMax - xMin) / getHeight() + xMin;
		} else {
			throw new RuntimeException();
		}
	}
	
	@SuppressWarnings("unused")
	private int xUnitsToYPixels(double unitsX) {
		if (!horizontalX) {
			return (int) ((unitsX - xMin) * getHeight() / (xMax - xMin));
		} else {
			throw new RuntimeException();
		}
	}
	
	private int yUnitsToXPixels(double unitsY) {
		if (!horizontalX) {
			return (int) ((unitsY - yMin) * getWidth() / (yMax - yMin));
		} else {
			throw new RuntimeException();
		}
	}
	/*
	@SuppressWarnings("unused")
	private double xPixelsToYUnits(int pixelsX) {
		if (!horizontalX) {
			return (double) pixelsX * (yMax - yMin) / getWidth() + yMin;
		} else {
			throw new RuntimeException();
		}
	}
	
	private double yPixelsToXUnits(int pixelsY) {
		if (!horizontalX) {
			return (double) (getHeight() - flipPixelsVertical(pixelsY)) * (xMax - xMin) / getHeight() + xMin;
		} else {
			throw new RuntimeException();
		}
	}
	
	private int xUnitsToYPixels(double unitsX) {
		if (!horizontalX) {
			return flipPixelsVertical((int) (-1 * (unitsX - xMin) * getWidth() / (xMax - xMin) + getHeight()));
		} else {
			throw new RuntimeException();
		}
	}
	
	@SuppressWarnings("unused")
	private int yUnitsToXPixels(double unitsY) {
		if (!horizontalX) {
			return (int) ((unitsY - yMin) * getWidth() / (yMax - yMin));
		} else {
			throw new RuntimeException();
		}
	}
	*/
	private List<FunctionDrawer> functions = new ArrayList<FunctionDrawer>();
	private Dimension preferredSize;
	private double xMin;
	private double xMax;
	private double yMin;
	private double yMax;
	private boolean horizontalX;
	
	public double getXMin() {
		return xMin;
	}
	
	public double getXMax() {
		return xMax;
	}
	
	public double getYMin() {
		return yMin;
	}
	
	public double getYMax() {
		return yMax;
	}
	
	public Function getFunction(int i) {
		return functions.get(i).function;
	}
	
	public FunctionGraph(int width, int height, double xMin, double xMax, double yMin, double yMax, boolean horizontalX) {
		this.preferredSize = new Dimension(width, height);
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
		this.horizontalX = horizontalX;
	}
	
	@Override
	public void paintComponent(Graphics graphics) {
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, getWidth(), getHeight());
		//TODO: draw axes, units
		for (FunctionDrawer function : functions) {
			function.paint(graphics);
		}
	}
	
	public void addFunction(Function function, Color color) {
		functions.add(new FunctionDrawer(function, color));
		repaint();
	}
	
	@Override
	public Dimension getPreferredSize() {
		return preferredSize;
	}
	
}
