package com.fonowizja.ox.internationalization;

import java.util.ResourceBundle;

/**
 * @author krzysztof.kramarz
 */
class TextProvider
{

   private static TextProvider instance;
   private static ResourceBundle bundle;

   private TextProvider()
   {
   }

   public static TextProvider getInstance()
   {
      if (instance == null)
      {
         instance = new TextProvider();
         bundle = MessageLoader.setBundle(LanguagesKey.ENGLISH);
      }
      return instance;
   }

   /**
    * Changes language of messages
    *
    * @param language
    *       language type
    */
   public void changeLanguageBundle(LanguagesKey language)
   {
      bundle = MessageLoader.setBundle(language);
   }

   /**
    * Returns message from given key
    *
    * @param key
    *       enum parameter from LocalizationKey
    */
   public String getMessage(MessagesKey key)
   {
      return bundle.getString(key.name());
   }

}
