//import java.util.Scanner;
//import java.util.concurrent.ThreadLocalRandom;
//
///**
// * Created by gung on 10/15/21.
// */
//public class Utility {
//
//    public static final String ANSI_RESET = "\u001B[0m";
//    public static final String ANSI_BLACK = "\u001B[30m";
//    public static final String ANSI_RED = "\u001B[31m";
//    public static final String ANSI_GREEN = "\u001B[32m";
//    public static final String ANSI_YELLOW = "\u001B[33m";
//    public static final String ANSI_BLUE = "\u001B[34m";
//    public static final String ANSI_PURPLE = "\u001B[35m";
//    public static final String ANSI_CYAN = "\u001B[36m";
//    public static final String ANSI_WHITE = "\u001B[37m";
//
//
//    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
//    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
//    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
//    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
//    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
//    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
//    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
//    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
//
//    private static final String INVALID_INPUT=ANSI_YELLOW_BACKGROUND+"WARNING"+" "+"Invalid Input!"+ Utility.ANSI_RESET;
//
//    public static int getIntegerFromUser(String prompt, int lowerBound, int upperBound){
//        Scanner scanner = new Scanner(System.in);
//        while (true) {
//            promptUser(prompt+" ["+lowerBound+", "+upperBound+"] ");
//            if (scanner.hasNextInt()) {
//
//                int inputInteger = scanner.nextInt();
//                if(upperBound>=inputInteger&&lowerBound<=inputInteger){
//                    return inputInteger;
//                }else{
//                    promptUser(INVALID_INPUT);
//                }
//            } else {
//                promptUser(INVALID_INPUT);
//                scanner.next();
//            }
//        }
//    }
//
//    public static void promptUser(String prompt){
//        System.out.println(prompt);
//    }
//    public static int randomInteger(int lowerBound, int upperBound){
//        return ThreadLocalRandom.current().nextInt(lowerBound, upperBound+1);
//    }
//}
