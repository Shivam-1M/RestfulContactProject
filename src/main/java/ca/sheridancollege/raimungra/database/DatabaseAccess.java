package ca.sheridancollege.raimungra.database;

import ca.sheridancollege.raimungra.beans.Contact;
import ca.sheridancollege.raimungra.beans.User;
import ca.sheridancollege.raimungra.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class DatabaseAccess {

    @Autowired
    protected NamedParameterJdbcTemplate jdbc;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;



    public List<Contact> findAll() {
        MapSqlParameterSource namedParameters= new MapSqlParameterSource();
        String query = "SELECT * FROM contact";
        return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Contact>(Contact.class));
    }

    public User findUserAccount(String email) {

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String query = "SELECT * FROM sec_user where email=:email";
        parameters.addValue("email", email);
        ArrayList<User> users = (ArrayList<User>)jdbc.query(query, parameters, new
                BeanPropertyRowMapper<User>(User.class));
        if (users.size()>0)
            return users.get(0);
        else
            return null;


    }

    public List<String> getRolesById(Long userId) {

        ArrayList<String> roles = new ArrayList<String>();
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String query = "select user_role.userId, sec_role.roleName "
                + "FROM user_role, sec_role "
                + "WHERE user_role.roleId=sec_role.roleId "
                + "AND userId=:userId";
        parameters.addValue("userId", userId);
        List<Map<String, Object>> rows = jdbc.queryForList(query, parameters);
        for (Map<String, Object> row : rows) {
            roles.add((String)row.get("roleName"));
        }
        return roles;
    }

    public void addUser(String email, String password) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String query = "INSERT INTO sec_user"
                + "(email, encryptedPassword, enabled)"
                + " VALUES (:email, :encryptedPassword, 1)";
        parameters.addValue("email", email);
        parameters.addValue("encryptedPassword", passwordEncoder.encode(password));
        jdbc.update(query, parameters);

    }

    public void addRole(Long userId, Long roleId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String query = "INSERT INTO user_role(userId, roleId) VALUES (:userId, :roleId)";
        parameters.addValue("userId", userId);
        parameters.addValue("roleId", roleId);
        jdbc.update(query, parameters);
    }

    public List<Contact> findById(Long id) {
        MapSqlParameterSource namedParameters= new MapSqlParameterSource();
        String query = "SELECT * FROM contact WHERE id = :id";
        namedParameters.addValue("id", id);
        return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Contact>(Contact.class));
    }

    public Long save(Contact contact) {
        MapSqlParameterSource namedParameters= new MapSqlParameterSource();
        KeyHolder generatedKeyHolder= new GeneratedKeyHolder();
        String query = "INSERT INTO contact(name, phoneNumber, address, email, role) VALUES(:name, :phoneNumber, :address, :email, :role)";
        namedParameters.addValue("name", contact.getName());
        namedParameters.addValue("phoneNumber", contact.getPhoneNumber());
        namedParameters.addValue("address", contact.getAddress());
        namedParameters.addValue("email", contact.getEmail());
        namedParameters.addValue("role", contact.getRole());
        jdbc.update(query, namedParameters, generatedKeyHolder);
        return (Long) generatedKeyHolder.getKey();
    }

    public void deleteAll() {
        MapSqlParameterSource namedParameters= new MapSqlParameterSource();
        String query = "DELETE FROM contact";
        jdbc.update(query, namedParameters);
    }

    public void deleteByID(Long id) {
        MapSqlParameterSource namedParameters= new MapSqlParameterSource();
        String query = "DELETE FROM contact where id = :id";
        namedParameters.addValue("id", id);
        jdbc.update(query, namedParameters);
    }

    public Long count() {
        MapSqlParameterSource namedParameters= new MapSqlParameterSource();
        String query = "SELECT count(*) FROM contact";
        return jdbc.queryForObject(query, namedParameters, Long.TYPE);
    }

    public void saveAll(List<Contact> contactList) {
        for (Contact c : contactList) {
            save(c);
        }
    }

    public void updateContact(Contact contact){

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "UPDATE contact SET name= :name, phoneNumber= :phoneNumber, address= :address, email= :email, role= :role WHERE id =:id";
        namedParameters.addValue("id", contact.getId());
        namedParameters.addValue("name", contact.getName());
        namedParameters.addValue("phoneNumber", contact.getPhoneNumber());
        namedParameters.addValue("address", contact.getAddress());
        namedParameters.addValue("email", contact.getEmail());
        namedParameters.addValue("role", contact.getRole());
        int rowsAffected = jdbc.update(query, namedParameters);
        if (rowsAffected>0)
            System.out.println("contact record was successfully updated!");
    }





}
