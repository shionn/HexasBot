package hexas;

import java.io.StringWriter;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.ibatis.session.SqlSession;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import hexas.db.SessionFactory;
import hexas.db.dao.ProductScanDao;
import hexas.db.dbo.Product;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class ProductScanner extends Application implements ChangeListener<Worker.State> {

	private static final int TIME_SLEEP = 5;

	public static void main(String[] args) {
		launch(args);
	}

	private WebView webView;
	private Iterator<Product> products;
	private Product product;

	@Override
	public void start(Stage primaryStage) throws Exception {
		try (SqlSession session = new SessionFactory().open()) {
			products = session.getMapper(ProductScanDao.class).list().iterator();
		}

		product = products.next();
		webView = new WebView();
		webView.getEngine().load(product.getUrl());
		webView.getEngine().getLoadWorker().stateProperty().addListener(this);

		Scene scene = new Scene(webView, 1280, 900);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@Override
	public void changed(ObservableValue<? extends State> observable, State oldValue, State newState) {
		if (newState == Worker.State.SUCCEEDED) {
			sleepAndExecute(new Runnable() {
				@Override
				public void run() {
					scanPage();
				}
			});
		}
	}

	private void scanPage() {
		if (product.getUrl().startsWith("https://www.amazon.fr/")) {
			Document doc = toJsoup();
			String price = doc
					.select("#corePrice_feature_div span.a-price .a-offscreen")
					.stream()
					.map(Element::text)
					.findAny()
					.orElse(null);
			String vendor = doc
					.select("#merchantInfoFeature_feature_div .offer-display-feature-text-message")
					.stream()
					.map(Element::text)
					.findAny()
					.orElse(null);
			System.out.println(vendor + " " + price);
		}
		if (products.hasNext()) {
			product = products.next();
			webView.getEngine().load(product.getUrl());
		} else {
			Platform.exit();
		}
	}

	private Document toJsoup() {
		try {
			Transformer t = TransformerFactory.newInstance().newTransformer();
			StringWriter w = new StringWriter();
			t.transform(new DOMSource(webView.getEngine().getDocument()), new StreamResult(w));
			return Jsoup.parse(w.toString());
		} catch (TransformerException e) {
			throw new IllegalStateException(e);
		}
	}

	private void sleepAndExecute(Runnable runnable) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(TIME_SLEEP);
					Platform.runLater(runnable);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

}
