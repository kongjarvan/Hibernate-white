package site.metacoding.white.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

@Component
public class SHA256 {

	public String encrypt(String text) {
		// text += "_솔트";
		// 소금치면 해시값이 완전 뒤틀려져서 DB가 뚫혀도 해시 된 값은 뚫리지 않음
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(text.getBytes());
			return bytesToHex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("비밀번호 해싱 실패");
		}

	}

	private String bytesToHex(byte[] bytes) {
		StringBuilder builder = new StringBuilder();
		for (byte b : bytes) {
			builder.append(String.format("%02x", b));
		}
		return builder.toString();
	}

}