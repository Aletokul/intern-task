package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.model.Skill;
import com.example.demo.repository.SkillRepository;

@Service
public class SkillService {
	
	private final SkillRepository skillRepo;

	public SkillService(SkillRepository skillRepo) {
		this.skillRepo = skillRepo;
	}
	
	public Skill addSkill(Skill skill) {
		return skillRepo.save(skill);
	}
	
	public Optional<Skill> findByName(String skill){
		return skillRepo.findByName(skill);
	}
	
	public List<Skill> findAllSkills(){
		return skillRepo.findAll();
	}
	
	
	
	
	
}
