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
    int let;
    int num;
    boolean alive;
    
    private final Color team;
    private final char symbol;
    
    
    Piece(Color team, int let, int num, char symbol, boolean alive)
    {
        //initialize the current values:
        this.let = let;
        this.num = num;
        this.alive = alive;
        
        this.team = team;
        this.symbol = symbol; 
    }
    
    // copy constructor
    Piece (Piece otherPiece)
    {
        this.let = otherPiece.let;
        this.num = otherPiece.num;
        this.alive = otherPiece.alive;
        
        this.team = otherPiece.team;
        this.symbol = otherPiece.symbol; 
    }
    
    //return piece that the moving piece affected (vacant spot at destination, 
    //captured piece-including en passant, or rook during castling):
    public abstract boolean moveTo(int let, int num, Board board); 
    public abstract boolean canMoveTo(int let, int num, Board board);
    public abstract Piece copy();
    public abstract boolean isVulnerableToEnPassant();
    public abstract boolean canCastle();
    
    public abstract boolean isEqualTo(Piece otherPiece);
    
    public boolean isEqualToBaseValues(Piece otherPiece)
    {
        return (this.let == otherPiece.let &&
                this.num == otherPiece.num &&
                this.alive == otherPiece.alive &&
                this.team == otherPiece.team &&
                this.symbol == otherPiece.symbol);
    }
    
    public boolean canSeeEnemyKing(Board board) {
        int let = board.getEnemyKing(this).getLet();
        int num = board.getEnemyKing(this).getNum();
        
        return (
            canMoveTo(let, num, board)
        );
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
        return alive;
    }
    
    public int getLet()
    {
        return let;
    }
    
    public int getNum()
    {
        return num;
    }
    
    public Color getTeam()
    {
        return team;
    }
    
    public boolean isOnTeam(Color team)
    {
        return this.team == team;
    }
    
    public void setVulnerableToEnPassantFalse()
    {
        //intentionally blank
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
        this.alive = false;
    }
    
    public void setNextAliveTrue()
    {
        this.alive = true;
    }
    
    public void setNextCoordinates(int let, int num)
    {
        this.let = let;
        this.num = num;
    }
    
    public boolean isNotOnTeamOf(Piece piece)
    {
        return (this.team != piece.getTeam());
    }
}
