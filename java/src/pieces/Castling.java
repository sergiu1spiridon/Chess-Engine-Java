package pieces;

import common.Pair;
import interfaces.CastlingInterface;
import interfaces.Piece;
import xboard.Moves;
import xboard.XBoard;

import java.util.ArrayList;
import java.util.Optional;


public class Castling implements CastlingInterface {
	private static Castling instance;
	private boolean canMakeRocadaMareWhite;
	private boolean canMakeRocadaMicaWhite;
	private boolean canMakeRocadaMareBlack;
	private boolean canMakeRocadaMicaBlack;

	private Castling() {
		this.canMakeRocadaMareBlack = true;
		this.canMakeRocadaMareWhite = true;
		this.canMakeRocadaMicaBlack = true;
		this.canMakeRocadaMicaWhite = true;
	}

	public static Castling getInstance() {
		if (instance == null) {
			instance = new Castling();
		}

		return instance;
	}

	public static Castling reset() {
		instance = new Castling();
		return instance;
	}

	@Override
	public String makeValidMove(String chessBoard) {
//		System.out.println(canMakeRocadaMareBlack + " " + canMakeRocadaMicaWhite + " "
//				+ canMakeRocadaMareWhite + " " + canMakeRocadaMareBlack);
		String move = null;
		if (canMakeRocadaMicaWhite || canMakeRocadaMicaBlack) {
			move = this.rocadaMica(chessBoard);
		}
		if (move == null) {
			if (canMakeRocadaMareWhite || canMakeRocadaMareBlack) {
				move = this.rocadaMare(chessBoard);
			}
		}
		return move;
	}

//     012345678                                                       987654321
//    "/rnbqkbnr/pppppppp/********/********/********/********/PPPPPPPP/RNBQKBNR/";

	@Override
	public String rocadaMare(String chessBoard) {
		// verific campurile individual si dupa simulez pozitia kingului pana la destinatie sa verific daca e in sah
		StringBuilder chessBoardSimulator = new StringBuilder(chessBoard);
		if (XBoard.getInstance().isPlayingWhite()) {
			if (chessBoard.charAt(2) == '*' && chessBoard.charAt(3) == '*' && chessBoard.charAt(4) == '*') {
				System.out.println("primul if");
				Pair oldPos = Moves.getInstance().getKing().getPosition();
				for (int i = 5; i >= 3; i--) {
					System.out.println("pas la for");
					Moves.getInstance().getKing().setPosition(new Pair(i % 9 - 1, 0));
					chessBoardSimulator.setCharAt(i, 'k');
					if (Moves.getInstance().getKing().inCheck(chessBoardSimulator.toString())) {
						System.out.println("la return de null");
						Moves.getInstance().getKing().setPosition(oldPos);
						return null;
					}
					chessBoardSimulator.setCharAt(i, '*');
				}
				// chessboard after moving
				chessBoardSimulator.setCharAt(1, '*');
				chessBoardSimulator.setCharAt(2, '*');
				chessBoardSimulator.setCharAt(3, 'k');
				chessBoardSimulator.setCharAt(4, 'r');

				Moves.getInstance().getKing().setPosition(new Pair(2, 0));
				Moves.getInstance().setChessBoard(chessBoardSimulator.toString());
				ArrayList<Piece> pieces = Moves.getInstance().getAIPieces();

				// rege schimbat in AIPieces
				Piece changedPiece;
				Optional<Piece> changedPiece2 = pieces.stream().filter(piece ->
						piece.getPosition().equals(oldPos)).findAny();
				if (changedPiece2.isPresent()) {
					changedPiece = changedPiece2.get();
					changedPiece.setPosition(new Pair(2, 0));
				}

				// tura schimbata in AIPieces
				changedPiece2 = pieces.stream().filter(piece ->
						piece.getPosition().equals(new Pair(0, 0))).findAny();
				if (changedPiece2.isPresent()) {
					changedPiece = changedPiece2.get();
					changedPiece.setPosition(new Pair(3, 0));
				}
//				changedPiece = pieces.stream().filter(piece ->
//						piece.getPosition().equals(new Pair(0, 0))).findAny().get();
//				changedPiece.setPosition(new Pair(3, 0));

				return "e1c1";
			}
		} else {
			if (chessBoard.charAt(chessBoard.length() - 6) == '*' && chessBoard.charAt(chessBoard.length() - 7) == '*' && chessBoard.charAt(chessBoard.length() - 8) == '*') {
				Pair oldPos = Moves.getInstance().getKing().getPosition();
				for (int i = chessBoard.length() - 5; i >= chessBoard.length() - 7; i--) {
					Moves.getInstance().getKing().setPosition(new Pair(i % 9 - 1, 7));
					chessBoardSimulator.setCharAt(i, 'K');
					if (Moves.getInstance().getKing().inCheck(chessBoardSimulator.toString())) {
						Moves.getInstance().getKing().setPosition(oldPos);
						return null;
					}
					chessBoardSimulator.setCharAt(i, '*');
				}
				// chessboard after moving
				chessBoardSimulator.setCharAt(7 * 9 + 1, '*');
				chessBoardSimulator.setCharAt(7 * 9 + 2, '*');
				chessBoardSimulator.setCharAt(7 * 9 + 3, 'K');
				chessBoardSimulator.setCharAt(7 * 9 + 4, 'R');

				Moves.getInstance().getKing().setPosition(new Pair(2, 7));
				Moves.getInstance().setChessBoard(chessBoardSimulator.toString());
				ArrayList<Piece> pieces = Moves.getInstance().getAIPieces();

				// rege schimbat in AIPieces
				Piece changedPiece;
				Optional<Piece> changedPiece2 = pieces.stream().filter(piece ->
						piece.getPosition().equals(oldPos)).findAny();
				if (changedPiece2.isPresent()) {
					changedPiece = changedPiece2.get();
					changedPiece.setPosition(new Pair(2, 7));
				}

				// tura schimbata in AIPieces

				changedPiece2 = pieces.stream().filter(piece ->
						piece.getPosition().equals(new Pair(0, 7))).findAny();
				if (changedPiece2.isPresent()) {
					changedPiece = changedPiece2.get();
					changedPiece.setPosition(new Pair(3, 7));
				}
//				changedPiece = pieces.stream().filter(piece ->
//						piece.getPosition().equals(new Pair(0, 7))).findAny().get();
//				changedPiece.setPosition(new Pair(3, 7));

				return "e8c8";
			}
		}

		return null;
	}

//     012345678                                                       987654321
//    "/rnbqkbnr/pppppppp/********/********/********/********/PPPPPPPP/RNBQKBNR/";

	public boolean isCanMakeRocadaMareWhite() {
		return canMakeRocadaMareWhite;
	}

	public void setCanMakeRocadaMareWhite(boolean canMakeRocadaMareWhite) {
		this.canMakeRocadaMareWhite = canMakeRocadaMareWhite;
	}

	public boolean isCanMakeRocadaMicaWhite() {
		return canMakeRocadaMicaWhite;
	}

	public void setCanMakeRocadaMicaWhite(boolean canMakeRocadaMicaWhite) {
		this.canMakeRocadaMicaWhite = canMakeRocadaMicaWhite;
	}

	public boolean isCanMakeRocadaMareBlack() {
		return canMakeRocadaMareBlack;
	}

	public void setCanMakeRocadaMareBlack(boolean canMakeRocadaMareBlack) {
		this.canMakeRocadaMareBlack = canMakeRocadaMareBlack;
	}

	public boolean isCanMakeRocadaMicaBlack() {
		return canMakeRocadaMicaBlack;
	}

	public void setCanMakeRocadaMicaBlack(boolean canMakeRocadaMicaBlack) {
		this.canMakeRocadaMicaBlack = canMakeRocadaMicaBlack;
	}

	@Override
	public String rocadaMica(String chessBoard) {
		StringBuilder chessBoardSimulator = new StringBuilder(chessBoard);
		if (XBoard.getInstance().isPlayingWhite()) {
			if (chessBoard.charAt(6) == '*' && chessBoard.charAt(7) == '*') {
                Pair oldPos = Moves.getInstance().getKing().getPosition();
				for (int i = 5; i <= 7; i++) {
					Moves.getInstance().getKing().setPosition(new Pair(i % 9 - 1, 0));
					chessBoardSimulator.setCharAt(i, 'k');
					if (Moves.getInstance().getKing().inCheck(chessBoardSimulator.toString())) {
                        Moves.getInstance().getKing().setPosition(oldPos);
						return null;
					}
					chessBoardSimulator.setCharAt(i, '*');
				}
                // chessboard after moving
                chessBoardSimulator.setCharAt(5, '*');
                chessBoardSimulator.setCharAt(6, 'r');
                chessBoardSimulator.setCharAt(7, 'k');
                chessBoardSimulator.setCharAt(8, '*');

				Moves.getInstance().getKing().setPosition(new Pair(6, 0));
				Moves.getInstance().setChessBoard(chessBoardSimulator.toString());
				ArrayList<Piece> pieces = Moves.getInstance().getAIPieces();

				// rege schimbat in AIPieces
				Piece changedPiece;
				Optional<Piece> changedPiece2 = pieces.stream().filter(piece ->
						piece.getPosition().equals(oldPos)).findAny();
				if (changedPiece2.isPresent()) {
					changedPiece = changedPiece2.get();
					changedPiece.setPosition(new Pair(6, 0));
				}

				// tura schimbata in AIPieces
				changedPiece2 = pieces.stream().filter(piece ->
						piece.getPosition().equals(new Pair(7, 0))).findAny();
				if (changedPiece2.isPresent()) {
					changedPiece = changedPiece2.get();
					changedPiece.setPosition(new Pair(5, 0));
				}
//				changedPiece = pieces.stream().filter(piece ->
//						piece.getPosition().equals(new Pair(7, 0))).findAny().get();
//				changedPiece.setPosition(new Pair(5, 0));

                return "e1g1";
			}
		} else {
			if (chessBoard.charAt(chessBoard.length() - 4) == '*' && chessBoard.charAt(chessBoard.length() - 3) == '*') {
				Pair oldPos = Moves.getInstance().getKing().getPosition();
				for (int i = chessBoard.length() - 5; i <= chessBoard.length() - 3; i++) {
					Moves.getInstance().getKing().setPosition(new Pair(i % 9 - 1, 7));
					chessBoardSimulator.setCharAt(i, 'K');
					if (Moves.getInstance().getKing().inCheck(chessBoardSimulator.toString())) {
						Moves.getInstance().getKing().setPosition(oldPos);
						return null;
					}
					chessBoardSimulator.setCharAt(i, '*');
				}
				// chessboard after moving
				chessBoardSimulator.setCharAt(7 * 9 + 5, '*');
				chessBoardSimulator.setCharAt(7 * 9 + 6, 'R');
				chessBoardSimulator.setCharAt(7 * 9 + 7, 'K');
				chessBoardSimulator.setCharAt(7 * 9 + 8, '*');

				Moves.getInstance().getKing().setPosition(new Pair(6, 7));
				Moves.getInstance().setChessBoard(chessBoardSimulator.toString());
				ArrayList<Piece> pieces = Moves.getInstance().getAIPieces();
				Piece changedPiece;
				// rege schimbat in AIPieces
				Optional<Piece> changedPiece2 = pieces.stream().filter(piece ->
						piece.getPosition().equals(oldPos)).findAny();
				if (changedPiece2.isPresent()) {
					changedPiece = changedPiece2.get();
					changedPiece.setPosition(new Pair(6, 7));
				}

				// tura schimbata in AIPieces
				changedPiece2 = pieces.stream().filter(piece ->
						piece.getPosition().equals(new Pair(7, 7))).findAny();
				if (changedPiece2.isPresent()) {
					changedPiece = changedPiece2.get();
					changedPiece.setPosition(new Pair(5, 7));
				}
//				changedPiece = pieces.stream().filter(piece ->
//						piece.getPosition().equals(new Pair(7, 7))).findAny().get();
//				changedPiece.setPosition(new Pair(5, 7));
				return "e8g8";
			}
		}



		return null;
	}
}
