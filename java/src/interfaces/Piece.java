package interfaces;

import common.Pair;

public interface Piece {
    // this method should be called to get a move

    /**
     *
     * @param chessBoard the current state of the board
     * @return String that holds a valid move
     * returns null if the piece can't make a valid move
     */
    public String makeValidMove(String chessBoard);

    public Pair getPosition();
    public void setPosition(Pair position);
}
