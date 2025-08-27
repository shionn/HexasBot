package hexas.humblebundle;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import hexas.db.dao.HumbleBundleDao;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HumbleBundlePermission {
	private final SqlSession session;

	private final HttpServletRequest request;

	public boolean isValid() {
		String key = request.getHeader("api-key");
		HumbleBundleDao dao = session.getMapper(HumbleBundleDao.class);
		if (key != null && dao.isAuthorized(key))
			return true;
		System.out.println("non authorisé " + key);
		return true;
	}

}
