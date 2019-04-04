package com.fonowizja.ox.gui;

import java.awt.*;

import javax.swing.*;

import com.fonowizja.ox.game_elements.GameElementsService;
import com.fonowizja.ox.game_elements.GameElementsServiceImpl;

/**
 * Main panel for game
 *
 * @author krzysztof.kramarz
 */
final class MainPanel extends JPanel
{
   private static final long serialVersionUID = -8960621084166445350L;
   private JFrame jFrame;
   private GamePanel gamePanel;
   private UpperSettingsPanel upperSettingsPanel;
   private RightSettingsPanel rightSettingsPanel;

   MainPanel()
   {
      //tworzenie zewnętrzengo kontenera JFrame
      jFrame = new JFrame("Gra w OX");
      jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      jFrame.setBounds(10, 10, 4000, 4000);

      jFrame.setVisible(true);

      //dodajemy MainPanel i ustawiamy dla niego zarządcę
      jFrame.add(this);
      setLayout(new BorderLayout(2, 2));

      //tworzymy GamePanel, dodajemy do MainPanelu i ustawiamy położenie
      gamePanel = new GamePanel();
      add(gamePanel, BorderLayout.CENTER);

      //tworzymy GamePanel, dodajemy do MainPanelu i ustawiamy położenie
      upperSettingsPanel = new UpperSettingsPanel(gamePanel);
      add(upperSettingsPanel, BorderLayout.NORTH);

      //tworzymy GamePanel, dodajemy do MainPanelu i ustawiamy położenie
      rightSettingsPanel = new RightSettingsPanel(gamePanel, upperSettingsPanel);

      gamePanel.setRightSettingsPanel(rightSettingsPanel);
      add(rightSettingsPanel, BorderLayout.EAST);
      validate();

   }
}
