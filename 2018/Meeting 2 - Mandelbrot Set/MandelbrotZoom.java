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

public class MandelbrotZoom {

	private static final int MAX_ITERATIONS = 100;
	private static final int WINDOW_WIDTH = 1000;
	private static final int WINDOW_HEIGHT = 1000;
	private static final double X_OFFSET = -1.75;
	private static final double Y_OFFSET = 0;

	private static JFrame createWindow() {
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

	private static void renderMandelbrotSet(int[] pixelBuffer, int max, int width, int height, double zoom) {
		System.out.println(zoom);
		for (int pixel = 0; pixel < pixelBuffer.length; pixel++) {

			// Shift the coordinate plane to x=[-2,1] y=[-1,1]
			double x = ((pixel % width) / (double) width) * 3.0 - 2.0;
			double y = ((pixel / height) / (double) height) * 2.0 - 1.0;

			// Must zoom in by a power of 2 since the Mandelbrot set is calculated with z = z^2 + c
			x /= Math.pow(2, zoom);
			y /= Math.pow(2, zoom);

			if (isInMandelbrotSet(x + X_OFFSET, y + Y_OFFSET, max, zoom)) {
				pixelBuffer[pixel] = 0xFFFFFF;
			}
		}		
	}

	public static void clearImage(int[] p) {
		for (int i = 0; i < p.length; i++) {
			p[i] = 0x1f2e2e;
		}
	}

	private static boolean isInMandelbrotSet(double x, double y, int max, double zoom) {
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

		return false;
	}

	private static double getZoom(double startTime) {
		double currentTime = System.currentTimeMillis();
		double adjusted = (currentTime - startTime) / 3000;

		if (adjusted < 1) return 1;
		return adjusted;
	}

	public static void main(String args[]) {
		Canvas canvas;
		int[] pixelBuffer;
		Graphics graphics;
		BufferStrategy strat;
		BufferedImage img = getBufferedImage(WINDOW_WIDTH, WINDOW_HEIGHT);

		canvas = createCanvas(WINDOW_WIDTH, WINDOW_HEIGHT);
		setWindow(createWindow(), canvas);
		strat = getBufferStrategy(canvas);
		graphics = strat.getDrawGraphics();
		pixelBuffer = getPixelBuffer(img);
		

		double startTime = System.currentTimeMillis();
		while(true) {
			renderMandelbrotSet(pixelBuffer, MAX_ITERATIONS, WINDOW_WIDTH, WINDOW_HEIGHT, getZoom(startTime));
			graphics.drawImage(img, 0, 0, null);
			strat.show();
			clearImage(pixelBuffer);
		}
	}

}