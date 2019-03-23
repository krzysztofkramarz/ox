package com.fonowizja.ox.game_elements;

import com.fonowizja.ox.game_elements.game.BoardObservator;

/**
 * @author krzysztof.kramarz
 */
public interface GameElementsService
{
   String getBoardAsString();

   boolean putSignIntoBoard(Sign sign, Integer positionX, Integer positionY);

   void cleanBoard();

   void setBoardObservator(BoardObservator boardObservator);

}
