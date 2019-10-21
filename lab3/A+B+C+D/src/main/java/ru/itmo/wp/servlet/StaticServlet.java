package ru.itmo.wp.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class StaticServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();

        ArrayList<String> parsedUri = parseUri(uri);
        ArrayList<File> requestedFiles = new ArrayList<>();

        boolean isUriContainsFiles = true;

        for (int i = 0; i < parsedUri.size(); i++) {
            if (!parsedUri.get(i).startsWith("/")) {
                parsedUri.set(i, "/" + parsedUri.get(i));
            }

            File fileInSrc = new File(new File(getServletContext().getRealPath("")).getParentFile().getParentFile(), "/src/main/webapp/static" + parsedUri.get(i));
            File fileInStatic = new File(getServletContext().getRealPath("/static" + parsedUri.get(i)));

            if (fileInSrc.isFile()) {
                requestedFiles.add(fileInSrc);
            } else if (fileInStatic.isFile()) {
                requestedFiles.add(fileInStatic);
            } else {
                isUriContainsFiles = false;
            }
        }

        if (isUriContainsFiles) {
            response.setContentType(getContentTypeFromName(parsedUri.get(0)));
            OutputStream outputStream = response.getOutputStream();

            for (File file : requestedFiles) {
                Files.copy(file.toPath(), outputStream);
            }

            outputStream.flush();
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void sendFile(HttpServletResponse response, File file, String contentType) throws IOException {
        response.setContentType(contentType);
        OutputStream outputStream = response.getOutputStream();
        Files.copy(file.toPath(), outputStream);
        outputStream.flush();
    }

    private ArrayList<String> parseUri(String uri) {
        return new ArrayList<String>(Arrays.asList(uri.split("\\+")));
    }

    private String getContentTypeFromName(String name) {
        name = name.toLowerCase();

        if (name.endsWith(".png")) {
            return "image/png";
        }

        if (name.endsWith(".jpg")) {
            return "image/jpeg";
        }

        if (name.endsWith(".html")) {
            return "text/html";
        }

        if (name.endsWith(".css")) {
            return "text/css";
        }

        if (name.endsWith(".js")) {
            return "application/javascript";
        }

        throw new IllegalArgumentException("Can't find content type for '" + name + "'.");
    }
}
