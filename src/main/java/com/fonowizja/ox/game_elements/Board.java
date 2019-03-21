package com.fonowizja.ox.game_elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.fonowizja.ox.game_elements.game.BoardObservator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Place for acting single play
 *
 * @author krzysztof.kramarz
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class Board
{
   static final String EXCEPTION_MESSAGE = "Jakikolwiek rozmiar tablicy nie może być = 0";
   private List<Sign> board;
   private Integer boardSize;
   private Integer x;
   private Integer y;
   private Integer winningSize;
   private BoardObservator boardObservator;
   private HashMap<String, List<Integer>> xxxWinnerFields = new HashMap<>();
   private HashMap<String, List<Integer>> oooWinnerFields = new HashMap<>();
   private Integer positionInBoard;

   private Board(Integer x, Integer y, Integer winningSize) throws IllegalArgumentException
   {
      if (x == 0 || y == 0)
      {
         throw new IllegalArgumentException(EXCEPTION_MESSAGE);
      }
      this.x = x;
      this.y = y;
      boardSize = x * y;
      this.winningSize = winningSize;
      board = new ArrayList<>(boardSize);
      setEmptyBoard();
   }

   void setEmptyBoard()
   {
      for (int i = 0; i < boardSize; i++)
      {
         board.add(Sign.EMPTY);
      }
   }

   void setBoardObservator(BoardObservator boardObservator)
   {
      this.boardObservator = boardObservator;

   }

   String getBoardAsString()
   {
      StringBuilder boardAsString = new StringBuilder();

      boardAsString.append(" ");

      for (int i = 0; i < x; i++)
      {
         boardAsString.append(i + "|");
      }
      boardAsString.append("\n");

      int rowNumber = 0;
      boardAsString.append(rowNumber);

      for (int i = 0; i < boardSize; i++)
      {

         if (i % x == 0 && i != 0)
         {
            boardAsString.append("\n");
            rowNumber++;
            boardAsString.append(rowNumber);
         }

         boardAsString.append(board.get(i).getSign());
         boardAsString.append("|");

      }
      return boardAsString.toString();
   }

   boolean putSignIntoBoard(Sign sign, Integer positionInRow, Integer howManyFullRows)
   {
      positionInBoard = howManyFullRows * x + positionInRow;

      if (board.get(positionInBoard) == Sign.EMPTY)
      {
         board.add(positionInBoard, sign);
         setFieldsToCheck(sign, howManyFullRows);

         return true;
      }

      return false;
   }

   private void setFieldsToCheck(Sign sign, Integer howManyFullRows)
   {
      createHorizontalWinnerFields(sign);
      createVerticalWinnerFields(sign);
      createSlashWinnerFields(sign, howManyFullRows);
      createBackSlashWinnerFields(sign);

      // boardObservator.notifyAboutWinner(sign);
   }

   void createBackSlashWinnerFields(Sign sign)
   {
      int scope = winningSize - 1;
      int j = scope;
      int start = howManyFullRows * x + (x - 1);
      for (int i = positionInBoard - (scope * x + j); i  >= howManyFullRows * x - scope*x  && i < boardSize; i += x + 1)
      {
         if (i >= start - x * scope)
         {
            continue;
         }
         List<Integer> horizontalFields = generateRangeOfSearching(i, n -> n + x - 1, winningSize);
         String key = horizontalFields.toString();
         fillMap(sign, key, horizontalFields);
         j--;
      }
   }

   void createSlashWinnerFields(Sign sign, Integer howManyFullRows)
   {
      int scope = winningSize - 1;
      int j = scope;
      int start = howManyFullRows * x + (x - 1);
      for (int i = positionInBoard - (scope * x - j); i + scope * ( x - 1) >= howManyFullRows * x + scope && i > 0; i += x - 1)
      {
         if (i <= (start) - x * scope)
         {
            continue;
         }
         List<Integer> horizontalFields = generateRangeOfSearching(i, n -> n + x - 1, winningSize);
         String key = horizontalFields.toString();
         fillMap(sign, key, horizontalFields);
         j++;
      }
   }

   void createVerticalWinnerFields(Sign sign)
   {
      for (int i = positionInBoard - ((boardSize - 1) * x); i + ((boardSize - 1) * x) < boardSize - x && i > 0; i += x)
      {
         List<Integer> horizontalFields = generateRangeOfSearching(i, n -> n + x, winningSize);
         String key = horizontalFields.toString();
         fillMap(sign, key, horizontalFields);
      }
   }

   void createHorizontalWinnerFields(Sign sign)
   {
      int scope = winningSize - 1;

      for (int i = positionInBoard - scope; i % x != 0 && i < positionInBoard + scope; i++)
      {
         List<Integer> horizontalFields = generateRangeOfSearching(i, n -> n + 1, winningSize);
         String key = horizontalFields.toString();
         fillMap(sign, key, horizontalFields);
      }
   }

   boolean fillMap(Sign sign, String key, List<Integer> value)
   {
      if (sign == Sign.O)
      {
         xxxWinnerFields.remove(key);
         if (!oooWinnerFields.containsKey(key))
         {
            oooWinnerFields.put(key, value);
         }
         return true;
      }

      if (sign == Sign.X)
      {
         oooWinnerFields.remove(key);
         if (!xxxWinnerFields.containsKey(key))
         {
            xxxWinnerFields.put(key, value);
         }
         return true;
      }

      return false;
   }

   List<Integer> generateRangeOfSearching(Integer startingPosition, IntUnaryOperator intUnaryOperator, Integer limit)
   {
      return IntStream.iterate(startingPosition, intUnaryOperator)
            .limit(limit)
            .boxed()
            .collect(Collectors.toList());
   }
   ///////////////////   BUILDER  //////////////////////////

   static final class BoardBuilder // implements XXX, YYY, CanBeBuild
   {
      private Integer x;
      private Integer y;
      private Integer winningSize;

      BoardBuilder xxx(Integer x)
      {
         this.x = x;
         return this;
      }

      BoardBuilder ooo(Integer y)
      {
         this.y = y;
         return this;
      }

      BoardBuilder winningSize(Integer winningSize)
      {
         this.winningSize = winningSize;
         return this;
      }

      Board build()
      {
         Board board = new Board(x, y, winningSize);
         return board;
      }

   }

   // private interface XXX
   // {
   //    YYY x(Integer x);
   // }
   //
   // private interface YYY
   // {
   //    CanBeBuild y(Integer y);
   // }
   //
   // private interface CanBeBuild
   // {
   //    Board build();
   // }
   //
   static BoardBuilder builder()
   {
      return new BoardBuilder();
   }
}
