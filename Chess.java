/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Character.toUpperCase;

/**
 *
 * @author Gamon
 */
public class Chess {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Board board = new Board();
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        board.printBoard();
    }
    
    
    void move(BufferedReader buffer, Board board, Color team) throws IOException
    {
        boolean moved = false;
	char startChar, destinationChar;
	int startLet, startNum, let, num;

	if (team == Color.WHITE)
	{
		System.out.print("White ");
	} else
	{
		System.out.print("Black ");
	}
	System.out.println("team's turn.");

	while (!moved)
	{
            if (board.movedIntoCheck(team, true)) 
            {
                System.out.println("Your king is in check.");
            }

            System.out.print("Input LETTER NUMBER of piece to move: ");
            startChar = (char)buffer.read();
            startChar = Character.toUpperCase(startChar);
            startNum = Character.getNumericValue(buffer.read());
            startLet = startChar - 'A' + 1;

            if ( startLet < 1 || startLet > 8 || 
                    startNum < 1 || startNum > 8 || 
                    !(board.getPiece(startLet, startNum).isOnTeam(team)) )
            {
                    System.out.println("Invalid starting coordinates. They must be on your team and on the board.");
                    continue;
            }

            System.out.print("Input LETTER NUMBER of destination: ");
            destinationChar = (char)buffer.read();
            destinationChar = Character.toUpperCase(startChar);
            num = Character.getNumericValue(buffer.read());
            let = destinationChar - 'A' + 1;

            if (let < 1 || let > 8 ||
                    num < 1 || num > 8 ||
                    !(board.getPiece(startLet, startNum).isOnTeam(team)))
            {
                    System.out.println("Invalid ending coordinates. They must be on the board.");
                    continue;
            }

            moved = board.movePieceFromTo(team, startLet, startNum, let, num);
            if (!moved)
            {
                    System.out.println("Invalid move.");
            }
	}
    }

    void runGame(BufferedReader buffer, Board board) throws IOException
    {
	boolean checkMate = false; //replace with king checker
	while (!checkMate)
	{ 
		System.out.println("Turn: " + board.getTurn());
		board.printBoard();
		move(buffer, board, Color.WHITE);
		board.printBoard();
		move(buffer, board, Color.BLACK);
                board.incrementTurn();
	}
    }
    
}
