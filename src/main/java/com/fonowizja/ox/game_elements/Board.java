package com.fonowizja.ox.game_elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import com.fonowizja.ox.game_elements.game.BoardObservator;

/**
 * Place for acting single play
 *
 * @author krzysztof.kramarz
 */
@SuppressWarnings("FieldNamingConvention")
class Board
{
   //TODO uproscic pola, kilka niepotrzebnych lub redundantnych

   static final String EXCEPTION_MESSAGE = "Jakikolwiek rozmiar tablicy nie może być = 0";
   private final List<Sign> board = new ArrayList<>();
   private final Integer boardSize;
   private final Integer x;
   private final Integer y;
   private final Integer winningSize;
   private BoardObservator boardObservator;
   private final Map<String, List<Integer>> hypotheticalWinningFieldsXXX = new HashMap<>();
   private final Map<String, List<Integer>> hypotheticalWinningFieldsOOO = new HashMap<>();
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
      cleanBoard();

   }

   void cleanBoard()
   {
      board.clear();
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

   boolean makeMove(Sign sign, Integer positionX, Integer positionY) throws Exception
   {

      if (!putSignIntoBoard(sign, positionX, positionY))
      {
         //TODO zrobic wyjątek ze nie da się wstawic znaku na niepuste pole
         throw new Exception();
      }

      createHypotheticalWinningFields(sign);

      // boardObservator.notifyAboutWinner(sign);

      return checkWinnerAtThisMove(sign);

   }

   private boolean checkWinnerAtThisMove(Sign sign) throws Exception
   {
      AtomicBoolean gameIsWin = new AtomicBoolean(true);
      if (sign == Sign.O)
      {
         for (String keysOfFieldsToCheck : hypotheticalWinningFieldsOOO.keySet())
         {
            List<Integer> fieldsToCheck = hypotheticalWinningFieldsOOO.get(keysOfFieldsToCheck);
            fieldsToCheck.forEach(index -> {
               if (board.get(index) != sign)
               {
                  gameIsWin.set(false);
               }
            });
         }
      }
      else if (sign == Sign.X)
      {
         {
            for (String keysOfFieldsToCheck : hypotheticalWinningFieldsXXX.keySet())
            {
               List<Integer> fieldsToCheck = hypotheticalWinningFieldsXXX.get(keysOfFieldsToCheck);
               fieldsToCheck.forEach(index -> {
                  if (board.get(index) != sign)
                  {
                     gameIsWin.set(false);
                  }
               });
            }
         }

      }
      else
      {
         //TODO zrobic wyjatek
         throw new Exception();
      }
      return gameIsWin.get();
   }

   boolean putSignIntoBoard(Sign sign, Integer positionX, Integer positionY)
   {
      positionInBoard = positionY * x + positionX;

      if (board.get(positionInBoard) == Sign.EMPTY)
      {
         board.add(positionInBoard, sign);

         return true;
      }

      return false;
   }

   private void createHypotheticalWinningFields(Sign sign)
   {
      HypotheticalWinningFieldsCreator hypotheticalWinningFieldsCreator =
            HypotheticalWinningFieldsCreator.builder()
                  .sign(sign)
                  .winningSize(winningSize)
                  .x(x)
                  .positionInBoard(positionInBoard)
                  .boardSize(boardSize)
                  .build();

      List<List<Integer>> horizontalWinnerFields = hypotheticalWinningFieldsCreator.createHorizontalWinnerFields();
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

   }

    void fillMap(Sign sign, String key, List<Integer> value)
   {
      if (sign == Sign.O)
      {
         if (hypotheticalWinningFieldsXXX.containsKey(key))
         {
            hypotheticalWinningFieldsXXX.remove(key);
            return;
         }
         if (!hypotheticalWinningFieldsOOO.containsKey(key))
         {
            hypotheticalWinningFieldsOOO.put(key, value);
         }
         return;
      }

      if (sign == Sign.X)
      {
         if (hypotheticalWinningFieldsOOO.containsKey(key))
         {
            hypotheticalWinningFieldsOOO.remove(key);
            return;
         }

         if (!hypotheticalWinningFieldsXXX.containsKey(key))
         {
            hypotheticalWinningFieldsXXX.put(key, value);
         }
      }

   }

   ///////////////////   BUILDER  //////////////////////////

   @SuppressWarnings("NewMethodNamingConvention")
   static final class BoardBuilder implements NeedX, NeedY, NeedWinningSize, CanBeBuild
   {
      private Integer x;
      private Integer y;
      private Integer winningSize;

      @Override
      public BoardBuilder x(Integer x)
      {
         this.x = x;
         return this;
      }

      @Override
      public BoardBuilder y(Integer y)
      {
         this.y = y;
         return this;
      }

      @Override
      public BoardBuilder winningSize(Integer winningSize)
      {
         this.winningSize = winningSize;
         return this;
      }

      @Override
      public Board build()
      {
         Board board = new Board(x, y, winningSize);
         return board;
      }

   }

   @SuppressWarnings("NewMethodNamingConvention")
   interface NeedX
   {
      NeedY x(Integer x);
   }

   @SuppressWarnings("NewMethodNamingConvention")
   interface NeedY
   {
      NeedWinningSize y(Integer y);
   }

   interface NeedWinningSize
   {
      CanBeBuild winningSize(Integer winningSize);
   }

   interface CanBeBuild
   {
      Board build();
   }

   static NeedX builder()
   {
      return new BoardBuilder();
   }
}
