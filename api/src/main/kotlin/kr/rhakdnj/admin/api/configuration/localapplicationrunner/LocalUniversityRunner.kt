package kr.rhakdnj.admin.api.configuration.localapplicationrunner

import kr.rhakdnj.core.domain.common.vo.ImageUrl
import kr.rhakdnj.core.domain.university.University
import kr.rhakdnj.core.domain.university.repository.UniversityRepository
import kr.rhakdnj.core.domain.university.vo.UniversityCategory
import kr.rhakdnj.core.domain.university.vo.UniversityEntranceProcess
import kr.rhakdnj.core.domain.university.vo.UniversityName
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("local")
class LocalUniversityRunner(
	private val universityRepository: UniversityRepository,
) {
	fun init() {
		universityRepository.saveAll(
			listOf(
				University(
					isMain = true,
					name = UniversityName("서울대학교"),
					image = ImageUrl("https://simpple-backend.s3.ap-northeast-2.amazonaws.com/university/seoul.png"),
					category = UniversityCategory.UNIVERSITY,
					entranceProcess = UniversityEntranceProcess.EXAM,
				),
				University(
					isMain = true,
					name = UniversityName("연세대학교"),
					image = ImageUrl("https://simpple-backend.s3.ap-northeast-2.amazonaws.com/university/yonsei.png"),
					category = UniversityCategory.UNIVERSITY,
					entranceProcess = UniversityEntranceProcess.EXAM,
				),
				University(
					isMain = true,
					name = UniversityName("고려대학교"),
					image = ImageUrl("https://simpple-backend.s3.ap-northeast-2.amazonaws.com/university/korea.png"),
					category = UniversityCategory.UNIVERSITY,
					entranceProcess = UniversityEntranceProcess.EXAM,
				),
				University(
					isMain = false,
					name = UniversityName("서강대학교"),
					image = ImageUrl("https://simpple-backend.s3.ap-northeast-2.amazonaws.com/university/sogang.png"),
					category = UniversityCategory.UNIVERSITY,
					entranceProcess = UniversityEntranceProcess.EXAM,
				),
			),
		)
	}
}
