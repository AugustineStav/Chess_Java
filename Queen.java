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
public class Queen extends FarMovingPiece {
    
    public Queen(Color team, int let, int num, boolean isAlive) {
        super(team, let, num, 'Q', isAlive);
    }
    
    public Queen(Queen otherQueen)
    {
        super(otherQueen);
    }
    
    @Override
    public Queen copy() {
        return new Queen(this);
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
                (canMoveRight(getLet(), getNum(), let, num, board)) ||
                (canMoveDiagonalUpRight(getLet(), getNum(), let, num, board)) ||
                (canMoveDiagonalUpLeft(getLet(), getNum(), let, num, board)) ||
                (canMoveDiagonalDownRight(getLet(), getNum(), let, num, board)) ||
                (canMoveDiagonalDownLeft(getLet(), getNum(), let, num, board));
    }
    
    @Override
    public boolean isEqualTo(Piece otherPiece) 
    {
        return (isEqualToBaseValues(otherPiece));
    }
    
    @Override
    public boolean canCastle() {
        return false;
    }

    @Override
    public boolean isVulnerableToEnPassant() {
        return false;
    }
}