package kr.co.picTO.controller.oauth;

import kr.co.picTO.entity.oauth.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class BaseAuthController {

    private final HttpSession httpSession;

    @GetMapping("/index")
    public String index(Model model){
        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if(user != null && user.getPicture() != null){
            model.addAttribute("userName", user.getName());
            model.addAttribute("userEmail", user.getEmail());
            model.addAttribute("userPic", user.getPicture());
        }
        else if(user != null) {
            model.addAttribute("userName", user.getName());
            model.addAttribute("userEmail", user.getEmail());
        }
        return "oauth/index";
    }
}
