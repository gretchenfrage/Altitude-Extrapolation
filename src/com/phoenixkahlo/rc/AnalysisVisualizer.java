package com.phoenixkahlo.rc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

import javax.swing.JPanel;

import com.phoenixkahlo.swingutils.BasicFrame;
import com.phoenixkahlo.swingutils.FunctionGraph;
import com.phoenixkahlo.swingutils.SetSizeSwingImage;
import com.phoenixkahlo.swingutils.SwingImage;
import com.phoenixkahlo.utils.Function;
import com.phoenixkahlo.utils.MapFunction;

public class AnalysisVisualizer extends JPanel {

	public static void visualize(BufferedImage unshifted, BufferedImage shifted, Map<Integer, Double> data,
			int scanYMin, int scanYMax) throws IOException {
		BasicFrame frame = new BasicFrame();
		frame.add(new AnalysisVisualizer(unshifted, shifted, data, scanYMin, scanYMax));
		frame.display();
	}
	
	private static final long serialVersionUID = 7337085794805913681L;
	
	private static final int IMAGE_SIZE = 500;
	private static final int GRAPH_WIDTH = 300;
	
	private SwingImage unshifted;
	private SwingImage shifted;
	private FunctionGraph graph;
	private Function function;
	private double xMin;
	private double xMax;
	private int scanYMin;
	private int scanYMax;
	
	/*
	 * Assumed that images are of same dimensions
	 */
	public AnalysisVisualizer(BufferedImage unshiftedImage, BufferedImage shiftedImage,
			Map<Integer, Double> data, int scanYMin, int scanYMax) throws IOException {
		this.scanYMin = scanYMin;
		this.scanYMax = scanYMax;
		
		// Setup images
		unshifted = new SetSizeSwingImage(unshiftedImage, IMAGE_SIZE, IMAGE_SIZE);
		shifted = new SetSizeSwingImage(shiftedImage, IMAGE_SIZE, IMAGE_SIZE);
		add(unshifted);
		add(shifted);
		
		// Setup graph
		function = new MapFunction(data);
		xMin = (double) -unshiftedImage.getHeight() / 2;
		xMax = (double) unshiftedImage.getHeight() / 2;
		graph = new FunctionGraph(
				GRAPH_WIDTH, (int) unshifted.getPreferredSize().getHeight(),
				xMin, xMax,
				0, function.highestY(xMin, xMax, 1),
				false
				);
		graph.addFunction(function, Color.RED);
		add(graph);
		
		// Setup positioning
		setLayout(new LayoutManager() {
			@Override
			public void addLayoutComponent(String name, Component comp) {}
			@Override
			public void removeLayoutComponent(Component comp) {}
			@Override
			public Dimension preferredLayoutSize(Container parent) {
				return parent.getPreferredSize();
			}
			@Override
			public Dimension minimumLayoutSize(Container parent) {
				return preferredLayoutSize(parent);
			}
			@Override
			public void layoutContainer(Container parent) {
				if (getOptimalShift() >= 0) {
					unshifted.setBounds(0, 0, unshifted.getPreferredSize().width, unshifted.getPreferredSize().height);
					shifted.setBounds(
							unshifted.getPreferredSize().width, getDisplayedShift(),
							shifted.getPreferredSize().width, shifted.getPreferredSize().height
							);
					graph.setBounds(
							unshifted.getPreferredSize().width + shifted.getPreferredSize().width, 0,
							graph.getPreferredSize().width, graph.getPreferredSize().height
							);
				} else {
					unshifted.setBounds(0, getDisplayedShift(), 
							unshifted.getPreferredSize().width, unshifted.getPreferredSize().height);
					shifted.setBounds(unshifted.getPreferredSize().width, 0, 
							shifted.getPreferredSize().width, shifted.getPreferredSize().height);
					graph.setBounds(
							unshifted.getPreferredSize().width + shifted.getPreferredSize().width,
							getDisplayedShift(), graph.getPreferredSize().width, graph.getPreferredSize().height
							);
				}
			}
		});
		
	}
	
	private int getDisplayedShift() {
		return Math.abs(getOptimalShift() * unshifted.getPreferredSize().height / unshifted.getImage().getHeight());
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(
				(int) (unshifted.getPreferredSize().width + shifted.getPreferredSize().width) + graph.getPreferredSize().width,
				(int) (unshifted.getPreferredSize().height) + getDisplayedShift()
				);
	}
	
	private int getOptimalShift() {
		return (int) function.xOfLowestY(xMin, xMax, 1);
	}
	
	@Override
	public void paint(Graphics graphics) {
		super.paint(graphics);
		
		graphics.setColor(Color.GREEN);
		int y1 = (int) ((double) scanYMin * unshifted.getPreferredSize().height / unshifted.getImage().getHeight());
		int y2 = (int) ((double) scanYMax * unshifted.getPreferredSize().height / unshifted.getImage().getHeight());
		if (getOptimalShift() < 0) {
			y1 += getDisplayedShift();
			y2 += getDisplayedShift();
		}
		graphics.drawLine(0, y1, getWidth(), y1);
		graphics.drawLine(0, y2, getWidth(), y2);
		
		graphics.setColor(Color.MAGENTA);
		int y;
		if (getOptimalShift() >= 0)
			y = getDisplayedShift() + unshifted.getHeight() / 2;
		else
			y = unshifted.getHeight() / 2;
		graphics.drawLine(unshifted.getWidth(), y,
				graph.yUnitsToXPixels(function.invoke(getOptimalShift())) + unshifted.getWidth() + shifted.getWidth(),
				y);
	}
	
}
