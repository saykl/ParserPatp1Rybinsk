package com.company;

public class Main {


    public static String GetURLPath(String[] args) {
        Boolean resU = false;
        Boolean resP = false;
        Argument arg = new Argument();
        String Path = "";
        int pos = 0;
        if (arg.FindArg(args, "P")) {
            pos = arg.FindPosArg(args, "P");
            pos = pos + 1; //Позиция пути к файлу Html или ссылке
            Path = args[pos];
            resP = true;
        } else {
            resP = false;
        }

        if (arg.FindArg(args, "U")) {
            pos = arg.FindPosArg(args, "U");
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
    }


    
    public static void main(String[] args) {
        Parser ParsSite = new Parser();
        Argument arg = new Argument();
        arg.CheckHelp(args); //проверяем нужно ли выводить справку, если нужно - выводим
        int direction = arg.CheckDirection(args);
        String Path = GetURLPath(args);
        Boolean UseArgs = false; //Используем ли аргументы
        Boolean OnlyFirst = false;
        Boolean isUrl = false;
        if (arg.FindArg(args, "U")) isUrl = true;
        else
            Path = Path.replace("\\", "/");
        if (args.length != 0)
            UseArgs = true;

        if (direction == 0) {
            if (((arg.FindArg(args, "Full")) || ((arg.FindArg(args, "home")) && arg.FindArg(args, "work")))
                    && (!arg.FindArg(args, "OnlyFirst")))
                System.out.println( ParsSite.Pars(Path, 2, isUrl));
            if (arg.FindArg(args, "currentAll"))
                System.out.println(ParsSite.GetCurrent(ParsSite.Pars(Path, 2, isUrl)));
            if (arg.FindArg(args, "OnlyFirst")) System.out.println(arg.OnlyFirstTime(ParsSite.GetCurrent(ParsSite.Pars(Path, 2, isUrl))));
            if (((arg.FindArg(args, "Full")) || ((arg.FindArg(args, "home")) && arg.FindArg(args, "work")))
                    && (!arg.FindArg(args, "OnlyFirst")))
                System.out.println( ParsSite.Pars(Path, 3, isUrl));
            if (arg.FindArg(args, "currentAll"))
                System.out.println( ParsSite.GetCurrent(ParsSite.Pars(Path, 3, isUrl)));
            if (arg.FindArg(args, "OnlyFirst")) System.out.println(arg.OnlyFirstTime(ParsSite.GetCurrent(ParsSite.Pars(Path, 3, isUrl))));
        } else {
            if ((direction == 2) && (!(arg.FindArg(args, "OnlyFirst"))))
                System.out.println(ParsSite.Pars(Path, direction, isUrl));
            if ((direction == 3) && (!(arg.FindArg(args, "OnlyFirst"))))
                System.out.println(  ParsSite.Pars(Path, direction, isUrl));


            if (arg.FindArg(args, "OnlyFirst")) System.out.println(arg.OnlyFirstTime(ParsSite.GetCurrent(ParsSite.Pars(Path, direction, isUrl))));
            if (arg.FindArg(args, "currentAll")) System.out.println(ParsSite.GetCurrent(ParsSite.Pars(Path, direction, isUrl)));
        }

    }
}

