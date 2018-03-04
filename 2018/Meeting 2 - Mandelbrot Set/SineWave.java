/*
 * An animated Sine wave written in Java
 * Nishant Sinha
 * 1/21/2018
 */

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.DataBufferInt;
import java.awt.image.BufferStrategy;

public class SineWave {

	private static void createWindow() throws Exception {
		final int WINDOW_SIZE = 500;

		JFrame frame = new JFrame("Sinewave!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Canvas canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE));

		frame.add(canvas);
		frame.pack(); // shrink window to size of items

		BufferedImage img = new BufferedImage(WINDOW_SIZE, WINDOW_SIZE, BufferedImage.TYPE_INT_RGB);

		frame.setVisible(true);

		canvas.createBufferStrategy(2);
		BufferStrategy strat = canvas.getBufferStrategy();
		int[] pixelBuffer = getPixelBuffer(img);

		while (true) {
			long start = System.currentTimeMillis();

			Graphics g = strat.getDrawGraphics();
			
			//Render here
			clearImage(pixelBuffer);
			renderSineWave(WINDOW_SIZE, WINDOW_SIZE, pixelBuffer);
			g.drawImage(img, 0, 0, null);
			
			g.dispose();
			strat.show();


			long end = System.currentTimeMillis();
			long elapsed = end - start;
			if (elapsed < 16) {
				Thread.sleep(16 - elapsed);
			}
		}
	}

	private static int[] getPixelBuffer(BufferedImage image) {
		return ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	}

	private static void renderSineWave(int width, int height, int[] p) {
		final double RESOLUTION = 50;
		final double SPEED = 5;

		double time = System.currentTimeMillis();

		for (int x = 0; x < RESOLUTION * width; x++) {

			double y_position = (Math.sin((x / RESOLUTION / 60.0) + (time * (SPEED / 2000))) + 1) * (499 / 2.0);


			int y = (int) y_position;
			int pixel = (int) (x / RESOLUTION) + (y * width);

			p[pixel] = 0xccccff;
		}
	}

	public static void clearImage(int[] p) {
		for (int i = 0; i < p.length; i++) {
			p[i] = 0x1f2e2e;
		}
	}

	public static void main(String args[]) throws Exception {
		createWindow();
	}


}