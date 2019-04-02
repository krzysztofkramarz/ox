package com.fonowizja.ox.game_elements;

import java.util.List;
import java.util.Map;

import com.fonowizja.ox.game.BoardObservator;

/**
 * @author krzysztof.kramarz
 */
public interface GameElementsService
{
   @Deprecated
   String getBoardAsString();

   boolean isWinningMove(Sign sign, Integer boardPosition) throws FieldIsNotEmptyException, IllegalSignException;

   void cleanBoard();

   void setBoardObservator(BoardObservator boardObservator);

   boolean isDraw();

   Map<String, List<Integer>> getHypotheticalWinningFieldsForX();

   Map<String, List<Integer>> getHypotheticalWinningFieldsForO();

   /**
    * This is simple getter of allEmptyWinningCombinationsThatCanBeUsed map
    *
    * @return allEmptyWinningCombinationsThatCanBeUsed;
    */
   Map<String, List<Integer>> getAllEmptyWinningCombinationsThatCanBeUsed();

}
