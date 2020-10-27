package ssv.com.rest;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import ssv.com.entity.Account;
import ssv.com.service.AccountService;
import ssv.com.service.JwtService;



public class JwtAuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {
	 private final static String TOKEN_HEADER = "authorization";
	  @Autowired
	  private JwtService jwtService;
	  @Autowired
	  private AccountService accountService;
	  @Override
	  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	      throws IOException, ServletException {
	    HttpServletRequest httpRequest = (HttpServletRequest) request;
	    String authToken = httpRequest.getHeader(TOKEN_HEADER);
	    if (jwtService.validateTokenLogin(authToken)) {
	      String token = jwtService.getUsernameFromToken(authToken);
	      Account account = accountService.loadUserByEmail(token);
	      if (account != null) {
	        boolean enabled = true;
	        boolean accountNonExpired = true;
	        boolean credentialsNonExpired = true;
	        boolean accountNonLocked = true;
	        UserDetails userDetail = new User(token,account.getPassword(), enabled, accountNonExpired,
	            credentialsNonExpired, accountNonLocked, account.getAuthorities());
	        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail,
	            null, userDetail.getAuthorities());
	        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        httpRequest.getSession().setAttribute("userInfo", account);
	      }
	    }
	    chain.doFilter(request, response);
	  }

}
