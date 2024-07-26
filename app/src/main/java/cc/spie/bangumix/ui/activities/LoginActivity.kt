package cc.spie.bangumix.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import cc.spie.bangumix.BuildConfig
import cc.spie.bangumix.R
import cc.spie.bangumix.data.repositories.AuthorizationRepository
import cc.spie.bangumix.ui.theme.BangumiXTheme
import cc.spie.bangumix.ui.theme.BladerRounded
import cc.spie.bangumix.utils.informError
import cc.spie.bangumix.utils.preferences.LoginInfoManager
import cc.spie.bangumix.utils.types.LoginInfo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    @Inject
    lateinit var loginInfoManager: LoginInfoManager

    @Inject
    lateinit var authorizationRepository: AuthorizationRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val data = intent.data

        if (data != null) {
            val code = data.getQueryParameter("code")
            if (code != null) {
                CoroutineScope(Dispatchers.IO).launch {
                    val loginInfo = authorizationRepository.getAccessToken(code)
                    if (loginInfo.isSuccess) {
                        val info = loginInfo.getOrNull()!!
                        loginInfoManager.saveLogin(info)
                        gotoMainActivity()
                    } else {
                        val e = loginInfo.exceptionOrNull()
                        informError(e)
                    }
                }
            }
        }

        enableEdgeToEdge()
        setContent {
            BangumiXTheme {
                val scope = rememberCoroutineScope()

                var gettingSavedLoginInfo by remember {
                    mutableStateOf(false)
                }

                var refreshingSavedAccessToken by remember {
                    mutableStateOf(false)
                }

                var notLoggedIn by remember {
                    mutableStateOf(false)
                }

                LaunchedEffect(key1 = Unit) {
                    scope.launch {
                        gettingSavedLoginInfo = true
                        val loginInfo = loginInfoManager.getSavedLogin()
                        if (loginInfo == null) {
                            notLoggedIn = true
                        } else {
                            if (loginInfo.isExpired()) {
                                refreshingSavedAccessToken = true
                                val newToken = refreshAccessToken(loginInfo.refreshCode)
                                if (newToken == null) {
                                    notLoggedIn = true
                                } else {
                                    loginInfoManager.saveLogin(newToken)
                                    gotoMainActivity()
                                }
                                refreshingSavedAccessToken = false
                            } else {
                                gotoMainActivity()
                            }
                        }
                        gettingSavedLoginInfo = false
                    }
                }

                if (notLoggedIn) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "BANGUMIX",
                                fontFamily = BladerRounded,
                                fontSize = TextUnit(48f, TextUnitType.Sp),
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                                .padding(horizontal = 8.dp)
                        ) {
                            Button(onClick = {
                                // open oauth url in browser
                                val intent = Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("https://bgm.tv/oauth/authorize?client_id=${BuildConfig.CLIENT_ID}&response_type=code&redirect_uri=${BuildConfig.REDIRECT_URI}")
                                )
                                startActivity(intent)
                            }, modifier = Modifier.weight(1f)) {
                                Text(text = stringResource(R.string.login))
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            TextButton(onClick = {
                                gotoMainActivity()
                            }, modifier = Modifier.weight(1f)) {
                                Text(text = stringResource(R.string.skip))
                            }
                        }
                    }
                }
            }
        }
    }

    private fun gotoMainActivity() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }

    private suspend fun refreshAccessToken(token: String): LoginInfo? {
        return authorizationRepository.refreshAccessToken(token).let {
            if (it.isSuccess) {
                it.getOrNull()
            } else {
                val e = it.exceptionOrNull()
                informError(e)
                null
            }
        }
    }
}