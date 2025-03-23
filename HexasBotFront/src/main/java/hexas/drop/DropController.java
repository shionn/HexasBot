package hexas.drop;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

import hexas.db.dao.ProductDao;
import hexas.db.dbo.Product;

@Controller
@SessionScope
public class DropController {

	@Autowired
	private SqlSession session;

	private String last = "/drops";

	@GetMapping({ "/", "/drops" })
	public ModelAndView getDrops() {
		List<Product> listAllDrops = session.getMapper(ProductDao.class).listFilteredDrops();
		last = "/drops";
		return new ModelAndView("drops").addObject("drops", listAllDrops);
	}

	@GetMapping({ "/drops/all" })
	public ModelAndView getAllDrops() {
		List<Product> listAllDrops = session.getMapper(ProductDao.class).listAllDrops();
		last = "/drops/all";
		return new ModelAndView("drops").addObject("drops", listAllDrops);
	}

	@GetMapping({ "/drops/add" })
	public ModelAndView addDrop() {
		return new ModelAndView("drops-add");
	}

	@PostMapping({ "/drops/add" })
	public String addDrop(@RequestParam("marque") String marque, @RequestParam("metaModel") String metaModel,
			@RequestParam("model") String model, @RequestParam("url") String url, @RequestParam("msrp") String msrp,
			@RequestParam("notifyChannel") String notifyChannel, @RequestParam("scanner") String scanner,
			@RequestParam("notifyPrice") BigDecimal notifyPrice) {
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
						.notifyPrice(notifyPrice)
						.scanner(scanner)
						.build());
		session.commit();
		return "redirect:" + last;
	}

	@GetMapping({ "/drops/edit/{id}" })
	public ModelAndView editDrop(@PathVariable("id") int id) {
		Product product = session.getMapper(ProductDao.class).read(id);
		return new ModelAndView("drops-add").addObject("product", product);
	}

	@PostMapping({ "/drops/edit/{id}" })
	public String editDrop(@PathVariable("id") int id, @RequestParam("marque") String marque,
			@RequestParam("metaModel") String metaModel, @RequestParam("model") String model,
			@RequestParam("url") String url, @RequestParam("msrp") String msrp,
			@RequestParam("notifyChannel") String notifyChannel, @RequestParam("scanner") String scanner,
			@RequestParam(name = "lastPrice", required = false) BigDecimal lastPrice,
			@RequestParam("notifyPrice") BigDecimal notifyPrice) {
		ProductDao dao = session.getMapper(ProductDao.class);
		Product product = dao.read(id);
		product.setMarque(marque);
		product.setMetaModel(metaModel);
		product.setModel(model);
		product.setUrl(url);
		product.setMsrp(msrp);
		product.setNotifyChannel(notifyChannel);
		product.setScanner(scanner);
		product.setNotifyPrice(notifyPrice);
		product.setLastPrice(lastPrice);
		dao.update(product);
		session.commit();
		return "redirect:" + last + "#" + id;
	}

}
