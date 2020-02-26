
public class BasicStrategy extends Strategy {
	public String getName() {
		return "Basic Strategy";
	}

	public Rail returnRail(Rail[] possibleRails) {
		int index = (int)Math.random() * possibleRails.length;
		return possibleRails[index];
	}
}
