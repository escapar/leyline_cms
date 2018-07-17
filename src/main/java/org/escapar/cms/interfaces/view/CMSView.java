package org.escapar.cms.interfaces.view;

import org.escapar.leyline.framework.interfaces.view.LeylineView;

/**
 * Created by bytenoob on 5/29/16.
 */
public class CMSView extends LeylineView {
    public interface ADMIN_LIST extends LIST{
    }
    public interface USER_LIST extends LIST{
    }

    public interface ADMIN_DETAIL extends DETAIL {
    }
    public interface USER_DETAIL extends DETAIL {
    }
}
