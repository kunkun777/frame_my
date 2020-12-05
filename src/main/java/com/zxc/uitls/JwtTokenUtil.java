package com.zxc.uitls;

import com.zxc.contants.Constant;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.Map;
import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;

/**
 * ���ĳ��ģ��ǵø���ؾ�̬����
 */
@Slf4j
public class JwtTokenUtil {
    private static String secretKey;
    private static Duration accessTokenExpireTime;
    private static Duration refreshTokenExpireTIme;
    private static Duration refreshTokenExpireAppTIme;
    private static  String issuer;

    public static void setJwtProperties(TokenSetting tokenSetting){
        secretKey=tokenSetting.getSecretKey();
        accessTokenExpireTime=tokenSetting.getAccessTokenExpireTime();
        refreshTokenExpireAppTIme=tokenSetting.getRefreshTokenExpireAppTIme();
        refreshTokenExpireTIme=tokenSetting.getRefreshTokenExpireTIme();
        issuer=tokenSetting.getIssuer();
    }
    /**
     * ���� access_token
     * @Author:      С��
     * @UpdateUser:
     * @Version:     0.0.1
     * @param subject
     * @param claims
     * @return       java.lang.String
     * @throws
     */
        public static String getAccessToken(String subject, Map<String,Object> claims){

            return generateToken(issuer,subject,claims,accessTokenExpireTime.toMillis(),secretKey);
        }

        // ���������Ѿ������� access_token �ķ���������������� refresh_token �ķ���(PC �˹���ʱ���һЩ)

    /**
     * ���� PC refresh_token
     * @Author:      С��
     * @UpdateUser:
     * @Version:     0.0.1
     * @param subject
     * @param claims
     * @return       java.lang.String
     * @throws
     */
        public static String getRefreshToken(String subject,Map<String,Object> claims){
            return generateToken(issuer,subject,claims,refreshTokenExpireAppTIme.toMillis(),secretKey);
        }

    /**
     * ���� App�� refresh_token
     * @Author:      С��
     * @UpdateUser:
     * @Version:     0.0.1
     * @param subject
     * @param claims
     * @return       java.lang.String
     * @throws
     */
        public static String getRefreshAppToken(String subject,Map<String,Object> claims){
            return generateToken(issuer,subject,claims,refreshTokenExpireAppTIme.toMillis(),secretKey);
        }
    /**
     * ǩ��token
     * @Author:      С��
     * @UpdateUser:
     * @Version:     0.0.1
     * @param issuer ǩ����
     * @param subject �������JWT�����壬������������ һ�����û�id
     * @param claims �洢��JWT�������Ϣ һ���Щ�û���Ȩ��/��ɫ��Ϣ
     * @param ttlMillis ��Чʱ��(����)
     * @return       java.lang.String
     * @throws
     */
        public static String generateToken(String issuer, String subject, Map<String, Object> claims, long ttlMillis, String secret) {

            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

            long nowMillis = System.currentTimeMillis();
            Date now = new Date(nowMillis);

            byte[] signingKey = DatatypeConverter.parseBase64Binary(secret);

            JwtBuilder builder = Jwts.builder();
            builder.setHeaderParam("typ","JWT");
            if(null!=claims){
                builder.setClaims(claims);
            }
            if (!StringUtils.isEmpty(subject)) {
                builder.setSubject(subject);
            }
            if (!StringUtils.isEmpty(issuer)) {
                builder.setIssuer(issuer);
            }
            builder.setIssuedAt(now);
            if (ttlMillis >= 0) {
                long expMillis = nowMillis + ttlMillis;
                Date exp = new Date(expMillis);
                builder.setExpiration(exp);
            }
            builder.signWith(signatureAlgorithm, signingKey);
            return builder.compact();
        }

    /**
     * �������л�ȡ��������
     * @Author:      С��
     * @UpdateUser:
     * @Version:     0.0.1
     * @param token
     * @return       io.jsonwebtoken.Claims
     * @throws
     */
        public static Claims getClaimsFromToken(String token) {
            Claims claims=null;
            try {
                claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretKey)).parseClaimsJws(token).getBody();
            } catch (Exception e) {
                if(e instanceof ClaimJwtException){
                    claims=((ClaimJwtException) e).getClaims();
                }
            }
            return claims;
        }

    /**
     * ��ȡ�û�id
     * @Author:      С��
     * @UpdateUser:
     * @Version:     0.0.1
     * @param token
     * @return       java.lang.String
     * @throws
     */
        public static String getUserId(String token){
            String userId=null;
            try {
                Claims claims = getClaimsFromToken(token);
                userId = claims.getSubject();
            } catch (Exception e) {
                log.error("error={}",e);
            }
            return userId;
        }

    /**
     * ��ȡ�û���
     * @Author:      С��
     * @UpdateUser:
     * @Version:     0.0.1
     * @param token
     * @return       java.lang.String
     * @throws
     */
        public static String getUserName(String token){

            String username=null;
            try {
                Claims claims = getClaimsFromToken(token);
                username = (String) claims .get(Constant.JWT_USER_NAME);
            } catch (Exception e) {
                log.error("error={}",e);
            }
            return username;
        }

    /**
     * ��֤token �Ƿ����(true:�ѹ��� false:δ����)
     * @Author:      С��
     * @UpdateUser:
     * @Version:     0.0.1
     * @param token
     * @param secretKey
     * @return       java.lang.Boolean
     * @throws
     */
        public static Boolean isTokenExpired(String token) {

            try {
                Claims claims = getClaimsFromToken(token);
                Date expiration = claims.getExpiration();
                return expiration.before(new Date());
            } catch (Exception e) {
                log.error("error={}",e);
                return true;
            }
        }

    /**
     * У������(true����֤ͨ�� false����֤ʧ��)
     * @Author:      С��
     * @UpdateUser:
     * @Version:     0.0.1
     * @param token
     * @return       java.lang.Boolean
     * @throws
     */
        public static Boolean validateToken(String token) {
            Claims claimsFromToken = getClaimsFromToken(token);
            return (null!=claimsFromToken && !isTokenExpired(token));
        }

    /**
     * ��ȡtoken��ʣ�����ʱ��
     * @Author:      С��
     * @UpdateUser:
     * @Version:     0.0.1
     * @param token
     * @param secretKey
     * @return       long
     * @throws
     */
        public static long getRemainingTime(String token){
            long result=0;
            try {
                long nowMillis = System.currentTimeMillis();
                result= getClaimsFromToken(token).getExpiration().getTime()-nowMillis;
            } catch (Exception e) {
                log.error("error={}",e);
            }
            return result;
        }
}

