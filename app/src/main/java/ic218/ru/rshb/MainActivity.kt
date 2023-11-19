package ic218.ru.rshb

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.tech.MifareClassic
import android.nfc.tech.NfcA
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ic218.ru.rshb.feature.auth.AuthScreen
import ic218.ru.rshb.feature.main.MainScreen
import ic218.ru.rshb.feature.splash.SplashScreen
import ic218.ru.rshb.ui.theme.RSHBTheme
import ic218.ru.rshb.utils.NfcManager
import org.koin.android.ext.android.getKoin

class MainActivity : ComponentActivity() {

    private var pendingIntent: PendingIntent? = null
    private var intentFiltersArray: Array<IntentFilter>? = null
    private val techListsArray = arrayOf(arrayOf(MifareClassic::class.java.name, NfcA::class.java.name))
    private val nfcAdapter: NfcAdapter? by lazy { NfcAdapter.getDefaultAdapter(this) }

    private val nfcManager = getKoin().get<NfcManager>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeNfcCommands()

        enableEdgeToEdge()

        setContent {
            RSHBTheme {
                val navController = rememberNavController()

                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = Color(0xFF131B25)
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .safeDrawingPadding()
                    ) {
                        NavHost(navController = navController, startDestination = "splash") {
                            composable("splash") { SplashScreen(navController) }
                            composable("auth") { AuthScreen(navController) }
                            composable("main") { MainScreen(navController) }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        nfcManager.clearListeners()
        super.onDestroy()
    }

    private fun subscribeNfcCommands() {
        nfcManager.addCommandListener(NfcManager.CommandListener { command ->
            when (command) {
                is NfcManager.Command.Init -> initNfc()
                is NfcManager.Command.EnableForeground -> {
                    nfcAdapter?.enableForegroundDispatch(
                        this@MainActivity,
                        pendingIntent,
                        intentFiltersArray,
                        techListsArray
                    )
                }

                is NfcManager.Command.DisableForeground -> {
                    nfcAdapter?.disableForegroundDispatch(this@MainActivity)
                }
            }
        })
    }

    private fun initNfc() {
        pendingIntent = PendingIntent.getActivity(this, 0, Intent(this, javaClass), PendingIntent.FLAG_MUTABLE)
        val ndef = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)
        try {
            ndef.addDataType("*/*")
        } catch (e: IntentFilter.MalformedMimeTypeException) {
            throw RuntimeException("fail", e)
        }
        intentFiltersArray = arrayOf(ndef)
        if (nfcAdapter == null) {
            nfcManager.sendResult(NfcManager.Result.Inited(false))
            AlertDialog.Builder(this@MainActivity, R.style.MyDialogStyle)
                .apply {
                    setMessage("This device doesn't support NFC.")
                    setPositiveButton("Cancel") { _, _ -> finish() }
                }
                .create()
                .apply {
                    setCanceledOnTouchOutside(false)
                }
                .show()

        } else if (nfcAdapter?.isEnabled == false) {
            nfcManager.sendResult(NfcManager.Result.Inited(false))
            AlertDialog.Builder(this@MainActivity, R.style.MyDialogStyle)
                .apply {
                    setTitle("NFC Disabled")
                    setMessage("Plesae Enable NFC")
                    setPositiveButton("Settings") { _, _ -> startActivity(Intent(Settings.ACTION_NFC_SETTINGS)) }
                    setNegativeButton("Cancel") { _, _ -> finish() }
                }
                .create()
                .apply {
                    setCanceledOnTouchOutside(false)
                }
                .show()
        } else {
            nfcManager.sendResult(NfcManager.Result.Inited(true))
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        val action = intent.action

        if (NfcAdapter.ACTION_TECH_DISCOVERED == action || NfcAdapter.ACTION_NDEF_DISCOVERED == action) {
            var st = ""
            val msg = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID)
            if (msg != null) {
                st = msg.joinToString(separator = ":") { eachByte -> "%02x".format(eachByte) }
            }
            nfcManager.sendResult(NfcManager.Result.Success(st))
        }
    }
}