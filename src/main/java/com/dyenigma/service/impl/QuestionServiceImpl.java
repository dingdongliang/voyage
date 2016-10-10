package com.dyenigma.service.impl;

import com.dyenigma.dao.QuestionMapper;
import com.dyenigma.entity.Question;
import com.dyenigma.service.QuestionService;
import com.dyenigma.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Description:
 * author  dyenigma
 * date 2016/5/13 11:06
 */
@Transactional
@Service("questionService")
public class QuestionServiceImpl extends BaseServiceImpl<Question> implements QuestionService {
    @Autowired
    protected QuestionMapper questionMapper;

    /**
     * Description: 分页查询所有信息
     * Name:findAllByPage
     * Author:dyenigma
     * Time:2016/5/13 11:10
     * param:[pageUtil]
     * return:java.util.List<com.dyenigma.entity.Question>
     *
     * @param pageUtil
     */
    @Override
    public List<Question> findAllByPage(PageUtil pageUtil) {
        return questionMapper.findAllByPage(pageUtil);
    }

    /**
     * Description:查找某个用户的所有问题
     * Name:findAllByUser
     * Author:dyenigma
     * Time:2016/5/13 11:04
     * param:[userId]
     * return:java.util.List<com.dyenigma.entity.Question>
     *
     * @param userId
     */
    @Override
    public List<Question> findAllByUser(String userId) {
        return null;
    }

    /**
     * Description:查找某段时间内的所有问题
     * Name:findAllByTime
     * Author:dyenigma
     * Time:2016/5/13 11:04
     * param:[start, end]
     * return:java.util.List<com.dyenigma.entity.Question>
     *
     * @param start
     * @param end
     */
    @Override
    public List<Question> findAllByTime(String start, String end) {
        return null;
    }

    /**
     * Description:按关键字列查询问题
     * Name:findAllByKey
     * Author:dyenigma
     * Time:2016/5/13 11:04
     * param:[key]
     * return:java.util.List<com.dyenigma.entity.Question>
     *
     * @param key
     */
    @Override
    public List<Question> findAllByKey(String key) {
        return null;
    }

    /**
     * Description:模糊查询
     * Name:findAllByWord
     * Author:dyenigma
     * Time:2016/5/13 11:05
     * param:[word]
     * return:java.util.List<com.dyenigma.entity.Question>
     *
     * @param word
     */
    @Override
    public List<Question> findAllByWord(String word) {
        return null;
    }
}
