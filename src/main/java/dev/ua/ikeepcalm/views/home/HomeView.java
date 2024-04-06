package dev.ua.ikeepcalm.views.home;

import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import dev.ua.ikeepcalm.views.MainLayout;

@PageTitle("Uaproject")
@Route(value = "home", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class HomeView extends VerticalLayout {

    public HomeView() {
        setSpacing(false);

        Image img = new Image("images/uaproject-pfp.png", "placeholder pfp");
        img.setWidth("200px");
        add(img);

        H1 header = new H1("Український Minecraft сервер");
        header.addClassNames(Margin.Top.XLARGE, Margin.Bottom.MEDIUM);
        add(header);
        add(new Paragraph("Простір ідеальних умов для розкриття\n" +
                "власного творчого потенціалу в захоплюючому середовищі"));

        HorizontalLayout layout = getHorizontalLayout();
        add(layout);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");

    }

    private static HorizontalLayout getHorizontalLayout() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.addClassName("custom-horizontal-layout");
        layout.setWidth("80%");
        layout.setAlignItems(Alignment.CENTER);

        Details community = new Details();
        community.addClassName("custom-details");
        community.setSummaryText("Спільнота");
        TextArea communityTextArea = new TextArea();
        communityTextArea.setValue("Спільнота сервера - це дружня та дружелюбна спільнота, яка завжди рада новим гравцям та готова допомогти у вирішенні будь-яких питань. У нас ви знайдете багато друзів, з якими вам буде цікаво грати!");
        communityTextArea.setReadOnly(true);
        communityTextArea.setWidth("100%");

        community.add(communityTextArea);
        community.addThemeVariants(DetailsVariant.FILLED);
        community.setWidth("60%");

        Details features = new Details();
        features.addClassName("custom-details");
        features.setSummaryText("Особливості");
        TextArea featuresTextArea = new TextArea();
        featuresTextArea.setValue("Сервер має багато цікавих особливостей, які роблять гру на ньому неповторною та захоплюючою. Унікальна містична атмосфера, різноманітні івенти, власні міста, красива генерація і більше!");
        featuresTextArea.setReadOnly(true);
        featuresTextArea.setWidth("100%");
        features.add(featuresTextArea);
        features.addThemeVariants(DetailsVariant.FILLED);
        features.setWidth("60%");

        layout.add(community, features);
        return layout;
    }


}
