package ru.koleslena.shop.test.orm.dao.testimpl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import ru.koleslena.shop.exception.ShopException;
import ru.koleslena.shop.orm.dao.UserRoleDao;
import ru.koleslena.shop.orm.dto.Role;
import ru.koleslena.shop.orm.dto.User;

/**
 * @author koleslena
 *  <p/>
 * Date: Dec 17, 2013
 */
@Repository
public class UserRoleDaoTest implements UserRoleDao, Serializable {

	private DataHolder holder = DataHolder.getInstance();
	
	@Override
	public User authUser(String login, String security) {
		return (User) (login.equals("lena") ? holder.get(User.class, new Long(1)) : holder.get(User.class, new Long(2)));
	}

	@Override
	public void createUser(String login, String security) throws ShopException {
		User u = new User();
		u.setName(login);
		u.setPassword(security);
		u.setRole(getRoleBySpringName(Role.STRING_USER_ROLE_NAME));
		
		holder.save(u);
	}

	@Override
	public Role getRoleBySpringName(String name) {
		return holder.getRole(name);
	}
}
