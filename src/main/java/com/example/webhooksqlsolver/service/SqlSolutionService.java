package com.example.webhooksqlsolver.service;

import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Slf4j
public class SqlSolutionService {
    
    private static final Logger logger = LoggerFactory.getLogger(SqlSolutionService.class);
    
    public String getSolutionQuery(String regNo) {
        logger.info("Generating SQL solution for registration number: {}", regNo);
        
        // Solution for finding younger employees count in each department
        return "SELECT " +
               "    e1.EMP_ID, " +
               "    e1.FIRST_NAME, " +
               "    e1.LAST_NAME, " +
               "    d.DEPARTMENT_NAME, " +
               "    COUNT(e2.EMP_ID) as YOUNGER_EMPLOYEES_COUNT " +
               "FROM " +
               "    EMPLOYEE e1 " +
               "    JOIN DEPARTMENT d ON e1.DEPARTMENT = d.DEPARTMENT_ID " +
               "    LEFT JOIN EMPLOYEE e2 ON e1.DEPARTMENT = e2.DEPARTMENT " +
               "        AND e1.DOB > e2.DOB " +  // e2 is younger (born later) than e1
               "GROUP BY " +
               "    e1.EMP_ID, " +
               "    e1.FIRST_NAME, " +
               "    e1.LAST_NAME, " +
               "    d.DEPARTMENT_NAME " +
               "ORDER BY " +
               "    e1.EMP_ID DESC";
    }
} 