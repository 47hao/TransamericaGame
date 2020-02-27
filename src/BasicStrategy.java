public class BasicStrategy extends Strategy {
	public String getName() {
		return "Basic Strategy";
	}


	@Override
	public Rail returnRail(City[] targetCities, Rail[] possibleRails, Board wholeBoard) {
		int index = (int)Math.random() * possibleRails.length;
		return possibleRails[index];
	}
}
