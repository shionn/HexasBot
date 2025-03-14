package hexas.drop;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class JsoupDropSeeker {

	private static final List<DropSearch> SOURCES = Arrays
			.asList(new AmazonDropSearch("XFX", "RX 9070XT", "Quicksilver OC Gaming",
					"XFX-Quicksilver-Radeon-9070XT-RX-97TQICKB9/dp/B0DXVMSQ5T"),
					new AmazonDropSearch("XFX", "RX 9070", "Quicksilver OC Gaming",
							"XFX-Quicksilver-Radeon-Gaming-RX-97QICKBBA/dp/B0DW4H2R4D"));

	@Autowired
	private DropDatabase db;

	@Scheduled(fixedDelay = 5, timeUnit = TimeUnit.MINUTES)
	public void scan() throws IOException {
		db.getDrops().clear();
		for (DropSearch drop : SOURCES) {
			List<DropResult> search = drop.search();
			System.out.println("found : " + search);
			db.getDrops().addAll(search);
		}

	}

}
