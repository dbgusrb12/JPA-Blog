package com.jpa.jpaBlog.jpaBlog.index;

import com.jpa.jpaBlog.core.config.Navigation;
import com.jpa.jpaBlog.core.config.Section;
import com.jpa.jpaBlog.jpaBlog.post.entity.Post;
import com.jpa.jpaBlog.jpaBlog.post.entity.PostStatus;
import com.jpa.jpaBlog.jpaBlog.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.data.domain.ExampleMatcher.matching;

@Controller
@RequiredArgsConstructor
@Navigation(Section.HOME)
public class IndexController {

    private final PostRepository postRepository;

    @GetMapping("/")
    public String home(@RequestParam(required = false) String q, Model model, @PageableDefault(size = 5, sort = "regDate", direction = Sort.Direction.DESC) Pageable pageable){
        Example<Post> post = Example.of(
                Post.builder()
                        .title(q)
                        .status(PostStatus.Y)
                        .build(),
                matching()
                        .withMatcher("title", ExampleMatcher.GenericPropertyMatcher::contains));
        model.addAttribute("posts", postRepository.findAll(post, pageable));
        return "index";
    }
}