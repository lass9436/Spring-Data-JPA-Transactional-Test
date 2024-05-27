# Spring Data JPA에 트랜잭션 적용

JPA를 사용할 때 트랜잭션을 꼭 사용해야 한다고 합니다. 그런데 트랜잭션을 적용했을 때와 그렇지 않았을 때 어떤 차이가 있는지 궁금해서 직접 테스트해보기로 했습니다. PlatformTransactionManager를 사용해서 수동으로 트랜잭션을 적용해보고, @Transactional 어노테이션을 사용했을 때와 어떤 차이가 있는지도 살펴보고자 하는 레포지토리입니다.
# 관련 블로그 포스팅

해당 레포지토리에 대한 설명을 블로그 글에 작성해놨습니다.

[![블로그 포스팅](https://img.shields.io/badge/블로그-포스팅으로%20이동-orange?logo=tistory)](https://lass9436.tistory.com/4)
