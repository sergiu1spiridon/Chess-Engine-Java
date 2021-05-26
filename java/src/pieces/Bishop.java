package pieces;

import common.Pair;
import interfaces.BishopInterface;
import xboard.Moves;
import xboard.XBoard;

import java.util.ArrayList;
import java.util.Random;

public class Bishop implements BishopInterface {
    private Pair position;
    private Random random = new Random();

    public Bishop(Pair position) {
        this.position = position;
    }

    private String updateString(int newX, int newY) {
        String prevChessBoard = Moves.getInstance().getChessBoard();
        char[] charArray = prevChessBoard.toCharArray();

        charArray[position.y * 9 + position.x + 1] = '*';
        if (XBoard.getInstance().isPlayingWhite()) {
            charArray[newY * 9 + newX + 1] = 'b';
        } else {
            charArray[newY * 9 + newX + 1] = 'B';
        }

        String chessBoard = "";
        for (char i : charArray) {
            chessBoard += i;
        }
        return chessBoard;
    }
    @Override
    public String moveFirstDiagonalForward() {

        ArrayList<Integer> possibleNumberOfRowsToMove = new ArrayList<>();
        Integer direction = Moves.getInstance().getDirectionController();
        int i = 1;
        String chessBoard = Moves.getInstance().getChessBoard();
        String enemyArray = Moves.getInstance().getEnemyArray();

        while ((((position.y - i * direction) * 9 + (position.x + i * direction) + 1) >= 0) &&
                (((position.y - i * direction) * 9 + (position.x + i * direction) + 1) < 73)) {
            // if we find a blank square we could go further
            // if we find an enemy piece we can take it and stop there
            // if we find one of our pieces we stop before it
            if ("*".indexOf(chessBoard.charAt((position.y - i * direction) * 9 + (position.x + i * direction) + 1)) >= 0) {
                possibleNumberOfRowsToMove.add(i);
                i++;
            } else if (enemyArray.indexOf(chessBoard.charAt((position.y - i * direction) * 9 + (position.x + i * direction) + 1)) >= 0) {
                possibleNumberOfRowsToMove.add(i);
                break;
            } else {
                break;
            }
        }

        // update state of board and check if it's in check now
//        System.out.println("SUNT LA PRIMA 1");
        Integer chosenNumber = random.nextInt(possibleNumberOfRowsToMove.size());
        Integer numberOfRows = possibleNumberOfRowsToMove.get(chosenNumber);
        String newChessBoard = updateString(position.x + numberOfRows * direction,
                position.y - numberOfRows * direction);
        Boolean check = Moves.getInstance().getKing().inCheck(newChessBoard);

        String move;
        if (check) {
            // can return null here, if it can't move
//            System.out.println("print n1");
            move = null;
            possibleNumberOfRowsToMove.remove(numberOfRows);
            while (possibleNumberOfRowsToMove.size() != 0) {
                chosenNumber = random.nextInt(possibleNumberOfRowsToMove.size());
                numberOfRows = possibleNumberOfRowsToMove.get(chosenNumber);
                newChessBoard = updateString(position.x + numberOfRows * direction,
                        position.y - numberOfRows * direction);
                check = Moves.getInstance().getKing().inCheck(newChessBoard);
                if (!check) {
                    move = (char) (position.x + numberOfRows * direction + 'a') +
                            String.valueOf((position.y + 1 - numberOfRows * direction));

                    position.y -= numberOfRows * direction;
                    position.x += numberOfRows * direction;
                    break;
                }
                possibleNumberOfRowsToMove.remove(numberOfRows);
            }
        } else {
            move = (char) (position.x + numberOfRows * direction + 'a') +
                    String.valueOf((position.y + 1 - numberOfRows * direction));
            position.y -= numberOfRows * direction;
            position.x += numberOfRows * direction;

        }
        return move;
    }

    @Override
    public String moveFirstDiagonalBackwards() {

        ArrayList<Integer> possibleNumberOfRowsToMove = new ArrayList<>();
        Integer direction = Moves.getInstance().getDirectionController();
        int i = 1;
        String chessBoard = Moves.getInstance().getChessBoard();
        String enemyArray = Moves.getInstance().getEnemyArray();

        while ((((position.y + i * direction) * 9 + (position.x - i * direction) + 1) >= 0) &&
                (((position.y + i * direction) * 9 + (position.x - i * direction) + 1) < 73)) {
            // if we find a blank square we could go further
            // if we find an enemy piece we can take it and stop there
            // if we find one of our pieces we stop before it
            if ("*".indexOf(chessBoard.charAt((position.y + i * direction) * 9 + (position.x - i * direction) + 1)) >= 0) {
                possibleNumberOfRowsToMove.add(i);
                i++;
            } else if (enemyArray.indexOf(chessBoard.charAt((position.y + i * direction) * 9 + (position.x - i * direction) + 1)) >= 0) {
                possibleNumberOfRowsToMove.add(i);
                break;
            } else {
                break;
            }
        }

//        System.out.println("SUNT LA PRIMA 2");

        // update state of board and check if it's in check now
        Integer chosenNumber = random.nextInt(possibleNumberOfRowsToMove.size());
        Integer numberOfRows = possibleNumberOfRowsToMove.get(chosenNumber);
        String newChessBoard = updateString(position.x - numberOfRows * direction,
                position.y + numberOfRows * direction);
        Boolean check = Moves.getInstance().getKing().inCheck(newChessBoard);

        String move;
        if (check) {
//            System.out.println("print n2");
            // can return null here, if it can't move
            move = null;
            possibleNumberOfRowsToMove.remove(numberOfRows);
            while (possibleNumberOfRowsToMove.size() != 0) {
                chosenNumber = random.nextInt(possibleNumberOfRowsToMove.size());
                numberOfRows = possibleNumberOfRowsToMove.get(chosenNumber);
                newChessBoard = updateString(position.x - numberOfRows * direction,
                        position.y + numberOfRows * direction);
                check = Moves.getInstance().getKing().inCheck(newChessBoard);
                if (!check) {
                    move = (char) (position.x - numberOfRows * direction + 'a') +
                            String.valueOf((position.y + 1 + numberOfRows * direction));

                    position.y += numberOfRows * direction;
                    position.x -= numberOfRows * direction;
                    break;
                }
                possibleNumberOfRowsToMove.remove(numberOfRows);
            }
        } else {
            move = (char) (position.x - numberOfRows * direction + 'a') +
                    String.valueOf((position.y + 1 + numberOfRows * direction));
            position.y += numberOfRows * direction;
            position.x -= numberOfRows * direction;

        }
        return move;
    }

    @Override
    public String moveSecondDiagonalForward() {

//        System.out.println("SUNT LA PRIMA 3");
        ArrayList<Integer> possibleNumberOfRowsToMove = new ArrayList<>();
        Integer direction = Moves.getInstance().getDirectionController();
        int i = 1;
        String chessBoard = Moves.getInstance().getChessBoard();
        String enemyArray = Moves.getInstance().getEnemyArray();

        while ((((position.y - i * direction) * 9 + (position.x - i * direction) + 1) >= 0) &&
                (((position.y - i * direction) * 9 + (position.x - i * direction) + 1) < 73)) {
            // if we find a blank square we could go further
            // if we find an enemy piece we can take it and stop there
            // if we find one of our pieces we stop before it
            if ("*".indexOf(chessBoard.charAt((position.y - i * direction) * 9 + (position.x - i * direction) + 1)) >= 0) {
                possibleNumberOfRowsToMove.add(i);
                i++;
            } else if (enemyArray.indexOf(chessBoard.charAt((position.y - i * direction) * 9 + (position.x - i * direction) + 1)) >= 0) {
                possibleNumberOfRowsToMove.add(i);
                break;
            } else {
                break;
            }
        }
        // update state of board and check if it's in check now
        Integer chosenNumber = random.nextInt(possibleNumberOfRowsToMove.size());
        Integer numberOfRows = possibleNumberOfRowsToMove.get(chosenNumber);
        String newChessBoard = updateString(position.x - numberOfRows * direction,
                position.y - numberOfRows * direction);
        Boolean check = Moves.getInstance().getKing().inCheck(newChessBoard);

        String move;
        if (check) {
            // can return null here, if it can't move
//            System.out.println("print n3");
            move = null;
            Boolean ok = possibleNumberOfRowsToMove.remove(numberOfRows);
//            possibleNumberOfRowsToMove.remove(numberOfRows);
            while (possibleNumberOfRowsToMove.size() != 0) {
//                System.out.println("print n3 in while");
                chosenNumber = random.nextInt(possibleNumberOfRowsToMove.size());
                numberOfRows = possibleNumberOfRowsToMove.get(chosenNumber);
                newChessBoard = updateString(position.x - numberOfRows * direction,
                        position.y - numberOfRows * direction);
                check = Moves.getInstance().getKing().inCheck(newChessBoard);


//                System.out.println("print n3 in while v2");
                if (!check) {
                    move = (char) (position.x - numberOfRows * direction + 'a') +
                            String.valueOf((position.y + 1 - numberOfRows * direction));

                    position.y -= numberOfRows * direction;
                    position.x -= numberOfRows * direction;
                    break;
                }
//                possibleNumberOfRowsToMove.remove(numberOfRows);
                ok = possibleNumberOfRowsToMove.remove(numberOfRows);
            }
        } else {
            move = (char) (position.x - numberOfRows * direction + 'a') +
                    String.valueOf((position.y + 1 - numberOfRows * direction));
            position.y -= numberOfRows * direction;
            position.x -= numberOfRows * direction;

        }
        return move;
    }

    @Override
    public String moveSecondDiagonalBackwards() {


//        System.out.println("SUNT LA PRIMA 4");
        ArrayList<Integer> possibleNumberOfRowsToMove = new ArrayList<>();
        Integer direction = Moves.getInstance().getDirectionController();
        int i = 1;
        String chessBoard = Moves.getInstance().getChessBoard();
        String enemyArray = Moves.getInstance().getEnemyArray();

        while ((((position.y + i * direction) * 9 + (position.x + i * direction) + 1) >= 0) &&
                (((position.y + i * direction) * 9 + (position.x + i * direction) + 1) < 73)) {
            // if we find a blank square we could go further
            // if we find an enemy piece we can take it and stop there
            // if we find one of our pieces we stop before it
            if ("*".indexOf(chessBoard.charAt((position.y + i * direction) * 9 + (position.x + i * direction) + 1)) >= 0) {
                possibleNumberOfRowsToMove.add(i);
                i++;
            } else if (enemyArray.indexOf(chessBoard.charAt((position.y + i * direction) * 9 + (position.x + i * direction) + 1)) >= 0) {
                possibleNumberOfRowsToMove.add(i);
                break;
            } else {
                break;
            }
        }
        // update state of board and check if it's in check now
        Integer chosenNumber = random.nextInt(possibleNumberOfRowsToMove.size());
        Integer numberOfRows = possibleNumberOfRowsToMove.get(chosenNumber);
        String newChessBoard = updateString(position.x + numberOfRows * direction,
                position.y + numberOfRows * direction);
        Boolean check = Moves.getInstance().getKing().inCheck(newChessBoard);

        String move;
        if (check) {
//            System.out.println("print n4");
            // can return null here, if it can't move
            move = null;
            possibleNumberOfRowsToMove.remove(numberOfRows);
            while (possibleNumberOfRowsToMove.size() != 0) {
                chosenNumber = random.nextInt(possibleNumberOfRowsToMove.size());
                numberOfRows = possibleNumberOfRowsToMove.get(chosenNumber);
                newChessBoard = updateString(position.x + numberOfRows * direction,
                        position.y + numberOfRows * direction);
                check = Moves.getInstance().getKing().inCheck(newChessBoard);
                if (!check) {
                    move = (char) (position.x + numberOfRows * direction + 'a') +
                            String.valueOf((position.y + 1 + numberOfRows * direction));

                    position.y += numberOfRows * direction;
                    position.x += numberOfRows * direction;
                    break;
                }
                possibleNumberOfRowsToMove.remove(numberOfRows);
            }
        } else {
            move = (char) (position.x + numberOfRows * direction + 'a') +
                    String.valueOf((position.y + 1 + numberOfRows * direction));
            position.y += numberOfRows * direction;
            position.x += numberOfRows * direction;

        }
        return move;
    }

    @Override
    public String makeValidMove(String chessBoard) {
        String move;
        ArrayList<Integer> possibleMoves = new ArrayList<>();

//        System.out.println(position.x + " " + position.y);

        // change depending on with what pieces the AI plays
        Integer direction = Moves.getInstance().getDirectionController();
        String enemyArray = Moves.getInstance().getEnemyArray() + "*";


//        System.out.println("a intrat in nebun");

        // check validity for the first diagonal forward move
        // code 0
        if ((((position.y - (1 * direction)) * 9 + (position.x + (1 * direction)) + 1) >= 0) &&
                (((position.y - (1 * direction)) * 9 + (position.x + (1 * direction)) + 1) < 73) &&
                (enemyArray.indexOf(chessBoard.charAt((
                        position.y - (1 * direction)) * 9 + (position.x + (1 * direction)) + 1)) >= 0)) {
            possibleMoves.add(0);
        }
        // check validity for the first diagonal backward left
        // code 1
        if ((((position.y + (1 * direction)) * 9 + (position.x - (1 * direction) + 1)) >= 0) &&
                (((position.y + (1 * direction)) * 9 + (position.x - (1 * direction) + 1)) < 73) &&
                (enemyArray.indexOf(chessBoard.charAt(
                        (position.y + (1 * direction)) * 9 + (position.x - (1 * direction) + 1))) >= 0)) {
            possibleMoves.add(1);
        }
        // check validity for the second diagonal forward
        // code 2
        if ((((position.y - (1 * direction)) * 9 + ((position.x - (1 * direction)) + 1)) >= 0) &&
                (((position.y - (1 * direction)) * 9 + (position.x - (1 * direction) + 1)) < 73) &&
                (enemyArray.indexOf(chessBoard.charAt(
                        (position.y - (1 * direction)) * 9 + (position.x - (1 * direction) + 1))) >= 0)) {
            possibleMoves.add(2);
        }
        // check validity for the second diagonal backwards
        // code 3
        if ((((position.y + (1 * direction)) * 9 + (position.x + (1 * direction) + 1)) >= 0) &&
                (((position.y + (1 * direction)) * 9 + (position.x + (1 * direction) + 1)) < 73) &&
                (enemyArray.indexOf(chessBoard.charAt((
                        position.y + (1 * direction)) * 9 + (position.x + (1 * direction) + 1))) >= 0)) {
            possibleMoves.add(3);
        }

        // there can be not moves made with this piece
        if (possibleMoves.size() == 0) {
            return null;
        }

        char[] charArray = chessBoard.toCharArray();
        int oldPos = position.y * 9 + position.x + 1;

//        charArray[position.y * 9 + position.x + 1] = '*';

        // Choose at random a move to make on this piece
        // The moves have different weights
        StringBuilder newMove = new StringBuilder();

//        System.out.println("la nebun");
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
                charArray[position.y * 9 + position.x + 1] = 'b';
            } else {
                charArray[position.y * 9 + position.x + 1] = 'B';
            }
        }

        chessBoard = "";
        for (char i : charArray) {
            chessBoard += i;
        }

        Moves.getInstance().setChessBoard(chessBoard);

        move = newMove.toString();
        if (ok == 1) {
            return move;
        } else {
            return null;
        }
    }

    private String chooseTheMove(Integer moveCode) {
        switch (moveCode) {
            case 0:
                return this.moveFirstDiagonalForward();

            case 1:
                return this.moveFirstDiagonalBackwards();

            case 2:
                return this.moveSecondDiagonalForward();

            case 3:
                return this.moveSecondDiagonalBackwards();

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