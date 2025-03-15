package hexas;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.session.SqlSession;

import hexas.db.SessionFactory;
import hexas.db.dao.ProductScanDao;
import hexas.db.dbo.Product;
import hexas.parser.PageParserRetreiver;
import hexas.parser.WebViewToJsoup;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class JavaFxProductScanner extends Application implements ChangeListener<Worker.State> {

	private static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64; rv:136.0) Gecko/20100101 Firefox/136.0";
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
			products = session.getMapper(ProductScanDao.class).list("javafx").iterator();
		}

		product = products.next();
		webView = new WebView();
		webView.getEngine().setUserAgent(USER_AGENT);
		webView.getEngine().getLoadWorker().stateProperty().addListener(this);
		webView.getEngine().load(product.getUrl());

		Scene scene = new Scene(webView, 1600, 1200);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@Override
	public void changed(ObservableValue<? extends State> observable, State oldValue, State newState) {
		if (newState == Worker.State.SUCCEEDED) {
			System.out.println("page loaded");
//			if (webView.getEngine().getDocument().getTextContent().contains("Cloudflare")) {
//				sleepAndExecute(TIME_SLEEP * 3, new Runnable() {
//					@Override
//					public void run() {
//					}
//				});
//			} else {
				sleepAndExecute(TIME_SLEEP, new Runnable() {
					@Override
					public void run() {

						scanPage();
					}
				});
//			}
		}
	}

	private void scanPage() {
		new PageParserRetreiver().resolve(product).parse(new WebViewToJsoup().build(webView), product);
		if (products.hasNext()) {
			product = products.next();
			webView.getEngine().load(product.getUrl());
		} else {
			Platform.exit();
		}
	}

	private void sleepAndExecute(long time, Runnable runnable) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(time);
					Platform.runLater(runnable);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

}
