package com.jojoidu.book.springboot.web;

import com.jojoidu.book.springboot.config.auth.dto.SessionUser;
import com.jojoidu.book.springboot.domain.user.User;
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

//    책에 써있는 최종코드
//    @GetMapping("/")
//    public String index(Model model, @LoginUser SessionUser user){
//        System.out.println("IndexController index()");
//        model.addAttribute("posts",postsService.findAllDesc());
//
//        if(user != null){
//            model.addAttribute("userName",user.getName());
//        }
//        System.out.println("//IndexController index()");
//        return "index";
//    }

    //    책에 써있는 개선전 코드
//    @GetMapping("/")
//    public String index(Model model){
//        model.addAttribute("posts",postsService.findAllDesc());
//
//        SessionUser user = (SessionUser) httpSession.getAttribute("user");
//
//        if(user != null){
//            model.addAttribute("userName",user.getName());
//        }
//
//        return "index";
//    }

    @GetMapping("/")
    public String index(Model model){
        System.out.println("IndexController index()");
        model.addAttribute("posts",postsService.findAllDesc());

        User user =null;
        SessionUser sessionUser = null;
        if(httpSession.getAttributeNames().hasMoreElements()){
            user = (User)httpSession.getAttribute("user");
            sessionUser = new SessionUser(user);
        }
        if(sessionUser != null){
            model.addAttribute("userName",sessionUser.getName());
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
