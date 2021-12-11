package ca.sheridancollege.raimungra.beans;

import lombok.*;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor

public class Contact {

    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String phoneNumber;

    @NonNull
    private String address;

    @NonNull
    private String email;


    @NonNull
    private String role;

}
