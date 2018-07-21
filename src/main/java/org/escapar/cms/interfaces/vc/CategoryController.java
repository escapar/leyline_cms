package org.escapar.cms.interfaces.vc;

import org.escapar.cms.business.domain.category.Category;
import org.escapar.cms.business.domain.commons.CategoryType;
import org.escapar.cms.business.domain.topic.TopicDTO;
import org.escapar.cms.business.service.CategoryService;
import org.escapar.cms.interfaces.rest.TopicAPI;
import org.escapar.leyline.framework.infrastructure.common.exceptions.PersistenceException;
import org.escapar.leyline.framework.interfaces.dto.PageJSON;
import org.escapar.leyline.framework.interfaces.vc.PageableController;
import org.omg.CORBA.portable.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("category")

public class CategoryController {
    @Autowired CategoryService categoryService;
    @Autowired TopicAPI topicAPI;
    @Autowired IndexController indexController;
    private static final int size = 6;

    public void addMenuCategories(Model m) throws PersistenceException {
        m.addAttribute("menuCategories",categoryService.findNonStaticCategories());
    }

    @RequestMapping("/{catName}")
    public String cat(Model model,@PathVariable(value="catName")  String catName,@PageableDefault(page = 0, size = size, sort="createdAt", direction = Sort.Direction.DESC) Pageable p) throws PersistenceException, NoSuchMethodException {
        addMenuCategories(model);
        Category c = categoryService.findOneByAlias(catName);
        if(c != null){
            model.addAttribute("c",c);

            MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
            parameters.add("category.id",String.valueOf(c.getId()));
            parameters.add("latestPublished.published","true");
            PageJSON<TopicDTO> res = topicAPI.listWithQuery(p,parameters);

            //            List<Topic> topics = topicService.findByCategory(c).stream().filter(i->i.getLatest()!=null).collect(Collectors.toList());
            if(c.getType()!=CategoryType.STATIC) {
                model.addAttribute("topics", res.getContent());
                model.addAttribute("page",res);
                return "site/categories/list";
            }else{
                if(c.getReference() == null){
                    return "404";
                }
                return indexController.topic(model,c.getReference());
            }

        }
        return "404";
    }

    @RequestMapping("/{catName}/page/{page}")
    public String catPaged(Model model, @PathVariable Integer page,@PathVariable(value="catName") String catName, @RequestParam(required = false) String direction, @RequestParam(required = false, defaultValue = "id") String properties, @RequestParam(required = false) Integer pagesize)
            throws ApplicationException, PersistenceException, NoSuchMethodException {
        return cat(model,catName,getPageRequest(page,direction,properties,pagesize));
    }

    public PageRequest getPageRequest(Integer page, String direction, String property, Integer pagesize) throws ApplicationException {
        if (pagesize == null) {
            pagesize = size;
        }
        return PageableController.getPageRequestStatic(page,direction,property,pagesize);
    }



}
