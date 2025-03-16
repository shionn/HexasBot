package hexas.parser;

import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javafx.scene.web.WebView;

public class WebViewToJsoup {

	public Document build(WebView webView) {
		try {
			Transformer t = TransformerFactory.newInstance().newTransformer();
			StringWriter w = new StringWriter();
			t.transform(new DOMSource(webView.getEngine().getDocument()), new StreamResult(w));
			return Jsoup.parse(w.toString());
		} catch (TransformerException e) {
			throw new IllegalStateException(e);
		}
	}

}
