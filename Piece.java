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
public abstract class Piece {
    private int[] currNextLet;
    private int[] currNextNum;
    private boolean[] currNextAlive; //is the piece still on the board?
    
    private Color team;
    private char symbol;
    
    
    Piece(Color team, int let, int num, char symbol, boolean isAlive)
    {
        currNextLet = new int[2];
        currNextNum = new int[2];
        currNextAlive = new boolean[2];
        
        //initialize the current values:
        currNextLet[0] = let;
        currNextNum[0] = num;
        currNextAlive[0]= isAlive;
        
        //initialize the next possible values:
        currNextLet[1] = let;
        currNextNum[1] = num;
        currNextAlive[1]= isAlive;
        
        this.team = team;
        this.symbol = symbol;
        
    }
    //return piece that the moving piece affected (vacant spot at destination, 
    //captured piece-including en passant, or rook during castling):
    public abstract Piece canMoveTo(int let, int num, Board board); 
    public abstract void confirmCurrValues(); //confirm move
    public abstract void undoNextValues(); //undo move
    
    public void confirmCurrBaseValues()
    {
        currNextLet[0] = currNextLet[1];
        currNextNum[0] = currNextNum[1];
        currNextAlive[0] = currNextAlive[1];
    }
    
    public void undoNextBaseValues()
    {
        currNextLet[1] = currNextLet[0];
        currNextNum[1] = currNextNum[0];
        currNextAlive[1] = currNextAlive[0];
    }
    
    public void display()
    {
        System.out.print(symbol);
        switch (team)
        {
            case BLACK:
                System.out.print("b ");
                break;
            case WHITE:
                System.out.print("w ");
                break;
            case VACANT:
                System.out.print("x ");
                break;
        }
    }
    
    public boolean isAlive()
    {
        return currAlive;
    }
    
    public int getLet()
    {
        return currLet;
    }
    
    public int getNum()
    {
        return currNum;
    }
    
    public Color getTeam()
    {
        return team;
    }
    
    public boolean isOnTeam(Color team)
    {
        return this.team == team;
    }
    
    public boolean isVulnerableToEnPassant(int turnCounter)
    {
        return false;
    }
    
    public boolean isVacant()
    {
        return (team == Color.VACANT);
    }
    
    public boolean isEnemyOf(Piece piece)
    {
        return (piece.getTeam() != this.team && 
                !piece.isVacant() && !isVacant());
    }
    
    public void setNextAliveFalse()
    {
        this.nextAlive = false;
    }
    
    public void setNextCoordinates(int let, int num)
    {
        this.nextLet = let;
        this.nextNum = num;
    }
    
    public boolean isNotOnTeamOf(Piece piece)
    {
        return (this.team != piece.getTeam());
    }
}
