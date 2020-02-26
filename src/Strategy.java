public abstract class Strategy {

	abstract Rail returnRail(City[] targetCities, Rail[] possibleRails, Board wholeBoard);
}
