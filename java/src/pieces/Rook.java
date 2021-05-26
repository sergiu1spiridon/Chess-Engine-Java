package pieces;

import common.Pair;
import interfaces.RookInterface;
import xboard.Moves;
import xboard.XBoard;

import java.util.ArrayList;
import java.util.Random;

public class Rook implements RookInterface {
	private Pair position;
	private Random random = new Random();

	public Rook(Pair position) {
		this.position = position;
	}

	private String updateString(int newX, int newY) {
		String prevChessBoard = Moves.getInstance().getChessBoard();
		char[] charArray = prevChessBoard.toCharArray();

		charArray[position.y * 9 + position.x + 1] = '*';
		if (XBoard.getInstance().isPlayingWhite()) {
			charArray[newY * 9 + newX + 1] = 'r';
		} else {
			charArray[newY * 9 + newX + 1] = 'R';
		}

		String chessBoard = "";
		for (char i : charArray) {
			chessBoard += i;
		}
		return chessBoard;
	}

	// move code = 0

	/**
	 * changes the position of the piece
	 *
	 * @return String that represents a move "e2e4"
	 */
	@Override
	public String moveForward() {

		ArrayList<Integer> possibleNumberOfRowsToMove = new ArrayList<>();
		Integer direction = Moves.getInstance().getDirectionController();
		int i = 1;
		String chessBoard = Moves.getInstance().getChessBoard();
		String enemyArray = Moves.getInstance().getEnemyArray();

		while ((((position.y - i * direction) * 9 + position.x + 1) >= 0) &&
				(((position.y - i * direction) * 9 + position.x + 1) < 73)) {
			// if we find a blank square we could go further
			// if we find a enemy piece we can take it and stop there
			// if we find one of our pieces we stop before it
			if ("*".indexOf(chessBoard.charAt((position.y - i * direction) * 9 + position.x + 1)) >= 0) {
				possibleNumberOfRowsToMove.add(i);
				i++;
			} else if (enemyArray.indexOf(chessBoard.charAt((position.y - i * direction) * 9 + position.x + 1)) >= 0) {
				possibleNumberOfRowsToMove.add(i);
				break;
			} else {
				break;
			}
		}
		Integer chosenNumber = random.nextInt(possibleNumberOfRowsToMove.size());
		Integer numberOfRows = possibleNumberOfRowsToMove.get(chosenNumber);
		String newChessBoard = updateString(position.x,
				position.y - numberOfRows * direction);
		Boolean check = Moves.getInstance().getKing().inCheck(newChessBoard);

		String move;
		if (check) {
			// can return null here, if it can't move
			move = null;
			possibleNumberOfRowsToMove.remove(numberOfRows);
			while (possibleNumberOfRowsToMove.size() != 0) {
				chosenNumber = random.nextInt(possibleNumberOfRowsToMove.size());
				numberOfRows = possibleNumberOfRowsToMove.get(chosenNumber);
				newChessBoard = updateString(position.x,
						position.y - numberOfRows * direction);
				check = Moves.getInstance().getKing().inCheck(newChessBoard);
				if (!check) {
					move = (char) (position.x + 'a') +
							String.valueOf((position.y + 1 - numberOfRows * direction));

					position.y -= numberOfRows * direction;
					position.x += 0;
					break;
				}
				possibleNumberOfRowsToMove.remove(numberOfRows);
			}
		} else {
			move = (char) (position.x + 'a') +
					String.valueOf((position.y + 1 - numberOfRows * direction));
			position.y -= numberOfRows * direction;
			position.x += 0;

		}
		return move;
	}

	// move code = 1

	/**
	 * changes the position of the piece
	 *
	 * @return String that represents a move "e2e4"
	 */
	@Override
	public String moveLeft() {

		ArrayList<Integer> possibleNumberOfRowsToMove = new ArrayList<>();
		Integer direction = Moves.getInstance().getDirectionController();
		int i = 1;
		String chessBoard = Moves.getInstance().getChessBoard();
		String enemyArray = Moves.getInstance().getEnemyArray();

		while (((position.y * 9 + (position.x - i * direction) + 1) >= 0) &&
				((position.y * 9 + (position.x - i * direction) + 1) < 73)) {
			// if we find a blank square we could go further
			// if we find a enemy piece we can take it and stop there
			// if we find one of our pieces we stop before it
			if ("*".indexOf(chessBoard.charAt(position.y * 9 + (position.x - i * direction) + 1)) >= 0) {
				possibleNumberOfRowsToMove.add(i);
				i++;
			} else if (enemyArray.indexOf(chessBoard.charAt(position.y * 9 + (position.x - i * direction) + 1)) >= 0) {
				possibleNumberOfRowsToMove.add(i);
				break;
			} else {
				break;
			}
		}
		Integer chosenNumber = random.nextInt(possibleNumberOfRowsToMove.size());
		Integer numberOfRows = possibleNumberOfRowsToMove.get(chosenNumber);
		String newChessBoard = updateString(position.x - numberOfRows * direction,
				position.y);
		Boolean check = Moves.getInstance().getKing().inCheck(newChessBoard);

		String move;
		if (check) {
			// can return null here, if it can't move
			move = null;
			possibleNumberOfRowsToMove.remove(numberOfRows);
			while (possibleNumberOfRowsToMove.size() != 0) {
				chosenNumber = random.nextInt(possibleNumberOfRowsToMove.size());
				numberOfRows = possibleNumberOfRowsToMove.get(chosenNumber);
				newChessBoard = updateString(position.x - numberOfRows * direction,
						position.y);
				check = Moves.getInstance().getKing().inCheck(newChessBoard);
				if (!check) {
					move = (char) (position.x - numberOfRows * direction + 'a') +
							String.valueOf((position.y + 1));

					position.y += 0;
					position.x -= numberOfRows * direction;
					break;
				}
				possibleNumberOfRowsToMove.remove(numberOfRows);
			}
		} else {
			move = (char) (position.x - numberOfRows * direction + 'a') +
					String.valueOf((position.y + 1));
			position.y += 0;
			position.x -= numberOfRows * direction;
		}
		return move;
	}

	// move code = 2

	/**
	 * changes the position of the piece
	 *
	 * @return String that represents a move "e2e4"
	 */
	@Override
	public String moveRight() {

		ArrayList<Integer> possibleNumberOfRowsToMove = new ArrayList<>();
		Integer direction = Moves.getInstance().getDirectionController();
		int i = 1;
		String chessBoard = Moves.getInstance().getChessBoard();
		String enemyArray = Moves.getInstance().getEnemyArray();

		while (((position.y * 9 + (position.x + i * direction) + 1) >= 0) &&
				((position.y * 9 + (position.x + i * direction) + 1) < 73)) {
			// if we find a blank square we could go further
			// if we find a enemy piece we can take it and stop there
			// if we find one of our pieces we stop before it
			if ("*".indexOf(chessBoard.charAt(position.y * 9 + (position.x + i * direction) + 1)) >= 0) {
				possibleNumberOfRowsToMove.add(i);
				i++;
			} else if (enemyArray.indexOf(chessBoard.charAt(position.y * 9 + (position.x + i * direction) + 1)) >= 0) {
				possibleNumberOfRowsToMove.add(i);
				break;
			} else {
				break;
			}
		}
		Integer chosenNumber = random.nextInt(possibleNumberOfRowsToMove.size());
		Integer numberOfRows = possibleNumberOfRowsToMove.get(chosenNumber);
		String newChessBoard = updateString(position.x + numberOfRows * direction,
				position.y);
		Boolean check = Moves.getInstance().getKing().inCheck(newChessBoard);

		String move;
		if (check) {
			// can return null here, if it can't move
			move = null;
			possibleNumberOfRowsToMove.remove(numberOfRows);
			while (possibleNumberOfRowsToMove.size() != 0) {
				chosenNumber = random.nextInt(possibleNumberOfRowsToMove.size());
				numberOfRows = possibleNumberOfRowsToMove.get(chosenNumber);
				newChessBoard = updateString(position.x + numberOfRows * direction,
						position.y);
				check = Moves.getInstance().getKing().inCheck(newChessBoard);
				if (!check) {
					move = (char) (position.x + numberOfRows * direction + 'a') +
							String.valueOf((position.y + 1));
					position.y += 0;
					position.x += numberOfRows * direction;
					break;
				}
				possibleNumberOfRowsToMove.remove(numberOfRows);
			}
		} else {
			move = (char) (position.x + numberOfRows * direction + 'a') +
					String.valueOf((position.y + 1));
			position.y += 0;
			position.x += numberOfRows * direction;
		}
		return move;
	}

	// move code = 3

	/**
	 * changes the position of the piece
	 *
	 * @return String that represents a move "e2e4"
	 */
	@Override
	public String moveBack() {

		ArrayList<Integer> possibleNumberOfRowsToMove = new ArrayList<>();
		Integer direction = Moves.getInstance().getDirectionController();
		int i = 1;
		String chessBoard = Moves.getInstance().getChessBoard();
		String enemyArray = Moves.getInstance().getEnemyArray();

		while ((((position.y + i * direction) * 9 + position.x + 1) >= 0) &&
				(((position.y + i * direction) * 9 + position.x + 1) < 73)) {
			// if we find a blank square we could go further
			// if we find a enemy piece we can take it and stop there
			// if we find one of our pieces we stop before it
			if ("*".indexOf(chessBoard.charAt((position.y + i * direction) * 9 + position.x + 1)) >= 0) {
				possibleNumberOfRowsToMove.add(i);
				i++;
			} else if (enemyArray.indexOf(chessBoard.charAt((position.y + i * direction) * 9 + position.x + 1)) >= 0) {
				possibleNumberOfRowsToMove.add(i);
				break;
			} else {
				break;
			}
		}
		Integer chosenNumber = random.nextInt(possibleNumberOfRowsToMove.size());
		Integer numberOfRows = possibleNumberOfRowsToMove.get(chosenNumber);
		String newChessBoard = updateString(position.x,
				position.y + numberOfRows * direction);
		Boolean check = Moves.getInstance().getKing().inCheck(newChessBoard);

		String move;
		if (check) {
			// can return null here, if it can't move
			move = null;
			possibleNumberOfRowsToMove.remove(numberOfRows);
			while (possibleNumberOfRowsToMove.size() != 0) {
				chosenNumber = random.nextInt(possibleNumberOfRowsToMove.size());
				numberOfRows = possibleNumberOfRowsToMove.get(chosenNumber);
				newChessBoard = updateString(position.x,
						position.y + numberOfRows * direction);
				check = Moves.getInstance().getKing().inCheck(newChessBoard);
				if (!check) {
					move = (char) (position.x + 'a') +
							String.valueOf((position.y + numberOfRows * direction + 1));
					position.y += numberOfRows * direction;
					position.x += 0;
					break;
				}
				possibleNumberOfRowsToMove.remove(numberOfRows);
			}
		} else {
			move = (char) (position.x + 'a') +
					String.valueOf((position.y + numberOfRows * direction + 1));
			position.y += numberOfRows * direction;
			position.x += 0;
		}
		return move;
	}

	/**
	 * @param chessBoard the current state of the board
	 * @return String that represents a valid move
	 * returns null if no moves can be made by this piece
	 */
	@Override
	public String makeValidMove(String chessBoard) {
		String move = null;
		ArrayList<Integer> possibleMoves = new ArrayList<>();


		// change depending on with what pieces the AI plays
		Integer direction = Moves.getInstance().getDirectionController();
		String enemyArray = Moves.getInstance().getEnemyArray() + "*";

		// check validity for the forward move
		// code 0
		if ((((position.y - (1 * direction)) * 9 + (position.x) + 1) >= 0) &&
				(((position.y - (1 * direction)) * 9 + (position.x) + 1) < 73) &&
				(enemyArray.indexOf(chessBoard.charAt((
						position.y - (1 * direction)) * 9 + (position.x) + 1)) >= 0)) {
			possibleMoves.add(0);
		}
		// check validity for the move left
		// code 1
		if ((((position.y) * 9 + (position.x - (1 * direction) + 1)) >= 0) &&
				(((position.y) * 9 + (position.x - (1 * direction) + 1)) < 73) &&
				(enemyArray.indexOf(chessBoard.charAt((
						position.y) * 9 + (position.x - (1 * direction) + 1))) >= 0)) {
			possibleMoves.add(1);
		}
		// check validity for the move right
		// code 2
		if ((((position.y) * 9 + (position.x + (1 * direction) + 1)) >= 0) &&
				(((position.y) * 9 + (position.x + (1 * direction) + 1)) < 73) &&
				(enemyArray.indexOf(chessBoard.charAt((
						position.y) * 9 + (position.x + (1 * direction) + 1))) >= 0)) {
			possibleMoves.add(2);
		}
		// check validity for the backwards move
		// code 3
		if ((((position.y + (1 * direction)) * 9 + (position.x) + 1) >= 0) &&
				(((position.y + (1 * direction)) * 9 + (position.x) + 1) < 73) &&
				(enemyArray.indexOf(chessBoard.charAt((
						position.y + (1 * direction)) * 9 + (position.x) + 1)) >= 0)) {
			possibleMoves.add(3);
		}

		// there can not be moves made with this piece
		if (possibleMoves.size() == 0) {
			return null;
		}


//		System.out.println("la tu");

		char[] charArray = chessBoard.toCharArray();
		int oldPos = position.y * 9 + position.x + 1;
//		charArray[position.y * 9 + position.x + 1] = '*';

		// Choose at random a move to make on this piece
		// The moves have different weights
		StringBuilder newMove = new StringBuilder();

		newMove.append((char) ('a' + position.x));
		newMove.append((position.y + 1));
		Integer getAMove = possibleMoves.get(random.nextInt(possibleMoves.size()));
		String theMove = chooseTheMove(getAMove);
		int ok = 0;
		if (theMove != null) {
			ok = 1;
			charArray[oldPos] = '*';
			newMove.append(theMove);
		} else {
			possibleMoves.remove(getAMove);
			while(possibleMoves.size() != 0) {
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
				charArray[position.y * 9 + position.x + 1] = 'r';
			} else {
				charArray[position.y * 9 + position.x + 1] = 'R';
			}
		}

		chessBoard = "";
		for (char i : charArray) {
			chessBoard += i;
		}

//		System.out.println(chessBoard);

		Moves.getInstance().setChessBoard(chessBoard);

		move = newMove.toString();
		if (ok == 1) {
			return move;
		} else {
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
				return this.moveLeft();

			case 2:
				return this.moveRight();

			case 3:
				return this.moveBack();

		}
		return null;
	}

	@Override
	public Pair getPosition() {
		return this.position;
	}

	@Override
	public void setPosition(Pair position) {
		this.position = position;
	}
}
