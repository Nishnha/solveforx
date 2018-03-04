# MANDELBROT SET (in Java)
There are three separate files for this lecture:
- SineWave.java
- Mandelbrot.java
- MandelbrotZoom.java

To run a file, with Java installed, navigate to where the file is saved in a terminal and run 
`javac <file>.java && java <file>`.
e.g. `javac Mandelbrot.java && java Mandelbrot`.

SineWave.java is meant to give an introduction to drawing a Java window, adding a canvas, rendering directly to that canvas, and animating the window contents.
Mandelbrot.java renders a mandelbrot set and contains comments explaining the conversion from the mandelbrot set equation to code.
The MandelbrotZoom.java file zooms in to a particular point in the mandelbrot set.

The next lecture will focus on optimizing the mandelbrot set render function. This will be explored through MandelbrotZoom.java. Since zooming in on a particular point can be intensive to calculate (as seen by running MandelbrotZoom.java), optimizations will allow for smoother zooming.

## Screenshots
![Mandelbrot Set Render](https://i.imgur.com/itgtH27.png)
