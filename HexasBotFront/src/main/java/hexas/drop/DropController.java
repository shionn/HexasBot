package hexas.drop;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
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
		List<Product> lasts = session.getMapper(LastProductPriceDao.class).list();
		List<Pair<String, List<Product>>> drops = new ArrayList<>();
		drops.add(Pair.of("Lego", lasts.stream().filter(p -> p.getMarque().equals("Lego")).toList()));
		drops.add(Pair.of("RX 9070", lasts.stream().filter(p -> p.getMetaModel().equals("RX 9070")).toList()));
		drops.add(Pair.of("RX 9070 XT", lasts.stream().filter(p -> p.getMetaModel().equals("RX 9070 XT")).toList()));

		lasts = session
				.getMapper(LastProductPriceDao.class)
				.listByProduct()
				.stream()
				.filter(p -> !p.getPrices().isEmpty())
				.toList();
		List<Pair<String, List<Product>>> gpus = new ArrayList<>();

		gpus.add(Pair.of("RX 9070", lasts.stream().filter(p -> p.getMetaModel().equals("RX 9070")).toList()));
		gpus.add(Pair.of("RX 9070 XT", lasts.stream().filter(p -> p.getMetaModel().equals("RX 9070 XT")).toList()));

		return new ModelAndView("drops").addObject("drops", drops).addObject("gpus", gpus);
	}

}
