package com.fonowizja.ox.game_elements;

/**
 * @author krzysztof.kramarz
 */
public interface GameElementsService
{
   String getBoardAsString();

   boolean putSignIntoBoard(Sign sign, Integer positionInRow, Integer howManyFullRows);

   void setEmptyBoard();

}
