package com.fonowizja.ox.automatic_machine_game;

import com.fonowizja.ox.game_elements.GameElementsService;
import com.fonowizja.ox.game_elements.Sign;
import com.fonowizja.ox.gui.GamePanel;

/**
 * @author krzysztof.kramarz
 */
public interface AutomaticMachineService
{

   AutomaticMachine createAutomaticMachine(GameElementsService gameElementsService, GamePanel gamePanel);

   void automaticTestMachineStart(Sign sign);
}
