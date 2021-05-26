package pieces;

import common.Pair;
import interfaces.PawnInteface;
import xboard.Moves;
import xboard.XBoard;

import java.util.ArrayList;
import java.util.Random;

public class Pawn implements PawnInteface {

	private Pair position;
	private boolean firstMove;

	public Pawn(Pair position) {
		this.position = position;
		this.firstMove = true;
		this.setFirstMove();
	}

	private String updateString(int newX, int newY) {
		String prevChessBoard = Moves.getInstance().getChessBoard();
		char[] charArray = prevChessBoard.toCharArray();

		charArray[position.y * 9 + position.x + 1] = '*';
		if (XBoard.getInstance().isPlayingWhite()) {
			charArray[newY * 9 + newX + 1] = 'p';
		} else {
			charArray[newY * 9 + newX + 1] = 'P';
		}

		if (XBoard.getInstance().isPlayingWhite()) {
			if (position.y == 7) {
				Moves.getInstance().removePiece(position);
				charArray[newY * 9 + newX + 1] = 'q';
			}
		} else {
			if (position.y == 0) {
				Moves.getInstance().removePiece(position);
				charArray[newY * 9 + newX + 1] = 'Q';
			}
		}

		String chessBoard = "";
		for (char i : charArray) {
			chessBoard += i;
		}
		return chessBoard;
	}

	@Override
	/*
	 * code 0
	 * Changes the position of the piece
	 */
	public String moveForward() {
		Random random = new Random();
		int numberOfRows = 1;
		// random pt pondere mai mare 80 % sanse sa mearga 2 casute
		if (firstMove && random.nextInt(10) < 8) {
			if ((position.y - (2 * Moves.getInstance().getDirectionController())) * 9 + position.x + 1 > 0) {
				if (Moves.getInstance().getChessBoard().charAt((position.y -
						(2 * Moves.getInstance().getDirectionController())) * 9 + position.x + 1) == '*') {
					numberOfRows = 2;
				}
			}
		}

		// the first move of the pawn has been made
		firstMove = false;

		// check daca se afla in sah dupa mutare
		String newChessboard = updateString(position.x,
				position.y - numberOfRows * Moves.getInstance().getDirectionController());
		Boolean check = Moves.getInstance().getKing().inCheck(newChessboard);

		if (!check) {
//			System.out.println("in sah pion fata");
			String move = (char) (position.x + 'a') +
					String.valueOf((position.y + 1 - numberOfRows * Moves.getInstance().getDirectionController()));
			position.y -= numberOfRows * Moves.getInstance().getDirectionController();
			position.x += 0;

			return move;
		} else {
			return null;
		}
	}

	@Override
	/*
	 * code 1
	 * Changes the position of the piece as well
	 */
	public String movePrincipalDiagonal() {
		firstMove = false;

		String newChessBoard = updateString(position.x + 1 * Moves.getInstance().getDirectionController(),
				position.y - 1 * Moves.getInstance().getDirectionController());
		Boolean check = Moves.getInstance().getKing().inCheck(newChessBoard);


		if (!check) {
			String move = (char) (position.x + 'a' + 1 * Moves.getInstance().getDirectionController()) +
					String.valueOf((position.y + 1) - 1 * Moves.getInstance().getDirectionController());
			position.y -= 1 * Moves.getInstance().getDirectionController();
			position.x += 1 * Moves.getInstance().getDirectionController();
			return move;
		} else {
			return null;
		}
	}

	@Override
	/*
	 * code 2
	 * Changes the position of the piece
	 */
	public String moveSecondaryDiagonal() {
		firstMove = false;

		String newChessBoard = updateString(position.x - 1 * Moves.getInstance().getDirectionController(),
				position.y - 1 * Moves.getInstance().getDirectionController());
		Boolean check = Moves.getInstance().getKing().inCheck(newChessBoard);

		if (!check) {
			String move = (char) (position.x + 'a' - 1 * Moves.getInstance().getDirectionController()) +
					String.valueOf((position.y + 1) - 1 * Moves.getInstance().getDirectionController());
			position.y -= 1 * Moves.getInstance().getDirectionController();
			position.x -= 1 * Moves.getInstance().getDirectionController();
			return move;
		} else {
			return null;
		}

	}

	@Override
	/*
	 * Should receive the current state of the board
	 * returns a move like "x1x2"
	 *
	 * Check if the return is null
	 * if it's null then this piece can make no moves
	 *
	 * Send this outcome to the XBoard because the piece position has
	 * changed
	 */
	public String makeValidMove(String chessBoard) {
		String move = null;
		ArrayList<Integer> possibleMoves = new ArrayList<>();
		Random random = new Random();
		Moves moves = Moves.getInstance();

		// check validity of second diagonal move
		if (((position.y - (1 * moves.getDirectionController())) * 9 + position.x -
				(1 * moves.getDirectionController()) + 1) >= 0
				&& (Moves.getInstance().getEnemyArray().indexOf(chessBoard.charAt((
				position.y - (1 * moves.getDirectionController())) * 9
				+ position.x - (1 * moves.getDirectionController()) + 1)) >= 0)) {

			possibleMoves.add(2);
			possibleMoves.add(2);
			possibleMoves.add(2);
		}
		// check validity of first diagonal move
		if (((position.y - (1 * moves.getDirectionController())) * 9 + position.x +
				(1 * moves.getDirectionController()) + 1) >= 0
				&& (Moves.getInstance().getEnemyArray().indexOf(chessBoard.charAt((position.y -
				(1 * moves.getDirectionController())) * 9 + position.x +
				(1 * moves.getDirectionController()) + 1)) >= 0)) {

			possibleMoves.add(1);
			possibleMoves.add(1);
			possibleMoves.add(1);
		}
		// check validity of forward move
		if (((position.y - (1 * moves.getDirectionController())) * 9 + position.x + 1) >= 0
				&& "*".indexOf(chessBoard.charAt((position.y -
				(1 * moves.getDirectionController())) * 9 + position.x + 1)) >= 0) {
			possibleMoves.add(0);
		}

		if (possibleMoves.size() == 0) {
			return null;
		}

		char[] charArray = chessBoard.toCharArray();
		int oldPos = position.y * 9 + position.x + 1;


		// Choose at random a move to make on this piece
		// The moves have different weights
		StringBuilder newMove = new StringBuilder();

		newMove.append((char) ('a' + position.x));
		newMove.append((position.y + 1));
		Integer getAMove = possibleMoves.get(random.nextInt(possibleMoves.size()));
		String theMove = chooseTheMove(getAMove);
//		String theMove = chooseTheMove(possibleMoves.get(random.nextInt(possibleMoves.size())));

//		System.out.println("misca pionul");

		int ok = 0;
		if (theMove != null) {
			ok = 1;
			charArray[oldPos] = '*';
			newMove.append(theMove);
		} else {
			possibleMoves.remove(getAMove);
			while (possibleMoves.size() != 0) {
				Integer newMoveArray = possibleMoves.get(random.nextInt(possibleMoves.size()));
				theMove = chooseTheMove(newMoveArray);

				if (theMove != null) {
					ok = 1;
					charArray[oldPos] = '*';
					newMove.append(theMove);
					break;
				}
				possibleMoves.remove(newMoveArray);
			}
		}


		if (ok == 1) {
			if (XBoard.getInstance().isPlayingWhite()) {
				charArray[position.y * 9 + position.x + 1] = 'p';
			} else {
				charArray[position.y * 9 + position.x + 1] = 'P';
			}

			if (XBoard.getInstance().isPlayingWhite()) {
				char[] chooseSwitch = {'r', 'b', 'q', 'n'};

				if (position.y == 7) {
					Moves.getInstance().removePiece(position);
					char chosen = chooseSwitch[random.nextInt(4)];
					Moves.getInstance().getAIPieces().add(PieceFactory.getWhitePieces(
							chosen, position.x, position.y));
					charArray[position.y * 9 + position.x + 1] = chosen;
					newMove.append(chosen);
				}
			} else {
				char[] chooseSwitch = {'R', 'B', 'Q', 'N'};
				if (position.y == 0) {
					Moves.getInstance().removePiece(position);
					char chosen = chooseSwitch[random.nextInt(4)];
					Moves.getInstance().getAIPieces().add(PieceFactory.getPiece(
							chosen, position.x, position.y));
					charArray[position.y * 9 + position.x + 1] = chosen;
					newMove.append(chosen);
				}
			}
		}

		chessBoard = "";
		for (char i : charArray) {
			chessBoard += i;
		}

		Moves.getInstance().setChessBoard(chessBoard);

		move = newMove.toString();
		if (ok == 1) {
//			System.out.println("da");
			return move;
		} else {
			// can't move the pawn, so return null
			return null;
		}
	}

	/**
	 * @param moveCode This is the code of one of the methods for movement
	 * @return
	 */
	private String chooseTheMove(Integer moveCode) {
		switch (moveCode) {
			case 0:
				return this.moveForward();

			case 1:
				return this.movePrincipalDiagonal();

			case 2:
				return this.moveSecondaryDiagonal();

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

	public void setFirstMove() {
		if (position.y != 1) {
			firstMove = false;
		}
	}
}
