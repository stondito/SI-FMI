//package com.company.other;
//
//import java.io.PrintStream;
//import java.util.ArrayList;
//import java.util.Random;
//import java.util.Scanner;
//
//public class Solver{
//    private static class Board{
//        Random random = new Random();
//        int[] x;
//
//        Board(int n){
//            x = new int[n];
//            scramble();
//        }
//
//        private void scramble(){
//            int n = x.length;
//            for(int i = 0; i < n; i++){
//                x[i] = i;
//            }
//            for(int i = 0; i < n; i++){
//                int j = random.nextInt(n);
//                int rowToSwap = x[i];
//                x[i] = x[j];
//                x[j] = rowToSwap;
//            }
//        }
//        int conflicts(int row, int col){
//            int count = 0;
//            for(int c = 0; c < x.length; c++){
//                if(c == col){
//                    continue;
//                }
//                int r = x[c];
//                if (r == row || Math.abs(r-row) == Math.abs(c-col)){
//                    count++;
//                }
//            }
//            return count;
//        }
//
//        private void solve(){
//            int moves = 0;
//            ArrayList<Integer> candidates = new ArrayList<Integer>();
//
//            while(true){
//                int maxConflicts = 0;
//                candidates.clear();
//                for(int c = 0; c < x.length; c++){
//                    int conflicts = conflicts(x[c],c);
//                    if(conflicts == maxConflicts){
//                        candidates.add(c);
//                    }else if(conflicts > maxConflicts){
//                        maxConflicts = conflicts;
//                        candidates.clear();
//                        candidates.add(c);
//                    }
//                }
//
//                if(maxConflicts == 0){
//                    return;
//                }
//
//                int worstQueenColumn = candidates.get(random.nextInt(candidates.size()));
//                int minConflicts = x.length;
//                candidates.clear();
//                for(int r = 0; r < x.length; r++){
//                    int conflicts = conflicts(r, worstQueenColumn);
//                    if(conflicts == minConflicts){
//                        candidates.add(r);
//                    }else if(conflicts < minConflicts){
//                        minConflicts = conflicts;
//                        candidates.clear();
//                        candidates.add(r);
//                    }
//                }
//
//                if(!candidates.isEmpty()){
//                    x[worstQueenColumn] = candidates.get(random.nextInt(candidates.size()));
//                }
//                moves++;
//                if(moves == x.length * 2){
//                    scramble();
//                    moves = 0;
//                }
//            }
//        }
//
//        private void print(PrintStream stream){
//
////            int N = x.length;
////            for (int row = 0; row < N ; row++){
////                for (int col = 0; col < N; col++){
////                    if(x[col] == row){
////                        System.out.print("* ");
////                    }
////                    else{
////                        System.out.print('_' + " ");
////                    }
////                }
////                System.out.println();
////            }
//        }
//    }
//
//    public static void main(String[] args){
//        Scanner input = new Scanner(System.in);
//        System.out.println("Enter number of Queens:");
//        int n=input.nextInt();
//        Board board = new Board(n);
//
//        board.solve();
//        board.print(System.out);
//    }
//}