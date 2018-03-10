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
package com.k41d.cms.interfaces.view.admin.views.topics;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.k41d.cms.business.domain.topic.Topic;
import com.k41d.cms.business.service.TopicService;
import com.k41d.cms.interfaces.view.admin.common.AbstractEditorDialog;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A dialog for editing {@link Topic} objects.
 */
@SpringComponent
public class TopicEditorDialog extends AbstractEditorDialog<Topic> {

    private final TextField categoryNameField = new TextField("Topic Name");

    @Autowired
    TopicService topicService;

    public TopicEditorDialog(){

    }
    public TopicEditorDialog(BiConsumer<Topic, Operation> itemSaver,
                             Consumer<Topic> itemDeleter) {
        super("Topic", itemSaver, itemDeleter);

        addNameField();
    }

    private void addNameField() {
        getFormLayout().add(categoryNameField);

        getBinder().forField(categoryNameField)
                .withConverter(String::trim, String::trim)
                .withValidator(new StringLengthValidator(
                        "Topic name must contain at least 3 printable characters",
                        3, null))
                .withValidator(
                        name -> topicService.findByName(name).size() == 0,
                        "Topic name must be unique")
                .bind(Topic::getName, Topic::setName);
    }

    @Override
    protected void confirmDelete() {
//        int reviewCount = ReviewService.getInstance()
//                .findReviews(getCurrentItem().getName()).size();
        int reviewCount = 1;
        if (reviewCount > 0) {
            openConfirmationDialog(
                    "Delete Topic “" + getCurrentItem().getName() + "”?",
                    "There are " + reviewCount
                            + " reviews associated with this category.",
                    "Deleting the category will mark the associated reviews as “undefined”."
                            + "You may link the reviews to other categories on the edit page.");
        }
    }
}
