package com.fonowizja.ox.gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.fonowizja.ox.game_elements.Sign;

/**
 * @author krzysztof.kramarz
 */
final class UpperSettingsPanel extends JPanel
{

   private JSlider boardLengthSlider;
   private JSlider winningSizeSlider;
   private JTextField boardLengthSliderResultTextField;
   private JTextField winningSizeResultTextField;
   private GamePanel gamePanel;

   private ButtonGroup whoIsFirstBG;
   private JRadioButton XisFirsJRB;
   private JRadioButton OisFirstJRB;
   private JPanel whoIsFistPanel;
   private JPanel boardLengthPanel;
   private JPanel winningSizePanel;
   private JLabel boardLengthLabel;
   private JLabel winningSizeLabel;

   private Sign whoIsFirst;

   UpperSettingsPanel(GamePanel gamePanel)
   {

      this.gamePanel = gamePanel;
      setLayout(new FlowLayout(FlowLayout.CENTER, 2, 2));
      setBorder(new LineBorder(new Color(40), 1));
      setPreferredSize(new Dimension(4000, 200));

      setBoardLengthSliderAttibutes();
      setWinningSizeSliderAttibutes();

      setBoardLengthPanel();
      setWinningSizePanel();

      setWhoIsFirst();
   }

   private void setWinningSizePanel()
   {
      winningSizePanel = new JPanel();
      winningSizeLabel = new JLabel("Ustaw ile znaczkow wygrywa");
      winningSizeResultTextField = new JTextField("3", 2);
      winningSizePanel.add(winningSizeResultTextField);
      winningSizePanel.add(winningSizeLabel);
      winningSizePanel.add(winningSizeSlider);

      TitledBorder border = BorderFactory.createTitledBorder("Ustaw ile znaczkow wygrywa");
      border.setTitleColor(Color.blue);
      border.setBorder(new LineBorder(Color.RED, 1));
      winningSizePanel.setBorder(border);
      winningSizePanel.setVisible(true);

      add(winningSizePanel);
   }

   private void setBoardLengthPanel()
   {
      boardLengthPanel = new JPanel();
      boardLengthLabel = new JLabel("Ustaw szerokość tablicy");
      boardLengthSliderResultTextField = new JTextField("3", 2);
      boardLengthPanel.add(boardLengthSliderResultTextField);
      boardLengthPanel.add(boardLengthLabel);
      boardLengthPanel.add(boardLengthSlider);

      TitledBorder border = BorderFactory.createTitledBorder("Ustaw szerokość tablicy");
      border.setTitleColor(Color.blue);
      border.setBorder(new LineBorder(Color.RED, 1));
      boardLengthPanel.setBorder(border);
      boardLengthPanel.setVisible(true);

      add(boardLengthPanel);
   }

   private void setWhoIsFirst()
   {

      whoIsFistPanel = new JPanel(new FlowLayout());

      whoIsFirstBG = new ButtonGroup();
      XisFirsJRB = new JRadioButton("Pierwszy gra X", true);
      OisFirstJRB = new JRadioButton("Pierwszy gra O", false);
      XisFirsJRB.addActionListener(event -> whoIsFirst = Sign.X);
      OisFirstJRB.addActionListener(event -> whoIsFirst = Sign.O);
      whoIsFirstBG.add(XisFirsJRB);
      whoIsFirstBG.add(OisFirstJRB);
      whoIsFistPanel.add(XisFirsJRB);
      whoIsFistPanel.add(OisFirstJRB);

      TitledBorder border = BorderFactory.createTitledBorder("Kto zaczyna");
      border.setTitleColor(Color.RED);
      border.setBorder(new LineBorder(Color.RED, 1));
      whoIsFistPanel.setBorder(border);
      add(whoIsFistPanel);
      whoIsFistPanel.setVisible(true);
   }

   private void setBoardLengthSliderAttibutes()
   {
      boardLengthSlider = new JSlider(JSlider.HORIZONTAL, 3, 30, 3);
      boardLengthSlider.addChangeListener(new boardLengthSliderListener());

      SliderHelper.setSliderAttibutes(boardLengthSlider);

   }

   public void setMaximumWinningSize(int maximumWinningSize)
   {
      winningSizeSlider.setMaximum(maximumWinningSize);
   }

   private class boardLengthSliderListener implements ChangeListener
   {
      @Override
      public void stateChanged(ChangeEvent e)
      {
         JSlider sds = (JSlider) e.getSource();
         int ileButtonow = sds.getValue();
         boardLengthSliderResultTextField.setText(String.valueOf(ileButtonow));
         gamePanel.resizeX(ileButtonow);

         winningSizeSlider.setMaximum(gamePanel.getMaxWinningSize());

         validate();

      }
   }

   private void setWinningSizeSliderAttibutes()
   {
      winningSizeSlider = new JSlider(JSlider.HORIZONTAL, 3, 3, 3);
      winningSizeSlider.addChangeListener(new WinningSizeSliderListener());
      winningSizeSlider.setMinimum(3);
      winningSizeSlider.setMaximum(gamePanel.getMaxWinningSize());

      SliderHelper.setWinningSizeSliderAttibutes(winningSizeSlider);

   }

   private class WinningSizeSliderListener implements ChangeListener
   {
      @Override
      public void stateChanged(ChangeEvent e)
      {
         JSlider source = (JSlider) e.getSource();
         int winningSize = source.getValue();
         gamePanel.setWinningSize(winningSize);
         winningSizeResultTextField.setText(String.valueOf(winningSize));
         validate();

      }
   }

}
