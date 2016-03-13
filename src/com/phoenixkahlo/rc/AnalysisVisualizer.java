package com.phoenixkahlo.rc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

import javax.swing.JPanel;

import com.phoenixkahlo.swingutils.FunctionGraph;
import com.phoenixkahlo.swingutils.SizeLimitedSwingImage;
import com.phoenixkahlo.swingutils.SwingImage;
import com.phoenixkahlo.utils.Function;

public class AnalysisVisualizer extends JPanel {

	private static final long serialVersionUID = 7337085794805913681L;
	
	private static final int GRAPH_WIDTH = 300;
	
	private SwingImage unshifted;
	private SwingImage shifted;
	private Map<Integer, Double> data;
	private FunctionGraph graph;
	
	/*
	 * Assumed that images are of same dimensions
	 */
	public AnalysisVisualizer(BufferedImage unshiftedImage, BufferedImage shiftedImage, Map<Integer, Double> data) throws IOException {
		
		// Setup images
		unshifted = new SizeLimitedSwingImage(unshiftedImage, () -> 500, () -> 500);
		shifted = new SizeLimitedSwingImage(shiftedImage, () -> 500, () -> 500);
		add(unshifted);
		add(shifted);
		
		// Setup graph
		this.data = data;
		Function function = new Function() {
			@Override
			public double invoke(double x) {
				return data.get((int) x);
			}
			@Override
			public boolean inDomain(double x) {
				return data.containsKey((int) x);
			}
		};
		int imageHeight = unshiftedImage.getHeight();
		double xMin = (double) -imageHeight / 2;
		double xMax = (double) imageHeight / 2;
		graph = new FunctionGraph(GRAPH_WIDTH, imageHeight, xMin, xMax, 0, function.highestY(xMin, xMax, 1), true);
		
		//graph = new FunctionGraph(
		//		GRAPH_WIDTH, unshiftedImage.getHeight(),
		//		-unshiftedImage.getHeight() / 2, unshiftedImage.getHeight() / 2,
		//		0, function.highestY(-unshiftedImage.getHeight() / 2, unshiftedImage.getHeight() / 2, 1),
		//		false
		//		);
				
		graph.addFunction(function, Color.RED);
		graph.addFunction(new SwingTester.IndentityFunction(), Color.BLUE);
		add(graph);
		
		// Setup positioning
		setLayout(new LayoutManager() {
			@Override
			public void addLayoutComponent(String name, Component comp) {}
			@Override
			public void removeLayoutComponent(Component comp) {}
			@Override
			public Dimension preferredLayoutSize(Container parent) {
				System.out.println("should be about to call get preferred size");
				return parent.getPreferredSize();
			}
			@Override
			public Dimension minimumLayoutSize(Container parent) {
				return preferredLayoutSize(parent);
			}
			@Override
			public void layoutContainer(Container parent) {
				if (getOptimalShift() > 0) {
					unshifted.setBounds(0, 0, unshifted.getPreferredSize().width, unshifted.getPreferredSize().height);
					shifted.setBounds(
							unshifted.getPreferredSize().width, getOptimalShift(),
							shifted.getPreferredSize().width, shifted.getPreferredSize().height
							);
					graph.setBounds(
							unshifted.getPreferredSize().width + shifted.getPreferredSize().width, 0,
							graph.getPreferredSize().width, graph.getPreferredSize().height
							);
					System.out.println("x range: " + graph.getXMin() + " to " + graph.getXMax());
					System.out.println("y range: " + graph.getYMin() + " to " + graph.getYMax());
				} else {
					System.out.println("EAT THE HELL");
				}
			}
		});
		
	}
	
	public Dimension getPreferredSize() {
		System.out.println("get preferred size called");
		return new Dimension(
				(int) (unshifted.getPreferredSize().getWidth() + shifted.getPreferredSize().getWidth()) + GRAPH_WIDTH,
				(int) (unshifted.getPreferredSize().getHeight()) + getOptimalShift()
				);
	}
	
	private int getOptimalShift() {
		int lowestKey = -1;
		double lowestValue = Double.MAX_VALUE;
		for (int key : data.keySet()) {
			if (data.get(key) < lowestValue) {
				lowestKey = key;
				lowestValue = data.get(key);
			}
		}
		return lowestKey;
	}
	
	private double getHighestY() {
		double out = -Double.MAX_VALUE;
		for (double n : data.values()) {
			if (n > out) out = n;
		}
		return out;
	}
	
}
