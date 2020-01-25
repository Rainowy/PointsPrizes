//package pl.coderslab.login.service;
//
//import org.junit.Before;
//import org.mockito.Mock;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import pl.coderslab.login.entity.Parent;
//import pl.coderslab.login.repository.ParentRepository;
//import pl.coderslab.login.repository.RoleRepository;
//
//import static org.mockito.MockitoAnnotations.initMocks;
//
//public class ParenServiceTest {
//
//    @Mock
//    private ParentRepository mockParentRepository;
//
//    @Mock
//    private RoleRepository mockRoleRepository;
//
//    @Mock
//    private BCryptPasswordEncoder mockBCryptPasswordEncoder;
//
//    private ParentService parentServiceUnderTest;
//    private Parent parent;
//
//    @Before
//    public void setUp(){
//        initMocks(this);
//
//        parentServiceUnderTest = new ParentService(mockParentRepository,
//                mockRoleRepository,
//                mockBCryptPasswordEncoder);
//
//        parent = Parent.builder()
//                .id
//
//
//
//    }
//}
