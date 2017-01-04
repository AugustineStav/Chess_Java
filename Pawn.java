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
public class Pawn extends Piece {
    private int currTurnDoubleJumpedOn;  //set to value that will not match existing turns (so that an unmoved pawn is not vulnerable to en passant)
    private boolean currFirstMove;
    
    private int nextTurnDoubleJumpedOn;
    private boolean nextFirstMove;
    
    Pawn(Color team, int let, int num)
    {
        super(team, let, num, 'P', true);
        currFirstMove = true;
        currTurnDoubleJumpedOn = -1;
        nextFirstMove = true;
        nextTurnDoubleJumpedOn = -1;
    }
    
    @Override
    //only updates NEXT values and does the myKingInCheck function
    //if it passes the myKingInCheck function, then set CURR values to NEXT values
    public Piece canMoveTo(int let, int num, Board board) {        
        if (
            (let == getLet()+1 || let == getLet() - 1) &&
            (
                (
                    isOnTeam(Color.WHITE) &&
                    num == getNum() + 1
                ) ||
                (
                    isOnTeam(Color.BLACK) &&
                    num == getNum() - 1
                )
            )
        )
        {
            //en passant:
            if (board.getPiece(let, num).isVulnerableToEnPassant(board.getTurn()) &&
                isEnemyOf(board.getPiece(let, getNum())))
            {
                nextFirstMove = false;
                board.getPiece(let, getNum()).setNextAliveFalse();
                setNextCoordinates(let, num);
                
                return board.getPiece(let, getNum());
            }
            
            //capture enemy at destination:
            if (isEnemyOf(board.getPiece(let, num)))
            {
                nextFirstMove = false;
                board.getPiece(let, num).setNextAliveFalse();
                setNextCoordinates(let, num);
                
                return board.getPiece(let, num);
            }
        }
        
        //move one forward to empty space:
        if (
                let == getLet() && board.getPiece(let, num).isVacant() &&
                (
                    (isOnTeam(Color.WHITE) && num == getNum() + 1) ||
                    (isOnTeam(Color.BLACK) && num == getNum() - 1)
                )
        )
        {
            nextFirstMove = false;
            setNextCoordinates(let, num);

            return board.getPiece(let, num);
        }
        
        //double jump over a vacant space to a vacant space on the first move:
        if (
                currFirstMove && let == getLet() && board.getPiece(let, num).isVacant() &&
                (
                    (
                    isOnTeam(Color.WHITE) && num == getNum() + 2 && 
                    board.getPiece(let, getNum() + 1).isVacant()
                    ) ||
                    (
                    isOnTeam(Color.BLACK) && num == getNum() - 2 && 
                    board.getPiece(let, getNum() - 1).isVacant()
                    ) 
                )
        )
        {
            nextFirstMove = false;
            nextTurnDoubleJumpedOn = board.getTurn();
            setNextCoordinates(let, num);

            return board.getPiece(let, num);
        }

        return null;
    }
    
    @Override
    public boolean isVulnerableToEnPassant(int turnCounter)
    {
        if (getTeam() == Color.WHITE)
        {
                return (turnCounter == currTurnDoubleJumpedOn);
        }
        else
        {
                return (turnCounter == currTurnDoubleJumpedOn + 1);
        }
    }

    @Override
    public void confirmCurrValues() {
        confirmCurrBaseValues();
        currTurnDoubleJumpedOn = nextTurnDoubleJumpedOn;  
        currFirstMove = nextFirstMove;
    }

    @Override
    public void undoNextValues() {
        undoNextBaseValues();
        nextTurnDoubleJumpedOn = currTurnDoubleJumpedOn;  
        nextFirstMove = currFirstMove;
    }
    
}
