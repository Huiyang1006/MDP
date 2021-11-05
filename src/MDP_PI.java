public class MDP_PI extends MDP {

    protected int ActNum;
    protected boolean flag;

    /*rewrite the bellman function for Policy iteration*/
    protected void bellman(int row, int col) {

        double[] Q_value = new double[4];
        int flag_max = 0;

        Q_value[0] = calculate_Q(row, col, 0);
        Q_value[1] = calculate_Q(row, col, 1);
        Q_value[2] = calculate_Q(row, col, 2);
        Q_value[3] = calculate_Q(row, col, 3);

        for (int i = 0; i < Q_value.length; i++) {
            flag_max = Q_value[i] > Q_value[flag_max] ? i : flag_max;
        }

        ActNum = flag_max;
//        UPrime[row][col] = Q_value[flag_max];
//        Policy[row][col] = (flag_max==0 ? 'W' : (flag_max==1 ? 'E' : (flag_max==2 ? 'N': 'S')));
    }

    /*policy evaluation algorithm*/
    public void policy_evaluation(int[][] policy) {

        for (int i = this.row - 1; i >= 0; i--) {
            for (int j = 0; j < column; j++) {
                if ((i == wall_row1 && j == wall_column1) || (i == wall_row2 && j == wall_column2) ||
                        (i == terminal_row1 && j == terminal_column1) || (i == terminal_row2 && j == terminal_column2) ||
                        (i == terminal_row3 && j == terminal_column3)) continue;
                else Utility[i][j] = calculate_Q(i, j, policy[i][j]);

//        calculate_Q()

            }
        }
    }

    /*policy iteration algorithm*/
    public void policy_iteration() {

        System.out.println("\n******Policy iteration Algorithm******");
        /*transfer Policy to number state*/
        int[][] Policy_Num = new int[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if ((i == wall_row1 && j == wall_column1) || (i == wall_row2 && j == wall_column2) ||
                        (i == terminal_row1 && j == terminal_column1) || (i == terminal_row2 && j == terminal_column2) ||
                        (i == terminal_row3 && j == terminal_column3)) continue;
                else switch (Policy[i][j]) {
                    case 'W' -> Policy_Num[i][j] = 0;
                    case 'E' -> Policy_Num[i][j] = 1;
                    case 'N' -> Policy_Num[i][j] = 2;
                    case 'S' -> Policy_Num[i][j] = 3;
                }
            }
        }


        do {

            policy_evaluation(Policy_Num);
            flag = true;

            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    if ((i == wall_row1 && j == wall_column1) || (i == wall_row2 && j == wall_column2) ||
                            (i == terminal_row1 && j == terminal_column1) || (i == terminal_row2 && j == terminal_column2) ||
                            (i == terminal_row3 && j == terminal_column3)) continue;
                    else {
                        bellman(i, j);
                        if (calculate_Q(i, j, ActNum) > calculate_Q(i, j, Policy_Num[i][j])) {
                            Policy_Num[i][j] = ActNum;
                            flag = false;
                        }
                    }
                }
            }

        } while (!flag);

        /*transfer Number state policy to char policy*/
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if ((i == wall_row1 && j == wall_column1) || (i == wall_row2 && j == wall_column2)) {
                    Policy[i][j] = '-';
                } else if ((i == terminal_row1 && j == terminal_column1) || (i == terminal_row2 && j == terminal_column2) ||
                        (i == terminal_row3 && j == terminal_column3)) {
                    Policy[i][j] = 'T';
                } else switch (Policy_Num[i][j]) {
                    case 0 -> Policy[i][j] = 'W';
                    case 1 -> Policy[i][j] = 'E';
                    case 2 -> Policy[i][j] = 'N';
                    case 3 -> Policy[i][j] = 'S';
                }
            }
        }

        /*print the policy*/
        for (int i = row - 1; i >= 0; i--) {
            for (int j = 0; j < column; j++) {
                System.out.print(Policy[i][j] + "  ");
            }
            System.out.print("\n");
            //System.out.println(Arrays.toString(Policy[i]));
        }
    }
}
