package dev.ua.ikeepcalm.bot.impls;

import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class EmbedBuilder {

    @NotNull
    public static net.dv8tion.jda.api.EmbedBuilder getFormEmbed() {
        net.dv8tion.jda.api.EmbedBuilder embed = new net.dv8tion.jda.api.EmbedBuilder();
        embed.setTitle("Як пройти на сервер?");
        embed.setColor(Color.YELLOW);
        embed.setDescription("Вітаю, поціновувач майнкрафту! Для того, щоб отримати прохідку (дозвіл грати на нашому сервері) і \n" +
                             "вже безпосередньо зайти на нього за загальним айпі (uaproject.xyz), у тебе є два шляхи:");
        embed.addField("1. Придбати допуск за 50₴", "Все, що вам треба зробити для цього, натиснути на відповідну кнопку під цим повідомленням, вказати у полі Ім'я ваш дійсний ігровий нікнейм, обрати суму, що дорівнює, або перевищує 50₴. Ваш нікнейм буде додано до білого списку серверу майже моментально.", false);
        embed.addField("2. Заповнити анкету", "Для того, щоб заповнити анкету вам потрібно натиснути на кнопку під цим повідомленням, уважно прочитати кожне питання, написати розгорнуту відповідь, надіслати, і чекати, поки адміністрація перевірить її. Результат анкети ви отримаєте у приватних повідомленнях. Уважно передивіться правила перед цим варіантом! У вас є лише одна спроба.", false);
        embed.setImage("https://media.discordapp.net/attachments/1155135359265546392/1155135375329738824/vitania.png?ex=6623c4a6&is=66114fa6&hm=cb1f02603525bc12e4f2db9d72e34da2816534b197e4bae9ddef2acd50a7aea4&=&format=webp&quality=lossless&width=1440&height=450");
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
        embed.addField("(c) Папер", "Оновлення для Paper не мають жодних термінів виходу, ніколи. Будь-які оновлення з'являться тоді, коли будуть готові, і єдине, що вам залишається - терпляче чекати на них разом з усіма іншими", false);
        return embed;
    }
}
