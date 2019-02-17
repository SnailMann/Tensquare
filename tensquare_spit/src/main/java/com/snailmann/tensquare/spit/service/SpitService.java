package com.snailmann.tensquare.spit.service;

import com.snailmann.tensquare.common.util.IdWorker;
import com.snailmann.tensquare.spit.dao.SpitDao;
import com.snailmann.tensquare.spit.entity.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 吐槽服务层
 */
@Service
public class SpitService {

    @Autowired
    private SpitDao spitDao;

    @Autowired
    IdWorker idWorker;

    @Autowired
    MongoTemplate mongoTemplate;


    /**
     * 根据上级Id查询吐槽数据 | 分页查询
     *
     * @param parentId
     * @param page
     * @param size
     * @return
     */
    public Page<Spit> findByParentid(String parentId, int page, int size) {
        //记得要剪1 ，这个分页框架是从0当第一页算的
        Pageable pageable = PageRequest.of(page - 1, size);
        return spitDao.findByParentid(parentId, pageable);
    }


    /**
     * 为某个吐槽点赞
     *
     * @param spitId
     */
    public void thumbup(String spitId) {
        /**
         * 方式一： 这样的方式有效率问题，有两次对DB的交互
         */
        /*Spit spit = spitDao.findById(spitId).get();
        //点赞数 + 1
        spit.setThumbup((spit.getThumbup() == null ? 0 : spit.getThumbup()) + 1);
        spitDao.save(spit);*/

        /**
         * 方式二：改进，使用原生Mongo命令实现自增 db.spit.update({_id: "1"},{$inc: { thumbup: NumberInt(1)}})
         */
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is("1"));
        Update update = new Update();
        update.inc("thumbup", 1d);
        mongoTemplate.updateFirst(query, update, "spit");
    }

    public List<Spit> findAll() {
        return spitDao.findAll();
    }

    public Spit findById(String id) {
        return spitDao.findById(id).get();
    }

    /**
     * 新增一个吐槽 | 发布吐槽
     *
     * @param spit
     */
    public void save(Spit spit) {
        spit.set_id(idWorker.nextId() + "");
        //发布日期
        spit.setPublishtime(new Date());
        //浏览数
        spit.setVisits(0);
        //分享数
        spit.setShare(0);
        //点赞数
        spit.setThumbup(0);
        //评论数
        spit.setComment(0);
        //状态
        spit.setState("1");
        spitDao.save(spit);
    }

    public void update(Spit spit) {
        spitDao.save(spit);
    }

    public void deleteById(String id) {
        spitDao.deleteById(id);
    }

}
