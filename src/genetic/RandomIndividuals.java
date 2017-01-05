package genetic;

import java.util.ArrayList;
import java.util.Random;

/**
 * Creates a number of random programs
 *
 * @author Lucas
 * @version 0.1
 */
class RandomIndividuals {

    /**
     * List of programs
     */
    private ArrayList<Individual> randomIndividuals;

    RandomIndividuals(int number) {
        randomIndividuals = new ArrayList<>();

        // grow method
        int progress;
        for (int i = 0; i < number; i++) {
            progress = (i*100)/number;
            if (progress % 5 == 0) System.out.print("\rCreating population: " + progress + "% done (" + i + "/" + number + ")");

            Ind ind = new Ind("a");

            randomIndividuals.add(new Individual(ind.getString()));
            //System.out.println(randomIndividuals.get(randomIndividuals.size()-1).getProgramAsString());
        }
        System.out.println("\rCreating population: 100% done.  " + number + " programs");
    }

    ArrayList<Individual> getRandomIndividuals() {
        return randomIndividuals;
    }

    private class Ind {

        /**
         * program in string form, then passed to InputParser
         */
        String progString;

        /**
         * list of vars
         */
        private ArrayList<Character> varList;

        // TODO: maybe do something with maxDepth
        private int maxDepth;

        private Ind(String a) {
            // init var list
            varList = new ArrayList<>();
            // init maxDepth
            this.maxDepth = maxDepth;
            // generate program
            progString = generateProgram();
        }

        public String getString() {
            return progString;
        }

        private String generateProgram() {
            progString = "prog read " + generateVar();
            progString += generateBlock(true);
            progString += " write " + generateOutputVar();
            return progString;
        }

        private String generateVar() {
            char var;
            String allChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            // create new var
            if (varList.isEmpty()) {
                var = allChars.charAt(new Random().nextInt(allChars.length()));
                varList.add(var);
                // TODO: check that not used already
            } else {
                if (new Random().nextBoolean()) {
                    var = allChars.charAt(new Random().nextInt(allChars.length()));
                    varList.add(var);
                    // TODO: check that not used already
                } else {
                    var = varList.get(new Random().nextInt(varList.size()));
                }
            }
            return String.valueOf(var);
        }

        private String generateBlock(boolean firstBlock) {
            int rand;
            String blockString = " { ";
            if (firstBlock) {
                rand = 0;
            } else {
                rand = new Random().nextInt(100);
            }
            if (rand < 40) {
                blockString += generateStatements();
            }
            blockString += "}";
            return blockString;
        }

        private String generateOutputVar() {
            return String.valueOf(varList.get(new Random().nextInt(varList.size())));
        }

        private String generateStatements() {
            String statements = generateCmd();
            int rand = new Random().nextInt(100);
            if (rand < 40) {
                statements += "; " + generateStatements();
            }
            return statements;
        }

        private String generateCmd() {
            String cmd = "";
            int rand = new Random().nextInt(3);
            //rand = 0;
            // assign
            if (rand == 0) {
                cmd += generateVar();
                cmd += " := ";
                cmd += generateExpr();
            }
            // while
            else if (rand == 1) {
                cmd += "while ";
                cmd += generateExpr();
                cmd += generateBlock(false);
            }
            // if
            else if (rand == 2) {
                cmd += "if ";
                cmd += generateExpr();
                cmd += generateBlock(false);
                cmd += generateElseBlock();
            }
            return cmd;
        }

        private String generateElseBlock() {
            String eb = "";
            int rand = new Random().nextInt(100);
            if (rand < 60) {
                eb += " else" + generateBlock(false);
            }
            return eb;
        }

        private String generateExpr() {
            String expr;
            int rand = new Random().nextInt(100);
            //System.out.print("rand=" + rand + ", ");
            if (rand < 25) {
                //System.out.print("nil");
                expr = "nil";
            } else if (rand < 50) {
                //System.out.print("var");
                expr = generateVar();
            } else if (rand < 67){
                expr = "hd " + generateExpr();
                //System.out.print("hd, ");
            } else if (rand < 84) {
                expr = "tl " + generateExpr();
                //System.out.print("tl, ");
            } else {
                expr = "cons " + generateExpr() + " " + generateExpr();
                //System.out.print("cons, ");
            }
            //System.out.println();
            return expr;
        }
    }
}