package com.fonowizja.ox.game_elements;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author krzysztof.kramarz
 */
public class HypotheticalWinningFieldsCreatorTest
{

   @DataProvider(name = "verticalFields")
   public Object[][] verticalFields()
   {
      return new Object[][] {
            { Sign.O, 3, 6, 7, 24, Arrays.asList(Arrays.asList(1, 7, 13), Arrays.asList(7, 13, 19)) },
            { Sign.O, 3, 6, 14, 24, Arrays.asList(Arrays.asList(2, 8, 14), Arrays.asList(8, 14, 20)) },
            { Sign.O, 3, 6, 0, 24, Arrays.asList(Arrays.asList(0, 6, 12)) },
            { Sign.O, 3, 6, 16, 24, Arrays.asList(Arrays.asList(4, 10, 16), Arrays.asList(10, 16, 22)) },
            { Sign.O, 3, 6, 17, 24, Arrays.asList(Arrays.asList(5, 11, 17), Arrays.asList(11, 17, 23)) },
            { Sign.O, 3, 6, 23, 24, Arrays.asList(Arrays.asList(11, 17, 23)) },
            { Sign.O, 3, 6, 18, 24, Arrays.asList(Arrays.asList(6, 12, 18)) }
      };
   }

   @Test(dataProvider = "verticalFields")
   public void testVerticalFields(Sign sign, Integer winningSize, Integer widthBoad, Integer positionInBoarrd, Integer boardSize,
         List<List<Integer>> expect)

   {
      HypotheticalWinningFieldsCreator hypotheticalWinningFieldsCreator =
            HypotheticalWinningFieldsCreator.builder().sign(sign).winningSize(winningSize).x(widthBoad).positionOnBoard(positionInBoarrd)
                  .boardSize(boardSize).build();
      List<List<Integer>> result = hypotheticalWinningFieldsCreator.createVerticalWinnerFields();

      assertThat(expect).isEqualTo(result);
   }

   @DataProvider(name = "horiontalFields")
   public Object[][] horizontalFields()
   {
      return new Object[][] {
            { Sign.O, 3, 6, 7, 24, Arrays.asList(Arrays.asList(6, 7, 8), Arrays.asList(7, 8, 9)) },
            { Sign.O, 3, 6, 17, 24, Arrays.asList(Arrays.asList(15, 16, 17)) },
            { Sign.O, 3, 6, 18, 24, Arrays.asList(Arrays.asList(18, 19, 20)) },
            { Sign.O, 3, 6, 23, 24, Arrays.asList(Arrays.asList(21, 22, 23)) },
            { Sign.O, 3, 6, 23, 24, Arrays.asList(Arrays.asList(21, 22, 23)) },
            { Sign.O, 3, 6, 21, 24, Arrays.asList(Arrays.asList(19, 20, 21), Arrays.asList(20, 21, 22), Arrays.asList(21, 22, 23)) },
            { Sign.O, 3, 6, 16, 24, Arrays.asList(Arrays.asList(14, 15, 16), Arrays.asList(15, 16, 17)) },
            { Sign.O, 3, 6, 14, 24, Arrays.asList(Arrays.asList(12, 13, 14), Arrays.asList(13, 14, 15), Arrays.asList(14, 15, 16)) }
      };
   }

   @Test(dataProvider = "horiontalFields")
   public void testHoriontalFields(Sign sign, Integer winningSize, Integer widthBoad, Integer positionInBoarrd, Integer boardSize,
         List<List<Integer>> expect)

   {
      HypotheticalWinningFieldsCreator hypotheticalWinningFieldsCreator =
            HypotheticalWinningFieldsCreator.builder().sign(sign).winningSize(winningSize).x(widthBoad).positionOnBoard(positionInBoarrd)
                  .boardSize(boardSize).build();
      List<List<Integer>> result = hypotheticalWinningFieldsCreator.createHorizontalWinnerFields();

      assertThat(expect).isEqualTo(result);
   }

   @DataProvider(name = "slashFields")
   public Object[][] slashFields()
   {
      return new Object[][] {
            { Sign.O, 3, 6, 7, 24, Arrays.asList(Arrays.asList(2, 7, 12)) },
            { Sign.O, 3, 6, 6, 24, Arrays.asList() },
            { Sign.O, 3, 6, 16, 24, Arrays.asList(Arrays.asList(11, 16, 21)) },
            { Sign.O, 3, 6, 14, 24, Arrays.asList(Arrays.asList(4, 9, 14), Arrays.asList(9, 14, 19)) },
            { Sign.O, 3, 6, 23, 24, Arrays.asList() },
            { Sign.O, 3, 6, 13, 24, Arrays.asList(Arrays.asList(3, 8, 13), Arrays.asList(8, 13, 18)) },
            { Sign.O, 3, 6, 18, 24, Arrays.asList(Arrays.asList(8, 13, 18)) },
            { Sign.O, 3, 6, 0, 24, Arrays.asList() },
            { Sign.O, 3, 6, 1, 24, Arrays.asList() },
            { Sign.O, 3, 6, 22, 24, Arrays.asList() },
            { Sign.O, 3, 6, 17, 24, Arrays.asList() },

      };
   }

   @Test(dataProvider = "slashFields")
   public void testSlashFields(Sign sign, Integer winningSize, Integer widthBoad, Integer positionInBoarrd, Integer boardSize,
         List<List<Integer>> expect)

   {
      HypotheticalWinningFieldsCreator hypotheticalWinningFieldsCreator =
            HypotheticalWinningFieldsCreator.builder().sign(sign).winningSize(winningSize).x(widthBoad).positionOnBoard(positionInBoarrd)
                  .boardSize(boardSize).build();
      List<List<Integer>> result = hypotheticalWinningFieldsCreator.createSlashWinnerFields();

      assertThat(expect).isEqualTo(result);
   }

   @DataProvider(name = "backSlashFields")
   public Object[][] backSlashFields()
   {
      return new Object[][] {
            { Sign.O, 3, 6, 7, 24, Arrays.asList(Arrays.asList(0, 7, 14), Arrays.asList(7, 14, 21)) },
            { Sign.O, 3, 6, 16, 24, Arrays.asList(Arrays.asList(2, 9, 16), Arrays.asList(9, 16, 23)) },
            { Sign.O, 3, 6, 11, 24, Arrays.asList() },
            { Sign.O, 3, 6, 5, 24, Arrays.asList() },
            { Sign.O, 3, 6, 18, 24, Arrays.asList() },
            { Sign.O, 3, 6, 12, 24, Arrays.asList() },
            { Sign.O, 3, 6, 19, 24, Arrays.asList() },
            { Sign.O, 3, 6, 20, 24, Arrays.asList(Arrays.asList(6, 13, 20)) },
            { Sign.O, 3, 6, 6, 24, Arrays.asList(Arrays.asList(6, 13, 20)) },
            { Sign.O, 3, 6, 13, 24, Arrays.asList(Arrays.asList(6, 13, 20)) },
            { Sign.O, 3, 6, 14, 24, Arrays.asList(Arrays.asList(0, 7, 14), Arrays.asList(7, 14, 21)) }
      };
   }

   @Test(dataProvider = "backSlashFields")
   public void testBackSlashFields(Sign sign, Integer winningSize, Integer widthBoad, Integer positionInBoarrd, Integer boardSize,
         List<List<Integer>> expect)

   {
      HypotheticalWinningFieldsCreator hypotheticalWinningFieldsCreator =
            HypotheticalWinningFieldsCreator.builder().sign(sign).winningSize(winningSize).x(widthBoad).positionOnBoard(positionInBoarrd)
                  .boardSize(boardSize).build();
      List<List<Integer>> result = hypotheticalWinningFieldsCreator.createBackSlashWinnerFields();

      assertThat(expect).isEqualTo(result);
   }

}