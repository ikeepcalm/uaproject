package dev.ua.ikeepcalm.views.home;

import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility;
import dev.ua.ikeepcalm.views.MainLayout;

@PageTitle("Головна")
@Route(value = "home", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class HomeView extends VerticalLayout {

    public HomeView() {
        setSpacing(false);
        setPadding(false);
        setSizeFull();

        Scroller scroller = new Scroller();
        scroller.getStyle().set("overflow-y", "auto");
        scroller.getStyle().set("max-height", "100vh");

        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.getStyle().set("padding", "20px");
        contentLayout.setWidthFull();
        Div content = new Div();
        Div imageTextContainer = new Div();
        imageTextContainer.addClassName("background-overlay-container");
        Image image = new Image("images/background.webp", "Background image");
        image.addClassName("background-overlay");
        imageTextContainer.add(image);

        content.add(imageTextContainer);
        contentLayout.add(content);

        Div header = new Div();
        header.addClassNames(LumoUtility.AlignItems.CENTER, LumoUtility.JustifyContent.CENTER, LumoUtility.Display.FLEX, LumoUtility.Padding.Horizontal.LARGE, LumoUtility.Padding.Vertical.LARGE, LumoUtility.Width.FULL, LumoUtility.Margin.NONE, LumoUtility.BoxSizing.BORDER, LumoUtility.FlexDirection.COLUMN, LumoUtility.MaxWidth.FULL, LumoUtility.Margin.AUTO);
        Image logo = new Image("images/uaproject-pfp.png", "Logo");
        logo.getStyle().set("width", "100px");
        header.getStyle().set("width", "100%");
        H1 h1 = new H1("Uaproject Reborn");
        h1.addClassName(LumoUtility.Margin.Bottom.NONE);
        h1.addClassName(LumoUtility.TextColor.SECONDARY);
        H3 h2 = new H3("Український Minecraft сервер");
        h2.addClassName(LumoUtility.TextColor.TERTIARY);
        h2.addClassName(LumoUtility.Margin.Top.NONE);
        header.add(logo);
        header.add(h1);
        header.add(h2);
        contentLayout.add(header);

        scroller.setContent(contentLayout);
        add(scroller);
    }
}
