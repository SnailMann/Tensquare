package com.snailmann.tensquare.base.service;

import com.snailmann.tensquare.base.dao.LabelDao;
import com.snailmann.tensquare.base.entity.Label;
import com.snailmann.tensquare.common.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


/**
 * 标签业务逻辑层
 *
 * @author SnailMann
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class LabelService {

    @Autowired
    private LabelDao labelDao;
    @Autowired
    private IdWorker idWorker;

    public List<Label> findAll() {
        return labelDao.findAll();
    }

    public Label findById(String id) {
        return labelDao.findById(id).get();
    }

    public void save(Label label){
        //分布式ID生成器生成ID
        label.setId(idWorker.nextId()+"");
        labelDao.save(label);
    }

    public void update(Label label){
        labelDao.save(label);
    }

    public void deleteById(String id){
        labelDao.deleteById(id);
    }

}
