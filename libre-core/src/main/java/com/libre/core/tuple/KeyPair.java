package com.libre.core.tuple;

import cn.hutool.crypto.SecureUtil;
import lombok.RequiredArgsConstructor;


/**
 * rsa 的 key pair 封装
 *
 */
@RequiredArgsConstructor
public class KeyPair {

	public String getPublicBase64() {
		return SecureUtil.rsa().getPublicKeyBase64();
	}

	public String getPrivateBase64() {
		return SecureUtil.rsa().getPrivateKeyBase64();
	}

	@Override
	public String toString() {
		return "PublicKey=" + this.getPublicBase64() + '\n' + "PrivateKey=" + this.getPrivateBase64();
	}
}
