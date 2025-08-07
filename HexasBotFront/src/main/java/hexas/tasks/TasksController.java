package hexas.tasks;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

import hexas.db.dao.ProductDao;
import hexas.db.dao.TasksDao;
import hexas.db.dbo.Product;
import hexas.db.dbo.Task;
import hexas.db.dbo.TaskType;

@Controller
@SessionScope
@RequestMapping(path = "/tasks")
public class TasksController {

	@Autowired
	private SqlSession session;

	@GetMapping()
	public ModelAndView getScanTasks() {
		List<Task> tasks = session.getMapper(TasksDao.class).list();
		return new ModelAndView("tasks").addObject("tasks", tasks);
	}

	@GetMapping("/add")
	public ModelAndView addTask() {
		List<Product> products = session.getMapper(ProductDao.class).listProducts();
		return new ModelAndView("tasks-add").addObject("types", TaskType.values()).addObject("products", products);
	}

	@PostMapping("/add")
	public String submitAddDrop(@ModelAttribute Task task) {
		session.getMapper(TasksDao.class).create(task);
		session.commit();
		return "redirect:/tasks";
	}

	@GetMapping("/edit/{id}")
	public ModelAndView editTask(@PathVariable("id") int id) {
		Task task = session.getMapper(TasksDao.class).read(id);
		List<Product> products = session.getMapper(ProductDao.class).listProducts();
		return new ModelAndView("tasks-add")
				.addObject("types", TaskType.values())
				.addObject("products", products)
				.addObject("task", task);
	}

	@PostMapping("/edit/{id}")
	public String submitEditTask(@PathVariable("id") int id, @ModelAttribute() Task task) {
		task.setId(id);
		session.getMapper(TasksDao.class).update(task);
		session.commit();
		return "redirect:/tasks";
	}

	@GetMapping("/delete/{id}")
	public String submitDeleteeTask(@PathVariable("id") int id) {
		session.getMapper(TasksDao.class).delete(id);
		session.commit();
		return "redirect:/tasks";
	}
}
