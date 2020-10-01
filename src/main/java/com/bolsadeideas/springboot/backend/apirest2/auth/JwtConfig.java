package com.bolsadeideas.springboot.backend.apirest2.auth;

/*Y en esta clace vamos a tener un public
 * y ahi tenemos un constante que static y final
 * static:  se puede acceder de forma directa por que es 
 * un atributo de la clase no del objeto es el nombre del objeto 
 * junto al atributo
 * final:no puede cambiar el valor es constante.
 * Vsmo a crear otra constante como por ejemplo rsa public*/

public class JwtConfig {
	
	public static final  String  LLAVE_SECRETA = "alguna.clave.secreta.12345678";
	
	public static final  String RSA_PRIVADA = "-----BEGIN RSA PRIVATE KEY-----\n" + 
			"MIIEpgIBAAKCAQEAse9WcsPzkEC5Y+PABWZdiDRGlVrDpy1okdgkij3P798Q4oMO\n" + 
			"pxBE72r81BaYA1Ek7vwx2CKzrbv7vPMWjeoTMa/BpnP2DyU7S9tCH1sy6pIV0gkp\n" + 
			"ARh4ZOUoGlCGnIFCa5c8SGH9Lv9Js3Ijdyx3YdWAPcI5cGeabWKvhQYG6n5/R24w\n" + 
			"4xii7pQ5WuewF/s76HPFor5Bg0DnPizmvYdRXMsjzBxcZKUBnw5TFUDOZP7Oj4pm\n" + 
			"SIgVECJfJIc73tObvWItXx1m5/d/8DDGf2Ycpl+B2FzPYxBsNA4CJhoORWqlK9F+\n" + 
			"CzgBoA515/NKu9CW9wdFpTkNpIxnj/cSl4FAwwIDAQABAoIBAQCWAK//RkRBut7P\n" + 
			"YAPUSYzWM0PBy2efuv8p3G5JgHyYupOQcW3b1zJWJ/qGjZmPSkv5kPAwRmj1MMGz\n" + 
			"c7by4n9V5tY5Dg9Ei12mwGvI5WhSKd9tVr2Tp95vKGSbcoFY1Pt6ml9+oeUARsUK\n" + 
			"G1HcuMbX2lYnOmKT6TNdvdE8Mm2IJr4CgRnw7xaMKacO2ziwjpfliF8cHmV0lN5X\n" + 
			"dbXt6XT8ZrqCKXxkZCtCNinB/ZM9+Er5iZ5NcFFNlgUC+LvtSa73r47ycFuCYrjE\n" + 
			"yZN+qUSR9K5MJ8Ewhx49zm1jkkZbf4EnXIGjPowIDbicr9upTQlPxO/Yvo4fpVbH\n" + 
			"6K7ZhihRAoGBANvA84442+caS8E2gpt5T+ODIavvngIXr2mb9U0b5w2ETwzpiH+s\n" + 
			"t7wPnFnXtHU7MS3PbpjwiuKyknGJ4NHATWtQ57SrqWwDSzKtF7FllmvL2SLzZv6m\n" + 
			"Ec/DC19P0rXcS5H+AmYHgDMeQpyL/SVmVRHOwV9Q52JimGSX/NN7WztrAoGBAM9I\n" + 
			"mOP6xxzsqyr8fBCte0UZ3J9gOXYA8/tzuUX6iawWcjTXcqHeOt34dCy3+Y5HrZGL\n" + 
			"ORK3m+9JJ4yzzDAWnKIKsGv8iqJBGQcbhpTjjtVuEgemUZrSHOprjIHKWUvO8dbJ\n" + 
			"H4OWrhWLzSJU1miunX7JfNUbnb5WdY4O1l/jCP4JAoGBAMB1k5lKmOUk9ZXJHMf3\n" + 
			"jg0Oyo2ftT3V6o3Oxh57QndnGiiP2zkeVmhBL8jLjsNhTOf3FMCjYq+6f2EqxRhO\n" + 
			"mD9VwyhLc30iwVgnmmmGNCBCju/6Rojdfxn7k1ktOriFsDWzRfx/szqR1Prv64w2\n" + 
			"vGh1MIw++fcDrL3lEstQ7QzzAoGBAKclpFwaSdtSIQitxqFwMvnay+QcpNk6FYQL\n" + 
			"ojgCumdxRjDEFuTeHdwGUDALPopz1EuL1773HiALfn/gMGQKp82nDvJVF7mVAsiP\n" + 
			"cqu2/+OtOY6/mAhfLAAAL7ItaGpP+wW28tmadNBdBlKL1MVfIRMvCKGT/lSQxYSD\n" + 
			"BG0X15V5AoGBAJ+EhU5d7YglY2MsmHlwD1qDbdhEpRI2PVZLqoStzZAOfT2VE977\n" + 
			"2iC4sGg6uJ7PZMeJM1krefpYc97cGsGSbhcXDDR75ps/VuHKtJAs7angBUVt8Cn9\n" + 
			"kxckvouDYziMLr9pf0q0+6728p5sZfFKEYaQwu7YxvuGH/CXnwD++DQi\n" + 
			"-----END RSA PRIVATE KEY-----";
	
	public static final  String RSA_PUBLICA = "-----BEGIN PUBLIC KEY-----\n" + 
			"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAse9WcsPzkEC5Y+PABWZd\n" + 
			"iDRGlVrDpy1okdgkij3P798Q4oMOpxBE72r81BaYA1Ek7vwx2CKzrbv7vPMWjeoT\n" + 
			"Ma/BpnP2DyU7S9tCH1sy6pIV0gkpARh4ZOUoGlCGnIFCa5c8SGH9Lv9Js3Ijdyx3\n" + 
			"YdWAPcI5cGeabWKvhQYG6n5/R24w4xii7pQ5WuewF/s76HPFor5Bg0DnPizmvYdR\n" + 
			"XMsjzBxcZKUBnw5TFUDOZP7Oj4pmSIgVECJfJIc73tObvWItXx1m5/d/8DDGf2Yc\n" + 
			"pl+B2FzPYxBsNA4CJhoORWqlK9F+CzgBoA515/NKu9CW9wdFpTkNpIxnj/cSl4FA\n" + 
			"wwIDAQAB\n" + 
			"-----END PUBLIC KEY-----";
	

}
