package cc.spie.bangumix.data.webparsers

import cc.spie.bangumix.data.models.Subject
import cc.spie.bangumix.data.models.SubjectType
import cc.spie.bangumix.data.repositories.BangumiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.util.Locale

class Ranking(
    type: SubjectType,
    private val bangumiRepository: BangumiRepository
) {

    private val pages: MutableMap<Int, List<Subject>> = mutableMapOf()

    private val baseUrl = if (type != SubjectType.Real) {
        "https://bgm.tv/${type.name.lowercase(Locale.ENGLISH)}/browser?sort=rank"
    } else {
        "https://bgm.tv/real/browser/platform/all?sort=rank"
    }

    private suspend fun subjectFromElement(element: Element): Result<Subject> {
        val subjectId = element.id().split("_")[1].toInt()
        return bangumiRepository.getSubject(subjectId)
    }

    suspend fun requestPage(index: Int) = withContext(Dispatchers.IO) {
        if (pages.containsKey(index)) return@withContext Result.success(pages[index])
        val url = if (index == 0) baseUrl else "$baseUrl&page=${index + 1}"
        val doc = Jsoup.connect(url).get()
        val subjects = doc.select("#browserItemList > li")
        val list = mutableListOf<Subject>()
        val subjectsDeferred = subjects.map { element ->
            async {
                subjectFromElement(element)
            }
        }
        val results = subjectsDeferred.awaitAll()
        results.forEach {
            if (it.isSuccess) {
                list.add(it.getOrNull()!!)
            } else {
                return@withContext Result.failure(it.exceptionOrNull()!!)
            }
        }
        pages[index] = list
        Result.success(list)
    }
}