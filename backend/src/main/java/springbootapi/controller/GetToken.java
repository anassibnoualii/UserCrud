package  springbootapi.controller;



import java.io.FileInputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping()
public class GetToken {
	
	@Value("${token.jsk.password}")
	private String jskPassword;
	@Value("${token.kys.url}")
	private String kysUrl ;
	@Value("${token.certif.url}")
	private String certifUrl;

	@GetMapping("/token")
	public String designKaycloakToken() {
		String jwtToken = "";
		String email = "annass.ibnouali@gmail.com";
		jwtToken = Jwts.builder().setSubject(email).claim("roles", "user").setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, "secretkey").compact();
		return jwtToken;
	}

	@GetMapping("/mbankingToken")
	public String signMBankingToken(@RequestParam(value = "user") String user,@RequestParam(value = "login") String login) throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException,
			CertificateException, IOException {
		Calendar calNow = Calendar.getInstance();
		Date dateNow = calNow.getTime();
		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		ks.load(new FileInputStream(kysUrl), jskPassword.toCharArray());
		Key key = ks.getKey(jskPassword, jskPassword.toCharArray());
		calNow.add(Calendar.DATE, 3);
		Date dateExp = calNow.getTime();
		Map<String, Object> claims = new HashMap<>();
		claims.put("user", user);
		claims.put("login", login);
		
		return  Jwts.builder().setSubject("mbanking").setAudience("mbanking").setExpiration(dateExp)
				.setIssuedAt(dateNow).setId("mbankingid").setClaims(claims).signWith(SignatureAlgorithm.RS512, key)
				.compact();
		
	}

}
