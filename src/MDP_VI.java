public class MDP_VI extends MDP{


    /*calculate the value of delta*/
    private double calculate_delta(double eps, double gamma) {
        return eps*(1-gamma)/gamma;
    }

    /*value iteration algorithm*/
    public void value_iteration() {
        System.out.println("\n******Value iteration Algorithm******");

        /*record the number of iteration*/
        int count = 0;

        do {
            count++;
            Uclone(Utility, UPrime);
            delta = 0;

            for (int i = this.row - 1; i >= 0; i--) {
                for (int j = 0; j < column; j++) {
                    if ((i == wall_row1 && j == wall_column1) || (i == wall_row2 && j == wall_column2) ||
                            (i == terminal_row1 && j == terminal_column1) || (i == terminal_row2 && j == terminal_column2) ||
                            (i == terminal_row3 && j == terminal_column3)) continue;
                    else bellman(i, j);
                    double diff = Math.abs(UPrime[i][j] - Utility[i][j]);
                    delta = Math.max(diff, delta);
                }
            }

            /*print the current state*/
            System.out.println("____________" + count + "th iteration____________");
//            for (int i = row - 1; i >= 0; i--) {
//                System.out.println(Arrays.toString(UPrime[i]));
//            }
            for (int i = row - 1; i >= 0; i--) {
                for (int j = 0; j< column; j++) {
                    if ((i == wall_row1 && j == wall_column1) || (i == wall_row2 && j == wall_column2)) {
                        System.out.print("------  ");
                    }
//                    else if ((i == terminal_row1 && j == terminal_column1) || (i == terminal_row2 && j == terminal_column2) ||
//                            (i == terminal_row3 && j == terminal_column3)) {
//                        System.out.print("  " + 0 + "     ");
//                    }
                    //else System.out.printf("%5.3f\t", UPrime[i][j]);
                    else if ((i == terminal_row1 && j == terminal_column1) || (i == terminal_row2 && j == terminal_column2) ||
                            (i == terminal_row3 && j == terminal_column3)) {
                        System.out.printf("%5d\t", (int)UPrime[i][j]);
                    }
                    else System.out.printf("%+5.4f\t", UPrime[i][j]);
                }
                System.out.print("\n");
            }

        } while (delta > calculate_delta(epsilon, discount_rate));

        /*print the final Policy*/
        System.out.println("_____________Final Policy_____________");
        for (int i = row - 1; i >= 0; i--) {
            for (int j = 0; j<column; j++) {
                System.out.print(Policy[i][j] + "  ");
            }
            System.out.print("\n");
            //System.out.println(Arrays.toString(Policy[i]));
        }

    }
}
