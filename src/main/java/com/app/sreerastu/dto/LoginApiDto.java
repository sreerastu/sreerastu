package com.app.sreerastu.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginApiDto {
    private String emailAddress;
    private String password;
  //  private Map<List<String>,List<String>> LoginStatus= new HashMap<>();
}
