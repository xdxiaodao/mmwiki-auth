package com.github.xdxiaodao.wiki.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LdapAuthApplication {

  public static void main(String[] args) {
    SpringApplication.run(LdapAuthApplication.class,
                          args);
  }

}
