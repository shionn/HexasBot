package hexas.drop;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import hexas.db.dao.ProductDao;
import hexas.db.dbo.Product;

@Controller
public class DropController {

	@Autowired
	private SqlSession session;

	@GetMapping({"/", "/drops"})
	public ModelAndView getDrops() {
		List<Product> listAllDrops = session.getMapper(ProductDao.class).listAllDrops();
		listAllDrops.stream().forEach(d -> System.out.println(d.getMetaModel()));
		return new ModelAndView("drops").addObject("drops", listAllDrops);
	}
}
