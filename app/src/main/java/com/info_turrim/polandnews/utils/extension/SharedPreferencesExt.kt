package com.info_turrim.polandnews.utils.extension

import android.content.SharedPreferences
import androidx.core.content.edit
import com.info_turrim.polandnews.options.domain.model.Profile

private const val FIRST_LAUNCH = "FIRST_LAUNCH"
private const val IS_USER_LOGGED_IN = "IS_USER_LOGGED_IN"
private const val ACCESS_TOKEN = "ACCESS_TOKEN"
private const val REFRESH_TOKEN = "REFRESH_TOKEN"
private const val IS_REF_REPORTED = "IS_REF_REPORTED"
private const val IS_USER_REAL = "IS_USER_REAL"
private const val IS_GOOGLE_ACCOUNT = "IS_GOOGLE_ACCOUNT"
private const val IS_POLICY_ACCEPTED = "IS_POLICY_ACCEPTED"
private const val UUID = "UUID"

private const val USER_EMAIL = "USER_EMAIL"
private const val USER_NAME = "USER_NAME"
private const val USER_CITY = "USER_CITY"
private const val USER_COUNTRY = "USER_COUNTRY"
private const val USER_GCLID = "USER_GCLID"
private const val USER_PASSWORD = "USER_PASSWORD"
private const val USER_SEX = "USER_SEX"
private const val USER_YEAR_OF_BIRTH = "USER_YEAR_OF_BIRTH"
private const val USER_ID = "USER_ID"

fun SharedPreferences.setFirstLaunch(isFirstLaunch: Boolean) {
    edit { putBoolean(FIRST_LAUNCH, isFirstLaunch) }
}

fun SharedPreferences.getFirstLaunch() = getBoolean(FIRST_LAUNCH, true)

fun SharedPreferences.setIsUserLoggedIn(isUserLoggedIn: Boolean) {
    edit { putBoolean(IS_USER_LOGGED_IN, isUserLoggedIn) }
}

fun SharedPreferences.getIsUserLoggedIn() = getBoolean(IS_USER_LOGGED_IN, false)

fun SharedPreferences.setToken(token: com.info_turrim.polandnews.auth.data.model.TokenResponse) {
    edit {
        putString(ACCESS_TOKEN, token.access_token)
        putString(REFRESH_TOKEN, token.refresh_token)
    }
}

fun SharedPreferences.getAccessToken() = getString(ACCESS_TOKEN, String.EMPTY) ?: String.EMPTY

fun SharedPreferences.getRefreshToken() = getString(REFRESH_TOKEN, String.EMPTY) ?: String.EMPTY

fun SharedPreferences.clearToken() {
    edit {
        remove(ACCESS_TOKEN)
        remove(REFRESH_TOKEN)
    }
}

fun SharedPreferences.setRefReported() {
    edit {
        putBoolean(IS_REF_REPORTED, true)
    }
}

fun SharedPreferences.setUserEmail(email: String) {
    edit {
        putString(USER_EMAIL, email)
    }
}

fun SharedPreferences.getUserEmail() = getString(USER_EMAIL, String.EMPTY) ?: String.EMPTY

fun SharedPreferences.setUserName(email: String) {
    edit {
        putString(USER_NAME, email)
    }
}

fun SharedPreferences.getUserName() = getString(USER_NAME, String.EMPTY) ?: String.EMPTY

fun SharedPreferences.setUserCity(city: String) {
    edit {
        putString(USER_CITY, city)
    }
}

fun SharedPreferences.getUserCity() = getString(USER_CITY, String.EMPTY) ?: String.EMPTY

fun SharedPreferences.setUserCountry(country: String) {
    edit {
        putString(USER_COUNTRY, country)
    }
}

fun SharedPreferences.getUserCountry() = getString(USER_COUNTRY, String.EMPTY) ?: String.EMPTY

fun SharedPreferences.setUserGCLID(gclid: String) {
    edit {
        putString(USER_GCLID, gclid)
    }
}

fun SharedPreferences.getUserGCLID() = getString(USER_GCLID, String.EMPTY) ?: String.EMPTY

fun SharedPreferences.setUserPassword(password: String) {
    edit {
        putString(USER_PASSWORD, password)
    }
}

fun SharedPreferences.getUserPassword() = getString(USER_PASSWORD, String.EMPTY) ?: String.EMPTY

fun SharedPreferences.setUserSex(sex: Int) {
    edit {
        putInt(USER_SEX, sex)
    }
}

fun SharedPreferences.getUserSex() = getInt(USER_SEX, -1)

fun SharedPreferences.setUserYearOfBirth(year: Int) {
    edit {
        putInt(USER_YEAR_OF_BIRTH, year)
    }
}

fun SharedPreferences.getUserYearOfBirth() = getInt(USER_YEAR_OF_BIRTH, -1)

fun SharedPreferences.setProfile(profile: Profile) {
    profile.apply {
        setUserId(profile.id)
        setUserCity(city.orEmpty())
        setUserCountry(country.orEmpty())
        setUserEmail(email)
        setUserGCLID(gclid.orEmpty())
        setUserSex(sex ?: -1)
        setUserName(username)
        setUserYearOfBirth(year_of_birth ?: -1)
    }
}

fun SharedPreferences.getProfile() = Profile(
    id = getUserId(),
    city = getUserCity(),
    country = getUserCountry(),
    email = getUserEmail(),
    gclid = getUserGCLID(),
    password = getUserPassword(),
    sex = getUserSex(),
    username = getUserName(),
    year_of_birth = getUserYearOfBirth()
)

fun SharedPreferences.setIsUserReal(isReal: Boolean) {
    edit {
        putBoolean(IS_USER_REAL, isReal)
    }
}

fun SharedPreferences.getIsUserReal() = getBoolean(IS_USER_REAL, false)

fun SharedPreferences.setUserId(userId: Int) {
    edit {
        putInt(USER_ID, userId)
    }
}

fun SharedPreferences.getUserId() = getInt(USER_ID, -1)

fun SharedPreferences.clearUserData() {
    clearToken()
    edit {
        remove(USER_EMAIL)
        remove(USER_NAME)
        remove(USER_CITY)
        remove(USER_COUNTRY)
        remove(USER_GCLID)
        remove(USER_PASSWORD)
        remove(USER_SEX)
        remove(USER_YEAR_OF_BIRTH)
    }
    setIsUserReal(false)
    setIsUserLoggedIn(false)
}

fun SharedPreferences.setIsGoogleAccount(isGoogleAccount: Boolean) {
    edit {
        putBoolean(IS_GOOGLE_ACCOUNT, isGoogleAccount)
    }
}

fun SharedPreferences.getIsGoogleAccount() = getBoolean(IS_GOOGLE_ACCOUNT, false)

fun SharedPreferences.isTermsPolicyAccepted() = getBoolean(IS_POLICY_ACCEPTED, false)
fun SharedPreferences.setIsTermsPolicyAccepted(isTermsPolicyAccepted: Boolean) {
    edit { putBoolean(IS_POLICY_ACCEPTED, isTermsPolicyAccepted) }
}

fun SharedPreferences.getUUID() = getString(UUID, "") ?: ""
fun SharedPreferences.setUUID(uuid: String) {
    edit { putString(UUID, uuid) }
}


