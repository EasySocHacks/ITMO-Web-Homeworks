package ru.itmo.wp.servlet;

import ru.itmo.wp.util.ImageUtils;

import javax.imageio.ImageIO;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;

public class CaptchaFilter extends HttpFilter {

    private final String CAPTCHA_URI = "/captcha.html";
    private final String EXPECTED_NUMBER_ATTRIBUTE = "expectedNumber";
    private final String IS_CAPTCHA_ACCEPTED_ATTRIBUTE = "isCaptchaAccepted";
    private final String REDIRECTED_PAGE_ATTRIBUTE = "redirectedPage";
    private final String ENTERED_NUMBERS_URI = "/captcha/enter";

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        Object isCaptchaAccepted = request.getSession().getAttribute(IS_CAPTCHA_ACCEPTED_ATTRIBUTE);
        String uri = request.getRequestURI();

        if (isCaptchaAccepted != null && (boolean) isCaptchaAccepted && uri != ENTERED_NUMBERS_URI) {
            if (uri.equals(CAPTCHA_URI)) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

            chain.doFilter(request, response);
        } else {
            if (uri.equals(CAPTCHA_URI)) {
                chain.doFilter(request, response);
            } else if (uri.equals("/captcha/img") && request.getMethod().equals("GET")) {
                String expectedNumber = (String) request.getSession().getAttribute(EXPECTED_NUMBER_ATTRIBUTE);

                request.getSession().setAttribute(EXPECTED_NUMBER_ATTRIBUTE, expectedNumber);

                response.setContentType("image/png");
                OutputStream outputStream = response.getOutputStream();
                ByteArrayInputStream byteArrayOutputStream = new ByteArrayInputStream(ImageUtils.toPng(expectedNumber));
                BufferedImage bufferedImage = ImageIO.read(byteArrayOutputStream);
                ImageIO.write(bufferedImage, "png", outputStream);

                outputStream.flush();
            } else if (uri.equals(ENTERED_NUMBERS_URI) && request.getMethod().equals("POST")) {
                String inputString = request.getParameter("answer");
                String expectedString = (String) request.getSession().getAttribute(EXPECTED_NUMBER_ATTRIBUTE);

                if (inputString.equals(expectedString)) {
                    request.getSession().setAttribute(IS_CAPTCHA_ACCEPTED_ATTRIBUTE, true);
                    response.sendRedirect((String) request.getSession().getAttribute(REDIRECTED_PAGE_ATTRIBUTE));
                    request.removeAttribute(REDIRECTED_PAGE_ATTRIBUTE);
                } else {
                    response.sendRedirect("/");
                }
            } else {
                int expectedNumber = (int) (Math.random() * 899 + 100);

                request.getSession().setAttribute(EXPECTED_NUMBER_ATTRIBUTE, String.valueOf(expectedNumber));

                if (request.getSession().getAttribute(REDIRECTED_PAGE_ATTRIBUTE) == null) {
                    request.getSession().setAttribute(REDIRECTED_PAGE_ATTRIBUTE, uri);
                }

                response.sendRedirect(CAPTCHA_URI);
            }
        }
    }
}
