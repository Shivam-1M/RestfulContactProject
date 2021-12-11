package ca.sheridancollege.raimungra.beans;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class User {

    @NonNull
    private Long userId;

    @NonNull
    private String email;

    @NonNull
    private String EncryptedPassword;

    @NonNull
    private Boolean enabled;

    private String[] roleNames = {"Admin", "Member"};
    private String roleName;




}
