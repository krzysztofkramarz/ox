package com.fonowizja.ox.game_elements;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author krzysztof.kramarz
 */
public class AllWinningFieldsCreatorTest
{

   @DataProvider
   public Object[][] getBoard1()
   {
      return new Object[][] {
            { Arrays.asList(Arrays.asList(0, 1, 2), Arrays.asList(1, 2, 3), Arrays.asList(2, 3, 4), Arrays.asList(3, 4, 5),
                  Arrays.asList(4, 5, 6), Arrays.asList(7, 8, 9), Arrays.asList(8, 9, 10), Arrays.asList(9, 10, 11), Arrays.asList(10, 11, 12),
                  Arrays.asList(11, 12, 13), Arrays.asList(14, 15, 16), Arrays.asList(15, 16, 17), Arrays.asList(16, 17, 18),
                  Arrays.asList(17, 18, 19), Arrays.asList(18, 19, 20), Arrays.asList(2, 8, 14), Arrays.asList(3, 9, 15),
                  Arrays.asList(4, 10, 16), Arrays.asList(5, 11, 17), Arrays.asList(6, 12, 18), Arrays.asList(4, 12, 20), Arrays.asList(3, 11, 19),
                  Arrays.asList(2, 10, 18), Arrays.asList(1, 9, 17), Arrays.asList(0, 8, 16))
            }

      };
   }

   @Test(dataProvider = "getBoard1")
   public void testCreateAllWinningFields1(List<List<Integer>> expect)
   {
      Map<String, List<Integer>> expectWinningFields = new HashMap<>();

      for (List<Integer> all : expect)
      {
         expectWinningFields.put(all.toString(), all);
      }
      AllWinningFieldsCreator allWinningFieldsCreator = new AllWinningFieldsCreator(7, 21, 3);

      Map<String, List<Integer>> resultWinningFields = allWinningFieldsCreator.createAllWinningFields();

      assertThat(resultWinningFields).containsAllEntriesOf(expectWinningFields);
   }

   @DataProvider
   public Object[][] getBoard2()
   {
      return new Object[][] {
            { Arrays.asList(Arrays.asList(0, 1, 2), Arrays.asList(3, 4, 5), Arrays.asList(6, 7, 8), Arrays.asList(0, 3, 6),
                  Arrays.asList(1, 4, 7), Arrays.asList(2, 5, 8), Arrays.asList(2, 4, 6), Arrays.asList(0, 4, 8))
            }

      };
   }

   @Test(dataProvider = "getBoard2")
   public void testCreateAllWinningFields3(List<List<Integer>> expect)
   {
      Map<String, List<Integer>> expectWinningFields = new HashMap<>();

      for (List<Integer> all : expect)
      {
         expectWinningFields.put(all.toString(), all);
      }
      AllWinningFieldsCreator allWinningFieldsCreator = new AllWinningFieldsCreator(3, 9, 3);

      Map<String, List<Integer>> resultWinningFields = allWinningFieldsCreator.createAllWinningFields();

      assertThat(resultWinningFields).containsAllEntriesOf(expectWinningFields);
   }

}