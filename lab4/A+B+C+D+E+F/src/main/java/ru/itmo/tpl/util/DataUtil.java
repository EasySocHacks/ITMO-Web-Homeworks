package ru.itmo.tpl.util;

import ru.itmo.tpl.model.Link;
import ru.itmo.tpl.model.Post;
import ru.itmo.tpl.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DataUtil {
    private static final List<User> USERS = Arrays.asList(
            new User(1, "MikeMirayanov", "Mikhail Mirzayanov", UserColor.RED),
            new User(2, "tourist", "Genady Korotkevich", UserColor.RED),
            new User(3, "emusk", "Elon Musk",  UserColor.GREEN),
            new User(5, "pashka", "Pavel Mavrin", UserColor.BLUE),
            new User(7, "geranazavr555", "Georgiy Nazarov", UserColor.RED),
            new User(11, "cannon147", "Erofey Bashunov", UserColor.GREEN)
    );

    private static final List<Link> HEADER_LINKS = Arrays.asList(
            new Link("/index", "Index"),
            new Link("/misc/help", "Help"),
            new Link("/users", "Users")
    );

    private static final List<Post> POSTS = Arrays.asList(
            new Post(1,
                    "О сорванном раунде 591/Технокубок 2020 — Отборочный Раунд 1",
                    "Привет, Codeforces!\n" +
                            "\n" +
                            "К сожалению, недоброжелатели сорвали проведение раунда, сделав DDOS на нашу инфраструктуру. Ни координатор, ни авторы раунда не виноваты, что у вас не получилось полноценно принять участие в раунде. Пожалуйста, не минусуйте анонс раунда. Я думаю, что такая ситуация — дополнительный повод поддержать авторов. Они подготовили хорошие задачи!\n" +
                            "\n" +
                            "Видимо, подобную атаку надо расценивать как симптом того, что Codeforces перерос фазу юношества и вступил во взрослую серьезную жизнь. Конечно, мы ответим адекватными мерами, чтобы защититься от подобных инцидентов. К счастью, за почти 10 лет работы вокруг сложилось большое сообщество тех, кому небезразличен Codeforces. Мы не переживаем по поводу возможных дополнительных трат или приложенных усилий. У нас всё получится. Раунды должны продолжаться.\n" +
                            "\n" +
                            "— MikeMirzayanov\n" +
                            "\n" +
                            "UPD 1: Раунды будут нерейтинговыми, но по результатам тестирования лучшие участники отбора Технокубка будут приглашены в Финал. Мы сообщим подробности завтра. Кроме того, будет проведён дополнительный (четвёртый) отборочный раунд.\n" +
                            "\n" +
                            "UPD 2: Ура! Сегодня пережили еще одну DDOS-атаку. Раунд прошел неидеально, но сорван не был.",
                    1),
            new Post(5,
                    "AtCoder Grand Contest 020",
                    "What is better than setting one AtCoder Grand Contest? Setting two AtCoder Grand Contests, of course!\n" +
                            "\n" +
                            "AtCoder Grand Contest 020 will be held on Sunday (time). The writer is tourist.\n" +
                            "\n" +
                            "Contest Link\n" +
                            "\n" +
                            "Contest Announcement\n" +
                            "\n" +
                            "This is the first contest of 2018 counted towards AtCoder Race Ranking. If you get into top 30 in this contest, you will get GP30 scores.\n" +
                            "\n" +
                            "The point values will be 300 — 500 — 700 — 1100 (500) — 1400 — 2100. Note that the contest duration is unusual (130 minutes).\n" +
                            "\n" +
                            "Let's discuss problems after the contest.",
                    2),
            new Post(777,
                    "Вторая командная интернет-олимпиада, Сезон 2019-20",
                    "Всем привет!\n" +
                            "\n" +
                            "19 октября в 15:00 состоится вторая командная интернет-олимпиада для школьников. Приглашаем вас принять в ней участие! В этот раз вам предстоит помочь или помешать Джокеру.\n" +
                            "\n" +
                            "Продолжительность олимпиады — 3 часа в базовой и 5 часов в усложненной номинациях. Вы сами можете выбрать номинацию, в которой будете участвовать. Не забудьте зарегистрироваться на цикл командных интернет-олимпиад в этом сезоне перед началом олимпиады, если не сделали этого ранее. Обратите внимание, что для участия в командных олимпиадах, нужно зарегистрировать команду (по ссылке \"Новая команда\"). Команда может содержать от 1 до 3 человек. Дополнительную информацию, а также расписание всех предстоящих командных интернет-олимпиад можно посмотреть на страничке интернет-олимпиад.\n" +
                            "\n" +
                            "Условия появятся на сайте в момент начала олимпиады, а также на вкладке \"Файлы\" в тестирующей системе. Тестирующая система находится по адресу pcms.itmo.ru.\n" +
                            "\n" +
                            "Олимпиаду для вас подготовили Николай Будин (Nebuchadnezzar), Ильдар Гайнуллин (300iq), Арсений Кириллов (senek_k), Дмитрий Саютин (_kun_), Михаил Анопренко (manoprenko), Даниил Орешников (doreshnikov), Григорий Шовкопляс (GShark) и Дмитрий Гнатюк (ima_ima_go).\n" +
                            "\n" +
                            "Для связи с жюри можно использовать адрес электронной почты iojury@gmail.com.\n" +
                            "\n" +
                            "Удачи!",
                    7),
            new Post(987,
                    "About Dasha Code Championship and Mirror Round 588",
                    "Hello.\n" +
                            "\n" +
                            "I just want to test text length ngt 250",
                    1)
    );

    private static List<Link> getHeaderLinks() {
        return  HEADER_LINKS;
    }

    private static List<User> getUsers() {
        return USERS;
    }

    private static List<Post> getPosts() {return POSTS;}

    public static void putData(Map<String, Object> data) {
        data.put("users", getUsers());
        data.put("header_links", getHeaderLinks());
        data.put("posts", getPosts());

        for (User user : getUsers()) {
            Object loggedUserId = data.get("logged_user_id");
            if (loggedUserId != null && user.getId() == (Long) loggedUserId) {
                data.put("user", user);
            }
        }
    }
}
