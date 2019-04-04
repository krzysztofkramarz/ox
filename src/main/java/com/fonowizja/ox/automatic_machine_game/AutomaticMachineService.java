package com.fonowizja.ox.automatic_machine_game;

import java.util.List;

import javax.swing.*;

import com.fonowizja.ox.game_elements.GameElementsService;
import com.fonowizja.ox.game_elements.Sign;
import com.fonowizja.ox.gui.GamePanel;

/**
 * Servis for play automatic and semi-automatic games
 * @author krzysztof.kramarz
 */
public interface AutomaticMachineService
{
   /**
    * Creates {@link AutomaticMachine}
    * @param gameElementsService on this will be game played
    * @param gamePanel for manipulation on game panel by automatic machine
    * @return
    */
   AutomaticMachine createAutomaticMachine(GameElementsService gameElementsService, GamePanel gamePanel);

   public void semiAutomaticGameStart(List<JButton> buttonsList, Sign  semiGameMachinePlayer, Integer winningSize);

   void automaticTestMachineStart(Sign sign, Integer winningSize);

   void makeSemiAutomaticMove();
}
