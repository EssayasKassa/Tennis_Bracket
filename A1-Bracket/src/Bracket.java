import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
/**
 * Assignment 1 - CS 2920
 * @ Author - Essayas Kassa (313737) & Moshiur Rahman (310782)
 */
public class Bracket {
    private String[] arr;
    /**
     Constructor
     @param numCompetitors the number of competitors in the tournament
     **/
    public Bracket(int numCompetitors) {
        arr = new String[calcArraySize(numCompetitors)];
        for(int i=1;i<arr.length;i++){
            arr[i] = "-";
        }
        toJSON(); //this will create json with empty brackets
    }
    /**
     Load the results for a given round into the Bracket
     @param round - the round in the tournament being loaded
     @param resultsFile - the file where the results for the given round can be found
     **/
    public void loadResults(int round, String resultsFile) {
        int pointer = getPointer(round);

        File file = new File(resultsFile);
        try {
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()){
                arr[pointer] = scan.nextLine();
                pointer++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     Return a String of the matches for a given player. Each match should appear on its own line and should only include the opponent name.
     The String starts with the first round match (opponent) for the player, round 2 (if applicable) is on line 2, etc.
     **/
    public String getMatchesForPlayer(String player) {
        int index = getPointer(1);

        for(int i=index; i<arr.length; i++){
            if(arr[i].equals(player)){
                return traverse(i);
            }
        }
        return"Oops! No player found..!";
    }
    /**
     * this method recursively search for the opponent of a given player index. it searches from the bottom of the bracket and works it
     * way up until the last match of the given player.
     * @param child the index of the given player
     * @return String representation of all the opponents of the given player
     */
    private String traverse(int child){
        int parent = getParentIndex(child);
        int sibling = getSiblingIndex(parent,child);
        //parent == 1 is the root of the bracket. that's the end.
        if(!arr[parent].equals(arr[child]) || parent == 1 ) return arr[sibling];

        return arr[sibling]+"\n"+traverse(parent);
    }
    /**
     Return the bracket in JSON format
     Also calls the createJson method which actually create the JSON string of the bracket
     **/
    public String toJSON() {
        String json ="";
        try {
            PrintWriter pw = new PrintWriter("Bracket.json");
            json = createJson(1);
            pw.println(json);
            pw.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return json;
    }
    /**
     * this method creates JSON format string of the whole bracket which involves recursion.
     * @param parent the parent node of a particular tree.
     * @return JSON string
     */
    private String createJson(int parent){
        String name = arr[parent];
        if(!hasChildren(parent)){
            return "{\n\"name\" : \""+name+"\"\n}";
        } else {
            return  "{\n\"name\" : \""+name+"\",\n\"children\" : [" +createJson(getLeftChildIndex(parent))+",\n"+createJson(getRightChildIndex(parent))+"\n]}";
        }
    }
    /**
     * this method calculate what will be the size of the array based on the number of competitors.
     * @param numCompetitors the number of competitors
     * @return size the size if the array
     */
    private int calcArraySize(int numCompetitors){
        int size = 2;
        while (numCompetitors > 1){
            size +=numCompetitors;
            numCompetitors = numCompetitors/2;
        }
        return size;
    }
    /**
     * this method calculates from what position on the array a given round has been stored.
     * @param roundNumber the number of round
     * @return the position(index) on the array.
     */
    private int getPointer(int roundNumber){
        return (int) (arr.length/(Math.pow(2,(roundNumber))));
    }
    /**
     * this method calculate the parent index of a given child index
     * @param child index of the child
     * @return the parent index
     */
    private int getParentIndex(int child){
        return child/2;
    }
    /**
     * this  method calculate the left child index of a given parent index
     * @param parent the index of the parent
     * @return the left child index
     */
    private int getLeftChildIndex(int parent){
        return parent *2;
    }
    /**
     * this  method calculate the right child index of a given parent index
     * @param parent the index of the parent
     * @return the right child index
     */
    private  int getRightChildIndex(int parent){
        return (parent*2) + 1;
    }
    /**
     * this method determines which is the other opponent(sibling) of a given parent
     * @param parent the parent index
     * @param child the child index
     * @return the index of the other sibling
     */
    private int getSiblingIndex(int parent, int child){
        if(getLeftChildIndex(parent)==child) return getRightChildIndex(parent);
        else return getLeftChildIndex(parent);
    }
    /**
     * this method returns true or false based on if the given parent has children or not
     * @param parent the index of the parent
     * @return boolean true if parent has children, false if parent doesn't.
     */
    private boolean hasChildren(int parent){
        return parent*2<arr.length;
    }
}
