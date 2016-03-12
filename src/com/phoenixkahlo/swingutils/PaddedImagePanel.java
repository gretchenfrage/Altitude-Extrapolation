package com.phoenixkahlo.swingutils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PaddedImagePanel extends ImagePanel {
	
	private static final long serialVersionUID = 724838436757720541L;
	
	private int imageX;
	private int imageY;
	private int bottomXPad;
	private int bottomYPad;
	private int topXPad;
	private int topYPad;
	
	
	public PaddedImagePanel(BufferedImage image, int imageX, int imageY, int bottomXPad, int bottomYPad, int topXPad, int topYPad) throws IOException {
		super(image);
		this.imageX = imageX;
		this.imageY = imageY;
		this.bottomXPad = bottomXPad;
		this.bottomYPad = bottomYPad;
		this.topXPad = topXPad;
		this.topYPad = topYPad;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(bottomXPad + imageX + topXPad, bottomYPad + imageY + topYPad);
	}
	
	@Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, bottomXPad + imageX + topXPad, bottomYPad + imageY + topYPad);
        graphics.drawImage(getImage(), bottomXPad, bottomYPad, imageX, imageY, Color.GREEN, null);
    }

}
