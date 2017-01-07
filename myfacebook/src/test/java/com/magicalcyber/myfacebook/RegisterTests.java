package com.magicalcyber.myfacebook;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@Transactional
public class RegisterTests {

	private static final String PASSWORD = "12345";

	private static final String NAME = "magicalcyber";

	private static final String EMAIL = "mm@gmail.com";


	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void testRegister_emailNotRegistered_mustSuccess() throws Exception {

		MockHttpServletRequestBuilder req = MockMvcRequestBuilders.post("/register")
			.param("name", NAME)
			.param("email", EMAIL)
			.param("password", PASSWORD)
			.param("rePassword", PASSWORD)
			.contentType(MediaType.APPLICATION_FORM_URLENCODED);
		

		mockMvc.perform(req)
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("login"));
				
	}
	
	@Test
	public void testRegister_emailRegistered_mustErrorRegisteredEmail() throws Exception {

		// 1 - create registered email
		testRegister_emailNotRegistered_mustSuccess();
		
		// 2 - use same email to register
		MockHttpServletRequestBuilder req = MockMvcRequestBuilders.post("/register")
			.param("name", NAME)
			.param("email", EMAIL)
			.param("password", PASSWORD)
			.param("rePassword", PASSWORD)
			.contentType(MediaType.APPLICATION_FORM_URLENCODED);
			
		// 3 - validate errors field must contain 'email'
		mockMvc.perform(req)
			.andExpect(status().isOk())
			.andExpect(view().name("index"))
			.andExpect(model().attributeHasFieldErrors("registerForm", "email"));
	}
}
