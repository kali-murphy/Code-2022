public class Solution {

    private static boolean checkApplesColCut(String[] pizza, int sR, int sC, int eR, int eC, int col) {

        // Checking the top part
        boolean leftCheck = false;
        for (String p : pizza) {
            for (int c = sC; c < col; c++) {
                if (p.charAt(c) == 'A') {
                    leftCheck = true;
                    break;
                }
            }
            if (leftCheck) {
                break;
            }
        }

        // Checking the bottom part
        boolean rightCheck = false;
        for (String p : pizza) {
            for (int c = col; c <= eC; c++) {
                if (p.charAt(c) == 'A') {
                    rightCheck = true;
                    break;
                }
            }
            if (rightCheck) {
                break;
            }
        }

        return leftCheck && rightCheck;
    }

    private static boolean checkApplesRowCut(String[] pizza, int sR, int sC, int eR, int eC, int row) {

        // Checking the top part
        boolean topCheck = false;
        for (int r = sR; r < row; r++) {
            for (int i = r; i <= eR; i++) {
                if (pizza[r].charAt(i) == 'A') {
                    topCheck = true;
                    break;
                }
            }
        }

        // Checking the bottom part
        boolean bottomCheck = false;
        for (int r = row; r <= eR; r++) {
            for (int i = r; i <= eR; i++) {
                if (pizza[r].charAt(i) == 'A') {
                    bottomCheck = true;
                    break;
                }
            }
        }

        return topCheck && bottomCheck;
    }

    private static int helper(String[] pizza, int k, int sR, int sC, int eR, int eC) {

        if (k == 1) {
            return 1; // it's one way to cut a pizza into k pieces
        }

        int totalWays = 0;
        boolean flag = false;

        // Cutting horizontally
        // sR = 0, eR = 2
        // row = 1..2
        for (int row = sR + 1; row <= eR; row++) {
            if (checkApplesRowCut(pizza, sR, sC, eR, eC, row)) {
                flag = true;
                if (flag) {
                    sR = 1;
                }
                totalWays += helper(pizza, k - 1, row, sC, eR, eC);
            }
        }

        // Cutting vertically
        for (int col = sC + 1; col <= eC; col++) {
            if (checkApplesColCut(pizza, sR, sC, eR, eC, col)) {
                totalWays += helper(pizza, k - 1, sR, col, eR, eC);
            }
        }

        return totalWays % (int)(10E9 + 7);
    }

    public static int ways(String[] pizza, int k) {

        // ["A..","AAA","..."]
        /* charAt()
        A . .
        A A A
        . . .
        */
        return helper(pizza, k, 0, 0, pizza.length - 1, pizza[0].length() - 1);
    }

    public static void main(String[] args) {
        System.out.println(ways(new String[]{"A..","AAA","..."}, 3));
    }
}
