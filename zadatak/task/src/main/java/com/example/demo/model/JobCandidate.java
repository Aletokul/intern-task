package com.example.demo.model;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
public class JobCandidate {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private Date birth;
	private String phoneNumber;
	private String mail;
	
	@ManyToMany
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@JoinTable(
		name = "candidate_skills",
		joinColumns = @JoinColumn(name = "candidate_id"),
		inverseJoinColumns = @JoinColumn(name = "skill_id")
	)
	private Set <Skill> skills = new HashSet<>();
	
	

}
