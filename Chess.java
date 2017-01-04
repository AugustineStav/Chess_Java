/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Gamon
 */
public class Chess {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        RunGame menu = new RunGame();
        Scanner input = new Scanner(System.in);
        menu.selectPlayer(input);
    }
    
}
