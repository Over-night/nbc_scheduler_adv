package com.advanceschedular.dto.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

// EqualsAndHashCode : .equals() .hashcode() 사용가능

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MemberSignUpRequest {
    @NotBlank
    @Size(min = 4, max = 50)
    @EqualsAndHashCode.Include
    @ToString.Include
    private String username;

    @NotBlank
    @Size(max = 50)
    @ToString.Include
    private String nickname;

    @NotBlank
    @Size(min = 6, max = 255)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // 응답 시 숨김
    private String password;

    @NotBlank
    @Email
    @ToString.Include
    private String email;
}
