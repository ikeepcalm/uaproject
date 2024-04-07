package dev.ua.ikeepcalm.views.features;

import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.ListStyleType;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.MaxWidth;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;
import dev.ua.ikeepcalm.views.MainLayout;

@PageTitle("Механіки")
@Route(value = "features", layout = MainLayout.class)
public class FeaturesView extends Main implements HasComponents, HasStyle {

    private OrderedList imageContainer;

    public FeaturesView() {
        constructUI();

        imageContainer.add(
                new FeatureViewCard(
                        "Кастомна генерація",
                        "Чесно кажучи, ванільна генерація майнкрафту вже набридла. Ми вирішили це виправити, додавши кастомні структури і покращивши генерацію світу!",
                        "images/generation.jpg",
                        "Minecraft")
                );

        imageContainer.add(
                new FeatureViewCard(
                        "Незвичний чат",
                        "Як писати в глобальний чат? - Доволі часте запитання на всіх серверах, але не у нас. У нас просто немає глобального чату, а отже, щоб увесь світ дізнався про ваші подвиги, треба постаратися!",
                        "images/chat.jpg",
                        "Minecraft")
        );


        imageContainer.add(
                new FeatureViewCard(
                        "Підтримка Бедроку",
                        "Ми підтримуємо версію гри для мобільних пристроїв, що дозволяє грати на сервері з будь-якого пристрою. Звісно, нащо так робити не зрозуміло, але у нас - можна!",
                        "images/bedrock.jpg",
                        "Minecraft")
        );

        imageContainer.add(
                new FeatureViewCard(
                        "Містичність",
                        "Вірус, який тероризував гравців у четвертому сезоні, щось пробудив у недрах світу. Звичні простори розширюються, а вирують нові сили. Чи зможете ви зупинити цю загрозу?",
                        "images/mystical.jpg",
                        "Minecraft")
        );

        imageContainer.add(
                new FeatureViewCard(
                        "Режим гостя",
                        "Необхідність заповнювати анкету або покупати допуск на сервер, щоб вперше зайти, насправді, дуже дратує. Саме тому ми додали режим гостя, який дозволяє вам зайти на сервер і подивитися на його роботу без жодних обмежень!",
                        "images/spectator.jpg",
                        "Minecraft")
        );

        imageContainer.add(
                new FeatureViewCard(
                        "Цікаві івенти",
                        "Якщо вам набридло збирати ресурси і будувати, ми готові вас розважити. Щотижня ми проводимо цікаві івенти, які дозволяють вам відпочити від рутини і отримати приємні бонуси!",
                        "images/discord.jpg",
                        "Discord")
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