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
    
    private int prevTurnDoubleJumpedOn;
    private boolean prevFirstMove;
    
    private Queen potentialQueen; //points to the queen that the pawn can turn into if it reaches the opposing side of the board
    
    Pawn(Color team, int let, int num, boolean isAlive, Queen queen)
    {
        super(team, let, num, 'P', isAlive);
        currFirstMove = true;
        currTurnDoubleJumpedOn = -1;
        prevFirstMove = true;
        prevTurnDoubleJumpedOn = -1;
        potentialQueen = queen;
    }
    
    @Override
    //only updates NEXT values and does the myKingInCheck function
    //if it passes the myKingInCheck function, then set CURR values to NEXT values
    public Piece getTargetAndMoveTo(int let, int num, Board board) {        
        //forward, diagonal one space:
        if (
            canMoveTo(let, num, board)
        )
        {
            //en passant:
            if (board.getPiece(let, getNum()).isVulnerableToEnPassant(board.getTurn()) &&
                isEnemyOf(board.getPiece(let, getNum())))
            {
                currFirstMove = false;
                board.getPiece(let, getNum()).setNextAliveFalse();
                setNextCoordinates(let, num);
                return board.getPiece(let, getNum());
            }
            
            //capture enemy at destination:
            if (isEnemyOf(board.getPiece(let, num)))
            {
                currFirstMove = false;
                board.getPiece(let, num).setNextAliveFalse();
                setNextCoordinates(let, num);
                return board.getPiece(let, num);
            }
        }
        
        //move one forward to vacant space:
        if (
                let == getLet() && board.getPiece(let, num).isVacant() &&
                (
                    (isOnTeam(Color.WHITE) && num == getNum() + 1) ||
                    (isOnTeam(Color.BLACK) && num == getNum() - 1)
                )
        )
        {
            //promotion to queen at edge of board:
            if (num == 8 || num == 1)
            {
                setNextAliveFalse(); //this pawn is no long active
                potentialQueen.setNextAliveTrue(); //a queen takes its place
                potentialQueen.setNextCoordinates(let, num);
                return potentialQueen;
            }
            
            //move to vacant space in middle of board:
            currFirstMove = false;
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
            currFirstMove = false;
            currTurnDoubleJumpedOn = board.getTurn();
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
        prevTurnDoubleJumpedOn = currTurnDoubleJumpedOn;  
        prevFirstMove = currFirstMove;
    }

    @Override
    public void undoNextValues() {
        undoNextBaseValues();
        currTurnDoubleJumpedOn = prevTurnDoubleJumpedOn;  
        currFirstMove = prevFirstMove;
    }

    @Override
    public boolean canMoveTo(int let, int num, Board board) {
        return (let == getLet()+1 || let == getLet() - 1) &&
                (
                (
                isOnTeam(Color.WHITE) &&
                num == getNum() + 1
                ) ||
                (
                isOnTeam(Color.BLACK) &&
                num == getNum() - 1
                )
                );
    }
    
}
