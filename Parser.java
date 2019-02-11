package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class Parser {
    public String Pars(String Path, int direction, boolean isUrl) {
        String P2 = "";
        // JSoup Parsing an HTML file in Java
        Document htmlFile = Jsoup.parse(Path, "UTF-8"); // wrong
        htmlFile = GetFile(htmlFile, isUrl, Path);
        Element ElementP2 = htmlFile.getElementsByTag("p").get(direction);
        P2 = ElementP2.text();
        P2.replaceAll("Вниманию пассажиров!", "");
        P2 = P2.substring(0, P2.lastIndexOf("Вниманию пассажиров"));
        return P2;
    }

    public static Document GetFile(Document htmlFile, boolean isUrl, String Path) {
        try {
            if (isUrl)
                htmlFile = Jsoup.connect(Path).get();
            else
                htmlFile = Jsoup.parse(new File(Path), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return htmlFile;
    }

    public String GetCurrent(String AllTime) {
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

}
