package com.github.xdxiaodao.wiki.auth.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xdxiaodao
 * @email xdxiaodao@gmail.com
 * @copyright (C) http://github.com/xdxiaodao
 * @date 2019-10-10 17:02
 * @desc
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {
  //用户名
  private String name;
  //登录名
  private String sAMAccountName;
  private String displayName;
  private String email;
  private String department;

  //所属组列表
  private List<String> role;


  public List<String> getRole() {
    return role;
  }

  public void setRole(List<String> role) {
    this.role = role;
  }

  @Override
  public String toString() {

    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append("name:"+name+",");
    stringBuffer.append("sAMAccountName:"+sAMAccountName);
    if(role!=null && role.size()>0){
      stringBuffer.append(",role:"+String.join(",",role));
    }

    return stringBuffer.toString();
  }
}
