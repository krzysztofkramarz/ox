package com.fonowizja.ox.automatic_machine_game;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fonowizja.ox.game_elements.GameElementsService;
import com.fonowizja.ox.game_elements.Sign;
import com.fonowizja.ox.gui.GamePanel;

/**
 * @author krzysztof.kramarz
 */
class AutomaticMachine
{
   private final Map<String, List<Integer>> allEmptyWinningCombinationsThatCanBeUsed;
   private final GameElementsService gameElementsService;
   private GamePanel gamePanel;
   private Set<Integer> freeFieldsForEnemy;
   private Set<Integer> fieldsForWinner;
   private Set<String> combinationsCanBeUsedToPlay;

   AutomaticMachine(GameElementsService gameElementsService, GamePanel gamePanel)
   {
      this.gamePanel = gamePanel;
      this.gameElementsService = gameElementsService;
      allEmptyWinningCombinationsThatCanBeUsed = gameElementsService.getAllEmptyWinningCombinationsThatCanBeUsed();
   }

   public void automaticTestMachineStart(Sign sign)
   {
      combinationsCanBeUsedToPlay = allEmptyWinningCombinationsThatCanBeUsed.keySet();
      Iterator<String> iterator = combinationsCanBeUsedToPlay.iterator();

      for (Iterator<String> it = iterator; it.hasNext(); )
      {
         String key = it.next();

      }
      String next = iterator.next();

      List<Integer> integers = allEmptyWinningCombinationsThatCanBeUsed.get(next);

      for (Integer positionOnBoard : integers)
      {
         gamePanel.putSignOnField(sign, positionOnBoard);
      }
      gamePanel.validatePanel();

   }
}
