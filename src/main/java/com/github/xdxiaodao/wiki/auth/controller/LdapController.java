package com.github.xdxiaodao.wiki.auth.controller;

import com.github.xdxiaodao.wiki.auth.model.Person;
import com.github.xdxiaodao.wiki.auth.model.auth.LdapAuthRequest;
import com.github.xdxiaodao.wiki.auth.model.auth.LdapAuthResponse;
import com.github.xdxiaodao.wiki.auth.model.auth.LdapAuthResponse.Data;
import com.github.xdxiaodao.wiki.auth.service.LdapService;
import io.micrometer.core.instrument.util.StringUtils;
import java.util.Map;
import javax.naming.NamingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xdxiaodao
 * @email xdxiaodao@gmail.com
 * @copyright (C) http://github.com/xdxiaodao
 * @date 2019-10-10 17:06
 * @desc
 */
@RestController
@Slf4j
public class LdapController {

  @Autowired
  private LdapService ldapService;

  private static final Data EMPTY_DATA = new Data();

  @PostMapping(value = "/mmwiki/auth")
  public LdapAuthResponse auth(@RequestParam Map<String, String> ldapAuthRequestMap) throws NamingException {

    try {
      LdapAuthRequest ldapAuthRequest = LdapAuthRequest.builder()
          .username(ldapAuthRequestMap.getOrDefault("username", ""))
          .password(ldapAuthRequestMap.getOrDefault("password", ""))
          .extData(ldapAuthRequestMap.getOrDefault("ext_data", "")).build();
      if (null == ldapAuthRequest
          || StringUtils.isBlank(ldapAuthRequest.getUsername())
          || StringUtils.isBlank(ldapAuthRequest.getPassword())) {
        log.warn("[auth] user or password is null");
        return LdapAuthResponse.builder().message("用户名或者密码不能为空").data(EMPTY_DATA).build();
      }

      if (!"mmwiki".equalsIgnoreCase(ldapAuthRequest.getExtData())) {
        log.warn("[auth] ext data is empty, user:{}",
                 ldapAuthRequestMap.getOrDefault("username", ""));
        return LdapAuthResponse.builder().message("非法请求").data(EMPTY_DATA).build();
      }

      Person person = ldapService.findByUsername(ldapAuthRequest.getUsername(), ldapAuthRequest.getPassword());
      if (null == person) {
        log.warn("[auth] person is null, user:{}",
                 ldapAuthRequestMap.getOrDefault("username", ""));
        return LdapAuthResponse.builder().message("用户不存在或者认证失败").data(EMPTY_DATA).build();
      }

      log.info("[auth] user:{}, auth success!", ldapAuthRequestMap.getOrDefault("username", ""));
      return LdapAuthResponse.builder().message("")
          .data(Data.builder().givenName(person.getName())
                    .email(person.getEmail())
                    .department(person.getDepartment())
                    .im("")
                    .location("")
                    .mobile("")
                    .phone("")
                    .position("").build()).build();

    } catch (AuthenticationException ae) {
      log.warn("[auth] auth failed,user:{}, {}",
               ldapAuthRequestMap.getOrDefault("username", ""), ae.getMessage());
      return LdapAuthResponse.builder().message("认证失败").data(EMPTY_DATA).build();
    } catch (Exception e) {
      log.warn("[auth] auth has some exception, user:{}",
               ldapAuthRequestMap.getOrDefault("username", ""), e);
      return LdapAuthResponse.builder().message("系统异常").data(EMPTY_DATA).build();
    }
  }

  @GetMapping("/ping")
  public String ping() {
    return "pong";
  }
}
