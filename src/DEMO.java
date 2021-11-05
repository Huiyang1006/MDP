import java.io.*;

public class DEMO {

    //private final static String textFile = System.getProperty("user.dir") + "/mdp_input.txt";
    private final static String textFile = System.getProperty("user.dir") + "/src" + "/mdp_input.txt";

    public static void main(String[] args) throws IOException {

//        System.out.println(System.getProperty("user.dir"));
//        System.out.println(textFile);

        /*get the txt file line by line*/
        File file = new File(textFile);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        MDP_VI mdp1 = new MDP_VI();
        MDP_VI mdp2 = new MDP_VI();

        while((line = br.readLine())!= null) {

            /*skip empty line and comment*/
            if (line.length() ==0 || line.charAt(0) == '#') continue;
            else {
                System.out.println(line);
                /*divide the whole line with title and value*/
                String[] seg = line.split(" : ");
                /*receive value from the txt file and assign them to variables*/
                switch (seg[0]) {
                    case "size" -> {
                        mdp1.row = seg[1].charAt(2) - 48;
                        mdp1.column = seg[1].charAt(0) - 48;
                        mdp2.row = seg[1].charAt(2) - 48;
                        mdp2.column = seg[1].charAt(0) - 48;
//                        System.out.println("Row: " + mdp.row + ", Column: " + mdp.column);
                    }

                    case "walls" -> {
                        mdp1.wall_row1 = seg[1].charAt(2) - 48 - 1;
                        mdp1.wall_row2 = seg[1].charAt(8) - 48 - 1;
                        mdp1.wall_column1 = seg[1].charAt(0) - 48 - 1;
                        mdp1.wall_column2 = seg[1].charAt(6) - 48 - 1;
                        mdp2.wall_row1 = seg[1].charAt(2) - 48 - 1;
                        mdp2.wall_row2 = seg[1].charAt(8) - 48 - 1;
                        mdp2.wall_column1 = seg[1].charAt(0) - 48 - 1;
                        mdp2.wall_column2 = seg[1].charAt(6) - 48 - 1;
//                        System.out.println("Wall_Row_1: " + mdp.wall_row1 + ", Wall_Column_1: " + mdp.wall_column1);
//                        System.out.println("Wall_Row_2: " + mdp.wall_row2 + ", Wall_Column_2: " + mdp.wall_column2);
                    }

                    case "terminal_states" -> {
                        String[] seg2 = seg[1].split(" , ");
                        //System.out.println(seg2[0].toString());

                        mdp1.terminal_row1 = seg2[0].charAt(2) - 48 - 1;
                        mdp1.terminal_row2 = seg2[1].charAt(2) - 48 - 1;
                        mdp1.terminal_row3 = seg2[2].charAt(2) - 48 - 1;
                        mdp2.terminal_row1 = seg2[0].charAt(2) - 48 - 1;
                        mdp2.terminal_row2 = seg2[1].charAt(2) - 48 - 1;
                        mdp2.terminal_row3 = seg2[2].charAt(2) - 48 - 1;

                        mdp1.terminal_column1 = seg2[0].charAt(0) - 48 - 1;
                        mdp1.terminal_column2 = seg2[1].charAt(0) - 48 - 1;
                        mdp1.terminal_column3 = seg2[2].charAt(0) - 48 - 1;
                        mdp2.terminal_column1 = seg2[0].charAt(0) - 48 - 1;
                        mdp2.terminal_column2 = seg2[1].charAt(0) - 48 - 1;
                        mdp2.terminal_column3 = seg2[2].charAt(0) - 48 - 1;

                        mdp1.reward1 = seg2[0].charAt(5) - 48;
                        if (seg2[0].charAt(4) == '-') mdp1.reward1 = mdp1.reward1 * -1;
                        mdp2.reward1 = seg2[0].charAt(5) - 48;
                        if (seg2[0].charAt(4) == '-') mdp2.reward1 = mdp2.reward1 * -1;

                        mdp1.reward2 = seg2[1].charAt(5) - 48;
                        if (seg2[1].charAt(4) == '-') mdp1.reward2 = mdp1.reward2 * -1;
                        mdp2.reward2 = seg2[1].charAt(5) - 48;
                        if (seg2[1].charAt(4) == '-') mdp2.reward2 = mdp2.reward2 * -1;

                        mdp1.reward3 = seg2[2].charAt(5) - 48;
                        if (seg2[2].charAt(4) == '-') mdp1.reward3 = mdp1.reward3 * -1;
                        mdp2.reward3 = seg2[2].charAt(5) - 48;
                        if (seg2[2].charAt(4) == '-') mdp2.reward3 = mdp2.reward3 * -1;

//                        System.out.println("x: " + mdp.terminal_column1 + " y: " + mdp.terminal_row1 + " R: " + mdp.reward1);
//                        System.out.println("x: " + mdp.terminal_column2 + " y: " + mdp.terminal_row2 + " R: " + mdp.reward2);
//                        System.out.println("x: " + mdp.terminal_column3 + " y: " + mdp.terminal_row3 + " R: " + mdp.reward3);
                    }

                    case "reward" -> {
                        mdp1.transfer_reward = Double.parseDouble(seg[1].substring(1));
                        if (seg[1].charAt(0) == '-') mdp1.transfer_reward = mdp1.transfer_reward * -1;

                        mdp2.transfer_reward = Double.parseDouble(seg[1].substring(1));
                        if (seg[1].charAt(0) == '-') mdp2.transfer_reward = mdp2.transfer_reward * -1;
//                        System.out.println("reward: " + mdp.transfer_reward);
                    }

                    case "transition_probabilities" -> {
                        String[] seg2 = seg[1].split(" ");
                        mdp1.pRightExecution = Double.parseDouble(seg2[0]);
                        mdp1.pErrorExecution1 = Double.parseDouble(seg2[1]);
                        mdp1.pErrorExecution2 = Double.parseDouble(seg2[2]);
                        mdp1.pErrorExecution3 = Double.parseDouble(seg2[3]);
                        mdp2.pRightExecution = Double.parseDouble(seg2[0]);
                        mdp2.pErrorExecution1 = Double.parseDouble(seg2[1]);
                        mdp2.pErrorExecution2 = Double.parseDouble(seg2[2]);
                        mdp2.pErrorExecution3 = Double.parseDouble(seg2[3]);
                    }

                    case "discount_rate" -> {
                        mdp1.discount_rate = Double.parseDouble(seg[1]);
                        mdp2.discount_rate = Double.parseDouble(seg[1]);
                    }

                    case "epsilon" -> {
                        mdp1.epsilon = Double.parseDouble(seg[1]);
                        mdp2.epsilon = Double.parseDouble(seg[1]);
                    }
                }
            }
        }

        /*initialize the problem of value iteration*/
        mdp1.init();
        /*start value_iteration*/
        mdp1.value_iteration();

        /*initialize the problem of policy iteration*/
        mdp2.init();

    }

}
