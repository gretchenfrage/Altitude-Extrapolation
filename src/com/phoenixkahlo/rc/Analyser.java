package com.phoenixkahlo.rc;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.phoenixkahlo.utils.BinUtils;

public class Analyser {
		
	public static void main(String[] args) throws IOException {
		//int scanYMin = 2000;
		//int scanYMax = 2100;
		int shiftMin = -3000;
		int shiftMax = 3000;
		
		
		BufferedImage bottom = ImageIO.read(new File("bottom.jpeg"));
		BufferedImage top = ImageIO.read(new File("top.jpeg"));
		
		//Map<Integer, Double> data = computeData(bottom, top, scanYMin, scanYMax, shiftMin, shiftMax);
		//Map<Integer, Double> data = readData();
		/*
		AnalysisVisualizer.visualize(bottom, top, data, scanYMin, scanYMax);
		writeData(data);
		System.out.println("Data saved");
		*/
		for (int scanYMin = 0; scanYMin < bottom.getHeight() - 100; scanYMin += 100) {
			Map<Integer, Double> data = computeData(bottom, top, scanYMin, scanYMin + 100, shiftMin, shiftMax);
			writeData(data, "bands" + File.separator + scanYMin + "-" + (scanYMin + 100) + ".dat");
			System.out.println("completed range " + scanYMin + "-" + (scanYMin + 100) + ".dat");
		}
	}
	
	public static Map<Integer, Double> readData(String filename) throws IOException {
		Map<Integer, Double> shifts = new HashMap<Integer, Double>();
		InputStream in = new FileInputStream(new File(filename));
		while (in.available() > 0) {
			shifts.put(BinUtils.readInt(in), BinUtils.readDouble(in));
		}
		return shifts;
	}
	
	public static Map<Integer, Double> computeData(BufferedImage unshifted, BufferedImage shifted,
			int minY, int maxY, int minShift, int maxShift) throws IOException {
		Map<Integer, Double> data = new HashMap<Integer, Double>();
		for (int shift = minShift; shift <= maxShift; shift++) {
			try {
				data.put(shift, computeShift(unshifted, shifted, minY, maxY, shift));
			} catch (Exception e) {
			}
		}
		return data;
	}
	
	private static double computeShift(BufferedImage unshifted, BufferedImage shifted, int minY, int maxY, int shift) 
			throws Exception {
		double out = 0;
		for (int x = 0; x < unshifted.getWidth(); x++) {
			for (int y = minY; y <= maxY; y++) {
				if (y > 0 && y < unshifted.getHeight() && y - shift > 0 && y - shift < shifted.getHeight())
					out += colorDistance(unshifted.getRGB(x, y), shifted.getRGB(x, y - shift));	
				else
					throw new Exception();
			}
		}
		return out;
	}
	
	public static double colorDistance(int c1, int c2) {
		return Math.sqrt(
				Math.pow(((c1 & 0x00FF0000) >> 16) - ((c2 & 0x00FF0000) >> 16), 2) + 
				Math.pow(((c1 & 0x0000FF00) >> 8) - ((c2 & 0x0000FF00) >> 8), 2) + 
				Math.pow((c1 & 0x000000FF) - (c2 & 0x000000FF), 2)
				);
	}
	
	public static void writeData(Map<Integer, Double> data, String name) throws IOException {
		OutputStream out = new FileOutputStream(new File(name));
		for (int key : data.keySet()) {
			BinUtils.writeInt(out, key);
			BinUtils.writeDouble(out, data.get(key));
		}
	}
		
}
