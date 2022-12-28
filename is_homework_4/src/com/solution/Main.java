package com.solution;


import java.util.Scanner;

public class Main {
    static char emptyCell = '-';
    static int maxScore = 10;
    static int minScore = -10;
    static int isPlayerTurn;
    static char computer;
    static char player;
    static boolean isPair = true;

    static char[][] board = { { '-', '-', '-' },
                              { '-', '-', '-' },
                              { '-', '-', '-' } };

    static void print() {
        System.out.println("=============");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println("\n=============");
        }
    }

    static boolean makeTurn(int i, int j, int isPersonTurn) {
        if (board[i][j] == emptyCell) {
            if (isPersonTurn == 1) {
                board[i][j] = player;
            }
            else {
                board[i][j] = computer;
            }

            System.out.println("--------------------");

            print();

            if (isThereWinner()) {
                if (isPersonTurn == 1) {
                    isPair = false;
                    System.out.println("You win!");
                }
                else {
                    System.out.println("You lost!");
                    isPair = false;
                }
            }

            return true;
        }

        return false;
    }

    static int maxScore(int a, int b, int depth) {
        int curScore = evaluateBoard(depth);

        // check if a player is winning
        if (curScore != 0) {
            return curScore;
        }

        if (!areThereMoreMoves()) {
            return 0;
        }

        //best score for max player is the smallest possible number
        int bestScore = Integer.MIN_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == emptyCell) {
                    board[i][j] = computer;

                    //recursive call, maximizer
                    bestScore = Math.max(bestScore, minScore(a, b, depth + 1));

                    //clear for next possible moves
                    board[i][j] = emptyCell;

                    if (bestScore >= b) {
                        return bestScore;
                    }
                    a = Math.max(a, bestScore);
                }
            }
        }
        return bestScore;
    }

    static int minScore(int a, int b, int depth) {
        int curScore = evaluateBoard(depth);

        // check if some players are winning
        if (curScore != 0) {
            return curScore;
        }
        //no moves left and no winner => tied
        if (!areThereMoreMoves()) {
            return 0;
        }

        //init best score for min player is the biggest possible number
        int bestScore = Integer.MAX_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == emptyCell) {
                    board[i][j] = player;
                    //recursive call, minimizer
                    bestScore = Math.min(bestScore, maxScore(a, b, depth + 1));
                    //undo so minimizer can try next possible moves
                    board[i][j] = emptyCell;

                    if (bestScore <= a) {
                        return bestScore;
                    }
                    b = Math.min(b, bestScore);
                }
            }
        }
        return bestScore;
    }

    static Point findBestComputerTurn() {
        int bestValue = Integer.MIN_VALUE;
        Point bestNextTurn = new Point();

        bestNextTurn.x = -1;
        bestNextTurn.y = -1;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == emptyCell) {
                    board[i][j] = computer;

                    int curTurnValue = minScore(Integer.MIN_VALUE, Integer.MAX_VALUE, 0);

                    //undo
                    board[i][j] = emptyCell;

                    if (curTurnValue > bestValue) {
                        bestNextTurn.x = i;
                        bestNextTurn.y = j;
                        bestValue = curTurnValue;
                    }
                }
            }
        }
        return bestNextTurn;
    }

    static int evaluateBoard(int depth) {
        //checking rows for victory
        for (int row = 0; row < 3; row++) {
            if (board[row][0] == board[row][1] && board[row][1] == board[row][2]) {
                if (board[row][0] == computer) {
                    return maxScore - depth;
                }
                else if (board[row][0] == player) {
                    return minScore + depth;
                }
            }
        }
        //checking columns for victory
        for (int col = 0; col < 3; col++) {
            if (board[0][col] == board[1][col] && board[1][col] == board[2][col]) {
                if (board[0][col] == computer) {
                    return maxScore - depth;
                }
                else if (board[0][col] == player) {
                    return minScore + depth;
                }
            }
        }

        //checking main diagonal for victory
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            if (board[0][0] == computer) {
                return maxScore - depth;
            }
            else if (board[0][0] == player) {
                return minScore + depth;
            }
        }

        //checking second diagonal for victory
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            if (board[0][2] == computer) {
                return maxScore - depth;
            }
            else if (board[0][2] == player) {
                return minScore + depth;
            }
        }

        //if it is tied => return 0
        return 0;
    }

    static boolean areThereMoreMoves() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == emptyCell) {
                    return true;
                }
            }
        }
        return false;
    }

    static boolean isThereWinner() {
        if (evaluateBoard(0) != 0) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to start the game? 0 - no 1 - yes : ");
        isPlayerTurn = scanner.nextInt();

        if (isPlayerTurn == 1) {
            computer = 'o';
            player = 'x';
        }
        else {
            computer = 'x';
            player = 'o';
        }

        while (areThereMoreMoves() && !isThereWinner()) {
            int i, j;
            if (isPlayerTurn == 1) {
                do {
                    System.out.println("Row:");
                    i = scanner.nextInt();
                    System.out.println("Column:");
                    j = scanner.nextInt();
                } while (!makeTurn(i - 1, j - 1, isPlayerTurn));
                --isPlayerTurn;
                continue;
            }
            Point bestTurn = findBestComputerTurn();
            makeTurn(bestTurn.x, bestTurn.y, isPlayerTurn);
            ++isPlayerTurn;
        }

        if(isPair) {
            System.out.println("Pair");
        }

    }
}
