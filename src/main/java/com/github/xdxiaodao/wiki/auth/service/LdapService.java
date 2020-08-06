package com.github.xdxiaodao.wiki.auth.service;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import com.github.xdxiaodao.wiki.auth.model.Person;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author xdxiaodao
 * @email xdxiaodao@gmail.com
 * @copyright (C) http://github.com/xdxiaodao
 * @date 2019-10-10 17:03
 * @desc
 */
@Service
public class LdapService {
  @Autowired
  private LdapTemplate ldapTemplate;

  @Value("${spring.ldap.domainName}")
  private String ldapDomainName;

  public Person findByUsername(String username, String password) throws NamingException {
    //这里注意用户名加域名后缀  userDn格式：abcd@rikylee.com
    String userDn = username + ldapDomainName;
    //使用用户名、密码验证域用户
    DirContext ctx = ldapTemplate.getContextSource().getContext(userDn, password);
    //如果验证成功根据sAMAccountName属性查询用户名和用户所属的组
    Person person = ldapTemplate.search(query().where("objectclass").is("person").and("sAMAccountName").is(username), new AttributesMapper<Person>() {
      @Override
      public Person mapFromAttributes(Attributes attributes) throws NamingException {
        Person person = new Person();
        person.setName(attributes.get("cn").get().toString());
        person.setSAMAccountName(attributes.get("sAMAccountName").get().toString());
        person.setDepartment(attributes.get("department").get().toString());
        person.setEmail(attributes.get("mail").get().toString());
        person.setDisplayName(attributes.get("displayName").get().toString());

        String memberOf = attributes.get("memberOf").toString().replace("memberOf: ", "");
        List<String> list = new ArrayList<>();
        String[] roles = memberOf.split(",");
        for (String role : roles) {
          if (StringUtils.startsWithIgnoreCase(role.trim(), "CN=")) {
            list.add(role.trim().replace("CN=", ""));
          }
        }
        person.setRole(list);

        return person;
      }
    }).get(0);
    //关闭ldap连接
    LdapUtils.closeContext(ctx);

    return person;
  }
}
