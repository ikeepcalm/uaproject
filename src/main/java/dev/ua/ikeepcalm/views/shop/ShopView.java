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
                        "Допуск гравця",
                        "50",
                        """
                                ✧ Роль гравця на DS сервері
                                ✧ Моментальний доступ на сервер
                                ✧ Доступ до всіх функцій сервера""",
                        "images/discord.jpg",
                        "https://donatello.to/uaproject")
                );

        imageContainer.add(
                new ShopCard(
                        "Підписка Wealth-1",
                        "50",
                        """
                                ✧ Унікальна роль на DS сервері
                                ✧ Доступ до приватного каналу
                                ✧ Територія міста до 500 блоків
                                ✧ Грейліст - 1 гравець в місяць
                                ✧ Одне повідомлення поштою на день""",
                        "images/discord.jpg",
                        "https://donatello.to/uaproject")
        );

        imageContainer.add(
                new ShopCard(
                        "Підписка Wealth-2",
                        "100",
                        """
                                ✧ Унікальна роль на DS сервері
                                ✧ Доступ до приватного каналу
                                ✧ Територія міста до 800 блоків
                                ✧ Грейліст - 2 гравця в місяць
                                ✧ Два повідомлення поштою на день""",
                        "images/discord.jpg",
                        "https://donatello.to/uaproject")
        );

        imageContainer.add(
                new ShopCard(
                        "Підписка Wealth-3",
                        "150",
                        """
                                ✧ Унікальна роль на DS сервері
                                ✧ Доступ до приватного каналу
                                ✧ Територія міста до 1200 блоків
                                ✧ Грейліст - 3 гравця в місяць
                                ✧ Три повідомлення поштою на день""",
                        "images/discord.jpg",
                        "https://donatello.to/uaproject")
        );

        imageContainer.add(
                new ShopCard(
                        "Підписка Wealth-4",
                        "300",
                        """
                                ✧ Унікальна роль на DS сервері
                                ✧ Доступ до приватного каналу
                                ✧ Територія міста до 1800 блоків
                                ✧ Грейліст - 3 гравця в місяць
                                ✧ П'ять повідомлень поштою на день""",
                        "images/discord.jpg",
                        "https://donatello.to/uaproject")
        );

        imageContainer.add(
                new ShopCard(
                        "Nitro Booster",
                        "free",
                        """
                                ✧ Особлива роль на сервері
                                ✧ Доступ до приватного каналу
                                ✧ Дозвіл керувати войс-каналами""",
                        "images/discord.jpg",
                        "https://discord.gg/nyAMvRru7x",
                        "Приєднатися")
        );


    }

    private void constructUI() {
        addClassNames("features-view");
        addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);

        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);

        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("Магазин серверу");
        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);
        Paragraph description = new Paragraph("Хочете підтримати проєкт та отримати унікальні можливості? Тоді вам сюди!");
        description.addClassNames(Margin.Bottom.SMALL, Margin.Top.NONE, TextColor.SECONDARY, Display.FLEX, FlexDirection.COLUMN, AlignItems.CENTER);
        headerContainer.add(header, description);
        add(headerContainer);

        imageContainer = new OrderedList();
        imageContainer.addClassNames(Gap.MEDIUM, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE);

        add(container, imageContainer);
    }
}
