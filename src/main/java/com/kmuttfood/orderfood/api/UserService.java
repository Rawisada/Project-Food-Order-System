package com.kmuttfood.orderfood.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.kmuttfood.orderfood.entity.Role;
import com.kmuttfood.orderfood.entity.User;

@Configuration
@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private OrderCustomerRepository orderCustomerRepo;

    public boolean creat(User user) {

        // verify
        // ถ้ามี email ให้
        if (userRepo.existsByEmail(user.getEmail())) {
            return false;
        } else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodedPassword = encoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            Role roleUser = roleRepo.findByName("User");
            user.addRole(roleUser);
            user.setEnabled(true);
            userRepo.save(user);
            return true;
        }

    }

    public List<User> listAllUser() {
        return userRepo.findAll();
    }

    public User getId(Long id) {
        return userRepo.findById(id).get();
    }

    public User getEmail(String email) {
        return userRepo.findUserByEmail(email);
    }

    public List<Role> listRoles() {
        return roleRepo.findAll();
    }

    public void delete(Long id) throws Exception {
        try {
            // User user = userRepo.findById(id).get();
            // orderCustomerRepo.deleteByUser(user);
            userRepo.deleteById(id);
        } catch (Exception e) {

        }

    }

    public void save(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setEnabled(true);
        userRepo.save(user);
    }

    public void ban(User user) {
        user.setEnabled(false);
        userRepo.save(user);
    }

    public void cancelBan(User user) {
        user.setEnabled(true);
        userRepo.save(user);
    }

    public List<User> findListUser(Boolean enabled) {
        return userRepo.findListUser(enabled);
    }
}
