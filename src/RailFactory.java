import java.awt.Polygon;
import java.util.ArrayList;

public class RailFactory {

    final static int offset = 2;

    final static int[] lDiagLengths = {2, 4, 6, 7, 8, 9, 10, 10, 11, 11, 10, 11, 10, 9, 9, 11, 11, 10, 5, 4};
    final static Position[] leftDiagonalPositions = {new Position(0, 0), new Position(1, 0), new Position(2, 0),
        new Position(3, 0), new Position(4, 0), new Position(5, 0), new Position(6, 0), new Position(7, 0),
        new Position(8, 0), new Position(9, 0), new Position(10, 1), new Position(11, 1), new Position(12, 2),
        new Position(13, 3), new Position(14, 3), new Position(15, 1), new Position(16, 1), new Position(17, 2),
        new Position(18, 7), new Position(19, 8)};
    
    final static Position[] rightDiagonalPositions = {new Position(2, 6), new Position(1, 4), new Position(0, 2),
        new Position(0, 1), new Position(0, 0), new Position(1, 0), new Position(2, 0), new Position(3, 0),
        new Position(4, 0), new Position(5, 0), new Position(6, 0), new Position(7, 0), new Position(8, 0),
        new Position(9, 0), new Position(11, 1), new Position(14, 3), new Position(15, 3), new Position(15, 2),
        new Position(15, 1), new Position(16, 1)};

    final static int[] rDiagLengths = {4, 7, 9, 11, 12, 12, 12, 12, 12, 12, 12, 12, 11, 10, 8, 5, 2, 2, 2, 1};

    final static Position[] horizontalPositions = {new Position(0, 0), new Position(0, 1), new Position(0, 2),
        new Position(1, 3), new Position(1, 4), new Position(2, 5), new Position(2, 6), new Position(3, 7),
        new Position(4, 8), new Position(5, 9), new Position(6, 10), new Position(8, 11), new Position(11, 12),
        new Position(15, 2), new Position(15, 1)};

    final static int[] horizontalLengths = {9, 11, 12, 16, 16, 15, 15, 15, 15, 14, 13, 11, 8, 2, 1};

    final static Position[] leftDiagonalDoubleRails = {   new Position(1,2), new Position(2, 0), new Position(3, 1), new Position(2,4), new Position(3,4), new Position(3,6), new Position(4, 8), new Position(5, 4), new Position()
    		
    		
    };
    final static Position[] rightDiagonalDoubleRails = { new Position(0,0), new Position(8,6)
    		
    		
    };
    final static Position[] rightDoubleRails = { new Position(4,4), new Position(10,10),
    		new Position(8,7)
		
		
    };

    public ArrayList<Rail> genRails() {
        System.out.println("debugging, rail at 4, 2 set to placed");

        ArrayList<Rail> rails = new ArrayList<Rail>();

        Position startPos;
        Position endPos;
        Rail r;

        for (int i = 0; i < leftDiagonalPositions.length; i++) {
            for (int j = 0; j < lDiagLengths[i]; j++) {
                startPos = new Position(leftDiagonalPositions[i].getX(), leftDiagonalPositions[i].getY() + j);
                endPos = new Position(startPos.getX(), startPos.getY() + 1);
                r = new Rail(startPos, endPos);
                r.setHitbox(new Polygon(new int[] {(int) GamePanel.gridToPixel(r.startPos()).getX() - offset, (int) GamePanel.gridToPixel(r.startPos()).getX() + offset,
                    (int) GamePanel.gridToPixel(r.endPos()).getX() + offset, (int) GamePanel.gridToPixel(r.endPos()).getX() - offset},
                    new int[] {(int) GamePanel.gridToPixel(r.startPos()).getY(), (int) GamePanel.gridToPixel(r.startPos()).getY(),
                    (int) GamePanel.gridToPixel(r.endPos()).getY(), (int) GamePanel.gridToPixel(r.endPos()).getY()}, 4));
                for(Position p : leftDiagonalDoubleRails)
                	if(startPos.equals(p))
                        r.setDouble();
                
                rails.add(r);
            }
        }
        // int leftD = rails.size();
        // System.out.println("left diagonal count: " + leftD);

        for (int i = 0; i < rightDiagonalPositions.length; i++) {
            for (int j = 0; j < rDiagLengths[i]; j++) {
                startPos = new Position(rightDiagonalPositions[i].getX() + j, rightDiagonalPositions[i].getY() + j);
                endPos = new Position(startPos.getX() + 1, startPos.getY() + 1);
                r = new Rail(startPos, endPos);
                r.setHitbox(new Polygon(new int[] {(int) GamePanel.gridToPixel(r.startPos()).getX() - offset, (int) GamePanel.gridToPixel(r.startPos()).getX() + offset,
                    (int) GamePanel.gridToPixel(r.endPos()).getX() + offset, (int) GamePanel.gridToPixel(r.endPos()).getX() - offset},
                    new int[] {(int) GamePanel.gridToPixel(r.startPos()).getY(), (int) GamePanel.gridToPixel(r.startPos()).getY(),
                    (int) GamePanel.gridToPixel(r.endPos()).getY(), (int) GamePanel.gridToPixel(r.endPos()).getY()}, 4));
                for(Position p : rightDiagonalDoubleRails)
                	if(startPos.equals(p))
                		r.setDouble();
                rails.add(r);
            }
        }

        // int rightD = rails.size() - leftD;
        // System.out.println("right diagonal count: " + rightD);

        for (int i = 0; i < horizontalPositions.length; i++) {
            for (int j = 0; j < horizontalLengths[i]; j++) {
                startPos = new Position(horizontalPositions[i].getX() + j, horizontalPositions[i].getY());
                endPos = new Position(startPos.getX() + 1, startPos.getY());
                r = new Rail(startPos, endPos);
                r.setHitbox(new Polygon(new int[] {(int) GamePanel.gridToPixel(r.startPos()).getX(), (int) GamePanel.gridToPixel(r.startPos()).getX(),
                    (int) GamePanel.gridToPixel(r.endPos()).getX(), (int) GamePanel.gridToPixel(r.endPos()).getX()},
                    new int[] {(int) GamePanel.gridToPixel(r.startPos()).getY() + offset, (int) GamePanel.gridToPixel(r.startPos()).getY() - offset,
                    (int) GamePanel.gridToPixel(r.endPos()).getY() - offset, (int) GamePanel.gridToPixel(r.endPos()).getY() + offset}, 4));
                for(Position p : rightDoubleRails)
                	if(startPos.equals(p))
                		r.setDouble();
                rails.add(r);
            }
        }

        // int horizontal = rails.size() - rightD - leftD;
        // System.out.println("horizontal count: " + horizontal);
        
        // for (Rail rail : rails) {
        //     if (rail.getState().equals(Rail.PLACED)) {
        //         for (int i = 0; i < 10; i++) {
        //             System.out.println("PLACED RAIL");
        //         }
        //     }
        // }
        for (Rail rail : rails) {
            rail.setState(Rail.EMPTY);
        }
        for (Rail rail : rails) {
            if (rail.startPos().equals(new Position(4, 0)) && rail.endPos().equals(new Position(4, 1))) {
                rail.setState(Rail.PLACED);
                System.out.println("placed rail");
            }
        }

        return rails;
    }

    //this method is literally copy and pasted from the ArrayList implementation
    //except for some reason, it works here but not in the contains() method
    public static int containsRail(Position p, ArrayList<Position> posList) {
        for (int i = 0; i < posList.size(); i++) {
                if (p.equals(posList.get(i)))
                    return i;
        }
        return -1;
    }
    /*public static void main(String[] args) {

        // System.out.println(new RailFactory().genRails().size());

        for (Rail r : new RailFactory().genRails()) {
            System.out.println(r);
        }
        ArrayList<Position> positions = new ArrayList<Position>();
        ArrayList<Rail> rails = new RailFactory().genRails();



        for (Rail rail : rails) {
            if (containsRail(rail.startPos(), positions) == -1) {
                positions.add(rail.startPos());
            }
            if (containsRail(rail.endPos(), positions) == -1) {
                positions.add(rail.endPos());
            }
        }

        // Position[] wrongs = {new Position(18, 5), new Position(18, 3), new Position(19, 4), new Position(18, 1), new Position(19, 2)};

        // for (Rail rail : rails) {
        //     for (int i = 0; i < wrongs.length; i++) {
        //         if (rail.startPos().equals(wrongs[i]) || rail.endPos().equals(wrongs[i])) {
        //             System.err.println("bad position at: ");
        //             System.out.println(wrongs[i]);
        //         }
        //     }
        // }




        System.out.println("number of positions: " + positions.size());
        System.out.println("number of rails: " + rails.size());

        // for (Position p : positions) {
        //     System.out.println(p);
        // }
        


        // System.out.println("lDiag positions length: " + leftDiagonalPositions.length);
        // System.out.println("lDiagLengths: " + lDiagLengths.length);
        // System.out.println("rDiag positions length: " + rightDiagonalPositions.length);
        // System.out.println("rDiagLengths: " + rDiagLengths.length);
        // System.out.println("h positions: " + horizontalPositions.length);
        // System.out.println("h lengths: " + horizontalLengths.length);

        // System.exit(1);

        // Position startPos;
        // Position endPos;

        // for (int i = 0; i < leftDiagonalPositions.length; i++) {
        //     for (int j = 0; j < lDiagLengths.length - 1; j++) {
        //         startPos = new Position(leftDiagonalPositions[i].getX(), leftDiagonalPositions[i].getY() + j);
        //         endPos = new Position(startPos.getX(), startPos.getY() + 1);
        //         rails.add(new Rail(startPos, endPos));
        //     }
        // }

        // for (int i = 0; i < rightDiagonalPositions.length; i++) {
        //     for (int j = 0; j < rDiagLengths.length - 1; j++) {
        //         startPos = new Position(leftDiagonalPositions[i].getX() + j, leftDiagonalPositions[i].getY() + j);
        //         endPos = new Position(startPos.getX() + 1, startPos.getY() + 1);
        //         rails.add(new Rail(startPos, endPos));
        //     }
        // }

        // for (int i = 0; i < horizontalPositions.length; i++) {
        //     for (int j = 0; j < horizontalLengths.length - 1; j++) {
        //         startPos = new Position(horizontalPositions[i].getX() + j, horizontalPositions[i].getY());
        //         endPos = new Position(startPos.getX() + 1, startPos.getY());
        //         rails.add(new Rail(startPos, endPos));
        //     }
        // }

        // for (Rail rail : rails) {
        //     // System.out.println("(" + rail.startPos().toString() + ", " + rail.endPos() + ")");
        //     System.out.println(rail.startPos() + " --> " + rail.endPos());
        // }

    }

    // public static void main(String[] args) {
    //     //we do this because if it has 2 _lines_ it needs 3 points
    //     // for (int i = 0; i < lengths.length; i++) {
    //     //     lengths[i]++;
    //     // }
    //     // System.out.println("linestarts: " + lineStarts.length);
    //     // System.out.println("lenghts: " + lengths.length);

    //     System.out.println("points: ");
    //     // int a = 1;
    //     Position startPos;
    //     Position endPos;
    //     for (int i = 0; i < leftDiagonalPositions.length; i++) {
    //         for (int j = 0; j < lengths[i]; j++) {
    //             // System.out.print("(" + lineStarts[i].getX() + ", " + (lineStarts[i].getY() + j) + "), ");
    //             // System.out.println(a++);
    //             startPos = new Position(i, j);
    //             // endPos = new Position
    //             // rails[i][j] = new Rail()
    //         }
    //         // System.out.println();
    //     }
    // }*/
}
