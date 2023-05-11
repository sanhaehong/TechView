package com.sanhaehong.project.techview.domain.question;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
    NETWORK("네트워크"),
    OPERATION_SYSTEM("운영체제"),
    DATABASE("데이터베이스"),
    DATA_STRUCTURE("자료구조"),
    ALGORITHM("알고리즘"),
    SECURITY("보안"),
    JAVA("자바"),
    SPRING("스프링"),
    JPA("JPA");

    private final String name;
}
