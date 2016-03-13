package com.phoenixkahlo.rc;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import com.phoenixkahlo.swingutils.BasicFrame;
import com.phoenixkahlo.swingutils.FunctionGraph;
import com.phoenixkahlo.utils.Function;

public class SFdfsd {

	private static final int GRAPH_WIDTH = 300;
	
	public static void main(String[] args) {
		Map<Integer, Double> data = new HashMap<Integer, Double>();
		for (int i = -200; i <= 200; i++) {
			data.put(i, Math.sin((double) i / 10) * 50 + 100);
		}
		
		Function function = new Function() {
			@Override
			public double invoke(double x) {
				return data.get((int) x);
			}
			@Override
			public boolean inDomain(double x) {
				return data.containsKey((int) x);
			}
		};
		
		FunctionGraph graph = new FunctionGraph(
				GRAPH_WIDTH, 800,
				-400, 400,
				0, function.highestY(-400, 400, 1),
				false
				);
		graph.addFunction(new SwingTester.Parabola(), Color.RED);
		graph.addFunction(new SwingTester.IndentityFunction(), Color.BLACK);
		graph.addFunction(new SwingTester.SineFunction(), Color.GREEN);
		graph.addFunction(function, Color.BLUE);
		
		BasicFrame frame = new BasicFrame();
		frame.add(graph);
		frame.display();
	}

}
