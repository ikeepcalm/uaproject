package dev.ua.ikeepcalm.custom;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;

@WebServlet
public class AstroServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getPathInfo() == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String requestedFile = request.getPathInfo();
        String path = request.getServletPath();
        String resourcePath = "/META-INF/resources/custom/wiki" + path + requestedFile;

        try (InputStream resourceStream = getClass().getResourceAsStream(resourcePath)) {
            if (resourceStream == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            String mimeType = getServletContext().getMimeType(resourcePath);
            response.setContentType(mimeType != null ? mimeType : "text/html");
            resourceStream.transferTo(response.getOutputStream());
        }
    }
}
