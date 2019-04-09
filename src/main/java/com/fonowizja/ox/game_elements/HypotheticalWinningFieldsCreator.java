package com.fonowizja.ox.game_elements;

import static com.fonowizja.ox.game_elements.IntegerRangeHelper.generateRangeOfSearching;

import java.util.ArrayList;
import java.util.List;

/**
 * @author krzysztof.kramarz
 */
@SuppressWarnings("MissortedModifiers")
class HypotheticalWinningFieldsCreator
{
   //TODO mniejszyć i zrefaktorować ilość pól, zrefaktorować logikę obliczeń
   //TODO redundantne dane
   private Sign sign;

   private Integer winningSize;

   private Integer x;

   private Integer positionOnBoard;

   private Integer boardSize;

   List<List<Integer>> createHorizontalWinnerFields()
   {
      List<List<Integer>> hypotheticalFields = new ArrayList<>();
      int scope = winningSize - 1;
      int statOfCurrentRow = positionOnBoard - positionOnBoard % x;
      // końcowy zakres nie wyjedzie na kolejny rząd - inne rozwiazanie:    i + scope < ((whichRow + 1) * x) + x
      for (int i = positionOnBoard - scope; i + scope < (statOfCurrentRow) + x && i <= positionOnBoard; i++)
      {
         if (i < statOfCurrentRow)
         {
            continue;
         }

         hypotheticalFields.add(generateRangeOfSearching(i, n -> n + 1, winningSize));
      }
      return hypotheticalFields;
   }

   List<List<Integer>> createVerticalWinnerFields()
   {
      List<List<Integer>> hypotheticalFields = new ArrayList<>();
      int scope = winningSize - 1;
      for (int i = positionOnBoard - scope * x; i + scope * x < boardSize && i <= positionOnBoard; i += x)
      {
         if (i < 0)
         {
            continue;
         }
         hypotheticalFields.add(generateRangeOfSearching(i, n -> n + x, winningSize));
      }
      return hypotheticalFields;
   }

   List<List<Integer>> createSlashWinnerFields()
   {
      List<List<Integer>> hypotheticalFields = new ArrayList<>();
      int scope = winningSize - 1;
      for (int i = positionOnBoard - scope * (x - 1); i + scope * (x - 1) < boardSize && i <= positionOnBoard; i += (x - 1))
      {
         if (i < 0)
         {
            continue;
         }

         if (i + scope * (x - 1) < i - (i % x) + scope * x)
         {
            continue;
         }
         hypotheticalFields.add(generateRangeOfSearching(i, n -> n + x - 1, winningSize));
      }
      return hypotheticalFields;
   }

   List<List<Integer>> createBackSlashWinnerFields()
   {
      List<List<Integer>> hypotheticalFields = new ArrayList<>();
      int scope = winningSize - 1;
      for (int i = positionOnBoard - scope * (x + 1); i + scope * (x + 1) < boardSize && i <= positionOnBoard; i += x + 1)
      {
         if (i < 0)
         {
            continue;
         }
         if (i + scope * (x + 1) >= i - (i % x) + (scope + 1) * x)
         {
            continue;
         }
         hypotheticalFields.add(generateRangeOfSearching(i, n -> n + x + 1, winningSize));
      }
      return hypotheticalFields;
   }

   //      ###########   BUILDER   ###########
   static final class Builder implements NeedSign, NeedWinningSize, NeedX, NeedPositionOnBoard, CanBeBuild, NeedBoardSize
   {
      private Sign sign;
      private Integer winningSize;
      private Integer x;
      private Integer positionOnBoard;
      private Integer boardSize;

      @Override
      public Builder sign(Sign sign)
      {
         this.sign = sign;
         return this;
      }

      @Override
      public Builder winningSize(Integer winningSize)
      {
         this.winningSize = winningSize;
         return this;
      }

      @Override
      public Builder x(Integer x)
      {
         this.x = x;
         return this;
      }

      @Override
      public Builder positionOnBoard(Integer positionOnBoard)
      {
         this.positionOnBoard = positionOnBoard;
         return this;
      }

      @Override
      public Builder boardSize(Integer boardSize)
      {
         this.boardSize = boardSize;
         return this;
      }

      @Override
      public HypotheticalWinningFieldsCreator build()
      {
         HypotheticalWinningFieldsCreator toBuild = new HypotheticalWinningFieldsCreator();
         toBuild.sign = this.sign;
         toBuild.winningSize = this.winningSize;
         toBuild.x = this.x;
         toBuild.positionOnBoard = this.positionOnBoard;
         toBuild.boardSize = this.boardSize;

         return toBuild;
      }

   }

   interface NeedSign
   {
      NeedWinningSize sign(Sign sign);
   }

   interface NeedWinningSize
   {
      NeedX winningSize(Integer winningSize);
   }

   interface NeedX
   {
      NeedPositionOnBoard x(Integer x);
   }

   interface NeedPositionOnBoard
   {
      NeedBoardSize positionOnBoard(Integer positionOnBoard);
   }

   interface NeedBoardSize
   {
      CanBeBuild boardSize(Integer boardSize);
   }

   interface CanBeBuild
   {
      HypotheticalWinningFieldsCreator build();
   }

   static NeedSign builder()
   {
      return new Builder();
   }
}
