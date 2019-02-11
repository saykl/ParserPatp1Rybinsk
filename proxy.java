package com.company;

import autoitx4java.AutoItX;
import com.jacob.com.LibraryLoader;

import java.io.File;

public class proxy {
    public void mozProxyAuth(String login, String password) {
        String JACOB_DLL_TO_USE = System.getProperty("sun.arch.data.model").contains("32") ? "jacob-1.18-x86.dll" : "jacob-1.18-x64.dll";
        File file = new File(System.getProperty("user.dir"), JACOB_DLL_TO_USE);
        System.setProperty(LibraryLoader.JACOB_DLL_PATH, file.getAbsolutePath());
        AutoItX x = new AutoItX();
        if (x.winWait("Требуется аутентификация", null, 10)) {
            x.winActivate("Требуется аутентификация");
            x.send(login + "{TAB}" + password + "{ENTER}", false);
        }
    }

}
