package by.kovalski.jwtauth;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;

public class Main {
    public static void main(String[] args) {
     String encoded = Encoders.BASE64.encode("sdsa".getBytes());
     System.out.println(encoded);
     byte[] decoded = Decoders.BASE64.decode(encoded);
     System.out.println(String.valueOf(decoded));
    }
}
