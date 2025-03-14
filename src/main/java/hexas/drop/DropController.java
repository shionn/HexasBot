package hexas.drop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DropController {
	@Autowired
	private DropDatabase db;

	@GetMapping({"/", "/drops"})
	public ModelAndView getDrops() {
		return new ModelAndView("drops").addObject("drops", db.getDrops());
	}
}
