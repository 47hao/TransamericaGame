
public class BasicStrategy extends Strategy{
	public String getName() {
		return "Basic Strategy";
	}

	public Rail returnRail(possibleRails[]) {
		int index=Math.random()*possibleRails.length;
		return possibleRails[index];
	}
}
