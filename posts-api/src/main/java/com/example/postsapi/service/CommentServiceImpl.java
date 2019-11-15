package com.example.postsapi.service;

import com.example.postsapi.model.Comment;
import com.example.postsapi.model.CommentWithDetails;
import com.example.postsapi.rabbitmq.Sender;
import com.example.postsapi.repository.PostCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private Sender sender;

    @Autowired
    private PostCommentRepository postCommentRepository;

    @Override
    public Comment createComment(String username, Long postId, Comment text){
        Comment comment = sender.createComment(username, postId, text);
        return comment;
    }

    @Override
    public List<CommentWithDetails> getComments(Long postId) {
        List<Comment> commentList = postCommentRepository.getCommentsByPostId(postId);

        List<CommentWithDetails> commentWithDetails = new ArrayList<>();
        for(int i = 0; i < commentList.size(); i++){

            commentWithDetails.add()
        }
        return commentWithDetails;
    }
}
