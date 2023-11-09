import java.util.*;

public class Controller {
    public static void main(String[] args)
    {
        Board newBoard = new Board();
        Player playerOne = new Player("Player 1");
        Player playerTwo = new Player("Player 2");
        newBoard.initializeBoard();

        Presenter.displaySpikes(newBoard);
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the first player name : ");
        String inputString = in.nextLine();

        EXIT_PROGRAM:
        {
            if(!checkQuitCommand(inputString))
            {
                //Exit the program
                break EXIT_PROGRAM;
            }
            playerOne.setName(inputString);
            System.out.print("Enter the second player name : ");
            inputString = in.nextLine();
            if(!checkQuitCommand(inputString))
            {
                //Exit program
                break EXIT_PROGRAM;
            }
            playerTwo.setName(inputString);

            //Displays the play board
            Presenter.displayPlayArea(playerOne,playerTwo);
            Presenter.displayCommandPrompt();
            String command = in.nextLine();


            /*
            * Super loop to play the game. Accpet the command from the user.
            */
            while(!(command.equals("Q") || command.equals("q")))
            {
                Command.acceptCommand(command);
                int[] temp = new int[2];
                temp = Command.getDiceRoll();
                System.out.println("1st Dice: " + temp[0]);
                System.out.println("2nd Dice: " + temp[1]);
                Presenter.displayPlayArea(playerOne,playerTwo);
                Presenter.displayCommandPrompt();
                command = in.nextLine();
            }
        }

        //Exit acknowladgement message
        System.out.println("QUIT");
    }

    private static boolean checkQuitCommand(String inputArg)
    {
        boolean returnState = true;
        if(inputArg.equals("q") || inputArg.equals("Q"))
        {
            return false;
        }
        return returnState;
    }
}
