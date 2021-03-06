package pl.coderslab.login.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;

    @Value("${spring.queries.parents-query}")
    private String parentsQuery;

    @Value("${spring.queries.roles-email-parent}")
    private String rolesByEmailParent;

    @Value("${spring.queries.roles-username-parent}")
    private String rolesByUserNameParent;

    @Value("${spring.queries.roles-username-child}")
    private String rolesByUserNameChild;

    @Value("${spring.queries.roles-email-child}")
    private String rolesByEmailChild;

    @Value("${spring.queries.parent-username}")
    private String parentByName;

    @Value("${spring.queries.parent-email}")
    private String parentByEmail;

    @Value("${spring.queries.child-username}")
    private String childByName;

    @Value("${spring.queries.child-email}")
    private String childByEmail;


    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {

        auth.
                jdbcAuthentication()
                .usersByUsernameQuery(parentByEmail)
                .authoritiesByUsernameQuery(rolesByEmailParent)
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
//        auth.
//                jdbcAuthentication()
//                .usersByUsernameQuery(parentByName)
//                .authoritiesByUsernameQuery(rolesByUserNameParent)
//                .dataSource(dataSource)
//                .passwordEncoder(bCryptPasswordEncoder);
        auth.
                jdbcAuthentication()
                .usersByUsernameQuery(childByEmail)
                .authoritiesByUsernameQuery(rolesByEmailChild)
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
        auth.
                jdbcAuthentication()
                .usersByUsernameQuery(childByName)
                .authoritiesByUsernameQuery(rolesByUserNameChild)
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return new MySimpleUrlAuthenticationSuccessHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.
                authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/registration").permitAll()
                .antMatchers("/parent/**").hasAuthority("PARENT")
                .antMatchers("/child/**").hasAuthority("CHILD")
                .antMatchers("/admin/**").hasAuthority("ADMIN")

                .anyRequest()
                .authenticated()
                .and().csrf().disable().formLogin()

                .loginPage("/login").failureUrl("/login?error=true")
//                .defaultSuccessUrl("/admin/home")
                .successHandler(myAuthenticationSuccessHandler())
//                .usernameParameter("email")
                .usernameParameter("username")
                .passwordParameter("password")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/").and().exceptionHandling()
                .accessDeniedPage("/access-denied");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }
}