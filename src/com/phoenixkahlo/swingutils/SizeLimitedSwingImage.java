package com.phoenixkahlo.swingutils;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import com.phoenixkahlo.utils.IntCallback;

public class SizeLimitedSwingImage extends SwingImage {

	private static final long serialVersionUID = -6872488998230466834L;
	
	private IntCallback widthLimiter;
	private IntCallback heightLimiter;
	
	public SizeLimitedSwingImage(File image, IntCallback widthLimiter, IntCallback heightLimiter) throws IOException {
		super(image);
		this.widthLimiter = widthLimiter;
		this.heightLimiter = heightLimiter;
	}
	
	public SizeLimitedSwingImage(BufferedImage image, IntCallback widthLimiter, IntCallback heightLimiter) throws IOException {
		super(image);
		this.widthLimiter = widthLimiter;
		this.heightLimiter = heightLimiter;
	}
	
	@Override
	public Dimension getPreferredSize() {
		int widthLimit = widthLimiter.invoke();
		int heightLimit = heightLimiter.invoke();
		if (widthLimit < heightToWidth(heightLimit)) { // If the width limiter might be limiting the draw size
			return new Dimension(widthLimit, widthToHeight(widthLimit));
		} else { // If the height limiter might be limiting the draw size
			return new Dimension(heightToWidth(heightLimit), heightLimit);
		}
	}

}
