package dev.ua.ikeepcalm.views.features;

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
import com.vaadin.flow.theme.lumo.LumoUtility;
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
import org.jetbrains.annotations.NotNull;

@PageTitle("Механіки")
@Route(value = "features", layout = MainLayout.class)
public class FeaturesView extends Main implements HasComponents, HasStyle {

    private OrderedList imageContainer;

    public FeaturesView() {
        constructUI();

        imageContainer.add(
                new FeatureCard(
                        "Кастомна генерація",
                        "Чесно кажучи, ванільна генерація майнкрафту вже набридла. Ми вирішили це виправити, додавши кастомні структури і покращивши генерацію світу!",
                        "images/generation.jpg",
                        "Minecraft")
                );

        imageContainer.add(
                new FeatureCard(
                        "Незвичний чат",
                        "Як писати в глобальний чат? - Доволі часте запитання на всіх серверах, але не у нас. У нас просто немає глобального чату, а отже, щоб увесь світ дізнався про ваші подвиги, треба постаратися!",
                        "images/chat.jpg",
                        "Minecraft")
        );


        imageContainer.add(
                new FeatureCard(
                        "Підтримка Бедроку",
                        "Ми підтримуємо версію гри для мобільних пристроїв, що дозволяє грати на сервері з будь-якого пристрою. Звісно, нащо так робити не зрозуміло, але у нас - можна!",
                        "images/bedrock.jpg",
                        "Minecraft")
        );

        imageContainer.add(
                new FeatureCard(
                        "Містичний світ",
                        "Вірус, який тероризував гравців у четвертому сезоні, щось пробудив у недрах світу. Звичні простори розширюються, а вирують нові сили. Чи зможете ви зупинити цю загрозу?",
                        "images/mystical.jpg",
                        "Minecraft")
        );

        imageContainer.add(
                new FeatureCard(
                        "Режим гостя",
                        "Необхідність заповнювати анкету або покупати допуск на сервер, щоб вперше зайти, насправді, дуже дратує. Саме тому ми додали режим гостя, який дозволяє вам зайти на сервер навіть зараз!",
                        "images/spectator.jpg",
                        "Minecraft")
        );

        imageContainer.add(
                new FeatureCard(
                        "Програма авторства",
                        "Ми підтримуємо авторів контенту, які хочуть знімати відео або проводити стріми на нашому сервері. Для них ми розробили спеціальну програму, яка дозволяє отримати доступ до унікальних можливостей!",
                        "images/author.jpg",
                        "Minecraft")
        );

        imageContainer.add(
                new FeatureCard(
                        "Мі-9",
                        "Система покарань 'покараний-модератор-бан' хоч і працює, але зовсім нецікаво. Тому на нашому сервері діє внутрішньоігрова поліція, яка відповідає за порядок на сервері. Це дозволяє гравцям самим вирішувати конфлікти і вирішувати питання порушення правил!",
                        "images/mi9.jpg",
                        "Minecraft")
        );

        imageContainer.add(
                new FeatureCard(
                        "Цікава економіка",
                        "Очікували звичну консольну економіку у нас? Тут ви й помилилися! Ще одна особливість нашого серверу - унікальна економічна система. Наприклад, гравець може зробити свій власний гаманець, який треба буде обов'язково носити із собою, щоб мати доступ до своїх багатств!",
                        "images/economy.webp",
                        "Minecraft")
        );

        imageContainer.add(
                new FeatureCard(
                        "Войс-чат & Емоції",
                        "Ми підтримуємо голосовий чат, який дозволяє вам спілкуватися з іншими гравцями без обмежень. Також у нас є власна система емоцій, яка дозволяє вам виразити свої почуття у грі! Звісно, якщо ви захочете, ніхто нікого не змушує. Поки.",
                        "images/voicechat.jpg",
                        "Minecraft")
        );


        imageContainer.add(
                new FeatureCard(
                        "Цікаві івенти",
                        "Якщо вам набридло збирати ресурси і будувати, ми готові вас розважити. Щотижня ми проводимо цікаві івенти, які дозволяють вам відпочити від рутини і отримати приємні бонуси!",
                        "images/events.jpg",
                        "Discord")
        );

        imageContainer.add(
                new FeatureCard(
                        "Активне спілкування",
                        "Якщо вам більше по душі спілкуватися з гравцями, ніж займатися будівництвом, ми маємо для вас щось особливе. На нашому сервері діє рівнева система, яка дозволяє вам отримувати бонуси за активність і взаємодію з іншими гравцями!",
                        "images/levels.jpg",
                        "Discord")
        );

        imageContainer.add(
                new FeatureCard(
                        "Спойлери, новини і анонси",
                        "Дискорд проєкту - основне місце взаємодії адміністрації з гравцями. Тут ми публікуємо спойлери, новини і анонси, які дозволяють вам бути в курсі всіх подій на сервері!",
                        "images/discord.jpg",
                        "Discord")
        );

    }

    private void constructUI() {
        addClassNames("features-view");
        addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);

        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);

        VerticalLayout headerContainer = getVerticalLayout();
        add(headerContainer);

        imageContainer = new OrderedList();
        imageContainer.addClassNames(Gap.MEDIUM, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE);

        add(container, imageContainer);
    }

    @NotNull
    private static VerticalLayout getVerticalLayout() {
        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("Механіки серверу");
        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);
        Paragraph description = new Paragraph("Ті самі унікальні речі, які ви не зустрінете на інших серверах");
        description.addClassNames(Margin.Bottom.SMALL, Margin.Top.NONE, TextColor.SECONDARY, Display.FLEX, LumoUtility.FlexDirection.COLUMN, AlignItems.CENTER);
        headerContainer.add(header, description);
        return headerContainer;
    }
}
