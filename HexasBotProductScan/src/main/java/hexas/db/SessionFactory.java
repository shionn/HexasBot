package hexas.db;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SessionFactory {

	private static class Holder {
		private static final Holder instance = new Holder();

		private SqlSessionFactory factory = build();

		private SqlSessionFactory build() {
			try {
				return new SqlSessionFactoryBuilder()
					.build(Thread.currentThread().getContextClassLoader().getResourceAsStream("mybatis.xml"));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	private SqlSessionFactory factory() {
		return Holder.instance.factory;
	}

	public org.apache.ibatis.session.SqlSession open() {
		return factory().openSession();
	}

}
