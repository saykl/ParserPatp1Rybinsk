# ParserPatp1Rybinsk
Консольный парсер с сайта патп № 1 http://www.rybinsk-patp1.ru/ или http://patp1ryb.ru/

Утилита работает с обязательными ключами:
U <ссылка на сайт> - для работы с сайта или
P <путь к файлу> - для работы с диска. При работе с диска, на данный момент не должно быть пробелов.

Доступные команды
help - выводит список команд;
home, work - направление. home - от патп, work - от призмы;
OnlyFirst - вывести первый подходящий маршрут на текущее время;
currentAll -  все подходящие маршруты на текущее время;
Full -  все подходящие маршруты на текущее время;



Используется для оповещения и вывода информации на дашборд в умном доме.
Для оповещения можно использовать с аргументами OnlyFirst home, которые получают первое подходящее время. И умный дом через колонку Google Home оповещает сколько осталось до оставщегося времени прибытия автобуса.


Используемые библиотеки:
Jsoup.

Необходимые библиотеки, которые объявлены в коде, но не используются:
https://github.com/accessrichard/autoitx4java/blob/master/README.md
sourceforge.net/projects/jacob-project/
Планируется их использовать для авторизации moz proxy
