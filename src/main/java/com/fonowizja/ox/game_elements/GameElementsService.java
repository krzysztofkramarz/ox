package com.fonowizja.ox.game_elements;

import com.fonowizja.ox.game_elements.game.BoardObservator;

/**
 * @author krzysztof.kramarz
 */
public interface GameElementsService
{
   String getBoardAsString();

   boolean putSignIntoBoard(Sign sign, Integer positionInRow, Integer howManyFullRows);

   void setEmptyBoard();

   void setBoardObservator(BoardObservator boardObservator);

}
