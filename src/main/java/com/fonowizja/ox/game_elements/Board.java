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

   private void setSize(Integer x, Integer y) throws IllegalArgumentException
   {
      if (x == 0 || y == 0)
      {
         throw new IllegalArgumentException(EXCEPTION_MESSAGE);
      }
      this.x = x;
      this.y = y;
      boardSize = x * y;
      board = new ArrayList<>(boardSize);
      setEmptyBoard();
   }

   private void setEmptyBoard()
   {
      for (int i = 0; i < boardSize; i++)
      {
         board.add(Sign.EMPTY);
      }
   }

   String getBoardAsString()
   {
      // StringJoiner stringJoiner = new StringJoiner();
      StringBuilder boardAsString = new StringBuilder();
      for (int i = 0; i < boardSize; i++)
      {
         if (i % x == 0 && i != 0)
         {
            boardAsString.append("\n");
         }

         boardAsString.append(board.get(i).getSign());
         boardAsString.append("|");

      }
      return boardAsString.toString();
   }

   static final class BoardBuilder // implements XXX, YYY, CanBeBuild
   {
      private Integer x;
      private Integer y;

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

      Board build()
      {
         Board board = new Board();
         board.setSize(x, y);
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
