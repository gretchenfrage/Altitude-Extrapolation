package com.phoenixkahlo.swingutils;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class SetSizeSwingImage extends SizeLimitedSwingImage {

	private static final long serialVersionUID = -3114576456739491980L;

	public SetSizeSwingImage(BufferedImage image, int width, int height) throws IOException {
		super(image, () -> width, () -> height);
	}

}
