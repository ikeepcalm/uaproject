package dev.ua.ikeepcalm.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.vaadin.lineawesome.LineAwesomeIcon;


public class MainLayout extends AppLayout {

    public static class MenuItemInfo extends ListItem {

        private final Class<? extends Component> view;

        public MenuItemInfo(String menuTitle, Component icon, Class<? extends Component> view) {
            this.view = view;
            RouterLink link = new RouterLink();
            link.addClassNames(LumoUtility.Display.FLEX, LumoUtility.Gap.XSMALL, LumoUtility.Height.MEDIUM, LumoUtility.AlignItems.CENTER, LumoUtility.Padding.Horizontal.SMALL,
                    LumoUtility.TextColor.BODY);
            link.setRoute(view);

            Span text = new Span(menuTitle);
            text.addClassNames(LumoUtility.FontWeight.MEDIUM, LumoUtility.FontSize.MEDIUM, LumoUtility.Whitespace.NOWRAP);

            if (icon != null) {
                link.add(icon);
            }
            link.add(text);
            add(link);
        }

        public Class<?> getView() {
            return view;
        }

    }

    public MainLayout() {
        addToNavbar(true, createHeaderContent());
        setDrawerOpened(false);
    }

    private Component createHeaderContent() {
        Header header = new Header();
        header.addClassNames(LumoUtility.BoxSizing.BORDER, LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN, LumoUtility.Width.FULL);

        Div layout = new Div();
        layout.addClassNames(LumoUtility.Display.FLEX, LumoUtility.AlignItems.CENTER, LumoUtility.Padding.Horizontal.LARGE, LumoUtility.JustifyContent.CENTER);

        Nav nav = new Nav();
        nav.addClassNames(LumoUtility.Display.FLEX, LumoUtility.Overflow.AUTO, LumoUtility.Padding.Horizontal.MEDIUM, LumoUtility.Padding.Vertical.XSMALL, LumoUtility.JustifyContent.CENTER);

        UnorderedList list = new UnorderedList();

        nav.add(list);

        UI.getCurrent().getPage().retrieveExtendedClientDetails(details -> {
            if (details.getScreenWidth() < 1200) {
                for (ListItem listItem : createMenuIcons()) {
                    list.add(listItem);
                    list.addClassNames(LumoUtility.Display.FLEX, LumoUtility.Gap.XSMALL, LumoUtility.ListStyleType.NONE, LumoUtility.Margin.NONE, LumoUtility.Padding.NONE);
                }
            } else {
                for (ListItem listItem : createMenuItems()) {
                    list.add(listItem);
                    list.addClassNames(LumoUtility.Display.FLEX, LumoUtility.Gap.SMALL, LumoUtility.ListStyleType.NONE, LumoUtility.Margin.NONE, LumoUtility.Padding.NONE);
                }
            }
        });

        header.add(layout, nav);
        return header;
    }


    private ListItem[] createMenuItems() {
        return new ListItem[]{
                new MenuItemInfo("Головна", LineAwesomeIcon.HOME_SOLID.create(), dev.ua.ikeepcalm.views.home.HomeView.class),
                new MenuItemInfo("Механіки", LineAwesomeIcon.FEATHER_ALT_SOLID.create(), dev.ua.ikeepcalm.views.features.FeaturesView.class),
                new MenuItemInfo("Правила", LineAwesomeIcon.PAPERCLIP_SOLID.create(), dev.ua.ikeepcalm.views.rules.RulesView.class),
                new MenuItemInfo("Вікіпедія", LineAwesomeIcon.WIKIPEDIA_W.create(), dev.ua.ikeepcalm.views.wiki.WikiView.class),
                new MenuItemInfo("Магазин", LineAwesomeIcon.SHOPPING_BASKET_SOLID.create(), dev.ua.ikeepcalm.views.shop.ShopView.class),
                new MenuItemInfo("Анкета", LineAwesomeIcon.PEN_ALT_SOLID.create(), dev.ua.ikeepcalm.views.form.FormView.class),
                new ExternalMenuItemInfo("↝", LineAwesomeIcon.DISCORD.create(), "https://discord.gg/nyAMvRru7x"),
                new ExternalMenuItemInfo("↝", LineAwesomeIcon.MAP.create(), "https://map.uaproject.xyz/"),
        };
    }

    private ListItem[] createMenuIcons() {
        return new ListItem[]{
                new MenuItemInfo("", LineAwesomeIcon.HOME_SOLID.create(), dev.ua.ikeepcalm.views.home.HomeView.class),
                new MenuItemInfo("", LineAwesomeIcon.FEATHER_ALT_SOLID.create(), dev.ua.ikeepcalm.views.features.FeaturesView.class),
                new MenuItemInfo("", LineAwesomeIcon.PAPERCLIP_SOLID.create(), dev.ua.ikeepcalm.views.rules.RulesView.class),
                new MenuItemInfo("", LineAwesomeIcon.WIKIPEDIA_W.create(), dev.ua.ikeepcalm.views.wiki.WikiView.class),
                new MenuItemInfo("", LineAwesomeIcon.SHOPPING_BASKET_SOLID.create(), dev.ua.ikeepcalm.views.shop.ShopView.class),
                new MenuItemInfo("", LineAwesomeIcon.PEN_ALT_SOLID.create(), dev.ua.ikeepcalm.views.form.FormView.class),
        };
    }

    private static class ExternalMenuItemInfo extends ListItem {

        public ExternalMenuItemInfo(String menuTitle, Component icon, String url) {
            Anchor link = new Anchor();
            link.addClassNames(LumoUtility.Display.FLEX, LumoUtility.Gap.XSMALL, LumoUtility.Height.MEDIUM, LumoUtility.AlignItems.CENTER, LumoUtility.Padding.Horizontal.SMALL,
                    LumoUtility.TextColor.BODY);
            link.setHref(url);
            link.setTarget("_blank");

            Span text = new Span(menuTitle);
            text.addClassNames(LumoUtility.FontWeight.MEDIUM, LumoUtility.FontSize.MEDIUM, LumoUtility.Whitespace.NOWRAP);

            if (icon != null) {
                link.add(icon);
            }
            link.add(text);
            add(link);
        }
    }

}
