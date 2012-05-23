import java.awt.Color;

public class BarnesHut {

    public static void main(String[] args) {
        final double dt = 0.1;                     // time quantum
        int N = StdIn.readInt();                   // number of particles
        double radius = StdIn.readDouble();        // radius of universe

		//few variables for display purposes
		long start, end;
		MovingAverage avTime = new MovingAverage(100); //lets get rolling average
		boolean showQuads = false, showCalcTime = false;
		
        // turn on animation mode and rescale coordinate system
        StdDraw.show(0);
        StdDraw.setXscale(-radius, +radius);
        StdDraw.setYscale(-radius, +radius);
		
		start = System.currentTimeMillis();
        // read in and initialize bodies
        Body[] bodies = new Body[N];               // array of N bodies
        for (int i = 0; i < N; i++) {
            double px   = StdIn.readDouble();
            double py   = StdIn.readDouble();
            double vx   = StdIn.readDouble();
            double vy   = StdIn.readDouble();
            double mass = StdIn.readDouble();
            int red     = StdIn.readInt();
            int green   = StdIn.readInt();
            int blue    = StdIn.readInt();
            Color color = new Color(red, green, blue);
            bodies[i]   = new Body(px, py, vx, vy, mass, color);
        }
		end = System.currentTimeMillis();
		System.out.println("Bodies read in: " + (double) ((end - start)/1000.0) + " seconds");
		
		// simulate the universe movement
        for (double t = 0.0; true; t += dt) {
			start = System.currentTimeMillis();

			//Build Quad (we are creating a universe here!)
			Quad q = new Quad(0,0,radius*2.0);
			BHTree thetree = new BHTree(q);
		
			//Populate universe with bodies (make sure body is in universe)
			for (int i = 0; i < N; i++) {
				 if (bodies[i].in(q)) thetree.insert(bodies[i]);
			}

            // update the forces and bodies if still in universe
            for (int i = 0; i < N; i++) {
                bodies[i].resetForce();
                if (bodies[i].in(q)) {
                  thetree.updateForce(bodies[i]);
				  
                  //Calculate the new positions
                  bodies[i].update(dt);
                }
            }
	
			end = System.currentTimeMillis();
			if(showCalcTime) System.out.println("Time ("+t+") computed in: " + (double) ((end - start)/1000.0) + " seconds");

			avTime.newNum(end - start);

            // draw the bodies
            StdDraw.clear(StdDraw.BLACK);
			String writeout = "Time: " + t;
			//StdDraw.text(0,0,writeout);
			if(showQuads) thetree.draw();
            for (int i = 0; i < N; i++) {
                bodies[i].draw();
            }
            StdDraw.show((int) avTime.getAvg());
			if ( StdDraw.hasNextKeyTyped() ) {
				char key = StdDraw.nextKeyTyped();
				if(key == 'd') showQuads = !showQuads;
				if(key == 't') showCalcTime = !showCalcTime;
				if(key == 'q') System.exit(0);
			}
        }
    }
}
