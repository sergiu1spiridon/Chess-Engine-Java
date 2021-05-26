package interfaces;

public interface KingInterface extends QueenInterface{
    public Boolean inCheck(String chessboard);
    public Boolean inCheckMate(String chessboard) ;
    public String getOutOfCheck();
}
