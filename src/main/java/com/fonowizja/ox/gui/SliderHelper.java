package com.fonowizja.ox.gui;

import javax.swing.*;

/**
 * @author krzysztof.kramarz
 */
class SliderHelper
{
   static void setSliderAttibutes(JSlider jSlider)
   {
      jSlider.setMajorTickSpacing(6);
      jSlider.setMinorTickSpacing(1);
      jSlider.setPaintTicks(true);
      jSlider.setPaintLabels(true);
      jSlider.setVisible(true);
      jSlider.setSnapToTicks(true);
      jSlider.setPaintTrack(true);
   }

}
