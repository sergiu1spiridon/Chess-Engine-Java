package xboard;

import java.util.Scanner;

public class GameCommunication {

    public static void readCommands() {

        XBoard xBoard = XBoard.getInstance();
        Scanner scanner = new Scanner(System.in);
        boolean gameClosed = false;

        while (!gameClosed) {
            String command = scanner.nextLine();
            String copyCommand = command.split(" ")[0];
            boolean checkMove = copyCommand.matches(".*\\d.*");
            if (!checkMove) {
                switch (copyCommand) {
                    case "new":
                        xBoard.reset();
                        xBoard.setPlayingWhite(false);
                        xBoard.setPause(false);
                        break;
                    case "force":
                        xBoard.setPause(true);
                        break;
                    case "go":
                        xBoard.setPause(false);
                        xBoard.makeNewMove();
                        break;
                    case "protover":
                        System.out.println("feature sigint=0");
                        System.out.println("feature san=0");
                        System.out.println("feature usermove=0");
                        break;
                    case "quit":
                        gameClosed = true;
                        break;
                    case "white":
                        xBoard.setPlayingWhite(true);
                        xBoard.setPause(false);
                        break;
                    case "black":
                        xBoard.setPlayingWhite(false);
                        xBoard.setPause(false);
                        break;
                    default:
                        break;
                }
            } else {
                // Got a command for move now.
                xBoard.getPlayerMove(command);
                xBoard.makeNewMove();
            }

        }

    }
}
