package co.com.example.authentication_security.controllers;

import co.com.example.authentication_security.entity.User;
import co.com.example.authentication_security.repository.UserRepository;
import co.com.example.authentication_security.service.CaptchaService;
import co.com.example.authentication_security.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@AllArgsConstructor
@Controller
public class AuthController {

/*    private UserRepository userRepository;*/

    private UserService userService;
    private CaptchaService captchaService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @GetMapping("/home")
    public String home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();;
            model.addAttribute("authentication", userDetails);
        }

        return "home"; // el nombre de tu .html o .jsp
    }


    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model,
                               @RequestParam("g-recaptcha-response") String captchaResponse) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        if (!captchaService.verifyCaptcha(captchaResponse)) {
            model.addAttribute("captchaError", "Captcha verification failed");
            return "register";
        }

        userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "login";
    }
}
