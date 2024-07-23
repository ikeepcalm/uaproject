package dev.ua.ikeepcalm.custom;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;

@WebServlet
public class FaviconServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String resourcePath = "/META-INF/resources/custom/favicon.svg";

        try (InputStream resourceStream = getClass().getResourceAsStream(resourcePath)) {
            if (resourceStream == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            String mimeType = getServletContext().getMimeType(resourcePath);
            response.setContentType(mimeType != null ? mimeType : "application/octet-stream");
            resourceStream.transferTo(response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}