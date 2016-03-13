package com.phoenixkahlo.rc;

import java.awt.image.BufferedImage;

public class Main {
/*
	public static void main(String[] args) throws IOException {
		readImages();
		int minY = bottom.getHeight() / 2 - 100;
		int maxY = bottom.getHeight() / 2 + 100;
		displayComparisons(minY, maxY, 42);
	}
	
	public static BufferedImage bottom;
	public static BufferedImage top;
	
	public static void displayComparisons(int minY, int maxY, int shift) throws IOException {
		JFrame frame = new JFrame();
		frame.setLayout(new FlowLayout());
		frame.add(new PaddedImagePanel(bottom, 500, 500, 0, 0, 0, 42));
		frame.add(new PaddedImagePanel(top, 500, 500, 0, 42, 0, 0));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public static void readImages() throws IOException {
		bottom = ImageIO.read(new File("bottom.jpeg")); // To be not shifted
		top = ImageIO.read(new File("top.jpeg")); // To be shifted
	}
	
	public static void computeComparisons(int minY, int maxY) throws IOException {
		Map<Integer, Double> comparisons = new HashMap<Integer, Double>();
		createCache();
		
		for (int shift = -200; shift <= 200; shift++) {
			comparisons.put(shift, sumDifference(bottom, top, minY, maxY, shift));
			writeCache(comparisons);
			System.out.println("computed shift " + shift);
		}
	}
	
	public static void analyseComparisons() throws IOException {
		Map<Integer, Double> comparisons = readCache();
		int lowestKey = Integer.MAX_VALUE;
		double lowestValue = Double.MAX_VALUE;
		for (Map.Entry<Integer, Double> entry : comparisons.entrySet()) {
			if (entry.getValue() < lowestValue) {
				lowestKey = entry.getKey();
				lowestValue = entry.getValue();
			}
		}
		System.out.println("Lowest key = " + lowestKey);
	}
	
	public static void createCache() throws IOException {
		File file = new File("comparisons.dat");
		file.createNewFile();
	}
	
	public static void writeCache(Map<Integer, Double> data) throws IOException {
		OutputStream out = new FileOutputStream(new File("comparisons.dat"));
		for (Map.Entry<Integer, Double> entry : data.entrySet()) {
			out.write(BinUtils.intToBytes(entry.getKey()));
			out.write(BinUtils.doubleToBytes(entry.getValue()));
		}
		out.close();
	}
	
	public static Map<Integer, Double> readCache() throws IOException {
		Map<Integer, Double> out = new HashMap<Integer, Double>();
		InputStream in = new FileInputStream(new File("comparisons.dat"));
		while (in.available() > 0) {
			byte[] keyBytes = new byte[4];
			byte[] valueBytes = new byte[8];
			in.read(keyBytes);
			in.read(valueBytes);
			int key = BinUtils.bytesToInt(keyBytes);
			double value = BinUtils.bytesToDouble(valueBytes);
			out.put(key, value);
		}
		in.close();
		return out;
	}
	
	/*
	 * Images assumed to be same width
	 */
	public static double sumDifference(BufferedImage normal, BufferedImage shifted, int minY, int maxY, int shift) {
		double sum = 0;
		for (int x = 0; x < normal.getWidth(); x++) {
			for (int y = minY; y <= maxY; y++) {
				sum += colorDifference(normal.getRGB(x, y), shifted.getRGB(x, y + shift));
			}
		}
		return sum;
	}
	
	public static double colorDifference(int c1, int c2) {
		return Math.sqrt(
				Math.pow(((c1 & 0x00FF0000) >> 16) - ((c2 & 0x00FF0000) >> 16), 2) + 
				Math.pow(((c1 & 0x0000FF00) >> 8) - ((c2 & 0x0000FF00) >> 8), 2) + 
				Math.pow((c1 & 0x000000FF) - (c2 & 0x000000FF), 2)
				);
	}

}
