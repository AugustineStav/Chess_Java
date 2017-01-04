/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.util.Arrays;
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
    private int whiteRepetitions; //number of times white team has repeated the current board position
    private int blackRepetitions;
    
    Board()
    {
        this.whiteRepetitions = 0;
        this.blackRepetitions = 0;
        this.turnCounter = 1;
        initializeEmptyBoardAndTeam();
        
        //instantiate each team's pieces:
        makeTeam(Color.BLACK);
	makeTeam(Color.WHITE);
        
        //place pieces on the board (lookup matrix):
        updateBoard();
    }

    Board(Board otherBoard)
    {
        this.whiteRepetitions = otherBoard.whiteRepetitions;
        this.blackRepetitions = otherBoard.whiteRepetitions;
        this.turnCounter = otherBoard.turnCounter;
        initializeEmptyBoardAndTeam();
        
        //copy each piece on the otherBoard to teams;
        for (int i = 0; i < 48; i++)
        {
            this.teams[Color.BLACK.getValue()][i] = otherBoard.teams[Color.BLACK.getValue()][i].copy();
            this.teams[Color.WHITE.getValue()][i] = otherBoard.teams[Color.WHITE.getValue()][i].copy();
        }
        
        updateBoard();
    }
    
    private void initializeEmptyBoardAndTeam() {
        this.teams = new Piece[2][48]; //16 pieces on each team plus 32 possible pawn promotions
        this.game = new Piece[9][9]; //8 by 8 game board, indexing from 1 to 8
        this.vacant = new Vacant();
        
        for (int teamNum = 0; teamNum < 2; teamNum++)
        {
            for (int pieceNum = 0; pieceNum < 48; pieceNum++)
            {
                this.teams[teamNum][pieceNum] = this.vacant;
            }
        }
    }
    
    public Board copy()
    {
        return new Board(this);
    }
    
    public boolean isEqualTo(Board otherBoard)
    {
        for (int let = 1; let <= 8; let++)
        {
            for (int num = 1; num <= 8; num++)
            {
                if(!this.game[num][let].isEqualTo(otherBoard.game[num][let]))
                {
                    return false;
                }
            }
        }
        return true;
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
            //add the pawns and possible promotions to the team:
            teams[team.getValue()][i + 7] = new Pawn(team, i, pawnsNum, true);
            teams[team.getValue()][i + 15] = new Queen(team, i, pawnsNum, false);
            teams[team.getValue()][i + 23] = new Knight(team, i, pawnsNum, false);
            teams[team.getValue()][i + 31] = new Rook(team, i, pawnsNum, false);
            teams[team.getValue()][i + 39] = new Bishop(team, i, pawnsNum, false);
        }
    }
    
    public final void makeTeamTest(Color team) 
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
            //add the pawns and possible promotions to the team:
            teams[team.getValue()][i + 7] = new Pawn(team, i, pawnsNum, true);
            teams[team.getValue()][i + 15] = new Queen(team, i, pawnsNum, false);
            teams[team.getValue()][i + 23] = new Knight(team, i, pawnsNum, false);
            teams[team.getValue()][i + 31] = new Rook(team, i, pawnsNum, false);
            teams[team.getValue()][i + 39] = new Bishop(team, i, pawnsNum, false);
        }
        
        for (int i = 1; i <= 8; i++)
        {
            if (i==1)
            {
                teams[team.getValue()][i + 7] = new Pawn(team, 1, 6, true);
                teams[team.getValue()][i + 15] = new Queen(team, i, pawnsNum, false);
                teams[team.getValue()][i + 23] = new Knight(team, i, pawnsNum, false);
                teams[team.getValue()][i + 31] = new Rook(team, i, pawnsNum, false);;
                teams[team.getValue()][i + 39] = new Bishop(team, i, pawnsNum, false);
            }
            else
            {
                teams[team.getValue()][i + 7] = new Pawn(team, i, pawnsNum, true);
                teams[team.getValue()][i + 15] = new Queen(team, i, pawnsNum, false);
                teams[team.getValue()][i + 23] = new Knight(team, i, pawnsNum, false);
                teams[team.getValue()][i + 31] = new Rook(team, i, pawnsNum, false);
                teams[team.getValue()][i + 39] = new Bishop(team, i, pawnsNum, false);
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
            for (int pieceNum = 0; pieceNum < 48; pieceNum++)
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
    
    public Piece getEnemyKing(Piece piece)
    {
        return teams[getEnemyTeam(piece.getTeam()).getValue()][0];
    }
    
    public Color getEnemyTeam(Color team)
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
    
    public boolean isInCheck(Color team)
    {
        for (int i = 0; i < 48; i++)
        {
            if(teams[getEnemyTeam(team).getValue()][i].isAlive() &&
               teams[getEnemyTeam(team).getValue()][i].canSeeEnemyKing(this))
            {
                return true;
            }
        }
        return false;
    }
    
    private boolean isDraw()
    {
        //stalemate
        //repitition
        //impossibility of checkmate
        //offer a draw?
        
        return false;
    }
    
    public boolean moveFromTo(int startLet, int startNum, int let, int num, Color team, Board prevBoard)
    {
        boolean successfullyMoved;
        successfullyMoved = getPiece(startLet, startNum).moveTo(let, num, this);
        updateEnemyPawnsEnPassant(team);
        incrementRepititions(team, prevBoard);
        updateBoard();
        return successfullyMoved;
    }
    
    private void updateEnemyPawnsEnPassant(Color team)
    {
        for (int i = 0; i < 48; i++)
        {
            if(teams[getEnemyTeam(team).getValue()][i].isVulnerableToEnPassant())
            {
                teams[getEnemyTeam(team).getValue()][i].setVulnerableToEnPassantFalse();
            }
        }
    }
    
    public void incrementRepititions(Color team, Board prevBoard)
    {
        if (team == Color.WHITE && isEqualTo(prevBoard) && turnCounter > 1)
        {
            whiteRepetitions++;
        }
        else if (team == Color.BLACK && isEqualTo(prevBoard) && turnCounter > 1)
        {
            blackRepetitions++;
        }
        else
        {
            whiteRepetitions = 0;
            blackRepetitions = 0;
        }
    }
    
    public boolean threeFoldRepetitions()
    {
        if ((whiteRepetitions >= 3 && blackRepetitions >=2)
                || (blackRepetitions >= 3 && whiteRepetitions >=2))
        {
            return true;
        }
        return false;
    }
    
    public Piece getPieceOnTeamAt(Color team, int i)
    {
        return teams[team.getValue()][i];
    }
    
    public Piece getPawnsPromotion(Pawn pawn, char choiceChar)
    {
        //get index of pawn in teams[pawn.getTeam().getValue()][i]
        int i = Arrays.asList(teams[pawn.getTeam().getValue()]).indexOf(pawn);
        int num = 0;
        
        switch (choiceChar)
        {
            case 'Q':
                num = 8;
                break;
            case 'K':
                num = 16;
                break;
            case 'R':
                num = 24;
                break;
            case 'B':
                num = 32;
                break;
        }
        
        return teams[pawn.getTeam().getValue()][i+num];
    }
    
    public boolean insufficientMaterialToCheckmate() //TO BE IMPLEMENTED
    {
        return false;
    }
    
}
