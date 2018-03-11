/*
 * Copyright 2000-2017 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.k41d.cms.interfaces.view.admin.views.categories;

import com.k41d.cms.business.domain.topic.Topic;
import com.k41d.cms.business.service.TopicService;
import com.k41d.cms.interfaces.view.admin.MainLayout;
import com.k41d.cms.interfaces.view.admin.common.AbstractEditorDialog;
import com.k41d.leyline.framework.infrastructure.common.exceptions.PersistenceException;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Displays the list of available categories, with a search filter as well as
 * buttons to add a new Topic or edit existing ones.
 */
@SpringComponent
@Route(value = "categories", layout = MainLayout.class)
@PageTitle("Topics List")
public class CategoriesList extends VerticalLayout {
    final TopicService topicService;

    private final TextField searchField = new TextField("", "Search");
    private final Grid<Topic> grid = new Grid<>();

    private final CategoryEditorDialog form;

    @Autowired
    public CategoriesList(TopicService topicService, CategoryEditorDialog form) throws PersistenceException {
        this.form = form;
        this.topicService = topicService;
        initView();

        addSearchBar();
        addGrid();

        updateView();
        form.dialogInit((topic, operation) -> {
            try {
                saveTopic(topic, operation);
            } catch (PersistenceException e) {
                e.printStackTrace();
            }
        }, topic1 -> {
            try {
                deleteTopic(topic1);
            } catch (PersistenceException e) {
                e.printStackTrace();
            }
        });
    }

    private void initView() {
        addClassName("categories-list");
        setDefaultHorizontalComponentAlignment(Alignment.STRETCH);
    }

    private void addSearchBar() {
        Div viewToolbar = new Div();
        viewToolbar.addClassName("view-toolbar");

        searchField.setPrefixComponent(new Icon("lumo", "search"));
        searchField.addClassName("view-toolbar__search-field");
        searchField.addValueChangeListener(e -> {
            try {
                updateView(e.getValue());
            } catch (PersistenceException e1) {
                e1.printStackTrace();
            }
        });

        Button newButton = new Button("New Topic", new Icon("lumo", "plus"));
        newButton.getElement().setAttribute("theme", "primary");
        newButton.addClassName("view-toolbar__button");
        newButton.addClickListener(e -> form.open(new Topic(),
                AbstractEditorDialog.Operation.ADD));

        viewToolbar.add(searchField, newButton);
        add(viewToolbar);
    }

    private void addGrid() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd-MM-yy");
        grid.addColumn(Topic::getName).setHeader("标题");
        grid.addColumn(topic->topic.getCategory().getName()).setHeader("栏目");
        grid.addColumn(topic->
                topic.getLatestPublished() == null ? "/"+topic.getLatest().getVersionString() :
                        topic.getLatestPublished().getVersionString()+"/"+topic.getLatest().getVersionString()).setHeader("发布版本/最新版本");
        grid.addColumn(topic->
                topic.getLatestPublished() == null ?
                        "/"+f.format(topic.getLatest().getCreatedAt()) :
                        f.format(topic.getLatestPublished().getPublishedAt())+"/"+f.format(topic.getLatest().getCreatedAt())).setHeader("发布日期/创建日期");
        grid.addColumn(topic->topic.getLikes().size()).setHeader("点赞数");

        grid.addColumn(new ComponentRenderer<>(this::createEditButton))
                .setFlexGrow(0);
        
        grid.addClassName("categories");
        grid.getElement().setAttribute("theme", "row-dividers");
        add(grid);
    }

    private Button createEditButton(Topic topic) {
        Button edit = new Button("Edit", event -> form.open(topic,
                AbstractEditorDialog.Operation.EDIT));
        edit.setIcon(new Icon("lumo", "edit"));
        edit.addClassName("review__edit");
        edit.getElement().setAttribute("theme", "tertiary");
        return edit;
    }

    private String getReviewCount(Topic Topic) {
//        List<Topic> reviewsInTopic = ReviewService.getInstance()
//                .findReviews(Topic.getName());
//        int sum = reviewsInTopic.stream().mapToInt(Review::getCount).sum();
//        return Integer.toString(sum);
        return "1";
    }

    private void updateView() throws PersistenceException {
        List<Topic> topics = (List<Topic>)topicService.findAll();
        grid.setItems(topics);
    }

    private void updateView(String search) throws PersistenceException {
        List<Topic> topics = (List<Topic>)topicService.findByNameLike(search);
        grid.setItems(topics);
    }


    private void saveTopic(Topic topic,
            AbstractEditorDialog.Operation operation) throws PersistenceException {
        topicService.save(topic);

        Notification.show(
                "Topic successfully " + operation.getNameInText() + "ed.", 3000, Position.BOTTOM_START);
        updateView();
    }

    private void deleteTopic(Topic topic) throws PersistenceException {
//        List<Review> reviewsInTopic = ReviewService.getInstance()
//                .findReviews(Topic.getName());
//
//        reviewsInTopic.forEach(review -> {
//            review.setTopic(TopicService.getInstance()
//                    .findTopicOrThrow("Undefined"));
//            ReviewService.getInstance().saveReview(review);
//        });
//        TopicService.getInstance().deleteTopic(Topic);
        topicService.delete(topic);
        Notification.show("Topic successfully deleted.", 3000, Position.BOTTOM_START);
        updateView();
    }
}
