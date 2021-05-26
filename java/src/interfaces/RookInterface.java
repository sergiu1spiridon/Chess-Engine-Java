package interfaces;

// Sergiu
public interface RookInterface extends Piece{
    // methods characteristic to a rook piece

    // towards the enemy
    public String moveForward();

    public String moveLeft();

    public String moveRight();

    // away from the enemy
    public String moveBack();
}
