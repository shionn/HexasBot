package hexas.parser;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class AmazonPageParserTest {

	private AmazonPageParser parser = new AmazonPageParser();

	@Test
	public void testParsePrice() {
		assertThat(parser.parsePrice("1 083,09€")).isEqualByComparingTo(BigDecimal.valueOf(1083.09));
		assertThat(parser.parsePrice("12,34 €")).isEqualByComparingTo(BigDecimal.valueOf(12.34));
	}

}
