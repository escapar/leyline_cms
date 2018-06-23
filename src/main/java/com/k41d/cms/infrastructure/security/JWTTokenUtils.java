package com.k41d.cms.infrastructure.security;

import java.time.ZonedDateTime;

import javax.servlet.http.HttpServletRequest;

import com.k41d.cms.infrastructure.utils.AppUtils;
import com.k41d.leyline.framework.domain.user.LeylineUser;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Setter;

/**
 * Created by bytenoob on 6/18/16.
 */


@ConfigurationProperties(prefix = "jwt")
@Setter
@Component

public class JWTTokenUtils {

    public static String signingKey;

    public static Claims parse(final HttpServletRequest request) {
        final String authHeader = request.getHeader("X-Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return parse(authHeader.substring(7));
    }

    public static Claims parse(final String token) {
        return Jwts.parser().setSigningKey(signingKey)
                .parseClaimsJws(token).getBody();
    }

    public static String sign(final LeylineUser user) {
        return user == null ? null :
                Jwts.builder().setSubject(user.getName())
                        .claim("roles", RoleDTO.fromUser(user))
                        .claim("name", user.getName())
                        .claim("id", user.getId())
                        .setExpiration(AppUtils.fromZonedDateTime(ZonedDateTime.now().plusWeeks(1)))
                        .signWith(SignatureAlgorithm.HS256, signingKey)
                        .compact();
    }

    public static String getSigningKey() {
        return signingKey;
    }

    public static void setSigningKey(final String signingKey) {
        JWTTokenUtils.signingKey = new String(Base64.encode(signingKey.getBytes()));
    }
}
