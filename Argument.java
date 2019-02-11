package com.company;

public class Argument {
    public int CheckDirection(String[] args) {
        int direction = 0;
        Boolean isWork = false;
        Boolean isHome = false;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("work")) {
                isWork = true;
                direction = 3;
            }
            if (args[i].equals("home")) {
                direction = 2;
                isHome = true;
            }
        }
        if ((isHome == true) && (isWork == true))
            direction = 0;
        return direction;
    }

    public boolean FindArg(String[] args, String arg) {
        boolean result = false;
        for (int i = 0; i < args.length; i++)
            if (args[i].equals(arg))
                result = true;
        return result;
    }

    public boolean CheckHelp(String[] args) {
        boolean direction = true;
        if ((FindArg(args, "help")) || (FindArg(args, "-help")) || (FindArg(args, "-h")) ||
                (FindArg(args, "h")))
            help();
        return direction;
    }

    public boolean help() {
        //home, work - направление, cur - вывести первый подходящий маршрут на текущее время,
        // currentAll - все подходящие маршруты на текущее время
        System.out.println("home, work - направление");
        System.out.println("OnlyFirst - вывести первый подходящий маршрут на текущее время");
        System.out.println("currentAll -  все подходящие маршруты на текущее время");
        System.out.println("Full -  все подходящие маршруты на текущее время");
        return true;
    }

    public int FindPosArg(String[] args, String arg) {
        int result = 0;
        for (int i = 0; i < args.length; i++)
            if (args[i].equals(arg))
                result = i;
        return result;
    }

    public String OnlyFirstTime(String AllTime) {
        String res = "";
        AllTime.trim();
        try
        {
            String[] Fullarray = AllTime.split(" ");
            res = Fullarray[1];
        }
        catch(Exception ex)
        {
            res = "";
        }

        return res;
    }
}
