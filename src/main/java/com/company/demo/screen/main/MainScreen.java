package com.company.demo.screen.main;

import io.jmix.ui.ScreenTools;
import io.jmix.ui.component.*;
import io.jmix.ui.component.mainwindow.Drawer;
import io.jmix.ui.component.mainwindow.SideMenu;
import io.jmix.ui.icon.JmixIcon;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nullable;

@UiController("demo_MainScreen")
@UiDescriptor("main-screen.xml")
@Route(path = "main", root = true)
public class MainScreen extends Screen implements Window.HasWorkArea {

    private static boolean SELECT_ON_CLICK = false;

    @Autowired
    private ScreenTools screenTools;

    @Autowired
    private AppWorkArea workArea;
    @Autowired
    private Drawer drawer;
    @Autowired
    private Button collapseDrawerButton;
    @Autowired
    private SideMenu sideMenu;
    @Autowired
    private CheckBox showClicked;

    @Subscribe
    public void onInit(InitEvent event) {
        sideMenu.setSelectOnClick(SELECT_ON_CLICK);
        sideMenu.getMenuItems().forEach(menuItem -> menuItem.setExpanded(true));
        sideMenu.getMenuItemNN("demo_User.browse").setBadgeText("10");

        showClicked.setValue(SELECT_ON_CLICK);
        showClicked.addValueChangeListener(this::onShowClickedValueChange);
    }

    public void onShowClickedValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        SELECT_ON_CLICK = Boolean.TRUE.equals(event.getValue());
        reloadMainScreen();
    }

    private void reloadMainScreen() {
        UiControllerUtils.getScreenContext(this).getScreens()
                .create(MainScreen.class, OpenMode.ROOT)
                .show();
    }

    @Subscribe("collapseDrawerButton")
    private void onCollapseDrawerButtonClick(Button.ClickEvent event) {
        drawer.toggle();
        if (drawer.isCollapsed()) {
            collapseDrawerButton.setIconFromSet(JmixIcon.CHEVRON_RIGHT);
        } else {
            collapseDrawerButton.setIconFromSet(JmixIcon.CHEVRON_LEFT);
        }
    }

    @Nullable
    @Override
    public AppWorkArea getWorkArea() {
        return workArea;
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        screenTools.openDefaultScreen(
                UiControllerUtils.getScreenContext(this).getScreens());

        screenTools.handleRedirect();
    }
}
