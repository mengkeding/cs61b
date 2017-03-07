import java.util.Arrays;
import edu.princeton.cs.algs4.In;




public class NBody {


	public static double readRadius(String planetsTxtPath){
        In in = new In(planetsTxtPath);
		int numOfPlanets = in.readInt();
		double radius = in.readDouble();
		return radius;
	}

	public static Planet [] readPlanets(String planetsTxtPath){
        In in = new In(planetsTxtPath);
		int numOfPlanets = in.readInt();
		double radius = in.readDouble();
        Planet [] allPlanets = new Planet[numOfPlanets];
        for(int i = 0; i < numOfPlanets; i++){
			double xxPos = in.readDouble();
			double yyPos = in.readDouble();
			double xxVel = in.readDouble();
			double yyVel = in.readDouble();
			double mass = in.readDouble();
	        String imgFileName = in.readString();
	        allPlanets[i] = new Planet(xxPos,yyPos,xxVel,yyVel,mass,imgFileName);
        }
		return allPlanets;
	}

/**Store the 0th and 1st command line arguments as doubles named T and dt.
Store the 2nd command line argument as a String named filename.
Read in the planets and the universe radius from the file described by filename 
using your methods from earlier in this assignment. */
	
	public static void main(String[] args){
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
	    Planet [] allPlanets = readPlanets(filename);
        Double radius = readRadius(filename);
        // First,set the scale so that it matches the radius of the universe,
        // Then draw the image starfield.jpg as the background.
        StdDraw.setScale(-2 * radius, 2 * radius);
        StdDraw.picture(0, 0, "./images/starfield.jpg");
        for (Planet p : allPlanets) {
        	p.draw();
        }
        // StdDraw.enableDoubleBuffering();

        for(double t = 0; t <= T; t += dt) {
        	double[] xForces = new double [allPlanets.length];
        	double[] yForces = new double [allPlanets.length];
        	for(int i = 0; i < allPlanets.length; i++) {
        		xForces [i] = allPlanets[i].calcNetForceExertedByX(allPlanets);
        		yForces [i] = allPlanets[i].calcNetForceExertedByY(allPlanets);  
        	}
        	for(int i = 0; i < allPlanets.length; i++) {
        	  allPlanets[i].update(dt, xForces [i], yForces [i]);
        	}
            StdDraw.picture(0, 0, "./images/starfield.jpg");
            for (Planet p : allPlanets) {
        	  p.draw();
            }
            StdDraw.show(10);
            //StdDraw.pause(10);
        }
        StdOut.printf("%d\n", allPlanets.length);
        StdOut.printf("%.2e\n",radius);
        for (Planet p : allPlanets) {
          StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",p.xxPos, p.yyPos, p.xxVel, p.yyVel, p.mass, p.imgFileName);
        }


	}


}
