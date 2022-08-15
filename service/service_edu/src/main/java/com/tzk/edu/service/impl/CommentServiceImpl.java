package com.tzk.edu.service.impl;

import com.tzk.edu.entity.Comment;
import com.tzk.edu.mapper.CommentMapper;
import com.tzk.edu.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author zkTang
 * @since 2022-08-04
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
