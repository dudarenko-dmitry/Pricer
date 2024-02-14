//package pl.senla.pricer.controller;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
////@RestController // doesn't work in http: doesn't redirect to templates *.html
//@Controller
//@RequestMapping("/auth")
//@Slf4j
//public class ControllerSecurity {
//
//    @GetMapping("/login")
//    public String getLoginPage() {
//        log.debug("Test login");
//        return "login";
//    }
//
//    @GetMapping("/main")
//    public String getMainPage() {
//        log.debug("Test main");
//        return "main";
//    }
//
//    @GetMapping("/registration")
//    public String createRegistrationUser() {
//        log.debug("Test registration");
//        return "registration";
//    }
//
//}
