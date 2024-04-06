package dev.ua.ikeepcalm.views.rules;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import dev.ua.ikeepcalm.views.MainLayout;

@PageTitle("Правила")
@Route(value = "rules", layout = MainLayout.class)
public class RulesView extends VerticalLayout {


    private class Rule {
        private String number;
        private String description;

        public Rule(String number, String description) {
            this.number = number;
            this.description = description;
        }

        public String getNumber() {
            return number;
        }

        public String getDescription() {
            return description;
        }
    }

    public RulesView() {

        H2 header = new H2("Головні правила сервера");
        header.addClassNames(Margin.AUTO, Margin.Bottom.MEDIUM);
        add(header);


        addRule(new Rule("1.1", "Гріферство, грабіжництво, псування будівель і ландшафту"));
        addRule(new Rule("1.2", "Мульти-акаунти, стороннє програмне забезпечення"));
        addRule(new Rule("1.3", "Лаг-машини, клонування речей, вбивство без причин"));
        addRule(new Rule("1.4", "Використання TLauncher або будь яких інших російських лаунчерів"));
        addRule(new Rule("1.5", "Токсична поведінка, флуд, спам, тролінг, реклама"));
        addRule(new Rule("1.6", "Контент 18+, NSFW, злочинно-терористична символіка"));
        addRule(new Rule("1.7", "Расизм, сексизм, шовінізм у бік усіх, окрім росіян"));
        addRule(new Rule("1.8", "Російська мова у текстових канал, на медіа, інтерфейсі"));
        addRule(new Rule("1.9", "Російська мова у голосових каналах проти згоди інших учасників"));
        addRule(new Rule("1.10", "Заперечення злочинів і збройної агресії РФ проти України"));

        addNote("Незнання правил не звільняє від відповідальності за їх порушення. У випадках, коли не вдається чітко визначити пункт порушення, за адміністрацією залишається право обрати запобіжний захід без обґрунтування своїх дій!");

        setHeightFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("overflow-y", "auto");
        getStyle().set("text-align", "center");
    }

    private void addRule(Rule... rules) {
        Div ruleBlock = new Div();
        ruleBlock.addClassNames("rule-block");
        for (Rule rule : rules) {
            Paragraph ruleParagraph = new Paragraph(rule.getNumber() + " " + rule.getDescription());
            ruleParagraph.setWidth("100%");
            ruleBlock.add(ruleParagraph);
        }
        ruleBlock.setWidth("90%");
        add(ruleBlock);
    }

    private void addNote(String note) {
        Div noteBlock = new Div();
        noteBlock.addClassNames("note-block");
        Paragraph noteParagraph = new Paragraph(note);
        noteBlock.add(noteParagraph);
        noteBlock.setWidth("90%");
        add(noteBlock);
    }

}
