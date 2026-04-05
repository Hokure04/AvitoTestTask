package org.example.Utils;

import io.qameta.allure.Allure;

public class AllureUtils {

    private AllureUtils(){
    }

    public static void attachText(String name, String value){
        Allure.addAttachment(name, "text/plain",value);
    }

    public static void attachJson(String name, String value){
        Allure.addAttachment(name, "application/json", value, ".json");
    }
}
