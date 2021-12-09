package ca.sheridancollege.raimungra.controllers;

import ca.sheridancollege.raimungra.beans.Contact;
import ca.sheridancollege.raimungra.database.DatabaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    DatabaseAccess da;

    @GetMapping
    public List<Contact> getContactCollection() {

        return da.findAll();
    }

    @GetMapping("/{id}")
    public Contact getIndividualContact(@PathVariable Long id) {

        return da.findById(id).get(0);
    }

    @PostMapping(consumes="application/json")
    public Long postContact(@RequestBody Contact contact) {

        return da.save(contact);
    }

    @PutMapping(consumes="application/json")
    public String putContactCollection(@RequestBody List<Contact> contactList) {
        da.deleteAll();
        da.saveAll(contactList);
        return "Total Records: " + da.count();
    }

    @PutMapping("/{id}")
    public String putContact(@RequestBody Contact contact, @PathVariable Long id) {
        da.deleteByID(id);
        da.save(contact);
        return "Total Records: " + da.count();
    }

    @DeleteMapping
    public void deleteContactCollection() {

        da.deleteAll();
    }

    @DeleteMapping("/{id}")
    public void deleteIndividualContact(@PathVariable Long id) {

        da.deleteByID(id);
    }

}
