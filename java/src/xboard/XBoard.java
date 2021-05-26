package xboard;

import java.util.HashMap;
import java.util.HashSet;

public class XBoard {
	private static XBoard instance;
	private boolean isPause = false;
	private static Moves moves = null;
	private boolean playingWhite = false;
	private Integer makedMoves = 0;
	private Integer whiteCheck = 0;
	private Integer blackCheck = 0;
	private HashMap<String, Integer> stateOfGameWhite = new HashMap<>();
	private HashMap<String, Integer> stateOfGameBlack = new HashMap<>();

	private XBoard() {
	}

	public static XBoard getInstance() {
		if (instance == null) {
			instance = new XBoard();
			moves = Moves.getInstance();
		}

		return instance;
	}

	/**
	 * Checks if the game is in pause
	 *
	 * @return
	 */
	public boolean isPause() {
		return isPause;
	}

	/**
	 * Sets the game status
	 *
	 * @param pause
	 */
	public void setPause(boolean pause) {
		isPause = pause;
	}

	/**
	 * Resets the move instance
	 */
	public void reset() {
		Moves.makeNewBoard();
		makedMoves = 0;
		whiteCheck = 0;
		blackCheck = 0;
		stateOfGameWhite = new HashMap<>();
		stateOfGameBlack = new HashMap<>();
		moves = Moves.getInstance();
	}

	/**
	 * Sends a move command to the xboard
	 */
	public void makeNewMove() {
		if (!this.isPause) {
			String oldChessboard = Moves.getInstance().getChessBoard();
			String output = (new AlphaBetaPruning()).chooseTheBestMove();
			if (output == null) {
				System.out.println("1-0 {mat}");
				return;
			}

			if (sameState(stateOfGameWhite, stateOfGameBlack)) return;
			System.out.println(output);
			setPlayingWhite(!isPlayingWhite());
			Moves.getInstance().setMadeMoves(new HashSet<>());
			if (Moves.getInstance().getKing().inCheck(Moves.getInstance().getChessBoard())) {
				// 3 check rule
				if (!isPlayingWhite()) {
					this.whiteCheck++;
					System.out.println("Da 1" + this.whiteCheck);
					if (this.whiteCheck == 3) {
						System.out.println("1-0 {White mates (3check)}");
						return;
					}
				} else {
					this.blackCheck++;
					System.out.println("da 2 " + this.blackCheck);
					if (this.blackCheck == 3) {
						System.out.println("0-1 {Black mates (3check)}");
						return;
					}
				}
			}
			setPlayingWhite(!isPlayingWhite());
			if (pat()) return;
			char thirdChar = output.charAt(7);
			char fourthChar = output.charAt(8);
			newPos(oldChessboard, thirdChar, fourthChar);
		}

	}

	private boolean pat() {
		int len = Moves.getInstance().getChessBoard().length();
		int numberOfPiecesOnBoard = 0;
		for (int i = 0; i < len; i++) {
			char c = Moves.getInstance().getChessBoard().charAt(i);
			if (c != '*' && c != '/') {
				numberOfPiecesOnBoard++;
			}
		}
		if (numberOfPiecesOnBoard == 2) {
			System.out.println("1/2-1/2 {Stalemate}");
			return true;
		}
		return false;
	}

	private void newPos(String oldChessboard, char thirdChar, char fourthChar) {
		int newPos;
		newPos = (thirdChar - 'a') + (fourthChar - '0' - 1) * 9 + 1;
		if (oldChessboard.charAt(newPos) != '*') {
			makedMoves = 0;
		} else {
			makedMoves++;
		}
		if (makedMoves == 50) {
			System.out.println("1/2-1/2 {50 moves no capture}");
		}
	}

	/**
	 * Update the chessboard.
	 *
	 * @param command e2e4 for example
	 */
	public void getPlayerMove(String command) {
		String oldChessboard = Moves.getInstance().getChessBoard();
		moves.getPlayerMove(command);
		if (sameState(stateOfGameBlack, stateOfGameWhite)) return;
		Moves.getInstance().setMadeMoves(new HashSet<>());
		if (Moves.getInstance().getKing().inCheck(Moves.getInstance().getChessBoard())) {
			// 3 check rule
			if (!isPlayingWhite()) {
				this.whiteCheck++;
				System.out.println("Da 3 " + this.whiteCheck);
				if (this.whiteCheck == 3) {
					System.out.println("1-0 {White mates (3check)}");
					return;
				}
			} else {
				this.blackCheck++;
				System.out.println("Da 4 " + this.blackCheck);
				if (this.blackCheck == 3) {
					System.out.println("0-1 {Black mates (3check)}");
					return;
				}
			}
		}
		char thirdChar = command.charAt(2);
		char fourthChar = command.charAt(3);
		if (pat()) return;
		newPos(oldChessboard, thirdChar, fourthChar);

	}

	private boolean sameState(HashMap<String, Integer> stateOfGameBlack, HashMap<String, Integer> stateOfGameWhite) {
		if (isPlayingWhite()) {
			if (addToHash(stateOfGameBlack)) return true;
		} else {

			if (addToHash(stateOfGameWhite)) return true;
		}
		return false;
	}

	private boolean addToHash(HashMap<String, Integer> stateOfGameBlack) {
		stateOfGameBlack.computeIfAbsent(Moves.getInstance().getChessBoard(), s -> 0);
		int oldVal = stateOfGameBlack.get(Moves.getInstance().getChessBoard());
		stateOfGameBlack.put(Moves.getInstance().getChessBoard(), oldVal + 1);

		if (oldVal + 1 == 3) {
			System.out.println("1/2-1/2 {Draw by 3 same state}");
			return true;
		}
		return false;
	}

	/**
	 * Use this function for COLOR.WHITE
	 * or COLOR.BLACK
	 * <p>
	 * If the color changes we set this
	 * and here we'll call setAIPiecesForColorChange
	 *
	 * @param value true is we play with white
	 */
	public void setPlayingWhite(boolean value) {
		playingWhite = value;

		Moves.getInstance().setAIPiecesForColorChange(playingWhite);
	}

	public boolean isPlayingWhite() {
		return playingWhite;
	}


	public Integer getMakedMoves() {
		return makedMoves;
	}

	public void setMakedMoves(Integer makedMoves) {
		this.makedMoves = makedMoves;
	}
}
