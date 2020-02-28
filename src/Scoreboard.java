import java.util.ArrayList;
public class Scoreboard {
	Player players[];
	int[] coordinates=new int[players.length*2];
	Scoreboard(){
		players=new Player[6];
	}
	public void addScores(int[] scores) {
		for (int i=0; i<players.length; i++) {
			coordinates[i*2]= players[i].score;
		}
		for (int z=0; z<players.length; z++) {
			players[z].score=players[z].score + scores[z];
		}
		int i=0;
		for (int j=1; j<12 ; j=j+2) {
			coordinates[j]=players[i].score;
			i++;
		}
	}
	public int[] trainCoordinates() {
		return coordinates;
	}
	public ArrayList<String> gameOver() {
		ArrayList<String> winnerOrder = new ArrayList<String>();
		ArrayList<Integer> scores = new ArrayList<Integer>();
		for (int i=0; i<players.length; i++) {
			scores.add(players[i].score);
		}
		int highest=0;
		for (int a=0; a<scores.size(); a++) {
			int highestIndex=0;
			if (scores.get(a)>highest) {
				highest=scores.get(a);
				highestIndex=a;
			}
			if (a==scores.size()-1) {
				for (int z=0; z<players.length; z++) {
					if (highest==players[z].score) {
						winnerOrder.add(players[z].getName());
						scores.remove(highestIndex);
					}
				}

			}
		}
		return winnerOrder;
	}
}

