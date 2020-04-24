
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class Board {
    final static String GS_MARKER = "marker";
    final static String GS_ROUND = "round";
    final static String GS_ROUND_END = "roundEnd";
    final static String GS_GAME_END = "gameEnd";

    private String gameState;
    // private Queue<Rail> newRails;
    // private Player activePlayer;
    // private final Position[] positions = new Position[188];

    public final ArrayList<Rail> rails = new ArrayList<Rail>();
    private ArrayList<Position> positions;
    private ArrayList<Ellipse2D> positionHitboxes;
    private ArrayList<Position> possiblePlacements = new ArrayList<Position>();
    private ArrayList<Player> playerArray = new ArrayList<Player>();

    public final static City[] cities = { new City(new Position(17, 2), "Boston", GamePanel.Orange),
            new City(new Position(17, 4), "New York", GamePanel.Orange),
            new City(new Position(17, 5), "Washington", GamePanel.Orange),
            new City(new Position(18, 7), "Richmond", GamePanel.Orange),
            new City(new Position(17, 8), "Winston", GamePanel.Orange),
            new City(new Position(19, 10), "Charleston", GamePanel.Orange),
            new City(new Position(19, 12), "Jacksonville", GamePanel.Orange),
            new City(new Position(17, 2), "Boston", GamePanel.Orange),

            new City(new Position(15, 2), "Buffalo", GamePanel.Blue),
            new City(new Position(13, 3), "Chicago", GamePanel.Blue),
            new City(new Position(15, 5), "Cincinnati", GamePanel.Blue),
            new City(new Position(10, 2), "Minneapolis", GamePanel.Blue),
            new City(new Position(3, 1), "Helena", GamePanel.Blue),
            new City(new Position(10, 1), "Duluth", GamePanel.Blue),
            new City(new Position(7, 1), "Bismark", GamePanel.Blue),

            new City(new Position(9, 4), "Omaha", GamePanel.Yellow),
            new City(new Position(13, 6), "St. Louis", GamePanel.Yellow),
            new City(new Position(11, 6), "Kansas City", GamePanel.Yellow),
            new City(new Position(11, 8), "Oklahoma City", GamePanel.Yellow),
            new City(new Position(8, 8), "Sante Fe", GamePanel.Yellow),
            new City(new Position(4, 4), "Salt Lake City", GamePanel.Yellow),
            new City(new Position(7, 5), "Denver", GamePanel.Yellow),

            new City(new Position(7, 9), "Phoenix", GamePanel.Red),
            new City(new Position(10, 11), "El Paso", GamePanel.Red),
            new City(new Position(13, 10), "Dallas", GamePanel.Red),
            new City(new Position(14, 12), "Houston", GamePanel.Red),
            new City(new Position(15, 9), "Memphis", GamePanel.Red),
            new City(new Position(17, 10), "Atlanta", GamePanel.Red),
            new City(new Position(16, 12), "New Orleans", GamePanel.Red),

            new City(new Position(0, 0), "Seattle", GamePanel.Green),
            new City(new Position(0, 1), "Portland", GamePanel.Green),
            new City(new Position(2, 5), "Sacramento", GamePanel.Green),
            new City(new Position(2, 6), "San Francisco", GamePanel.Green),
            new City(new Position(5, 9), "Los Angeles", GamePanel.Green),
            new City(new Position(6, 10), "San Diego", GamePanel.Green),
            new City(new Position(1, 3), "Medford", GamePanel.Green) };

    private int remainingRails;

    public Board() {
        // Orange: Boston, New York, Washington, Richmond, Winston, Charleston,
        // Jacksonville
        // Blue: Buffalo, Chicago, Cincinnati, Minneapolis, Helena, Duluth, Bismark
        // Yellow: Omaha, St.Louis, Kansas City, Oklahoma City, Sante Fe, Salt Lake
        // City, Denver
        // Red: Phoenix, El Paso, Dallas, Houston, Memphis, Atlanta, New Orleans
        // Green: Seattle, Portland, Sacremento, San Francisco, Los Angeles, San Diego,
        // Medford
        // cities[0] = new City(new Position(17, 2), "Boston", GamePanel.cityOrange);
        // cities[1] = new City(new Position(17, 4), "New York", GamePanel.cityOrange);
        // cities[2] = new City(new Position(17, 5), "Washington",
        // GamePanel.cityOrange);
        // cities[3] = new City(new Position(18, 7), "Richmond", GamePanel.cityOrange);
        // cities[4] = new City(new Position(17, 8), "Winston", GamePanel.cityOrange);
        // cities[5] = new City(new Position(19, 10), "Charleston",
        // GamePanel.cityOrange);
        // cities[6] = new City(new Position(19, 12), "Jacksonville",
        // GamePanel.cityOrange);

        // cities[7] = new City(new Position(15, 2), "Buffalo", GamePanel.cityBlue);
        // cities[8] = new City(new Position(13, 3), "Chicago", GamePanel.cityBlue);
        // cities[9] = new City(new Position(0, 0), "Cincinnati", GamePanel.cityBlue);
        // cities[10] = new City(new Position(10, 2), "Minneapolis",
        // GamePanel.cityBlue);
        // cities[11] = new City(new Position(3, 1), "Helena", GamePanel.cityBlue);
        // cities[12] = new City(new Position(10, 1), "Duluth", GamePanel.cityBlue);
        // cities[13] = new City(new Position(7, 1), "Bismark", GamePanel.cityBlue);

        // cities[14] = new City(new Position(9, 4), "Omaha", GamePanel.cityYellow);
        // cities[15] = new City(new Position(13, 6), "St. Louis",
        // GamePanel.cityYellow);
        // cities[16] = new City(new Position(11, 6), "Kansas City",
        // GamePanel.cityYellow);
        // cities[17] = new City(new Position(11, 8), "Oklahoma City",
        // GamePanel.cityYellow);
        // cities[18] = new City(new Position(8, 8), "Sante Fe", GamePanel.cityYellow);
        // cities[19] = new City(new Position(4, 4), "Salt Lake City",
        // GamePanel.cityYellow);
        // cities[20] = new City(new Position(7, 5), "Denver", GamePanel.cityYellow);

        // cities[21] = new City(new Position(7, 9), "Phoenix", GamePanel.cityRed);
        // cities[22] = new City(new Position(10, 11), "El Paso", GamePanel.cityRed);
        // cities[23] = new City(new Position(13, 10), "Dallas", GamePanel.cityRed);
        // cities[24] = new City(new Position(14, 12), "Houston", GamePanel.cityRed);
        // cities[25] = new City(new Position(15, 9), "Memphis", GamePanel.cityRed);
        // cities[26] = new City(new Position(17, 10), "Atlanta", GamePanel.cityRed);
        // cities[27] = new City(new Position(16, 12), "New Orleans",
        // GamePanel.cityRed);

        // cities[28] = new City(new Position(0, 0), "Seattle", GamePanel.cityGreen);
        // cities[29] = new City(new Position(0, 1), "Portland", GamePanel.cityGreen);
        // cities[30] = new City(new Position(2, 5), "Sacremento", GamePanel.cityGreen);
        // cities[31] = new City(new Position(2, 6), "San Francisco",
        // GamePanel.cityGreen);
        // cities[32] = new City(new Position(5, 9), "Los Angeles",
        // GamePanel.cityGreen);
        // cities[33] = new City(new Position(6, 10), "San Diego", GamePanel.cityGreen);
        // cities[34] = new City(new Position(1, 3), "Medford", GamePanel.cityGreen);

        RailFactory rf = new RailFactory();
        rails.addAll(rf.genRails());
        positions = rf.getPositions();
        positionHitboxes = rf.getPositionHitboxes();

        // newRails = new AbstractQueue<Rail>();
    }

    public void setRemainingRails(int r) {
        remainingRails = r;
    }

    public int getRemainingRails() {
        return remainingRails;
    }

    public ArrayList<Ellipse2D> getPositionHitboxes() {
        return positionHitboxes;
    }

    public ArrayList<Position> getPositions() {
        return positions;
    }

    public void addPlayer(Player p) {
        playerArray.add(p);
    }
    // public ArrayList quickestPath(Position initialNode, Position endNode) {
    // int leftLimit, rightLimit, topLimit, botLimit;
    // ArrayList<Rail> shortestPath= new ArrayList<Rail>(0);

    // if(initialNode.getX()<endNode.getX()) {
    // //The plus two is just in case there are quicker routes that are beyond the
    // original borders
    // leftLimit=initialNode.getX()+2;
    // rightLimit=endNode.getX()+2;
    // }else {
    // leftLimit=endNode.getX()+2;
    // rightLimit=initialNode.getX()+2;
    // }

    // if(initialNode.getY()<endNode.getY()+2) {
    // topLimit=initialNode.getY()+2;
    // botLimit=endNode.getY()+2;
    // }else {
    // topLimit=endNode.getY()+2;
    // botLimit=initialNode.getY()+2;
    // }

    // }

    public Rail getRail(Position start, Position end) {
        for (int i = 0; i < rails.size(); i++) {
            if (equals(start, rails.get(i).startPos()) && equals(end, rails.get(i).endPos())
                    || equals(end, rails.get(i).startPos()) && equals(start, rails.get(i).endPos())) {
                return rails.get(i);
            }
        }
        System.out.print("Board Class: No Such Rails");
        return null;
    }

    public ArrayList<Rail> getSurroundingRails(Position pos) {
        ArrayList<Rail> returnRails = new ArrayList<Rail>();
        for (Rail rail : rails) {
            Position railStart = rail.startPos();
            Position railEnd = rail.endPos();
            if ((railStart.getX() == pos.getX() && railStart.getY() == pos.getY())
                    || (railEnd.getX() == pos.getX() && railEnd.getY() == pos.getY())) {
                returnRails.add(rail);
            }
        }
        return returnRails;
    }

    public ArrayList<Rail> computeConnectedRails(Player p) {
        ArrayList<Rail> returnVal = new ArrayList<Rail>();
        ArrayList<Position> currentPositions = new ArrayList<Position>();
        boolean stop = false;
        currentPositions.add(p.getMarkerPos());
        while (!stop) {
            for (int i = 0; i < currentPositions.size(); i++) {
                Position aroundPos = currentPositions.get(i);
                for (Rail check : getSurroundingRails(aroundPos)) {
                    for (Rail r : rails) {
                        boolean duplicate = false;
                        for (Rail previous : returnVal) {
                            if (previous.equals(r)) {
                                duplicate = true;
                            }
                        }
                        if (!duplicate && (r.getState().equals(Rail.PLACED)) && r.equals(check)) {
                            returnVal.add(r);
                            if (aroundPos.equals(r.endPos()))
                                currentPositions.add(r.startPos());
                            else
                                currentPositions.add(r.endPos());
                            stop = false;
                        } else {

                        }
                    }
                }
                currentPositions.remove(aroundPos);
            }
            if (currentPositions.size() == 0) {
                stop = true;
            }
        }
        return returnVal;
    }

    public ArrayList<Rail> computePossiblePlacements(Player p) {
        ArrayList<Rail> returnVal = new ArrayList<Rail>();

        ArrayList<Rail> currentRails = computeConnectedRails(p);

        if (currentRails.size() == 0) // no rails have been placed yet
        {
            return getSurroundingRails(p.getMarkerPos());
        }

        for (Rail r : currentRails) {
            for (Rail r2 : getSurroundingRails(r.startPos())) {
                boolean duplicate = false;
                for (Rail check : returnVal) {
                    if (check.equals(r2)) {
                        duplicate = true;
                    }
                }
                for (Rail check : currentRails) {
                    if (check.equals(r2)) {
                        duplicate = true;
                    }
                }
                if (!duplicate && r2.getState() == Rail.EMPTY) {
                    returnVal.add(r2);
                }
            }
            for (Rail r2 : getSurroundingRails(r.endPos())) {
                boolean duplicate = false;
                for (Rail check : returnVal) {
                    if (check.equals(r2)) {
                        duplicate = true;
                    }
                }
                for (Rail check : currentRails) {
                    if (check.equals(r2)) {
                        duplicate = true;
                    }
                }
                if (!duplicate && r2.getState() == Rail.EMPTY) {
                    returnVal.add(r2);
                }
            }
        }
        return returnVal;
    }

    public int getDistancetoCity(Player p, City c) {

        Position target = c.getPos();
        ArrayList<Position> connectedPoints = new ArrayList<Position>();
        connectedPoints.addAll(p.getConnectedPoints());

        int min = 100;
        int index = -1;
        int minTest = 100;
        for (Position pos : p.getConnectedPoints()) {
            minTest = Math.min(min, Math.abs(pos.getX() - target.getX()) + Math.abs(pos.getY() - target.getY()));
            if (minTest < min) {
                min = minTest;
                index = p.getConnectedPoints().indexOf(pos);
            }
        }

        Position currentPos = p.getConnectedPoints().get(index);

        int distance = 0;
        min = 100;
        while (!target.equals(currentPos)) {
            ArrayList<Position> possibleNodes = new ArrayList<Position>();
            // calculate possible nodes
            for (Rail r : getSurroundingRails(currentPos)) {
                if (r.startPos().equals(currentPos) && !possibleNodes.contains(r.startPos())) {
                    possibleNodes.add(r.startPos());
                }
                if (r.endPos().equals(currentPos) && !possibleNodes.contains(r.endPos())) {
                    possibleNodes.add(r.endPos());
                }
            }

            ArrayList<Position> possibleNodesAddition = new ArrayList<Position>();
            for (Position node : possibleNodes) {
                Position[] newNodes = { new Position(node.getX() - 1, node.getY() - 1),
                        new Position(node.getX() + 1, node.getY() + 1), new Position(node.getX(), node.getY() - 1),
                        new Position(node.getX(), node.getY() + 1), new Position(node.getX() - 1, node.getY()),
                        new Position(node.getX() + 1, node.getY()), };
                for (int i = 0; i < newNodes.length; i++) {
                    if (positions.contains(newNodes[i])) {
                        // valid position check
                        possibleNodesAddition.add(newNodes[i]);
                    }
                }

            }
            possibleNodes.addAll(possibleNodesAddition);
            // positive distance

            // System.out.println("removing " + currentPos);
            possibleNodes.remove(currentPos);
            for (Position position : possibleNodes) {
                // System.out.println(position);
            }

            // System.out.println("targetPos: " + target);

            min = 100;
            index = -1;
            for (Position pos : possibleNodes) {
                // if (!alreadyFound) {
                minTest = Math.abs(pos.getX() - target.getX()) + Math.abs(pos.getY() - target.getY());
                if (minTest < min) {
                    min = minTest;
                    index = possibleNodes.indexOf(pos);
                    // System.out.println("pos: " + pos);
                    // System.out.println("currentPos: " + currentPos);
                    // alreadyFound = true;
                }
                // }
            }
            distance += getRail(possibleNodes.get(index), currentPos).isDouble() ? 2 : 1;
            currentPos = possibleNodes.get(index);
        }
        return distance;
    }

    // public ArrayList<Rail> quickestPath(Position a, Position b) {
    // ArrayList<Rail> possiblePaths = new ArrayList<Rail>(0);

    // int leftBound, rightBound, topBound, botBound;
    // if (a.getX() < b.getX()) {
    // leftBound = a.getX() - 2;
    // rightBound = b.getX() + 2;
    // } else {
    // if (a.getX() > b.getX()) {
    // leftBound = b.getX() - 2;
    // rightBound = a.getX() + 2;
    // } else {
    // leftBound = b.getX() - 2;
    // rightBound = a.getX() + 2;
    // }
    // }

    // if (a.getY() < b.getY()) {
    // topBound = a.getY() - 2;
    // botBound = b.getY() + 2;
    // } else {
    // if (a.getY() > b.getY()) {
    // topBound = b.getY() - 2;
    // botBound = a.getY() + 2;
    // } else {
    // topBound = b.getY() - 2;
    // botBound = a.getY() + 2;
    // }
    // }
    // int[] boundaries = { leftBound, rightBound, topBound, botBound };
    // possiblePaths.add();

    // }

    public ArrayList<Rail> quickestPath(Position a, Position b) {
        int leftBound, rightBound, topBound, botBound;
        if (a.getX() < b.getX()) {
            leftBound = a.getX() - 2;// -2+2
            rightBound = b.getX() + 2;
        } else {
            if (a.getX() > b.getX()) {
                rightBound = a.getX() + 2;// +2-2
                leftBound = b.getX() - 2;
            } else {
                rightBound = a.getX() + 2;// +2-2
                leftBound = a.getX() - 2;
                // they would be on a the same vertical line;
            }
        }

        if (a.getY() < b.getY()) {
            topBound = a.getY() - 2;// same as above
            botBound = b.getY() + 2;
        } else {
            if (a.getY() > b.getY()) {
                botBound = a.getY() + 2;
                topBound = b.getY() - 2;
            } else {
                botBound = a.getY() + 2;
                topBound = a.getY() - 2;
                // they would be on a the same horizontal line;
            }
        }

        ArrayList<Rail> restrictedRails = new ArrayList<Rail>();
        ArrayList<Position> restrictedPositions = new ArrayList<Position>();
        for (int i = 0; i < rails.size(); i++) {
            if (rails.get(i).startPos().getX() >= leftBound && rails.get(i).startPos().getX() <= rightBound
                    && rails.get(i).endPos().getX() >= leftBound && rails.get(i).endPos().getX() <= rightBound
                    && rails.get(i).startPos().getY() >= topBound && rails.get(i).startPos().getY() <= botBound
                    && rails.get(i).endPos().getY() >= topBound && rails.get(i).endPos().getY() <= botBound) {
                restrictedRails.add(rails.get(i));
            }
        }

        for (Rail r : restrictedRails) {
            Boolean startEqual = false;
            Boolean endEqual = false;
            for (Position p : restrictedPositions) {
                if (p.equals(r.startPos()))
                    startEqual = true;
                if (p.equals(r.endPos()))
                    endEqual = true;
            }
            if (!startEqual)
                restrictedPositions.add(r.startPos());
            if (!endEqual)
                restrictedPositions.add(r.endPos());
        }
        // System.out.println(restrictedPositions.size());

        ArrayList<ArrayList<Position>> paths = new ArrayList<ArrayList<Position>>();
        ArrayList<Position> initial = new ArrayList<Position>();
        initial.add(a);
        paths.add(initial);
        // ^gives the first moves
        // restricts the rails array to ones within the bounds
        ArrayList<ArrayList<Position>> returnArray = new ArrayList<ArrayList<Position>>();
        paths = makePaths(paths, b, restrictedPositions, returnArray, 30);
        ArrayList<ArrayList<Rail>> railPath = new ArrayList<ArrayList<Rail>>();
        for (ArrayList<Position> path : paths) {
            railPath.add(new ArrayList<Rail>());
            for (int j = 0; j < path.size() - 1; j++) {
                Rail r = new Rail(path.get(j), path.get(j + 1));
                railPath.get(railPath.size() - 1).add(getThisRail(rails, r.startPos(), r.endPos()));
            }
        }

        int shortestScore = 0;
        int shortestScoreIndex = 0;
        for (Rail r : railPath.get(0)) {
            if (r.isDouble())
                shortestScore += 2;
            else
                shortestScore++;
        }
        for (int l = 0; l < railPath.size(); l++) {
            int currentScore = 0;
            for (int c = 0; c < railPath.get(l).size(); c++) {
                if (railPath.get(l).get(c).isDouble())
                    currentScore += 2;
                else
                    currentScore++;
            }
            if (currentScore < shortestScore) {
                shortestScore = currentScore;
                shortestScoreIndex = l;
            }
            // System.out.println(currentScore + "Current Score");
        }
        // System.out.println(shortestScoreIndex);
        return railPath.get(shortestScoreIndex);

    }

    public ArrayList<ArrayList<Position>> makePaths(ArrayList<ArrayList<Position>> paths, Position b,
            ArrayList<Position> restrictedPositions, ArrayList<ArrayList<Position>> returnArray, int nth) {
        ArrayList<ArrayList<Position>> addedArrays = new ArrayList<ArrayList<Position>>();
        for (ArrayList<Position> currentPath : paths) {
            Position lastPosition = currentPath.get(currentPath.size() - 1);
            ArrayList<Position> around = new ArrayList<Position>();
            around.add(new Position(lastPosition.getX() + 1, lastPosition.getY()));
            around.add(new Position(lastPosition.getX() - 1, lastPosition.getY()));
            around.add(new Position(lastPosition.getX(), lastPosition.getY() + 1));
            around.add(new Position(lastPosition.getX(), lastPosition.getY() - 1));
            around.add(new Position(lastPosition.getX() + 1, lastPosition.getY() + 1));
            around.add(new Position(lastPosition.getX() - 1, lastPosition.getY() - 1));
            for (Position nextPosition : around) {
                Boolean repeat = repeat(currentPath, nextPosition);
                Boolean inBound = false;
                for (Position p : restrictedPositions) {
                    if (p.equals(nextPosition))
                        inBound = true;
                }
                Boolean endIsB = nextPosition.equals(b);
                if (!repeat && inBound) {

                    ArrayList<Position> addThisToAddedArray = new ArrayList<Position>();
                    for (Position p : currentPath)
                        addThisToAddedArray.add(p);
                    addThisToAddedArray.add(nextPosition);
                    addedArrays.add(addThisToAddedArray);
                    if (endIsB) {
                        returnArray.add(addThisToAddedArray);
                        addedArrays.remove(addedArrays.get(addedArrays.size() - 1));
                        if (returnArray.size() >= nth || addedArrays.size() == 0)
                            return returnArray;
                    }
                } else {

                }
            }

        }
        // System.out.println(paths.size()+" "+addedArrays.size());
        return makePaths(addedArrays, b, restrictedPositions, returnArray, nth);
    }

    public boolean railEquals(Rail r, Rail rail) {
        if (rail.startPos().equals(r.startPos()) && rail.endPos().equals(r.endPos())
                || rail.startPos().equals(r.endPos()) && rail.endPos().equals(r.startPos())) {
            return true;
        }
        return false;
    }

    public boolean repeat(ArrayList<Position> array, Position p) {
        for (Position pos : array) {
            if (pos.equals(p))
                return true;

        }
        return false;
    }

    public Rail setStartCoordinateTo(Rail r, Position p) {
        Position start = r.startPos();
        Position end = r.endPos();
        if (p.equals(r.startPos()))
            return r;
        else
            return new Rail(end, start);
    }

    public Rail getThisRail(ArrayList<Rail> rails, Position A, Position B) {
        for (Rail rail : rails) {
            if (rail.startPos().equals(A) && rail.endPos().equals(B)
                    || rail.startPos().equals(B) && rail.endPos().equals(A))
                return rail;
        }
        return null;
    }

    public boolean isDoubleRail(Position startPos, Position endPos) {
        for (int i = 0; i < rails.size(); i++) {
            Rail r = rails.get(i);
            if (equals(r.startPos(), startPos) && equals(r.endPos(), endPos)
                    || equals(r.startPos(), endPos) && equals(r.endPos(), startPos)) {
                if (rails.get(i).isDouble())
                    return true;
                return false;
            }
        }
        return false;
    }

    public boolean equals(Position a, Position b) {
        // only compares the x and y values, excludes state boolean
        if (a.getX() == b.getX() && a.getY() == b.getY())
            return true;
        return false;
    }

    public City getCity(Position location) {
        for (int i = 0; i < cities.length; i++) {
            Position cityLoc = cities[i].getPos();
            if (cityLoc.getX() == location.getX() && cityLoc.getY() == location.getY())
                return cities[i];
        }
        return null;
    }

    public void setRailState(Rail r, String state) {
        r.setState(state);
    }

    public ArrayList<Player> getPlayerArray() {
        return playerArray;
    }

    public ArrayList<Rail> getRails() {
        return rails;
    }

    public City[] getCities() {
        return cities;
    }

    // public void setActivePlayer(Player p) {
    // activePlayer = p;
    // }

    // public Player getActivePlayer() {
    // return activePlayer;
    // }

    public void setGameState(String state) {
        gameState = state;
    }

    public String getGameState() {
        return gameState;
    }

}
