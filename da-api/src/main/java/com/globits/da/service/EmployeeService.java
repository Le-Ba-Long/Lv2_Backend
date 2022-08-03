package com.globits.da.service;

import com.globits.da.domain.Employee;
import com.globits.da.dto.EmployeeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface EmployeeService {
    public List<EmployeeDTO> getAllEmployee();

    public EmployeeDTO insertEmployee(EmployeeDTO employeeDTO);

    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO);

    public Boolean deleteEmployeeById(Long id);

    public void saveAll(List<EmployeeDTO> employeeDTOList);

    public List<EmployeeDTO> findByAllKeyword(String keyword);

    public Optional<EmployeeDTO> findEmployeeById(Long id);

    Page<Employee> getPage(Pageable pageable);
}
