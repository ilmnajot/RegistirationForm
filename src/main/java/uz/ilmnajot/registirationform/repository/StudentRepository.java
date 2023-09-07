package uz.ilmnajot.registirationform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.ilmnajot.registirationform.entity.Student;
import uz.ilmnajot.registirationform.entity.User;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {


    Optional<Student> findUserByEmail(String email);
}
