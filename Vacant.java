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
public class Vacant extends Piece {

    public Vacant() {
        super(Color.VACANT, 0, 0, 'x', false);
    }
    
    public Vacant(Vacant vacant)
    {
        super(Color.VACANT, 0, 0, 'x', false);
    }
    
    @Override
    public Vacant copy() {
        return new Vacant(this);
    }

    @Override
    public boolean moveTo(int let, int num, Board board) {
        return false;
    }

    @Override
    public boolean canMoveTo(int let, int num, Board board) {
        return false;
    }
    
    @Override
    public boolean isEqualTo(Piece otherPiece) 
    {
        if (otherPiece instanceof Vacant)
        {
            return true;
        }
        return false;
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
