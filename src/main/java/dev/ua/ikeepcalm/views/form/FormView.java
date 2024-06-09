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
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Paragraph;
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
import dev.ua.ikeepcalm.utils.ResponseUtil;
import dev.ua.ikeepcalm.views.MainLayout;
import dev.ua.ikeepcalm.views.form.source.LauncherType;
import dev.ua.ikeepcalm.views.form.source.PlayerType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import org.springframework.beans.factory.annotation.Value;

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
    private final JDA jda;
    private boolean customLauncher = false;

    @Value("${discord.admin-channel-id}")
    private long adminChannelId;

    public FormView(DiscordUserService service, JDA jda) {
        this.service = service;
        this.jda = jda;

        VerticalLayout verticalLayout = new VerticalLayout();
        H3 title = new H3();
        Paragraph description = new Paragraph();
        FormLayout formLayout = new FormLayout();

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

        TextField otherLauncher = new TextField();
        otherLauncher.setPlaceholder("ex. Badlion, Lunar, etc.");
        otherLauncher.addThemeVariants(TextFieldVariant.LUMO_HELPER_ABOVE_FIELD);
        otherLauncher.setHelperText("Вкажіть назву іншого лаунчера");
        otherLauncher.setWidth("100%");
        gameLauncher.setHelperText("Вкажіть назву іншого лаунчера");

        gameLauncher.addValueChangeListener(event -> {
            System.out.println(event.toString());
            if (gameLauncher.getValue().value() == LauncherType.OTHER) {
                customLauncher = true;
                verticalLayout.addComponentAtIndex(3, otherLauncher);
            } else {
                customLauncher = false;
                verticalLayout.remove(otherLauncher);
            }
        });

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
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        getContent().setAlignItems(FlexComponent.Alignment.CENTER);
        verticalLayout.setWidth("100%");
        verticalLayout.setMaxWidth("800px");
        verticalLayout.setHeight("min-content");
        title.setText("Анкета допуску на сервер");
        title.setWidth("100%");
        description.setText("Зверніть увагу на свої відповіді! У вас є лише одна спроба заповнити анкету! Якість і розгорнутість відповідей напряму впливає на результат перевірки, що може зайняти від 1 до 7 днів. Після відправлення анкети ви отримаєте повідомлення на сервері Discord. Успіхів!");
        description.setWidth("100%");
        formLayout.setWidth("100%");
        nickname.setHelperText("Ваш ігровий нікнейм (для грейлісту)");
        nickname.setPlaceholder("ex. Player123");
        birthDate.setHelperText("Ваша дата народження");
        advised.setHelperText("Звідки ви дізналися про наш сервер?");
        advised.setPlaceholder("ex. Друзі, реклама, пошукові системи, тощо");
        hobbies.setHelperText("Які у вас є хоббі, чим ви займаєтесь у вільний час?");
        hobbies.setPlaceholder("ex. Читання, гра в ігри, спорт, тощо");
        typeOfPlayer.setHelperText("Хто ви за типом гравця в майнкрафті?");
        typeOfPlayer.setWidth("min-content");
        setPlayerTypeData(typeOfPlayer);
        gameLauncher.setHelperText("З якого лаунчеру ви граєте?");
        gameLauncher.setWidth("min-content");
        setLaunchersData(gameLauncher);
        task.setHelperText(
                "Для того, щоб викопати яму, трьом чоловікам потрібно 5 годин. Скільки часу потрібно восьми чоловікам? Відповідь обґрунтуйте :)");
        task.setPlaceholder("ex. Ділимо кількість мавпочок на кількість бананів: 90/3 = 30 хвилин, щоб з'їсти один банан...");
        task.setWidth("100%");
        experience.setHelperText("У вас є досвід гри на приватних майнкрафт серверах? Опишіть його якомога детальніше!");
        experience.setPlaceholder("ex. Я грав на сервері з 2015 року, володію знаннями у плагінах, вмію будувати, тощо");
        experience.setWidth("100%");
        conflict.setHelperText(
                "Уявіть, що вас ображає адміністратор серверу. Що ви зробите, якщо потрапите у таку ситуацію?");
        conflict.setPlaceholder("ex. Звернуся до іншого адміністратора, напишу на форумі, тощо");
        conflict.setWidth("100%");
        memory.setHelperText(
                "Опишіть ваш улюблений спогад із дитинства :'");
        memory.setPlaceholder("ex. Як я вперше побачив сніг, як я вперше побачив море, тощо");
        memory.setWidth("100%");
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        buttonPrimary.setText("Зберегти і відправити");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        getContent().add(verticalLayout);
        verticalLayout.add(title);
        verticalLayout.add(description);
        verticalLayout.add(formLayout);
        formLayout.add(nickname);
        formLayout.add(birthDate);
        formLayout.add(advised);
        formLayout.add(hobbies);
        formLayout.add(typeOfPlayer);
        formLayout.add(gameLauncher);
        verticalLayout.add(task);
        verticalLayout.add(experience);
        verticalLayout.add(conflict);
        verticalLayout.add(memory);
        verticalLayout.add(layoutRow);

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
            if (customLauncher && (otherLauncher.getValue() == null || otherLauncher.getValue().isEmpty())) {
                otherLauncher.setInvalid(true);
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
            if (customLauncher) {
                currentPerson.setGameLauncher(LauncherType.OTHER);
                currentPerson.setCustomLauncher(otherLauncher.getValue());
            } else {
                currentPerson.setGameLauncher(gameLauncher.getValue().value);
            }
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
            new ResponseUtil(null, adminChannelId).sendNewForm(currentPerson, jda);
        });

        layoutRow.add(buttonPrimary);
    }

    private void getDiscordUser() {
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Увійдіть за допомогою Discord");
        dialog.setText("Щоб продовжити, увійдіть за допомогою вашого акаунту Discord. Це потрібно, щоб ідентифікувати вас на сервері, а також сповістити про результат перевірки анкети! Щоб отримати повідомлення, ПП мають бути відкритими!");

        dialog.setConfirmText("Увійти");
        dialog.setCancelText("Пізніше");
        dialog.setCancelable(true);

        dialog.addConfirmListener(event -> {
            dialog.close();
            UI.getCurrent().getPage().setLocation("https://discord.com/oauth2/authorize?client_id=1226236763975188550&response_type=code&redirect_uri=https%3A%2F%2Fuaproject-reborn.xyz%2Flogin%2Fcallback&scope=identify");
        });
        dialog.addCancelListener(event -> {
            dialog.close();
            UI.getCurrent().navigate("");
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
            if (service.findByDiscordId(key.get()).isEmpty()) {
                return;
            }
            currentPerson = service.findByDiscordId(key.get()).get();

            Guild guild = jda.getGuildById(1221552838807654450L);
            if (guild == null) {
                return;
            }

            try {
                Member member = guild.retrieveMemberById(currentPerson.getDiscordId()).complete();
            } catch (ErrorResponseException exc) {
                ConfirmDialog dialog = new ConfirmDialog();
                dialog.setHeader("Ваш акаунт не знайдено!");
                dialog.setText("Для того, щоб подати анкету, вам потрібно бути у нашому дискорд сервері. Перейдіть за посиланням, щоб приєднатися до нас!");
                dialog.setCancelText("Закрити");
                dialog.setConfirmText("Приєднатися");
                dialog.addConfirmListener(e -> {
                    dialog.close();
                    UI.getCurrent().getPage().setLocation("https://discord.gg/nyAMvRru7x");
                });
                dialog.addCancelListener(event -> {
                    dialog.close();
                    UI.getCurrent().navigate("");
                });
                dialog.open();
                return;
            }

            if (currentPerson.isAlreadyTried()) {
                if (currentPerson.isWasApproved()) {
                    ConfirmDialog dialog = new ConfirmDialog();
                    dialog.setHeader("Анкету було прийнято!");
                    dialog.setText("З радістю повідомляю, що ваша анкета була прийнята! Доступ гравця було надано на вказаний у анкеті нікнейм. Якщо у вас виникнуть проблеми, звертайтеся до адміністрації сервера. Дякуємо за участь!");
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
                    ConfirmDialog dialog = new ConfirmDialog();
                    dialog.setHeader("Ви вже заповнили анкету!");
                    dialog.setText("Ваша анкета вже збережена. Ми зв'яжемося з вами найближчим часом! Також, перевірте, чи ваші ПП відкриті для нашого бота");
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
                }
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
        launcherItems.add(new LauncherItem(LauncherType.MINECRAFT_LAUNCHER, LauncherType.MINECRAFT_LAUNCHER.getLauncher()));
        launcherItems.add(new LauncherItem(LauncherType.POLYMC, LauncherType.POLYMC.getLauncher()));
        launcherItems.add(new LauncherItem(LauncherType.TLAUNCHER, LauncherType.TLAUNCHER.getLauncher()));
        launcherItems.add(new LauncherItem(LauncherType.TLAUNCHER_LEGACY, LauncherType.TLAUNCHER_LEGACY.getLauncher()));
        launcherItems.add(new LauncherItem(LauncherType.BEDROCK_LAUNCHER, LauncherType.BEDROCK_LAUNCHER.getLauncher()));
        launcherItems.add(new LauncherItem(LauncherType.PRISM_LAUNCHER, LauncherType.PRISM_LAUNCHER.getLauncher()));
        launcherItems.add(new LauncherItem(LauncherType.AT_LAUNCHER, LauncherType.AT_LAUNCHER.getLauncher()));
        launcherItems.add(new LauncherItem(LauncherType.POLLYMC, LauncherType.POLLYMC.getLauncher()));
        launcherItems.add(new LauncherItem(LauncherType.MULTI_MC, LauncherType.MULTI_MC.getLauncher()));
        launcherItems.add(new LauncherItem(LauncherType.OTHER, LauncherType.OTHER.getLauncher()));
        select.setItems(launcherItems);
        select.setItemLabelGenerator(LauncherItem::label);
    }
}
