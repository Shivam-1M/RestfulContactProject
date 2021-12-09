package ca.sheridancollege.raimungra.beans;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor

public class Contact {

    @NonNull
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String phoneNumber;

    private String address;
    private String email;
    private String[] roles = {"Admin", "Member", "Guest"};
    private String role;



}
