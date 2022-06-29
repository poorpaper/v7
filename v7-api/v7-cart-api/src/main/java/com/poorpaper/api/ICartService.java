package com.poorpaper.api;

import com.poorpaper.commons.pojo.ResultBean;

public interface ICartService {
    
    public ResultBean add(String token, Long productId, Integer count);

    public ResultBean del(String token, Long productId);

    public ResultBean update(String token, Long productId, Integer count);

    public ResultBean query(String token);

    public ResultBean merge(String noLoginKey, String loginKey);
}
