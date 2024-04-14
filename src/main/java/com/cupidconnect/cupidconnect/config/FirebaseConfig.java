package com.cupidconnect.cupidconnect.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
@Component
public class FirebaseConfig {
    private static final Logger logger = LoggerFactory.getLogger(FirebaseConfig.class);
    @PostConstruct
    public void initialization() {
        FileInputStream serviceAccount =
                null;
        try {
            serviceAccount = new FileInputStream("./serviceAccountKey.json");
            FirebaseOptions options = null;
                options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .setDatabaseUrl("https://vien-toan-3d-default-rtdb.asia-southeast1.firebasedatabase.app")
                        .build();

            FirebaseApp.initializeApp(options);
        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }
}
