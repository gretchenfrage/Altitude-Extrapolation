package com.phoenixkahlo.swingutils;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

public class VerticallyFloatingLayout implements LayoutManager {

	private int height;
	private int floatY;
	
	public VerticallyFloatingLayout(int height, int floatY) {
		this.height = height;;
		this.floatY = floatY;
	}
	
	@Override
	public void addLayoutComponent(String name, Component comp) {}

	@Override
	public void removeLayoutComponent(Component comp) {}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		if (parent.getComponentCount() == 0) return new Dimension(0, 0);
		return new Dimension(parent.getComponent(parent.getComponentCount() - 1).getPreferredSize().width, height);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return new Dimension(0, 0);
	}

	@Override
	public void layoutContainer(Container parent) {
		if (parent.getComponentCount() == 0) return;
		Component component = parent.getComponent(parent.getComponentCount() - 1);
		if (component.isVisible()) {
			component.setBounds(0, floatY, component.getPreferredSize().width, component.getPreferredSize().height);
		}
	}

	/*
	 *  Insets insets = parent.getInsets();
		Dimension size = parent.getSize();
		int width = size.width - insets.left - insets.right;
		int height = insets.top;

		for (int i = 0, c = parent.getComponentCount(); i < c; i++) {
			Component m = parent.getComponent(i);
			if (m.isVisible()) {
				m.setBounds(insets.left, height, width, m.getPreferredSize().height);
				height += m.getSize().height + gap;
			}
		}
	 */
	
}
