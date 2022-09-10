package com.example.test.old.old_service;

import com.example.test.old.entity.favor;

import java.util.List;

public interface IFavorService {
    int addfavor(String username, int foodid);
    int deletefavor(String username, int foodid);
    List<favor> searchfavor(String username, int foodid);
    List<favor> favorlist(String username);
}
