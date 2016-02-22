package com.lnb.simpleframework.tools;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Utility {

	/**
	 * 将键值对重组为HTTP参数形式，做UrlEncode并返回
	 *
	 * @param paramsMap
	 * @return
	 */
	public static String getHttpParamsStr(Map<String, String> paramsMap) {
		try {
			HttpEntity entity = null;
			List<NameValuePair> _paramsList = new ArrayList<NameValuePair>();
			StringBuilder paramStringBuilder = new StringBuilder();

			for (String key : paramsMap.keySet()) {
				_paramsList
						.add(new BasicNameValuePair(key, paramsMap.get(key)));
			}

			entity = new UrlEncodedFormEntity(_paramsList, HTTP.UTF_8);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					entity.getContent()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				paramStringBuilder.append(line);
			}

			return paramStringBuilder.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 由Query字符串转为HashMap
	 *
	 * @param paramsStr
	 *            形如"key1=value1&key2=value2..."
	 * @return decode后的HashMap [key, value]
	 */
	public static HashMap<String, String> getHttpParamsMap(String paramsStr) {
		HashMap<String, String> paramsMap = new HashMap<String, String>();

		if (paramsStr != null) {
			String[] keyValuePairs = paramsStr.split("&");
			for (String keyValuePair : keyValuePairs) {
				String[] keyToValue = keyValuePair.split("=");
				try {
					if (keyToValue.length == 2) {
						paramsMap.put(
								URLDecoder.decode(keyToValue[0], HTTP.UTF_8),
								URLDecoder.decode(keyToValue[1], HTTP.UTF_8));
					} else {
						paramsMap.put(
								URLDecoder.decode(keyToValue[0], HTTP.UTF_8),
								"");
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}

		return paramsMap;
	}

	/**
	 * AES encode 加密
	 *
	 * @param key
	 *            迷药
	 * @param plainText
	 *            无加密串
	 * @return
	 */
	public static String AESEncrypt(String key, String plainText) {
		// convert key to bytes
		try {
			byte[] keyBytes = key.getBytes("UTF-8");
			// Use the first 16 bytes (or even less if key is shorter)
			byte[] keyBytes16 = new byte[16];
			System.arraycopy(keyBytes, 0, keyBytes16, 0,
					Math.min(keyBytes.length, 16));

			// convert plain text to bytes
			byte[] plainBytes = plainText.getBytes("UTF-8");

			// setup cipher
			SecretKeySpec skeySpec = new SecretKeySpec(keyBytes16, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
			// byte[] iv = new byte[16]; // initialization vector with all 0
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

			// encrypt
			byte[] encrypted = cipher.doFinal(plainBytes);
			byte[] encoded = android.util.Base64.encode(encrypted, android.util.Base64.NO_WRAP);
			String encodedStr = new String(encoded);
			return encodedStr;
		} catch (Exception e) {

		}
		return "";
	}

	/**
	 * AES解密
	 *
	 * @param key
	 *            迷药
	 * @param data
	 *            加密串
	 * @return
	 */
	public static String AESDecrpyt(String key, String data) {
		try {
			byte[] keyBytes = key.getBytes("UTF-8");
			// Use the first 16 bytes (or even less if key is shorter)
			byte[] keyBytes16 = new byte[16];
			System.arraycopy(keyBytes, 0, keyBytes16, 0,
					Math.min(keyBytes.length, 16));

			byte[] decoded = android.util.Base64.decode(data, android.util.Base64.DEFAULT);

			// setup cipher
			SecretKeySpec skeySpec = new SecretKeySpec(keyBytes16, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);

			// decrypt
			byte[] decrypted = cipher.doFinal(decoded);
			return new String(decrypted);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
