import java.util.Arrays;

public class MDP {

    /*information of the environment*/
    public int row;
    public int column;
    public double transfer_reward;
    public double discount_rate;
    public double epsilon;
    protected double delta;
    protected double[][] Reward;
    protected char[][] Policy;
    protected double[][] Utility;
    protected double[][] UPrime;

    /*information of actions*/
    public String[] actions = {"left", "right", "up", "down"};
    public double pRightExecution;
    public double pErrorExecution1;
    public double pErrorExecution2;
    public double pErrorExecution3;

    /*information of walls*/
    public int wall_row1;
    public int wall_row2;
    public int wall_column1;
    public int wall_column2;

    /*information of termination*/
    public int terminal_row1, terminal_row2, terminal_row3;
    public int terminal_column1, terminal_column2, terminal_column3;
    public int reward1, reward2, reward3;

    /*calculate the value of delta*/
//    private double calculate_delta(double eps, double gamma) {
//        return eps*(1-gamma)/gamma;
//    }

    /*initialize the mdp problem*/
    public void init() {
        /*initial with immediate reward*/
        Reward = new double[row][column];
        for (int i=0; i < row; i++) {
            for (int j=0; j<column; j++) {
                Reward[i][j] = transfer_reward;
            }
        }
        /*initial Utility and UPrime*/
        Utility = new double[row][column];
        for (int i=0; i<row; i++) {
            for (int j=0; j<column; j++) {
                Utility[i][j] = 0;
            }
        }
        UPrime = new double[row][column];
        for (int i=0; i<row; i++) {
            for (int j=0; j<column; j++) {
                UPrime[i][j] = 0;
            }
        }
        /*mark the terminal point*/
        Reward[terminal_row1][terminal_column1] = reward1;
        Reward[terminal_row2][terminal_column2] = reward2;
        Reward[terminal_row3][terminal_column3] = reward3;
        Reward[wall_row1][wall_column1] = 0;
        Reward[wall_row2][wall_column2] = 0;

        Policy = new char[row][column];
        Policy[terminal_row1][terminal_column1] = 'T';
        Policy[terminal_row2][terminal_column2] = 'T';
        Policy[terminal_row3][terminal_column3] = 'T';
        Policy[wall_row1][wall_column1] = '-';
        Policy[wall_row2][wall_column2] = '-';

        /*test*/
//        for (int i = row - 1; i >= 0; i--) {
//            System.out.println(Arrays.toString(Reward[i]));
//        }
//        for (int i = row - 1; i >= 0; i--) {
//            System.out.println(Arrays.toString(Policy[i]));
//        }
    }

    /*Action left*/
    private double move_L(int row, int col) {
        if (col == 0 || (row == wall_row1 && col == wall_column1 + 1) || (row == wall_row2 && col == wall_column2 + 1))
            return discount_rate * Utility[row][col] + Reward[row][col];
        else return discount_rate * Utility[row][col-1] + Reward[row][col-1];
    }

    /*Action right*/
    private double move_R(int row, int col) {
        if (col == this.column-1 || (row == wall_row1 && col == wall_column1 - 1) || (row == wall_row2 && col == wall_column2 - 1))
            return discount_rate * Utility[row][col] + Reward[row][col];
        else return discount_rate * Utility[row][col+1] + Reward[row][col+1];
    }

    /*Action up*/
    private double move_U(int row, int col) {
        if (row == this.row-1 || (row == wall_row1 - 1 && col == wall_column1) || (row == wall_row2 - 1 && col == wall_column2))
            return discount_rate * Utility[row][col] + Reward[row][col];
        else return discount_rate * Utility[row+1][col] + Reward[row+1][col];
    }

    /*Action down*/
    private double move_D(int row, int col) {
        if (row == 0 || (row == wall_row1 + 1 && col == wall_column1) || (row == wall_row2 + 1 && col == wall_column2))
            return discount_rate * Utility[row][col] + Reward[row][col];
        else return discount_rate * Utility[row-1][col] + Reward[row-1][col];
    }

    /*calculate the Q value*/
    private double calculate_Q (int r, int c, int flag) {
        switch (flag) {
            case 0 -> {
                return pRightExecution * move_L(r, c) + pErrorExecution3 * move_R(r, c) +
                        pErrorExecution2 * move_U(r, c) + pErrorExecution1 * move_D(r, c);
            }

            case 1 -> {
                return pErrorExecution3 * move_L(r, c) + pRightExecution * move_R(r, c) +
                        pErrorExecution1 * move_U(r, c) + pErrorExecution2 * move_D(r, c);
            }

            case 2 -> {
                return pErrorExecution1 * move_L(r, c) + pErrorExecution2 * move_R(r, c) +
                        pRightExecution * move_U(r, c) + pErrorExecution3 * move_D(r, c);
            }

            case 3 -> {
                return pErrorExecution2 * move_L(r, c) + pErrorExecution1 * move_R(r, c) +
                        pErrorExecution3 * move_U(r, c) + pRightExecution * move_D(r, c);
            }

//            case 4 -> {
//
//            }

            default -> {
                return 0;
            }
        }
    }

    /*Bellman equation update*/
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

        UPrime[row][col] = Q_value[flag_max];
        Policy[row][col] = (flag_max==0 ? 'W' : (flag_max==1 ? 'E' : (flag_max==2 ? 'N': 'S')));

    }

}
