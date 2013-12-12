package ru.koleslena.shop.web.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import ru.koleslena.shop.orm.dto.User;
import ru.koleslena.shop.service.UserService;

public class ShopAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserService userService;
	
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		
		User user = userService.authorizeUser(authentication.getName(), authentication.getCredentials().toString());
		
		if (user != null) {
			String role = user.getRole().getSpringName();
			
			final List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>(); {
				AUTHORITIES.add(new SimpleGrantedAuthority(role));
			}
			
			UserDetails userDetails = new UserDetails(user.getId(), user.getName());
			
			return new UsernamePasswordAuthenticationToken(userDetails, null, AUTHORITIES);
		}
			throw new BadCredentialsException("Неправильная пара логин-пароль");
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return false;
	}

}
