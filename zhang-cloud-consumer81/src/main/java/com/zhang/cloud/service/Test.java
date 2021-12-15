package com.zhang.cloud.service;

import com.zhang.cloud.aop.UserParam;
import com.zhang.cloud.entities.BookDO;
import com.zhang.cloud.entities.UserDO;

/**
 * @author 98549
 * @date 2021/12/5 20:07
 */
@UserParam
public interface Test {

    UserDO findUser1ById(int id);

    BookDO findBookById(int id);
}
