package com.k41d.cms.interfaces.view.admin;

import com.k41d.cms.interfaces.view.admin.views.topics.TopicsList;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcons;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.page.BodySize;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

/**
 * The main view contains a simple label element and a template element.
 */
@HtmlImport("styles/shared-styles.html")
@Route("")
@Theme(Lumo.class)
@BodySize(height = "100vh", width = "100vw")
public class MainLayout extends Div implements RouterLayout,
        AfterNavigationObserver, PageConfigurator {

    private static final String ACTIVE_ITEM_STYLE = "main-layout__nav-item--selected";
    private RouterLink topics;
//    private RouterLink reviews;


    public MainLayout() {
        H2 title = new H2("Leyline CMS");
        title.addClassName("main-layout__title");

//        reviews = new RouterLink(null, ReviewsList.class);
//        reviews.add(new Icon(VaadinIcons.LIST), new Text("Reviews"));
//        reviews.addClassName("main-layout__nav-item");

        topics = new RouterLink(null, TopicsList.class);
        topics.add(new Icon(VaadinIcons.ARCHIVES), new Text("Topics"));
        topics.addClassName("main-layout__nav-item");

//        Div navigation = new Div(reviews, topics);

        Div navigation = new Div(topics);
        navigation.addClassName("main-layout__nav");

        Div header = new Div(title, navigation);
        header.addClassName("main-layout__header");
        add(header);

        addClassName("main-layout");
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        // updating the active menu item based on if either of views is active
        // (note that this is triggered even for the error view)
        String segment = event.getLocation().getFirstSegment();
//        boolean reviewsActive = segment.equals(reviews.getHref());
        boolean topicsActive = segment.equals(topics.getHref());

//        reviews.setClassName(ACTIVE_ITEM_STYLE, reviewsActive);
        topics.setClassName(ACTIVE_ITEM_STYLE, topicsActive);
    }

    @Override
    public void configurePage(InitialPageSettings settings) {
        settings.addMetaTag("apple-mobile-web-app-capable", "yes");
        settings.addMetaTag("apple-mobile-web-app-status-bar-style", "black");
    }

}
