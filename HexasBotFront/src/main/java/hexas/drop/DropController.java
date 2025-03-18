package hexas.drop;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import hexas.db.dao.ProductDao;
import hexas.db.dbo.Product;

@Controller
public class DropController {

	@Autowired
	private SqlSession session;

	@GetMapping({ "/", "/drops" })
	public ModelAndView getDrops() {
		List<Product> listAllDrops = session.getMapper(ProductDao.class).listAllDrops();
		return new ModelAndView("drops").addObject("drops", listAllDrops);
	}

	@GetMapping({ "/drops/add" })
	public ModelAndView addDrop() {
		return new ModelAndView("drops-add");
	}

	@PostMapping({ "/drops/add" })
	public String addDrop(@RequestParam("marque") String marque, @RequestParam("metaModel") String metaModel,
			@RequestParam("model") String model, @RequestParam("url") String url, @RequestParam("msrp") String msrp,
			@RequestParam("notifyChannel") String notifyChannel, @RequestParam("scanner") String scanner) {
		session
				.getMapper(ProductDao.class)
				.create(Product
						.builder()
						.marque(marque)
						.metaModel(metaModel)
						.model(model)
						.url(url)
						.msrp(msrp)
						.notifyChannel(notifyChannel)
						.scanner(scanner)
						.build());
		session.commit();
		return "redirect:/drops";
	}

	@GetMapping({ "/drops/edit/{id}" })
	public ModelAndView editDrop(@PathVariable("id")int id) {
		Product product = session.getMapper(ProductDao.class).read(id);
		return new ModelAndView("drops-add").addObject("product", product);
	}

}
