package hexas.drop;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class PcComponeentes {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:128.0) Gecko/20100101 Firefox/128.0";

    public static void main(String[] args) throws IOException {
        System.setProperty("java.net.useSystemProxies", "true");
        System.setProperty("http.proxyHost", "localhost");
        System.setProperty("http.proxyPort", "3128");

        Document document = Jsoup
                .connect("https://www.pccomponentes.fr/cartes-graphiques/amd-radeon-rx-9070-xt?seller=pccomponentes")
                .header("User-Agent", USER_AGENT)
                .get();
        document.select("#category-list-product-grid > a").stream().forEach(e -> {
            String price = e.select("[data-e2e='price-card']").text();
            String name = e.select("h3").text();
            System.out.println(name + " : " + price);

        });

    }
}
