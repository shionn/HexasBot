package shionn.hexas.services;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mongojack.JacksonDBCollection;

import shionn.hexas.auth.Authentification;
import shionn.hexas.mongo.mo.Channel;

@RunWith(MockitoJUnitRunner.class)
public class AuthServiceTest {
	private static final String MD5_PASSWORD = "5f4dcc3b5aa765d61d8327deb882cf99";
	private static final String PASSWORD = "password";
	private static final String CHANNEL = "#channel";
	private static final String CHANNEL_SESSION_KEY = "channel";
	@Mock
	private JacksonDBCollection<Channel, String> users;
	@Mock
	private HttpServletRequest request;
	@Mock
	private HttpSession session;
	@InjectMocks
	private AuthService service;

	@Before
	public void setUp() throws Exception {
		when(request.getSession()).thenReturn(session);
		when(users.findOneById(CHANNEL)).thenReturn(new Channel(CHANNEL, MD5_PASSWORD));
	}

	@Test
	public void testAuthentify() throws Exception {
		assertThat(
				(boolean) service.authentify(authentification(CHANNEL, PASSWORD), request)
						.getEntity()).isTrue();
		verify(session).removeAttribute(CHANNEL_SESSION_KEY);
		verify(session).setAttribute(CHANNEL_SESSION_KEY, CHANNEL);
	}

	@Test
	public void testAuthentifyWrongPassword() throws Exception {
		assertThat(
				(boolean) service.authentify(authentification(CHANNEL, "foo"), request).getEntity())
				.isFalse();
		verify(session).removeAttribute(CHANNEL_SESSION_KEY);
		verify(session, never()).setAttribute(eq(CHANNEL_SESSION_KEY), anyObject());
	}

	@Test
	public void testAuthentifyWrongUser() throws Exception {
		assertThat(
				(boolean) service.authentify(authentification("bar", PASSWORD), request)
						.getEntity()).isFalse();
		verify(session).removeAttribute(CHANNEL_SESSION_KEY);
		verify(session, never()).setAttribute(eq(CHANNEL_SESSION_KEY), anyObject());
	}

	@Test
	public void testAuthentified() throws Exception {
		assertThat(service.authentified(request)).isFalse();
		when(session.getAttribute(CHANNEL_SESSION_KEY)).thenReturn(CHANNEL);
		assertThat(service.authentified(request)).isTrue();
	}

	// @Test
	// public void testRegister() throws Exception {
	// when(users.insert(any(User.class))).then(new Answer<WriteResult<User,String>>() {
	// @Override
	// public WriteResult<User,String> answer(InvocationOnMock invocation) throws Throwable {
	// @SuppressWarnings("unchecked")
	// WriteResult<User,String> result = mock(WriteResult.class);
	// User user = (User) invocation.getArguments()[0];
	// assertThat(user.getChannel()).isEqualTo(LOGIN);
	// assertThat(user.getPassword()).isEqualTo(MD5_PASSWORD);
	// when(result.getSavedObject()).thenReturn(user);
	// return result;
	// }
	// });
	// User user = service.register(authentification(LOGIN, PASSWORD));
	// assertThat(user.getChannel()).isEqualTo(LOGIN);
	// assertThat(user.getPassword()).isEqualTo(MD5_PASSWORD);
	// verify(users).insert(any(User.class));
	// }

	private Authentification authentification(String login, String pass) {
		Authentification auth = new Authentification();
		auth.setChannel(login);
		auth.setPassword(pass);
		return auth;
	}

}
