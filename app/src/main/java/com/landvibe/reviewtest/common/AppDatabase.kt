package com.landvibe.reviewtest.common

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.landvibe.reviewtest.diary.Diary
import com.landvibe.reviewtest.diary.DiaryDao


/*
    Room을 사용하기 위한 클래스,
    DB의 기본적인 사용을 나타낸다.
    entities - Room에서 관리하는 클래스들
    version - 앱 버전이 아닌 디비의 테이블등이 수정 되는 경우 버전을 올려주고 마이그레이션(디비 정보 변경)을 하기 위함
    abstract fun xxxDao - 사용하는 Data access object를 미리 선언
    companion object, instance - 데이터 베이스를 접근을 한곳으로 하기 위한 오브젝트 생성
    Room.databaseBuilder - context와 db이름 설정 들을 추가
 */
@Database(entities = [Diary::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun diaryDao(): DiaryDao

    companion object {
        val instance = Room.databaseBuilder(
            App.instance,
            AppDatabase::class.java, "diary.db"
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }
}