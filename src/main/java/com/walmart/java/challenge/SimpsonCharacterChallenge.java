package com.walmart.java.challenge;

public class SimpsonCharacterChallenge {

    public static void main(String[] args) {
        Simpson simpson = new Simpson("Bart", 8);
        System.out.printf("%s - %d\n", simpson.name, simpson.age);
    }

    static abstract class Character {
        String name;
        int age = 35;
        static {
            System.out.printf("D'oh");
        }
        Character(String name, int age) {
            this.name = "Homer";
            this.age = this.age;
            System.out.println("character");
        }
    }
    static class Simpson extends Character{
        Simpson(String name, int age) {
            super(name, age);
            System.out.println("simpson");
        }
    }


}
