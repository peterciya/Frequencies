package com.example.frequencies;

import java.util.Random;

public class Encrypting {
    public static String encryptingString(String password){
        Random random = new Random();
        int key = random.nextInt(26);
        String stringToEncrypt = "";
        for (int i = 0; i < password.length(); i++){
            if ((int)password.charAt(i) == 32){
                stringToEncrypt += (char)32;
            } else if ((int)password.charAt(i) > 122) {
                int temp = ((int)password.charAt(i) + key) - 122;
                stringToEncrypt += (char)(96 + temp);
            } else if ((int)password.charAt(i) + key > 90 && (int)password.charAt(i) + key < 96){
                int temp = ((int)password.charAt(i) + key) - 90;
                stringToEncrypt += (char)(64 + temp);
            } else {
                stringToEncrypt += (char)((int)password.charAt(i) + key);
            }
        }

        return stringToEncrypt;
    }
}
