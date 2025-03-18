package hexas;

import org.openqa.selenium.By;

public abstract class Bys extends By {

	public static By text(String text) {
		return By.xpath("//*[normalize-space()='" + text + "']");
	}


}
