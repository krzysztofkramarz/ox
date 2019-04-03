package com.fonowizja.ox.internationalization;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author krzysztof.kramarz
 */
class MessageLoader
{

   /**
    * Provides properties for appropiate language. Englsh is default
    *
    * @param language
    *       application language
    * @return resource bundle
    */
   static ResourceBundle setBundle(LanguagesKey language)
   {
      switch (language)
      {
         case ENGLISH:
            return ResourceBundle.getBundle("OX", new Locale("eng", "ENG"));
         case POLISH:
            return ResourceBundle.getBundle("OX", new Locale("pl", "PL"));
         default:
            return ResourceBundle.getBundle("OX", new Locale("eng", "ENG"));
      }
   }

}

