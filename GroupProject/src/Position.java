import java.awt.Point;

public class Position {
	int x,y;
	public void setx(int x){
		this.x=x;
	}
	public void setY(int y) {
		this.y=y;
	}
	int getX() {
		return x;
	}
	int getY() {
		return y;
	}
	int getPixelX() {
		return 0;
		//returns Pixel value of x
	}
	int getPixelY() {
		return 0;
		//returns Pixel value of y
	}
	public Point pixelLoc() {
		return new Point(x,y);
	}
}
