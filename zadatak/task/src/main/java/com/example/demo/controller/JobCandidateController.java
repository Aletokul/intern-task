package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.JobCandidate;
import com.example.demo.service.JobCandidateService;

import io.swagger.v3.oas.annotations.Operation;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/candidates")
public class JobCandidateController {

	private final JobCandidateService candidateService;

	public JobCandidateController(JobCandidateService candidateService) {
		this.candidateService = candidateService;
	}
	
	@Operation(summary = "Get all candidates")
	@GetMapping
	public List<JobCandidate> getAllCandidates() {
	    return candidateService.findAll();
	}
	
	@Operation(summary = "Add new candidate")
	@PostMapping
	public JobCandidate addCandidate (@RequestBody JobCandidate candidate) {
		return candidateService.addCandidate(candidate);
	}
	
	@Operation(summary = "Update candidate")
	@PutMapping("/{candidateId}")
	public JobCandidate updateCandidateInfo(@PathVariable Long candidateId, @RequestBody JobCandidate candidate) {
		return candidateService.updateCandidate(candidateId, candidate);
	}
	
	@Operation(summary = "Search candidate by name")
	@GetMapping("/search")
	public List<JobCandidate> findByName(@RequestParam String candidate){
		return candidateService.findByName(candidate);
	}
	
	@Operation(summary = "Search candidate by skill")
	@GetMapping("/search-skill")
	public List<JobCandidate> findBySkill(@RequestParam String skill){
		return candidateService.findSkills(skill);
	}
	
	@Operation(summary = "Delete candidate")
	@DeleteMapping("/{candidateId}")
	public void deleteCandidate(@PathVariable Long candidateId) {
		candidateService.deleteCandidate(candidateId);
	}
	
	@Operation(summary = "Add skill to candidate")
	@PostMapping("/{candidateId}/skills-add/{skill}")
	public JobCandidate addSkillToCandidate(@PathVariable Long candidateId, @PathVariable String skill) {
		return candidateService.addSkillCandidate(candidateId, skill);
	}
	
	@Operation(summary = "Remove skill from candidate")
	@PostMapping("/{candidateId}/skills-remove/{skill}")
	public JobCandidate removeSkillFromCandidate(@PathVariable Long candidateId, @PathVariable String skill) {
		return candidateService.removeSkillCandidate(candidateId, skill);
	}
	
	
	
	

	
}
