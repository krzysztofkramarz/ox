package com.fonowizja.ox.game_elements;

import org.testng.annotations.Test;

/**
 * @author krzysztof.kramarz
 */
public class BoardTest
{
   Board board = Board.builder().xxx(5).yyy(5).build();

   @Test
   public void testSetSize()
   {
      int expectedSize = 25;

      // assertThat()  n//TODO nie ma co testować, bo Board hermetyzuje dostep do tablicy
   }

}
