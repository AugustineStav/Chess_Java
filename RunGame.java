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
public class RunGame {
    Board currBoard;
    Board prevBlackBoard;
    Board prevWhiteBoard; 
    
    //fix castling
    
    RunGame()
    {
        currBoard = new Board();
        prevBlackBoard = currBoard.copy();
        prevWhiteBoard = currBoard.copy();
    }

    private void move(Scanner input, Color team)
    {
        Board backupBoard; //copy of the current board
        Board prevBoard; //state of board at the previous turn of the moving team
        boolean moved = false;
        String startCoord, destinationCoord;
        int startLet, startNum, let, num;

        if (team == Color.WHITE)
        {
                System.out.print("White ");
                backupBoard = prevBlackBoard;
                prevBoard = prevWhiteBoard;
        } else
        {
                System.out.print("Black ");
                backupBoard = prevWhiteBoard;
                prevBoard = prevBlackBoard;
        }
        System.out.println("team's turn.");

        while (!moved)
        {
            if (currBoard.isInCheck(team)) 
            {
                System.out.println("Your king is in check.");
            }

            System.out.print("Input LETTER NUMBER of piece to move: ");
            startCoord = input.next();
            if(startCoord.length() == 2 &&
                    Character.toUpperCase(startCoord.charAt(0)) >= 'A' && Character.toUpperCase(startCoord.charAt(0)) <= 'H' &&
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

            if (!currBoard.getPiece(startLet, startNum).isOnTeam(team))
            {
                System.out.println("Invalid starting position. The piece must be on your team.");
                continue;
            }

            System.out.print("Input LETTER NUMBER of destination: ");
            destinationCoord = input.next();
            if(destinationCoord.length() == 2 &&
                    Character.toUpperCase(destinationCoord.charAt(0)) >= 'A' && Character.toUpperCase(destinationCoord.charAt(0)) <= 'H' &&
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

            moved = currBoard.moveFromTo(startLet, startNum, let, num, team, prevBoard);

            if (moved) //the piece can reach the destination, barring exposing its own team's King to check
            {
                if (currBoard.isInCheck(team)) //if player exposed own king to check
                {
                    System.out.println("Invalid move. You cannot move your own King into check.");
                    currBoard = backupBoard.copy();
                    moved = false;
                }
                else //successful move
                {
                    //the player moved and did not expose own king to check
                    //the previous board is updated to the current state:
                    if (team == Color.WHITE)
                    {
                        prevWhiteBoard = currBoard.copy(); 
                    }
                    else
                    {
                        prevBlackBoard = currBoard.copy();
                    }
                }
            }
            else
            {
                System.out.println("Invalid move. Your piece cannot reach this destination.");
                currBoard = backupBoard.copy();
                moved = false;
            }
        }
    }

    public void selectPlayer(Scanner input) throws IOException
    {
        boolean gameOver = false; //replace with king checker
        while (!gameOver)
        { 
            System.out.println("Turn: " + currBoard.getTurn());

            gameOver = playerMove(input, gameOver, Color.WHITE);

            if (gameOver)
            {
                continue;
            }
            
            gameOver = playerMove(input, gameOver, Color.BLACK);
            currBoard.incrementTurn();
        }
    }

    private boolean playerMove(Scanner input, boolean gameOver, Color team) {
        currBoard.printBoard();
        move(input, team);
        String teamName;
        if (team == Color.WHITE)
        {
            teamName = "White";
        }
        else
        {
            teamName = "Black";
        }
        if (canOnlyMoveIntoCheck(currBoard.getEnemyTeam(team)))
        {
            gameOver = true;
            currBoard.printBoard();
            if (currBoard.isInCheck(currBoard.getEnemyTeam(team)))
            {
                
                System.out.println("Checkmate. " + teamName + " team wins.");
            }
            else
            {
                System.out.println("Stalemate.");
            }
        }
        if (currBoard.threeFoldRepetitions())
        {
            String yesNo;
            char yN;
            boolean choosing = true;
            while (choosing)
            {
                System.out.println("Three repetitions. Does " + teamName + " claim a draw? YES or NO.");
                yesNo = input.next();
                if(yesNo.length() >= 1 &&
                        Character.toUpperCase(yesNo.charAt(0)) == 'Y' ||
                        Character.toUpperCase(yesNo.charAt(0)) == 'N')
                {
                    yN = Character.toUpperCase(yesNo.charAt(0));
                    choosing = false;
                }
                else
                {
                    System.out.println("Invalid choice.");
                }
            }
        }
        return gameOver;
    }

    private boolean canOnlyMoveIntoCheck(Color team) {
        Piece mover;
        Piece target;
        Board backupBoard; //copy of the current board
        Board prevBoard; //state of board at the previous turn of the moving team
        boolean moved = false;

        if (team == Color.WHITE)
        {
                backupBoard = prevBlackBoard;
                prevBoard = prevWhiteBoard;
        } else
        {
                backupBoard = prevWhiteBoard;
                prevBoard = prevBlackBoard;
        }

        for (int i = 0; i < 24; i++)
        {
            mover = currBoard.getPieceOnTeamAt(team, i);
            if (mover.isAlive())
            {
                for (int let = 1; let <= 8; let++)
                {
                    for (int num = 1; num <= 8; num++)
                    {
                        moved = currBoard.moveFromTo(mover.getLet(), mover.getNum(), let, num, team, prevBoard);
                        if (moved) //the piece can reach the destination, barring exposing its own team's King to check
                        {
                            if (!currBoard.isInCheck(team)) //if there is at least one case where a move removes the king from check
                            {
                                //reset the board to the original state:
                                currBoard = backupBoard.copy();
                                return false; //not checkmate
                            }
                        }
                        //reset the board to the original state before trying the next possible destination:
                        currBoard = backupBoard.copy();
                    }
                }
            }
        }
        return true;
    }
}