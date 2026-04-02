package com.prince.finance.finsecure.security;

public class GenerateSecret {

    public static void main(String[] args) {
        byte[] key = new byte[32];
        new java.security.SecureRandom().nextBytes(key);
        System.out.println(java.util.Base64.getEncoder().encodeToString(key));
    }
}
