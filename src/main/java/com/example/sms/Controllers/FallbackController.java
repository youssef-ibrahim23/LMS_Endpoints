package com.example.sms.Controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class FallbackController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        return "error";
    }

}