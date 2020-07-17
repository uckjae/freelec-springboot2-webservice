package com.jojoidu.book.springboot.web;

import com.jojoidu.book.springboot.config.auth.LoginUser;
import com.jojoidu.book.springboot.config.auth.dto.SessionUser;
import com.jojoidu.book.springboot.service.PostsService;
import com.jojoidu.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){
        model.addAttribute("posts",postsService.findAllDesc());

//        SessionUser user = new SessionUser((User) httpSession.getAttribute("user"));

//        User user = null;
//        SessionUser sessionUser = null;
//        if(httpSession.getAttributeNames().hasMoreElements()){
//
//            user = (User)httpSession.getAttribute("user");
//            sessionUser = new SessionUser(user);
//        }
//
//        if(sessionUser !=null){
//            model.addAttribute("userName",sessionUser.getName());
//        }

        if(user != null){
            model.addAttribute("userName",user.getName());
        }

        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post",dto);
        return "posts-update";
    }
}
