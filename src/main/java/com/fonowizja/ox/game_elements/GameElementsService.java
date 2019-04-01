package com.fonowizja.ox.game_elements;

import com.fonowizja.ox.game.BoardObservator;

/**
 * @author krzysztof.kramarz
 */
public interface GameElementsService
{
   @Deprecated
   String getBoardAsString();

   boolean isWinningMove(Sign sign, Integer boardPosition) throws Exception;

   void cleanBoard();

   void setBoardObservator(BoardObservator boardObservator);

   boolean isDraw();

}
