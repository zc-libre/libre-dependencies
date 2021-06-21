package com.libre.security.provider;

import lombok.Data;

import java.util.List;

/**
 * @author zhao.cheng
 * @date 2021/4/20 17:57
 */
@Data
public class AuthUser {

    private String clientId;

    private Long userId;

    private String username;

    private List<Long> deptId;

    private List<Long> roleId;
}
