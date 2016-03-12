package com.phoenixkahlo.swingutils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.phoenixkahlo.utils.DoubleFunction;


public class FunctionPanelOld extends JPanel {
	
	private static final long serialVersionUID = 9051380125134841689L;
	
	private class FunctionDrawer {
		
		DoubleFunction function;
		Color color;
		
		FunctionDrawer(DoubleFunction function, Color color) {
			this.function = function;
			this.color = color;
		}
		
		void paint(Graphics graphics) {
			graphics.setColor(color);
			for (int pixelX = 0; pixelX < getXLength(); pixelX++) {
				if (function.inDomain(pixelXToUnits(pixelX)) && function.inDomain(pixelXToUnits(pixelX + 1))) {
					if (horizontalX) {
						graphics.drawLine(pixelX, flipYPixels(pixelXToY(pixelX)),
								pixelX + 1, flipYPixels(pixelXToY(pixelX + 1)));
					} else {
						graphics.drawLine(flipXPixels(pixelXToY(pixelX)), flipYPixels(pixelX), flipXPixels(pixelXToY(pixelX + 1)), flipYPixels(pixelX + 1));
					}
				}
			}
		}
		
		int pixelXToY(int pixelX) {
			double mathX = pixelX * (xMax - xMin) / getXLength() + xMin;
			double mathY = function.invoke(mathX);
			int pixelY = (int) ((mathY - yMin) * getYLength() / (yMax - yMin));
			return pixelY;
		}
		
	}
	
	private double pixelXToUnits(int pixels) {
		return pixels * (xMax - xMin) / getXLength() + xMin;
	}
	
	private int flipYPixels(int y) {
		return height - y;
	}
	
	private int flipXPixels(int x) {
		return width - x;
	}
	
	private int getXLength() {
		return horizontalX ? width : height;
	}
	
	private int getYLength() {
		return horizontalX ? height : width;
	}
	
	private List<FunctionDrawer> functions = new ArrayList<FunctionDrawer>();
	private int width;
	private int height;
	private int xMin;
	private int xMax;
	private int yMin;
	private int yMax;
	private boolean horizontalX;
	
	public FunctionPanelOld(int width, int height, int xMin, int xMax, int yMin, int yMax, boolean horizontalX) {
		this.width = width;
		this.height = height;
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
		this.horizontalX = horizontalX;
	}
	
	public void addFunction(DoubleFunction function, Color color) {
		functions.add(new FunctionDrawer(function, color));
		repaint();
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}
	
	@Override
	public void paintComponent(Graphics graphics) {
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, width, height);
		//TODO: draw axes, units
		for (FunctionDrawer drawer : functions) {
			drawer.paint(graphics);
		}
	}
	
	
}
