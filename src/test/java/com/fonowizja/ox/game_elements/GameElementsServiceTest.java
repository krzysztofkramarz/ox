
package com.fonowizja.ox.game_elements;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author krzysztof.kramarz
 */
public class GameElementsServiceTest
{

   @DataProvider(name = "boardAsString")
   public Object[][] boardAsString()
   {
      return new Object[][] {
            { " |", 1, 1 },
            { " | |", 2, 1 },
            { " | | |", 3, 1 },
            { " | | | |", 4, 1 },
            { " | | | | |", 5, 1 },
            { " |\n |", 1, 2 },
            { " |\n |\n |", 1, 3 },
            { " |\n |\n |\n |", 1, 4 },
            { " |\n |\n |\n |\n |", 1, 5 },
            { " |\n |\n |\n |\n |\n |", 1, 6 },
            { " | |\n | |", 2, 2 },
            { " | | |\n | | |", 3, 2 },
            { " | | | |\n | | | |", 4, 2 },
            { " | | | | |\n | | | | |", 5, 2 },
            { " | | | | | |\n | | | | | |", 6, 2 },
            { " | | | | | | | | | | |\n | | | | | | | | | | |\n | | | | | | | | | | |", 11, 3 },
            { " | | | | | | | | | | | | | | | | |\n | | | | | | | | | | | | | | | | |\n | | | | | | | | | | | | | | | | |", 17, 3 },
            { " | | | | | | | | | | | | | | | | | | | | |\n | | | | | | | | | | | | | | | | | | | | |\n | | | | | | | | | | | | | | | | | | | | |",
                  21,
                  3 },
            { " | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n | | | | | | | | | | | | | | | | | | | | | | | | | | | | |"
                  + " | | | | |\n | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |", 34, 3 },
            { " | | |\n | | |\n | | |", 3, 3 },
            { " | | | |\n | | | |\n | | | |\n | | | |", 4, 4 },
            { " | | | | |\n | | | | |\n | | | | |\n | | | | |\n | | | | |", 5, 5 },
            { " | | | | | |\n | | | | | |\n | | | | | |\n | | | | | |\n | | | | | |\n | | | | | |", 6, 6 },
            { " | | | | | | |\n | | | | | | |\n | | | | | | |\n | | | | | | |\n | | | | | | |\n | | | | | | |\n | | | | | | |", 7, 7 }

      };
   }

   @Test(dataProvider = "boardAsString")
   public void testGetBoardAsEmptyString(String expectedBoardAsString, Integer x, Integer y)
   {

      Board board = Board.builder().xxx(x).yyy(y).build();
      assertThat(board.getBoardAsString()).isEqualTo(expectedBoardAsString);

   }

   @DataProvider(name = "boardThrowsException")
   public Object[][] boardThrowsException()
   {
      return new Object[][] {
            { 0, 0 },
            { 1, 0 },
            { 5, 0 },
            { 7, 0 },
            { 91, 0 },
            { 254, 0 },
            { 0, 1 },
            { 0, 5 },
            { 0, 7 },
            { 0, 91 },
            { 0, 254 }
      };
   }

   @Test(dataProvider = "boardThrowsException")
   public void testGetBoardThrowsException(Integer x, Integer y)
   {

      assertThatThrownBy(() -> Board.builder().xxx(x).yyy(y).build())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(Board.EXCEPTION_MESSAGE);

   }
}
