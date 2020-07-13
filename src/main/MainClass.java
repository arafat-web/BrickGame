/*
 * Name: Brick Game - Game.
 * Author: Arafat Hossain Ar
 * Date: 1/05/2020
 * All right reserved. Don't copy single line of code without permission.
 */
package main;

import GameSetup.GameSetup;

public class MainClass {

    public static void main(String[] args) {
        GameSetup gamesetup = new GameSetup("Brick Games", 480, 560);
        gamesetup.start();
    }

}
