package hexas.db;


import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.RequestScope;

@Controller
public class SpringSessionFactory {

	private static class Holder {
		private static final Holder instance = new Holder();

		private SqlSessionFactory factory = build();

		private SqlSessionFactory build() {
			return new SqlSessionFactoryBuilder().build(Thread.currentThread()
					.getContextClassLoader().getResourceAsStream("mybatis.xml"));
		}
	}

	private SqlSessionFactory factory() {
		return Holder.instance.factory;
	}

	@Bean
	@RequestScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
	public org.apache.ibatis.session.SqlSession open() {
		org.apache.ibatis.session.SqlSession session = factory().openSession();
		return new SqlSession(session);
	}

}
