package com.fonowizja.ox.automatic_machine_game;

import com.fonowizja.ox.game_elements.GameElementsService;
import com.fonowizja.ox.game_elements.Sign;
import com.fonowizja.ox.gui.GamePanel;

/**
 * @author krzysztof.kramarz
 */
public class AutomaticMachineServiceImpl implements AutomaticMachineService
{
   private AutomaticMachine automaticMachine;

   @Override
   public AutomaticMachine createAutomaticMachine(GameElementsService gameElementsService, GamePanel gamePanel)
   {
      automaticMachine = new AutomaticMachine(gameElementsService, gamePanel);
      return automaticMachine;
   }

   @Override
   public void automaticTestMachineStart(Sign sign, Integer winningSize)
   {
      automaticMachine.automaticTestMachineStart(sign, winningSize);
   }
}
