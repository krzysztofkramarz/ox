package com.fonowizja.ox.game_elements;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.function.IntUnaryOperator;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author krzysztof.kramarz
 */
@SuppressWarnings("LocalVariableHidesMemberVariable")
public class BoardTest
{
   Board board = Board.builder().x(5).y(5).winningSize(3).build();

   @Test
   public void testSetSize()
   {
      int expectedSize = 25;

      // assertThat()  n//TODO nie ma co testować, bo Board hermetyzuje dostep do tablicy
   }

   @DataProvider(name = "putSighnInEmptySpaces")
   public Object[][] putSighnInEmptySpaces()
   {
      return new Object[][] {
            { Sign.X, 0, 0 },
            { Sign.X, 1, 0 },
            { Sign.X, 2, 0 },
            { Sign.O, 3, 0 },
            { Sign.O, 4, 0 },
            { Sign.X, 0, 1 },
            { Sign.X, 1, 3 },
            { Sign.X, 2, 4 },
            { Sign.O, 4, 2 },
            { Sign.O, 4, 4 }
      };
   }

   @Test(dataProvider = "putSighnInEmptySpaces")
   public void testPutSignIntoBoard(Sign sign, Integer x, Integer y)
   {
      board.cleanBoard();
      boolean result = board.putSignIntoBoard(sign, x, y);
      assertThat(result).isTrue();
   }

   @DataProvider(name = "putSighnInNotAllowedSpaces")
   public Object[][] putSighnInNotAllowedSpaces()
   {
      return new Object[][] {
            { Sign.X, 0, 0, 0, 0 },
            { Sign.X, 1, 1, 1, 1 },
            { Sign.X, 2, 2, 2, 2 },
            { Sign.O, 2, 1, 2, 1 },
            { Sign.O, 4, 0, 4, 0 },
            { Sign.X, 3, 2, 3, 2 },
            { Sign.X, 4, 4, 4, 4 }
      };
   }

   @Test(dataProvider = "putSighnInNotAllowedSpaces")
   public void testPutSighnInNotAllowedSpaces(Sign sign, Integer x, Integer y, Integer x1, Integer y2)
   {
      board.cleanBoard();
      board.putSignIntoBoard(sign, x, y);
      boolean result = board.putSignIntoBoard(sign, x1, y2);
      assertThat(result).isFalse();
   }



   @DataProvider(name = "horizontalWinnerFields")
   public Object[][] horizontalWinnerFields()
   {
      return new Object[][] {
            { 5, 5, 3, 1, Arrays.asList(1, 2, 3) },
            { 5, 5, 5, 1, Arrays.asList(1, 2, 3, 4, 5) },
            { 20, 20, 4, 21, Arrays.asList(21, 22, 23, 24) },
            { 9, 9, 4, 6, Arrays.asList(6, 7, 8, 9) },
      };

   }



}
