package io.github.gungyxy.draft;

import java.util.Scanner;

/**
 * Created by gung on 10/15/21.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Please input 1 for Black Jack, 2 for Trianta Ena");
        Scanner scanner = new Scanner(System.in);
        int inputInteger;
        while (true) {
            if (scanner.hasNextInt()) {
                inputInteger = scanner.nextInt();
                break;
            } else {
                scanner.next();
            }
        }

        if(inputInteger==1){
            new BlackJack().start();
        }else {
            new TriantaEna().start();
        }

    }
}
