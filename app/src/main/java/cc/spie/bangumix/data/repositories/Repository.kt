package cc.spie.bangumix.data.repositories

abstract class Repository {

    suspend fun <T> invokeApi(apiCall: suspend () -> T): Result<T> {
        return try {
            Result.success(apiCall())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}