/*
 * SPDX-FileCopyrightText: Copyright © 2017 WebGoat authors
 * SPDX-License-Identifier: GPL-2.0-or-later
 */
package org.owasp.webgoat.container.users;

import java.util.Objects;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@AllArgsConstructor
public class UserValidator implements Validator {

  private final UserRepository userRepository;

  @Override
  public boolean supports(Class<?> clazz) {
    return UserForm.class.equals(clazz);
  }

  @Override
  public void validate(Object o, Errors errors) {
    UserForm userForm = (UserForm) o;
    String username = userForm.getUsername();
    String password = userForm.getPassword();

    if (username == null || username.trim().isEmpty()) {
      errors.rejectValue("username", "username.blank", "This field is required.");
    } else if (userRepository.findByUsername(username) != null) {
      errors.rejectValue("username", "username.duplicate");
    }

    if (Objects.equals(password, username)) {
      errors.rejectValue(
          "password", "password.username", "Password must not be the same as username.");
    }

    if (!Objects.equals(userForm.getMatchingPassword(), password)) {
      errors.rejectValue("matchingPassword", "password.diff");
    }
  }
}
