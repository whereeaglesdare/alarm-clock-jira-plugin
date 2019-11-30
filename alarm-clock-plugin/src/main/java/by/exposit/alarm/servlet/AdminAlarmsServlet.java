package by.exposit.alarm.servlet;

import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.user.util.UserUtil;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.auth.LoginUriProvider;
import com.atlassian.sal.api.websudo.WebSudoManager;
import com.atlassian.sal.api.websudo.WebSudoSessionException;
import com.atlassian.templaterenderer.TemplateRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

@Component
public class AdminAlarmsServlet extends HttpServlet{
    private static final Logger log = LoggerFactory.getLogger(AdminAlarmsServlet.class);

    @ComponentImport
    @Autowired
    private JiraAuthenticationContext authenticationContext;

    @ComponentImport
    @Autowired
    private LoginUriProvider loginUriProvider;

    @ComponentImport
    @Autowired
    private UserUtil userUtil;

    @ComponentImport
    @Autowired
    private WebSudoManager webSudoManager;

    @ComponentImport
    @Autowired
    private TemplateRenderer templateRenderer;

    private static final String ADMIN_ALARMS_TEMPLATE = "/templates/servlet/admin-alarms.vm";
    /**
     * Getting URI for redirect if user not logged in
     * @param request HttpServletRequest
     * @return URI for redirect
     */
    private URI getUri(HttpServletRequest request) {
        StringBuffer builder = request.getRequestURL();
        if (request.getQueryString() != null)
        {
            builder.append("?");
            builder.append(request.getQueryString());
        }
        return URI.create(builder.toString());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /**
         * This part of code should realize in ServletFilter
         */
        if(!authenticationContext.isLoggedInUser()) {
            resp.sendRedirect(loginUriProvider.getLoginUri(getUri(req)).toASCIIString());
            return;
        }

        if(!userUtil.getJiraSystemAdministrators().contains(authenticationContext.getLoggedInUser())
                && !userUtil.getJiraAdministrators().contains(authenticationContext.getLoggedInUser())) {
            resp.setStatus(403);
            return;
        };
        /**
         * Rendering template
         */
        try {
            templateRenderer.render(ADMIN_ALARMS_TEMPLATE, resp.getWriter());
        }  catch (WebSudoSessionException ex) {
            webSudoManager.willExecuteWebSudoRequest(req);
        }
    }

}