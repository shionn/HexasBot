package hexas.drop;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

import hexas.db.dao.LastProductPriceDao;
import hexas.db.dbo.Product;

@Controller
@SessionScope
public class DropController {

	@Autowired
	private SqlSession session;

	@GetMapping({ "/", "/drops" })
	public ModelAndView getDrops() {
		List<Product> lastDrops = session.getMapper(LastProductPriceDao.class).list();
		return new ModelAndView("drops").addObject("lastDrops", lastDrops);
	}

}
