package com.fonowizja.ox.internationalization;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author krzysztof.kramarz
 */
public class MessageProviderTest
{

   @DataProvider(name = "testGetSomeMessage")
   public Object[][] testGetSomeMessage()
   {
      return new Object[][] {
            { "UPPER_PANEL_BOARD_LENGTH_SLIDER", "Ustaw szerokość tablicy" },
            { "UPPER_PANEL_WINNING_SIZE_SLIDER", "Ile znaczków wygrywa" },
            { "UPPER_PANEL_WHO_FIRST", "Kto zaczyna grę" },
            { "UPPER_PANEL_WHO_FIRST_X", "Pierwszy gra X" },
            { "UPPER_PANEL_WHO_FIRST_O", "Pierwszy gra O" }
      };
   }

   @Test(dataProvider = "testGetSomeMessage")
   public void testGetSomeMessage(String key, String message)
   {
      String result = MessageProvider.getInstance().getMessage(MessagesKey.valueOf(key));

      assertThat(result).isEqualTo(message);
   }

}