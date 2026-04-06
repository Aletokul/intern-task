package com.example.demo.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
public class Skill {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@ManyToMany(mappedBy = "skills")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@JsonIgnore
	private Set <JobCandidate> candidates = new HashSet<>();
	
	
}
