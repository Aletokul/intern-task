package com.example.demo.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.model.JobCandidate;
import com.example.demo.service.JobCandidateService;

@ExtendWith(MockitoExtension.class)
public class JobCandidateControllerTest {
	
	private MockMvc mock;
	
	@Mock
	private JobCandidateService candidateService;
	
	@InjectMocks
	private JobCandidateController candidateController;
	
	@BeforeEach
	public void setup() {
		this.mock = MockMvcBuilders.standaloneSetup(candidateController).build();
	}
	
	@Test
	public void testFindByName() throws Exception {
		JobCandidate candidate = new JobCandidate();
		candidate.setName("Aleksa");
		
		when(candidateService.findByName("Aleksa")).thenReturn(List.of(candidate));
		
		mock.perform(get("/api/candidates/search?candidate=Aleksa")).andExpect(status().isOk())
		.andExpect(jsonPath("$[0].name").value("Aleksa"));
	}
	
	@Test
	public void testAddCandidate() throws Exception{
		JobCandidate candidate = new JobCandidate();
		candidate.setName("Danilo");
		
		when(candidateService.addCandidate(candidate)).thenReturn(candidate);
		
		mock.perform(post("/api/candidates").contentType(MediaType.APPLICATION_JSON).content("{\"name\": \"Danilo\"}"))
		.andExpect(status().isOk()).andExpect(jsonPath("$.name").value("Danilo"));
	}
}
