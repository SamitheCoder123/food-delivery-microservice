package com.swiggy.app.userService.service;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author : Samiullah Makandar
 * @purpose :
 */

@Service
public class TokenBlacklistService {
    private Set<String> blacklistedTokens = new HashSet<>();

    public void addToBlacklist(String token) {
        blacklistedTokens.add(token);
    }

    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }
}
