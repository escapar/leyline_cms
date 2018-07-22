package org.escapar.cms.infrastructure.security;

import java.time.ZonedDateTime;

import javax.servlet.http.HttpServletRequest;

import org.escapar.cms.infrastructure.utils.AppUtils;
import org.escapar.leyline.framework.domain.user.LeylineUser;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Setter;

/**
 * Created by bytenoob on 6/18/16.
 */

@Configuration
@PropertySource("classpath:jwt.properties")
@ConfigurationProperties(prefix = "jwt")
@Setter

public class JWTTokenUtils {

    public String signingKey;

    public Claims parse( HttpServletRequest request) {
         String authHeader = request.getHeader("X-Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return parse(authHeader.substring(7));
    }

    public Claims parse( String token) {
        if(token == null){
            return null;
        }
        return Jwts.parser().setSigningKey(signingKey)
                .parseClaimsJws(token).getBody();
    }

    public String sign( LeylineUser user) {
        return user == null ? null :
                Jwts.builder().setSubject(user.getName())
                        .claim("roles", RoleDTO.fromUser(user))
                        .claim("name", user.getName())
                        .claim("id", user.getId())
                        .setExpiration(AppUtils.fromZonedDateTime(ZonedDateTime.now().plusWeeks(1)))
                        .signWith(SignatureAlgorithm.HS256, this.signingKey)
                        .compact();
    }

    public String getSigningKey() {
        return signingKey;
    }

    public void setSigningKey( String signingKey) {
        this.signingKey = java.util.Base64.getEncoder().encodeToString(signingKey.getBytes());
    }
}
