package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
