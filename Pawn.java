/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.util.Scanner;

/**
 *
 * @author Gamon
 */
public class Pawn extends Piece {
    private boolean vulnerableToEnPasant;
    private boolean firstMove;
    
    Pawn(Color team, int let, int num, boolean isAlive)
    {
        super(team, let, num, 'P', isAlive);
        this.firstMove = true;
        this.vulnerableToEnPasant = false;
    }
    
    Pawn(Pawn otherPawn)
    {
        super(otherPawn);
        this.firstMove = otherPawn.firstMove;
        this.vulnerableToEnPasant = otherPawn.vulnerableToEnPasant;
    }
    
    @Override
    public Piece copy() {
        return new Pawn(this);
    }
    
    @Override
    //only updates NEXT values and does the myKingInCheck function
    //if it passes the myKingInCheck function, then set CURR values to NEXT values
    public boolean moveTo(int let, int num, Board board) {        
        //forward, diagonal one space:
        if (
            canMoveTo(let, num, board)
        )
        {
            //en passant:
            if (board.getPiece(let, getNum()).isVulnerableToEnPassant() &&
                isEnemyOf(board.getPiece(let, getNum())))
            {
                firstMove = false;
                board.getPiece(let, getNum()).setNextAliveFalse();
                setNextCoordinates(let, num);
                return true;
            }
            
            //capture enemy at destination:
            if (isEnemyOf(board.getPiece(let, num)))
            {
                firstMove = false;
                board.getPiece(let, num).setNextAliveFalse();
                setNextCoordinates(let, num);
                
                //promotion at edge of board:
                promotion(let, num, board);
                
                return true;
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
            firstMove = false;
            setNextCoordinates(let, num);
            
            //promotion at edge of board:
            promotion(let, num, board);
                
            return true;
        }
        
        //double jump over a vacant space to a vacant space on the first move:
        if (
                firstMove && let == getLet() && board.getPiece(let, num).isVacant() &&
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
            firstMove = false;
            vulnerableToEnPasant = true;
            setNextCoordinates(let, num);

            return true;
        }

        return false;
    }
    
    private void promotion(int let, int num, Board board)
    {
        if (num == 8 || num == 1)
        {
            String choiceString;
            char choiceChar;
            boolean choosing = true;
            setNextAliveFalse(); //this pawn is no long active
            Scanner input = new Scanner(System.in);
            System.out.println("Promotion. Enter QUEEN, KNIGHT, ROOK, or BISHOP.");

            while(choosing)
            {
                choiceString = input.next();
                if(choiceString.length() > 0)
                {
                    choiceChar = Character.toUpperCase(choiceString.charAt(0));
                    if (!(choiceChar == 'Q' ||
                            choiceChar == 'K' ||
                            choiceChar == 'R' ||
                            choiceChar == 'B'))
                    {
                        System.out.println("Invalid input. Enter QUEEN, KNIGHT, ROOK, or BISHOP.");
                        continue;
                    }
                }
                else
                {
                    System.out.println("Invalid input. Enter QUEEN, KNIGHT, ROOK, or BISHOP.");
                    continue;
                }
                board.getPawnsPromotion(this, choiceChar).setNextAliveTrue();
                this.setNextAliveFalse();
                board.getPawnsPromotion(this, choiceChar).setNextCoordinates(let, num);
                choosing = false;
            }
        }
    }
    
    @Override
    public boolean isVulnerableToEnPassant()
    {
        return vulnerableToEnPasant;
    }
    
    @Override
    public void setVulnerableToEnPassantFalse()
    {
        vulnerableToEnPasant = false;
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

    @Override
    public boolean isEqualTo(Piece otherPiece) 
    {
        if (otherPiece instanceof Pawn)
        {
            return (this.vulnerableToEnPasant == ((Pawn)otherPiece).vulnerableToEnPasant &&
                    this.firstMove == ((Pawn)otherPiece).firstMove &&
                    isEqualToBaseValues(otherPiece));
        }
        
        return false;
    }

    @Override
    public boolean canCastle() {
        return false;
    }
    
}
