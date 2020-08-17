package com.landvibe.reviewtest.promise

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/*
    Room에서 사용하는 모델 - 이렇게 하면 디비에 테이블이 생성되고 사용할 수 있다.
    @Entity - 디비 테이블이라고 생각
    @PrimaryKey - 데이터 구분을 위한 기본 키(학번같은 식별자), autoGenerate - 1부터 자동으로 생성
    아래 처럼 하면 Room을 이용해 memo 테이블이 id, title, content, alarm 이란 컬럼을 가지고 생성된다.
 */
@Entity
data class Promise(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val time: Long,
    val date: String,
    val promise: String
)