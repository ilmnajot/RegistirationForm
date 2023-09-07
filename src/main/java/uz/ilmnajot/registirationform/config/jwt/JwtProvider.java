package uz.ilmnajot.registirationform.config.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import uz.ilmnajot.registirationform.entity.User;

import java.util.Date;

@Component

public class JwtProvider {
    private static final long expireTime = 24 * 3600 * 1000 * 10L;
    private static final String secretKey = "myEnglishSecretWord";

    public  String generateToken(User user) {
        return Jwts
                .builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expireTime))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts
                    .parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getUsernameFromToken(String token) {

        return Jwts
                .parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}
