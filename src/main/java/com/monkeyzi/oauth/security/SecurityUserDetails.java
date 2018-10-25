package com.monkeyzi.oauth.security;

import com.monkeyzi.oauth.entity.domain.Permission;
import com.monkeyzi.oauth.entity.domain.Role;
import com.monkeyzi.oauth.entity.domain.User;
import com.monkeyzi.oauth.enums.SysEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SecurityUserDetails extends User implements UserDetails {
    private static final long serialVersionUID = 1L;

    public SecurityUserDetails(User user) {
        if(user!=null) {
            this.setUsername(user.getUsername());
            this.setPassword(user.getPassword());
            this.setStatus(user.getStatus());
            this.setRoles(user.getRoles());
            this.setPermissions(user.getPermissions());
        }
    }

    /**
     * 添加 角色和权限信息
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        List<Permission> permissions=this.getPermissions();
        //添加权限
        permissions.forEach(a->{
            if (a.getType().equals(SysEnum.PERMISSION_CODE.code)&& StringUtils.isNotBlank(a.getPath())){
                authorityList.add(new SimpleGrantedAuthority(a.getPath()));
            }
        });
        //添加角色
        List<Role> roles=this.getRoles();
        roles.forEach(a->{
            authorityList.add(new SimpleGrantedAuthority(a.getRoleName()));
        });

        return authorityList;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
