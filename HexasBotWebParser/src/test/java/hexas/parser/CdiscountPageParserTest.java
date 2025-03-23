package hexas.parser;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class CdiscountPageParserTest {

	@Test
	public void testParsePrice() {
		assertThat(new CdiscountPageParser().parsePrice("1028,98 €")).isEqualByComparingTo(BigDecimal.valueOf(1028.98));
	}
}
