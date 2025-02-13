package dev.trimpsuz.boosuu

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.trimpsuz.boosuu.ui.theme.BoosuuTheme
import java.io.DataOutputStream


class MainActivity : ComponentActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        var moduleEnabled: Boolean;

        try {
            sharedPreferences = getSharedPreferences("app_preferences", MODE_WORLD_READABLE)
            moduleEnabled = true
        } catch (t: Throwable) {
            moduleEnabled = false
        }

        setContent {
            BoosuuTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val context = LocalContext.current

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .then(
                                if (!moduleEnabled) Modifier.clickable { restartApp(context) }
                                else Modifier
                            ),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (moduleEnabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = if(moduleEnabled) Icons.Outlined.CheckCircle else Icons.Outlined.Info,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.surface
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = if(moduleEnabled) "Module enabled" else "Module not enabled",
                                    color = MaterialTheme.colorScheme.surface,
                                    fontWeight = FontWeight.Bold
                                )
                                if(!moduleEnabled) {
                                    Text(
                                        text = "Click here to restart the software",
                                        color = MaterialTheme.colorScheme.surface
                                    )
                                }
                            }
                        }
                    }

                    if(moduleEnabled) {
                        var logging by remember { mutableStateOf(sharedPreferences.getBoolean("pref_enable_logging", false)) }

                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "Logging: ",
                                fontSize = 20.sp,
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Switch(
                                checked = logging,
                                onCheckedChange = { state ->
                                    logging = state
                                    sharedPreferences.edit().putBoolean("pref_enable_logging", state).apply()
                                }
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                restartScopedSoftware(context)
                            },
                        ) {
                            Text(
                                text = "Restart scoped app",
                                color = MaterialTheme.colorScheme.surface,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }

    private fun restartScopedSoftware(context: Context) {
        try {
            val packageName = "com.busuu.android.enc"
            val process = Runtime.getRuntime().exec("su")
            val outputStream = DataOutputStream(process.outputStream)
            outputStream.writeBytes("pkill -f $packageName\n")
            outputStream.writeBytes("exit\n")
            outputStream.flush()
            process.waitFor()

            val intent = context.packageManager.getLaunchIntentForPackage(packageName)
            if(intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }
        } catch (_: Throwable) { }
    }

    private fun restartApp(context: Context) {
        val packageManager = context.packageManager
        val intent = packageManager.getLaunchIntentForPackage(context.packageName)
        val componentName = intent?.component;
        val mainIntent = Intent.makeRestartActivityTask(componentName)

        mainIntent.setPackage(context.packageName)
        context.startActivity(mainIntent)
        Runtime.getRuntime().exit(0)
    }
}


