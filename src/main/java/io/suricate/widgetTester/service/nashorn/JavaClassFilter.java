package io.suricate.widgetTester.service.nashorn;

import io.suricate.widgetTester.service.nashorn.script.Methods;
import jdk.nashorn.api.scripting.ClassFilter;


public class JavaClassFilter implements ClassFilter {

    /**
     * Method used to authorize access to some Java class
     * @param s class name to check
     * @return true is the class name is authorized, false otherwise
     */
    public boolean exposeToScripts(String s) {
        if (s.compareTo(Methods.class.getName()) == 0){
            return true;
        }
        return false;
    }
}
