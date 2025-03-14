package hexas.drop;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PcComponeentesDropSearch implements DropSearch {

    private static final String BASE_URL = "https://www.pccomponentes.fr/";
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:128.0) Gecko/20100101 Firefox/128.0";
    private final String metaModel;
    private final String url;

    @Override
    public List<DropResult> search() throws IOException {
        Document document = Jsoup.connect(BASE_URL + url).header("User-Agent", USER_AGENT).get();
        return document.select("#category-list-product-grid > a").stream().map(e -> {
            String price = e.select("[data-e2e='price-card']").text();
            String name = e.select("h3").text();
            return DropResult.builder()
                    .price(price)
                    .model(name)
                    .metalModel(metaModel)
                    .vendor("PCComponentes.fr")
                    .url(BASE_URL + url)
                    .build();
        }).toList();
    }

    public static void main(String[] args) throws IOException {
        System.setProperty("java.net.useSystemProxies", "true");
        System.setProperty("http.proxyHost", "localhost");
        System.setProperty("http.proxyPort", "3128");

        System.out
                .println(new PcComponeentesDropSearch("RX 9070XT",
                        "cartes-graphiques/amd-radeon-rx-9070-xt?seller=pccomponentes")
                        .search());
    }

}
