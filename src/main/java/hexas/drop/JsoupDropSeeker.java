package hexas.drop;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JsoupDropSeeker {

	public static void main(String[] args) throws IOException {

		List<AmazonDropSearch> drops = Arrays
				.asList(new AmazonDropSearch("XFX Quicksilver RX 9070XT Gaming",
						"https://www.amazon.fr/XFX-Quicksilver-Radeon-9070XT-RX-97TQICKB9/dp/B0DXVMSQ5T"),
						new AmazonDropSearch("XFX Quicksilver RX 9070 OC Gaming",
								"https://www.amazon.fr/XFX-Quicksilver-Radeon-Gaming-RX-97QICKBBA/dp/B0DW4H2R4D"));
		for (AmazonDropSearch drop : drops) {
			drop.search();
			System.out.println(drop.getModel() + " : " + drop.getPrice() + " : " + drop.getVendor());
		}

		// pc componentes :
		// https://www.pccomponentes.fr/cartes-graphiques/amd-radeon-rx-9070-xt?seller=pccomponentes
	}
}
