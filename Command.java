import java.util.*;

public class Command extends Dice{
    
    static int[] diceRoll = new int[2];
    private static boolean gameStart = false;

    public static void acceptCommand(String command, Board boardObj, Turn turnObj, Player playerOne, Player playerTwo)
    {
        switch (command.toLowerCase()) {
            case "r":
                /* Roll the dice */
                diceRoll = roll();
                //Show possible moves
                prediction(diceRoll[0],diceRoll[1],turnObj.getTurn(),boardObj);
                turnObj.toggleTurn(playerOne,playerTwo);
                turnObj.displayTurn(playerOne, playerTwo);
                // printMoves(moveList);
                break;
            case "hint":
                System.out.println("==============================");
                System.out.println("Commands to play:");
                System.out.println("1. R/r to roll");
                System.out.println("2. Q/q to quit");
                if(getGameState())
                {
                    System.out.println("3. Show to display the board");
                }else
                {
                    System.out.println("3. Start to initiate the game");
                    System.out.println("4. Show to display the board");
                }
                System.out.println("==============================");
            break;

            case "start":
                Presenter.displayPlayArea(boardObj,playerOne,playerTwo);
                startGame();
            break;

            case "show":
                if(getGameState())
                {
                    Presenter.displayPlayArea(boardObj,playerOne,playerTwo);
                }else
                {
                    System.out.println("Game is not yet started! Enter START to initiate the game.");
                }
            default:
                System.out.println("Invalid command. Please enter 'R/r' to roll the dice or 'Q/q' to Quit");
                break;
        }

    }

    public static int[] getDiceRoll()
    {
        return diceRoll;
    }

    static public void prediction(int nd1, int nd2, boolean turnPlayer, Board board) 
    {
        String playerColor = (turnPlayer == true) ? Checker.BLACK : Checker.RED;
        ArrayList<int[]> moveList = new ArrayList<>();

        for(int indexSpike = 0; indexSpike < 26; indexSpike++)
        {
            Spike temp = new Spike();
            temp = board.getSpike(indexSpike);

            if(!temp.isEmpty())
            {

                int indexCheckers = temp.size() - 1;
                if(playerColor.equals(temp.getCheckers(indexCheckers).getColor()))
                {
                    int source = temp.getCheckers(indexCheckers).getPosition();
                    checkAndAddMove(moveList, source, nd1, board, playerColor);
                    checkAndAddMove(moveList, source, nd2, board, playerColor);
                    checkAndAddMove(moveList, source, nd1 + nd2, board, playerColor);
                }
            }
        }

        printMoves(moveList);
    }

    static private void checkAndAddMove(List<int[]> moveList, int source, int steps, Board board, String playerColor) {
        int dest = source + steps;
        
        //need to skip the index for the bar
        if(dest == Board.BAR_SIPKE_FIRST_HALF || dest == Board.BAR_SIPKE_SECOND_HALF)
        {
            dest += 1;
        }
        if (isValidMove(dest, board, playerColor)) {
            moveList.add(new int[] {source, dest});
        }
    }

    static private boolean isValidMove(int dest, Board board, String playerColor) {
        if (dest < 0 || dest >= board.getTotalNoOfSpikes()) {
            return false;
        }

        String opponentColor = playerColor.equals(Checker.RED) ? Checker.BLACK : Checker.RED;
        Spike destinationSpike = board.getSpike(dest);
        return destinationSpike.nbColoredChecker(opponentColor) <= 1;
    }

    static private void printMoves(List<int[]> moveList) {
        System.out.println("Possible Moves:");
        int possibleMoves = 0;
        for (int[] move : moveList) {
            System.out.println(possibleMoves + ") " + move[0] + " to " + move[1]);
            possibleMoves++;
        }
    }

    private static void startGame()
    {
        gameStart = true;

    }

    private static boolean getGameState()
    {
        return gameStart;
    }

}
