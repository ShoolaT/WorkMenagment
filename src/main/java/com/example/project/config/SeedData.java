package com.example.project.config;

//import com.example.blog.entity.Post;
//import com.example.blog.repository.AuthorityRepository;
//import com.example.blog.service.AccountService;
//import com.example.blog.service.PostService;
import com.example.project.model.Authority;
import com.example.project.repository.AuthorityRepository;
import com.example.project.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SeedData implements CommandLineRunner {

    @Autowired
    private AuthorityService authorityService;
    @Autowired
    private AuthorityRepository authorityRepository;

//    @Autowired
//    private AccountService accountService;

//    @Autowired
//    private AuthorityRepository authorityRepository;

    @Override
    public void run(String... args) throws Exception {
        List<Authority> authorities = authorityService.getAllAuthorities();

        if (authorities.size() == 0) {

            Authority director = new Authority();
            director.setName("DIRECTOR");
            authorityRepository.save(director);

            Authority project_manager = new Authority();
            project_manager.setName("PROJECT_MANAGER");
            authorityRepository.save(project_manager);

            Authority employee = new Authority();
            employee.setName("EMPLOYEE");
            authorityRepository.save(employee);

//            Authority admin = new Authority();
//            admin.setName("ROLE_ADMIN");
//            authorityRepository.save(admin);
//
//            Account account1 = new Account();
//            Account account2 = new Account();
//
//            account1.setFirstName("user_first");
//            account1.setLastName("user_last");
//            account1.setEmail("user.user@domain.com");
//            account1.setPassword("password");
//            Set<Authority> authorities1 = new HashSet<>();
//            authorityRepository.findById("ROLE_USER").ifPresent(authorities1::add);
//            account1.setAuthorities(authorities1);
//
//
//            account2.setFirstName("admin_first");
//            account2.setLastName("admin_last");
//            account2.setEmail("admin.admin@domain.com");
//            account2.setPassword("password");
//
//            Set<Authority> authorities2 = new HashSet<>();
//            authorityRepository.findById("ROLE_ADMIN").ifPresent(authorities2::add);
//            authorityRepository.findById("ROLE_USER").ifPresent(authorities2::add);
//            account2.setAuthorities(authorities2);
//
//            accountService.save(account1);
//            accountService.save(account2);
//
//            Post post1 = new Post();
//            post1.setTitle("What is Lorem Ipsum?");
//            post1.setBody("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
//            post1.setAccount(account1);
//
//            Post post2 = new Post();
//            post2.setTitle("Something else Ipsum");
//            post2.setBody("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Magna eget est lorem ipsum dolor sit amet consectetur adipiscing. Tempus quam pellentesque nec nam aliquam sem et tortor. Pellentesque sit amet porttitor eget. Sed augue lacus viverra vitae congue eu consequat. Ultrices vitae auctor eu augue. Mattis rhoncus urna neque viverra. Consectetur lorem donec massa sapien faucibus et molestie ac feugiat. Sociis natoque penatibus et magnis dis parturient montes nascetur. Cursus turpis massa tincidunt dui ut ornare lectus. Odio pellentesque diam volutpat commodo sed egestas egestas fringilla. Id cursus metus aliquam eleifend mi. Nibh nisl condimentum id venenatis a condimentum.");
//            post2.setAccount(account2);
//
//           postService.save(post1, null);
//            postService.save(post2, null);
        }
    }

}