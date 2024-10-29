package com.KJS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        BufferedReader keybaordReader = null;
        try {
            System.out.printf("Hello and welcome!");

            keybaordReader = new BufferedReader(new InputStreamReader(System.in));
            while(true) {
                String message = keybaordReader.readLine();
                System.out.printf("입력된 문자열 : [%s]\n", message);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (keybaordReader != null) {
                    keybaordReader.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}