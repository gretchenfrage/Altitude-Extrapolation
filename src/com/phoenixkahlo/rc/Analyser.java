package com.phoenixkahlo.rc;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.phoenixkahlo.swingutils.BasicFrame;

public class Analyser {
		
		public static void main(String[] args) throws IOException {
			/*
			Map<Integer, Double> map = new HashMap<Integer, Double>();
			for (int i = 50; i <= 100; i++) {
				map.put(i, Math.sin((double) i / 3) * 50 + 100);
			}
			Function function = new Function() {
				@Override
				public double invoke(double x) {
					return map.get((int) x);
				}
				@Override
				public boolean inDomain(double x) {
					return map.containsKey((int) x);
				}
			};
			for (double i = 50; i <= 100; i++) {
				System.out.println("f(" + i + ")=" + function.invoke(i));
			}
			FunctionGraph graph = new FunctionGraph(500, 500, 0, 500, 0, function.highestY(0, 500, 1), false);
			graph.addFunction(function, Color.RED);
			BasicFrame frame = new BasicFrame();
			frame.add(graph);
			frame.display();
			*/
			
			BufferedImage bottom = ImageIO.read(new File("bottom.jpeg"));
			BufferedImage top = ImageIO.read(new File("top.jpeg"));
			Map<Integer, Double> map = new HashMap<Integer, Double>();
			for (int i = 50; i <= 100; i++) {
				map.put(i, Math.sin((double) i / 50) * 50 + 100);
			}
			
			for (Map.Entry<Integer, Double> entry : map.entrySet()) {
				System.out.println("f(" + entry.getKey() + ")=" + entry.getValue());
			}
			AnalysisVisualizer visualizer = new AnalysisVisualizer(bottom, top, map);
			BasicFrame frame = new BasicFrame();
			frame.add(visualizer);
			frame.setResizable(false);
			frame.display();
			
		}
	/*
	public static void main(String[] args) throws IOException {
		BufferedImage bottom = ImageIO.read(new File("bottom.jpeg"));
		BufferedImage top = ImageIO.read(new File("top.jpeg"));
		display(bottom, top, 42);
	}
	
	public static void display(BufferedImage unshifted, BufferedImage shifted, int shift, Map<Integer, Double> results) throws IOException {
		FlowFrame frame = new FlowFrame();
		frame.setResizable(false);
		
		SizeLimitedImagePanel i1 = new SizeLimitedImagePanel(unshifted, () -> 500, () -> 500);
		JPanel p1 = new JPanel(new VerticallyFloatingLayout(700, (int) (350 - i1.getPreferredSize().getHeight() / 2)));
		p1.add(i1);
		frame.add(p1);
		
		SizeLimitedImagePanel i2 = new SizeLimitedImagePanel(shifted, () -> 500, () -> 500);
		JPanel p2 = new JPanel(new VerticallyFloatingLayout(700, (int) (350 - i1.getPreferredSize().getHeight() / 2) + shift));
		p2.add(i2);
		frame.add(p2);
		
		DoubleFunction function = new DoubleFunction() {
			@Override
			public double invoke(double x) {
				return results.get(x);
			}
			@Override
			public boolean inDomain(double x) {
				return results.containsKey(x);
			}
		};
		FunctionPanel i3 = new FunctionPanel(100, 700, (int) (350 - i1.getPreferredSize().getHeight() / 2),
				(int) (350 + i1.getPreferredSize().getHeight() / 2), 0, Integer.MAX_VALUE, false);
		
		frame.display();
/*
	/*
	private BufferedImage bottom; // Not shifted
	private BufferedImage top; // Shifted
	private Map<Integer, Double> shifts;
	private int minShift;
	private int maxShift;
	private int minY;
	private int maxY;
	
	public static void main(String[] args) throws IOException {
		Analyser analyser = new Analyser(-200, 200, 1900, 2100);
		//analyser.computeShifts();
		analyser.readShifts();
		analyser.showShifts();
	}
	
	public Analyser(int minShift, int maxShift, int minY, int maxY) throws IOException {
		bottom = ImageIO.read(new File("bottom.jpeg"));
		top = ImageIO.read(new File("top.jpeg"));
		this.minShift = minShift;
		this.maxShift = maxShift;
		this.minY = minY;
		this.maxY = maxY;
	}
	
	public void showShifts() throws IOException {
		int bestShift = bestShift();
		FlowFrame frame = new FlowFrame();
		frame.add(new PaddedImagePanel(bottom, bottom.getWidth() / 6, bottom.getHeight() / 6, 0, 0, 0, bestShift / 6));
		frame.add(new PaddedImagePanel(bottom, bottom.getWidth() / 6, bottom.getHeight() / 6, 0, bestShift / 6, 0, 0));
		frame.add(new FunctionPanel)
		frame.display();
	}
	
	private int bestShift() {
		int bestKey = -1;
		double bestValue = -1;
		for (int key : shifts.keySet()) {
			if (shifts.get(key) > bestValue) {
				bestKey = key;
				bestValue = shifts.get(key);
			}
		}
		return bestKey;
	}
	
	public void computeShifts() throws IOException {
		shifts = new HashMap<Integer, Double>();
		for (int shift = minShift; shift <= maxShift; shift++) {
			shifts.put(shift, sumDistance(shift));
			System.out.println("Shift " + shift + " == " + shifts.get(shift));
		}
		writeShifts();
	}
	
	private void writeShifts() throws IOException {
		OutputStream out = new FileOutputStream(new File("shifts.dat"));
		for (int key : shifts.keySet()) {
			BinUtils.writeInt(out, key);
			BinUtils.writeDouble(out, shifts.get(key));
		}
	}
	
	public void readShifts() throws IOException {
		shifts = new HashMap<Integer, Double>();
		InputStream in = new FileInputStream(new File("shifts.dat"));
		while (in.available() > 0) {
			shifts.put(BinUtils.readInt(in), BinUtils.readDouble(in));
		}
	}
	
	
	private double sumDistance(int shift) {
		double out = 0;
		for (int x = 0; x < bottom.getWidth(); x++) {
			for (int y = minY; y <= maxY; y++) {
				out += colorDistance(bottom.getRGB(x, y), top.getRGB(x, y - shift));
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
	*/
	
}
