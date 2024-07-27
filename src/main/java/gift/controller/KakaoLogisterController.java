package gift.controller;

import gift.util.ResponseEntityUtil;
import gift.exception.BadRequestExceptions.BadRequestException;
import gift.service.KakaoLogisterService;
import gift.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/oauth/kakao")
public class KakaoLogisterController {
    private final Logger logger = LoggerFactory.getLogger(KakaoLogisterController.class);

    private final KakaoLogisterService kakaoLogisterService;
    private final JwtUtil jwtUtil;

    @Autowired
    KakaoLogisterController(KakaoLogisterService kakaoLogisterService, JwtUtil jwtUtil) {
        this.kakaoLogisterService = kakaoLogisterService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public String logister(@RequestParam String code, Model model) {
        try {
            String token = jwtUtil.generateToken(kakaoLogisterService.logister(code));
            model.addAttribute("token", token);
            model.addAttribute("success", true);
        } catch (BadRequestException e){
            model.addAttribute("message", e.getMessage());
            model.addAttribute("success", false);
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            model.addAttribute("message", e.getMessage());
            model.addAttribute("success", false);
        }
        return "loginResult";
    }
}
