package dev.ua.ikeepcalm.bot.utils;

import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class EmbedBuilder {

    @NotNull
    public static net.dv8tion.jda.api.EmbedBuilder getFormEmbed() {
        net.dv8tion.jda.api.EmbedBuilder embed = new net.dv8tion.jda.api.EmbedBuilder();
        embed.setTitle("Як пройти на сервер?");
        embed.setColor(Color.YELLOW);
        embed.setDescription("Вітаю, поціновувач майнкрафту! Для того, щоб отримати прохідку (дозвіл грати на нашому сервері) і \n" +
                             "вже безпосередньо зайти на нього за загальним айпі (mc.uaproject.xyz), у тебе є два шляхи:");
        embed.addField("1. Придбати допуск за 50₴", "Все, що вам треба зробити для цього, натиснути на відповідну кнопку під цим повідомленням, вказати у полі Ім'я ваш дійсний ігровий нікнейм, обрати суму, що дорівнює, або перевищує 50₴. Ваш нікнейм буде додано до білого списку серверу майже моментально.", false);
        embed.addField("2. Заповнити анкету", "Для того, щоб заповнити анкету вам потрібно натиснути на кнопку під цим повідомленням, уважно прочитати кожне питання, написати розгорнуту відповідь, надіслати, і чекати, поки адміністрація перевірить її. Результат анкети ви отримаєте у приватних повідомленнях. Уважно передивіться правила перед цим варіантом! У вас є лише одна спроба.", false);
        embed.setImage("https://media.discordapp.net/attachments/1143545807451213915/1259939893262422077/vitania.png?ex=668d81f7&is=668c3077&hm=8f946dce9de6bc23de28c03f7b3c0d26db80f11b22836a69d1ade475c7291f0a&=&format=webp&quality=lossless&width=687&height=215");
        return embed;
    }

    @NotNull
    public static net.dv8tion.jda.api.EmbedBuilder getLaunchersEmbed() {
        net.dv8tion.jda.api.EmbedBuilder embed = new net.dv8tion.jda.api.EmbedBuilder();
        embed.setTitle("Інфо-блок про лаунчери");
        embed.setColor(Color.ORANGE);
        embed.setDescription("Пункт правил 1.4 свідчить: Використання будь-яких російських лаунчерів гри - категорично заборонено. Постає питання: які лаунчери тоді можна використовувати?");
        embed.addField("PollyMc", "Гарний лаунчер для гри із підтримкою неофіціальних акаунтів. Зручний, має підтримку збірок з Modrinth, CurseForge, FTB; не потребує багато ресурсів від комп'ютера, можна встановити на будь яку систему ( macOS, Windows, Linux ). Ви можете завантажити його за посиланням: [PollyMc](https://pollymc.com)", false);
        embed.addField("PrismLauncher ", "Гарний лаунчер для гри лише для ліцензійних акаунтів! Покращена версія PollyMc, підтримує багато різних ОС, має підтримку збірок з різних сайтів [Prism Launcher](https://prismlauncher.org)", false);
        embed.addField("SKLauncher", "Альтернатива PollyMc, підтримує обидва варіанти акаунтів (ліцензійні і неліцензійні), не потребує багато ресурсів, має підтримку різних платформ, встановлення модпаків із різних ресурсів [SKLauncher](https://skmedix.pl)", false);
        embed.setImage("https://skmedix.pl/images/social.jpg");
        return embed;
    }

    @NotNull
    public static net.dv8tion.jda.api.EmbedBuilder getEstimatedArrivalTimeEmbed() {
        net.dv8tion.jda.api.EmbedBuilder embed = new net.dv8tion.jda.api.EmbedBuilder();
        embed.setTitle("**Ми нічого не можемо обіцяти!**");
        embed.setColor(Color.CYAN);
        embed.setDescription("Сервер цілком і повністю залежить від виходу програмного забезпечення для запуску сервера. Ми не можемо вплинути на цей процес, тому не можемо обіцяти точний час відкриття сервера");
        embed.addField("(c) Paper", "Оновлення для Paper не мають жодних термінів виходу, ніколи. Будь-які оновлення з'являться тоді, коли будуть готові, і єдине, що вам залишається - терпляче чекати на них разом з усіма іншими", false);
        embed.addField("На що ми ще очікуємо?", "[Патчі продуктивності серверу](https://github.com/PaperMC/Paper/tree/master/patches/unapplied/server)\n[Зміни від першоджерела Spigot](https://www.spigotmc.org/threads/spigot-bungeecord-1-21.650824/#post-4740590)", false);
        return embed;
    }

    @NotNull
    public static net.dv8tion.jda.api.EmbedBuilder getOfftopEmbed() {
        net.dv8tion.jda.api.EmbedBuilder embed = new net.dv8tion.jda.api.EmbedBuilder();
        embed.setTitle("**Це НЕ канал для запитань!**");
        embed.setColor(Color.cyan);
        embed.setDescription("Тікети призначені для вирішення приватних проблем - проблем, які можуть містити приватні дані гравця, що звернувся за допомогою; проблем, які безпосередньо стосуються техніних несправностей серверу. Для запитань використовуйте <#1221862007251271740>!");
        return embed;
    }

    @NotNull
    public static net.dv8tion.jda.api.EmbedBuilder getMi9HelpEmbed() {
        net.dv8tion.jda.api.EmbedBuilder embed = new net.dv8tion.jda.api.EmbedBuilder();
        embed.setTitle("Ви стали свідком порушення правил?");
        embed.setColor(Color.CYAN);
        embed.setDescription("Ми вам обов'язково допоможемо! Але для цього нам потрібно зібрати необхідну інформацію. Заповніть форму, яка наведена нижче, і ми обов'язково розглянемо вашу скаргу!");
        embed.addField("1. Ваш ігровий нікнейм", "Ваш нікнейм на сервері", false);
        embed.addField("2. Ігровий нікнейм порушника", "Нікнейм гравця, який порушив правила", false);
        embed.addField("3. Скріншоти / докази", "Скріншоти або відео, які підтверджують порушення", false);
        embed.addField("4. Пункт порушення / Опис", "Пункт порушення / опис порушення, яке ви побачили", false);
        embed.addField("5. Координати (якщо є)", "Координати місця події. Щоб ми знали де шукати!", false);
        return embed;
    }

    @NotNull
    public static net.dv8tion.jda.api.EmbedBuilder getBugReportEmbed() {
        net.dv8tion.jda.api.EmbedBuilder embed = new net.dv8tion.jda.api.EmbedBuilder();
        embed.setTitle("Форма звіту про помилку");
        embed.setColor(Color.getHSBColor(257.344f, 55.7315f, 93));
        embed.setDescription("Знайшли технічну несправність? Заповніть форму, яка наведена нижче, і ми обов'язково виправимо її!");
        embed.addField("1. Яку версію гри ви використовуєте?", "Вкажіть вашу версію гри, завантажувач модів (Forge, Fabric, NeoForge, etc.), встановлені модифікації, тощо", false);
        embed.addField("2. Поясніть суть технічної помилки", "В чому полягає проблема, з якою ви зустрілися? Яким чином вона впливає на ігровий процес?", false);
        embed.addField("3. Як ми можемо відтворити помилку?", "Опишіть порядок дій, яки ви робили, що привело вас до зустрічі із помилкою, щоб ми могли відтворити її самостійно", false);
        embed.addField("4. Прикріпіть лог файли", "Якщо ваша помилка стосується клієнту, прикріпіть сюди лог-файли гри (зазвичай знаходиться у теці із грою)", false);
        embed.addField("5. Прикріпіть скріншоти / відео-докази", "Ви змогли записати помилку на відео, чи можете прикріпити скріншоти, що підтверджують її? Додайте його до свого повідомлення", false);
        return embed;
    }
}
