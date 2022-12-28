package com.company;

import java.util.*;

public class Main {

    public static int row(int n) {
        return (int) Math.sqrt(n);
    }

    static boolean moveUp(int currZero, int m) { // OK
        return currZero > m;
    }

    static boolean moveDown(int currZero, int m) { // OK
        return (currZero - 1) / m + 1 < m ;
    }

    static boolean moveRight(int currZero, int m) { // OK
        return currZero % m - 1 >= 0;
    }

    static boolean moveLeft(int currZero, int m) {
        return currZero % m != 1;
    }

    static List<Integer> swap(List<Integer> curr, int zeroPos, int index) {
        List<Integer> copied = new ArrayList<>();
        copied.addAll(curr);

        int tmp = copied.get(index);
        copied.set(index, copied.get(zeroPos));
        copied.set(zeroPos, tmp);

        return copied;
    }
                        // future position - current
    static int optimized(int currZero, int casePosition, int m, List<Integer> matrix, List<Integer> newMatrix) {
        if (casePosition == 0) { // up
            return  MPF(matrix.get(currZero - m - 1), currZero - 1, m, newMatrix) - MPF(matrix.get(currZero - m - 1), currZero - m - 1, m, matrix);
        }
        else if (casePosition == 1) { // down
            return MPF(matrix.get(currZero + m - 1), currZero - 1, m, newMatrix) - MPF(matrix.get(currZero + m - 1), currZero + m - 1, m, matrix);
        }
        else if (casePosition == 2) { // right
            return MPF(matrix.get(currZero), currZero - 1, m, newMatrix) - MPF(matrix.get(currZero), currZero, m, matrix);
        }
        //left
        return MPF(matrix.get(currZero - 2), currZero - 1, m, newMatrix) - MPF(matrix.get(currZero - 2), currZero - 2, m, matrix);
    }

    static int MPF(int pos, int curr, int m, List<Integer> currM) {
        int row, goalRow, col, goalCol;

        if (currM.get(curr) % m == 0) {
            row = curr / m + 1;
            goalRow = (pos - 1) / m + 1;
        }
        else {
            row = curr / m + 1;
            goalRow = (pos - 1) / m + 1;
        }

        if (currM.get(curr) % m == 0) {
            goalCol = m;
            if ((curr + 1) % m == 0) {
                col = m;
            }
            else {
                col = (curr + 1) % m;
            }
        }
        else {
            goalCol = pos % m ;
            if ((curr + 1) % m == 0 ) {
                col = m;
            }
            else {
                col = (curr + 1) % m ;
            }
        }

        return Math.abs(row - goalRow) + Math.abs(col - goalCol);
    }

    static int calculateWeight(int m, List<Integer> curr) {
        int weight = 0;
        int N = curr.size();

        for (int i = 0; i < N; ++i) {
            if (curr.get(i) != 0) {
                weight += MPF(curr.get(i), i, m, curr);
            }
        }

        return weight;
    }

    static List<List<Integer>> getNeighbours(List<Integer> curr, int currZero, int m) {

        List<List<Integer>> neighbours = new ArrayList<>();

        if (moveUp(currZero, m)) {
            neighbours.add(0, swap(curr, currZero - 1, currZero - 1 - m));
        }
        else {
            neighbours.add(null);
        }
        if (moveDown(currZero, m)) {
            neighbours.add(1, swap(curr, currZero - 1, currZero -1 + m));
        }
        else {
            neighbours.add(null);
        }
        if (moveRight(currZero, m)) {
            neighbours.add(2, swap(curr, currZero - 1, currZero));
        }
        else {
            neighbours.add(null);
        }
        if (moveLeft(currZero, m)) {
            neighbours.add(3, swap(curr, currZero - 1, currZero - 1 - 1));
        }
        else {
            neighbours.add(null);
        }

        return neighbours;
    }

    static void findPath(List<Integer> matrix, int currZero, int m, int lv, int currLV, Set<List<Integer>> set, int weight) {
        // if is goal isFound = true
        if (isFound) {
            return;
        }

        if (lv <= currLV) {
            return;
        }

        Stack<List<Integer>> stack = new Stack<>();

        if (set.contains(matrix)) {
            return;
        }

        set.add(matrix);

        if (set.contains(goalMatrix)) {
            long endTime = System.currentTimeMillis();
            System.out.printf("%.4f \n",  ((endTime - startTime) / 1000.0));
            isFound = true;
            System.out.println("Found" + goalMatrix.toString());

            System.out.println(q.size());

            for(String path: q) {
                System.out.println(path);
            }

            return;
        }

        stack.add(matrix);

        while(!stack.isEmpty()) {
            List<Integer> curr = stack.pop();

            int minW = Integer.MAX_VALUE;
            int[] weights = {-1, -1, -1, -1};
            int i = 0;

            List<List<Integer>> neigh = getNeighbours(curr, currZero, m);

            for(List<Integer> n : neigh) {
                if (n != null && !set.contains(n)) {
                    int tmp = weight + optimized(currZero, i, m, matrix, n);

                    if (minW >= tmp) {
                        minW = tmp;
                        weights[i] = minW;
                    }
                }
                ++i;
            }

            Integer[] nextZeroPos = {-1, -1, -1, -1};
            for(int j=0; j<4; ++j) {
                if (weights[j] == minW) {
                    if (j == 0) { //up
                        nextZeroPos[j] = currZero - m;
                    }
                    else if (j == 1) { // down
                        nextZeroPos[j] = currZero + m;
                    }
                    else if (j == 2) { // right
                        nextZeroPos[j] = currZero + 1;
                    }
                    else { // left
                        nextZeroPos[j] = currZero - 1;
                    }
                    stack.add(neigh.get(j));
                }
            }

            for(int j=3; j >= 0; --j) {
                if (nextZeroPos[j] != -1) {
                    if (j == 0) {
                        q.add("down");
                    }
                    else if (j == 1) {
                        q.add("up");
                    }
                    else if (j == 2) {
                        q.add("left");
                    }
                    else {
                        q.add("right");
                    }
                    findPath(stack.pop(), nextZeroPos[j], m, lv, ++currLV, set, minW);
                    --currLV;
                    q.remove(q.size() -1);
                }
            }

        }

    }

    static List<Integer> matrix = new ArrayList<>();
    static List<String> q = new LinkedList<>();
    static List<Integer> goalMatrix = new ArrayList<>();
    static boolean isFound = false;

    static long startTime;
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int N, l, goalZero, m;

        N = scanner.nextInt();
        l = scanner.nextInt();

        if (l == -1) {
            goalZero = N;
            int tmp = 0;
            for (int i = 0; i <= N; ++i) {
                if (i != N) {
                    goalMatrix.add(i + 1);
                }else {
                    goalMatrix.add(0);
                }
            }
        }
        else {
            goalZero = l;
            for (int i = 0; i <= N; ++i) {
                if (i == goalZero) {
                    goalMatrix.add(0);
                }
                else {
                    goalMatrix.add(i + 1);
                }
            }
        }

        m = row(N + 1);

        int tmp, currZero = 0;
        for (int i = 0; i <= N; ++i) {
            tmp = scanner.nextInt();
            if (tmp == 0) {
                currZero = i;
            }
            matrix.add(tmp);
        }

        startTime = System.currentTimeMillis();
        int lv = 2;
        int currLV = 0;
        int weight = calculateWeight(m, matrix);
        while (!isFound) {
            Set<List<Integer>> set = new HashSet<>();
            findPath(matrix, currZero + 1, m, lv, currLV, set, weight);
            ++lv;
        }
    }
}
