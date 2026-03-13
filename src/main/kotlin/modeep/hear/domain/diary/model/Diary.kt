package modeep.hear.domain.diary.model

import modeep.hear.domain.common.annotation.Aggregate
import modeep.hear.domain.common.model.base.BaseTime
import modeep.hear.domain.common.model.emotion.Emotion
import modeep.hear.domain.diary.type.DiarySourceType
import java.util.UUID

@Aggregate
data class Diary(
    val id: UUID? = null,
    val userId: UUID? = null,
    val content: String,
    val emotion: Emotion,
    val tags: List<String>,
    val baseTime: BaseTime,
    val sourceType: DiarySourceType = DiarySourceType.AI_CHAT,
    val sessionId: UUID? = null
)

// CREATE TABLE "diary" (
//  "id" integer PRIMARY KEY,
//  "user_id" integer,
//  "content" text,
//  "emotion" varchar,
//  "tags" json,
//  "created_at" timestamp,
//  "updated_at" timestamp,
//  "source_type" varchar,
//  "session_id" integer
//);
