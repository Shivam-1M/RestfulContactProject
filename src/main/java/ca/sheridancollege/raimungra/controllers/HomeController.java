package ca.sheridancollege.raimungra.controllers;

import ca.sheridancollege.raimungra.beans.Contact;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController {

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

    @GetMapping("/")
    public String getHome() {
        return "home";
    }

    @GetMapping("secure/addContact")
    public String getAddContact() {
        return "secure/addContact";
    }

    @GetMapping("/login")
    public String getRegister() {
        return "login";
    }

    @GetMapping("/permission_denied")
    public String noPermission(){

        return "error/accessDenied";
    }

}
