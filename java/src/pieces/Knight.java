package pieces;

import common.Pair;
import interfaces.KnightInterface;
import xboard.Moves;
import xboard.XBoard;

import java.util.ArrayList;
import java.util.Random;

public class Knight implements KnightInterface {
    private Pair position;
    private final Random random = new Random();

    public Knight(Pair position) {
        this.position = position;
    }

    private String updateString(int newX, int newY) {
        String prevChessBoard = Moves.getInstance().getChessBoard();
        char[] charArray = prevChessBoard.toCharArray();

        charArray[position.y * 9 + position.x + 1] = '*';
        if (XBoard.getInstance().isPlayingWhite()) {
            charArray[newY * 9 + newX + 1] = 'n';
        } else {
            charArray[newY * 9 + newX + 1] = 'N';
        }

        String chessBoard = "";
        for (char i : charArray) {
            chessBoard += i;
        }
        return chessBoard;
    }

    @Override
    public String oneRightTwoForwardMove() {
        int direction = Moves.getInstance().getDirectionController();

        String newChessboard = updateString(position.x + 1 * direction,
                position.y - 2 * direction);
        Boolean check = Moves.getInstance().getKing().inCheck(newChessboard);

        if (!check) {
            String move = (char)(position.x + 1 * direction + 'a') +
                    String.valueOf(position.y - (2 * direction) + 1);
            position.y -= 2 * direction;
            position.x += 1 * direction;
            return move;
        } else {
            return null;
        }
    }

    @Override
    public String twoRightOneForwardMove() {
        int direction = Moves.getInstance().getDirectionController();

        String newChessboard = updateString(position.x + 2 * direction,
                position.y - 1 * direction);
        Boolean check = Moves.getInstance().getKing().inCheck(newChessboard);

        if (!check) {
            String move = (char)(position.x + 2 * direction + 'a') +
                    String.valueOf(position.y - (1 * direction) + 1);
            position.y -= 1 * direction;
            position.x += 2 * direction;
            return move;
        } else {
            return null;
        }
    }

    @Override
    public String twoRightOneBackwardsMove() {
        int direction = Moves.getInstance().getDirectionController();

        String newChessboard = updateString(position.x + 2 * direction,
                position.y + 1 * direction);
        Boolean check = Moves.getInstance().getKing().inCheck(newChessboard);

        if (!check) {
            String move = (char)(position.x + 2 * direction + 'a') +
                    String.valueOf(position.y + (1 * direction) + 1);
            position.y += 1 * direction;
            position.x += 2 * direction;
            return move;
        } else {
            return null;
        }
    }

    @Override
    public String oneRightTwoBackwardsMove() {
        int direction = Moves.getInstance().getDirectionController();

        String newChessboard = updateString(position.x + 1 * direction,
                position.y + 2 * direction);
        Boolean check = Moves.getInstance().getKing().inCheck(newChessboard);

        if (!check) {
            String move = (char)(position.x + 1 * direction + 'a') +
                    String.valueOf(position.y + (2 * direction) + 1);
            position.y += 2 * direction;
            position.x += 1 * direction;
            return move;
        } else {
            return null;
        }
    }

    @Override
    public String twoLeftOneForwardMove() {
        int direction = Moves.getInstance().getDirectionController();

        String newChessboard = updateString(position.x - 2 * direction,
                position.y - 1 * direction);
        Boolean check = Moves.getInstance().getKing().inCheck(newChessboard);

        if (!check) {
            String move = (char)(position.x - 2 * direction + 'a') +
                    String.valueOf(position.y - (1 * direction) + 1);
            position.y -= 1 * direction;
            position.x -= 2 * direction;
            return move;
        } else {
            return null;
        }

    }

    @Override
    public String twoLeftOneBackwardsMove() {
        int direction = Moves.getInstance().getDirectionController();

        String newChessboard = updateString(position.x - 2 * direction,
                position.y + 1 * direction);
        Boolean check = Moves.getInstance().getKing().inCheck(newChessboard);

        if (!check) {
            String move = (char)(position.x - 2 * direction + 'a') +
                    String.valueOf(position.y + (1 * direction) + 1);
            position.y += 1 * direction;
            position.x -= 2 * direction;
            return move;
        } else {
            return null;
        }

    }

    @Override
    public String oneLeftTwoForwardMove() {
        int direction = Moves.getInstance().getDirectionController();

        String newChessboard = updateString(position.x - 1 * direction,
                position.y - 2 * direction);
        Boolean check = Moves.getInstance().getKing().inCheck(newChessboard);

        if (!check) {
            String move = (char)(position.x - 1 * direction + 'a') +
                    String.valueOf(position.y - (2 * direction) + 1);
            position.y -= 2 * direction;
            position.x -= 1 * direction;
            return move;
        } else {
            return null;
        }

    }

    @Override
    public String oneLeftTwoBackwardsMove() {
        int direction = Moves.getInstance().getDirectionController();

        String newChessboard = updateString(position.x - 1 * direction,
                position.y + 2 * direction);
        Boolean check = Moves.getInstance().getKing().inCheck(newChessboard);

        if (!check) {
            String move = (char)(position.x - 1 * direction + 'a') +
                    String.valueOf(position.y + (2 * direction) + 1);
            position.y += 2 * direction;
            position.x -= 1 * direction;
            return move;
        } else {
            return null;
        }

    }

    @Override
    public String makeValidMove(String chessBoard) {
        String move = null;
        ArrayList<Integer> possibleMoves = new ArrayList<>();

//        System.out.println(position.x + " " + position.y);

        // change depending on with what pieces the AI plays
        int direction = Moves.getInstance().getDirectionController();
        String enemyArray = Moves.getInstance().getEnemyArray() + "*";

        // oneRightTwoForwardMove
        if ((((position.y - (2 * direction)) * 9 + (position.x + 1 * direction) + 1) >= 0) &&
                (((position.y - (2 * direction)) * 9 + (position.x + 1 * direction) + 1) < 73) &&
                (enemyArray.indexOf(chessBoard.charAt((
                        position.y - (2* direction)) * 9 + (position.x + 1 * direction) + 1)) >= 0)) {
            possibleMoves.add(0);
        }

        // twoRightOneForwardMove
        if ((((position.y - (1 * direction)) * 9 + (position.x + 2 * direction) + 1) >= 0) &&
                (((position.y - (1 * direction)) * 9 + (position.x + 2 * direction) + 1) < 73) &&
                (enemyArray.indexOf(chessBoard.charAt((
                        position.y - (1 * direction)) * 9 + (position.x + 2 * direction) + 1)) >= 0)) {
            if (!(position.x + 2 * direction < 0 || position.x + 2 * direction > 7)) {
                possibleMoves.add(1);
            }
        }

        // twoRightOneBackwardsMove
        if ((((position.y + (1 * direction)) * 9 + (position.x + 2 * direction) + 1) >= 0) &&
                (((position.y + (1 * direction)) * 9 + (position.x + 2 * direction) + 1) < 73) &&
                (enemyArray.indexOf(chessBoard.charAt((
                        position.y + (1 * direction)) * 9 + (position.x + 2 * direction) + 1)) >= 0)) {
            if (!(position.x + 2 * direction < 0 || position.x + 2 * direction > 7)) {
                possibleMoves.add(2);
            }
        }

        // oneRightTwoBackwardsMove
        if ((((position.y + (2 * direction)) * 9 + (position.x + 1 * direction) + 1) >= 0) &&
                (((position.y + (2 * direction)) * 9 + (position.x + 1 * direction) + 1) < 73) &&
                (enemyArray.indexOf(chessBoard.charAt((
                        position.y + (2 * direction)) * 9 + (position.x + 1 * direction) + 1)) >= 0)) {
            possibleMoves.add(3);
        }

        // oneLeftTwoBackwardsMove
        if ((((position.y + (2 * direction)) * 9 + (position.x - 1 * direction) + 1) >= 0) &&
                (((position.y + (2 * direction)) * 9 + (position.x - 1 * direction) + 1) < 73) &&
                (enemyArray.indexOf(chessBoard.charAt((
                        position.y + (2 * direction)) * 9 + (position.x - 1 * direction) + 1)) >= 0)) {
            possibleMoves.add(4);
        }

        // twoLeftOneBackwardsMove
        if ((((position.y + (1 * direction)) * 9 + (position.x - 2 * direction) + 1) >= 0) &&
                (((position.y + (1 * direction)) * 9 + (position.x - 2 * direction) + 1) < 73) &&
                (enemyArray.indexOf(chessBoard.charAt((
                        position.y + (1 * direction)) * 9 + (position.x - 2 * direction) + 1)) >= 0)) {
            if (!(position.x - 2 * direction < 0 || position.x - 2 * direction > 7)) {
                possibleMoves.add(5);
            }
        }

        // twoLeftOneForwardMove
        if ((((position.y - (1 * direction)) * 9 + (position.x - 2 * direction) + 1) >= 0) &&
                (((position.y - (1 * direction)) * 9 + (position.x - 2 * direction) + 1) < 73) &&
                (enemyArray.indexOf(chessBoard.charAt((
                        position.y - (1 * direction)) * 9 + (position.x - 2 * direction) + 1)) >= 0)) {
            if (!(position.x - 2 * direction < 0 || position.x - 2 * direction > 7)) {
                possibleMoves.add(6);
            }
        }



        // oneLeftTwoForwardMove
        if ((((position.y - (2 * direction)) * 9 + (position.x - 1 * direction) + 1) >= 0) &&
                (((position.y - (2 * direction)) * 9 + (position.x - 1 * direction) + 1) < 73) &&
                (enemyArray.indexOf(chessBoard.charAt((
                        position.y - (2 * direction)) * 9 + (position.x - 1 * direction) + 1)) >= 0)) {
            possibleMoves.add(7);
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

        newMove.append((char)('a' + position.x));
        newMove.append((position.y + 1));
        Integer getAMove = possibleMoves.get(random.nextInt(possibleMoves.size()));
        String theMove = chooseTheMove(getAMove);
//        newMove.append(chooseTheMove(possibleMoves.get(random.nextInt(possibleMoves.size()))));

        int ok = 0;
        if (theMove != null) {
            ok = 1;
            charArray[oldPos] = '*';
            newMove.append(theMove);
        } else {
            possibleMoves.remove(getAMove);
            while (possibleMoves.size() != 0) {
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
                charArray[position.y * 9 + position.x + 1] = 'n';
            } else {
                charArray[position.y * 9 + position.x + 1] = 'N';
            }
        }

        chessBoard = "";
        for(char i:charArray) {
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

    /**
     *
     * @param moveCode This is the code of one of the methods for movement
     * @return
     */
    private String chooseTheMove(Integer moveCode) {
        return switch (moveCode) {
            case 0 -> this.oneRightTwoForwardMove();
            case 1 -> this.twoRightOneForwardMove();
            case 2 -> this.twoRightOneBackwardsMove();
            case 3 -> this.oneRightTwoBackwardsMove();
            case 4 -> this.oneLeftTwoBackwardsMove();
            case 5 -> this.twoLeftOneBackwardsMove();
            case 6 -> this.twoLeftOneForwardMove();
            case 7 -> this.oneLeftTwoForwardMove();
            default -> null;
        };
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
