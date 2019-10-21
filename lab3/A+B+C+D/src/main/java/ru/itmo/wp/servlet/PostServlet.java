package ru.itmo.wp.servlet;

import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class PostServlet extends HttpServlet {

    final private String AUTH_URI = "/message/auth";
    final private String FIND_ALL_URI = "/message/findAll";
    final private String ADD_URI = "/message/add";

     ArrayList<HashMap<String, String>> usersMessages = new ArrayList<>();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();

        response.setContentType("application/json");

        switch (uri) {
            case AUTH_URI: {
                String user = request.getParameter("user");

                if (user == null) {
                    user = "";
                }

                request.getSession().setAttribute("user", user);

                String json = new Gson().toJson(user);

                response.getWriter().print(json);
                response.getWriter().flush();

                break;
            }

            case FIND_ALL_URI: {
                String json = new Gson().toJson(usersMessages);

                response.getWriter().print(json);
                response.getWriter().flush();

                break;
            }

            case ADD_URI: {
                String currentUser = request.getSession().getAttribute("user").toString();
                String text = request.getParameter("text");

                HashMap<String, String> newMessage = new HashMap<>();
                newMessage.put("user", currentUser);
                newMessage.put("text", text);

                usersMessages.add(newMessage);

                break;
            }
        }
    }
}