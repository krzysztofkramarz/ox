package com.fonowizja.ox.game_elements;

import static com.fonowizja.ox.game_elements.IntegerRangeHelper.generateRangeOfSearching;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author krzysztof.kramarz
 */
class AllWinningFieldsCreator
{
   private final Integer x;
   private final Integer boardSize;
   private final Integer winningSize;

   public AllWinningFieldsCreator(Integer x, Integer boardSize, Integer winningSize)
   {
      this.x = x;
      this.boardSize = boardSize;
      this.winningSize = winningSize;
   }

   Map<String, List<Integer>> createAllWinningFields()
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
         if (i % x == 0)
         {
            beginingOfRows.add(i);
         }
      }

      List<List<Integer>> horizontalWinningFields = new ArrayList<>();
      Integer howManySteps = x - winningSize - 1;
      for (Integer begining : beginingOfRows)
      {
         for (int i = begining; i + winningSize -1 < begining + x; i++)
         {
            horizontalWinningFields.add(generateRangeOfSearching(i, n -> n + 1, winningSize));
         }
      }

      return horizontalWinningFields;
   }

   private List<List<Integer>> createVerticalWinnerFields()
   {
      List<List<Integer>> verticalWinningFields = new ArrayList<>();

      for (int a = 0; a < x; a++)
      {

         for (int i = a; i + (winningSize - 1) * x < boardSize; i += x)
         {
            verticalWinningFields.add(generateRangeOfSearching(i, n -> n + x, winningSize));
         }
      }
      return verticalWinningFields;
   }

   List<List<Integer>> createSlashWinnerFields()
   {
      List<List<Integer>> slashWinningFields = new ArrayList<>();
      for (int i = 0; i + ((x - 1) * (winningSize - 1)) <= boardSize; i++)
      {

         if (i + (winningSize -1) * (x - 1) >i - (i % x) + (winningSize -1) * x)
         {
            continue;
         }
         slashWinningFields.add(generateRangeOfSearching(i, n -> n + x - 1, winningSize));
      }
      return slashWinningFields;
   }

   private List<List<Integer>> createBackSlashWinnerFields()
   {
      List<List<Integer>> backSlashWinningFields = new ArrayList<>();
      for (int i = 0; i + ((x + 1) * (winningSize - 1)) <= boardSize; i++)
      {
         if (i + winningSize * (x + 1) >= i - (i % x) + (winningSize + 1) * x)
         {
            continue;
         }
         backSlashWinningFields.add(generateRangeOfSearching(i, n -> n + x + 1, winningSize));
      }
      return backSlashWinningFields;
   }

}


