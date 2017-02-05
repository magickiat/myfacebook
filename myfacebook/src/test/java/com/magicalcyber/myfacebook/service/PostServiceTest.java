package com.magicalcyber.myfacebook.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.magicalcyber.myfacebook.constant.UserRole;
import com.magicalcyber.myfacebook.model.Post;
import com.magicalcyber.myfacebook.model.User;
import com.magicalcyber.myfacebook.repository.UserRepository;
import com.magicalcyber.myfacebook.web.form.CommentForm;
import com.magicalcyber.myfacebook.web.form.PostForm;
import com.magicalcyber.myfacebook.web.form.RegisterForm;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@Transactional
public class PostServiceTest {

	private static final String EMAIL_1 = "email1@gmail.com";
	private static final String EMAIL_2 = "email2@gmail.com";
	private static final String EMAIL_3 = "email3@gmail.com";

	private static final String NAME_1 = "name1";
	private static final String NAME_2 = "name2";
	private static final String NAME_3 = "name3";

	private static final String DEFAULT_PASSWORD = "12345";

	@Autowired
	private PostService postService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	private User currentUser;

	@Before
	public void setup() {

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

	@Test
	public void testPost_withNotFriend_mustNotSeePost() {
		// current user post
		PostForm form = new PostForm();
		form.setUserId(currentUser.getId());
		form.setContent("Hello");
		postService.save(form);

		List<Post> posts = postService.listPost(currentUser.getId());
		assertNotNull(posts);
		assertEquals(1, posts.size());

		// other user list post
		User otherUser = userRepository.findByEmail(EMAIL_2);
		posts = postService.listPost(otherUser.getId());
		assertNotNull(posts);
	}

	@Test
	public void testPost_withFriend_mustSeePost() {
		// current user post
		PostForm form = new PostForm();
		form.setUserId(currentUser.getId());
		form.setContent("Hello");
		postService.save(form);

		List<Post> posts = postService.listPost(currentUser.getId());
		assertNotNull(posts);
		assertEquals(1, posts.size());

		// add friend
		User otherUser = userRepository.findByEmail(EMAIL_2);
		userService.addFriend(currentUser.getId(), otherUser.getId());

		// other friend must see post
		posts = postService.listPost(otherUser.getId());
		assertNotNull(posts);
		assertEquals(1, posts.size());
	}

	@Test
	public void testComment_withFriend_mustComment() {
		// current user post
		PostForm form = new PostForm();
		form.setUserId(currentUser.getId());
		form.setContent("Hello");
		postService.save(form);

		List<Post> posts = postService.listPost(currentUser.getId());
		assertNotNull(posts);
		assertEquals(1, posts.size());

		// add friend
		User otherUser = userRepository.findByEmail(EMAIL_2);
		userService.addFriend(currentUser.getId(), otherUser.getId());

		// other friend comment
		posts = postService.listPost(otherUser.getId());
		assertNotNull(posts);
		assertEquals(1, posts.size());

		CommentForm c = new CommentForm();
		c.setPostId(posts.get(0).getId());
		c.setUserId(otherUser.getId());
		c.setContent("Hi");
		postService.saveComment(c);

		posts = postService.listPost(otherUser.getId());
		assertNotNull(posts);
		assertEquals(1, posts.size());
		assertNotNull(posts.get(0).getComments());
		assertEquals("Hi", posts.get(0).getComments().get(0).getContent());
	}
}
