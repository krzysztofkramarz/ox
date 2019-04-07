package com.fonowizja.ox.automatic_machine_game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import com.fonowizja.ox.game_elements.GameElementsService;
import com.fonowizja.ox.game_elements.Sign;
import com.fonowizja.ox.gui.GamePanel;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author krzysztof.kramarz
 */
public class AutomaticMachineServiceImplTest
{

   @BeforeMethod
   public void init()
   {
      MockitoAnnotations.initMocks(this);
   }

   @Test
   public void testCeateAutomaticMachine()
   {
      AutomaticMachineService automaticMachineService = new AutomaticMachineServiceImpl();

      GameElementsService gameElementsServiceMock = mock(GameElementsService.class);
      GamePanel gamePanelMock = mock(GamePanel.class);

      AutomaticMachine result = automaticMachineService.createAutomaticMachine(gameElementsServiceMock, gamePanelMock);

      assertThat(result).isInstanceOf(AutomaticMachine.class);
   }

   @DataProvider
   public Object[][] testAutomaticTestMachineStart()
   {
      return new Object[][] {
            { Sign.X },
            { Sign.O }

      };
   }

   @Test(dataProvider = "testAutomaticTestMachineStart")
   public void testAutomaticTestMachineStart(Sign sign)
   {
      AutomaticMachineService automaticMachineService = new AutomaticMachineServiceImpl();

      GameElementsService gameElementsServiceMock = mock(GameElementsService.class);
      GamePanel gamePanelMock = mock(GamePanel.class);

      automaticMachineService.createAutomaticMachine(gameElementsServiceMock, gamePanelMock);
      automaticMachineService.automaticTestMachineStart(sign);

   }

   @Test
   public void testSAutomaticGameStart()
   {
      AutomaticMachineService automaticMachineService = new AutomaticMachineServiceImpl();
      GameElementsService gameElementsServiceMock = mock(GameElementsService.class);
      GamePanel gamePanelMock = mock(GamePanel.class);

      automaticMachineService.createAutomaticMachine(gameElementsServiceMock, gamePanelMock);
      JButton jButton1 = mock(JButton.class);
      JButton jButton2 = mock(JButton.class);

      List<JButton> buttonsList = new ArrayList<>();
      buttonsList.add(jButton1);
      buttonsList.add(jButton2);

   }

   @Test
   public void testMakeSemiAutomaticMove()
   {
      AutomaticMachineService automaticMachineService = new AutomaticMachineServiceImpl();
      GameElementsService gameElementsServiceMock = mock(GameElementsService.class);
      GamePanel gamePanelMock = mock(GamePanel.class);

      automaticMachineService.createAutomaticMachine(gameElementsServiceMock, gamePanelMock);
      JButton jButton1 = mock(JButton.class);
      JButton jButton2 = mock(JButton.class);

      List<JButton> buttonsList = new ArrayList<>();
      buttonsList.add(jButton1);
      buttonsList.add(jButton2);

      automaticMachineService.semiAutomaticGameStart(buttonsList, Sign.X, 4);

   }
}