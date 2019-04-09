package com.fonowizja.ox.game_elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.AccessLevel;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Place for acting single play
 *
 * @author krzysztof.kramarz
 */
final class Board
{
   //TODO uproscic pola, kilka niepotrzebnych lub redundantnych
   private static Logger logger = LogManager.getLogger(Board.class);
   static final String EXCEPTION_MESSAGE = "boardSize to small";
   static final String EXCEPTION_WINNING_SIZE_MESSAGE = "winning size to small";
   private static final String FIELD_IS_NOT_EMPTY_EXCEPTION = "Field is not empty!";
   private static final String ILLEGAL_SIGN_EXCEPTION_MESSAGE = "This is neither X nor O";
   private final List<Sign> board = new ArrayList<>();
   private final Integer boardSize;
   private final Integer boardLenght;
   private final Integer winningSize;

   @Getter(AccessLevel.PACKAGE)
   private final Map<String, List<Integer>> hypotheticalWinningFieldsForX = new HashMap<>();
   @Getter(AccessLevel.PACKAGE)
   private final Map<String, List<Integer>> hypotheticalWinningFieldsForO = new HashMap<>();
   @Getter(AccessLevel.PACKAGE)
   private Map<String, List<Integer>> allEmptyWinningCombinationsThatCanBeUsed;
   private final AllEmptyWinningCombinationsCreator allEmptyWinningCombinationsCreator;

   private Board(Integer boardSize, Integer boardLenght, Integer winningSize)
   {
      this.boardSize = boardSize;
      this.boardLenght = boardLenght;
      this.winningSize = winningSize;
      allEmptyWinningCombinationsCreator = new AllEmptyWinningCombinationsCreator(boardSize, boardLenght, winningSize);
      revertBoardToBeginningState();
   }

   void revertBoardToBeginningState()
   {
      board.clear();
      for (int i = 0; i < boardSize; i++)
      {
         board.add(Sign.EMPTY);
      }
      hypotheticalWinningFieldsForO.clear();
      hypotheticalWinningFieldsForX.clear();
      allEmptyWinningCombinationsThatCanBeUsed = allEmptyWinningCombinationsCreator.createAllEmptyWinningCombinationsThatCanBeUsed();
   }

   @Deprecated
   String getBoardAsString()
   {
      StringBuilder boardAsString = new StringBuilder();

      boardAsString.append(" ");

      for (int i = 0; i < boardLenght; i++)
      {
         boardAsString.append(i + "|");
      }
      boardAsString.append("\n");

      int rowNumber = 0;
      boardAsString.append(rowNumber);

      for (int i = 0; i < boardSize; i++)
      {

         if (i % boardLenght == 0 && i != 0)
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

   boolean isWinningMove(Sign sign, Integer boardPosition) throws FieldIsNotEmptyException, IllegalSignException
   {
      logger.info("Loguję postawiony znak" + sign);

      if (!putSignIntoBoard(sign, boardPosition))
      {
         throw new FieldIsNotEmptyException(FIELD_IS_NOT_EMPTY_EXCEPTION);
      }

      createHypotheticalWinningFields(sign, boardPosition);

      return isWinnerAtThisMove(sign);

   }

   boolean putSignIntoBoard(Sign sign, Integer boardPosition)
   {

      if (board.get(boardPosition) == Sign.EMPTY)
      {
         board.remove(boardPosition.intValue());
         board.add(boardPosition, sign);

         return true;
      }

      return false;
   }

   private boolean isWinnerAtThisMove(Sign sign) throws IllegalSignException
   {
      AtomicInteger howManySucces = new AtomicInteger(0);
      if (sign == Sign.O)
      {
         for (String keysOfFieldsToCheck : hypotheticalWinningFieldsForO.keySet())
         {
            List<Integer> fieldsToCheck = hypotheticalWinningFieldsForO.get(keysOfFieldsToCheck);
            fieldsToCheck.forEach(index -> {

               if (board.get(index) == sign)
               {
                  howManySucces.getAndIncrement();
               }
            });
            if (howManySucces.get() == winningSize)
            {
               return true;
            }
            howManySucces.set(0);
         }
      }
      else if (sign == Sign.X)
      {
         {
            for (String keysOfFieldsToCheck : hypotheticalWinningFieldsForX.keySet())
            {
               List<Integer> fieldsToCheck = hypotheticalWinningFieldsForX.get(keysOfFieldsToCheck);
               fieldsToCheck.forEach(index -> {
                  if (board.get(index) == sign)
                  {
                     howManySucces.getAndIncrement();
                  }
               });
               if (howManySucces.get() == winningSize)
               {
                  return true;
               }
               howManySucces.set(0);
            }
         }

      }
      else
      {
         throw new IllegalSignException(ILLEGAL_SIGN_EXCEPTION_MESSAGE);
      }
      return false;
   }

   private void createHypotheticalWinningFields(Sign sign, Integer boardPosition)
   {
      HypotheticalWinningFieldsCreator hypotheticalWinningFieldsCreator =
            HypotheticalWinningFieldsCreator.builder()
                  .sign(sign)
                  .winningSize(winningSize)
                  .x(boardLenght)
                  .positionOnBoard(boardPosition)
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
         if (hypotheticalWinningFieldsForX.containsKey(key))
         {
            hypotheticalWinningFieldsForX.remove(key);
            return;
         }
         if (allEmptyWinningCombinationsThatCanBeUsed.containsKey(key))
         {
            hypotheticalWinningFieldsForO.put(key, value);
            allEmptyWinningCombinationsThatCanBeUsed.remove(key);
         }
         return;
      }

      if (sign == Sign.X)
      {
         if (hypotheticalWinningFieldsForO.containsKey(key))
         {
            hypotheticalWinningFieldsForO.remove(key);
            return;
         }

         if (allEmptyWinningCombinationsThatCanBeUsed.containsKey(key))
         {
            hypotheticalWinningFieldsForX.put(key, value);
            allEmptyWinningCombinationsThatCanBeUsed.remove(key);
         }
      }

   }

   //remis
   boolean isDraw()
   {
      if (hypotheticalWinningFieldsForX.isEmpty() && hypotheticalWinningFieldsForO.isEmpty() && allEmptyWinningCombinationsThatCanBeUsed.isEmpty())
      {
         return true;
      }
      return false;
   }

   ///////////////////   BUILDER  //////////////////////////

   @SuppressWarnings("NewMethodNamingConvention")
   static final class BoardBuilder implements NeedBoardSize, NeedBoardLenght, NeedWinningSize, CanBeBuild
   {
      private Integer boardSize;
      private Integer boardLenght;
      private Integer winningSize;

      @Override
      public NeedBoardLenght boardSize(Integer boardSize)
      {
         this.boardSize = boardSize;
         return this;
      }

      @Override
      public NeedWinningSize boardLenght(Integer boardLenght)
      {
         this.boardLenght = boardLenght;
         return this;
      }

      @Override
      public CanBeBuild winningSize(Integer winningSize)
      {
         this.winningSize = winningSize;
         return this;
      }

      @Override
      public Board build() throws IllegalArgumentException
      {

         if (boardSize < 9 || boardLenght < 3)
         {

            throw new IllegalArgumentException();
         }

         //todo sprecyzować wyjątek
         if (boardSize % boardLenght != 0)
         {
            throw new IllegalArgumentException();
         }

         Board toBuild = new Board(boardSize, boardLenght, winningSize);

         return toBuild;
      }

   }

   @SuppressWarnings("NewMethodNamingConvention")
   interface NeedBoardSize
   {
      NeedBoardLenght boardSize(Integer boardSize);
   }

   @SuppressWarnings("NewMethodNamingConvention")
   interface NeedBoardLenght
   {
      NeedWinningSize boardLenght(Integer boardLenght);
   }

   interface NeedWinningSize
   {
      CanBeBuild winningSize(Integer winningSize);
   }

   interface CanBeBuild
   {
      Board build() throws IllegalArgumentException;
   }

   static NeedBoardSize builder() throws IllegalArgumentException
   {
      return new BoardBuilder();
   }
}
