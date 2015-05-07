package shionn.hexas.auth;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AuthFilterTest {
    @Mock
    private FilterChain chain;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;

    private AuthFilter filter = new AuthFilter();

    @Before
    public void setUp() throws Exception {
        when(request.getSession()).thenReturn(session);
        filter.init(mock(FilterConfig.class));
    }

    @After
    public void tearDown() throws Exception {
        filter.destroy();
    }

    @Test
    public void testDoFilter() throws Exception {
        when(session.getAttribute("user")).thenReturn("foo");
        filter.doFilter(request, response, chain);
        verify(chain).doFilter(request, response);
    }

    @Test
    public void testDoFilterNotAuthenticated() throws Exception {
        filter.doFilter(request, response, chain);
        verify(chain, never()).doFilter(request, response);
        verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }

}
