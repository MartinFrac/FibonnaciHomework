package com.infoshareacademy.web;

import com.infoshareacademy.beans.FibonacciCalculator;
import com.infoshareacademy.freemarker.TemplateProvider;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/sequence")
public class FibonacciServlet extends HttpServlet {

    @Inject
    private TemplateProvider templateProvider;

    @Inject
    private FibonacciCalculator fibonacciCalculator;

    private static final String TEMPLATE_FIBONACCI = "fibonacci";

    private static final Logger LOG = LoggerFactory.getLogger(FibonacciServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.addHeader("Content-Type", "text/html; charset=utf-8");

        Map<String, Object> dataModel = new HashMap<>();
        String reqNumber = req.getParameter("replyNumber");

        try {
            int fibonacciNumber = Integer.parseInt(reqNumber);
            if(fibonacciNumber>0 && fibonacciNumber<47) {
                List<Integer> fibonnaciList = new ArrayList<>(fibonacciCalculator.getFibonacci(fibonacciNumber));
                dataModel.put("list", fibonnaciList);
                dataModel.put("fibonacci", fibonnaciList.get(fibonnaciList.size() - 1));
                dataModel.put("state", "success");
            }else if(fibonacciNumber>46){
                dataModel.put("state", "tooHigh");
            }
            else{
                dataModel.put("state", "error");
            }
        }catch (NumberFormatException | NullPointerException e){
            dataModel.put("state", "error");
        }

        Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_FIBONACCI);
        try {
            template.process(dataModel, resp.getWriter());
        } catch (
                TemplateException e) {
            LOG.warn("Error while processing the template: " + e.getMessage(), e);
        }
    }
}
