package utils;

import org.apache.commons.codec.binary.Base64;

public class AuthenticationHender {
    public static String encodeCredStr(String email, String token){
        if(email==null|| token==null){
            throw new IllegalArgumentException("[ERR] Email or token can not be null");
        }
        String cred = email.concat(":").concat(token);
        byte[] encodedCred = Base64.encodeBase64(cred.getBytes());
        return new String(encodedCred);
    }
}
