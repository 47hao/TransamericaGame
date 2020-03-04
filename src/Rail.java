
public class Rail {
	private Position startPoint;
	private Position endPoint;
	private String state = "";
	private boolean doubleRail = false;
	private boolean placedByCPU;
	
	final static String EMPTY = "empty";
	final static String NEW = "new";
	final static String PLACED = "placed";
	final static String BLINKING = "blinking";
	final static String HOVERING = "hovering";

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

	public Position startPos() {
		return startPoint;
	}

	public Position endPos() {
		return endPoint;
	}

	public boolean isDouble() {
		return doubleRail;
	}
}
