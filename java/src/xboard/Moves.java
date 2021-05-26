package xboard;

import common.Pair;
import interfaces.Piece;
import pieces.Castling;
import pieces.King;
import pieces.Pawn;
import pieces.PieceFactory;

import java.util.*;

public class Moves {

    private King king;
    private String chessBoard;
    private static Moves instance = null;
    private String lastMove = null;

    // this 3 change depending on the color we play
    private ArrayList<Piece> AIPieces;
    private String enemyArray;
    private int directionController;
    private Set<String> madeMoves;

    private Moves() {
        // initialize the pieces the AI has
        this.AIPieces = new ArrayList<>();
        Castling.reset();

        this.enemyArray = "rnbqkbnrp";
        this.directionController = 1;


        // FEN notation for the state of the board
        this.chessBoard = "/rnbqkbnr/pppppppp/********/********/********/********/PPPPPPPP/RNBQKBNR/";

        char[] charArray = this.chessBoard.toCharArray();

//        System.out.println(chessBoard);

        for (int i = 0; i < 73; i++) {
            Piece newPiece = PieceFactory.getPiece(charArray[i], (i % 9 - 1), i / 9);
            if (newPiece != null) {
                if (newPiece instanceof King) {
                    this.king = (King)newPiece;
                }
                this.AIPieces.add(newPiece);
            }
        }
    }

    /**
     * Made singleton so we have the same board around the whole project
     * @return an instance of Moves
     */
    public static Moves getInstance() {
        if (instance == null) {
            instance = new Moves();
        }

        return instance;
    }

    /**
     * Call this method in order to clear the current instance
     * called for new Game command
     * you should call getInstance after
     */
    public static void makeNewBoard() {
        instance = new Moves();
    }

    /**
     * Checks if there was moved a piece that is used in Castling
     *
     * sets Castling.canMakeRocada accordingly
     * @param move
     */
    private void seeValidityCastling(String move) {

//        System.out.println(move);
//        System.out.println(Integer.parseInt(String.valueOf(move.charAt(1))));

        if (chessBoard.charAt(0 * 9 + 1) != 'r') {
            Castling.getInstance().setCanMakeRocadaMareWhite(false);
        }

        if (chessBoard.charAt(0 * 9 + 8) != 'r') {
            Castling.getInstance().setCanMakeRocadaMicaWhite(false);
        }

        if (chessBoard.charAt(7 * 9 + 1) != 'R') {
            Castling.getInstance().setCanMakeRocadaMareBlack(false);
        }

        if (chessBoard.charAt(7 * 9 + 8) != 'R') {
            Castling.getInstance().setCanMakeRocadaMicaBlack(false);
        }

        if (Integer.parseInt(String.valueOf(move.charAt(1))) == 1 && (move.charAt(0) - 'a') == 4) {
            Castling.getInstance().setCanMakeRocadaMareWhite(false);
            Castling.getInstance().setCanMakeRocadaMicaWhite(false);
//            System.out.println("rocada white false");
        }

        if (Integer.parseInt(String.valueOf(move.charAt(1))) == 1 && (move.charAt(0) - 'a') == 7) {
            Castling.getInstance().setCanMakeRocadaMicaWhite(false);
//            System.out.println("rocada white false2");
        }

        if (Integer.parseInt(String.valueOf(move.charAt(1))) == 1 && (move.charAt(0) - 'a') == 0) {
            Castling.getInstance().setCanMakeRocadaMareWhite(false);
//            System.out.println("rocada white false3");
        }

        if (Integer.parseInt(String.valueOf(move.charAt(1))) == 8 && (move.charAt(0) - 'a') == 4) {
            Castling.getInstance().setCanMakeRocadaMareBlack(false);
            Castling.getInstance().setCanMakeRocadaMicaBlack(false);
//            System.out.println("rocada black false");
        }

        if (Integer.parseInt(String.valueOf(move.charAt(1))) == 8 && (move.charAt(0) - 'a') == 7) {
            Castling.getInstance().setCanMakeRocadaMicaBlack(false);
//            System.out.println("rocada black false1");
        }

        if (Integer.parseInt(String.valueOf(move.charAt(1))) == 8 && (move.charAt(0) - 'a') == 0) {
            Castling.getInstance().setCanMakeRocadaMareBlack(false);
//            System.out.println("rocada black false2");
        }
    }

    /**
     * Tries to make EnPassant
     * @return
     */
    private String tryEnPassant() {
        String move = null;

        if (XBoard.getInstance().isPlayingWhite()) {
            if (Integer.parseInt(String.valueOf(lastMove.charAt(1))) == 7) {
                if (Integer.parseInt(String.valueOf(lastMove.charAt(3))) == 5) {
                    int y = Integer.parseInt(String.valueOf(lastMove.charAt(3))) - 1;
                    int x = Integer.parseInt(String.valueOf(lastMove.charAt(2) - 'a'));
                    if (chessBoard.charAt(y * 9 + x + 1) == 'P') {

                        Optional<Piece> myPiece2 = AIPieces.stream().filter(
                                p -> p.getPosition().equals(new Pair(x + 1, y))).findAny();
                        Piece myPiece;


                        if (myPiece2.isPresent()) {
                            myPiece = myPiece2.get();
                            myPiece.setPosition(new Pair(x, y + 1));
                            StringBuilder chessBoardCopy = new StringBuilder(chessBoard);
                            chessBoardCopy.setCharAt((y + 1) * 9 + x + 1, 'p');
                            chessBoardCopy.setCharAt((y) * 9 + x + 2, '*');
                            chessBoardCopy.setCharAt((y) * 9 + x + 1, '*');
                            chessBoard = chessBoardCopy.toString();
                            move = (char)(x + 1 + 'a') + String.valueOf(y + 1) + (char)(x + 'a') + String.valueOf(y + 2);
                        } else {
                            myPiece2 = AIPieces.stream().filter(
                                    p -> p.getPosition().equals(new Pair(x - 1, y))).findAny();

                            if (myPiece2.isPresent()) {
                                myPiece = myPiece2.get();
                                myPiece.setPosition(new Pair(x, y + 1));
                                StringBuilder chessBoardCopy = new StringBuilder(chessBoard);
                                chessBoardCopy.setCharAt((y + 1) * 9 + x + 1, 'p');
                                chessBoardCopy.setCharAt((y) * 9 + x, '*');
                                chessBoardCopy.setCharAt((y) * 9 + x + 1, '*');
                                chessBoard = chessBoardCopy.toString();
                                move = (char)(x - 1 + 'a') + String.valueOf(y + 1) + (char)(x + 'a') + String.valueOf(y + 2);
                            }
                        }
                    }
                }
            }
        } else {
            if (Integer.parseInt(String.valueOf(lastMove.charAt(1))) == 2) {
                if (Integer.parseInt(String.valueOf(lastMove.charAt(3))) == 4) {
                    int y = Integer.parseInt(String.valueOf(lastMove.charAt(3))) - 1;
                    int x = Integer.parseInt(String.valueOf(lastMove.charAt(2) - 'a'));
                    if (chessBoard.charAt(y * 9 + x + 1) == 'p') {

                        Optional<Piece> myPiece2 = AIPieces.stream().filter(
                                p -> p.getPosition().equals(new Pair(x + 1, y))).findAny();
                        Piece myPiece;

                        if (myPiece2.isPresent()) {
                            myPiece = myPiece2.get();
                            myPiece.setPosition(new Pair(x, y - 1));
                            StringBuilder chessBoardCopy = new StringBuilder(chessBoard);
                            chessBoardCopy.setCharAt((y - 1) * 9 + x + 1, 'P');
                            chessBoardCopy.setCharAt((y) * 9 + x + 2, '*');
                            chessBoardCopy.setCharAt((y) * 9 + x + 1, '*');
                            chessBoard = chessBoardCopy.toString();
                            move = (char)(x + 1 + 'a') + String.valueOf(y + 1) + (char)(x + 'a') + String.valueOf(y);
                        } else {
                            myPiece2 = AIPieces.stream().filter(
                                    p -> p.getPosition().equals(new Pair(x - 1, y))).findAny();

                            if (myPiece2.isPresent()) {
                                myPiece = myPiece2.get();
                                myPiece.setPosition(new Pair(x, y - 1));
                                StringBuilder chessBoardCopy = new StringBuilder(chessBoard);
                                chessBoardCopy.setCharAt((y - 1) * 9 + x + 1, 'P');
                                chessBoardCopy.setCharAt((y) * 9 + x, '*');
                                chessBoardCopy.setCharAt((y) * 9 + x + 1, '*');
                                chessBoard = chessBoardCopy.toString();
                                move = (char)(x - 1 + 'a') + String.valueOf(y + 1) + (char)(x + 'a') + String.valueOf(y);
                            }
                        }
                    }
                }
            }
        }



        return move;
    }

    /**
     * See if the move the player did is enPassant
     * @param move
     * @return
     */
    private Boolean seeIfEnPassantWasTried(String move) {

        // pair cu pozitia initiala
        Pair initialPos = new Pair(move.charAt(0) - 'a', Integer.parseInt(String.valueOf(move.charAt(1))) - 1);
        // pair cu pozitia finala
        Pair finalPos = new Pair(move.charAt(2) - 'a', Integer.parseInt(String.valueOf(move.charAt(3))) - 1);

        // pair cu pozitia initiala
        Pair initialPosLastMove = new Pair(lastMove.charAt(0) - 'a', Integer.parseInt(String.valueOf(lastMove.charAt(1))) - 1);
        // pair cu pozitia finala
        Pair finalPosLastMove = new Pair(lastMove.charAt(2) - 'a', Integer.parseInt(String.valueOf(lastMove.charAt(3))) - 1);

        if (initialPos.y == 3 && finalPos.y == 2) {
            if (Math.abs(initialPos.x - finalPos.x) == 1) {
                if (initialPosLastMove.y == 1 && finalPosLastMove.y == 3) {
                    if (initialPosLastMove.x == finalPos.x) {
                        if (chessBoard.charAt(finalPosLastMove.y * 9 + finalPosLastMove.x + 1) == 'p') {
                            StringBuilder chessboardCopy = new StringBuilder(chessBoard);

                            chessboardCopy.setCharAt(finalPosLastMove.y * 9 + finalPosLastMove.x + 1, '*');
                            removePiece(new Pair(finalPosLastMove.y, finalPosLastMove.x));
                            return true;
                        }
                    }
                }
            }
        }

        if (initialPos.y == 4 && finalPos.y == 5) {
            if (Math.abs(initialPos.x - finalPos.x) == 1) {
                if (initialPosLastMove.y == 6 && finalPosLastMove.y == 4) {
                    if (initialPosLastMove.x == finalPos.x) {
                        if (chessBoard.charAt(finalPosLastMove.y * 9 + finalPosLastMove.x + 1) == 'P') {
                            StringBuilder chessboardCopy = new StringBuilder(chessBoard);

                            chessboardCopy.setCharAt(finalPosLastMove.y * 9 + finalPosLastMove.x + 1, '*');
                            removePiece(new Pair(finalPosLastMove.y, finalPosLastMove.x));
                            return true;
                        }
                    }
                }
            }
        }


        return false;
    }

    /**
     * miscarea de player
     * @param move should look like "e4e6"
     *             e4 - initial position
     *             e6 - future position
     */
    public void getPlayerMove(String move) {
        // aici ar veni codul pt cand poate sa ne ia el piesa cu en passant
        // sa updatez tabla de sah practic si sa scot din AI pieces

        seeValidityCastling(move);

        // pair cu pozitia initiala
        Pair initialPos = new Pair(move.charAt(0) - 'a', Integer.parseInt(String.valueOf(move.charAt(1))) - 1);
        // pair cu pozitia finala
        Pair finalPos = new Pair(move.charAt(2) - 'a', Integer.parseInt(String.valueOf(move.charAt(3))) - 1);


        // chessboard salvat in array
        char[] charArray = chessBoard.toCharArray();

            if (lastMove != null) {
                Boolean enPassantWasTried = seeIfEnPassantWasTried(move);
            }

            seeIfAIPieceMoved(initialPos, finalPos);


        // modifica tabla de sah -> pune piesa pe noua pozitie
        charArray[(finalPos.y * 8) + finalPos.y + finalPos.x + 1] =
                    charArray[(initialPos.y * 8) + initialPos.y + initialPos.x + 1];

        if (charArray[(initialPos.y * 9) + initialPos.x + 1] == 'P' && finalPos.y == 0) {
//            char[] chooseSwitch = {'R', 'B', 'Q', 'N'};
            charArray[(finalPos.y * 8) + finalPos.y + finalPos.x + 1] = 'Q';
        }

        if (charArray[(initialPos.y * 9) + initialPos.x + 1] == 'p' && finalPos.y == 7) {
//            char[] chooseSwitch = {'r', 'b', 'q', 'n'};
            charArray[(finalPos.y * 8) + finalPos.y + finalPos.x + 1] = 'q';
        }


        // sterge piesa abia luata din AI pieces
        removePiece(finalPos);

        // reseteaza pozitia intiala la *
        charArray[(initialPos.y * 8) + initialPos.y + initialPos.x + 1] = '*';

        if (initialPos.x == 4 && initialPos.y == 0) {
            if (finalPos.y == 0 && finalPos.x == 6) {
                if (charArray[finalPos.y * 9 + finalPos.x + 1] == 'k') {
                    charArray[finalPos.y * 9 + finalPos.x] = 'r';
                    charArray[finalPos.y * 9 + finalPos.x + 2] = '*';
                    Optional<Piece> myPiece2 = AIPieces.stream().filter(
                            p -> p.getPosition().equals(new Pair(finalPos.x + 1, finalPos.y))).findAny();

                    if (myPiece2.isPresent()) {
                        myPiece2.get().setPosition(new Pair(finalPos.x - 1, finalPos.y));
                    }

                    Castling.getInstance().setCanMakeRocadaMicaWhite(false);
                    Castling.getInstance().setCanMakeRocadaMareWhite(false);

                }
            }

            if (finalPos.y == 0 && finalPos.x == 1) {
                if (charArray[finalPos.y * 9 + finalPos.x + 1] == 'k') {
                    charArray[finalPos.y * 9 + finalPos.x + 2] = 'r';
                    charArray[finalPos.y * 9 + finalPos.x] = '*';
                    Optional<Piece> myPiece2 = AIPieces.stream().filter(
                            p -> p.getPosition().equals(new Pair(finalPos.x - 1, finalPos.y))).findAny();

                    if (myPiece2.isPresent()) {
                        myPiece2.get().setPosition(new Pair(finalPos.x + 1, finalPos.y));
                    }

                    Castling.getInstance().setCanMakeRocadaMareWhite(false);
                    Castling.getInstance().setCanMakeRocadaMicaWhite(false);

                }
            }
        }

        if (initialPos.x == 4 && initialPos.y == 7) {
            if (finalPos.y == 7 && finalPos.x == 6) {
                if (charArray[finalPos.y * 9 + finalPos.x + 1] == 'K') {
                    charArray[finalPos.y * 9 + finalPos.x] = 'R';
                    charArray[finalPos.y * 9 + finalPos.x + 2] = '*';
                    Optional<Piece> myPiece2 = AIPieces.stream().filter(
                            p -> p.getPosition().equals(new Pair(finalPos.x + 1, finalPos.y))).findAny();

                    if (myPiece2.isPresent()) {
                        myPiece2.get().setPosition(new Pair(finalPos.x - 1, finalPos.y));
                    }

                    Castling.getInstance().setCanMakeRocadaMicaBlack(false);
                    Castling.getInstance().setCanMakeRocadaMareBlack(false);

                }
            }

            if (finalPos.y == 7 && finalPos.x == 1) {
                if (charArray[finalPos.y * 9 + finalPos.x + 1] == 'K') {
                    charArray[finalPos.y * 9 + finalPos.x + 2] = 'R';
                    charArray[finalPos.y * 9 + finalPos.x] = '*';
                    Optional<Piece> myPiece2 = AIPieces.stream().filter(
                            p -> p.getPosition().equals(new Pair(finalPos.x - 1, finalPos.y))).findAny();

                    if (myPiece2.isPresent()) {
                        myPiece2.get().setPosition(new Pair(finalPos.x + 1, finalPos.y));
                    }

                    Castling.getInstance().setCanMakeRocadaMareBlack(false);
                    Castling.getInstance().setCanMakeRocadaMicaBlack(false);

                }
            }
        }

        // reface chessboardul cu noile pozitii
        chessBoard = "";
        for(char i:charArray) {
            chessBoard += i;
        }

        this.lastMove = move;
    }

    /**
     * This function is used to change the position of a piece in AIPieces
     *
     * Used only on force
     *
     * @param initialPair the position of the peice before being moved
     * @param finalPair position where it's supposed to move
     */
    private void seeIfAIPieceMoved(Pair initialPair, Pair finalPair) {

        Piece changedPiece = null;

        try {
            changedPiece = AIPieces.stream().filter(piece ->
                    piece.getPosition().equals(initialPair)).findAny().get();

        } catch (java.util.NoSuchElementException e) {
            return;
        }

        changedPiece.setPosition(finalPair);
        if (changedPiece instanceof Pawn) {
            ((Pawn) changedPiece).setFirstMove();
        }
    }

    /**
     * Call this to get a move from the AI
     * ex: sout(makeAMove)
     * @return String in format "e4e6"
     * write this on stdout
     */
    public String makeAMove() {
        StringBuilder move = new StringBuilder("move ");

//        System.out.println(king.getPosition().x + " " + king.getPosition().y);

        if (AIPieces.size() == 0) {
            return "resign";
        }

        Random random = new Random();
        int randomOutput = random.nextInt(AIPieces.size());

        if(random.nextInt(100) > 100){
            String tmp = Castling.getInstance().makeValidMove(chessBoard);
//            System.out.println("la rocada");
            if(tmp != null){
                move.append(tmp);
                return move.toString();
            }
        }

        if (lastMove != null) {
            if (random.nextInt(100) <= 100) {
                String tmp = null;

//                System.out.println(lastMove + " la inceput");
                tmp = tryEnPassant();

                if (tmp != null) {
                    lastMove = tmp;
//                    System.out.println(lastMove + " in If");
                    move.append(tmp);
                    return move.toString();
                }
            }
        }

        String moveGivenByRandom = AIPieces.get(randomOutput).makeValidMove(chessBoard);


        if (moveGivenByRandom == null) {

            int i = randomOutput + 1;
            while (i != randomOutput) {
                if (i == AIPieces.size()) {
                    i = 0;
                    if (i == randomOutput) {
                        break;
                    }
                }

                moveGivenByRandom = AIPieces.get(i).makeValidMove(chessBoard);

                if (moveGivenByRandom != null) {
                    break;
                }
                i++;
            }
        }

//        System.out.println("MOVE: " + moveGivenByRandom);
        if (moveGivenByRandom == null) {
//            return "resign";
			return null;
        }

//        System.out.println(chessBoard);

        // add move to the string "move" to make it ready for return
        move.append(moveGivenByRandom);

//        System.out.println(moveGivenByRandom.toString());
        seeValidityCastling(moveGivenByRandom.toString());

        this.lastMove = moveGivenByRandom.toString();
        return move.toString();
    }

    /**
     * This is called when player makes a move in order to remove the
     * piece that is taken(here we see if there is a piece to be taken too)
     *
     * @param pair this is the position of the piece that is taken
     */
    public void removePiece(Pair pair) {
        AIPieces.removeIf(piece -> {
            return piece.getPosition().equals(pair);
        });
    }

    /**
     *
     * function that should change the needed things for
     * when the color of the pieces the AI plays with changes
     *
     * @param isWhite boolean value is true if the AI plays white
     */
    public void setAIPiecesForColorChange(boolean isWhite) {

        char[] charArray = this.chessBoard.toCharArray();

        this.AIPieces = new ArrayList<>(0);

        if (isWhite) {

            this.enemyArray = "RNBQKBNRP";
            this.directionController = -1;

            for (int i = 0; i < 73; i++) {
                Piece newPiece = PieceFactory.getWhitePieces(charArray[i], i % 9 - 1, i / 9);
                if (newPiece != null) {
                    this.AIPieces.add(newPiece);

                    if (newPiece instanceof King) {
                        this.king = (King) newPiece;
                    }
                }
            }
        } else {

            this.enemyArray = "rnbqkbnrp";
            this.directionController = 1;

            for (int i = 0; i < 73; i++) {
                Piece newPiece = PieceFactory.getPiece(charArray[i], i % 9 - 1, i / 9);
                if (newPiece != null) {
                    this.AIPieces.add(newPiece);


                    if (newPiece instanceof King) {
                        this.king = (King) newPiece;
                    }
                }
            }
        }
    }

    public void resetAMadeMove(String move) {
    	Pair initialPosition = new Pair(move.charAt(5) - 'a', Integer.parseInt(String.valueOf(move.charAt(6))) - 1);
    	Pair finalPosition = new Pair(move.charAt(7) - 'a', Integer.parseInt(String.valueOf(move.charAt(8))) - 1);

    	Piece changedPiece;
		try {
			changedPiece = AIPieces.stream().filter(piece ->
					piece.getPosition().equals(finalPosition)).findAny().get();

			if (changedPiece instanceof King) {
			    Moves.getInstance().getKing().setPosition(initialPosition);
            }

		} catch (java.util.NoSuchElementException e) {
			return;
		}

		changedPiece.setPosition(initialPosition);

	}

    public King getKing() {
        return king;
    }

    public void setKing(King king) {
        this.king = king;
    }

    public String getChessBoard() {
        return chessBoard;
    }

    public void setChessBoard(String chessBoard) {
        this.chessBoard = chessBoard;
    }

    public ArrayList<Piece> getAIPieces() {
        return AIPieces;
    }

    public void setAIPieces(ArrayList<Piece> AIPieces) {
        this.AIPieces = AIPieces;
    }

    public String getEnemyArray() {
        return enemyArray;
    }

    public void setEnemyArray(String enemyArray) {
        this.enemyArray = enemyArray;
    }

    public int getDirectionController() {
        return directionController;
    }

    public void setDirectionController(int directionController) {
        this.directionController = directionController;
    }

    public boolean moveIsNotTriedYet(String move) {
        return !madeMoves.contains(move);
    }

    public Set<String> getMadeMoves() {
        return madeMoves;
    }

    public void setMadeMoves(Set<String> madeMoves) {
        this.madeMoves = madeMoves;
    }
}