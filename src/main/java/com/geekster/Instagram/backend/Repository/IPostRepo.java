package com.geekster.Instagram.backend.Repository;

import com.geekster.Instagram.backend.Model.Post;
import com.geekster.Instagram.backend.Model.PostType;
import com.geekster.Instagram.backend.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPostRepo extends JpaRepository<Post,Integer> {

    List<Post> findByPostTypeAndUser(PostType postType, User user);
}
