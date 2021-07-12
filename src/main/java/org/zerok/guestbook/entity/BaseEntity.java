package org.zerok.guestbook.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass //테이블로 생성되지 않는다
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
abstract class BaseEntity {

    @CreatedDate //jpa에서 엔티티의 생성 시간을 처리
    @Column(name="regdate" ,updatable = false) //updateable=false는 데이터베이스 반영할때 컬럼값 변경 x
    private LocalDateTime regDate;

    @LastModifiedDate //최동 수정 시간을 자동으로 처리 ,특정한 엔티티를 수정한 후에 save() 했을경우 동작
    @Column(name ="moddate")
    public  LocalDateTime modDate;
}
