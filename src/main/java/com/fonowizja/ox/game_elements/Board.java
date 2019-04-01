package com.fonowizja.ox.game_elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.Min;

import com.fonowizja.ox.game.BoardObservator;
import lombok.AccessLevel;
import lombok.Getter;

/**
 * Place for acting single play
 *
 * @author krzysztof.kramarz
 */
final class Board
{
   //TODO uproscic pola, kilka niepotrzebnych lub redundantnych

   static final String EXCEPTION_MESSAGE = "boardSize to small";
   static final String EXCEPTION_WINNING_SIZE_MESSAGE = "winning size to small";
   private static final String FIELD_IS_NOT_EMPTY_EXCEPTION = "Field is not empty!";
   private final List<Sign> board = new ArrayList<>();
   @Min(value = 9, message = EXCEPTION_MESSAGE)
   private final Integer boardSize;
   @Min(value = 3, message = EXCEPTION_MESSAGE)
   private final Integer boardLenght;
   @Min(value = 3, message = EXCEPTION_WINNING_SIZE_MESSAGE)
   private final Integer winningSize;

   //todo
   private BoardObservator boardObservator;

   @Getter(AccessLevel.PACKAGE)
   private final Map<String, List<Integer>> hypotheticalWinningFieldsXXX = new HashMap<>();
   @Getter(AccessLevel.PACKAGE)
   private final Map<String, List<Integer>> hypotheticalWinningFieldsOOO = new HashMap<>();
   @Getter(AccessLevel.PACKAGE)
   private final Map<String, List<Integer>> allWinningFields;

   //todo
   private HypotheticalWinningFieldsCreator createHorizontalWinnerFields;

   private Board(Integer boardSize, Integer boardLenght, Integer winningSize)
   {
      this.boardSize = boardSize;
      this.boardLenght = boardLenght;
      this.winningSize = winningSize;
      AllWinningFieldsCreator allWinningFieldsCreator = new AllWinningFieldsCreator(boardSize, boardLenght, winningSize);
      allWinningFields = allWinningFieldsCreator.createAllWinningFields();
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

   boolean isWinningMove(Sign sign, Integer boardPosition) throws Exception
   {

      if (!putSignIntoBoard(sign, boardPosition))
      {
         throw new FieldIsNotEmptyException(FIELD_IS_NOT_EMPTY_EXCEPTION);
      }

      createHypotheticalWinningFields(sign, boardPosition);

      //todo
      // boardObservator.notifyAboutWinner(sign);

      return checkWinnerAtThisMove(sign);

   }

   boolean putSignIntoBoard(Sign sign, Integer boardPosition)
   {

      if (board.get(boardPosition) == Sign.EMPTY)
      {
         board.add(boardPosition, sign);

         return true;
      }

      return false;
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

   private void createHypotheticalWinningFields(Sign sign, Integer boardPosition)
   {
      HypotheticalWinningFieldsCreator hypotheticalWinningFieldsCreator =
            HypotheticalWinningFieldsCreator.builder()
                  .sign(sign)
                  .winningSize(winningSize)
                  .x(boardLenght)
                  .positionInBoard(boardPosition)
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
         if (allWinningFields.containsKey(key))
         {
            hypotheticalWinningFieldsOOO.put(key, value);
            allWinningFields.remove(key);
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

         if (allWinningFields.containsKey(key))
         {
            hypotheticalWinningFieldsXXX.put(key, value);
            allWinningFields.remove(key);
         }
      }

   }

   //remis
   boolean isDraw()
   {
      if (hypotheticalWinningFieldsXXX.isEmpty() && hypotheticalWinningFieldsOOO.isEmpty() && allWinningFields.isEmpty())
      {
         return true;
      }
      return false;
   }

   ///////////////////   BUILDER  //////////////////////////

   @SuppressWarnings("NewMethodNamingConvention")
   static final class BoardBuilder implements NeedBoardSize, NeedBoardLenght, NeedWinningSize, CanBeBuild
   {
      @Min(value = 9, message = EXCEPTION_MESSAGE)
      private Integer boardSize;
      @Min(value = 3, message = EXCEPTION_MESSAGE)
      private Integer boardLenght;
      @Min(value = 3, message = EXCEPTION_WINNING_SIZE_MESSAGE)
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

         Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
         Set<ConstraintViolation<BoardBuilder>> validate = validator.validate(this);
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
