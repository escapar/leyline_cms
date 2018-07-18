package org.escapar.cms.interfaces.view.site;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.escapar.cms.business.domain.category.Category;
import org.escapar.cms.business.domain.commons.CategoryType;
import org.escapar.cms.business.domain.tag.Tag;
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
@RequestMapping("/")
public class IndexController {
    @Autowired CategoryService categoryService;
    @Autowired TopicService topicService;

    @RequestMapping("/")
    public String index(Model model) throws PersistenceException {
        addMenuCategories(model);
        model.addAttribute("latest", topicService.findLatest3Featured());
        return "site/index";
    }

    @RequestMapping("/category/{catName}")
    public String cat(Model model,@PathVariable(value="catName")  String catName) throws PersistenceException {
        addMenuCategories(model);
        Category c = categoryService.findOneByAlias(catName);
        if(c != null){
            model.addAttribute("c",c);
            List<Topic> topics = topicService.findByCategory(c).stream().filter(i->i.getLatest()!=null).collect(Collectors.toList());
            if(c.getType()!=CategoryType.STATIC) {
                model.addAttribute("topics", topics);
                return "site/categories/list";
            }else{
                if(c.getReference() == null){
                    return "404";
                }
                return topic(model,c.getReference());
            }

        }
        return "404";
    }

    @RequestMapping("/topic/{topicId}")
    public String topic(Model model, @PathVariable(value="topicId")  String topicId) throws PersistenceException {
        addMenuCategories(model);
        List<Topic> tList = topicService.findByNameLike(topicId);
        if(tList.size() < 1) {
            Optional<Topic> tOpt = topicService.findById(Long.valueOf(topicId));
            if(tOpt.isPresent()) {
                model.addAttribute("t", tOpt.get());
                return "site/topics/detail";
            }
        }else{
            model.addAttribute("t", tList.get(0));
            return "site/topics/detail";
        }
        return "404";
    }

    public void addMenuCategories(Model m) throws PersistenceException {
        m.addAttribute("menuCategories",categoryService.findNonStaticCategories());
    }

    public String tagsToString(List<Tag> tags) {
        if(tags == null || tags.size() < 1) return "";
        Optional<String> opt = tags.stream().map(Tag::getName).reduce((a,b)->a+" "+b);
        return opt.orElse("");
    }

}
