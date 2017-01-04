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
    int currLet;
    int currNum;
    boolean currAlive;
    
    int prevLet;
    int prevNum;
    boolean prevAlive;
    
    private Color team;
    private char symbol;
    
    
    Piece(Color team, int let, int num, char symbol, boolean isAlive)
    {
        
        //initialize the current values:
        currLet = let;
        currNum = num;
        currAlive= isAlive;
        
        //initialize the next possible values:
        prevLet = let;
        prevNum = num;
        prevAlive= isAlive;
        
        this.team = team;
        this.symbol = symbol;
        
    }
    //return piece that the moving piece affected (vacant spot at destination, 
    //captured piece-including en passant, or rook during castling):
    public abstract Piece getTargetAndMoveTo(int let, int num, Board board); 
    public abstract boolean canMoveTo(int let, int num, Board board);
    public abstract void confirmCurrValues(); //confirm move
    public abstract void undoNextValues(); //undo move
    
    public boolean canSeeEnemyKing(Board board) {
        int let = board.getEnemyKing(this).getLet();
        int num = board.getEnemyKing(this).getNum();
        
        return (
            canMoveTo(let, num, board)
        );
    }
    
    public void undoNextBaseValues()
    {
        currLet = prevLet;
        currNum = prevNum;
        currAlive = prevAlive;
    }
    
    public void confirmCurrBaseValues()
    {
        prevLet = currLet;
        prevNum = currNum;
        prevAlive = currAlive;
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
        this.currAlive = false;
    }
    
    public void setNextAliveTrue()
    {
        this.currAlive = true;
    }
    
    public void setNextCoordinates(int let, int num)
    {
        this.currLet = let;
        this.currNum = num;
    }
    
    public boolean isNotOnTeamOf(Piece piece)
    {
        return (this.team != piece.getTeam());
    }
}
