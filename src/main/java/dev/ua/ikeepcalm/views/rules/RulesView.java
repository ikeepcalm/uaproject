package dev.ua.ikeepcalm.views.rules;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import dev.ua.ikeepcalm.views.MainLayout;

@PageTitle("Правила")
@Route(value = "rules", layout = MainLayout.class)
public class RulesView extends VerticalLayout {

    private final VerticalLayout contentLayout;

    private record Rule(String number, String description) {
    }

    public RulesView() {

        Scroller scroller = new Scroller();
        scroller.getStyle().set("overflow-y", "auto");
        scroller.getStyle().set("max-height", "100vh");
        contentLayout = new VerticalLayout();

        H2 header = new H2("Правила сервера");
        header.addClassNames(Margin.AUTO, Margin.Bottom.MEDIUM);
        contentLayout.add(header);


        addRule(new Rule("1.1", "Гріферство, грабіжництво, псування будівель і ландшафту"));
        addRule(new Rule("1.2", "Мульти-акаунти, стороннє програмне забезпечення / моди"));
        addRule(new Rule("1.3", "Лаг-машини, клонування речей, експлойти / багоюз"));
        addRule(new Rule("1.4", "Використання будь яких російських лаунчерів гри"));
        addRule(new Rule("1.5", "Токсична поведінка, флуд, спам, тролінг, реклама"));
        addRule(new Rule("1.6", "Контент 18+, NSFW, злочинно-терористична символіка"));
        addRule(new Rule("1.7", "Расизм, сексизм, шовінізм у бік усіх, окрім росіян"));
        addRule(new Rule("1.8", "Російська мова у текстових канал, на медіа, інтерфейсі"));
        addRule(new Rule("1.9", "Російська мова у голосових каналах проти згоди інших учасників"));
        addRule(new Rule("1.10", "Заперечення злочинів і збройної агресії РФ проти України"));

        Span span = new Span("Правила можуть змінюватися без попередження. Перевіряйте їх регулярно!");
        span.getStyle().set("font-size", "0.8em");
        Span span2 = new Span("Незнання правил не звільняє від відповідальності за їх порушення!");
        span2.getStyle().set("font-size", "0.8em");
        contentLayout.add(span);
        contentLayout.add(span2);

        setHeightFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.START);
        getStyle().set("overflow-y", "auto");
        getStyle().set("text-align", "center");
        contentLayout.addClassNames(LumoUtility.FlexWrap.WRAP, LumoUtility.Overflow.HIDDEN, LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN, LumoUtility.AlignItems.CENTER);
        scroller.setContent(contentLayout);
        scroller.getStyle().set("overflow-y", "auto");
        scroller.getStyle().set("width", "100%");

        add(scroller);
    }

    private void addRule(Rule... rules) {
        Div ruleBlock = new Div();
        ruleBlock.addClassNames("rule-block");
        for (Rule rule : rules) {
            Paragraph ruleParagraph = new Paragraph(rule.number() + " " + rule.description());
            ruleParagraph.setWidth("100%");
            ruleBlock.add(ruleParagraph);
        }
        ruleBlock.setWidth("70%");
        contentLayout.add(ruleBlock);
    }
}
