package ca.sheridancollege.raimungra.controllers;

import ca.sheridancollege.raimungra.beans.Contact;
import ca.sheridancollege.raimungra.database.DatabaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    @Lazy
    private DatabaseAccess da;

    @GetMapping("secure/listContacts")
    public String viewContacts(Model model, RestTemplate restTemplate) {
        ResponseEntity<Contact[]> responseEntity = restTemplate.getForEntity
                ("http://localhost:8080/contacts", Contact[].class);
        model.addAttribute("contactList", responseEntity.getBody());
        return "secure/listContacts";
    }

    @GetMapping(value="/getContact/{id}", produces="application/json")
    @ResponseBody
    public Map<String, Object> getContact(@PathVariable int id, RestTemplate restTemplate) {
        Map<String, Object> data = new HashMap<String, Object>();
        ResponseEntity<Contact> responseEntity = restTemplate.getForEntity
                ("http://localhost:8080/contacts" + id, Contact.class);
        data.put("contact", responseEntity.getBody());
        return data;
    }

    @PostMapping("/registerUser")
    public String postRegister(@RequestParam String username, @RequestParam String[] role,@RequestParam String password) {
        da.addUser(username, password);

        Long userId= da.findUserAccount(username).getUserId();
        for(String r:role) {
            da.addRole(userId, Long.parseLong(r));
        }
        return "login";
    }

    @GetMapping("/register")
    public String getRegister() {
        return "register";
    }


    @GetMapping("/")
    public String getHome() {
        return "home";
    }

    @PostMapping("/addContact")
    public String addContact(Model model, @RequestParam String name, @RequestParam String phoneNumber, @RequestParam String address, @RequestParam String email, @RequestParam String role) {
        da.save(new Contact(name, phoneNumber, address, email, role));
        return "redirect:/secure/listContacts";
    }

    @GetMapping("/secure/addContact")
    public String getAddContact() {
        return "secure/addContact";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @GetMapping("/permission_denied")
    public String noPermission(){

        return "error/accessDenied";
    }

    @PostMapping("/insertContact")
    public String insertContact(Model model, @RequestParam String name, @RequestParam String phoneNumber, @RequestParam String address, @RequestParam String email, @RequestParam String role) {
        da.save(new Contact(name, phoneNumber, address, email, role));
        return "redirect:/secure/listContacts";
    }

/*
    @GetMapping("/secure")
    public String secureIndex(Authentication authentication, Model model){
        String email = authentication.getName();
        List<String> roleList= new ArrayList<String>();
        for (GrantedAuthority ga: authentication.getAuthorities()) {
            roleList.add(ga.getAuthority());
        }
        model.addAttribute("email", email);
        model.addAttribute("roleList", roleList);

        return "secure/**";
    }
*/

}
