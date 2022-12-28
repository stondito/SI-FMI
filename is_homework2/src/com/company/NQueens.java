package com.company;

import java.util.LinkedList;
import java.util.Scanner;

public class NQueens {

    static void printBoard(int N) {
        for (int i=0; i<N; ++i) {
            for (int j = 0; j < N; ++j) {
                if (j == board[i]) {
                    System.out.print(" * ");
                }
                else {
                    System.out.print(" _ ");
                }
            }
            System.out.println();
        }
    }

    static int calculateConflicts(int[] mainD, int[] secondD, int N, int[] rowsWeight, int mid) {
        int sum = 0;

        for(int i=0; i<N; ++i) {
            ++secondD[i + board[i]]; // обратния диагонал
            ++mainD[mid + (i - board[i])];
            ++rowsWeight[board[i]];
        }

        for (int i=0; i<N; ++i) {
            sum += secondD[i + board[i]] + mainD[mid + (i - board[i])] + rowsWeight[board[i]];
        }

        return sum;
    }

    static void shiftQueen(int[] mainD, int[] secondD, int N, int[] rowsWeight, int mid) {
        int max = -1;

        int weights;
        LinkedList<Integer> positionsMaxWeights = new LinkedList<>();
        int[] positionsMaxRows = new int[N];

        for(int i=0; i<N; ++i) {
            weights = secondD[i + board[i]] + mainD[mid + (i - board[i])] + rowsWeight[board[i]];

            if (max < weights) {
                positionsMaxWeights.clear();
                positionsMaxWeights.add(i);
                max = weights;
                positionsMaxRows[i] = board[i];
            }
            else if (max == weights) {
                positionsMaxWeights.add(i);
                positionsMaxRows[i] = board[i];
            }
        }

        int col = positionsMaxWeights.get( (int)(Math.random()*(positionsMaxWeights.size())+0));
        int rowMax = positionsMaxRows[col];

        int minWeight = Integer.MAX_VALUE;

        LinkedList<Integer> positionsMinWeights = new LinkedList<>();
        int curr;
        for (int i=0; i<N; ++i) {
            curr = secondD[col + i] + mainD[mid + (col -i)] + rowsWeight[i];
            if (minWeight >  curr) { // +1 one more for row, +1 one more for mainD and so on
                minWeight = curr;
                positionsMinWeights.clear();
                positionsMinWeights.add(i);
            }
            else if (minWeight == curr) {
                positionsMinWeights.add(i);
            }
        }

        int rowMin = positionsMinWeights.get( (int)(Math.random()*(positionsMinWeights.size())+0));

        board[col] = rowMin;

        // After change remove previous weights
        --secondD[rowMax + col];
        --mainD[mid + (col -rowMax)];
        --rowsWeight[rowMax];

        ++secondD[col+ rowMin]; // обратния диагонал
        ++mainD[mid + (col - rowMin)];
        ++rowsWeight[rowMin];
    }

    static boolean check(int[] mainD, int[] secondD, int N, int[] rowsWeight, int mid) {
        for(int i=0; i<N; ++i) {
            if (3 != secondD[i + board[i]] + mainD[mid + (i - board[i])]  + rowsWeight[board[i]]) {
                return false;
            }
        }
        return true;
    }

    static int[] board;

    static long startTime;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();

        board = new int[N];
        int[] rowsWeight = new int[N];

        startTime = System.currentTimeMillis();

        int cntDiagonals = 2*N - 1;
        int[] mainD = new int[cntDiagonals];
        int[] secondD = new int[cntDiagonals];

        int min = 0;

        for (int i = 0; i < N; ++i) {
            board[i] = (int)(Math.random()*(N -min)+min);
        }

        int minMatrixWeight = N*3;
        int mid = mainD.length / 2;
        int firstResult = calculateConflicts(mainD, secondD, N, rowsWeight, mid);

        if (firstResult == minMatrixWeight) {
            long endTime = System.currentTimeMillis();
            System.out.printf(" %.4f \n", ((endTime - startTime) / 1000.0));
        }
        else {
            do {
                shiftQueen(mainD, secondD, N, rowsWeight, mid);
            }while (!check(mainD, secondD, N,  rowsWeight, mid));

            long endTime = System.currentTimeMillis();
            System.out.printf("%.4f \n", ((endTime - startTime) / 1000.0));
        }

        printBoard(N);

    }

}