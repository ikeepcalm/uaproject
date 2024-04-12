package dev.ua.ikeepcalm.views.shop;

import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.*;
import dev.ua.ikeepcalm.views.MainLayout;

@PageTitle("Магазин")
@Route(value = "shop", layout = MainLayout.class)
public class ShopView extends Main implements HasComponents, HasStyle {

    private OrderedList imageContainer;

    public ShopView() {
        constructUI();

        imageContainer.add(
                new ShopCard(
                        "Допуск Гравця",
                        "50",
                        """
                                ✧ Моментальний доступ на сервер
                                ✧ Доступна територія до 5,000 блоків
                                ✧ Доступ до всіх функцій сервера""",
                        "images/discord.jpg",
                        "https://donatello.to/uaproject")
                );

        imageContainer.add(
                new ShopCard(
                        "Підписка Wealth-1",
                        "50",
                        """
                                ✧ Доступна територія до 6,000 блоків
                                ✧ Грейліст: 1 гравець в місяць
                                ✧ Доступ до всіх функцій сервера""",
                        "images/discord.jpg",
                        "https://donatello.to/uaproject")
        );

        imageContainer.add(
                new ShopCard(
                        "Підписка Wealth-2",
                        "100",
                        """
                                ✧ Доступна територія до 7,000 блоків
                                ✧ Грейліст: 2 гравця в місяць
                                ✧ Доступ до всіх функцій сервера""",
                        "images/discord.jpg",
                        "https://donatello.to/uaproject")
        );

        imageContainer.add(
                new ShopCard(
                        "Підписка Wealth-3",
                        "150",
                        """
                                ✧ Доступна територія до 8,000 блоків
                                ✧ Грейліст: 3 гравці в місяць
                                ✧ Доступ до всіх функцій сервера""",
                        "images/discord.jpg",
                        "https://donatello.to/uaproject")
        );

    }

    private void constructUI() {
        addClassNames("features-view");
        addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);

        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);

        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("Основні механіки серверу");
        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);
        Paragraph description = new Paragraph("Ті самі унікальні речі, які ви не зустрінете на інших серверах");
        description.addClassNames(Margin.Bottom.XLARGE, Margin.Top.NONE, TextColor.SECONDARY);
        headerContainer.add(header, description);


        imageContainer = new OrderedList();
        imageContainer.addClassNames(Gap.MEDIUM, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE);

        add(container, imageContainer);
    }
}
