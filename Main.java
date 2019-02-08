package com.company;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Calendar;

import autoitx4java.AutoItX;
import com.jacob.com.*;


public class Main {

    public static boolean isNow(String hAr, String mAr) {
        boolean min = false;
        Calendar rightNow = Calendar.getInstance();
        int hour = rightNow.get(Calendar.HOUR_OF_DAY);
        int minute = rightNow.get(Calendar.MINUTE);
        String limit = hAr + ":" + mAr;
        String[] parts = limit.split(":");
        Calendar cal2 = Calendar.getInstance();
        cal2.set(Calendar.HOUR_OF_DAY, Integer.parseInt(parts[0]));
        cal2.set(Calendar.MINUTE, Integer.parseInt(parts[1]));
        cal2.before(rightNow);
        if (rightNow.before(cal2)) //(hour <= Integer.parseInt(hAr))
            //  if (minute < Integer.parseInt(mAr))
            min = true;
        else
            min = false;
        return min;
    }

    public static boolean help() {
        //home, work - направление, cur - вывести первый подходящий маршрут на текущее время,
        // currentAll - все подходящие маршруты на текущее время
        System.out.println("home, work - направление");
        System.out.println("OnlyFirst - вывести первый подходящий маршрут на текущее время");
        System.out.println("currentAll -  все подходящие маршруты на текущее время");
        System.out.println("Full -  все подходящие маршруты на текущее время");
        return true;
    }

    public static boolean FindArg(String[] args, String arg) {
        boolean result = false;
        for (int i = 0; i < args.length; i++)
            if (args[i].equals(arg))
                result = true;
        return result;
    }

    public static int FindPosArg(String[] args, String arg) {
        int result = 0;
        for (int i = 0; i < args.length; i++)
            if (args[i].equals(arg))
                result = i;
        return result;
    }


    public static int CheckDirection(String[] args) {
        int direction = 0;
        Boolean isWork=false;
        Boolean isHome=false;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("work")) {
                isWork=true;
                direction = 3;
            }
            if (args[i].equals("home")) {
                direction = 2;
                isHome=true;
            }
        }
        if ((isHome == true) && (isWork==true))
            direction = 0;
        return direction;
    }

    public static boolean CheckHelp(String[] args) {
        boolean direction = true;
        if ((FindArg(args, "help")) || (FindArg(args, "-help")) || (FindArg(args, "-h")) ||
                (FindArg(args, "h")))
            help();
        return direction;
    }

    public static String GetCurrent(String AllTime) {
        String[] Fullarray = AllTime.split(" ");
        String CurrentTimeStr = "";
        for (int i = 0; i < Fullarray.length; i++) {
            //  System.out.println(Fullarray[i]);
            String[] PastArray = Fullarray[i].split("-");
            if (isNow(PastArray[0], PastArray[1]))
                CurrentTimeStr = CurrentTimeStr + " " + PastArray[0] + ":" + PastArray[1];
        }
        //  System.out.println("С учётом текущего времени : " + CurrentTimeStr);
        return CurrentTimeStr;
    }

    public static String OnlyFirstTime(String AllTime) {
        String res = "";
        AllTime.trim();
        String[] Fullarray = AllTime.split(" ");
        //   if (Fullarray.length !=0)
        res = Fullarray[1];
        return res;
        //  else return "";
    }

    public static String GetURLPath(String[] args) {
        Boolean resU = false;
        Boolean resP = false;
        String Path = "";
        int pos = 0;
        if (FindArg(args, "P")) {
            pos = FindPosArg(args, "P");
            pos = pos + 1; //Позиция пути к файлу Html или ссылке
            Path = args[pos];
            resP = true;
        } else {
            resP = false;
        }

        if (FindArg(args, "U")) {
            pos = FindPosArg(args, "U");
            pos = pos + 1; //Позиция пути к файлу Html или ссылке
            Path = args[pos];
            resU = true;
        } else {

            resU = false;
        }
        if ((!resU) && (!resP)) {
            System.out.println("Нужно ввести ссылку или путь к файлу html, синтаксис: P path/file и U url");
            System.exit(0);
        }
        return Path;
        //  else return "";
    }

    private void mozProxyAuth(String login, String password) {
        String JACOB_DLL_TO_USE = System.getProperty("sun.arch.data.model").contains("32") ? "jacob-1.18-x86.dll" : "jacob-1.18-x64.dll";
        File file = new File(System.getProperty("user.dir"), JACOB_DLL_TO_USE);
        System.setProperty(LibraryLoader.JACOB_DLL_PATH, file.getAbsolutePath());
        AutoItX x = new AutoItX();
        if (x.winWait("Требуется аутентификация", null, 10)) {
            x.winActivate("Требуется аутентификация");
            x.send(login + "{TAB}" + password + "{ENTER}", false);
        }
    }


    public static String Pars(String Path, int direction, boolean isUrl) {
        String P2 = "";
        // JSoup Parsing an HTML file in Java
        Document htmlFile = Jsoup.parse(Path, "UTF-8"); // wrong
        //  Document htmlFile = null;
        try {
            if (isUrl)
                htmlFile = Jsoup.connect(Path).get();
            else
                htmlFile =  Jsoup.parse(new File(Path), "UTF-8");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Element ElementP2 = htmlFile.getElementsByTag("p").get(direction);
        P2 = ElementP2.text();
        //  System.out.println("Отправление от ПАТП : " + p2);
        P2.replaceAll("Вниманию пассажиров!", "");
        P2 = P2.substring(0, P2.lastIndexOf("Вниманию пассажиров"));
     /*   switch (direction) {
            case 2:
                System.out.println("Отправление от ПАТП : " + P2);
                break;
            case 3:
                System.out.println("Отправление от завода Призма : " + P2);
                break;
        }*/
        return P2;
    }


    //TODO Сделать аргументы автобуc, первое подходящее время, всё подходящие, полное расписание, направление
    public static void main(String[] args) {
        //  mozProxyAuth("apetrov","Mother1965");
        CheckHelp(args); //проверяем нужно ли выводить справку, если нужно - выводим
        int direction = CheckDirection(args);
        String Path = GetURLPath(args);

        Boolean UseArgs = false; //Используем ли аргументы
        Boolean OnlyFirst = false;
        Boolean isUrl = false;
        if (FindArg(args, "U")) isUrl = true;
        else
            Path = Path.replace("\\", "/");
        //  System.out.println(Integer.toString(pos));
        //System.out.println();


        // System.out.println(args[pos]);

        if (args.length != 0)
            UseArgs = true;

        if (direction == 0) {
            if (((FindArg(args, "Full")) || ((FindArg(args, "home")) && FindArg(args, "work")))
                && (!FindArg(args, "OnlyFirst")))
                System.out.println("Отправление от ПАТП : " + Pars(Path, 2, isUrl));
            if (FindArg(args, "currentAll"))
                System.out.println("С учётом текущего времени : " + GetCurrent(Pars(Path, 2, isUrl)));
            if (FindArg(args, "OnlyFirst")) System.out.println(OnlyFirstTime(GetCurrent(Pars(Path, 2, isUrl))));
            if (((FindArg(args, "Full")) || ((FindArg(args, "home")) && FindArg(args, "work")))
                && (!FindArg(args, "OnlyFirst")))
            System.out.println("Отправление от призмы : " + Pars(Path, 3, isUrl));
            if (FindArg(args, "currentAll"))
                System.out.println("С учётом текущего времени : " + GetCurrent(Pars(Path, 3, isUrl)));
            if (FindArg(args, "OnlyFirst")) System.out.println(OnlyFirstTime(GetCurrent(Pars(Path, 3, isUrl))));
        } else {
            if ((direction == 2) && (!(FindArg(args, "OnlyFirst"))))
                System.out.println("Отправление от ПАТП : " + Pars(Path, direction, isUrl));
            if ((direction == 3) && (!(FindArg(args, "OnlyFirst"))))
                System.out.println("Отправление от завода Призма : " + Pars(Path, direction, isUrl));


            if (FindArg(args, "OnlyFirst")) System.out.println(OnlyFirstTime(GetCurrent(Pars(Path, direction, isUrl))));
            if (FindArg(args, "currentAll")) System.out.println(GetCurrent(Pars(Path, direction, isUrl)));
        }

    }
}

