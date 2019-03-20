package com.fonowizja.ox;



import static org.assertj.core.api.Assertions.assertThat;

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
            {Sign.O, "O"},
            {Sign.X, "X"}
      };
   }

   @DataProvider
   public  Object[][] getSignsNegative(){
      return new Object[][]{
            {Sign.O, "X"},
            {Sign.X, "O"}
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
