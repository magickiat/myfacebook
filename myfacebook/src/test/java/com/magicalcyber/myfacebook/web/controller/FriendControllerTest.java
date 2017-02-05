package com.magicalcyber.myfacebook.web.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.magicalcyber.myfacebook.constant.UserRole;
import com.magicalcyber.myfacebook.dto.FriendDto;
import com.magicalcyber.myfacebook.model.User;
import com.magicalcyber.myfacebook.repository.UserRepository;
import com.magicalcyber.myfacebook.service.UserService;
import com.magicalcyber.myfacebook.web.form.RegisterForm;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@Transactional
public class FriendControllerTest {

	private static final String EMAIL_1 = "email1@gmail.com";
	private static final String EMAIL_2 = "email2@gmail.com";
	private static final String EMAIL_3 = "email3@gmail.com";

	private static final String NAME_1 = "name1";
	private static final String NAME_2 = "name2";
	private static final String NAME_3 = "name3";

	private static final String DEFAULT_PASSWORD = "12345";

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;
	private User currentUser;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

		RegisterForm form1 = new RegisterForm();
		RegisterForm form2 = new RegisterForm();
		RegisterForm form3 = new RegisterForm();

		form1.setEmail(EMAIL_1);
		form2.setEmail(EMAIL_2);
		form3.setEmail(EMAIL_3);

		form1.setName(NAME_1);
		form2.setName(NAME_2);
		form3.setName(NAME_3);

		form1.setPassword(DEFAULT_PASSWORD);
		form2.setPassword(DEFAULT_PASSWORD);
		form3.setPassword(DEFAULT_PASSWORD);

		userService.create(form1, UserRole.ROLE_USER);
		userService.create(form2, UserRole.ROLE_USER);
		userService.create(form3, UserRole.ROLE_USER);

		currentUser = userRepository.findByEmail(EMAIL_1);
	}

	@SuppressWarnings("unchecked")
	@Test
	@WithMockUser(username = EMAIL_1, password = DEFAULT_PASSWORD, roles = "USER")
	public void testSearch_withoutFriend_mustFoundNotFriend() throws Exception {
		// expected friend
		User expectedFriend = userService.findByEmail(EMAIL_2);

		MockHttpServletRequestBuilder req = MockMvcRequestBuilders.get("/friend/search")
				.param("keyword", NAME_2)
				.param("userId", currentUser.getId().toString())
				.contentType(MediaType.APPLICATION_FORM_URLENCODED);

		Map<String, Object> model = mockMvc.perform(req)
				.andExpect(status().isOk())
				.andExpect(view().name("friend"))
				.andExpect(model().attributeExists("users"))
				.andReturn().getModelAndView().getModel();

		// check target friend
		Set<FriendDto> friends = (Set<FriendDto>) model.get("users");
		assertFalse(friends.isEmpty());
		assertTrue(friends.size() == 1);

		FriendDto target = friends.iterator().next();
		assertEquals(expectedFriend.getId(), target.getUserId());
		assertFalse(target.isFriend());
	}

	@SuppressWarnings("unchecked")
	@Test
	@WithMockUser(username = EMAIL_1, password = DEFAULT_PASSWORD, roles = "USER")
	public void testSearch_isFriend_mustFoundAlreadyFriend() throws Exception {
		// expected friend
		User expectedFriend = userService.findByEmail(EMAIL_2);
		userService.addFriend(currentUser.getId(), expectedFriend.getId());
		

		MockHttpServletRequestBuilder req = MockMvcRequestBuilders.get("/friend/search")
				.param("keyword", NAME_2)
				.param("userId", currentUser.getId().toString())
				.contentType(MediaType.APPLICATION_FORM_URLENCODED);

		Map<String, Object> model = mockMvc.perform(req)
				.andExpect(status().isOk())
				.andExpect(view().name("friend"))
				.andExpect(model().attributeExists("users"))
				.andReturn().getModelAndView().getModel();

		// check target friend
		Set<FriendDto> friends = (Set<FriendDto>) model.get("users");
		assertFalse(friends.isEmpty());
		assertTrue(friends.size() == 1);

		FriendDto target = friends.iterator().next();
		assertEquals(expectedFriend.getId(), target.getUserId());
		assertTrue(target.isFriend());
	}
}
