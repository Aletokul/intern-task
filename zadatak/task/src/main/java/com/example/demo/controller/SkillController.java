package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Skill;
import com.example.demo.service.SkillService;

import io.swagger.v3.oas.annotations.Operation;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/skills")
public class SkillController {

	private final SkillService skillService;

	public SkillController(SkillService skillService) {
		this.skillService = skillService;
	}
	
	@Operation(summary = "Add skill")
	@PostMapping
	public void addSkill(@RequestBody Skill skill) {
		skillService.addSkill(skill);
	}
	
	@Operation(summary = "Get all skills")
	@GetMapping
	public List<Skill> getSkills(){
		return skillService.findAllSkills();
	}
	
	
	
}
