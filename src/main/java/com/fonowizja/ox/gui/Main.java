package com.fonowizja.ox.gui;

import javax.swing.*;

/**
 * @author krzysztof.kramarz
 */
class Main
{
   public static void main(String[] args)
   {


      SwingUtilities.invokeLater(() -> new MainPanel());

      // GameElementsService gameElementsService = new GameElementsServiceImpl(1, 3,3);

   }
}
