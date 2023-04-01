package edu.kit.scholarizer.security.dtos;

import edu.kit.scholarizer.security.ValidEmail;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * This record holds the user data received from a registration/login call.
 * @author Tim Wolk
 * @version 1.0
 */
public record UserDTO(@NotNull @NonNull @NotEmpty @NotBlank @ValidEmail String mail,
                      @NotNull @NonNull @NotEmpty @NotBlank String password) {
}
