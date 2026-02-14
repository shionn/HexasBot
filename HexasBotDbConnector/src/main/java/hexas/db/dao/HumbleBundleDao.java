package hexas.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import hexas.db.dbo.Bundle;
import hexas.db.dbo.BundleChoice;

public interface HumbleBundleDao {

	@Select("SELECT EXISTS(SELECT * FROM api_key WHERE value = #{key} and type = 'humble-bundle')")
	boolean isAuthorized(String key);

	@Select("SELECT EXISTS(SELECT * FROM bundle WHERE name = #{name})")
	boolean exists(Bundle bundle);

	@Insert("INSERT INTO bundle(url, name, end_date) VALUES (#{url}, #{name}, #{endDate})")
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	int create(Bundle bundle);

	@Insert("INSERT INTO bundle_choice(bundle, price) VALUES (#{bundle.id}, #{choice.price})")
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "choice.id")
	int createChoice(@Param("bundle") Bundle bundle, @Param("choice") BundleChoice choice);

	@Insert("INSERT INTO bundle_choice_game(choice,game) VALUES( #{choice.id}, #{game})")
	int createChoiceGame(@Param("choice") BundleChoice choice, @Param("game") String game);

	@Select("SELECT * FROM bundle WHERE end_date > NOW() ORDER BY id DESC LIMIT 1")
	@Results({ //
			@Result(column = "id", property = "id"),
			@Result(column = "id", property = "choices", many = @Many(select = "listChoices")),
	})
	Bundle retreiveLast();

	@Select("SELECT * FROM bundle WHERE notified IS FALSE ORDER BY end_date LIMIT 1")
	@Results({ //
			@Result(column = "id", property = "id"),
			@Result(column = "id", property = "choices", many = @Many(select = "listChoices")),
	})
	Bundle oneToNotify();

	@Select("SELECT * FROM bundle_choice WHERE bundle = #{bundle} ORDER BY price DESC")
	@Results({ //
			@Result(column = "id", property = "id"),
			@Result(column = "id", property = "games", many = @Many(select = "listGames")),
	})
	List<BundleChoice> listChoices(int bundle);

	@Select("SELECT game FROM bundle_choice_game WHERE choice = #{choice} ORDER BY game")
	List<String> listGames(int choice);

	@Update("UPDATE bundle SET notified = TRUE WHERE id = #{id}")
	int markNotifyied(Bundle bundle);


}
