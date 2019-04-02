package com.fonowizja.ox.game_elements;

import static com.fonowizja.ox.game_elements.IntegerRangeHelper.generateRangeOfSearching;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author krzysztof.kramarz
 */
class AllEmptyWinningCombinationsCreator
{

   private final Integer boardSize;
   private final Integer boardLenght;
   private final Integer winningSize;

   AllEmptyWinningCombinationsCreator(Integer boardSize, Integer boardLenght, Integer winningSize)
   {
      this.boardSize = boardSize;
      this.boardLenght = boardLenght;
      this.winningSize = winningSize;
   }

   Map<String, List<Integer>> createAllEmptyWinningCombinationsThatCanBeUsed()
   {

      Map<String, List<Integer>> allWinningFields = new HashMap<>();

      List<List<Integer>> horizontalWinningFields = createHorizontalWinnerFields();
      for (List<Integer> horizontal : horizontalWinningFields)
      {
         allWinningFields.put(horizontal.toString(), horizontal);
      }

      List<List<Integer>> verticalWinningFields = createVerticalWinnerFields();
      for (List<Integer> vertical : verticalWinningFields)
      {
         allWinningFields.put(vertical.toString(), vertical);
      }

      List<List<Integer>> slashWinningFields = createSlashWinnerFields();
      for (List<Integer> slash : slashWinningFields)
      {
         allWinningFields.put(slash.toString(), slash);
      }

      List<List<Integer>> backSlashWinningFields = createBackSlashWinnerFields();
      for (List<Integer> backSlash : backSlashWinningFields)
      {
         allWinningFields.put(backSlash.toString(), backSlash);
      }

      return allWinningFields;
   }

   private List<List<Integer>> createHorizontalWinnerFields()
   {

      List<Integer> beginingOfRows = new ArrayList<>();

      for (int i = 0; i < boardSize; i++)
      {
         if (i % boardLenght == 0)
         {
            beginingOfRows.add(i);
         }
      }

      List<List<Integer>> horizontalWinningFields = new ArrayList<>();
      Integer howManySteps = boardLenght - winningSize - 1;
      for (Integer begining : beginingOfRows)
      {
         for (int i = begining; i + winningSize - 1 < begining + boardLenght; i++)
         {
            horizontalWinningFields.add(generateRangeOfSearching(i, n -> n + 1, winningSize));
         }
      }

      return horizontalWinningFields;
   }

   private List<List<Integer>> createVerticalWinnerFields()
   {
      List<List<Integer>> verticalWinningFields = new ArrayList<>();

      for (int a = 0; a < boardLenght; a++)
      {

         for (int i = a; i + (winningSize - 1) * boardLenght < boardSize; i += boardLenght)
         {
            verticalWinningFields.add(generateRangeOfSearching(i, n -> n + boardLenght, winningSize));
         }
      }
      return verticalWinningFields;
   }

   private List<List<Integer>> createSlashWinnerFields()
   {
      List<List<Integer>> slashWinningFields = new ArrayList<>();
      for (int i = 0; i + ((boardLenght - 1) * (winningSize - 1)) < boardSize; i++)
      {

         if (i + (winningSize - 1) * (boardLenght - 1) < i - (i % boardLenght) + (winningSize - 1) * boardLenght)
         {
            continue;
         }
         slashWinningFields.add(generateRangeOfSearching(i, n -> n + boardLenght - 1, winningSize));
      }
      return slashWinningFields;
   }

   private List<List<Integer>> createBackSlashWinnerFields()
   {
      List<List<Integer>> backSlashWinningFields = new ArrayList<>();
      for (int i = 0; i + ((boardLenght + 1) * (winningSize - 1)) < boardSize; i++)
      {
         if (i + (winningSize - 1) * (boardLenght + 1) >= i - (i % boardLenght) + (winningSize) * boardLenght)
         {
            continue;
         }
         backSlashWinningFields.add(generateRangeOfSearching(i, n -> n + boardLenght + 1, winningSize));
      }
      return backSlashWinningFields;
   }

}


