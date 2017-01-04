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
public class Rook extends FarMovingPiece {
    private boolean firstMove;
    
    public Rook(Color team, int let, int num, boolean isAlive) {
        super(team, let, num, 'R', isAlive);
        firstMove = true;
    }
    
    public Rook(Rook otherRook)
    {
        super(otherRook);
        this.firstMove = otherRook.firstMove;
    }
    
    @Override
    public Rook copy() {
        return new Rook(this);
    }

    @Override
    public boolean moveTo(int let, int num, Board board) {
        if ((getLet() == let && getNum() == num))
        {
            return false;
        }

        if (
            canMoveTo(let, num, board)
            )
        {
            firstMove = false;
            board.getPiece(let, num).setNextAliveFalse();
            setNextCoordinates(let, num);
            return true;
        }

        return false;
    }

    @Override
    public boolean canMoveTo(int let, int num, Board board) {
        return (canMoveUp(getLet(), getNum(), let, num, board)) ||
                (canMoveDown(getLet(), getNum(), let, num, board)) ||
                (canMoveLeft(getLet(), getNum(), let, num, board)) ||
                (canMoveRight(getLet(), getNum(), let, num, board));
    }
    
    @Override
    public boolean isEqualTo(Piece otherPiece) {
        if (otherPiece instanceof Rook)
        {
            return (this.firstMove == ((Rook)otherPiece).firstMove &&
                    isEqualToBaseValues(otherPiece));
        }
        
        return false;
    }

    @Override
    public boolean canCastle() {
        return firstMove;
    }

    @Override
    public boolean isVulnerableToEnPassant() {
        return false;
    }
    
}
