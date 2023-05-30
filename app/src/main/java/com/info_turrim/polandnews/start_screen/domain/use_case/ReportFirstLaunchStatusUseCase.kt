package com.info_turrim.polandnews.start_screen.domain.use_case

import android.content.SharedPreferences
import com.info_turrim.polandnews.AnalyticsReporter
import com.info_turrim.polandnews.auth.domain.AuthRepository
import javax.inject.Inject

class ReportFirstLaunchStatusUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val sharedPreferences: SharedPreferences,
    private val analyticsReporter: AnalyticsReporter
)/* : BaseUseCase<Boolean, Unit>() {

    override suspend fun run(param: Boolean): Result<Unit> {
        return Result.Success(analyticsReporter.reportFirstLaunchStatus(param))
    }
}*/