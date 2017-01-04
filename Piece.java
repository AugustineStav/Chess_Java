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
    private int currLet;
    private int currNum;
    private boolean currAlive; //is the piece still on the board?
    
    private int nextLet;
    private int nextNum;
    private boolean nextAlive; //is the piece still on the board?
    
    private Color team;
    private char symbol;
    
    
    Piece(Color team, int let, int num, char symbol, boolean isAlive)
    {
        this.currLet = let;
        this.currNum = num;
        this.currAlive = isAlive;
        
        this.nextLet = let;
        this.nextNum = num;
        this.nextAlive = isAlive;
        
        this.team = team;
        this.symbol = symbol;
        
    }
    
    public abstract boolean canMoveTo(int let, int num, Board board);
    public abstract void confirmCurrValues(); //confirm move
    public abstract void undoNextValues(); //undo move
    
    public void confirmCurrBaseValues()
    {
        currLet = nextLet;
        currNum = nextNum;
        currAlive = nextAlive;
    }
    
    public void undoNextBaseValues()
    {
        nextLet = currLet;
        nextNum = currNum;
        nextAlive = currAlive;
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
