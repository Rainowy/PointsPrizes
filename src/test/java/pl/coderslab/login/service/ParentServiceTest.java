package pl.coderslab.login.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.coderslab.login.entity.Child;
import pl.coderslab.login.entity.Parent;
import pl.coderslab.login.repository.ChildRepository;
import pl.coderslab.login.repository.ParentRepository;
import pl.coderslab.login.repository.RoleRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.MockitoAnnotations.initMocks;

public class ParentServiceTest {

    @Mock
    private ChildRepository mockChildRepository;
    @Mock
    private ParentRepository mockParentRepository;
    @Mock
    private RoleRepository mockRoleRepository;
    @Mock
    private BCryptPasswordEncoder mockBCryptPasswordEncoder;

    private ParentService parentServiceUnderTest;
    private Parent parent;
    private Child child;

    @Before
    public void setUp() {
        initMocks(this);
        parentServiceUnderTest = new ParentService(mockChildRepository, mockParentRepository, mockRoleRepository, mockBCryptPasswordEncoder);

        //wprowadzamy obiekt z formularza
        parent = Parent.builder()
                .id(1)
                .name("Tomasz")
                .lastName("Czarnecki")
                .email("czarny@gmail.com")
                .active(1)
                .children(Arrays.asList(child))
                .build();

        child= Child.builder()
                .id(1)
                .age(10)
                .email("zosia@com")
                .active(1)
                .parent(parent)
                .roles(new HashSet<>(Arrays.asList(mockRoleRepository.findByRole("ADMIN"))))
                .build();




        Mockito.when(mockParentRepository.save(any()))
                .thenReturn(parent);
        Mockito.when(mockParentRepository.findByEmail(anyString())).thenReturn(parent);
    }

    @Test
    public void testFindParentByEmail(){

        //spodziewany wynik
        final String email = "czarny@gmail.com";

        Parent result = parentServiceUnderTest.findParentByEmail(email);

        System.out.println(result);

        // Verify the results
        assertEquals(email, result.getEmail());
    }

    @Test
    public void testSaveParent(){

        //spodziewany wynik
        final String email = "czarny@gmail.com";

        System.out.println("PARENT TO " + parent);

        Parent result = parentServiceUnderTest.saveParent(Parent.builder().build());

        assertEquals(email,result.getEmail());
        assertEquals(parent.getName(),result.getName());
    }

    @Test
    public void testSaveChild(){

        System.out.println("DZIECKO To " + child );


        Parent result = parentServiceUnderTest.saveChild(Child.builder().build());

        System.out.println("PARENT TO " + result);

        System.out.println(result.getChildren().get(0));

        assertEquals(result.getChildren().get(0).getName(),child.getName());
    }
}