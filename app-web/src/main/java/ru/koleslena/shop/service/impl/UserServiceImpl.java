package ru.koleslena.shop.service.impl;

import org.apache.commons.codec.binary.Base64;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import ru.koleslena.shop.exception.ShopException;
import ru.koleslena.shop.orm.dao.UserRoleDao;
import ru.koleslena.shop.orm.dto.User;
import ru.koleslena.shop.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@SpringBean
    private UserRoleDao userDao;

    @Transactional
    @Override
    public User authorizeUser(String login, String password) {

    	String security = encodeString(password);
    	
        User user = userDao.authUser(login, security);

        return user;
    }

    @Transactional
    @Override
    public User createUser(String login, String password) throws ShopException {

    	String security = encodeString(password);
    	
        User user = userDao.createUser(login, security);

        return user;
    }
    
    private String encodeString(String str) {
        return Base64.encodeBase64String(
                DigestUtils.md5DigestAsHex(str.getBytes()).getBytes()
        ).trim();
    }

}
