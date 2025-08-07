package hexas.db.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import hexas.db.dbo.Bundle;
import hexas.db.dbo.BundleChoice;

public interface HumbleBundleDao {

	@Select("SELECT EXISTS(SELECT * FROM bundle WHERE url = #{url})")
	boolean exists(Bundle bundle);

	@Insert("INSERT INTO bundle(url, name, end_date) VALUES (#{url}, #{name}, #{endDate})")
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	int create(Bundle bundle);

	@Insert("INSERT INTO bundle_choice(bundle, price) VALUES (#{bundle.id}, #{choice.price})")
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "choice.id")
	int createChoice(@Param("bundle") Bundle bundle, @Param("choice") BundleChoice choice);

	@Insert("INSERT INTO bundle_choice_game(choice,game) VALUES( #{choice.id}, #{game})")
	int createChoiceGame(@Param("choice") BundleChoice choice, @Param("game") String game);

}
