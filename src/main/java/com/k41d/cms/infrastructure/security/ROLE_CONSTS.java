package com.k41d.cms.infrastructure.security;


public enum ROLE_CONSTS {
    ADMIN(99),
    UNCHECKED_USER(0),
    USER(1);
    public  int val;

    private ROLE_CONSTS( int val) {
        this.val = val;
    }

    public static String getState( int is){
        if(is == ROLE_CONSTS.ADMIN.val) return "ADMIN";
        if(is == ROLE_CONSTS.UNCHECKED_USER.val) return "UNCHECKED_USER";
        if(is == ROLE_CONSTS.USER.val) return "USER";

        return "";
    }

}
