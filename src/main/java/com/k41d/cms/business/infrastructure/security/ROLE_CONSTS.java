package com.k41d.cms.business.infrastructure.security;

import com.vs.business.domain.commons.InvoiceStatus;

public enum ROLE_CONSTS {
    ROLE_ADMIN(99),
    ROLE_UNCHECKED_USER(0),
    ROLE_USER(1);
    public final int val;

    private ROLE_CONSTS(final int val) {
        this.val = val;
    }

    public static String getState(final int is){
        if(is == ROLE_CONSTS.ROLE_ADMIN.val) return "ROLE_ADMIN";
        if(is == ROLE_CONSTS.ROLE_UNCHECKED_USER.val) return "ROLE_UNCHECKED_USER";
        if(is == ROLE_CONSTS.ROLE_USER.val) return "ROLE_USER";

        return "";
    }

}
