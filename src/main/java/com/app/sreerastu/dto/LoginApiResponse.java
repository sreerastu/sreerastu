package com.app.sreerastu.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class LoginApiResponse {
    private String emailAddress;
    private String password;
  //  private Map<List<String>,List<String>> LoginStatus= new HashMap<>();
}
