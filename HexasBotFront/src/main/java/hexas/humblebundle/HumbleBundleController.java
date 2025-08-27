package hexas.humblebundle;

import org.apache.ibatis.session.SqlSession;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import hexas.db.dao.HumbleBundleDao;
import hexas.db.dbo.Bundle;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/humble-bundle")
public class HumbleBundleController {

	private final SqlSession session;

	@GetMapping(path = "/json", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	Bundle getJson(@RequestHeader(name = "api-key", required = false, defaultValue = "none") String key) {
		System.out.println(key.substring(0, 4));
		HumbleBundleDao dao = session.getMapper(HumbleBundleDao.class);
		if (dao.isAuthorized(key)) {
			return dao.retreiveLast();
		}
		return null;
	}

}
