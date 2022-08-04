package com.example.demo;

import java.lang.reflect.Field;

public class TestUtils {
    public static void injectObjetcs(Object target, String fieldName, Object toInject){
        boolean wasPrivate = false;
       try{
           //If the target field is private, then we modify it on the instance and inject it in order to mockit
           Field declaredField = target.getClass().getDeclaredField(fieldName);
           if(!declaredField.isAccessible()){
               declaredField.setAccessible(true);
               wasPrivate = true;
           }
           declaredField.set(target, toInject);
           if(wasPrivate){
               declaredField.setAccessible(false);
           }
       }catch (NoSuchFieldException | IllegalAccessException e){
           e.printStackTrace();
       }
    }
}
