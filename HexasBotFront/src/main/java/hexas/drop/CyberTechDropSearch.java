package hexas.drop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CyberTechDropSearch implements DropSearch {

    private static final String BASE_URL = "https://www.cybertek.fr/carte-graphique/";
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:128.0) Gecko/20100101 Firefox/128.0";
    private final String marque;
    private final String metaModel;
    private final String model;
    private final String url;

    @Override
    public List<DropResult> search() throws IOException {
        Document doc = Jsoup.connect(BASE_URL + url).header("User-Agent", USER_AGENT).get();
        List<DropResult> results = new ArrayList<>();
        String stockLabel = doc.select("#ctl0_ContentPlaceHolder1_div_dispo_enligne").text();
        System.out.println(stockLabel);
        if ("En Stock".equals(stockLabel)) {
            DropResult result = DropResult.builder()
                    .marque(marque)
                    .metalModel(metaModel)
                    .model(model)
                    .url(BASE_URL + url)
                    .price(doc.select("#ctl0_ContentPlaceHolder1_l_prix").text())
                    .build();
            results.add(result);
        }
        System.out.println(doc);
        return results;
    }

    public static void main(String[] args) throws IOException {
        System.setProperty("java.net.useSystemProxies", "true");
        System.setProperty("http.proxyHost", "localhost");
        System.setProperty("http.proxyPort", "3128");

        System.out.println(new CyberTechDropSearch("Sapphire", "RX 5070XT", "Pulse Gaming OC",
                "sapphire-pulse-radeon-rx-9070-xt-gaming-oc-16gb-150015.aspx").search());
        System.out.println(new CyberTechDropSearch("MSI", "GT 710", "LP",
                "msi-geforce-gt-710-2gd3h-lp-28008.aspx").search());
    }


}
