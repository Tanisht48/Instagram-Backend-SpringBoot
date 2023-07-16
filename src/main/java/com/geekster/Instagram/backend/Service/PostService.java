package com.geekster.Instagram.backend.Service;

import com.geekster.Instagram.backend.Model.Post;
import com.geekster.Instagram.backend.Model.PostType;
import com.geekster.Instagram.backend.Model.User;
import com.geekster.Instagram.backend.Repository.IPostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private IPostRepo postRepo;
    public String createInstaPost(Post post, User user) {
        post.setUser(user);
        post.setCreatedDate(LocalDateTime.now());
        postRepo.save(post);

        return "A New Post Uploaded by "+user.getUserHandle();
    }

    public List<Post> findByPostTypeAndUser(PostType postType, User user) {

        return postRepo.findByPostTypeAndUser(postType,user);
    }
}
