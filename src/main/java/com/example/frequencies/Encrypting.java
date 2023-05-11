package com.example.frequencies;

import java.util.Random;

public class Encrypting {
    private static Random random = new Random();
    public static int key = random.nextInt(26);
    public static String encryptingString(String password){

        String encryptedString = "";
        for (int i = 0; i < password.length(); i++){
            if ((int)password.charAt(i) == 32){
                encryptedString += (char)32;
            } else if ((int)password.charAt(i) > 122) {
                int temp = ((int)password.charAt(i) + key) - 122;
                encryptedString += (char)(96 + temp);
            } else if ((int)password.charAt(i) + key > 90 && (int)password.charAt(i) + key < 96){
                int temp = ((int)password.charAt(i) + key) - 90;
                encryptedString += (char)(64 + temp);
            } else {
                encryptedString += (char)((int)password.charAt(i) + key);
            }
        }

        return encryptedString;
    }
    public static String decryptingString(String password){
        String decryptedString = "";
        for (int i = 0; i < password.length(); i++){
            if((int)password.charAt(i) == 32){
                decryptedString += (char)32;
            } else if (((int)password.charAt(i) - key) < 97 && ((int)password.charAt(i) - key) > 90) {
                int temp = ((int)password.charAt(i) - key) + 26;
                decryptedString += (char)temp;
            } else if (((int)password.charAt(i) - key) < 65) {
                int temp = ((int)password.charAt(i) - key) + 26;
                decryptedString += (char)temp;
            } else {
                decryptedString += (char)((int)password.charAt(i) - key);
            }
        }
        return decryptedString;
    }
}
