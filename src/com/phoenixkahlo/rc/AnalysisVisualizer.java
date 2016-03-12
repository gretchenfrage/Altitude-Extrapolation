package com.phoenixkahlo.rc;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

import javax.swing.JPanel;

import com.phoenixkahlo.swingutils.ImagePanel;
import com.phoenixkahlo.swingutils.SizeLimitedImagePanel;

public class AnalysisVisualizer extends JPanel {

	private static final long serialVersionUID = 7337085794805913681L;
	
	private static final int GRAPH_WIDTH = 100;
	
	private ImagePanel unshifted;
	private ImagePanel shifted;
	private Map<Integer, Double> data;
	
	/*
	 * Assumed that images are of same dimensions
	 */
	public AnalysisVisualizer(BufferedImage unshiftedImage, BufferedImage shiftedImage, Map<Integer, Double> data) throws IOException {
		
		unshifted = new SizeLimitedImagePanel(unshiftedImage, () -> 500, () -> 500);
		shifted = new SizeLimitedImagePanel(shiftedImage, () -> 500, () -> 500);
		this.data = data;
		
		setLayout(new LayoutManager() {
			@Override
			public void addLayoutComponent(String name, Component comp) {}

			@Override
			public void removeLayoutComponent(Component comp) {}

			@Override
			public Dimension preferredLayoutSize(Container parent) {
				return new Dimension(
						(int) (unshifted.getPreferredSize().getWidth() + shifted.getPreferredSize().getWidth()) + GRAPH_WIDTH,
						(int) (unshifted.getPreferredSize().getHeight()) + getOptimalShift()
						);
			}

			@Override
			public Dimension minimumLayoutSize(Container parent) {
				return preferredLayoutSize(parent);
			}

			@Override
			public void layoutContainer(Container parent) {
			}
		});
		
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
	
}
