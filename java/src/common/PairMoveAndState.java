package common;

public class PairMoveAndState {
	public String chessboard;
	public String move;
	public int score;

	public PairMoveAndState(String chessboard, String move, int score) {
		this.chessboard = chessboard;
		this.move = move;
		this.score = score;
	}

	public static PairMoveAndState max(PairMoveAndState p1, PairMoveAndState p2) {
		if (p1.score > p2.score) {
			return p1;
		} else {
			return p2;
		}
	}

	public static PairMoveAndState min(PairMoveAndState p1, PairMoveAndState p2) {
		if (p1.score > p2.score) {
			return p2;
		} else {
			return p1;
		}
	}
}
