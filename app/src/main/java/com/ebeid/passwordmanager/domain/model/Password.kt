package com.ebeid.passwordmanager.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ebeid.passwordmanager.utils.Constants.PASSWORDS_TABLE
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = PASSWORDS_TABLE)
class PasswordModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var title: String,
    var account: String,
    var username: String,
    var password: String
) : Parcelable{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PasswordModel

        if (id != other.id) return false
        if (title != other.title) return false
        if (account != other.account) return false
        if (username != other.username) return false
        if (password != other.password) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + title.hashCode()
        result = 31 * result + account.hashCode()
        result = 31 * result + username.hashCode()
        result = 31 * result + password.hashCode()
        return result
    }
}