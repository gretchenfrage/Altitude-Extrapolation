package com.phoenixkahlo.swingutils;

import java.awt.Component;

import javax.swing.JFrame;

public class BasicFrame extends JFrame {

	private static final long serialVersionUID = 7755423225682981113L;
	
	private boolean displayed = false;
	
	public BasicFrame() {
		super();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	@Override
	public Component add(Component component) {
		getContentPane().add(component);
		if (displayed) {
			revalidate();
			repaint();
		}
		return component;
	}
	
	public void display() {
		pack();
		setLocationRelativeTo(null);
		displayed = true;
		setVisible(true);
	}
	
}
