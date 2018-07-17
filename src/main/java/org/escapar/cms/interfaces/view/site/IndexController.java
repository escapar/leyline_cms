package org.escapar.cms.interfaces.view.site;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.escapar.cms.business.domain.category.Category;
import org.escapar.cms.business.domain.topic.Topic;
import org.escapar.cms.business.service.CategoryService;
import org.escapar.cms.business.service.TopicService;
import org.escapar.leyline.framework.infrastructure.common.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/static")
public class IndexController {
    @Autowired CategoryService categoryService;
    @Autowired TopicService topicService;

    @RequestMapping("/")
    public String index(Model model) throws PersistenceException {
        addMenuCategories(model);
        List<Category> categories = categoryService.findAll();
        Map<Category,List<Topic>> ct = new LinkedHashMap<>();
        categories.stream().filter(c->c.getReference() == null).forEach(i->ct.put(i,topicService.findLatestByCategory(i)));
        model.addAttribute("ct", ct);
        return "site/index";
    }

    @RequestMapping("/{catName}")
    public String cat(Model model,@PathVariable(value="catName")  String catName) throws PersistenceException {
        addMenuCategories(model);
        Category c = categoryService.findOneByAlias(catName);
        if(c != null){
            model.addAttribute("c",c);
            List<Topic> topics = topicService.findByCategory(c);
            if(topics.size()>1) {
                model.addAttribute("topics", topicService.findByCategory(c));
                return "site/categories/list";
            }else{
                return topic(model, c.getName(),Long.valueOf(c.getReference()));
            }

        }
        return "404";
    }

    @RequestMapping("/{catName}/{topicId}")
    public String topic(Model model,@PathVariable(value="catName")  String catName,@PathVariable(value="topicId")  Long topicId) throws PersistenceException {
        addMenuCategories(model);
        Optional<Topic> t = topicService.findById(topicId);
        if(t.isPresent()){
            model.addAttribute("t",t.get());
            return "site/topics/detail";
        }
        return "404";
    }

    public void addMenuCategories(Model m){
        m.addAttribute("menuCategories",categoryService.findStaticCategories());
    }

}
