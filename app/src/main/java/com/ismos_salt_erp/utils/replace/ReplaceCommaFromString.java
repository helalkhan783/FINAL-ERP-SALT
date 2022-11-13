package com.ismos_salt_erp.utils.replace;

public class ReplaceCommaFromString {
   public  static String replaceComma(String value){
       if (value == null){
           value ="0";
       }


       return value.replaceAll(",","");
   }
}
