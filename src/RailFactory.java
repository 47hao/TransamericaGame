import java.util.ArrayList;

public class RailFactory {

    static int[] lDiagLengths = {2, 4, 6, 7, 8, 9, 10, 10, 11, 11, 10, 11, 10, 9, 9, 11, 11, 10, 5, 4};
    static Position[] leftDiagonalPositions = {new Position(0, 0), new Position(1, 0), new Position(2, 0),
        new Position(3, 0), new Position(4, 0), new Position(5, 0), new Position(6, 0), new Position(7, 0),
        new Position(8, 0), new Position(9, 0), new Position(10, 1), new Position(11, 1), new Position(12, 2),
        new Position(13, 3), new Position(14, 3), new Position(15, 1), new Position(16, 1), new Position(17, 2),
        new Position(18, 7), new Position(19, 8)};
    
    static Position[] rightDiagonalPositions = {new Position(2, 5), new Position(1, 4), new Position(0, 2),
        new Position(0, 1), new Position(0, 0), new Position(1, 0), new Position(2, 0), new Position(3, 0),
        new Position(4, 0), new Position(5, 0), new Position(6, 0), new Position(7, 0), new Position(8, 0),
        new Position(9, 0), new Position(11, 1), new Position(14, 4), new Position(15, 4), new Position(16, 3),
        new Position(17, 2), new Position(18, 1)};
    static int[] rDiagLengths = {4, 7, 9, 11, 12, 12, 12, 12, 12, 12, 12, 12, 11, 9, 8, 5, 2, 2, 2, 1};

    static Position[] horizontalPositions = {new Position(0, 0), new Position(0, 1), new Position(0, 2),
        new Position(1, 3), new Position(1, 4), new Position(2, 5), new Position(2, 6), new Position(3, 7),
        new Position(4, 8), new Position(5, 9), new Position(6, 10), new Position(8, 11), new Position(11, 13),
        new Position(16, 2), new Position(17, 1)};

    static int[] horizontalLengths = {9, 11, 12, 16, 16, 15, 15, 15, 15, 14, 13, 11, 8, 2, 1};

    // final static Rail[][] rails = new Rail[188]; //XXX: find this number
    static ArrayList<Rail> rails = new ArrayList<Rail>();


    public static void main(String[] args) {

        // System.out.println("lDiag positions length: " + leftDiagonalPositions.length);
        // System.out.println("lDiagLengths: " + lDiagLengths.length);
        // System.out.println("rDiag positions length: " + rightDiagonalPositions.length);
        // System.out.println("rDiagLengths: " + rDiagLengths.length);
        // System.out.println("h positions: " + horizontalPositions.length);
        // System.out.println("h lengths: " + horizontalLengths.length);

        // System.exit(1);

        Position startPos;
        Position endPos;

        for (int i = 0; i < leftDiagonalPositions.length; i++) {
            for (int j = 0; j < lDiagLengths.length - 1; j++) {
                startPos = new Position(leftDiagonalPositions[i].getX(), leftDiagonalPositions[i].getY() + j);
                endPos = new Position(startPos.getX(), startPos.getY() + 1);
                rails.add(new Rail(startPos, endPos));
            }
        }

        for (int i = 0; i < rightDiagonalPositions.length; i++) {
            for (int j = 0; j < rDiagLengths.length - 1; j++) {
                startPos = new Position(leftDiagonalPositions[i].getX() + j, leftDiagonalPositions[i].getY() + j);
                endPos = new Position(startPos.getX() + 1, startPos.getY() + 1);
                rails.add(new Rail(startPos, endPos));
            }
        }

        for (int i = 0; i < horizontalPositions.length; i++) {
            for (int j = 0; j < horizontalLengths.length - 1; j++) {
                startPos = new Position(horizontalPositions[i].getX() + j, horizontalPositions[i].getY());
                endPos = new Position(startPos.getX() + 1, startPos.getY());
                rails.add(new Rail(startPos, endPos));
            }
        }

        for (Rail rail : rails) {
            // System.out.println("(" + rail.startPos().toString() + ", " + rail.endPos() + ")");
            System.out.println(rail.startPos() + " --> " + rail.endPos());
        }

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
    // }
}
