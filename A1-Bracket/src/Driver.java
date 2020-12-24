import java.util.Scanner;
/**
 * this is a driver class.
 */
public class Driver {
    public static void main(String[] args) {
        Bracket b = new Bracket(128);
        Scanner kbd = new Scanner(System.in);
        int currentRound = 0;

        showMenu();
        String userInput = kbd.nextLine();
        while (!isValid(userInput)) {
            showMenu();
            userInput = kbd.nextLine();
        }
        int input = Integer.parseInt(userInput);

        while (input != 3) {
            if (input == 1) {
                currentRound++;
                loadRound(currentRound,b);
            } else searchPlayer(kbd,b);

            showMenu();
            userInput = kbd.nextLine();
            while (!isValid(userInput)) {
                showMenu();
                userInput = kbd.nextLine();
            }
            input = Integer.parseInt(userInput);
        }
    }

    /**
     * this method calls to given brackets loadresults method to load a particular round.
     * @param roundNumber the round number to load
     * @param bracket the bracket to load into.
     */
    public static void loadRound(int roundNumber, Bracket bracket){
        if (roundNumber < 9) {
            String fileName = "round-" + roundNumber;
            System.out.println(fileName + ".txt");
            System.out.println("Loading matches for " + fileName);
            bracket.loadResults(roundNumber, fileName + ".txt");
            bracket.toJSON();
            System.out.println("Bracket written to Bracket.json");

        } else {
            System.out.println("All the rounds have been loaded.");
        }
    }

    /**
     * this method get the all matched player of a provided player from given bracket.
     * @param kbd a Scanner to take input from user
     * @param bracket the bracket to get the matched player from.
     */
    public static void searchPlayer(Scanner kbd, Bracket bracket){
        System.out.println("Enter the player name: ");
        String name = kbd.nextLine();
        System.out.println(bracket.getMatchesForPlayer(name));
    }

    /**
     * this method check if the number is valid or not.
     * @param input the user input to validate
     * @return true if it's valid number or false.
     */
    public static boolean isValid(String input){
        try{
            int num = Integer.parseInt((input));
            return num>0 || num<4;
        }
        catch (Exception e){
            System.out.println("Please enter a valid number. ");
        }
        return false;
    }
    /**
     * this method prints a menu of what can be done with the program.
     */
    public static void showMenu(){
        System.out.println("Enter a choice:\n" +
                "1. Load the results for the next round\n" +
                "2. Display the matches for a particular player\n" +
                "3. Quit");
    }
}
