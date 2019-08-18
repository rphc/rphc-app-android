package com.rphc.rphc_app_android.auxiliary;


import android.util.Base64;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.nio.charset.StandardCharsets;


public class JsonWebToken {

    private String base64Format;

    private String type;
    private String algorithm;
    private String token_type;
    private int expiration;
    private String jti;
    private int userId;

    public String base64() {
        return base64Format;
    }
    public String getType() {
        return type;
    }
    public String getAlgorithm() {
        return algorithm;
    }
    public String getToken_type() {
        return token_type;
    }
    public int getExpiration() {
        return expiration;
    }
    public String getJti() {
        return jti;
    }
    public int getUserId() {
        return userId;
    }


    public boolean isExpired() {
        return expiration < System.currentTimeMillis() / 1000;
    }


    public static JsonWebToken fromString(String jwt) {
        JsonWebToken jsonWebToken = new JsonWebToken();

        jsonWebToken.base64Format = jwt;

        String[] split = jwt.split("\\.");

        JsonObject header = new JsonParser().parse(getJson(split[0])).getAsJsonObject();
        jsonWebToken.type = header.get("typ").getAsString();
        jsonWebToken.algorithm = header.get("alg").getAsString();

        JsonObject body = new JsonParser().parse(getJson(split[1])).getAsJsonObject();
        jsonWebToken.token_type = body.get("token_type").getAsString();
        jsonWebToken.expiration = body.get("exp").getAsInt();
        jsonWebToken.jti = body.get("jti").getAsString();
        jsonWebToken.userId = body.get("user_id").getAsInt();

        return jsonWebToken;
    }

    private static String getJson(String strEncoded) {
        byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }


    @Override
    public String toString() {
        return "\nJson Web Token: (" + this.base64Format + ")\n" +
                "Header:\n" +
                "\t Type: " + this.type + "\n" +
                "\t Algorithm: " + this.algorithm + "\n" +
                "Body:\n" +
                "\t Token Type: " + this.token_type + "\n" +
                "\t Expiration: " + this.expiration + "\n" +
                "\t JTI: " + this.jti + "\n" +
                "\t User ID: " + this.userId + "\n" +
                "Information:\n" +
                "\t Expired: " + isExpired();
    }
}
