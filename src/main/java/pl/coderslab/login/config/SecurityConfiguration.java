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

    //niezbędne do wyłączenia kodowania hasła
    @Bean
    public PasswordEncoder passwordEncoder2() {
        return new PasswordEnconderTest();
    }
    //
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;

    @Value("${spring.queries.parents-query}")
    private String parentsQuery;

    @Value("${spring.queries.roles-email}")
    private String rolesByEmail;

    @Value("${spring.queries.roles-username}")
    private String rolesByUserName;

    @Value("${spring.queries.parent-username}")
    String parentByName;

    @Value("${spring.queries.parent-email}")
    String parentByEmail;

    @Value("${spring.queries.child-username}")
    String childByName;


//            = "select name, password, active from parent where name like 'tom'";

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {

        auth.
                jdbcAuthentication()
                .usersByUsernameQuery(parentByEmail)
                .authoritiesByUsernameQuery(rolesByEmail)
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder2());
        auth.
                jdbcAuthentication()
                .usersByUsernameQuery(parentByName)
                .authoritiesByUsernameQuery(rolesByUserName)
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder2());
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