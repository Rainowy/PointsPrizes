package pl.coderslab.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.login.entity.Role;

//@Repository("roleRepository")
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRole(String role);
}
