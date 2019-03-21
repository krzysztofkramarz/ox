package com.fonowizja.ox.game_elements;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author krzysztof.kramarz
 */
public class BoardTest
{
   Board board = Board.builder().xxx(5).yyy(5).build();

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
      board.setEmptyBoard();
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
      board.setEmptyBoard();
      board.putSignIntoBoard(sign, x, y);
      boolean result = board.putSignIntoBoard(sign, x1, y2);
      assertThat(result).isFalse();
   }

}
