package com.fonowizja.ox;

import com.fonowizja.ox.game_elements.GameElementsService;
import com.fonowizja.ox.game_elements.GameElementsServiceImpl;

/**
 * @author krzysztof.kramarz
 */
class Main
{
   public static void main(String[] args)
   {

      GameElementsService gameElementsService = new GameElementsServiceImpl(1, 3,3);

      System.out.println(gameElementsService.getBoardAsString());
   }
}
