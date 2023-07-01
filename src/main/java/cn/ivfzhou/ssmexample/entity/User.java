package cn.ivfzhou.ssmexample.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "user")
@Entity
public class User implements Serializable {

    // POJO -> 映射数据库表的.

    // VO -> 后台响应给浏览器的POJO类.

    // FORM -> 接收表单数据.

    // DTO -> Data Transfer Object

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    @NotBlank(message = "用户名为必填项,岂能不填!!")
    private String username;

    @NotBlank(message = "密码为必填项,岂能不填!!")
    @NotNull
    private String password;

    private String salt;

    @NotBlank(message = "手机号为必填项,岂能不填!!")
    private String phone;

    private Date birthday;

    private String email;

    private Date created;

    @Transient
    private String roleName;

}
