
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
            { " 0|\n0 |", 1, 1 },
            { " 0|1|\n0 | |", 2, 1 },
            { " 0|1|2|\n0 | | |", 3, 1 },
            { " 0|1|2|3|\n0 | | | |", 4, 1 },
            { " 0|1|2|3|4|\n0 | | | | |", 5, 1 },
            { " 0|\n0 |\n1 |", 1, 2 },
            { " 0|\n0 |\n1 |\n2 |", 1, 3 },
            { " 0|\n0 |\n1 |\n2 |\n3 |", 1, 4 },
            { " 0|\n0 |\n1 |\n2 |\n3 |\n4 |", 1, 5 },
            { " 0|\n0 |\n1 |\n2 |\n3 |\n4 |\n5 |", 1, 6 },
            { " 0|1|\n0 | |\n1 | |", 2, 2 },
            { " 0|1|2|\n0 | | |\n1 | | |", 3, 2 },
            { " 0|1|2|3|\n0 | | | |\n1 | | | |", 4, 2 },
            { " 0|1|2|3|4|\n0 | | | | |\n1 | | | | |", 5, 2 },
            { " 0|1|2|3|4|5|\n0 | | | | | |\n1 | | | | | |", 6, 2 },
            { " 0|1|2|3|4|5|6|7|8|9|10|\n0 | | | | | | | | | | |\n1 | | | | | | | | | | |\n2 | | | | | | | | | | |", 11, 3 },
            { " 0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|\n0 | | | | | | | | | | | | | | | | |\n1 | | | | | | | | | | | | | | | | |\n2 | | | | | | | | | | | | | | | | |", 17, 3 },
            { " 0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|\n0 | | | | | | | | | | | | | | | | | | | | |\n1 | | | | | | | | | | | | | | | | | | | | |\n2 | | | | | | | | | | | | | | | | | | | | |",
                  21,
                  3 },
            { " 0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|33|\n0 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n1 | | | | | | | | | | | | | | | | | | | | | | | | | | | | |"
                  + " | | | | |\n2 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |", 34, 3 },
            { " 0|1|2|\n0 | | |\n1 | | |\n2 | | |", 3, 3 },
            { " 0|1|2|3|\n0 | | | |\n1 | | | |\n2 | | | |\n3 | | | |", 4, 4 },
            { " 0|1|2|3|4|\n0 | | | | |\n1 | | | | |\n2 | | | | |\n3 | | | | |\n4 | | | | |", 5, 5 },
            { " 0|1|2|3|4|5|\n0 | | | | | |\n1 | | | | | |\n2 | | | | | |\n3 | | | | | |\n4 | | | | | |\n5 | | | | | |", 6, 6 },
            { " 0|1|2|3|4|5|6|\n0 | | | | | | |\n1 | | | | | | |\n2 | | | | | | |\n3 | | | | | | |\n4 | | | | | | |\n5 | | | | | | |\n6 | | | | | | |", 7, 7 }

      };
   }

   @Test(dataProvider = "boardAsString")
   public void testGetBoardAsEmptyString(String expectedBoardAsString, Integer x, Integer y)
   {

      Board board = Board.builder().x(x).y(y).winningSize(3).build();
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

      assertThatThrownBy(() -> Board.builder().x(x).y(y).winningSize(3).build())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(Board.EXCEPTION_MESSAGE);

   }


}
