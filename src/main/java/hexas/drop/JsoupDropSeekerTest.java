package hexas.drop;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class JsoupDropSeeker {
    public static void main(String[] args) throws IOException {
        Document doc =
                Jsoup.connect("https://www.amazon.fr/XFX-Quicksilver-Radeon-9070XT-RX-97TQICKB9/dp/B0DXVMSQ5T")
                        .header("User-Agent",
                                "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:128.0) Gecko/20100101 Firefox/128.0")
                        .get();
        //        System.out.println(doc);
        doc.select("#corePrice_feature_div span.a-price .a-offscreen")
                .stream()
                .map(Element::text)
                .forEach(System.out::println);

        doc.select("#merchantInfoFeature_feature_div .offer-display-feature-text-message")
                .stream()
                .map(e -> e.text())
                .distinct()
                .forEach(System.out::println);

        // pc componentes : https://www.pccomponentes.fr/cartes-graphiques/amd-radeon-rx-9070-xt?seller=pccomponentes
    }
}
