
public class Rail {
	Position startPoint;
	Position endPoint;
	String state = "";
	boolean doubleRail = false;
	boolean placedByCPU;

	public Rail(Position startPoint, Position endPoint) {
		this(startPoint, endPoint, false, false);
	}

	public Rail(Position startPoint, Position endPoint, boolean doubleRail) {
		this(startPoint, endPoint, doubleRail, false);
	}

	public Rail(Position startPoint, Position endPoint, boolean doubleRail, boolean placedByCPU) {
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.doubleRail = doubleRail;
		this.placedByCPU = placedByCPU;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}

	Position startPos() {
		return startPoint;
	}

	Position endPos() {
		return endPoint;
	}
	public boolean equals(Rail otherRail) {
		if(startPoint.getX()==otherRail.startPoint.getX()&&startPoint.getY()==otherRail.startPoint.getY()&&
			endPoint.getX()==otherRail.endPoint.getX()&&endPoint.getY()==otherRail.endPoint.getY()) {
			return true;
		}
		return false;
	}
}
