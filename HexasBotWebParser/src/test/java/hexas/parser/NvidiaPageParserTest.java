package hexas.parser;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import hexas.zold.NvidiaPageParser;

class NvidiaPageParserTest {

	@Test
	public void testParsePrice() {
		assertThat(new NvidiaPageParser().parsePrice("2,239.00")).isEqualByComparingTo(BigDecimal.valueOf(2239.00));
	}

}
