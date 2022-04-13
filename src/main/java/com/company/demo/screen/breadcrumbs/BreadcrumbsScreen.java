package com.company.demo.screen.breadcrumbs;

import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.component.Button;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("demo_BreadcrumbsScreen")
@UiDescriptor("breadcrumbs-screen.xml")
public class BreadcrumbsScreen extends Screen {
    @Autowired
    private ScreenBuilders screenBuilders;

    private int number = 1;

    @Subscribe
    public void onInit(InitEvent event) {
        updateCaption();
    }

    public void setNumber(int number) {
        this.number = number;

        updateCaption();
    }

    private void updateCaption() {
        getWindow().setCaption("Screen " + number);
    }

    @Subscribe("btn")
    public void onBtnClick(Button.ClickEvent event) {
        BreadcrumbsScreen screen = screenBuilders.screen(this)
                .withScreenClass(BreadcrumbsScreen.class)
                .build();

        screen.setNumber(number + 1);
        screen.show();
    }
}