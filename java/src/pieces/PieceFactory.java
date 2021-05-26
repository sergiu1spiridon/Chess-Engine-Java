package pieces;

import common.Pair;
import interfaces.Piece;
import xboard.Moves;

public class PieceFactory {
    public static Piece getPiece(char c, int x, int y) {
        switch (c) {
            case 'P' :
                return new Pawn(new Pair(x, y));

            case 'R' :
                return new Rook(new Pair(x, y));

            case 'B' :
                return new Bishop(new Pair(x, y));

            case 'N':
                return new Knight(new Pair(x, y));

            case 'K':
                return new King(new Pair(x, y));

            case 'Q':
                return new Queen(new Pair(x, y));

            default :
                return null;
        }
    }

    public static Piece getWhitePieces(char c, int x, int y) {
        switch (c) {
            case 'p' :
                return new Pawn(new Pair(x, y));

            case 'r' :
                return new Rook(new Pair(x, y));

            case 'b' :
                return new Bishop(new Pair(x, y));

            case 'n':
                return new Knight(new Pair(x, y));

            case 'k':
                return new King(new Pair(x, y));

            case 'q':
                return new Queen(new Pair(x, y));

            default :
                return null;
        }
    }
}
