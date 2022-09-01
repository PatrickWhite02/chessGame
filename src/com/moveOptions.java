package com;

import java.util.ArrayList;
import java.util.Arrays;

public class moveOptions extends ArrayList<Integer> {
    private static final int blank = 0;
    private static final int white = 1;
    private static final int black = 2;
    private static final int whiteKing = 1;
    private static final int whiteQueen = 2;
    private static final int whiteBishop = 3;
    private static final int whiteKnight = 4;
    private static final int whiteRook = 5;
    private static final int whitePawn = 6;
    private static final int blackKing = 7;
    private static final int blackQueen = 8;
    private static final int blackBishop = 9;
    private static final int blackKnight = 10;
    private static final int blackRook = 11;
    private static final int blackPawn = 12;
    private static final ArrayList<Integer> leftSide = new ArrayList<>(Arrays.asList(0,8,16,24,32,40,48,56));
    private static final ArrayList<Integer> rightSide = new ArrayList<>(Arrays.asList(7, 15, 23, 31, 39, 47, 55, 63));
    private static final int straight = 0;
    private static final int right = 1;
    private static final int left  = 2;

    private static Tile [] tiles = new Tile[64];
    private Tile selected;
    public static boolean doCheckTest = true;


    private int locationOfTile;
    private int teamOfTile;
    public int checkTmpTeam;

    public moveOptions(Tile [] tilesInput, Tile selectedTile){
        tiles = tilesInput;
        selected = selectedTile;
        locationOfTile = selected.getCoords();
        teamOfTile = selected.getCoords();

        if (selected.getValue() == blackPawn || selected.getValue() == whitePawn) {
            checkPawnMoves();
        }
        //move option for rooks
        if(selected.getValue() == blackRook || selected.getValue() == whiteRook){
            checkUpDown();
            checkLeftRight();
        }
        //move options for knights
        if(selected.getValue() == blackKnight || selected.getValue() == whiteKnight){
            checkKnightMoves();
        }
        //move options for bishops
        if(selected.getValue() == blackBishop || selected.getValue() == whiteBishop){
            checkLeftDiagonal();
            checkRightDiagonal();
        }
        //move option for queens
        if(selected.getValue() == blackQueen || selected.getValue() == whiteQueen){
            checkUpDown();
            checkLeftRight();
            checkRightDiagonal();
            checkLeftDiagonal();
        }
        //move option for kings. Need to add check functionality
        if(selected.getValue() == blackKing || selected.getValue() == whiteKing){
            checkKingMoves();
        }
    }
    private void checkUpDown(){
        int tmpLocation = locationOfTile;
        //keep looping until we hit the top line
        while(tmpLocation >= 8){
            tmpLocation = tmpLocation - 8;
            if(!(moveOptionsFilter(tmpLocation - locationOfTile, straight, false))){
                break;
            }
        }
        tmpLocation = locationOfTile;
        //keep looping until we hit the bottom line
        while(tmpLocation < 63){
            tmpLocation = tmpLocation + 8;
            if(!(moveOptionsFilter(tmpLocation - locationOfTile, straight, false))){
                break;
            }
        }
    }
    private void checkLeftRight(){
        int tmpLocation = locationOfTile;
        //keep looping until we hit the right side
        while(!(rightSide.contains(tmpLocation))){
            tmpLocation = tmpLocation + 1;
            if(!(moveOptionsFilter(tmpLocation - locationOfTile, straight, false))){
                break;
            }
        }
        tmpLocation = locationOfTile;
        //keep looping until we hit the bottom line
        while(!(leftSide.contains(tmpLocation))){
            tmpLocation = tmpLocation - 1;
            if(!(moveOptionsFilter(tmpLocation - locationOfTile, straight, false))){
                break;
            }
        }
    }
    private void checkLeftDiagonal (){
        int tmpLocation = locationOfTile;
        if(!(leftSide.contains(tmpLocation))){
            //top left Diagonal
            while(!leftSide.contains(tmpLocation)){
                tmpLocation = tmpLocation + (7);
                if(!(tmpLocation == locationOfTile)){
                    //break the loop if there is a piece in the way.
                    if(!(moveOptionsFilter(tmpLocation-locationOfTile, left, false))){
                        break;
                    }
                }
            }
        }
        tmpLocation = locationOfTile;
        if(!(leftSide.contains(tmpLocation))){
            //bottom left diagonal
            while(!leftSide.contains(tmpLocation)){
                tmpLocation = tmpLocation - (9);
                if(!(tmpLocation == locationOfTile)){
                    //break the loop if there is a piece in the way.
                    if(!(moveOptionsFilter(tmpLocation-locationOfTile, left, false))){
                        break;
                    }
                }
            }
        }
    }
    private void checkRightDiagonal (){
        int tmpLocation = locationOfTile;
        if(!(rightSide.contains(tmpLocation))){
            //bottom right diagonal
            while(!rightSide.contains(tmpLocation)){
                tmpLocation = tmpLocation + (9);
                if(!(tmpLocation == locationOfTile)){
                    //break the loop if there is a piece in the way.
                    if(!(moveOptionsFilter(tmpLocation-locationOfTile, right, false))){
                        break;
                    }
                }
            }
        }
        tmpLocation = locationOfTile;
        if(!(rightSide.contains(tmpLocation))){
            //bottom right diagonal
            while(!rightSide.contains(tmpLocation)){
                tmpLocation = tmpLocation - (7);
                if(!(tmpLocation == locationOfTile)){
                    //break the loop if there is a piece in the way.
                    if(!(moveOptionsFilter(tmpLocation-locationOfTile, right, false))){
                        break;
                    }
                }
            }
        }
    }
    private void checkKnightMoves(){
        //Check to make sure we aren't one away from the edge or on the edge
        //redundant check if leftside contains location?
        if(!leftSide.contains(locationOfTile - 1) && !leftSide.contains(locationOfTile)){
            //bottom left horizontal L.
            moveOptionsFilter(6, left, false);
            //top left horizontal L
            moveOptionsFilter(-10, left, false);
        }
        //bottom left vertical L
        moveOptionsFilter(15, left, false);
        //top left vertical L
        moveOptionsFilter(-17, left, false);
        //check to make sure we aren't one away from the edge or on the edge
        if(!rightSide.contains(locationOfTile + 1) && !rightSide.contains(locationOfTile)){
            //bottom right horizontal L
            moveOptionsFilter(10, right, false);
            //top right horizontal L
            moveOptionsFilter(-6, right, false);
        }
        //bottom right vertical L
        moveOptionsFilter(17, right, false);
        //top right vertical L
        moveOptionsFilter(-15, right, false);
    }
    private void checkPawnMoves(){
        //move options for a black pawn
        if(selected.getValue()==blackPawn){
            //move straight
            moveOptionsFilter(8, straight, true);
            //if the pawn is still in it's starting position, it can move forward 2
            if(locationOfTile<16){
                moveOptionsFilter(16, straight, true);
            }
            //move diagonal to the left. First make sure the piece isn't in the bottom row
            if(!(locationOfTile > 55)){
                if(tiles[locationOfTile + 7].getTeam()!=blank && tiles[locationOfTile +7].getTeam()!=teamOfTile){
                    moveOptionsFilter(7, left, false);
                }
            }
            //move diagonal to the right. first make sure the piece isn't in the bottom row OR in the piece right before the bottom row
            if(!(locationOfTile > 54)){
                if(tiles[locationOfTile + 9].getTeam()!=blank && tiles[locationOfTile +9].getTeam()!=teamOfTile){
                    moveOptionsFilter(9, right, false);
                }
            }
        }
        //move options for a white pawn
        if(selected.getValue()==whitePawn) {
            //move straight
            moveOptionsFilter(-8, straight, true);
            //if the pawn is still in it's starting position, it can move forward 2
            if (locationOfTile > 47) {
                moveOptionsFilter(-16, straight, true);
            }
            //if the pawn can diagonally take a piece of the other player, then do so
            //diagonal to the left. first make sure the piece isn't in the top row OR in the piece right before the top row
            if (!(locationOfTile < 9)) {
                if (tiles[locationOfTile - 9].getTeam() != blank && tiles[locationOfTile - 9].getTeam() != teamOfTile) {
                    moveOptionsFilter(-9, left, false);
                }
            }
            //diagonal to the right. first make sure the piece isn't in the top row
            if (!(locationOfTile < 8)) {
                if (tiles[locationOfTile - 7].getTeam() != blank && tiles[locationOfTile - 7].getTeam() != teamOfTile) {
                    moveOptionsFilter(-7, right, false);
                }
            }
        }
    }
    private void checkKingMoves(){
        //move up
        moveOptionsFilter(-8, straight, false);
        //move down
        moveOptionsFilter(8, straight, false);
        //move left
        if(!leftSide.contains(locationOfTile)){
            moveOptionsFilter(-1, straight, false);
            //move top left
            moveOptionsFilter(-9, straight, false);
            //move bottom left
            moveOptionsFilter(7, straight, false);
        }
        //move right
        if(!rightSide.contains(locationOfTile)){
            moveOptionsFilter(1, straight, false);
            //move top right
            moveOptionsFilter(-7, straight, false);
            //move bottom right
            moveOptionsFilter(9, straight, false);
        }
        //white castling
        if(selected.getValue() == whiteKing && !Board.getWhiteKingHasMoved()){
            if(!Board.getWhiteRookRightHasMoved()){
                //right castle
                //check if the spaces between the rook and the king are occupied
                if(tiles[62].getValue() == blank && tiles[61].getValue() == blank){
                    //castle white to the right but make sure it won't put us in check
                    //castling has it's own filter since it moves both the king and the rook
                    filterKingCastleForCheck(60, 63, 62, 61);
                }
            }
            if(!Board.getWhiteRookLeftHasMoved()) {
                //check if the spaces between the rook and the king are occupied
                if (tiles[57].getValue() == blank && tiles[58].getValue() == blank && tiles[59].getValue() == blank) {
                    //castle white to the left
                    filterKingCastleForCheck(60, 56, 58, 59);
                }
            }
        }
        //black castling
        if(selected.getValue() == blackKing && !Board.getBlackKingHasMoved()){
            if(!Board.getBlackRookRightHasMoved()){
                //check if the spaces between the rook and the king are occupied
                if(tiles[6].getValue() == blank && tiles[5].getValue() == blank){
                    //castle black to the right
                    filterKingCastleForCheck(4, 7, 6, 5);
                }
            }
            if(!Board.getBlackRookLeftHasMoved()) {
                //check if the spaces between the rook and the king are occupied
                if (tiles[1].getValue() == blank && tiles[2].getValue() == blank && tiles[3].getValue() == blank) {
                    //castle black to the left
                    filterKingCastleForCheck(4, 0, 2, 3);
                }
            }
        }
    }
    private void filterKingCastleForCheck(int kingLoc, int rookLoc, int kingDestination, int rookDestination){
        if(doCheckTest) {
            String kingTag = "whiteKing";
            String rookTag = "whiteRook";
            if(kingLoc == 4){
                kingTag = "blackKing";
                rookTag = "blackRook";
            }
            tiles[kingLoc].setValue("blank");
            tiles[rookLoc].setValue("blank");
            tiles[kingDestination].setValue(kingTag);
            tiles[rookDestination].setValue(rookTag);
            if (!willPutInCheck()) {
                add(kingDestination);
            }
            tiles[kingLoc].setValue(kingTag);
            tiles[rookLoc].setValue(rookTag);
            tiles[kingDestination].setValue("blank");
            tiles[rookDestination].setValue("blank");
        }
    }
    private boolean moveOptionsFilter(int moveDelta, int direction, boolean pawn){
        //if the requested piece isn't occupied, add it to the list
        //I have to put in a special case for a pawn since they can't take a piece directly in front of it
        if(moveDelta + locationOfTile > 63 || moveDelta + locationOfTile < 0){
            return false;
        }
        if(doCheckTest){
            checkTmpTeam = selected.getTeam();
            String containPieceTypeOfLocation = tiles[locationOfTile].getValueAsString();
            String containPieceTypeOfMoveDelta = tiles[locationOfTile + moveDelta].getValueAsString();
            tiles[locationOfTile + moveDelta].setValue(containPieceTypeOfLocation);
            tiles[locationOfTile].setValue("blank");
            //if the potential move would put you in check
            if(willPutInCheck()){
                //put it back
                tiles[locationOfTile].setValue(containPieceTypeOfLocation);
                tiles[locationOfTile + moveDelta].setValue(containPieceTypeOfMoveDelta);
                //break the loop of checks if it finds an occupied piece
                if(tiles[locationOfTile + moveDelta].isOccupied()){
                    return false;
                }
                return true;
            }
            tiles[locationOfTile].setValue(containPieceTypeOfLocation);
            tiles[locationOfTile + moveDelta].setValue(containPieceTypeOfMoveDelta);
        }
        boolean r = false;
        if(!tiles[locationOfTile + moveDelta].isOccupied() || (!pawn && tiles[locationOfTile + moveDelta].getTeam()!=selected.getTeam())) {
        if (direction == straight) {
                add(locationOfTile + moveDelta);
                r=true;
            }
            else if (direction == left && !rightSide.contains(locationOfTile + moveDelta)) {
                add(locationOfTile +  moveDelta);
                r=true;
            } else if (direction == right && !leftSide.contains(locationOfTile + moveDelta)){
                add(locationOfTile + moveDelta);
                r=true;
            }
            if(tiles[locationOfTile + moveDelta].isOccupied()){
                return false;
            }
        }
        return r;
    }
    private boolean willPutInCheck(){
        doCheckTest = false;
        for(Tile i : tiles){
            if(i.isOccupied() && i.getTeam()!=checkTmpTeam) {
                for (Tile j : i.moveOptions(tiles)) {
                    if ((j.getValue() == blackKing && checkTmpTeam == black) || (j.getValue() == whiteKing && checkTmpTeam == white)) {
                        doCheckTest = true;
                        return true;
                    }
                }
            }
        }
        doCheckTest = true;
        return false;
    }
}
