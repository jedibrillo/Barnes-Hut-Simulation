import java.awt.Color;
//A quadrant object that can self-subdivide. Important for creating a Barnes-Hut tree, since it holds quadrants.

public class Quad {
  
  private double xmid, ymid, length;
  //Create a square quadrant with a given length and midpoints (xmid,ymid)
	private Color color = new Color(0,255,0);
	
  public Quad(double xmid, double ymid, double length) {
    this.xmid=xmid;
    this.ymid=ymid; 
    this.length=length;
  }
  
  //How long is this quadrant?
  public double length() {
    return length;
  }
  
  //Check if the current quadrant contains a point
  //top and right borders belong to current Quad
  public boolean contains(double x, double y) {
    if (x<=this.xmid+this.length/2.0 && x>=this.xmid-this.length/2.0 && y<=this.ymid+this.length/2.0 && y>=this.ymid-this.length/2.0) {
	  return true;
    }
    else	return false;
  }
  //Create subdivisions of the current quadrant
  
  // Subdivision: Northwest quadrant
  public Quad NW() {
    Quad newquad = new Quad(this.xmid-this.length/4.0, this.ymid+this.length/4.0,this.length/2.0);
    return newquad;
  }
  
  // Subdivision: Northeast quadrant
  public Quad NE() {
    Quad newquad = new Quad(this.xmid+this.length/4.0, this.ymid+this.length/4.0,this.length/2.0);
    return newquad;
  }
  
  // Subdivision: Southwest quadrant
  public Quad SW() {
    Quad newquad = new Quad(this.xmid-this.length/4.0, this.ymid-this.length/4.0,this.length/2.0);
    return newquad;
  }
  
  // Subdivision: Southeast quadrant
  public Quad SE() {
    Quad newquad = new Quad(this.xmid+this.length/4.0, this.ymid-this.length/4.0,this.length/2.0);
    return newquad;
  }
  
	//draw the invoking Quad in Turtle graphics
	public void draw() {
		StdDraw.setPenColor(color);
		StdDraw.square(xmid, ymid, length/2.0);
	}
}