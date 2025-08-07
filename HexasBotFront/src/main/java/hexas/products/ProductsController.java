package hexas.products;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import hexas.db.dao.ProductDao;
import hexas.db.dbo.Product;
import lombok.RequiredArgsConstructor;

@Controller()
@RequiredArgsConstructor
@RequestMapping(path = "/products")
public class ProductsController {

	private final SqlSession session;

	@GetMapping()
	public ModelAndView viewProducts() {
		ProductDao dao = session.getMapper(ProductDao.class);
		return new ModelAndView("products").addObject("products", dao.listProducts());
	}

	@GetMapping({ "/add" })
	public ModelAndView addProduct() {
		return new ModelAndView("products-add");
	}

	@PostMapping({ "/add" })
	public String submitAddProduct(@ModelAttribute Product product) {
		ProductDao dao = session.getMapper(ProductDao.class);
		dao.create(product);
		session.commit();
		return "redirect:/products";
	}

	@GetMapping({ "/edit/{id}" })
	public ModelAndView editProduct(@PathVariable("id") int id) {
		Product product = session.getMapper(ProductDao.class).read(id);
		return new ModelAndView("products-add").addObject("product", product);
	}

	@PostMapping({ "/edit/{id}" })
	public String submitEditProduct(@PathVariable("id") int id, @ModelAttribute Product product) {
		product.setId(id);
		session.getMapper(ProductDao.class).update(product);
		session.commit();
		return "redirect:/products#" + id;
	}

	@GetMapping({ "/delete/{id}" })
	public String removeProduct(@PathVariable("id") int id) {
		session.getMapper(ProductDao.class).remove(id);
		session.commit();
		return "redirect:/products";
	}

}
