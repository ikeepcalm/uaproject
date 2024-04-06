package dev.ua.ikeepcalm.views.wiki;

import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import dev.ua.ikeepcalm.views.MainLayout;

import java.util.ArrayList;
import java.util.List;

@PageTitle("Вікіпедія")
@Route(value = "wiki", layout = MainLayout.class)
public class WikiView extends VerticalLayout {

    public WikiView() {
        // Sample questions and answers
        List<WikiCard> questions = new ArrayList<>();
        questions.add(new WikiCard("Як називається столиця України?", "Столиця України - Київ."));
        questions.add(new WikiCard("Як називається найбільше озеро в Україні?", "Найбільше озеро в Україні - Світязь."));
        questions.add(new WikiCard("Скільки йде сезон на сервері?", "Все залежить від активу на сервері, виходу нових версій, непередбачуваних ситуацій і ще невеличкого списку причин. В середньому тривалість одного сезону не перевищує 6 місяців"));
        questions.add(new WikiCard("Чи можна якось доєднатися до команди / адміністрації серверу?", "Зараз, на жаль, ні. За потреби, у Дискорд-сервері буде оголошений набір на одну з ігрових ролей, і ви зможете спробувати свої сили у відкритому змаганні бажаючих"));
        questions.add(new WikiCard("Чому адміністрація деколи ігнорує мої питання / повідомлення?", "Команда проєкту намагається відповідати всім, в чатах, розділах тех. підтримки, у грі, тощо, але деколи повідомлення плутаються між собою та зникають з поля зору. Переконайтеся, що повідомлення має зміст, ви чітко описали суть проблеми і предоставили усю необхідну інформацію про ситуацію, що склалася, і ненав'язливо нагадайте про це адміністрації"));
        questions.add(new WikiCard("Чому я не можу зайти на сервер?", "Причин може бути багато: від технічних проблем на сервері до проблем з вашим інтернет-провайдером. Переконайтеся, що ви використовуєте офіційний лаунчер, виберіть версію гри, яка підтримується сервером, перевірте ваше інтернет-з'єднання, перезапустіть гру, перезапустіть комп'ютер, перевірте статус сервера на нашому сайті або в нашому Дискорд-сервері"));
        questions.add(new WikiCard("Чому я не можу побачити свій дім на мапі?", "Мапа на сайті оновлюється не миттєво, а з певною затримкою. Якщо ви впевнені, що ваш дім вже давно побудований, але його все ще немає на мапі, зверніться до адміністрації"));

        FlexLayout cardLayout = new FlexLayout();
        cardLayout.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
        cardLayout.setWidthFull();

        for (WikiCard question : questions) {
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

            cardLayout.add(details);
        }
        add(cardLayout);
    }
}