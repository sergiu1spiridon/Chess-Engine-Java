package interfaces;

// Andrei
public interface BishopInterface extends Piece{
    // methods characteristic to a bishop

    // it goes towards the enemy
    public String moveFirstDiagonalForward();

    // moves away from enemy
    public String moveFirstDiagonalBackwards();

    public String moveSecondDiagonalForward();

    public String moveSecondDiagonalBackwards();
}
