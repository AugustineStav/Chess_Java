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
public class Board {
    private Piece[][] teams;
    private Piece[][] game;
    private int turnCounter;
    private Vacant vacant;
    
    Board()
    {
        turnCounter = 1;
        teams = new Piece[2][24]; //16 pieces on each team plus 8 possible queens
        game = new Piece[9][9]; //8 by 8 game board, indexing from 1 to 8
        vacant = new Vacant();
        
        for (int teamNum = 0; teamNum < 2; teamNum++)
        {
            for (int pieceNum = 0; pieceNum < 24; pieceNum++)
            {
                teams[teamNum][pieceNum] = vacant;
            }
        }
        
        //instantiate each team's pieces and place them on the board
        makeTeam(Color.BLACK);
	makeTeam(Color.WHITE);
        //makeTeamTest(Color.BLACK);
	//makeTeamTest(Color.WHITE);
        
        updateBoard();
    }
    
    //final so that it cannot be modified in a derived class (helper function used in constructor):
    public final void makeTeam(Color team) 
    {
        int nonPawnsNum, pawnsNum;
	if (team == Color.BLACK)
	{
		nonPawnsNum = 8;
		pawnsNum = 7;
	}
	else
	{
		nonPawnsNum = 1;
		pawnsNum = 2;
	}
        
        //King, Queen, Bishop_left, Bishop_right, Knight_left, Knight_right, Rook_left, Rook_right, Pawn*8(left to right):
        teams[team.getValue()][0] = new King(team, 5, nonPawnsNum, true);
        teams[team.getValue()][1] = new Queen(team, 4, nonPawnsNum, true);
        teams[team.getValue()][2] = new Bishop(team, 3, nonPawnsNum, true);
        teams[team.getValue()][3] = new Bishop(team, 6, nonPawnsNum, true);
        teams[team.getValue()][4] = new Knight(team, 2, nonPawnsNum, true);
        teams[team.getValue()][5] = new Knight(team, 7, nonPawnsNum, true);
        teams[team.getValue()][6] = new Rook(team, 1, nonPawnsNum, true);
        teams[team.getValue()][7] = new Rook(team, 8, nonPawnsNum, true);

        for (int i = 1; i <= 8; i++)
        {
            //add the possible promoted queens to the team:
            Queen queen = new Queen(team, i, pawnsNum, false);
            //add the pawns to the team:
            Pawn pawn = new Pawn(team, i, pawnsNum, true, queen);
            teams[team.getValue()][i + 7] = pawn;
            teams[team.getValue()][i + 15] = queen;
        }
    }
    
    public final void makeTeamTest(Color team) 
    {       
        if (team == Color.WHITE)
        {
            teams[team.getValue()][0] = new King(team, 1, 6, true);
            teams[team.getValue()][1] = new Pawn(team, 3, 6, true, new Queen(team, 1, 1, false));
            teams[team.getValue()][2] = new Pawn(team, 8, 5, true, new Queen(team, 2, 1, false));
            for (int i = 3; i < 24; i++)
            {
                teams[team.getValue()][i] = new Queen(team, i, 1, false);
            }   
        }
        
        if (team == Color.BLACK)
        {
            teams[team.getValue()][0] = new King(team, 6, 5, true);
            teams[team.getValue()][1] = new Queen(team, 3, 7, true);
            teams[team.getValue()][2] = new Pawn(team, 7, 7, true, new Queen(team, 1, 8, false));
            teams[team.getValue()][3] = new Pawn(team, 8, 7, true, new Queen(team, 2, 8, false));
            teams[team.getValue()][4] = new Rook(team, 4, 5, true);
            for (int i = 5; i < 24; i++)
            {
                teams[team.getValue()][i] = new Queen(team, i, 8, false);
            }   
        }
    }
    
    public final void updateBoard()
    {
        for (int let = 1; let <= 8; let++)
        {
            for (int num = 1; num <= 8; num++) 
            {
                game[num][let] = vacant;
            }
        }
        for (int teamNum = 0; teamNum < 2; teamNum++)
        {
            for (int pieceNum = 0; pieceNum < 24; pieceNum++)
            {
                Piece currPiece = teams[teamNum][pieceNum];
                if (currPiece.isAlive())
                {
                    game[currPiece.getNum()][currPiece.getLet()] = currPiece;
                }
            }
        }
    }
    
    public void printBoard()
    {
        int num = 8;
        int num2 = 1;
        System.out.println("White team:                      Black team:");
        System.out.print("  ");
        for (char c = 'A'; c <= 'H'; c++)
        {
            System.out.print(c + "  ");
        }
        System.out.print("         ");
        
        for (char c = 'H'; c >= 'A'; c--)
        {
            System.out.print(c + "  ");
        }
        System.out.println();

        while (num >=1)
        {
            System.out.print(num + " ");
            for (int let = 1; let <= 8; let++)
            {
                if (game[num][let] != null)
                {
                    game[num][let].display();
                }
                else
                {
                    System.out.print("xx ");
                }
            }
            System.out.print(num + "      " + num2 + " ");
            for (int let2 = 8; let2 >= 1; let2--)
            {
                if (game[num2][let2] != null)
                {
                    game[num2][let2].display();
                }
                else
                {
                    System.out.print("xx ");
                }
            }
            System.out.println(num2);
            num--;
            num2++;
        }

        System.out.print("  ");
        for (char c = 'A'; c <= 'H'; c++)
        {
            System.out.print(c + "  ");
        }
        System.out.print("         ");
        
        for (char c = 'H'; c >= 'A'; c--)
        {
            System.out.print(c + "  ");
        }
        System.out.println();
    }
    
    public Piece getPiece(int let, int num)
    {
        return game[num][let];
    }
    
    public int getTurn()
    {
        return turnCounter;
    }
    
    public void incrementTurn()
    {
        turnCounter++;
    }
    
    Piece getEnemyKing(Piece piece)
    {
        return teams[getEnemyTeam(piece.getTeam()).getValue()][0];
    }
    
    Color getEnemyTeam(Color team)
    {
        if (team == Color.BLACK)
        {
            return Color.WHITE;
        }
        if (team == Color.WHITE)
        {
            return Color.BLACK;
        }
        return Color.VACANT;
    }
    
    private boolean isInCheck(Color team)
    {
        for (int i = 0; i < 24; i++)
        {
            if(teams[getEnemyTeam(team).getValue()][i].isAlive() &&
               teams[getEnemyTeam(team).getValue()][i].canSeeEnemyKing(this))
            {
                return true;
            }
        }
        return false;
    }
    
    private void move(Scanner input, Color team)
    {
        boolean moved = false;
	String startCoord, destinationCoord;
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
            if (isInCheck(team)) 
            {
                System.out.println("Your king is in check.");
            }

            System.out.print("Input LETTER NUMBER of piece to move: ");
            startCoord = input.next();
            if(Character.toUpperCase(startCoord.charAt(0)) >= 'A' && Character.toUpperCase(startCoord.charAt(0)) <= 'H' &&
               Character.toUpperCase(startCoord.charAt(1)) >= '1' && Character.toUpperCase(startCoord.charAt(1)) <= '8')
            {
                startLet = Character.toUpperCase(startCoord.charAt(0)) - 'A' + 1;
                startNum = Character.toUpperCase(startCoord.charAt(1)) - '1' + 1;
            }
            else 
            {
                System.out.println("Invalid starting coordinates. They must be on the board.");
                continue;
            }
            
            if (!getPiece(startLet, startNum).isOnTeam(team))
            {
                System.out.println("Invalid starting position. The piece must be on your team.");
                continue;
            }
            
            System.out.print("Input LETTER NUMBER of destination: ");
            destinationCoord = input.next();
            if(Character.toUpperCase(destinationCoord.charAt(0)) >= 'A' && Character.toUpperCase(destinationCoord.charAt(0)) <= 'H' &&
               Character.toUpperCase(destinationCoord.charAt(1)) >= '1' && Character.toUpperCase(destinationCoord.charAt(1)) <= '8')
            {
                let = Character.toUpperCase(destinationCoord.charAt(0)) - 'A' + 1;
                num = Character.toUpperCase(destinationCoord.charAt(1)) - '1' + 1;
            }
            else 
            {
                System.out.println("Invalid destination. It must be on the board.");
                continue;
            }

            Piece mover = getPiece(startLet, startNum);
            Piece target = mover.getTargetAndMoveTo(let, num, this);
            updateBoard(); //update the game[][] board to be a table pointing to the potential next positions

            if (target != null) //the piece can reach the destination, barring exposing its own team's King to check
            {
                if (!isInCheck(team)) //investigate the curr values for check
                {
                    mover.confirmCurrValues(); //set prev member variables equal to curr values
                    target.confirmCurrValues();
                    moved = true; //successfully moved a piece, can now exit the while loop
                }
                else
                {
                    System.out.println("Invalid move. You cannot move your own King into check.");
                    mover.undoNextValues(); //set curr member variables equal to prev values
                    target.undoNextValues(); //if you moved yourself into check, under the target movement while we are sure it is not null
                }
            }
            else
            {
                System.out.println("Invalid move. Your piece cannot reach this destination.");
                mover.undoNextValues();
                //target is null, nothing to undo
            }
            
            updateBoard(); //update the game[][] board to be a table pointing to the original positions
	}
    }

    public void runGame(Scanner input) throws IOException
    {
	boolean gameOver = false; //replace with king checker
	while (!gameOver)
	{ 
            System.out.println("Turn: " + getTurn());

            printBoard();
            move(input, Color.WHITE);
            if (isUnableToMove(Color.BLACK))
            {
                gameOver = true;
                printBoard();
                if (isInCheck(Color.BLACK)) 
                {
                    
                    System.out.println("Checkmate. White team wins.");
                }
                else 
                {
                    System.out.println("Stalemate.");
                }
            }

            if (!gameOver){
                System.out.println("Turn: " + getTurn());
                
                printBoard();
                move(input, Color.BLACK);
                if (isUnableToMove(Color.WHITE))
                {
                    gameOver = true;
                    printBoard();
                    if (isInCheck(Color.WHITE)) 
                    {
                        System.out.println("Checkmate. Black team wins.");
                    }
                    else
                    {
                        System.out.println("Stalemate.");
                    }
                }  
            }
            incrementTurn();
	}
    }

    private boolean isUnableToMove(Color team) {
        Piece mover;
        Piece target;
        
        for (int i = 0; i < 24; i++)
        {
            mover = teams[team.getValue()][i];
            if (mover.isAlive())
            {
                for (int let = 1; let <= 8; let++)
                {
                    for (int num = 1; num <= 8; num++)
                    {
                        target = mover.getTargetAndMoveTo(let, num, this);
                        updateBoard(); //update the game[][] board to be a table pointing to the potential next positions

                        if (target != null) //the piece can reach the destination, barring exposing its own team's King to check
                        {
                            if (!isInCheck(team)) //if there is at least one case where a move removes the king from check
                            {
                                //reset the board to the original state:
                                mover.undoNextValues();
                                target.undoNextValues();
                                updateBoard();
                                
                                return false; //not checkmate
                            }
                            target.undoNextValues();
                        }
                        //reset the board to the original state before trying the next possible destination:
                        mover.undoNextValues();
                        updateBoard();
                    }
                }
            }
        }
        return true;
    }
}
