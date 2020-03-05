
public class Position {
	private int x, y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	int getX() {
		return x;
	}

	int getY() {
		return y;
	}

	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	public boolean equals(Position p) {
		if (getX() == p.getX() && getY() == p.getY()) {
			return true;
		}
		else {
			return false;
		}
	}
}

