package com.fonowizja.ox.gui;

import javax.swing.*;

/**
 * @author krzysztof.kramarz
 */
class SliderHelper
{
   static void setSliderAttibutes(JSlider jSlider){

      jSlider.setMajorTickSpacing(6);
      jSlider.setMinorTickSpacing(1);
      jSlider.setPaintTicks(true);
      jSlider.setPaintLabels(true);
      jSlider.setVisible(true);
      jSlider.setSnapToTicks(true);
      jSlider.setPaintTrack(true);
   }

   public static void setWinningSizeSliderAttibutes(JSlider winningSizeSlider)
   {
      winningSizeSlider.setMinorTickSpacing(1);
      winningSizeSlider.setPaintTicks(true);
      winningSizeSlider.setPaintLabels(true);
      winningSizeSlider.setVisible(true);
      winningSizeSlider.setSnapToTicks(true);
      winningSizeSlider.setPaintTrack(true);
   }
}
