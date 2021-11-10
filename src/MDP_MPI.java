public class MDP_MPI extends MDP_PI{

    /*policy evaluation algorithm for Modified Policy Iteration*/
    public void policy_evaluation(int[][] policy) {
        /*original policy evaluate process*/
        for (int i = this.row - 1; i >= 0; i--) {
            for (int j = 0; j < column; j++) {
                if ((i == wall_row1 && j == wall_column1) || (i == wall_row2 && j == wall_column2) ||
                        (i == terminal_row1 && j == terminal_column1) || (i == terminal_row2 && j == terminal_column2) ||
                        (i == terminal_row3 && j == terminal_column3)) continue;
                else Utility[i][j] = calculate_Q(i, j, policy[i][j]);
            }
        }

        /*additional value iterate process. In this case, since the state space is small, I choose to iterate 3 times*/
        int count = 3;
        do {
            count--;
            for (int i = this.row - 1; i >= 0; i--) {
                for (int j = 0; j < column; j++) {
                    if ((i == wall_row1 && j == wall_column1) || (i == wall_row2 && j == wall_column2) ||
                            (i == terminal_row1 && j == terminal_column1) || (i == terminal_row2 && j == terminal_column2) ||
                            (i == terminal_row3 && j == terminal_column3)) continue;
                    else UPrime[i][j] = calculate_Q(i, j, policy[i][j]);
                }
            }
            Uupdate(Utility, UPrime);
        } while (count>0);
    }

}
