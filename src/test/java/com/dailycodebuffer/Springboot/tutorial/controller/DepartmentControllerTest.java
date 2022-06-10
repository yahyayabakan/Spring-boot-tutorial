package com.dailycodebuffer.Springboot.tutorial.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.dailycodebuffer.Springboot.tutorial.entity.Department;
import com.dailycodebuffer.Springboot.tutorial.error.DepartmentNotFoundException;
import com.dailycodebuffer.Springboot.tutorial.service.DepartmentService;

@WebMvcTest(DepartmentController.class)
class DepartmentControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private DepartmentService departmentService;
	
	private Department department;

	@BeforeEach
	void setUp()  {
		department = Department.builder()
				.departmentAddress("University")
				.departmentCode("SE")
				.departmentName("Data Science")
				.departmentId(1L)
				.build();
	}

	@Test
	void testSaveDepartment() throws Exception {
		Department inputDepartment = Department.builder()
				.departmentAddress("University")
				.departmentCode("SE")
				.departmentName("Data Science")
				.build();
		
		Mockito.when(departmentService.saveDepartment(inputDepartment)).thenReturn(department);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/departments")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{       \r\n"
						+ "        \"departmentName\" : \"Data Science\",\r\n"
						+ "        \"departmentAddress\": \"University\",\r\n"
						+ "        \"departmentCode\": \"SE\"\r\n"
						+ "}"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void testFetchDepartmentById() throws Exception {
		Mockito.when(departmentService.fetchDepartmentById(1L)).thenReturn(department);
		
		mockMvc.perform(get("/departments/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.departmentName").
				value(department.getDepartmentName()));
				
		
	}

}
