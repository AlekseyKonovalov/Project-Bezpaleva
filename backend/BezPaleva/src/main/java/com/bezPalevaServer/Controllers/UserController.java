package com.bezPalevaServer.Controllers;

import com.bezPalevaServer.Services.UserService;
import com.bezPalevaServer.db.User;
import com.bezPalevaServer.db.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public User getUser(@RequestParam Map<String, String> params) {

        String vkId = params.get("vkId");

        if (vkId == null) return null;
        else{
            User user = userService.getUserFromDB(vkId);
            if (user == null) return  null;
            else return  user;
        }

    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public User addUser(@RequestParam Map<String, String> params) {

        String firstName = params.get("fName");
        String lastName = params.get("lName");
        String vkId = params.get("vkId");

        if (firstName == null || lastName == null ||vkId == null ) return null;
        else {

            User user = new User(firstName, lastName, vkId);
            return userService.addUserInBD(user);
        }
    }
}
