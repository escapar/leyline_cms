package org.escapar.cms.interfaces.vc;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import com.querydsl.core.types.Predicate;

import org.escapar.cms.business.domain.category.Category;
import org.escapar.cms.business.domain.commons.CategoryType;
import org.escapar.cms.business.domain.tag.Tag;
import org.escapar.cms.business.domain.topic.Topic;
import org.escapar.cms.business.domain.topic.TopicDTO;
import org.escapar.cms.business.service.CategoryService;
import org.escapar.cms.business.service.TopicService;
import org.escapar.cms.interfaces.rest.TopicAPI;
import org.escapar.leyline.framework.infrastructure.common.exceptions.PersistenceException;
import org.escapar.leyline.framework.interfaces.dto.PageJSON;
import org.escapar.leyline.framework.interfaces.dto.assembler.DTOAssembler;
import org.h2.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.QuerydslBindingsFactory;
import org.springframework.data.querydsl.binding.QuerydslPredicateBuilder;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.data.util.TypeInformation;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class IndexController {
    @Autowired CategoryService categoryService;
    @Autowired TopicService topicService;
    @Autowired TopicAPI topicAPI;

    @RequestMapping("/")
    public String index(Model model) throws PersistenceException {
        addMenuCategories(model);
        model.addAttribute("latest", topicService.findLatest3Featured());
        return "site/index";
    }




    @RequestMapping("/topic/{topicId}")
    public String topic(Model model, @PathVariable(value="topicId")  String topicId) throws PersistenceException {
        addMenuCategories(model);
        List<Topic> tList = topicService.findByNameLike(topicId);
        Topic t = null;
        if(tList.size() < 1) {
            Optional<Topic> tOpt = topicService.findById(Long.valueOf(topicId));
            if(tOpt.isPresent()) {
                t = tOpt.get();
            }
        }else{
           t = tList.get(0);
        }
        if(t == null) {
            return "404";
        }else{
            model.addAttribute("t", t);
            model.addAttribute("prev", topicService.findPrev(t));
            model.addAttribute("next", topicService.findNext(t));
            return "site/topics/detail";
        }
    }

    public void addMenuCategories(Model m) throws PersistenceException {
        m.addAttribute("menuCategories",categoryService.findNonStaticCategories());
    }

    public String tagsToString(List<Tag> tags) {
        if(tags == null || tags.size() < 1) return "";
        Optional<String> opt = tags.stream().map(Tag::getName).reduce((a,b)->a+" "+b);
        return opt.orElse("");
    }


    @RequestMapping(value = {"/robots", "/robot", "/robot.txt", "/robots.txt", "/null"})
    public void robot(HttpServletResponse response) throws IOException {

        InputStream resourceAsStream = null;
        try {

            ClassLoader classLoader = getClass().getClassLoader();
            resourceAsStream = classLoader.getResourceAsStream("robot.txt");

            response.addHeader("Content-disposition", "filename=robot.txt");
            response.setContentType("text/plain");
            IOUtils.copy(resourceAsStream, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {

        } finally {
            resourceAsStream.close();
        }
    }

}
