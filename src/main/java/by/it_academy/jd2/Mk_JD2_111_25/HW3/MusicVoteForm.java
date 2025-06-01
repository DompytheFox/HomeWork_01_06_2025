package by.it_academy.jd2.Mk_JD2_111_25.HW3;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


@WebServlet(urlPatterns = "/musicvote")

public class MusicVoteForm extends HttpServlet {

    String[] artistsArr = new String[]{"Korpiklaani", "Валентина Легкоступова", "Кирилл Сочный", "Красная Плесень"};
    String[] genresArr = new String[]{"Post-punk", "Heavy metal", "Retrowave", "Italo disco", "Goregrind", "Pop music", "Gangsta rap", "Nerdcore", "Jazz", "Другой жанр"};

    HashMap<String, Integer> artistMap = new HashMap<>();
    HashMap<String, Integer> genreMap = new HashMap<>();
    HashMap<String, Date> aboutMap = new HashMap<>();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        String artist = req.getParameter("artist");
        String[] genres = req.getParameterValues("genre");
        String about = req.getParameter("about");

        PrintWriter writer = resp.getWriter();

        if (artist == null || genres.length < 3 || about.trim() == "") {

            writer.write("<table border = '0' cellpadding = '5' width = '300'>");
            writer.write("<p><span style='color: red; font-size: 22px;'>Ошибка!</span></p>");
            writer.write("<tr><td>Не все поля заполнены</td></tr>");
            writer.write("<tr><td>Либо выбрано менее трёх жанров!</td></tr>");
            writer.write("<tr><td><button onclick=\"history.back()\">Вернуться</button></td></tr>");
            writer.write("</table>");
        } else {
            Date voteDate = new Date();
            if (artistMap.containsKey(artist)) {
                artistMap.put(artist, artistMap.get(artist) + 1);
            } else {
                artistMap.put(artist, 1);
            }
            for (String genre : genres) {
                if (genreMap.containsKey(genre)) {
                    genreMap.put(genre, genreMap.get(genre) + 1);
                } else {
                    genreMap.put(genre, 1);
                }
            }
            aboutMap.put(about, voteDate);

            writer.write("<p><span style='color: red; font-size: 22px;'>Результаты голосования</span></p>");
            writer.write("<table border = '0' cellpadding = '5' width = '400'>");
            writer.write("<tr><td><b>Исполнители</b></td><td><b>Голоса</b></td></tr>");
            for (Map.Entry<String, Integer> e : mapSorter(artistMap)) {
                writer.write("<tr><td>" + artistsArr[Integer.parseInt(e.getKey())] + "</td><td>" + e.getValue() + "</td></tr>");
            }
            writer.write("<tr><td><b>Жанры</b></td><td><b>Голоса</b></td></tr>");
            for (Map.Entry<String, Integer> e : mapSorter(genreMap)) {
                writer.write("<tr><td>" + genresArr[Integer.parseInt(e.getKey())] + "</td><td>" + e.getValue() + "</td></tr>");
            }
            writer.write("</table>");

            writer.write("<table border = '0' cellpadding = '5' width = '400'>");
            writer.write("<tr><td><button onclick=\"document.location='./'\">На главную</button></td></tr>");
            writer.write("<tr><td><b>Комментарии:</b></td></tr>");
            for (Map.Entry<String, Date> e : mapSorter(aboutMap)) {
                writer.write("<tr><td>" + e.getKey() + "</td></tr>");
                writer.write("<tr><td><span style='color: gray; font-size: 12px;'>добавлено " + e.getValue() + "</span></td></tr>");
            }
            writer.write("<tr><td></td></tr>");

            writer.write("</table>");
        }
    }

    public static <K, V extends Comparable<? super V>> List<Map.Entry<K, V>> mapSorter(Map<K, V> m) {
        List<Map.Entry<K, V>> li = new ArrayList<>(m.entrySet());
        li.sort(Map.Entry.comparingByValue());
        Collections.reverse(li);
        return li;
    }
}

