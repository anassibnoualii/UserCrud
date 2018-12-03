package springbootapi.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping()
public class VerifyToken {
	@Value("${token.jsk.password}")
	private String jskPassword;
	@Value("${token.kys.url}")
	private String kysUrl;
	@Value("${token.certif.url}")
	private String certifUrl;
	@Value("${token.get.url}")
	private String tokenUrl;
	@Value("${token.get.urlPath}")
	private String tokenUrlPath;

	@Autowired
	private RestTemplate restTemplate;

	@Bean
	RestTemplate resttemplate() {
		return new RestTemplate();
	}

	@GetMapping("/publicKey")
	public Jws<Claims> designMBankingToken(@RequestParam(value = "user") String user,
			@RequestParam(value = "login") String login) throws Exception {
		URI targetUrl = UriComponentsBuilder.fromUriString(tokenUrl).path(tokenUrlPath).queryParam("user", user)
				.queryParam("login", login).build().toUri();
		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		ks.load(new FileInputStream(kysUrl), jskPassword.toCharArray());
		PublicKey publicKey = loadPublicKey(certifUrl);
		String response = restTemplate.getForEntity(targetUrl, String.class).getBody();
		return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(response);
	}

	public PublicKey loadPublicKey(String filename) throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException,
	CertificateException, IOException {
		CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
		Certificate certificate = certificateFactory.generateCertificate(new FileInputStream(filename));
		return certificate.getPublicKey();

	}

}