package com.github.xdxiaodao.wiki.auth.model.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xdxiaodao
 * @email xdxiaodao@gmail.com
 * @copyright (C) http://github.com/xdxiaodao
 * @date 2019-10-10 17:12
 * @desc ldap校验请求
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LdapAuthRequest {

  private String username;
  private String password;

  @JsonProperty("ext_data")
  private String extData;
}
