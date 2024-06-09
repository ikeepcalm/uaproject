package dev.ua.ikeepcalm.views.wiki;

import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import dev.ua.ikeepcalm.views.MainLayout;

import java.util.List;

@PageTitle("Вікіпедія")
@Route(value = "wiki", layout = MainLayout.class)
public class WikiView extends VerticalLayout {

    public WikiView() {

        WikiGroup generalQuestions = new WikiGroup("Загальні питання");
        generalQuestions.addQuestion(new WikiCard("Скільки йде сезон на сервері?", "Все залежить від активу на сервері, виходу нових версій, непередбачуваних ситуацій і ще невеличкого списку причин. В середньому тривалість одного сезону не перевищує 6 місяців"));
        generalQuestions.addQuestion(new WikiCard("Як доєднатися до адміністрації?", "Зараз, на жаль, ніяк. За потреби, у Дискорд-сервері буде оголошений набір на одну з ігрових ролей, і ви зможете спробувати свої сили у відкритому змаганні бажаючих"));
        generalQuestions.addQuestion(new WikiCard("Чому адміністрація мені не відповідає?", "Команда проєкту намагається відповідати всім, в чатах, розділах тех. підтримки, у грі, тощо, але деколи повідомлення плутаються між собою та зникають з поля зору. Переконайтеся, що повідомлення має зміст, ви чітко описали суть проблеми і предоставили усю необхідну інформацію про ситуацію, що склалася, і ненав'язливо нагадайте про це адміністрації"));
        generalQuestions.addQuestion(new WikiCard("Чому я не можу зайти на сервер?", "Причин може бути багато: від технічних проблем на сервері до проблем з вашим інтернет-провайдером. Переконайтеся, що ви використовуєте адекватний лаунчер, виберіть версію гри, яка підтримується сервером, перевірте ваше інтернет-з'єднання, перезапустіть гру, перезапустіть комп'ютер, перевірте статус сервера на нашому сайті або в нашому Дискорд-сервері"));

        WikiGroup rolesQuestions = new WikiGroup("Ігрові ролі");
        rolesQuestions.addQuestion(new WikiCard("Що дає роль Гостя?", "Роль гостя надає будь-якому ентузіасту можливість спробувати сервер перед реєстрацією. Ви не зможете взаємодіяти з іншими гравцями, будівлями, предметами, мобами, тощо, але зможете спостерігати за грою, щоб зрозуміти, чи варто вам грати на цьому сервері"));
        rolesQuestions.addQuestion(new WikiCard("Що дає роль Гравця?", "Роль гравця дає можливість взаємодіяти з іншими гравцями, будівлями, предметами, мобами, тощо. Ви зможете вільно грати на сервері, будувати, спілкуватися з іншими гравцями, тощо в радіусі дії своєї ролі"));
        rolesQuestions.addQuestion(new WikiCard("Що дає роль Спонсора?", "Ви можете заснувати місто певної території, яка відповідає рівню спонсорства. До того ж ви отримуєте доступ до декільнох можливостей зробити із гостів (можливо, своїх друзів) повноцінних гравців, і обмежену кількість глобальних повідомлень на весь сервер поштою!"));
        rolesQuestions.addQuestion(new WikiCard("Як отримати роль Спонсора?", "Більше про це можна дізнатися у розділі \"Крамниця\" нашого сайту. Там ви знайдете усю необхідну інформацію про можливості, вартість, умови отримання ролі спонсора"));

        WikiGroup guestQuestions = new WikiGroup("Режим гостя");
        guestQuestions.addQuestion(new WikiCard("Чому я нічого не можу робити?", "Якщо ви в режимі гостя, то ви не маєте можливості взаємодіяти з іншими гравцями, будівлями, предметами, мобами, тощо. Це зроблено для того, щоб ви могли спокійно спостерігати за грою, але не заважати гравцям займатися своїми справами"));
        guestQuestions.addQuestion(new WikiCard("Чому я не можу писати у чат?", "Якщо ви в режимі гостя, то ви не маєте можливості бачити чат. Це зроблено для того, щоб ви могли спокійно спостерігати за грою, але не відволікатися на чат"));
        guestQuestions.addQuestion(new WikiCard("Як змінити свою роль до Гравця?", "Просто виконайте усі умови для цього - авторизуйтеся на сайті, заповніть анкету, дочекайтеся її перевірки / придбайте допуск. Якщо ви правильно вказали нікнейм, щойно умови будуть виконані, ви автоматично отримаєте роль гравця"));
        guestQuestions.addQuestion(new WikiCard("Як інший Гравець може витягти мене із гостів?", "Така можливість доступна лише для Спонсорів, якщо вони ще не використали усі свої спроби, відповідно їх рівню. Робиться це приблизно так: /graylist add <ім'я>"));

        WikiGroup wealthQuestions = new WikiGroup("Wealth підписки");
        wealthQuestions.addQuestion(new WikiCard("Які є види підписки на сервері?", "На сервері доступні чотири види підписки, більше ви можете дізнатися у розділі Крамниці. Вони відрізняються один від одного кількістю можливостей, які вони надають"));
        wealthQuestions.addQuestion(new WikiCard("Підписку потрібно поновлювати щомісяця?", "Так, ви вгадали. Підписку потрібно поновлювати щомісяця, щоб ви могли користуватися усіма можливостями, які вона надає"));
        wealthQuestions.addQuestion(new WikiCard("Які переваги дає активна підписка?", """
                З головного:\s
                - ви можете заснувати місто із територією відповідною до рівня підписки;\s
                - ви отримуєте доступ до приватного каналу на сервері;\s
                - ви отримуєте унікальну роль на сервері;\s
                - ви можете самостійно додавати певну кількість гостів у грейліст;\s
                - ви отримуєте можливість відправляти повідомлення усім гравцям на сервері обмежену кількість разів у день"""));

        WikiGroup economyQuestions = new WikiGroup("Економіка сервера");
        economyQuestions.addQuestion(new WikiCard("Що таке Гаманець?", "Гаманець - 'магічний' предмет, у якому ви можете зберігати власні кошти на ігровому сервері. Візьміть його в руку і натисніть ПКМ!"));
        economyQuestions.addQuestion(new WikiCard("Як отримати Гаманець?", "Його потрібно скрафтити! Для цього вам знадобиться: 6 шкіри, 2 паперу, 1 візер-троянда. Крафт виглядає наступним чином:", "images/wallet.png"));
        economyQuestions.addQuestion(new WikiCard("Яка ж валюта сервера?", "На сервері є три різновиди валюти: Копійки, Ліки і Аури. Шістьдесят копійок дорівнюють одному ліку, а шістдесят ліків дорівнюють одному ауру"));
        economyQuestions.addQuestion(new WikiCard("Як заробляти гроші?", "Для цього існує цікава механіка: оцінка. Обираєте будь-який предмет, і переносите його у вміст Гаманця, бачите оцінку у Копійках, після чого підтверджуєте продаж або відміняєте!"));

        FlexLayout cardLayout = new FlexLayout();
        cardLayout.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
        cardLayout.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        cardLayout.setWidthFull();
        cardLayout.addClassName(LumoUtility.Margin.Bottom.SMALL);

        for (WikiGroup group : List.of(generalQuestions, rolesQuestions, guestQuestions, wealthQuestions, economyQuestions)) {
            Div content = new Div();
            content.addClassNames(LumoUtility.FlexWrap.WRAP, LumoUtility.Overflow.HIDDEN, LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN, LumoUtility.AlignItems.START, LumoUtility.Width.FULL);
            H2 header = new H2(group.getHeader());
            header.getStyle().set("margin-bottom", "20px");
            content.add(header);

            for (WikiCard question : group.getQuestions()) {
                Details details = new Details();
                details.setSummaryText(question.question());
                details.getStyle().set("padding", "10px");

                TextArea answer = new TextArea();
                answer.setValue(question.answer());
                answer.setReadOnly(true);
                answer.setLabel("");
                answer.setHeight("auto");
                answer.setWidthFull();

                VerticalLayout verticalLayout = new VerticalLayout();
                verticalLayout.getStyle().setAlignItems(Style.AlignItems.CENTER);
                verticalLayout.getStyle().setDisplay(Style.Display.FLEX);
                verticalLayout.add(answer);

                details.addThemeVariants(DetailsVariant.FILLED);
                details.setWidthFull();
                details.getStyle().set("margin-bottom", "10px");

                if (question.filePath() != null) {
                    Image image = new Image(question.filePath(), "Image");
                    image.setWidth("50%");
                    image.getStyle().set("margin-top", "10px");
                    verticalLayout.add(image);
                }

                details.add(verticalLayout);

                content.add(details);
                cardLayout.add(content);
            }
        }
        add(cardLayout);
    }
}