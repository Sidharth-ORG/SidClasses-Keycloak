package com.learn.SidClasses.Security;

import com.nimbusds.jose.crypto.impl.MACProvider;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CustomConverter implements Converter<Jwt,Collection<GrantedAuthority>>
{

    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        //getting the realm_access which is a claim from the payload
        Map<String,Object> realmaccess= (Map<String,Object>)source.getClaims().get("realm_access");
        List<String> roles=(List<String>) realmaccess.get("roles");
        List<GrantedAuthority> list=roles.stream().map(role->new SimpleGrantedAuthority("ROLE_"+role)).collect(Collectors.toList());
        System.out.println("Extracted roles: " + roles);
        System.out.println("Granted Authorities: " + list);
        return list;
    }
}