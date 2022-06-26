package com.reicraftscodes.springbootemployee.repository;

import com.reicraftscodes.springbootemployee.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICompanyRepository extends JpaRepository<Company, Integer> {
}
