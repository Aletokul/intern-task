package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.JobCandidate;
import java.util.List;


public interface JobCandidateRepository extends JpaRepository<JobCandidate, Long>{
	List<JobCandidate> findByName(String name);
	List<JobCandidate> findBySkillsName(String skills);
}
