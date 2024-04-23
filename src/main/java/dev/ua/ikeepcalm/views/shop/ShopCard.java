package dev.ua.ikeepcalm.views.shop;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.theme.lumo.LumoUtility.*;

public class ShopCard extends ListItem {

    public ShopCard(String title, String price, String desc, String file, String url) {
        addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, AlignItems.CENTER, Padding.MEDIUM,
                BorderRadius.LARGE);

        Div div = new Div();
        div.addClassNames(Background.CONTRAST, Display.FLEX, AlignItems.CENTER, JustifyContent.CENTER,
                Margin.Bottom.MEDIUM, Overflow.HIDDEN, BorderRadius.MEDIUM, Width.FULL);
        div.setHeight("160px");

        Image image = new Image(file, "placeholder " + file);
        image.setWidth("100%");
        image.setAlt(title);

        div.add(image);

        Span header = new Span();
        header.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
        header.addClassNames(TextAlignment.CENTER, Margin.Vertical.SMALL);
        header.setText(title);

        Paragraph description = new Paragraph(desc);
        description.getStyle().set("white-space", "pre-line");
        description.addClassName(Margin.Vertical.MEDIUM);

        Button badge = new Button();
        badge.addThemeName(ButtonVariant.MATERIAL_CONTAINED.getVariantName());
        badge.setText("Придбати за " + price + "₴");
        badge.addClickListener(e -> UI.getCurrent().getPage().executeJs("window.open('" + url + "', '_blank')"));

        add(div, header, description, badge);
    }

    public ShopCard(String title, String price, String desc, String file, String url, String linkText) {
        addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, AlignItems.CENTER, Padding.MEDIUM,
                BorderRadius.LARGE);

        Div div = new Div();
        div.addClassNames(Background.CONTRAST, Display.FLEX, AlignItems.CENTER, JustifyContent.CENTER,
                Margin.Bottom.MEDIUM, Overflow.HIDDEN, BorderRadius.MEDIUM, Width.FULL);

        div.setHeight("160px");

        Image image = new Image(file, "placeholder " + file);
        image.setWidth("100%");
        image.setAlt(title);

        div.add(image);

        Span header = new Span();
        header.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
        header.addClassNames(TextAlignment.CENTER, Margin.Vertical.SMALL);
        header.setText(title);

        Paragraph description = new Paragraph(desc);
        description.getStyle().set("white-space", "pre-line");
        description.addClassName(Margin.Vertical.MEDIUM);

        Button badge = new Button();
        badge.addThemeName(ButtonVariant.MATERIAL_CONTAINED.getVariantName());
        badge.setText(linkText);
        badge.addClickListener(e -> UI.getCurrent().getPage().executeJs("window.open('" + url + "', '_blank')"));

        add(div, header, description, badge);
    }
}
