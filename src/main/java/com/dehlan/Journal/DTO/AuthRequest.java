package com.dehlan.Journal.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for login requests in JournalApp
 */
@Data                  // generates getters, setters, toString, equals, hashCode
@NoArgsConstructor     // default constructor
@AllArgsConstructor    // constructor with all fields
public class AuthRequest {
    private String username;
    private String password;
}
