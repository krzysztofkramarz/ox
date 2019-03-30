
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
            { " 0|1|2|3|4|\n0 | | | | |\n1 | | | | |", 10,5 },
            { " 0|1|2|3|4|5|\n0 | | | | | |\n1 | | | | | |", 12,6 },
            { " 0|1|2|3|4|5|6|7|8|9|10|\n0 | | | | | | | | | | |\n1 | | | | | | | | | | |\n2 | | | | | | | | | | |", 33,11 },
            { " 0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|\n0 | | | | | | | | | | | | | | | | |\n1 | | | | | | | | | | | | | | | | |\n2 | | | | | | | | | | | | | | | | |",
                  51, 17 },
            { " 0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|\n0 | | | | | | | | | | | | | | | | | | | | |\n1 | | | | | | | | | | | | | | | | | | | | |\n2 | | | | | | | | | | | | | | | | | | | | |",
                  63,21 },
            { " 0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|33|\n0 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n1 | | | | | | | | | | | | | | | | | | | | | | | | | | | | |"
                  + " | | | | |\n2 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |", 102, 34 },
            { " 0|1|2|\n0 | | |\n1 | | |\n2 | | |", 9, 3 },
            { " 0|1|2|3|\n0 | | | |\n1 | | | |\n2 | | | |\n3 | | | |", 16, 4 },
            { " 0|1|2|3|4|\n0 | | | | |\n1 | | | | |\n2 | | | | |\n3 | | | | |\n4 | | | | |", 25, 5 },
            { " 0|1|2|3|4|5|\n0 | | | | | |\n1 | | | | | |\n2 | | | | | |\n3 | | | | | |\n4 | | | | | |\n5 | | | | | |", 36, 6 },
            { " 0|1|2|3|4|5|6|\n0 | | | | | | |\n1 | | | | | | |\n2 | | | | | | |\n3 | | | | | | |\n4 | | | | | | |\n5 | | | | | | |\n6 | | | | | | |",
                  49, 7 }

      };
   }

   @Test(dataProvider = "boardAsString")
   public void testGetBoardAsEmptyString(String expectedBoardAsString, Integer boardSize, Integer boardLength)
   {

      Board board = Board.builder().boardSize(boardSize).boardLenght(boardLength).winningSize(3).build();
      assertThat(board.getBoardAsString()).isEqualTo(expectedBoardAsString);

   }

   @DataProvider(name = "boardThrowsException")
   public Object[][] boardThrowsException()
   {
      return new Object[][] {
            { 0, 0 },
            { 9, 2 },
            { 8, 3 },
            { 11,4 },
            { 91, 3 },
            { 3, 3 }
      };
   }

   @Test(dataProvider = "boardThrowsException")
   public void testGetBoardThrowsException(Integer boardSize, Integer boardLenght)
   {

      assertThatThrownBy(() -> Board.builder().boardSize(boardSize).boardLenght(boardLenght).winningSize(3).build())
            .isInstanceOf(IllegalArgumentException.class);


   }


   @DataProvider(name = "boardIsWellDone")
   public Object[][] boardIsWellDone()
   {
      return new Object[][] {
            { 12,4 },
            { 9, 3 },
            { 24,4 },
            { 15,5},
            { 27, 3 }
      };
   }

   @Test(dataProvider = "boardIsWellDone")
   public void testBoardIsWellDone(Integer boardSize, Integer boardLenght)
   {
      Board.builder().boardSize(boardSize).boardLenght(boardLenght).winningSize(3).build();


   }

   @Test
         public void testCleanBoard() throws Exception
   {

      Board toTest = Board.builder().boardSize(16).boardLenght(4).winningSize(3).build();
      toTest.isWinningMove(Sign.X,0);
      toTest.cleanBoard();
      toTest.isWinningMove(Sign.X, 0);
   }

   @Test
   public void testExceptionWhenSignAddedOnBusyField() throws Exception
   {

      Board toTest = Board.builder().boardSize(16).boardLenght(4).winningSize(3).build();
      toTest.isWinningMove(Sign.X,0);

      assertThatThrownBy(()->toTest.isWinningMove(Sign.X, 0)).isInstanceOf(Exception.class);
   }

}
