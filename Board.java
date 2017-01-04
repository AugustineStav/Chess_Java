/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

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
        int turnCounter = 1;
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
        
        teams[Color.BLACK.getValue()][8] = new Pawn(Color.BLACK, 1, 7);
        teams[Color.WHITE.getValue()][8] = new Pawn(Color.WHITE, 1, 2);
        teams[Color.BLACK.getValue()][9] = new Pawn(Color.BLACK, 2, 7);
        teams[Color.WHITE.getValue()][9] = new Pawn(Color.WHITE, 2, 2);
        teams[Color.BLACK.getValue()][10] = new Pawn(Color.BLACK, 3, 7);
        teams[Color.WHITE.getValue()][10] = new Pawn(Color.WHITE, 3, 2);
        teams[Color.BLACK.getValue()][11] = new Pawn(Color.BLACK, 4, 7);
        teams[Color.WHITE.getValue()][11] = new Pawn(Color.WHITE, 4, 2);
        teams[Color.BLACK.getValue()][12] = new Pawn(Color.BLACK, 5, 7);
        teams[Color.WHITE.getValue()][12] = new Pawn(Color.WHITE, 5, 2);
        teams[Color.BLACK.getValue()][13] = new Pawn(Color.BLACK, 6, 7);
        teams[Color.WHITE.getValue()][13] = new Pawn(Color.WHITE, 6, 2);
        teams[Color.BLACK.getValue()][14] = new Pawn(Color.BLACK, 7, 7);
        teams[Color.WHITE.getValue()][14] = new Pawn(Color.WHITE, 7, 2);
        teams[Color.BLACK.getValue()][15] = new Pawn(Color.BLACK, 8, 7);
        teams[Color.WHITE.getValue()][15] = new Pawn(Color.WHITE, 8, 2);
        
        teams[Color.BLACK.getValue()][0] = new King(Color.BLACK, 5, 8);
        teams[Color.WHITE.getValue()][0] = new King(Color.WHITE, 5, 1);
        
        updateBoard();
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
        System.out.print("  ");
        for (char c = 'A'; c <= 'H'; c++)
        {
            System.out.print(c + "  ");
        }
        System.out.println();

        for (int num = 8; num >= 1; num--)
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
            System.out.println(num);
        }

        System.out.print("  ");
        for (char c = 'A'; c <= 'H'; c++)
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
    
    public boolean movedIntoCheck() //TESTING, NEED TO UPDATE WITH LIST THAT CHECKS MOVING NEXT VALUES TO KING'S NEXT
    {
        return false;
    }
}
