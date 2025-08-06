package hexas.tasks;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

import hexas.db.dao.ProductDao;
import hexas.db.dao.ProductsDao;
import hexas.db.dbo.Product;
import hexas.db.dbo.TaskType;

@Controller
@SessionScope
public class TasksController {

	@Autowired
	private SqlSession session;

	@GetMapping({ "/tasks" })
	public ModelAndView getScanTasks() {
		List<Product> listAllDrops = session.getMapper(ProductDao.class).listFilteredDrops();
		return new ModelAndView("tasks").addObject("tasks", new ArrayList<>());
	}

//	@GetMapping({ "/drops/all" })
//	public ModelAndView getAllDrops() {
//		List<Product> listAllDrops = session.getMapper(ProductDao.class).listAllDrops();
//		return new ModelAndView("drops").addObject("drops", listAllDrops);
//	}
//
	@GetMapping({ "/tasks/add" })
	public ModelAndView addDrop() {
		List<Product> products = session.getMapper(ProductsDao.class).listProducts();
		return new ModelAndView("tasks-add")
				.addObject("types", TaskType.values())
				.addObject("products", products);
	}
//
//	@PostMapping({ "/drops/add" })
//	public String addDrop(@RequestParam("marque") String marque, @RequestParam("metaModel") String metaModel,
//			@RequestParam("model") String model, @RequestParam("url") String url, @RequestParam("msrp") String msrp,
//			@RequestParam("notifyChannel") String notifyChannel, @RequestParam("scanner") String scanner,
//			@RequestParam("excludePattern") String excludePattern,
//			@RequestParam("includePattern") String includePattern,
//			@RequestParam("notifyPrice") BigDecimal notifyPrice) {
//		session
//				.getMapper(ProductDao.class)
//				.create(Product
//						.builder()
//						.marque(marque)
//						.metaModel(metaModel)
//						.model(model)
//						.url(url)
//						.msrp(msrp)
//						.notifyChannel(notifyChannel)
//						.notifyPrice(notifyPrice)
//						.scanner(scanner)
//						.includePattern(includePattern)
//						.excludePattern(excludePattern)
//						.build());
//		session.commit();
//		return "redirect:/scan-tasks";
//	}
//
//	@GetMapping({ "/drops/edit/{id}" })
//	public ModelAndView editDrop(@PathVariable("id") int id) {
//		Product product = session.getMapper(ProductDao.class).read(id);
//		return new ModelAndView("drops-add").addObject("product", product);
//	}
//
//	@PostMapping({ "/drops/edit/{id}" })
//	public String editDrop(@PathVariable("id") int id, @RequestParam("marque") String marque,
//			@RequestParam("metaModel") String metaModel, @RequestParam("model") String model,
//			@RequestParam("url") String url, @RequestParam("msrp") String msrp,
//			@RequestParam("notifyChannel") String notifyChannel, @RequestParam("scanner") String scanner,
//			@RequestParam(name = "lastPrice", required = false) BigDecimal lastPrice,
//			@RequestParam("excludePattern") String excludePattern,
//			@RequestParam("includePattern") String includePattern,
//			@RequestParam("notifyPrice") BigDecimal notifyPrice) {
//		ProductDao dao = session.getMapper(ProductDao.class);
//		Product product = dao.read(id);
//		product.setMarque(marque);
//		product.setMetaModel(metaModel);
//		product.setModel(model);
//		product.setUrl(url);
//		product.setMsrp(msrp);
//		product.setNotifyChannel(notifyChannel);
//		product.setScanner(scanner);
//		product.setNotifyPrice(notifyPrice);
//		product.setLastPrice(lastPrice);
//		product.setIncludePattern(includePattern);
//		product.setExcludePattern(excludePattern);
//		dao.update(product);
//		session.commit();
//		return "redirect:/scan-tasks" + "#" + id;
//	}

}
