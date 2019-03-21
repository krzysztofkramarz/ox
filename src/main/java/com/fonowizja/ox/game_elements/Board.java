package com.fonowizja.ox.game_elements;

import java.util.ArrayList;
import java.util.List;

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
   private Integer numberOfWiningSigns;

   private Board(Integer x, Integer y, Integer numberOfWiningSigns) throws IllegalArgumentException
   {
      if (x == 0 || y == 0)
      {
         throw new IllegalArgumentException(EXCEPTION_MESSAGE);
      }
      this.x = x;
      this.y = y;
      boardSize = x * y;
      this.numberOfWiningSigns = numberOfWiningSigns;
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
      int positionInBoard = howManyFullRows * x + positionInRow;

      if (board.get(positionInBoard) == Sign.EMPTY)
      {
         board.add(positionInBoard, sign);
         return true;
      }
      return false;
   }

   static final class BoardBuilder // implements XXX, YYY, CanBeBuild
   {
      private Integer x;
      private Integer y;
      private Integer numberOfWiningSigns;

      BoardBuilder xxx(Integer x)
      {
         this.x = x;
         return this;
      }

      BoardBuilder yyy(Integer y)
      {
         this.y = y;
         return this;
      }

      BoardBuilder numberOfWiningSigns(Integer numberOfWiningSigns)
      {
         this.numberOfWiningSigns = numberOfWiningSigns;
         return this;
      }

      Board build()
      {
         Board board = new Board(x, y, numberOfWiningSigns);
         return board;
      }

   }

   // private interface XXX
   // {
   //    YYY xxx(Integer x);
   // }
   //
   // private interface YYY
   // {
   //    CanBeBuild yyy(Integer y);
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
