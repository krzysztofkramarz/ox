package com.fonowizja.ox.game_elements;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.*;

import org.testng.annotations.Test;

/**
 * @author krzysztof.kramarz
 */
public class GameElementsServiceTest
{
   @Test
   public void testGetBoardAsEmptyString()
   {

      GameElementsService gameElementsService = new Board();

      String expect = "";

      assertThat(gameElementsService.getBoardAsString()).isEqualTo(expect);
   }
}