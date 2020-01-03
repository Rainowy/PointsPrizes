package pl.coderslab.login.config;

import org.springframework.security.crypto.password.PasswordEncoder;

//klasa wyłączająca kodowanie hasła
public class PasswordEnconderTest implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return charSequence.toString().equals(s);
    }
}