package com.fonowizja.ox.gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author krzysztof.kramarz
 */
final class RightSettingsPanel extends JPanel
{
   private JSlider ySlider;
   private JTextField ySliderResult;
   private GamePanel gamePanel;
   private UpperSettingsPanel upperSettingsPanel;

   RightSettingsPanel(GamePanel gamePanel, UpperSettingsPanel upperSettingsPanel)
   {
      this.gamePanel = gamePanel;
      this.upperSettingsPanel = upperSettingsPanel;
      setLayout(new FlowLayout(FlowLayout.CENTER, 2, 2));
      setBorder(new LineBorder(new Color(40), 1));

      ySlider = new JSlider(JSlider.VERTICAL,
            3, 30, 3);
      setSliderAttibutes();
      add(ySlider);

      ySliderResult = new JTextField("3", 2);
      add(ySliderResult);
   }

   private void setSliderAttibutes()
   {
      ySlider.addChangeListener(new SliderListener());
      SliderHelper.setSliderAttibutes(ySlider);
   }

   private class SliderListener implements ChangeListener
   {
      @Override
      public void stateChanged(ChangeEvent e)
      {
         JSlider source = (JSlider) e.getSource();
         int y = source.getValue();
         ySliderResult.setText(String.valueOf(y));
         gamePanel.resizeY(y);
         upperSettingsPanel.setMaximumWinningSize(y);
         validate();

      }
   }

}
