package com.aptech.hrmapi.security.jwt;

import com.aptech.hrmapi.common.Response;
import com.aptech.hrmapi.exception.CommonException;
import io.jsonwebtoken.Jwts;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@SuppressWarnings("all")
public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserDetailsService userDetailsService;
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

    private long jwtExpiration = 600000;

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private long jwtExpirationConst;

    // Lọc các requset gửi đến
    // Kiểm tra token được gửi kèm theo
    // Mặc định mỗi requset có hiệu lực 10p
    // sau 10p ko có requset nào gửi đến => token hết hạn => trả về đăng nhập
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwt(request);
            if (jwt != null) {
                long loginTime = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt).getBody().getIssuedAt().getTime();
                if (new Date().getTime() - (loginTime + jwtExpiration) < 0) {
                    jwtExpiration = (new Date().getTime() - loginTime) + jwtExpirationConst;
                    String username = jwtProvider.getUserNameFromJwtToken(jwt);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } else {
                    jwtExpiration = 600000;
                    throw new CommonException(Response.EXPIRED_JWT_TOKEN);
                }
            } else {
                filterChain.doFilter(request, response);
                return;
            }
        } catch (CommonException e) {
            logger.error("Common exception", e);
        } catch (Exception ex) {
            logger.error("Can NOT set user authentication -> Message: {}", ex);
        }
        filterChain.doFilter(request, response);
    }

    // Lấy token từ request được gửi đến
    private String getJwt(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.replace("Bearer ", "");
        }
        return null;
    }
}
