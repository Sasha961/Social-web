package com.example.service;

import com.example.dto.PostStatisticRequestDto;
import com.example.repository.CommentRepository;
import com.example.repository.LikeRepository;
import com.example.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService{

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    @Override
    public long postStatistic(PostStatisticRequestDto postStatisticRequest) {
        return postRepository.count();
    }

    @Override
    public long likeStatistic(PostStatisticRequestDto postStatisticRequest) {
        return likeRepository.count();
    }

    @Override
    public long commentStatistic(PostStatisticRequestDto postStatisticRequest) {
        return commentRepository.count();
    }
}
