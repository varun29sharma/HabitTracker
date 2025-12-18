package com.habittracker.services;

import javafx.scene.Scene;
import javafx.scene.Parent;
import java.util.HashMap;
import java.util.Map;

public class ThemeService {
    private static boolean isDarkMode = false;
    private static Map<String, String> themeMap = new HashMap<>();
    
    static {
        themeMap.put("light", "/css/light.css");
        themeMap.put("dark", "/css/dark.css");
    }
    
    public static boolean isDarkMode() {
        return isDarkMode;
    }
    
    public static void toggleTheme(Scene scene) {
        isDarkMode = !isDarkMode;
        applyTheme(scene);
    }
    
    public static void applyTheme(Scene scene) {
        if (scene == null) return;
        
        // Remove all existing theme stylesheets
        scene.getStylesheets().clear();
        
        // Add current theme
        String theme = isDarkMode ? "dark" : "light";
        String cssPath = themeMap.get(theme);
        
        // Try to load CSS resource
        java.net.URL cssUrl = ThemeService.class.getResource(cssPath);
        if (cssUrl == null) {
            // Try with class loader
            cssUrl = ThemeService.class.getClassLoader().getResource(cssPath.substring(1)); // Remove leading /
        }
        
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.err.println("WARNING: Could not load CSS file: " + cssPath);
        }
    }
    
    public static void applyThemeToScene(Scene scene) {
        applyTheme(scene);
    }
}

