package org.escapar.cms.business.domain.commons;

public class VersionUtil {
    public static String nextSubVersion(String current){
        if(current == null || current.isEmpty()){
            return "1";
        }
        try {
            return intVersionUpgrade(current);
        }catch (Exception e){
            return "1";
        }
    }
    public static String nextMainVersion(String current){
        // use same policy for now
        return nextSubVersion(current);
    }
    private static String intVersionUpgrade(String current){
        return String.valueOf(Integer.valueOf(current) + 1);
    }
}
