package hexas.drop;

import java.io.IOException;

import hexas.db.dbo.Product;

public class DropScannerTester {

	
	public static void main(String[] args) throws IOException {
		new DropScanner()
				.scan(Product
						.builder()
						.url("https://www.caseking.de/en/asus-prime-radeon-rx-9070-xt-oc-edition-grafikkarte-16384-mb-gddr6/GCAS-659.html")
						.build());
	}

}
