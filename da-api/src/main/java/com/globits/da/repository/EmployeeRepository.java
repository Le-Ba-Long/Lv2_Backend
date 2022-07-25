package com.globits.da.repository;

import com.globits.da.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    //    @Query(value = "SELECT e FROM Employee e WHERE e.code LIKE %" + emp"%"
//            + " OR e.name LIKE %:keyword% "
//            + " OR e.phone LIKE %:keyword% "
//            + " OR e.email LIKE %:keyword% "
//            + " OR e.age LIKE %:keyword% ")
//    public List<Employee> findByAllKeyword(Employee employee);
//
//    @Query("SELECT e  FROM Employee e")
//    Page<EmployeeDTO> getPage(Pageable pageable);
}
