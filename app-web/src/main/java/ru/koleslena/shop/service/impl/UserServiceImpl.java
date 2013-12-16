package ru.koleslena.shop.service.impl;

import javax.inject.Inject;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import ru.koleslena.shop.exception.ShopException;
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
	
    @Override
    public User authorizeUser(String login, String password) {

    	String security = encodeString(password);
    	
        User user = userRoleDao.authUser(login, security);

        return user;
    }

    @Override
    public User createUser(String login, String password) throws ShopException {

    	String security = encodeString(password);
    	
        User user = userRoleDao.createUser(login, security);

        return user;
    }
    
    private String encodeString(String str) {
    	return Base64.encodeBase64String(
                DigestUtils.md5DigestAsHex(str.getBytes()).getBytes()
        ).trim();
    }

}
