package com.example.demo.service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.model.JobCandidate;
import com.example.demo.model.Skill;
import com.example.demo.repository.JobCandidateRepository;

@Service
public class JobCandidateService {

	private final JobCandidateRepository candidateRepo;
	private final SkillService skillService;

	public JobCandidateService(JobCandidateRepository candidate, SkillService skill) {
		this.candidateRepo = candidate;
		this.skillService = skill;
	}
	
	
	public JobCandidate addCandidate(JobCandidate candidate) {
		return candidateRepo.save(candidate);
	}
	
	public List<JobCandidate> findAll(){
		return candidateRepo.findAll();
	}
	
	public List<JobCandidate> findByName(String candidate) {
		return candidateRepo.findByName(candidate);
	}
	
	public void deleteCandidate(long candidate) {
		candidateRepo.deleteById(candidate);
	}
	
	public List<JobCandidate> findSkills(String skill){
		return candidateRepo.findBySkillsName(skill);
	}
	
	@Transactional
	public JobCandidate updateCandidate(Long candidateId, JobCandidate candidateUpdate) {
		JobCandidate candidate = candidateRepo.findById(candidateId)
				.orElseThrow(() -> new RuntimeException("Kandidat sa id: " + candidateId + " ne postoji"));
		candidate.setName(candidateUpdate.getName());
		candidate.setMail(candidateUpdate.getMail());
		candidate.setPhoneNumber(candidateUpdate.getPhoneNumber());
		candidate.setBirth(candidateUpdate.getBirth());
		
		return candidateRepo.save(candidate);
	}
	
	@Transactional
	public JobCandidate addSkillCandidate(Long candidateId, String skillName) {
		JobCandidate jobCandidate = candidateRepo.findById(candidateId)
				.orElseThrow(() -> new RuntimeException("Kandidat sa ID: " + candidateId +"nije pronađen"));
		
		Optional<Skill> skill = skillService.findByName(skillName);
		Skill tmp = null;
		
		if(skill.isEmpty()) {
			Skill newSKill = new Skill();
			newSKill.setName(skillName);
			tmp = skillService.addSkill(newSKill);
		}else {
			tmp = skill.get();
		}
		
		jobCandidate.getSkills().add(tmp);
		
		return candidateRepo.save(jobCandidate);
		
	}
	
	@Transactional
	public JobCandidate removeSkillCandidate(Long candidateId, String skillName) {
		JobCandidate jobCandidate = candidateRepo.findById(candidateId).
				orElseThrow(() -> new RuntimeException("Kandidat sa ID: " + candidateId + " ne postoji"));
	
		Skill skillToRemove = skillService.findByName(skillName).
				orElseThrow(() -> new RuntimeException("Skill: " + skillName + " ne postoji"));
		
		jobCandidate.getSkills().remove(skillToRemove);
		
		return candidateRepo.save(jobCandidate);
	}
	
	
	
}
