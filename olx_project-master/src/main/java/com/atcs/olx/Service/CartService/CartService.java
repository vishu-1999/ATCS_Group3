package com.atcs.olx.Service.CartService;

import org.springframework.stereotype.Service;

import com.atcs.olx.Entity.Authenticate.Register;

@Service
public interface CartService {
    public String setCart(Register user,long prodId);
}
