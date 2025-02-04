package com.banco.cuenta_bancaria.component;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.banco.cuenta_bancaria.util.AdminUserInitializer;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminUserInitializerRunner implements CommandLineRunner{

    private final AdminUserInitializer adminUserInitializer;

    @Override
    public void run(String... args) throws Exception {
        adminUserInitializer.initializeAdminUser();
    }

}
