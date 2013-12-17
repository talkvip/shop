package ru.koleslena.shop.service.impl;

import javax.inject.Inject;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import ru.koleslena.shop.exception.ShopException;
import ru.koleslena.shop.orm.dao.BaseDao;
import ru.koleslena.shop.orm.dao.UserRoleDao;
import ru.koleslena.shop.orm.dto.User;
import ru.koleslena.shop.service.UserService;

/**
 * @author koleslena
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Inject
    private UserRoleDao userRoleDao;
	
	@Inject
    private BaseDao baseDao;
	
    @Override
    public User authorizeUser(String login, String password) {

    	String security = encodeString(password);
    	
        User user = userRoleDao.authUser(login, security);

        return user;
    }

    @Override
    public void createUser(String login, String password) throws ShopException {

    	String security = encodeString(password);
    	
    	userRoleDao.createUser(login, security);

        return;
    }
    
    private String encodeString(String str) {
    	return Base64.encodeBase64String(
                DigestUtils.md5DigestAsHex(str.getBytes()).getBytes()
        ).trim();
    }

	@Override
	public User get(Long id) {
		return baseDao.findById(User.class, id);
	}

}
