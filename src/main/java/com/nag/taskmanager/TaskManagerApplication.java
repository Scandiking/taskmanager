package com.nag.taskmanager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class TaskManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskManagerApplication.class, args);
        openHomePage();
    }

    private static void openHomePage() {
        String url = "http://localhost:8080/api";
        try {
            Runtime rt = Runtime.getRuntime();
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                // For Windows
                rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
            } else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                // For Mac
                rt.exec("open " + url);
            } else if (System.getProperty("os.name").toLowerCase().contains("nix") ||
                    System.getProperty("os.name").toLowerCase().contains("nux")) {
                // For Linux
                rt.exec("xdg-open " + url);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
