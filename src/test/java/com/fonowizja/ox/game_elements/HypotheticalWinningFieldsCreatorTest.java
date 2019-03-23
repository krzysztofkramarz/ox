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
            { Sign.O, 3, 6, 16, 24, Arrays.asList(Arrays.asList(4, 10, 16), Arrays.asList(10, 16, 22)) },
            { Sign.O, 3, 6, 23, 24, Arrays.asList(Arrays.asList(11, 17, 23)) }
      };
   }

   @Test(dataProvider = "verticalFields")
   public void testVerticalFields(Sign sign, Integer winningSize, Integer widthBoad, Integer positionInBoarrd, Integer boardSize,
         List<List<Integer>> expect)

   {
      HypotheticalWinningFieldsCreator hypotheticalWinningFieldsCreator =
            HypotheticalWinningFieldsCreator.builder().sign(sign).winningSize(winningSize).x(widthBoad).positionInBoard(positionInBoarrd)
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
            { Sign.O, 3, 6, 16, 24, Arrays.asList(Arrays.asList(14, 15, 16), Arrays.asList(15, 16, 17)) },
            { Sign.O, 3, 6, 14, 24, Arrays.asList(Arrays.asList(12, 13, 14), Arrays.asList(13, 14, 15), Arrays.asList(14, 15, 16)) }
      };
   }

   @Test(dataProvider = "horiontalFields")
   public void testHoriontalFields(Sign sign, Integer winningSize, Integer widthBoad, Integer positionInBoarrd, Integer boardSize,
         List<List<Integer>> expect)

   {
      HypotheticalWinningFieldsCreator hypotheticalWinningFieldsCreator =
            HypotheticalWinningFieldsCreator.builder().sign(sign).winningSize(winningSize).x(widthBoad).positionInBoard(positionInBoarrd)
                  .boardSize(boardSize).build();
      List<List<Integer>> result = hypotheticalWinningFieldsCreator.createHorizontalWinnerFields();

      assertThat(expect).isEqualTo(result);
   }





















   @DataProvider(name = "slashFields")
   public Object[][] slashFields()
   {
      return new Object[][] {
            { Sign.O, 3, 6, 7, 24, Arrays.asList(Arrays.asList(2, 7, 12)) },
            { Sign.O, 3, 6, 6, 24, Arrays.asList()},
            { Sign.O, 3, 6, 16, 24, Arrays.asList(Arrays.asList(11,16,21))},
            { Sign.O, 3, 6, 14, 24, Arrays.asList(Arrays.asList(4,9,14), Arrays.asList(9,14,19)) },
            { Sign.O, 3, 6, 23, 24, Arrays.asList()}

      };
   }

   @Test(dataProvider = "slashFields")
   public void testSlashFields(Sign sign, Integer winningSize, Integer widthBoad, Integer positionInBoarrd, Integer boardSize,
         List<List<Integer>> expect)

   {
      HypotheticalWinningFieldsCreator hypotheticalWinningFieldsCreator =
            HypotheticalWinningFieldsCreator.builder().sign(sign).winningSize(winningSize).x(widthBoad).positionInBoard(positionInBoarrd)
                  .boardSize(boardSize).build();
      List<List<Integer>> result = hypotheticalWinningFieldsCreator.createSlashWinnerFields();

      assertThat(expect).isEqualTo(result);
   }

}