/**
 * Flip Frauenzimmer
 * Email: ffrauenzimmer@ucsd.edu
 * PID: A17685777
 * Sources Used: Java Interface Documentation, PA1 Write-up
 * 
 * This file is for CSE 12 PA1 in Spring 2023,
 * and contains the definition of RPS with a constructor,
 * main method for user move, and determines the winner of RPS.
 */

import java.util.Scanner;

/** 
 * This class has a constructor of possible moves, 
 * main method for user move, and determines the winner of a game.
 */
public class RPS extends RPSAbstract {
    // Messages for the game.
    protected static final String GAME_NOT_IMPLEMENTED =
            "Game not yet implemented.";
    /**
     * Construct a new instance of RPS with the given possible moves.
     *
     * @param moves all possible moves in the game.
     */
    public RPS(String[] moves) {
        this.possibleMoves = moves;
        this.playerMoves = new String[MAX_GAMES];
        this.cpuMoves = new String[MAX_GAMES];
    }


    /**
     * Reads user input, generates CPU moves, and plays the game.
     */
    public static void main(String[] args) {
        // If command line args are provided use those as the possible moves
        String[] moves = new String[args.length];
        if (args.length >= MIN_POSSIBLE_MOVES) {
            System.arraycopy(args, 0, moves, 0, args.length);
        } else {
            moves = RPS.DEFAULT_MOVES;
        }
        // Create new game and scanner
        RPS game = new RPS(moves);
        Scanner in = new Scanner(System.in);

        while (true) {
            // Prompts move, generates cpumove, stores playermove
            System.out.println(PROMPT_MOVE);
            String cpuMove = game.genCPUMove();
            String playerMove = in.nextLine();
            
            // While user does not input "q", play game
            if (playerMove.equals(QUIT)) {
                break;
            }

            // Checks if invalid move
            if (!game.isValidMove(playerMove)) {
                System.out.println(INVALID_INPUT);
                continue;
            }

            // Only valid moves, plays game
            game.playRPS(playerMove, cpuMove);
        }
        // When input is "q"
        game.displayStats();
        in.close();
    }

    /**
     * Takes the user move, the CPU move, and determines who wins.
     * 
     * @param playerMove move of the player
     * @param cpuMove move of the CPU
     * @return -1 for invalid move, 0 for tie, 1 for player win, 2 for cpu win
     */
    @Override
    public int determineWinner(String playerMove, String cpuMove) {
        int MAX_DIST = possibleMoves.length - 1;
        int PLAYER_POS = -1;
        int CPU_POS = -1;
        // finds index of playerMove and cpuMove within possibleMoves
        for (int i = 0; i < possibleMoves.length; i ++) {
            if (possibleMoves[i].equals(playerMove)) {PLAYER_POS = i;}
            if (possibleMoves[i].equals(cpuMove)) {CPU_POS = i;}
        }
        
        // Determines win outcome to return
        if (PLAYER_POS == -1
                || CPU_POS == -1) {
            return INVALID_INPUT_OUTCOME;
        }
        if (PLAYER_POS - CPU_POS == 1 
                || CPU_POS - PLAYER_POS == MAX_DIST) {
            return CPU_WIN_OUTCOME;
        }
        if (CPU_POS - PLAYER_POS == 1 
                || PLAYER_POS - CPU_POS == MAX_DIST){
            return PLAYER_WIN_OUTCOME;
        }
        else {
            return TIE_OUTCOME;
        }
    }
}