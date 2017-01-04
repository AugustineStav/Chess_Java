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
        teams[team.getValue()][0] = new King(team, 5, nonPawnsNum);
        teams[team.getValue()][1] = new Queen(team, 4, nonPawnsNum);
        teams[team.getValue()][2] = new Bishop(team, 3, nonPawnsNum);
        teams[team.getValue()][3] = new Bishop(team, 6, nonPawnsNum);
        teams[team.getValue()][4] = new Knight(team, 2, nonPawnsNum);
        teams[team.getValue()][5] = new Knight(team, 7, nonPawnsNum);
        teams[team.getValue()][6] = new Rook(team, 1, nonPawnsNum);
        teams[team.getValue()][7] = new Rook(team, 8, nonPawnsNum);
        
        teams[team.getValue()][8] = new Pawn(team, 1, pawnsNum);
        teams[team.getValue()][9] = new Pawn(team, 2, pawnsNum);
        teams[team.getValue()][10] = new Pawn(team, 3, pawnsNum);
        teams[team.getValue()][11] = new Pawn(team, 4, pawnsNum);
        teams[team.getValue()][12] = new Pawn(team, 5, pawnsNum);
        teams[team.getValue()][13] = new Pawn(team, 6, pawnsNum);
        teams[team.getValue()][14] = new Pawn(team, 7, pawnsNum);
        teams[team.getValue()][15] = new Pawn(team, 8, pawnsNum);
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
    
    //current boolean flag means "check the CURRENT" positions
    //otherwise, check the NEXT (potential) positions
    public boolean movedIntoCheck(Color team, boolean current) //TESTING, NEED TO UPDATE WITH LIST THAT CHECKS MOVING NEXT VALUES TO KING'S NEXT
    {
        return false;
    }
    
    boolean movePieceFromTo(Color team, int startLet, int startNum, int let, int num) {
        Piece mover = getPiece(startLet, startNum);
        Piece target = getPiece(startLet, startNum).canMoveTo(let, num, this);
        
        if (target != null)
        {
            if (!movedIntoCheck(team, false)) //investigate the NEXT values for check
            {
                mover.confirmCurrValues();
                target.confirmCurrValues();
                return true;
            }
            target.undoNextValues();
        }
        mover.undoNextValues();
        return false;
    }
    
    public void incrementTurn()
    {
        turnCounter++;
    }
}
