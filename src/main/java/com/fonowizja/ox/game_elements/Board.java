package com.fonowizja.ox.game_elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
   private Map<String, List<Integer>> hypotheticalWinningFieldsXXX = new HashMap<>();
   private Map<String, List<Integer>> hypotheticalWinningFieldsOOO = new HashMap<>();
   private Integer positionInBoard;
   private HypotheticalWinningFieldsCreator createHorizontalWinnerFields;

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
      cleanBoard();

   }

   void cleanBoard()
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

         boardAsString.append(board.get(i));
         boardAsString.append("|");

      }
      return boardAsString.toString();
   }

   boolean putSignIntoBoard(Sign sign, Integer positionX, Integer positionY)
   {
      positionInBoard = positionY * x + positionX;

      if (board.get(positionInBoard) == Sign.EMPTY)
      {
         board.add(positionInBoard, sign);
         createHypotheticalWinningFields(sign, positionY);

         return true;
      }

      return false;
   }

   private void createHypotheticalWinningFields(Sign sign, Integer positionY)
   {
      HypotheticalWinningFieldsCreator hypotheticalWinningFieldsCreator =
            HypotheticalWinningFieldsCreator.builder()
                  .sign(sign)
                  .winningSize(winningSize)
                  .x(x)
                  .positionInBoard(positionInBoard)
                  .boardSize(boardSize)
                  .build();

      List<List<Integer>> horizontalWinnerFields = hypotheticalWinningFieldsCreator.createHorizontalWinnerFields( positionY);

      for (List<Integer> hypotheticalFields : horizontalWinnerFields)
      {
         String key = hypotheticalFields.toString();
         fillMap(sign, key, hypotheticalFields);
      }

      List<List<Integer>> verticalWinnerFields = hypotheticalWinningFieldsCreator.createVerticalWinnerFields();
      for (List<Integer> hypotheticalFields : verticalWinnerFields)
      {
         String key = hypotheticalFields.toString();
         fillMap(sign, key, hypotheticalFields);
      }

      List<List<Integer>> slashWinnerFields = hypotheticalWinningFieldsCreator.createSlashWinnerFields();
      for (List<Integer> hypotheticalFields : slashWinnerFields)
      {
         String key = hypotheticalFields.toString();
         fillMap(sign, key, hypotheticalFields);
      }

      List<List<Integer>> backSlashWinnerFields = hypotheticalWinningFieldsCreator.createBackSlashWinnerFields();
      for (List<Integer> hypotheticalFields : backSlashWinnerFields)
      {
         String key = hypotheticalFields.toString();
         fillMap(sign, key, hypotheticalFields);
      }


      // boardObservator.notifyAboutWinner(sign);
   }

   private void fillMap(Sign sign, String key, List<Integer> value)
   {
      if (sign == Sign.O)
      {
         hypotheticalWinningFieldsXXX.remove(key);
         if (!hypotheticalWinningFieldsOOO.containsKey(key))
         {
            hypotheticalWinningFieldsOOO.put(key, value);
         }
         return;
      }

      if (sign == Sign.X)
      {
         hypotheticalWinningFieldsOOO.remove(key);
         if (!hypotheticalWinningFieldsXXX.containsKey(key))
         {
            hypotheticalWinningFieldsXXX.put(key, value);
         }
      }

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
