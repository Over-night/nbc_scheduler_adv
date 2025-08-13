package com.advanceschedular.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MemberSignInRequest {
    @NotBlank
    @Size(min = 4, max = 50)
    @EqualsAndHashCode.Include
    @ToString.Include
    private String username;

    @NotBlank
    @Size(min = 6, max = 255)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // 응답 시 숨김
    private String password;
}