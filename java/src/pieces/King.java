package pieces;

import common.Pair;
import interfaces.KingInterface;
import xboard.Moves;
import xboard.XBoard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class King implements KingInterface {
	private Pair position;
	private Random random = new Random();

	public King(Pair position) {
		this.position = position;
	}

	@Override
	public Boolean inCheck(String chessboard) {
//		System.out.println(chessboard);

		if (Moves.getInstance().getMadeMoves().contains(chessboard)) {
			return true;
		}

		HashMap<String, Character> enemies = new HashMap<>();
		makeDictionary(enemies);
		int direction = Moves.getInstance().getDirectionController();

		if (position.y - 1 * direction < 8 && position.y >= 0) {
			if ((position.y - 1 * direction) * 9 + position.x + 2 > 0){
				if (chessboard.charAt((position.y - 1 * direction) * 9 + position.x + 2) == enemies.get("pawn") ||
						chessboard.charAt((position.y - 1 * direction) * 9 + position.x) == enemies.get("pawn")) {
					return true;
				}
			}
		}

		int[] dirxk = {0, 0, 1, -1, 1, 1, -1, -1};
		int[] diryk = {-1, 1, 0, 0, 1, -1, 1, -1};

		for (int i = 0; i < 8; i++) {
			if (position.y + diryk[i] < 8 && position.y + diryk[i] >= 0) {
				if (chessboard.charAt((position.y + diryk[i]) * 9 + position.x + dirxk[i] + 1) == enemies.get("king")) {
					return true;
				}
			}
		}

		// check for forward
		int i = 1;
		while (true) {
			if ((position.y - i * direction < 0) || (position.y - i * direction > 7)) {
				break;
			}

			if (chessboard.charAt((position.y - i * direction) * 9 + position.x + 1) == enemies.get("queen") ||
					chessboard.charAt((position.y - i * direction) * 9 + position.x + 1) == enemies.get("rook")) {
				return true;
			}

			if (chessboard.charAt((position.y - i * direction) * 9 + position.x + 1) != '*')  {
				break;
			}

			i++;
		}

		// check for back
		i = 1;
		while (true) {
			if ((position.y + i * direction < 0) || (position.y + i * direction > 7)) {
				break;
			}

			if (chessboard.charAt((position.y + i * direction) * 9 + position.x + 1) == enemies.get("queen") ||
					chessboard.charAt((position.y + i * direction) * 9 + position.x + 1) == enemies.get("rook")) {
				return true;
			}

			if (chessboard.charAt((position.y + i * direction) * 9 + position.x + 1) != '*')  {
				break;
			}

			i++;
		}

		// check for right
		i = 1;
		while (true) {

			if (chessboard.charAt((position.y) * 9 + position.x + i * direction + 1) == enemies.get("queen") ||
					chessboard.charAt((position.y) * 9 + position.x + i * direction + 1) == enemies.get("rook")) {
				return true;
			}

			if (chessboard.charAt((position.y) * 9 + position.x + i * direction + 1) != '*')  {
				break;
			}

			i++;
		}

		// check for left
		i = 1;
		while (true) {

			if (chessboard.charAt((position.y) * 9 + position.x - i * direction + 1) == enemies.get("queen") ||
					chessboard.charAt((position.y) * 9 + position.x - i * direction + 1) == enemies.get("rook")) {
				return true;
			}

			if (chessboard.charAt((position.y) * 9 + position.x - i * direction + 1) != '*')  {
				break;
			}

			i++;
		}

		// check first diag forward
		i = 1;
		while (true) {

//			System.out.println("print 1");
			if (position.y - i * direction < 0 || position.y - i * direction > 7) {
				break;
			}

			if (chessboard.charAt((position.y - i * direction) * 9 + (position.x + i * direction) + 1) == enemies.get("queen") ||
					chessboard.charAt((position.y - i * direction) * 9 + (position.x + i * direction) + 1) == enemies.get("bishop")) {
				return true;
			}

			if (chessboard.charAt((position.y - i * direction) * 9 + (position.x + i * direction) + 1) != '*') {
				break;
			}

			i++;
		}

		// check second diag forward
		i = 1;
		while (true) {

			if (position.y - i * direction < 0 || position.y - i * direction > 7) {
				break;
			}

			if (chessboard.charAt((position.y - i * direction) * 9 + (position.x - i * direction) + 1) == enemies.get("queen") ||
					chessboard.charAt((position.y - i * direction) * 9 + (position.x - i * direction) + 1) == enemies.get("bishop")) {
				return true;
			}

			if (chessboard.charAt((position.y - i * direction) * 9 + (position.x - i * direction) + 1) != '*') {
				break;
			}

			i++;
		}

		// check first diag backward
		i = 1;
		while (true) {

			if (position.y + i * direction < 0 || position.y + i * direction > 7) {
				break;
			}

			if (chessboard.charAt((position.y + i * direction) * 9 + (position.x - i * direction) + 1) == enemies.get("queen") ||
					chessboard.charAt((position.y + i * direction) * 9 + (position.x - i * direction) + 1) == enemies.get("bishop")) {
				return true;
			}

			if (chessboard.charAt((position.y + i * direction) * 9 + (position.x - i * direction) + 1) != '*') {
				break;
			}

			i++;
		}

		// check second diag backward
		i = 1;
		while (true) {

			if (position.y + i * direction < 0 || position.y + i * direction > 7) {
				break;
			}

			if (chessboard.charAt((position.y + i * direction) * 9 + (position.x + i * direction) + 1) == enemies.get("queen") ||
					chessboard.charAt((position.y + i * direction) * 9 + (position.x + i * direction) + 1) == enemies.get("bishop")) {
				return true;
			}

			if (chessboard.charAt((position.y + i * direction) * 9 + (position.x + i * direction) + 1) != '*') {
				break;
			}

			i++;
		}

		int[] diry = {1, 1, -1, -1, 2, 2, -2, -2};
		int[] dirx = {2, -2, 2, -2, 1, -1, 1, -1};

//		System.out.println("POZITIE REGE " + this.position.y + " " + this.position.x);
		
		for (i = 0; i < 8; i++) {
			if (position.y + diry[i] > 0 && position.y + diry[i] < 8 &&
					position.x + dirx[i] > 0 && position.x + dirx[i] < 8) {
				if (chessboard.charAt((position.y + diry[i]) * 9 + position.x + dirx[i] + 1) == enemies.get("knight")) {
//					System.out.println("dau true");
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Put lower case letters for playing black
	 * and upper case for white
	 *
	 * @param enemies dictionary for enemy pieces
	 */
	private void makeDictionary(HashMap<String, Character> enemies) {
		if (XBoard.getInstance().isPlayingWhite()) {
			enemies.put("queen", 'Q');
			enemies.put("rook", 'R');
			enemies.put("bishop", 'B');
			enemies.put("pawn", 'P');
			enemies.put("knight", 'N');
			enemies.put("king", 'K');
		} else {
			enemies.put("queen", 'q');
			enemies.put("rook", 'r');
			enemies.put("bishop", 'b');
			enemies.put("pawn", 'p');
			enemies.put("knight", 'n');
			enemies.put("king", 'k');
		}
	}

	@Override
	public Boolean inCheckMate(String chessboard) {
		return null;
	}

	@Override
	public String getOutOfCheck() {
		return null;
	}

	private String updateString(int newX, int newY) {
		String prevChessBoard = Moves.getInstance().getChessBoard();
		char[] charArray = prevChessBoard.toCharArray();


		charArray[position.y * 9 + position.x + 1] = '*';
		if (XBoard.getInstance().isPlayingWhite()) {
			charArray[newY * 9 + newX + 1] = 'k';
		} else {
			charArray[newY * 9 + newX + 1] = 'K';
		}

		String chessBoard = "";
		for (char i : charArray) {
			chessBoard += i;
		}
		return chessBoard;
	}

	// code 0
	@Override
	public String moveFirstDiagonalForward() {

		int direction = Moves.getInstance().getDirectionController();
		String chessBoard = updateString(position.x + 1 * direction, position.y - 1 * direction);

		Pair oldPosition = new Pair(position.x, position.y);

		this.position.x = position.x + 1 * direction;
		this.position.y = position.y - 1 * direction;

		if (inCheck(chessBoard)) {
			this.setPosition(oldPosition);
			return null;
		}

		String move = (char) (position.x + 'a') +
				String.valueOf((position.y + 1));

		return move;
	}

	// code 1
	@Override
	public String moveFirstDiagonalBackwards() {

		int direction = Moves.getInstance().getDirectionController();
		String chessBoard = updateString(position.x - 1 * direction, position.y + 1 * direction);

		Pair oldPosition = new Pair(position.x, position.y);

		this.position.x = position.x - 1 * direction;
		this.position.y = position.y + 1 * direction;

		if (inCheck(chessBoard)) {
			this.setPosition(oldPosition);
			return null;
		}

		String move = (char) (position.x + 'a') +
				String.valueOf((position.y + 1));

		return move;
	}

	// code 2
	@Override
	public String moveSecondDiagonalForward() {

		int direction = Moves.getInstance().getDirectionController();
		String chessBoard = updateString(position.x - 1 * direction, position.y - 1 * direction);

		Pair oldPosition = new Pair(position.x, position.y);

		this.position.x = position.x - 1 * direction;
		this.position.y = position.y - 1 * direction;

		if (inCheck(chessBoard)) {
			this.setPosition(oldPosition);
			return null;
		}

		String move = (char) (position.x + 'a') +
				String.valueOf((position.y + 1));

		return move;
	}

	// code 3
	@Override
	public String moveSecondDiagonalBackwards() {

		int direction = Moves.getInstance().getDirectionController();
		String chessBoard = updateString(position.x + 1 * direction, position.y + 1 * direction);

		Pair oldPosition = new Pair(position.x, position.y);

		this.position.x = position.x + 1 * direction;
		this.position.y = position.y + 1 * direction;

		if (inCheck(chessBoard)) {
			this.setPosition(oldPosition);
			return null;
		}

		String move = (char) (position.x + 'a') +
				String.valueOf((position.y + 1));

		return move;
	}

	// code 4
	@Override
	public String moveForward() {

		int direction = Moves.getInstance().getDirectionController();
		String chessBoard = updateString(position.x, position.y - 1 * direction);

		Pair oldPosition = new Pair(position.x, position.y);

		this.position.x = position.x;
		this.position.y = position.y - 1 * direction;

		if (inCheck(chessBoard)) {
			this.setPosition(oldPosition);
			return null;
		}

		String move = (char) (position.x + 'a') +
				String.valueOf((position.y + 1));

		return move;
	}

	// code 5
	@Override
	public String moveLeft() {

		int direction = Moves.getInstance().getDirectionController();
		String chessBoard = updateString(position.x - 1 * direction, position.y);

		Pair oldPosition = new Pair(position.x, position.y);

		this.position.x = position.x - 1 * direction;
		this.position.y = position.y;

		if (inCheck(chessBoard)) {
			this.setPosition(oldPosition);
			return null;
		}

		String move = (char) (position.x + 'a') +
				String.valueOf((position.y + 1));

		return move;
	}

	// code 6
	@Override
	public String moveRight() {

		int direction = Moves.getInstance().getDirectionController();
		String chessBoard = updateString(position.x + 1 * direction, position.y);

		Pair oldPosition = new Pair(position.x, position.y);

		this.position.x = position.x + 1 * direction;
		this.position.y = position.y;

		if (inCheck(chessBoard)) {
			this.setPosition(oldPosition);
			return null;
		}

		String move = (char) (position.x + 'a') +
				String.valueOf((position.y + 1));

		return move;
	}

	// code 7
	@Override
	public String moveBack() {

		int direction = Moves.getInstance().getDirectionController();
		String chessBoard = updateString(position.x, position.y + 1 * direction);

		Pair oldPosition = new Pair(position.x, position.y);

		this.position.x = position.x;
		this.position.y = position.y + 1 * direction;

		if (inCheck(chessBoard)) {
			this.setPosition(oldPosition);
			return null;
		}

		String move = (char) (position.x + 'a') +
				String.valueOf((position.y + 1));

		return move;
	}

	@Override
	public String makeValidMove(String chessBoard) {
		String move = null;
		ArrayList<Integer> possibleMoves = new ArrayList<>();

//		System.out.println(position.x + " " + position.y);

		// change depending on with what pieces the AI plays
		Integer direction = Moves.getInstance().getDirectionController();
		String enemyArray = Moves.getInstance().getEnemyArray() + "*";
//		System.out.println(enemyArray);

		// check validity for the first diagonal forward move
		// code 0
		if ((((position.y - (1 * direction)) * 9 + (position.x + (1 * direction)) + 1) >= 0) &&
				(((position.y - (1 * direction)) * 9 + (position.x + (1 * direction)) + 1) < 73) &&
				(enemyArray.indexOf(chessBoard.charAt((
						position.y - (1 * direction)) * 9 + (position.x + (1 * direction)) + 1)) >= 0)) {
			possibleMoves.add(0);
		}
		// check validity for the first diagonal backward left
		// code 1
		if ((((position.y + (1 * direction)) * 9 + (position.x - (1 * direction) + 1)) >= 0) &&
				(((position.y + (1 * direction)) * 9 + (position.x - (1 * direction) + 1)) < 73) &&
				(enemyArray.indexOf(chessBoard.charAt(
						(position.y + (1 * direction)) * 9 + (position.x - (1 * direction) + 1))) >= 0)) {
			possibleMoves.add(1);
		}
		// check validity for the second diagonal forward
		// code 2
		if ((((position.y - (1 * direction)) * 9 + ((position.x - (1 * direction)) + 1)) >= 0) &&
				(((position.y - (1 * direction)) * 9 + (position.x - (1 * direction) + 1)) < 73) &&
				(enemyArray.indexOf(chessBoard.charAt(
						(position.y - (1 * direction)) * 9 + (position.x - (1 * direction) + 1))) >= 0)) {
			possibleMoves.add(2);
		}
		// check validity for the second diagonal backwards
		// code 3
		if ((((position.y + (1 * direction)) * 9 + (position.x + (1 * direction) + 1)) >= 0) &&
				(((position.y + (1 * direction)) * 9 + (position.x + (1 * direction) + 1)) < 73) &&
				(enemyArray.indexOf(chessBoard.charAt((
						position.y + (1 * direction)) * 9 + (position.x + (1 * direction) + 1))) >= 0)) {
			possibleMoves.add(3);
		}
		// check validity for the forward move
		// code 4
		if ((((position.y - (1 * direction)) * 9 + (position.x) + 1) >= 0) &&
				(((position.y - (1 * direction)) * 9 + (position.x ) + 1) < 73) &&
				(enemyArray.indexOf(chessBoard.charAt((
						position.y - (1 * direction)) * 9 + (position.x) + 1)) >= 0)) {
			possibleMoves.add(4);
		}
		// check validity for the move left
		// code 5
		if ((((position.y) * 9 + (position.x  - (1 * direction) + 1)) >= 0) &&
				(((position.y) * 9 + (position.x  - (1 * direction) + 1)) < 73) &&
				(enemyArray.indexOf(chessBoard.charAt((
						position.y) * 9 + (position.x - (1 * direction) + 1))) >= 0)) {
			possibleMoves.add(5);
		}
		// check validity for the move right
		// code 6
		if ((((position.y) * 9 + (position.x  + (1 * direction) + 1)) >= 0) &&
				(((position.y) * 9 + (position.x  + (1 * direction) + 1)) < 73) &&
				(enemyArray.indexOf(chessBoard.charAt((
						position.y) * 9 + (position.x + (1 * direction) + 1))) >= 0)) {
			possibleMoves.add(6);
		}
		// check validity for the backwards move
		// code 7
		if ((((position.y + (1 * direction)) * 9 + (position.x) + 1) >= 0) &&
				(((position.y + (1 * direction)) * 9 + (position.x) + 1) < 73) &&
				(enemyArray.indexOf(chessBoard.charAt((
						position.y + (1 * direction)) * 9 + (position.x) + 1)) >= 0)) {
			possibleMoves.add(7);
		}

		// there can be not moves made with this piece
		if (possibleMoves.size() == 0) {
			return null;
		}

		char[] charArray = chessBoard.toCharArray();

		// Choose at random a move to make on this piece
		// The moves have different weights
		StringBuilder newMove = new StringBuilder();

		newMove.append((char) ('a' + position.x));
		newMove.append((position.y + 1));

		Pair oldPosition = new Pair(position.x, position.y);

		int randomChosenNumber = random.nextInt(possibleMoves.size());

		String tempMove = chooseTheMove(possibleMoves.get(randomChosenNumber));


		possibleMoves.remove(randomChosenNumber);
		// loop until find a move that does not give check
		while (tempMove == null && possibleMoves.size() != 0) {


			randomChosenNumber = random.nextInt(possibleMoves.size());

			tempMove = chooseTheMove(possibleMoves.get(randomChosenNumber));

			possibleMoves.remove(randomChosenNumber);
		}


		// exit if there is no move to be made
		if (tempMove == null) {
			return null;
		}

		Moves.getInstance().getKing().setPosition(position);
		newMove.append(tempMove);

		charArray[oldPosition.y * 9 + oldPosition.x + 1] = '*';

		if (XBoard.getInstance().isPlayingWhite()) {
			charArray[position.y * 9 + position.x + 1] = 'k';
		} else {
			charArray[position.y * 9 + position.x + 1] = 'K';
		}

		chessBoard = new String("");
		for (char i : charArray) {
			chessBoard += i;
		}

		Moves.getInstance().setChessBoard(chessBoard);

		move = newMove.toString();

		return move;
	}

	private String chooseTheMove(Integer moveCode) {
		switch (moveCode) {
			case 0:
				return this.moveFirstDiagonalForward();

			case 1:
				return this.moveFirstDiagonalBackwards();

			case 2:
				return this.moveSecondDiagonalForward();

			case 3:
				return this.moveSecondDiagonalBackwards();

			case 4 :
				return this.moveForward();

			case 5 :
				return this.moveLeft();

			case 6 :
				return this.moveRight();

			case 7 :
				return this.moveBack();

		}
		return null;
	}

	@Override
	public Pair getPosition() {
		return position;
	}

	@Override
	public void setPosition(Pair position) {
		this.position = position;
	}
}
