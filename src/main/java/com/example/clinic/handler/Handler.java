package com.example.clinic.handler;

import com.example.clinic.text.SimpleMessage;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Handler {

    public static Date parseDate(String date) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy;HH:mm");
        return sf.parse(date);
    }

    public static String hashPassword(String password) throws Exception {
        String hashPassword;
        if (Objects.isNull(password)) return null;
        MessageDigest digest = MessageDigest.getInstance(SimpleMessage.MD5);
        digest.update(password.getBytes(), 0, password.length());
        hashPassword = new BigInteger(1, digest.digest()).toString(16);
        return hashPassword;
    }
}
