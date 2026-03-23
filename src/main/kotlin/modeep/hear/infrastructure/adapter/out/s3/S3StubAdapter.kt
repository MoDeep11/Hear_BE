package modeep.hear.infrastructure.adapter.out.s3

import modeep.hear.domain.s3.model.FileData
import modeep.hear.domain.s3.port.out.S3Port
import org.springframework.stereotype.Component

@Component
class S3StubAdapter : S3Port {
    // S3Port 인터페이스에 정의된 메서드들을 오버라이드합니다.
    override fun upload(file: FileData): String {
        // 나중에 구현할 거니까 지금은 임시 문자열이나 고정 URL을 반환하게 둡니다.
        return "https://temporary-url.com/mock-image.png"
    }

    override fun delete(s3Url: String) {
    }
}
