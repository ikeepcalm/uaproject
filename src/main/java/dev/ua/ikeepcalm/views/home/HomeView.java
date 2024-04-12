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
        H1 title = new H1("Uaproject Reborn");
        title.addClassName(LumoUtility.Margin.Bottom.NONE);
        title.addClassName(LumoUtility.TextColor.SECONDARY);
        H3 desc = new H3("Український Minecraft сервер");
        desc.addClassName(LumoUtility.TextColor.TERTIARY);
        desc.addClassName(LumoUtility.Margin.Top.NONE);
        H5 more = new H5("Скоро тут буде купа тексту, а поки...");
        more.addClassName(LumoUtility.TextColor.TERTIARY);
        more.addClassName(LumoUtility.Margin.Top.XLARGE);

        Footer footer = new Footer();
        H6 copyright = new H6("© 2024 uaproject / ikeepcalm");
        copyright.addClassName(LumoUtility.TextColor.TERTIARY);
        footer.add(copyright);
        footer.addClassNames(LumoUtility.Display.FLEX, LumoUtility.JustifyContent.CENTER);
        footer.getStyle().set("padding", "20px");
        footer.getStyle().set("width", "100%");

        header.add(logo);
        header.add(title);
        header.add(desc);
        header.add(more);
        header.add(footer);
        contentLayout.add(header);
        scroller.setContent(contentLayout);
        add(scroller);



    }
}
