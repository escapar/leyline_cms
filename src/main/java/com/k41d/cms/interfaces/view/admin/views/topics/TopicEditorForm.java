package com.k41d.cms.interfaces.view.admin.views.topics;

import com.k41d.cms.business.domain.topic.Topic;
import com.k41d.cms.business.service.TopicService;
import com.k41d.cms.interfaces.view.admin.MainLayout;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.data.binder.BindingValidationStatus;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.stream.Collectors;

@SpringComponent
@Route(value = "topics/edit", layout = MainLayout.class)
@PageTitle("Topics Edit")
public class TopicEditorForm extends FormLayout{

    private final TopicService service;

    @Autowired
    public TopicEditorForm(TopicService topicService){
        service = topicService;
    }

    public void init(){
        Binder<Topic> binder = new Binder<>();

// The object that will be edited
        Topic contactBeingEdited = new Topic();

// Create the fields
        TextField firstName = new TextField();
        firstName.setValueChangeMode(ValueChangeMode.EAGER);
        TextField lastName = new TextField();
        lastName.setValueChangeMode(ValueChangeMode.EAGER);
        TextField phone = new TextField();
        phone.setValueChangeMode(ValueChangeMode.EAGER);
        TextField email = new TextField();
        email.setValueChangeMode(ValueChangeMode.EAGER);
        DatePicker birthDate = new DatePicker();
        Checkbox doNotCall = new Checkbox("Do not call");
        Label infoLabel = new Label();
        NativeButton save = new NativeButton("Save");
        NativeButton reset = new NativeButton("Reset");

        this.addFormItem(firstName, "First name");
        this.addFormItem(lastName, "Last name");
        this.addFormItem(birthDate, "Birthdate");
        this.addFormItem(email, "E-mail");
        FormLayout.FormItem phoneItem = this.addFormItem(phone, "Phone");
        phoneItem.add(doNotCall);

// Button bar
        HorizontalLayout actions = new HorizontalLayout();
        actions.add(save, reset);
        save.getStyle().set("marginRight", "10px");
//
//        SerializablePredicate<String> phoneOrEmailPredicate = value -> !phone
//                .getValue().trim().isEmpty()
//                || !email.getValue().trim().isEmpty();
//
//// E-mail and phone have specific validators
//        Binder.Binding<Topic, String> emailBinding = binder.forField(email)
//                .withValidator(phoneOrEmailPredicate,
//                        "Both phone and email cannot be empty")
//                .withValidator(new EmailValidator("Incorrect email address"))
//                .bind(Topic::getEmail, Topic::setEmail);
//
//        Binder.Binding<Topic, String> phoneBinding = binder.forField(phone)
//                .withValidator(phoneOrEmailPredicate,
//                        "Both phone and email cannot be empty")
//                .bind(Topic::getPhone, Topic::setPhone);
//
//// Trigger cross-field validation when the other field is changed
//        email.addValueChangeListener(event -> phoneBinding.validate());
//        phone.addValueChangeListener(event -> emailBinding.validate());

// First name and last name are required fields
        firstName.setRequiredIndicatorVisible(true);
        lastName.setRequiredIndicatorVisible(true);

        binder.forField(firstName)
                .withValidator(new StringLengthValidator(
                        "Please add the first name", 1, null))
                .bind(Topic::getName, Topic::setName);
//        binder.forField(lastName)
//                .withValidator(new StringLengthValidator(
//                        "Please add the last name", 1, null))
//                .bind(topic->topic.getLatest().getTitle(), topic->topic.getLatest().setTitle());
//
//// Birthdate and doNotCall don't need any special validators
//        binder.bind(doNotCall, Topic::isDoNotCall, Topic::setDoNotCall);
//        binder.bind(birthDate, Topic::getBirthDate, Topic::setBirthDate);

// Click listeners for the buttons
        save.addClickListener(event -> {
            if (binder.writeBeanIfValid(contactBeingEdited)) {
                infoLabel.setText("Saved bean values: " + contactBeingEdited);
            } else {
                BinderValidationStatus<Topic> validate = binder.validate();
                String errorText = validate.getFieldValidationStatuses()
                        .stream().filter(BindingValidationStatus::isError)
                        .map(BindingValidationStatus::getMessage)
                        .map(Optional::get).distinct()
                        .collect(Collectors.joining(", "));
                infoLabel.setText("There are errors: " + errorText);
            }
        });
        reset.addClickListener(event -> {
            // clear fields by setting null
            binder.readBean(null);
            infoLabel.setText("");
            doNotCall.setValue(false);
        });
    }
}
