package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.model.JobCandidate;
import com.example.demo.repository.JobCandidateRepository;

@ExtendWith(MockitoExtension.class)
public class JobCandidateServiceTest {
	
	@Mock
	private JobCandidateRepository candidateRepo;
	
	@InjectMocks
	private JobCandidateService candidateService;
	
	@Test
	public void testUpdateCandidate() {
		Long id = 1L;
		JobCandidate current = new JobCandidate();
		current.setId(id);
		current.setName("Aleksa");
		current.setMail("aleksa@gmail.com");
		current.setPhoneNumber("0602541365");
		
		JobCandidate newCandidate = new JobCandidate();
		newCandidate.setName("Danilo");
		newCandidate.setMail("danilo@gmail.com");
		newCandidate.setPhoneNumber("0652141777");
		
		when(candidateRepo.findById(id)).thenReturn(Optional.of(current));
		
		when(candidateRepo.save(current)).thenReturn(current);
		
		JobCandidate result = candidateService.updateCandidate(id, newCandidate);
		
		assertNotNull(result);
		
		assertEquals("Danilo", result.getName());
		assertEquals("danilo@gmail.com", result.getMail());
		assertEquals("0652141777", result.getPhoneNumber());
		
		verify(candidateRepo).save(current);
		
	}
}
