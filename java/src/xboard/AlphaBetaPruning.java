package xboard;

import common.PairMoveAndState;
import common.ScoresForPieces;
import common.ScoresForPiecesBlack;
import common.ScoresForPiecesWhite;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class AlphaBetaPruning {

	int maxDepth = 4;
	boolean playsWhite;

	public String chooseTheBestMove() {
		Random random = new Random();
		String move = null;
		playsWhite = XBoard.getInstance().isPlayingWhite();

		PairMoveAndState returnedFromMiniMax;

		XBoard.getInstance().setPlayingWhite(!XBoard.getInstance().isPlayingWhite());

		returnedFromMiniMax = minimax(null, 0, true, -100000, 100000);
		move = returnedFromMiniMax.move;
		Moves.getInstance().setChessBoard(returnedFromMiniMax.chessboard);
		System.out.println(Moves.getInstance().getChessBoard());

		XBoard.getInstance().setPlayingWhite(!XBoard.getInstance().isPlayingWhite());
		return move;
	}

	private PairMoveAndState minimax(String move, int depth, boolean isMaximizingPlayer, int alpha, int beta) {
		PairMoveAndState value;
		String previousRes;

		if (depth == maxDepth) {
			return new PairMoveAndState(Moves.getInstance().getChessBoard(), move,
					evaluateScore(Moves.getInstance().getChessBoard()));
		}

		if (isMaximizingPlayer) {
			XBoard.getInstance().setPlayingWhite(!XBoard.getInstance().isPlayingWhite());
			Moves.getInstance().setMadeMoves(new HashSet<>());

			PairMoveAndState bestVal = new PairMoveAndState(null, null, -100000);

			String oldChessboard = Moves.getInstance().getChessBoard();
			String res = Moves.getInstance().makeAMove();

			while (res != null) {

				previousRes = res;

				Set<String> copyMadeMoves = Moves.getInstance().getMadeMoves();

				value = minimax(res, depth + 1, false, alpha, beta);
				value.move = res;
				value.chessboard = Moves.getInstance().getChessBoard();
				System.out.println(value.score);

				Moves.getInstance().setMadeMoves(copyMadeMoves);

				Moves.getInstance().getMadeMoves().add(Moves.getInstance().getChessBoard());

				bestVal = PairMoveAndState.max(bestVal, value);

				Moves.getInstance().setChessBoard(oldChessboard);
				Moves.getInstance().resetAMadeMove(res);

				alpha = Integer.max(alpha, bestVal.score);

				if (beta <= alpha) {
//					System.out.println("BREAK THE WHILE");
					break;
				}

				res = Moves.getInstance().makeAMove();

				if (previousRes.equals(res)) {
					Moves.getInstance().setChessBoard(oldChessboard);
					Moves.getInstance().resetAMadeMove(res);
					break;
				}
			}
			XBoard.getInstance().setPlayingWhite(!XBoard.getInstance().isPlayingWhite());
//			System.out.println(bestVal.score);
//			bestVal.score -= evaluateScore(bestVal.chessboard);
			return bestVal;
		} else {
			XBoard.getInstance().setPlayingWhite(!XBoard.getInstance().isPlayingWhite());
			Moves.getInstance().setMadeMoves(new HashSet<>());

			PairMoveAndState bestVal = new PairMoveAndState(null, null, 100000);

			String oldChessboard = Moves.getInstance().getChessBoard();
			String res = Moves.getInstance().makeAMove();

			while (res != null) {

				previousRes = res;

				Set<String> copyMadeMoves = Moves.getInstance().getMadeMoves();

				value = minimax(res, depth + 1, true, alpha, beta);
				value.move = res;
				value.chessboard = Moves.getInstance().getChessBoard();

				Moves.getInstance().setMadeMoves(copyMadeMoves);

				Moves.getInstance().getMadeMoves().add(Moves.getInstance().getChessBoard());

				bestVal = PairMoveAndState.min(bestVal, value);

				Moves.getInstance().setChessBoard(oldChessboard);
				Moves.getInstance().resetAMadeMove(res);

				beta = Integer.min(beta, bestVal.score);

				if (beta <= alpha) {
//					System.out.println("BREAK THE WHILE");
					break;
				}

				res = Moves.getInstance().makeAMove();

				if (previousRes.equals(res)) {
					Moves.getInstance().setChessBoard(oldChessboard);
					Moves.getInstance().resetAMadeMove(res);
					break;
				}
			}
			XBoard.getInstance().setPlayingWhite(!XBoard.getInstance().isPlayingWhite());

//			bestVal.score += evaluateScore(bestVal.chessboard);
			return bestVal;
		}
	}

	private ArrayList<PairMoveAndState> generateAllPossibleMoves() {
		ArrayList<PairMoveAndState> states = new ArrayList<>();

		Moves.getInstance().setMadeMoves(new HashSet<>());

		String oldChessboard = Moves.getInstance().getChessBoard();
		String res = Moves.getInstance().makeAMove();

		while (res != null) {
			Moves.getInstance().getMadeMoves().add(Moves.getInstance().getChessBoard());
			states.add(new PairMoveAndState(Moves.getInstance().getChessBoard(), res, evaluateScore(Moves.getInstance().getChessBoard())));
			Moves.getInstance().setChessBoard(oldChessboard);
			Moves.getInstance().resetAMadeMove(res);

			res = Moves.getInstance().makeAMove();
		}

		return states;
	}

	private int evaluateScore(String chessBoard) {
		int score = 0;
		ScoresForPieces scoresForPieces;

		if (playsWhite) {
			scoresForPieces = new ScoresForPiecesWhite();

			boolean prev = XBoard.getInstance().isPlayingWhite();

			XBoard.getInstance().setPlayingWhite(true);
			if (Moves.getInstance().getKing().inCheck(Moves.getInstance().getChessBoard())) {
				score -= 5000;
			}

			XBoard.getInstance().setPlayingWhite(false);
			if (Moves.getInstance().getKing().inCheck(Moves.getInstance().getChessBoard())) {
				score += 5000;
			}
			XBoard.getInstance().setPlayingWhite(prev);

			for (int i = 0; i < chessBoard.length(); i++) {
				int x = i % 9 - 1;
				int y = i / 9;

				if (chessBoard.charAt(i) == 'p') {
					score += scoresForPieces.getPawn()[y][x];
					score += 100;
				} else if (chessBoard.charAt(i) == 'r') {
					score += scoresForPieces.getRook()[y][x];
					score += 500;
				} else if (chessBoard.charAt(i) == 'n') {
					score += scoresForPieces.getKnight()[y][x];
					score += 320;
				} else if (chessBoard.charAt(i) == 'b') {
					score += scoresForPieces.getBishop()[y][x];
					score += 330;
				} else if (chessBoard.charAt(i) == 'q') {
					score += scoresForPieces.getQueen()[y][x];
					score += 900;
				} else if (chessBoard.charAt(i) == 'k') {
					score += scoresForPieces.getKing()[y][x];
					score += 1000;
				}
				else if (chessBoard.charAt(i) == 'P') {
					score -= 100;
				} else if (chessBoard.charAt(i) == 'R') {
					score -= 500;
				} else if (chessBoard.charAt(i) == 'N') {
					score -= 320;
				} else if (chessBoard.charAt(i) == 'B') {
					score -= 330;
				} else if (chessBoard.charAt(i) == 'Q') {
					score -= 900;
				}
			}
		} else {
			scoresForPieces = new ScoresForPiecesBlack();


			boolean prev = XBoard.getInstance().isPlayingWhite();

			XBoard.getInstance().setPlayingWhite(false);
			if (Moves.getInstance().getKing().inCheck(Moves.getInstance().getChessBoard())) {
				score -= 1000;
			}

			XBoard.getInstance().setPlayingWhite(true);
			if (Moves.getInstance().getKing().inCheck(Moves.getInstance().getChessBoard())) {
				score += 1000;
			}
			XBoard.getInstance().setPlayingWhite(prev);

			for (int i = 0; i < chessBoard.length(); i++) {
				int x = i % 9 - 1;
				int y = i / 9;

				if (chessBoard.charAt(i) == 'P') {
					score += scoresForPieces.getPawn()[y][x];
					score += 100;
				} else if (chessBoard.charAt(i) == 'R') {
					score += scoresForPieces.getRook()[y][x];
					score += 500;
				} else if (chessBoard.charAt(i) == 'N') {
					score += scoresForPieces.getKnight()[y][x];
					score += 320;
				} else if (chessBoard.charAt(i) == 'B') {
					score += scoresForPieces.getBishop()[y][x];
					score += 330;
				} else if (chessBoard.charAt(i) == 'Q') {
					score += scoresForPieces.getQueen()[y][x];
					score += 900;
				} else if (chessBoard.charAt(i) == 'K') {
					score += scoresForPieces.getKing()[y][x];
					score += 1000;
				}
				else if (chessBoard.charAt(i) == 'p') {
					score -= 100;
				} else if (chessBoard.charAt(i) == 'r') {
					score -= 500;
				} else if (chessBoard.charAt(i) == 'n') {
					score -= 320;
				} else if (chessBoard.charAt(i) == 'b') {
					score -= 330;
				} else if (chessBoard.charAt(i) == 'q') {
					score -= 900;
				}
			}
		}


		return score;
	}
}
