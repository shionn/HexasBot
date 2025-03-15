package hexas;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.session.SqlSession;

import hexas.db.SessionFactory;
import hexas.db.dao.ProductScanDao;
import hexas.db.dbo.Product;
import hexas.parser.AmazonPageParser;
import hexas.parser.PageParser;
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
		getPageParser().parse(webView, product);
		if (products.hasNext()) {
			product = products.next();
			webView.getEngine().load(product.getUrl());
		} else {
			Platform.exit();
		}
	}

	private PageParser getPageParser() {
		if (product.getUrl().startsWith("https://www.amazon.fr/")) {
			return new AmazonPageParser();
		}
		throw new IllegalStateException("no parser : " + product.getUrl());
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
