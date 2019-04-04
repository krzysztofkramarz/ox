package com.fonowizja.ox.internationalization;

import java.util.ResourceBundle;

/**
 * @author krzysztof.kramarz
 */
public class MessageProvider
{

   private static MessageProvider instance;
   private static ResourceBundle bundle;

   private MessageProvider()
   {
   }

   public static MessageProvider getInstance()
   {
      if (instance == null)
      {
         instance = new MessageProvider();
         bundle = MessageLoader.setBundle(LanguagesKey.POLISH);
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
