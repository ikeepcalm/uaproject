package dev.ua.ikeepcalm;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.AppShellSettings;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
@Theme(value = "uaproject")
public class Application implements AppShellConfigurator, CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void configurePage(AppShellSettings settings) {
        settings.addFavIcon("icon", "images/uaproject-pfp.png", "256x256");
        settings.addLink("shortcut icon", "icons/favicon.ico");
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);

        label:
        while (true) {
            System.out.println("Enter a command (type 'stop' to exit): ");
            String input = scanner.nextLine();

            String[] parts = input.split(" ", 2);
            String command = parts[0].toLowerCase();

            switch (command) {
                case "stop", "restart":
                    System.exit(0);
                    break label;
                default:
                    break;
            }
        }
        scanner.close();
    }
}
