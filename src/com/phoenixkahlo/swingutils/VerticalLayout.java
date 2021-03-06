/*
 * Copyright (c) 2009 Albert Kurucz.
 *
 * This file, VerticalLayout.java is part of JTStand.
 *
 * JTStand is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JTStand is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JTStand.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.phoenixkahlo.swingutils;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

/**
 * Organizes components in a vertical layout.
 * 
 * @author fred
 */
public class VerticalLayout implements LayoutManager {

	private int gap = 0;

	public VerticalLayout() {}

	public VerticalLayout(int gap) {
		this.gap = gap;
	}
	
	public int getGap() {
		return gap;
	}

	public void setGap(int gap) {
		this.gap = gap;
	}
	
	@Override
	public void addLayoutComponent(String name, Component c) {}
	
	@Override
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
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
	}
	
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return preferredLayoutSize(parent);
	}
	
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		Insets insets = parent.getInsets();
		Dimension pref = new Dimension(0, 0);

		for (int i = 0, c = parent.getComponentCount(); i < c; i++) {
			Component m = parent.getComponent(i);
			if (m.isVisible()) {
				Dimension componentPreferredSize = parent.getComponent(i).getPreferredSize();
				pref.height += componentPreferredSize.height + gap;
				pref.width = Math.max(pref.width, componentPreferredSize.width);
			}
		}

		pref.width += insets.left + insets.right;
		pref.height += insets.top + insets.bottom;

		return pref;
	}
	
	@Override
	public void removeLayoutComponent(Component c) {}

}