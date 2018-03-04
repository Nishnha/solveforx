/*
 * A Mandelbrot set renderer written in Java
 * Nishant Sinha
 * 1/21/2018
 */

import javax.swing.JFrame;
import java.awt.image.BufferedImage;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.DataBufferInt;
import java.awt.image.BufferStrategy;

public class Mandelbrot {

	private static final int MAX_ITERATIONS = 80;
	private static final int WINDOW_WIDTH = 1000;
	private static final int WINDOW_HEIGHT = 1000;

	private static JFrame createWindow() throws Exception {
		JFrame frame = new JFrame("Mandelbrot Set");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		return frame;
	}

	private static Canvas createCanvas(int width, int height) {
		Canvas canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		return canvas;
	}

	private static BufferedImage getBufferedImage(int width, int height) {
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		return img;
	}

	private static void setWindow(JFrame frame, Canvas canvas) {
		frame.add(canvas);
		frame.pack(); // shrink window to size of items
		frame.setVisible(true);
	}

	private static BufferStrategy getBufferStrategy(Canvas canvas) {
		canvas.createBufferStrategy(2);
		BufferStrategy strat = canvas.getBufferStrategy();
		return strat;
	}

	private static int[] getPixelBuffer(BufferedImage image) {
		return ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	}

	private static void renderMandelbrotSet(int[] pixelBuffer, int max, int width, int height) {
		for (int pixel = 0; pixel < pixelBuffer.length; pixel++) {

			// Shift the coordinate plane to x=[-2,1] y=[-1,1]
			double x = ((pixel % width) / (double) width) * 3.0 - 2.0;
			double y = ((pixel / width) / (double) height) * 2.0 - 1.0;

			if (isInMandelbrotSet(x, y, max)) {
				pixelBuffer[pixel] = 0xFFFFFF; // White
			} else {
				pixelBuffer[pixel] = 0x000000; // Black
			}
		}
	}

	private static boolean isInMandelbrotSet(double x, double y, int max) {
		int count = 0;

		double zx = x;
		double zy = y;

		while (x * x + y * y < 4) {
			// If, after max iterations, the (x, y) point is still in a circle of radius 2, the point converges.
			if (count > max) {
				return true;
			}

			// z = z^2 + c
			// (a + bi)^2 = (a + bi)(a + bi) = a^2 + abi + abi + (bi)^2 = a^2 - b^2 + 2abi	

			// real portion: a^2 - b^2
			// imaginatry portion: 2abi
			double new_zx = (x*x) - (y*y) + zx; // a^2 - b^2 + c
			y = 2*x*y + zy; // 2abi + c
			x = new_zx;

			count++;
		}

		// If the point falls outside of a circle of radius 2 then the point will diverge.
		return false;
	}

	public static void main(String args[]) {
		Canvas canvas;
		int[] pixelBuffer;
		Graphics graphics;
		BufferStrategy strat;
		BufferedImage img = getBufferedImage(WINDOW_WIDTH, WINDOW_HEIGHT);


		try {
			canvas = createCanvas(WINDOW_WIDTH, WINDOW_HEIGHT);
			setWindow(createWindow(), canvas);
			strat = getBufferStrategy(canvas);
			graphics = strat.getDrawGraphics();
			pixelBuffer = getPixelBuffer(img);

			renderMandelbrotSet(pixelBuffer, MAX_ITERATIONS, WINDOW_WIDTH, WINDOW_HEIGHT);

			graphics.drawImage(img, 0, 0, null);
			strat.show();			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}