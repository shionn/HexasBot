package hexas;

import org.jsoup.Jsoup;
import org.openqa.selenium.firefox.FirefoxDriver;

import hexas.db.dbo.Product;
import hexas.db.dbo.Task;
import hexas.db.dbo.TaskType;

public class CDiscount {
	public static void main(String[] args) throws InterruptedException {
		FirefoxDriver driver = new SeleniumInitier().initDriver();
		try {
			Task task = Task
					.builder()
					.type(TaskType.ProductPriceScan)
					.url("https://www.cdiscount.com/juniors/jeux-de-construction/lego-21335-ideas-le-phare-motorise-maquette-a-con/f-12083-lego21335.html?idOffre=2172474890#mpos=0|cd&sw=409b082946e4b146f5e9937cb0309ce546ce280e19ff67081290329f1254040d94e57c4c6ea755784bf9ecc2fda2ee552acd396ec72f33ee4a78b17223e4a0b48f6cfa49d8740358702903bc88685aa50c7aa53159f840965a3e1162d9e3d8a4a247d96de16a21925f784a6998b31c72")
					.product(Product.builder().build())
					.build();
			driver.get(task.getUrl());

			TaskParser parser = new TaskParserRetreiver().resolve(task);
			parser.sleep();
			parser.parse(Jsoup.parse(driver.getPageSource()), task);
		} finally {
			driver.quit();
		}
	}
}
