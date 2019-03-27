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
public class IntegerRangeHelperTest
{

   @DataProvider
   public Object[][] getIntegerStreams()
   {
      return new Object[][] {
            { 1, (IntUnaryOperator) n -> n + 1, 5, Arrays.asList(1, 2, 3, 4, 5) },
            { 3, (IntUnaryOperator) n -> n + 1, 5, Arrays.asList(3, 4, 5, 6, 7) },
            { 10, (IntUnaryOperator) n -> n + 1, 6, Arrays.asList(10, 11, 12, 13, 14, 15) },
            { -30, (IntUnaryOperator) n -> n + 10, 10, Arrays.asList(-30, -20, -10, 0, 10, 20, 30, 40, 50, 60) },
            { 23, (IntUnaryOperator) n -> n + 6, 6, Arrays.asList(23, 29, 35, 41, 47, 53) },
            { 10, (IntUnaryOperator) n -> n - 5, 8, Arrays.asList(10, 5, 0, -5, -10, -15, -20, -25) },
            { 20, (IntUnaryOperator) n -> n - 2, 7, Arrays.asList(20, 18, 16, 14, 12, 10, 8) },
            { 80, (IntUnaryOperator) n -> n / 2, 5, Arrays.asList(80, 40, 20, 10, 5) },
            { 1, (IntUnaryOperator) n -> n, 5, Arrays.asList(1, 1, 1, 1, 1) },
            { 1, (IntUnaryOperator) n -> n * 2, 5, Arrays.asList(1, 2, 4, 8, 16) },
            { 2, (IntUnaryOperator) n -> n * 2, 10, Arrays.asList(2, 4, 8, 16, 32, 64, 128, 512, 1024, 2048) }

      };
   }

   @Test(dataProvider = "getIntegerStreams")
   public void testGenerateRangeOfSearching(Integer start, IntUnaryOperator intUnaryOperator, Integer limit, List<Integer> expect)
   {

      List<Integer> result = IntegerRangeHelper.generateRangeOfSearching(start, intUnaryOperator, limit);
      assertThat(result).isEqualTo(expect);
   }

}