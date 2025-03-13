package hexas.drop;

import java.io.IOException;

public interface DropSearch {

	void search() throws IOException;

	String getPrice();
	
	String getVendor();

	String getModel();
}
