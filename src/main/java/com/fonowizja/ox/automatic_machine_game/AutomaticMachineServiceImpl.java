package com.fonowizja.ox.automatic_machine_game;

import java.util.List;

import javax.swing.*;

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

   public void semiAutomaticGameStart(List<JButton> buttonsList, Sign  semiGameMachinePlayer, Integer winningSize)
   {
      automaticMachine.semiAutomaticGameStart(buttonsList,semiGameMachinePlayer, winningSize);
   }

   @Override
   public void automaticTestMachineStart(Sign sign, Integer winningSize)
   {
      automaticMachine.automaticTestMachineStart(sign, winningSize);
   }

   @Override
   public void makeSemiAutomaticMove()
   {
      automaticMachine.makeSemiAutomaticMove();
   }
}
