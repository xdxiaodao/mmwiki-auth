package com.github.xdxiaodao.wiki.auth.model.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xdxiaodao
 * @email xdxiaodao@gmail.com
 * @copyright (C) http://github.com/xdxiaodao
 * @date 2019-10-10 17:13
 * @desc ldap校验响应
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LdapAuthResponse {

  private String message;
  private Data data;

  @lombok.Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Data {

    @JsonProperty("given_name")
    @JsonInclude(Include.NON_NULL)
    private String givenName; // 不能为空

    @JsonInclude(Include.NON_NULL)
    private String mobile;
    @JsonInclude(Include.NON_NULL)
    private String phone;
    @JsonInclude(Include.NON_NULL)
    private String email; // 不能为空
    @JsonInclude(Include.NON_NULL)
    private String department;
    @JsonInclude(Include.NON_NULL)
    private String position;
    @JsonInclude(Include.NON_NULL)
    private String location;
    @JsonInclude(Include.NON_NULL)
    private String im;
  }
}
