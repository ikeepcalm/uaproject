package dev.ua.ikeepcalm.views.wiki;

import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import dev.ua.ikeepcalm.views.MainLayout;

import java.util.ArrayList;
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
        rolesQuestions.addQuestion(new WikiCard("Що дає роль Спонсора?", "Роль спонсора збільшує радіус ігрового світу, на якому ви можете будувати, взаємодіяти з іншими гравцями, тощо. Також ви отримуєте доступ до декільнох можливостей зробити із гостів (можливо, своїх друзів) повноцінних гравців"));
        rolesQuestions.addQuestion(new WikiCard("Як отримати роль Спонсора?", "Більше про це можна дізнатися у розділі Магазин нашого сайту. Там ви знайдете усю необхідну інформацію про можливості, вартість, умови отримання ролі спонсора"));

        WikiGroup guestQuestions = new WikiGroup("Режим гостя");
        guestQuestions.addQuestion(new WikiCard("Чому я нічого не можу робити?", "Якщо ви в режимі гостя, то ви не маєте можливості взаємодіяти з іншими гравцями, будівлями, предметами, мобами, тощо. Це зроблено для того, щоб ви могли спокійно спостерігати за грою, але не заважати гравцям займатися своїми справами"));
        guestQuestions.addQuestion(new WikiCard("Чому я не можу писати у чат?", "Якщо ви в режимі гостя, то ви не маєте можливості бачити чат. Це зроблено для того, щоб ви могли спокійно спостерігати за грою, але не відволікатися на чат"));
        guestQuestions.addQuestion(new WikiCard("Як змінити свою роль до Гравця?", "Просто виконайте усі умови для цього - авторизуйтеся на сайті, заповніть анкету, дочекайтеся її перевірки / придбайте допуск. Якщо ви правильно вказали нікнейм, щойно умови будуть виконані, ви автоматично отримаєте роль гравця"));
        guestQuestions.addQuestion(new WikiCard("Як інший Гравець може витягти мене із гостів?", "Така можливість доступна лише для Спонсорів, якщо вони ще не використали усі свої спроби, відповідно їх рівню. Робиться це приблизно так: /graylist add <ім'я>"));

        FlexLayout cardLayout = new FlexLayout();
        cardLayout.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
        cardLayout.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        cardLayout.setWidthFull();

        for (WikiGroup group : List.of(generalQuestions, rolesQuestions, guestQuestions)) {
            Div content = new Div();
            content.addClassNames(LumoUtility.FlexWrap.WRAP, LumoUtility.Overflow.HIDDEN, LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN, LumoUtility.AlignItems.START, LumoUtility.Width.FULL);
            H2 header = new H2(group.getHeader());
            header.getStyle().set("margin-bottom", "20px");
            content.add(header);

            for (WikiCard question : group.getQuestions()) {
                Details details = new Details();
                details.setSummaryText(question.getQuestion());
                TextArea answer = new TextArea();
                answer.setValue(question.getAnswer());
                answer.setReadOnly(true);
                answer.setLabel("");
                answer.setHeight("auto");
                answer.setWidthFull();

                details.add(new VerticalLayout(answer));

                details.addThemeVariants(DetailsVariant.FILLED);
                details.setWidthFull();
                details.getStyle().set("margin-bottom", "10px");

                content.add(details);
                cardLayout.add(content);
            }
        }
        add(cardLayout);
    }
}