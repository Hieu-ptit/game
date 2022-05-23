package com.mc.security.jwt;

import com.management.SecurityMetersService;
import com.mapper.UserMapper;
import com.model.dto.DetailUser;
import com.service.impl.UserInternalServiceImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import tech.jhipster.config.JHipsterProperties;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";

    private static final String INVALID_JWT_TOKEN = "Invalid JWT token.";

    private final Key key;

    private final JwtParser jwtParser;

    private final long tokenValidityInMilliseconds;

    private final long tokenValidityInMillisecondsForRememberMe;

    private final UserMapper userMapper;

    private final UserInternalServiceImpl userService;

    private final SecurityMetersService securityMetersService;

    public TokenProvider(JHipsterProperties jHipsterProperties, UserMapper userMapper, UserInternalServiceImpl userService, SecurityMetersService securityMetersService) {
        this.userMapper = userMapper;
        this.userService = userService;
        byte[] keyBytes;
        String secret = jHipsterProperties.getSecurity().getAuthentication().getJwt().getBase64Secret();
        if (!ObjectUtils.isEmpty(secret)) {
            keyBytes = Decoders.BASE64.decode(secret);
        } else {
            secret = jHipsterProperties.getSecurity().getAuthentication().getJwt().getSecret();
            keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        }
        key = Keys.hmacShaKeyFor(keyBytes);
        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
        this.tokenValidityInMilliseconds = 1000 * jHipsterProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSeconds();
        this.tokenValidityInMillisecondsForRememberMe =
            1000 * jHipsterProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSecondsForRememberMe();

        this.securityMetersService = securityMetersService;
    }

    public String createToken(Authentication authentication, boolean rememberMe, Long timeExpire) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        long now = (new Date()).getTime();
        Date validity;
        if (rememberMe)
            validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe);
        else {
            validity = new Date(now + this.tokenValidityInMilliseconds);
        }
        DetailUser user = userService.getUserByEmail(authentication.getName());
        return Jwts
            .builder()
            .setSubject(authentication.getName())
            .claim(AUTHORITIES_KEY, authorities)
            .setId(user.getUserId())
            .signWith(key, SignatureAlgorithm.HS512)
            .setExpiration(validity)
            .compact();
//        loginSessionManager.saveLoginSession(user.getUserId(), token, timeExpire);
    }


    public Authentication getAuthentication(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();

        Collection<? extends GrantedAuthority> authorities = Arrays
            .stream(claims.get(AUTHORITIES_KEY).toString().split(","))
            .filter(auth -> !auth.trim().isEmpty())
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String authToken) {
        try {
            jwtParser.parseClaimsJws(authToken);

            return true;
        } catch (ExpiredJwtException e) {
            this.securityMetersService.trackTokenExpired();

        } catch (UnsupportedJwtException e) {
            this.securityMetersService.trackTokenUnsupported();

        } catch (MalformedJwtException e) {
            this.securityMetersService.trackTokenMalformed();

        } catch (SignatureException e) {
            this.securityMetersService.trackTokenInvalidSignature();

        } catch (IllegalArgumentException e) { // TODO: should we let it bubble (no catch), to avoid defensive programming and follow the fail-fast principle?
        }

        return false;
    }

    public DetailUser getClaim(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        return userMapper.mapToUser(claims);
    }
}
