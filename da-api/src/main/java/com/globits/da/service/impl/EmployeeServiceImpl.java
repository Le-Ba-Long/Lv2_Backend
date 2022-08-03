package com.globits.da.service.impl;

import com.globits.da.domain.Employee;
import com.globits.da.dto.EmployeeDTO;
import com.globits.da.repository.EmployeeRepository;
import com.globits.da.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<EmployeeDTO> getAllEmployee() {
        return employeeRepository.findAll()
                .stream()
                .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO insertEmployee(EmployeeDTO employeeDTO) {
        employeeRepository.save(modelMapper.map(employeeDTO, Employee.class));
        return employeeDTO;
    }

    @Override
    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO) {
        employeeRepository.save(modelMapper.map(employeeDTO, Employee.class));
        return employeeDTO;
    }

    //  @Override
//    public List<EmployeeDTO> findByAllKeyword(String keyword) {
//        return employeeRepository.findByAllKeyword(keyword)
//                .stream()
//                .map(employee -> {
//                    return modelMapper.map(employee, EmployeeDTO.class);
//                })
//                .collect(Collectors.toList());
//
//    }

    @Override
    public Boolean deleteEmployeeById(@PathVariable("id") Long id) {
        if (employeeRepository.findById(id).isPresent()) {
            employeeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void saveAll(List<EmployeeDTO> employeeDTOList) {
        List<Employee> list = employeeDTOList
                .stream()
                .map(employee -> {
                    return modelMapper.map(employee, Employee.class);
                }).collect(Collectors.toList());
        if (list.isEmpty()) {
            logger.info("List Employee is Empty");
        } else {
            employeeRepository.saveAll(list);
        }
    }

    @Override
    public List<EmployeeDTO> findByAllKeyword(String keyword) {
        return null;
    }

//    @Override
//    public List<EmployeeDTO> sortByName() {
//        return employeeRepository.sortByName()
//                .stream()
//                .map(employee -> {
//                    return modelMapper.map(employee, EmployeeDTO.class);
//                }).collect(Collectors.toList());
//    }

    @Override
    public Optional<EmployeeDTO> findEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(employee -> modelMapper.map(employee, EmployeeDTO.class));

    }

    @Override
    public Page<Employee> getPage(Pageable pageable) {
        return employeeRepository.findAll(pageable);


    }


}