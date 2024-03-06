package warehouse.management.app.web.rest.vm;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.UUID;

public class GetUserID {

    public UUID getIDFromToken(String jwt) {
        Claims claims = Jwts
            .parser()
            .setSigningKey(
                "NmI4ZTVkNTkxYjE3ZmI1MzJhODMyMzFiYTY4NGQ4NDY3NWVhNmJh" +
                "NTQzM2JhZGEyYzkxYWRmN2Q4ZjFjNTU5MWMyNjVlY2IxNDk5NjNmYjRjZjdmZjNhNjc1NjFjYmYzMjYyMDkxN2FlNzBjZ" +
                "WI0ZTAzY2I1ZDg3YWFiMmJmMzg"
            )
            .parseClaimsJws(jwt)
            .getBody();
        String accountId = claims.get("sub", String.class);
        return UUID.fromString(accountId);
    }
}
