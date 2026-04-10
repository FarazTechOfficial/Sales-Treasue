package com.example.distribution_sales_techfira.service.impl;

import com.example.distribution_sales_techfira.service.TokenBlacklist;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class TokenBlacklistService implements TokenBlacklist {
private  final ConcurrentMap<String, Date> blacklistedTokens = new ConcurrentHashMap<>();
private final JwtServiceImp jwtServiceImp;


public TokenBlacklistService(JwtServiceImp jwtServiceImp){
    this.jwtServiceImp=jwtServiceImp;
}
public void addToBlacklist(String token){
    Date expirationDate = jwtServiceImp.extractExpiration(token);
    blacklistedTokens.put(token,expirationDate);
}

public boolean isBlacklisted(String token){
    Date expirationDate = blacklistedTokens.get(token);
    if (expirationDate == null){
        return  false;
    }
    if (expirationDate.before(new Date())){
        blacklistedTokens.remove(token);
        return false;
    }
    return true;
}

@Scheduled(fixedRate = 86400000) // Cleanup every 24 hours
    public void cleanupExpiredTokens(){
    Date now = new Date();
    blacklistedTokens.entrySet().removeIf(entry-> entry.getValue().before(now));
}

}
