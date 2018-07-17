package org.escapar.cms.interfaces.view.site;

import org.escapar.leyline.framework.infrastructure.common.exceptions.PersistenceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminPanelController {
    @RequestMapping("/admin/index")
    public String index(Model model) throws PersistenceException {
        return "admin/index";
    }
}
