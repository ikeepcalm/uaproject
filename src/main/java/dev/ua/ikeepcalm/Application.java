package dev.ua.ikeepcalm;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
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
