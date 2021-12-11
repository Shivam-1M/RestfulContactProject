package ca.sheridancollege.raimungra.database;

import ca.sheridancollege.raimungra.beans.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class DatabaseAccess {

    @Autowired
    protected NamedParameterJdbcTemplate jdbc;

    public List<Contact> findAll() {
        MapSqlParameterSource namedParameters= new MapSqlParameterSource();
        String query = "SELECT * FROM contact";
        return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Contact>(Contact.class));
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

    public Contact findUserAccount(String email) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String query = "SELECT * FROM sec_user where email=:email";
        parameters.addValue("email", email);
        ArrayList<Contact> users = (ArrayList<Contact>)jdbc.query(query, parameters, new
                BeanPropertyRowMapper<Contact>(Contact.class));
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





}
