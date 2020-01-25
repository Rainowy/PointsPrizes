//package pl.coderslab.login.config;
//
//import org.springframework.security.crypto.password.PasswordEncoder;
//
////klasa wyłączająca kodowanie hasła
//public class PasswordEnconderTest implements PasswordEncoder {
//
//    @Override
//    public String encode(CharSequence charSequence) {
//        System.out.println(charSequence.toString());
//        return charSequence.toString();
//    }
//
//    @Override
//    public boolean matches(CharSequence charSequence, String s) {
//        System.out.println(charSequence.toString().equals(s));
//        return charSequence.toString().equals(s);
//    }
//}