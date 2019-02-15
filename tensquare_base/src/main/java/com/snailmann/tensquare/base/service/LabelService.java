package com.snailmann.tensquare.base.service;

import com.snailmann.tensquare.base.dao.LabelDao;
import com.snailmann.tensquare.base.entity.Label;
import com.snailmann.tensquare.common.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
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

    /**
     * 查询所有的标签
     */
    public List<Label> findAll() {
        return labelDao.findAll();
    }

    /**
     * 根据id查询标签
     *
     * @param id
     * @return
     */
    public Label findById(String id) {
        return labelDao.findById(id).get();
    }

    /**
     * 新增加一个标签
     *
     * @param label
     */
    public void save(Label label) {
        //分布式ID生成器生成ID
        label.setId(idWorker.nextId() + "");
        labelDao.save(label);
    }

    /**
     * 更新标签
     *
     * @param label
     */
    public void update(Label label) {
        labelDao.save(label);
    }

    /**
     * 根据id删除标签
     *
     * @param id
     */
    public void deleteById(String id) {
        labelDao.deleteById(id);
    }

    /**
     * 动态条件查询，根据label的动态条件查询符合条件的所有label
     * label的属性为空，则代表没条件，属性不为空则代表有条件
     *
     * @param label
     * @return
     */
    public List<Label> findSearch(Label label) {
        return labelDao.findAll(new Specification<Label>() {

            /**
             *
             * @param root  根对象，也就是要把条件封装到哪个对象中，where 类名 = label.getId
             * @param query 封装的都是查询关键字，比如group by ,order by ； 不常用到
             * @param cb 用来封装条件象的，如果直接返回null, 表示不需要任何条件
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();
                if (label.getLabelname() != null && !"".equals(label.getLabelname())) {
                    //第一个参数就是where lablename like ; 第二个参数就是后面的条件
                    Predicate predicate = cb.like(root.get(Label.LABEL_NAME).as(String.class), "%" + label.getLabelname() + "%");
                    predicateList.add(predicate);
                }
                if (label.getState() != null && !"".equals(label.getState())) {
                    Predicate predicate = cb.equal(root.get(Label.STATE).as(String.class), label.getState());
                    predicateList.add(predicate);
                }

                Predicate[] predicates = new Predicate[predicateList.size()];
                //一个predicate就是一个条件的拼装，cb.and可以把多个predicate合并成一个predicate; 其实就是where xxx and xxx and xxx
                return cb.and(predicateList.toArray(predicates));
            }
        });
    }

    /**
     * 动态条件查询标签列表 | 分页查询
     * 对比之前写的Mybatis plus的分页，我们可以知道，一般从前端传入的就两个分页条件
     * page： 当前页 ； size 每页的大小，有多少数据
     *
     * @param label 查询条件
     * @param page  当前页
     * @param size  每页的数量
     * @return
     */
    public Page<Label> pageQuery(Label label, int page, int size) {
        //封装分页对象，因为这个分页框架是从第0页开始，而前端页面肯定是从第一开始，所以需要 - 1
        Pageable pageable = PageRequest.of(page - 1, size);

        return labelDao.findAll((Specification<Label>) (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (label.getLabelname() != null && !"".equals(label.getLabelname())) {
                //第一个参数就是where lablename like ; 第二个参数就是后面的条件
                Predicate predicate = cb.like(root.get(Label.LABEL_NAME).as(String.class), "%" + label.getLabelname() + "%");
                predicateList.add(predicate);
            }
            if (label.getState() != null && !"".equals(label.getState())) {
                Predicate predicate = cb.equal(root.get(Label.STATE).as(String.class), label.getState());
                predicateList.add(predicate);
            }

            Predicate[] predicates = new Predicate[predicateList.size()];
            //一个predicate就是一个条件的拼装，cb.and可以把多个predicate合并成一个predicate; 其实就是where xxx and xxx and xxx
            return cb.and(predicateList.toArray(predicates));
        }, pageable);
    }
}
