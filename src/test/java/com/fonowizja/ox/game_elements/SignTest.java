package com.fonowizja.ox.game_elements;



import static org.assertj.core.api.Assertions.assertThat;

import com.fonowizja.ox.game_elements.Sign;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author krzysztof.kramarz
 */
public class SignTest
{

   @DataProvider
   public  Object[][] getSigns(){
      return new Object[][]{
            { Sign.O, "O"},
            {Sign.X, "X"},
            {Sign.EMPTY, "EMPTY"}
      };
   }

   @DataProvider
   public  Object[][] getSignsNegative(){
      return new Object[][]{
            {Sign.O, "X"},
            {Sign.X, "O"},
            {Sign.EMPTY, "O"}
      };
   }

   @Test(dataProvider = "getSigns")
   public void testSignExist(Sign sign, String value)
   {
      assertThat(sign).isEqualTo(Sign.valueOf(value));
   }

   @Test(dataProvider = "getSignsNegative")
   public void testSignNotExist(Sign sign, String value)
   {
      assertThat(sign).isNotEqualTo(Sign.valueOf(value));
   }

}
