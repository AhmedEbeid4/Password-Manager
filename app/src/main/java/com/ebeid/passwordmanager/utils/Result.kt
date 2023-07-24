package com.ebeid.passwordmanager.utils

data class DataStatus<out T>(
    val status: Status,
    val data: T? = null,
    val message: String? = null,
    val isEmpty: Boolean? = false
) {
    enum class Status {
        LOADING, SUCCESS, FAIL
    }

    companion object {
        fun <T> loading(): DataStatus<T> {
            return DataStatus(Status.LOADING)
        }

        fun <T> success(data: T?, isEmpty: Boolean?): DataStatus<T> {
            return DataStatus(Status.SUCCESS, data, isEmpty = isEmpty)
        }

        fun <T> error(error: String): DataStatus<T> {
            return DataStatus(Status.FAIL, message = error)
        }
    }
}

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}
