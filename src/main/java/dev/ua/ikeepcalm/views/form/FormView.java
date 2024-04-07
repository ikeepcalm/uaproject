package dev.ua.ikeepcalm.views.form;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.datepicker.DatePickerVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.select.SelectVariant;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextAreaVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.router.*;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import dev.ua.ikeepcalm.data.entities.DiscordUser;
import dev.ua.ikeepcalm.data.services.DiscordUserService;
import dev.ua.ikeepcalm.views.MainLayout;
import dev.ua.ikeepcalm.views.form.source.LauncherType;
import dev.ua.ikeepcalm.views.form.source.PlayerType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@PageTitle("Анкета")
@Route(value = "form", layout = MainLayout.class)
@Uses(Icon.class)
@PreserveOnRefresh
public class FormView extends Composite<VerticalLayout> implements BeforeEnterObserver {

    private final DiscordUserService service;
    private DiscordUser currentPerson;

    public FormView(DiscordUserService service) {
        this.service = service;

        VerticalLayout layoutColumn2 = new VerticalLayout();
        H3 h3 = new H3();
        FormLayout formLayout2Col = new FormLayout();

        TextField nickname = new TextField();
        nickname.addThemeVariants(TextFieldVariant.LUMO_HELPER_ABOVE_FIELD);

        DatePicker birthDate = new DatePicker();
        birthDate.addThemeVariants(DatePickerVariant.LUMO_HELPER_ABOVE_FIELD);

        TextField advised = new TextField();
        advised.addThemeVariants(TextFieldVariant.LUMO_HELPER_ABOVE_FIELD);

        TextField hobbies = new TextField();
        hobbies.addThemeVariants(TextFieldVariant.LUMO_HELPER_ABOVE_FIELD);

        Select<PlayerItem> typeOfPlayer = new Select<>();
        typeOfPlayer.addThemeVariants(SelectVariant.LUMO_HELPER_ABOVE_FIELD);

        Select<LauncherItem> gameLauncher = new Select<>();
        gameLauncher.addThemeVariants(SelectVariant.LUMO_HELPER_ABOVE_FIELD);

        TextArea task = new TextArea();
        task.addThemeVariants(TextAreaVariant.LUMO_HELPER_ABOVE_FIELD);
        TextArea experience = new TextArea();
        experience.addThemeVariants(TextAreaVariant.LUMO_HELPER_ABOVE_FIELD);
        TextArea conflict = new TextArea();
        conflict.addThemeVariants(TextAreaVariant.LUMO_HELPER_ABOVE_FIELD);
        TextArea memory = new TextArea();
        memory.addThemeVariants(TextAreaVariant.LUMO_HELPER_ABOVE_FIELD);

        HorizontalLayout layoutRow = new HorizontalLayout();
        Button buttonPrimary = new Button();
        Button buttonSecondary = new Button();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        getContent().setAlignItems(FlexComponent.Alignment.CENTER);
        layoutColumn2.setWidth("100%");
        layoutColumn2.setMaxWidth("800px");
        layoutColumn2.setHeight("min-content");
        h3.setText("Анкета допуску на сервер");
        h3.setWidth("100%");
        formLayout2Col.setWidth("100%");
        nickname.setHelperText("Ваш ігровий нікнейм (для грейлісту)");
        birthDate.setHelperText("Ваша дата народження");
        advised.setHelperText("Звідки ви дізналися про наш сервер?");
        hobbies.setHelperText("Які у вас є хоббі, чим ви займаєтесь у вільний час?");
        typeOfPlayer.setHelperText("Хто ви за типом гравця в майнкрафті?");
        typeOfPlayer.setWidth("min-content");
        setPlayerTypeData(typeOfPlayer);
        gameLauncher.setHelperText("З якого лаунчеру ви граєте?");
        gameLauncher.setWidth("min-content");
        setLaunchersData(gameLauncher);
        task.setHelperText(
                "Для того, щоб викопати яму, трьом чоловікам потрібно 5 годин. Скільки часу потрібно восьми чоловікам?");
        task.setWidth("100%");
        experience.setHelperText("У вас є досвід гри на приватних майнкрафт серверах? Опишіть його якомога детальніше!");
        experience.setWidth("100%");
        conflict.setHelperText(
                "Уявіть, що вас ображає адміністратор серверу. Що ви зробите, якщо потрапите у таку ситуацію?");
        conflict.setWidth("100%");
        memory.setHelperText(
                "Опишіть ваш улюблений спогад із дитинства. Наприклад, особисто мій - коли мені дали пограти у WoT :'");
        memory.setWidth("100%");
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        buttonPrimary.setText("Зберегти і відправити");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonSecondary.setText("Відмінити і очистити");
        buttonSecondary.setWidth("min-content");
        getContent().add(layoutColumn2);
        layoutColumn2.add(h3);
        layoutColumn2.add(formLayout2Col);
        formLayout2Col.add(nickname);
        formLayout2Col.add(birthDate);
        formLayout2Col.add(advised);
        formLayout2Col.add(hobbies);
        formLayout2Col.add(typeOfPlayer);
        formLayout2Col.add(gameLauncher);
        layoutColumn2.add(task);
        layoutColumn2.add(experience);
        layoutColumn2.add(conflict);
        layoutColumn2.add(memory);
        layoutColumn2.add(layoutRow);

        buttonPrimary.addClickListener(event -> {
            boolean valid = true;
            if (currentPerson == null) {
                return;
            }
            if (nickname.getValue() == null || nickname.getValue().isEmpty()) {
                nickname.setInvalid(true);
                valid = false;
            }
            if (birthDate.getValue() == null) {
                birthDate.setInvalid(true);
                valid = false;
            }
            if (advised.getValue() == null || advised.getValue().isEmpty()) {
                advised.setInvalid(true);
                valid = false;
            }
            if (hobbies.getValue() == null || hobbies.getValue().isEmpty()) {
                hobbies.setInvalid(true);
                valid = false;
            }
            if (typeOfPlayer.getValue() == null || gameLauncher.isEmpty()) {
                typeOfPlayer.setInvalid(true);
                valid = false;
            }
            if (gameLauncher.getValue() == null || gameLauncher.isEmpty()) {
                gameLauncher.setInvalid(true);
                valid = false;
            }
            if (task.getValue() == null || task.getValue().isEmpty()) {
                task.setInvalid(true);
                valid = false;
            }
            if (experience.getValue() == null || experience.getValue().isEmpty()) {
                experience.setInvalid(true);
                valid = false;
            }
            if (conflict.getValue() == null || conflict.getValue().isEmpty()) {
                conflict.setInvalid(true);
                valid = false;
            }
            if (memory.getValue() == null || memory.getValue().isEmpty()) {
                memory.setInvalid(true);
                valid = false;
            }

            if (!valid) {
                return;
            }

            currentPerson.setNickname(nickname.getValue());
            currentPerson.setBirthday(birthDate.getValue());
            currentPerson.setAdvised(advised.getValue());
            currentPerson.setHobbies(hobbies.getValue());
            currentPerson.setTypeOfPlayer(typeOfPlayer.getValue().value);
            currentPerson.setGameLauncher(gameLauncher.getValue().value);
            currentPerson.setTask(task.getValue());
            currentPerson.setExperience(experience.getValue());
            currentPerson.setConflict(conflict.getValue());
            currentPerson.setMemory(memory.getValue());
            currentPerson.setAlreadyTried(true);

            service.update(currentPerson);
            ConfirmDialog dialog = new ConfirmDialog();
            dialog.setHeader("Дякуємо за заповнення анкети!");
            dialog.setText("Ваша анкета успішно збережена. Ми зв'яжемося з вами найближчим часом!");
            dialog.setConfirmText("Закрити");
            dialog.addConfirmListener(e -> {
                dialog.close();
                UI.getCurrent().navigate("");
            });
            dialog.open();
        });

        layoutRow.add(buttonPrimary);
        layoutRow.add(buttonSecondary);
    }

    private void getDiscordUser() {
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Увійдіть за допомогою Discord");
        dialog.setText("Щоб продовжити, увійдіть за допомогою вашого акаунту Discord. Це потрібно, щоб ідентифікувати вас на сервері, а також сповістити про результат перевірки анкети!");

        dialog.setConfirmText("Увійти");
        dialog.addConfirmListener(event -> {
            dialog.close();
            UI.getCurrent().getPage().setLocation("https://discord.com/oauth2/authorize?client_id=1226236763975188550&response_type=code&redirect_uri=http%3A%2F%2Flocalhost%3A8080%2Flogin%2Fcallback&scope=identify");
        });
        dialog.addCancelListener(event -> {
            dialog.close();
            UI.getCurrent().navigate("/form");
        });
        dialog.open();
    }


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        Location location = beforeEnterEvent.getLocation();
        QueryParameters queryParameters = location.getQueryParameters();
        Optional<String> key = queryParameters.getSingleParameter("key");
        Optional<String> username = queryParameters.getSingleParameter("username");
        if (key.isEmpty()) {
            getDiscordUser();
        } else {
            currentPerson = service.findByDiscordId(key.get()).get();
            if (currentPerson.isAlreadyTried()) {
                ConfirmDialog dialog = new ConfirmDialog();
                dialog.setHeader("Ви вже заповнили анкету!");
                dialog.setText("Ваша анкета вже збережена. Ми зв'яжемося з вами найближчим часом! Також, перевірте, чи ваші ПП відкриті для нашого бота.");
                dialog.setConfirmText("Закрити");
                dialog.addConfirmListener(e -> {
                    dialog.close();
                    UI.getCurrent().navigate("");
                });
                dialog.addCancelListener(event -> {
                    dialog.close();
                    UI.getCurrent().navigate("/form");
                });
                dialog.open();
            } else {
                username.ifPresent(s -> currentPerson.setUsername(s));
                service.update(currentPerson);
            }
        }
    }

    record LauncherItem(LauncherType value, String label) {
    }

    record PlayerItem(PlayerType value, String label) {
    }

    private void setPlayerTypeData(Select<PlayerItem> select) {
        List<PlayerItem> playerItems = new ArrayList<>();
        playerItems.add(new PlayerItem(PlayerType.ADVENTURER, PlayerType.ADVENTURER.getType()));
        playerItems.add(new PlayerItem(PlayerType.BUILDER, PlayerType.BUILDER.getType()));
        playerItems.add(new PlayerItem(PlayerType.FARMER, PlayerType.FARMER.getType()));
        playerItems.add(new PlayerItem(PlayerType.FIGHTER, PlayerType.FIGHTER.getType()));
        playerItems.add(new PlayerItem(PlayerType.REDSTONE_ENGINEER, PlayerType.REDSTONE_ENGINEER.getType()));
        playerItems.add(new PlayerItem(PlayerType.OTHER, PlayerType.OTHER.getType()));
        select.setItems(playerItems);
        select.setItemLabelGenerator(PlayerItem::label);
    }

    private void setLaunchersData(Select<LauncherItem> select) {
        List<LauncherItem> launcherItems = new ArrayList<>();
        launcherItems.add(new LauncherItem(LauncherType.TLAUNCHER, LauncherType.TLAUNCHER.getLauncher()));
        launcherItems.add(new LauncherItem(LauncherType.TLAUNCHER_LEGACY, LauncherType.TLAUNCHER_LEGACY.getLauncher()));
        launcherItems.add(new LauncherItem(LauncherType.MINECRAFT_LAUNCHER, LauncherType.MINECRAFT_LAUNCHER.getLauncher()));
        launcherItems.add(new LauncherItem(LauncherType.BEDROCK, LauncherType.BEDROCK.getLauncher()));
        launcherItems.add(new LauncherItem(LauncherType.PRISM_LAUNCHER, LauncherType.PRISM_LAUNCHER.getLauncher()));
        launcherItems.add(new LauncherItem(LauncherType.POLYMC, LauncherType.POLYMC.getLauncher()));
        launcherItems.add(new LauncherItem(LauncherType.POLLYMC, LauncherType.POLLYMC.getLauncher()));
        launcherItems.add(new LauncherItem(LauncherType.MULTI_MC, LauncherType.MULTI_MC.getLauncher()));
        launcherItems.add(new LauncherItem(LauncherType.OTHER, LauncherType.OTHER.getLauncher()));
        select.setItems(launcherItems);
        select.setItemLabelGenerator(LauncherItem::label);
    }
}